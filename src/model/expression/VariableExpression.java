package model.expression;

import model.containers.IDictionary;
import model.containers.IHeap;
import model.value.IValue;
import exceptions.ADTException;
import exceptions.ExpressionException;

public record VariableExpression(String variableName) implements IExpression {

    @Override
    public IValue evaluate(IDictionary<String, IValue> symTable, IHeap<IValue> heap) throws ExpressionException {
        try {
            return symTable.get(variableName);
        } catch (ADTException e) {
            throw new ExpressionException("Variable not defined: " + variableName);
        }
    }

    @Override
    public String toString() {
        return variableName;
    }
}