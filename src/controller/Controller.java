package controller;

import exceptions.RepositoryException;
import model.state.ProgramState;
import model.value.IValue;
import model.value.RefValue;
import repository.IRepository;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

public class Controller {
    public final IRepository repository;
    private final ExecutorService executor;
    public Controller(IRepository repository) {
        this.repository = repository;
        this.executor = Executors.newFixedThreadPool(2);
    }

    public List<ProgramState> removeCompletedPrg(List<ProgramState> inPrgList) {
        return inPrgList.stream()
                .filter(ProgramState::isNotCompleted)
                .collect(Collectors.toList());
    }

    public void oneStepForAllPrg(List<ProgramState> prgList) throws InterruptedException {
        prgList.forEach(prg -> {
            try {
                repository.logPrgStateExec(prg);
            } catch (RepositoryException e) {
                System.out.println(e.getMessage());
            }
        });

        List<Callable<ProgramState>> callList = prgList.stream()
                .map((ProgramState p) -> (Callable<ProgramState>) (p::oneStep))
                .collect(Collectors.toList());

        List<ProgramState> newPrgList = executor.invokeAll(callList).stream()
                .map(future -> {
                    try {
                        return future.get();
                    } catch (InterruptedException | ExecutionException e) {
                        System.out.println(e.getMessage());
                        return null;
                    }
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

        prgList.addAll(newPrgList);

        prgList.forEach(prg -> {
            try {
                repository.logPrgStateExec(prg);
            } catch (RepositoryException e) {
                System.out.println(e.getMessage());
            }
        });

        repository.setPrgList(prgList);
    }

    public void shutdown() {
        executor.shutdownNow();
    }

    public void allSteps() throws InterruptedException {
        List<ProgramState> prgList = removeCompletedPrg(repository.getPrgList());

        while (!prgList.isEmpty()) {
            List<Integer> symTableAddr = prgList.stream()
                    .flatMap(p -> getAddrFromValues(p.getSymTable().getContent().values()).stream())
                    .collect(Collectors.toList());

            Map<Integer, IValue> newHeap = safeGarbageCollector(symTableAddr, prgList.getFirst().getHeap().getContent());
            prgList.getFirst().getHeap().setContent(newHeap);

            oneStepForAllPrg(prgList);

            prgList = removeCompletedPrg(repository.getPrgList());
        }

        executor.shutdownNow();

        repository.setPrgList(prgList);
    }

    private List<Integer> getAddrFromValues(Collection<IValue> symTableValues) {
        return symTableValues.stream()
                .filter(v -> v instanceof RefValue)
                .map(v -> {
                    RefValue v1 = (RefValue) v;
                    return v1.address();
                })
                .collect(Collectors.toList());
    }

    private Map<Integer, IValue> safeGarbageCollector(
            List<Integer> symTableAddr, Map<Integer, IValue> heap) {
        // find the addresses that are referenced from the symbol table (this makes it safe)
        List<Integer> reachableAddresses = getAddrFromValues(heap.values());
        reachableAddresses.addAll(symTableAddr);

        return heap.entrySet().stream()
                .filter(e -> reachableAddresses.contains(e.getKey()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }
}