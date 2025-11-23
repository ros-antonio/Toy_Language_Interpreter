package controller;

import exceptions.ADTException;
import exceptions.ExpressionException;
import exceptions.RepositoryException;
import exceptions.StatementException;
import model.containers.IStack;
import model.state.ProgramState;
import model.statement.IStatement;
import model.value.IValue;
import model.value.RefValue;
import repository.IRepository;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Controller {
    public final IRepository repository;

    public Controller(IRepository repository) {
        this.repository = repository;
    }

    public ProgramState oneStep(ProgramState programState)
            throws StatementException, ADTException, ExpressionException {
        IStack<IStatement> exeStack = programState.getExeStack();

        if (exeStack.isEmpty()) {
            throw new StatementException("Program state stack is empty");
        }

        IStatement statement = exeStack.pop();
        return statement.execute(programState);
    }

    public void allSteps() throws StatementException, ADTException, ExpressionException, RepositoryException {
        ProgramState programState = repository.getCrtPrg();
        repository.logPrgStateExec();

        while (!programState.getExeStack().isEmpty()) {
            oneStep(programState);
            repository.logPrgStateExec();
            List<Integer> symTableAddr = getAddrFromValues(programState.getSymTable().getContent().values());
            Map<Integer, IValue> newHeap = safeGarbageCollector(symTableAddr, programState.getHeap().getContent());
            programState.getHeap().setContent(newHeap);
            repository.logPrgStateExec();
        }
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