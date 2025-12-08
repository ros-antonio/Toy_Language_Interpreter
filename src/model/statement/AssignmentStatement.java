package model.statement;

import model.expression.IExpression;
import model.state.ProgramState;
import model.type.IType;
import model.value.IValue;
import exceptions.StatementException;

public record AssignmentStatement(IExpression expression, String variableName)
        implements IStatement {

    @Override
    public ProgramState execute(ProgramState state) {
        var symbolTable = state.getSymTable();
        var heap = state.getHeap();
        if (!symbolTable.hasKey(variableName)) {
            throw new StatementException("Variable not defined");
        }
        IValue value = expression.evaluate(symbolTable, heap);
        IType type = symbolTable.get(variableName).getType();
        if (!value.getType().equals(type)) {
            throw new StatementException("Type mismatch");
        }
        symbolTable.update(variableName, value);
        return null;
    }

    @Override
    public String toString() {
        return variableName + " = " + expression.toString();
    }
}
