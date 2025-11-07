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
        }
    }
}