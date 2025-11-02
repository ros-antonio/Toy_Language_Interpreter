package model.statement;

import model.state.ProgramState;
import model.type.Type;
import exceptions.StatementException;

public record VariableDeclarationStatement(Type type, String variableName) implements IStatement {

    @Override
    public ProgramState execute(ProgramState state) {
        var symbolTable = state.getSymTable();
        if (symbolTable.hasKey(variableName)) {
            throw new StatementException("Variable already defined");
        }
        symbolTable.insert(variableName, type.getDefaultValue());
        return state;
    }
}
