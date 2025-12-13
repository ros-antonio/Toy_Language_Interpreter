package model.statement;

import exceptions.StatementException;
import model.containers.IDictionary;
import model.state.ProgramState;
import model.type.IType;

public interface IStatement {
    ProgramState execute(ProgramState state);
    IDictionary<String, IType> typecheck(IDictionary<String, IType> typeEnv) throws StatementException;
}
