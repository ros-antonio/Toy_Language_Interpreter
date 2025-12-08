package model.statement;

import model.containers.GenericStack;
import model.containers.IDictionary;
import model.containers.IStack;
import model.state.ProgramState;
import model.value.IValue;
import exceptions.StatementException;

public record ForkStatement(IStatement statement) implements IStatement {

    @Override
    public ProgramState execute(ProgramState state) throws StatementException {
        IStack<IStatement> newStack = new GenericStack<>();

        IDictionary<String, IValue> newSymTable = state.getSymTable().deepCopy();

        ProgramState newThread = new ProgramState(
                newStack,
                newSymTable,
                state.getOut(),
                state.getFileTable(),
                state.getHeap()
        );

        newThread.getExeStack().push(statement);

        return newThread;
    }

    @Override
    public String toString() {
        return "fork(" + statement.toString() + ")";
    }
}