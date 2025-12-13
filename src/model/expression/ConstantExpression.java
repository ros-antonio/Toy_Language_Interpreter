package model.expression;

import exceptions.ExpressionException;
import model.containers.IDictionary;
import model.containers.IHeap;
import model.type.IType;
import model.value.IValue;

public record ConstantExpression (IValue value) implements IExpression {

    @Override
    public IValue evaluate(IDictionary<String, IValue> symTable, IHeap<IValue> heap) {
        return value;
    }

    @Override
    public IType typecheck(IDictionary<String, IType> typeEnv) throws ExpressionException {
        return value.getType();
    }

    @Override
    public String toString() {
        return value.toString();
    }
}
