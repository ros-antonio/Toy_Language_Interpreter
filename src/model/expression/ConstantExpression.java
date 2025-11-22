package model.expression;

import model.containers.IDictionary;
import model.containers.IHeap;
import model.value.IValue;

public record ConstantExpression (IValue value) implements IExpression {

    @Override
    public IValue evaluate(IDictionary<String, IValue> symTable, IHeap<IValue> heap) {
        return value;
    }

    @Override
    public String toString() {
        return value.toString();
    }
}
