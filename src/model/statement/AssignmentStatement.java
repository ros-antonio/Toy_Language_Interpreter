package model.statement;

import model.containers.IDictionary;
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
    public IDictionary<String, IType> typecheck(IDictionary<String, IType> typeEnv) throws StatementException {
        if(!typeEnv.hasKey(variableName)) {
            throw new StatementException("Assignment: variable " + variableName + " not declared");
        }
        IType varType = typeEnv.get(variableName);
        IType expType = expression.typecheck(typeEnv);
        if (!varType.equals(expType)) {
            throw new StatementException("Assignment: right hand side and left hand side have different types");
        }
        return typeEnv;
    }

    @Override
    public String toString() {
        return variableName + " = " + expression.toString();
    }
}
