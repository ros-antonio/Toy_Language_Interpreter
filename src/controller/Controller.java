package controller;

import exceptions.ADTException;
import exceptions.ExpressionException;
import exceptions.RepositoryException;
import exceptions.StatementException;
import model.containers.IStack;
import model.state.ProgramState;
import model.statement.IStatement;
import repository.IRepository;

public class Controller {
    public final IRepository repository;
    private final boolean displayFlag;

    public Controller(IRepository repository, boolean displayFlag) {
        this.repository = repository;
        this.displayFlag = displayFlag;
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

        if (displayFlag) {
            System.out.println("--- Initial Program State ---");
            System.out.println(programState);
        }

        repository.logPrgStateExec();

        while (!programState.getExeStack().isEmpty()) {
            oneStep(programState);

            if (displayFlag) {
                System.out.println("--- Next Step State ---");
                System.out.println(programState);
            }
            repository.logPrgStateExec();
        }
    }
}