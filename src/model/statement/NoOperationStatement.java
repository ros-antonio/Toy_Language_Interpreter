package model.statement;

import exceptions.StatementException;
import model.containers.IDictionary;
import model.state.ProgramState;
import model.type.IType;

public class NoOperationStatement implements IStatement {
    @Override
    public ProgramState execute(ProgramState state) {
        return null;
    }

    @Override
    public IDictionary<String, IType> typecheck(IDictionary<String, IType> typeEnv) throws StatementException {
        return typeEnv;
    }

    @Override
    public String toString() {
        return "nop";
    }
}
