package model.statement;

import model.containers.IDictionary;
import model.state.ProgramState;
import model.type.IType;
import exceptions.StatementException;

public record VariableDeclarationStatement(IType type, String variableName) implements IStatement {

    @Override
    public ProgramState execute(ProgramState state) {
        var symbolTable = state.getSymTable();
        if (symbolTable.hasKey(variableName)) {
            throw new StatementException("Variable already defined");
        }
        symbolTable.insert(variableName, type.getDefaultValue());
        return null;
    }

    @Override
    public IDictionary<String, IType> typecheck(IDictionary<String, IType> typeEnv) throws StatementException {
        typeEnv.insert(variableName, type);
        return typeEnv;
    }

    @Override
    public String toString() {
        return type.toString().toLowerCase() + " " + variableName;
    }
}
