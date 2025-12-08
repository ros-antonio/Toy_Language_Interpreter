package model.statement;

import model.state.ProgramState;

public record CompoundStatement(IStatement first, IStatement second) implements IStatement {

    @Override
    public ProgramState execute(ProgramState state) {
        var stack = state.getExeStack();
        stack.push(second);
        stack.push(first);
        return null;
    }

    @Override
    public String toString() {
        return "(" + first.toString() + "; " + second.toString() + ")";
    }
}
