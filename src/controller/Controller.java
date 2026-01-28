package controller;

import exceptions.RepositoryException;
import model.state.ProgramState;
import model.value.IValue;
import model.value.RefValue;
import repository.IRepository;

import java.util.*;
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
            List<IValue> symTableValues = prgList.stream()
                    .flatMap(p -> p.getSymTable().getContent().values().stream())
                    .collect(Collectors.toList());

            Map<Integer, IValue> heapContent = prgList.getFirst().getHeap().getContent();

            List<Integer> accessibleAddresses = getAccessibleAddresses(symTableValues, heapContent);
            Map<Integer, IValue> newHeap = safeGarbageCollector(accessibleAddresses, heapContent);

            prgList.getFirst().getHeap().setContent(newHeap);
            oneStepForAllPrg(prgList);

            prgList = removeCompletedPrg(repository.getPrgList());
        }

        executor.shutdownNow();

        repository.setPrgList(prgList);
    }

    public List<Integer> getAccessibleAddresses(Collection<IValue> symTableValues, Map<Integer, IValue> heap) {
        Set<Integer> visited = new HashSet<>();
        Deque<Integer> stack = new ArrayDeque<>();

        for (IValue v : symTableValues) {
            if (v instanceof RefValue) {
                int addr = ((RefValue) v).address();
                if (!visited.contains(addr)) {
                    visited.add(addr);
                    stack.push(addr);
                }
            }
        }

        while (!stack.isEmpty()) {
            int addr = stack.pop();
            if (heap.containsKey(addr)) {
                IValue val = heap.get(addr);
                if (val instanceof RefValue) {
                    int innerAddr = ((RefValue) val).address();
                    if (!visited.contains(innerAddr)) {
                        visited.add(innerAddr);
                        stack.push(innerAddr);
                    }
                }
            }
        }
        return new ArrayList<>(visited);
    }

    private Map<Integer, IValue> safeGarbageCollector(List<Integer> accessibleAddresses, Map<Integer, IValue> heap) {
        return heap.entrySet().stream()
                .filter(e -> accessibleAddresses.contains(e.getKey()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }
}