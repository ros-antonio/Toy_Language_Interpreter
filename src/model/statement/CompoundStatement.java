package model.statement;

import exceptions.StatementException;
import model.containers.IDictionary;
import model.state.ProgramState;
import model.type.IType;

public record CompoundStatement(IStatement first, IStatement second) implements IStatement {

    @Override
    public ProgramState execute(ProgramState state) {
        var stack = state.getExeStack();
        stack.push(second);
        stack.push(first);
        return null;
    }

    @Override
    public IDictionary<String, IType> typecheck(IDictionary<String, IType> typeEnv) throws StatementException {
        return second.typecheck(first.typecheck(typeEnv));
    }

    @Override
    public String toString() {
        return "(" + first.toString() + "; " + second.toString() + ")";
    }
}
