package model.expression;

import model.containers.IDictionary;
import model.value.IValue;

public record ConstantExpression (IValue value) implements IExpression {

    @Override
    public IValue evaluate(IDictionary<String, IValue> symTable) {
        return value;
    }

    @Override
    public String toString() {
        return value.toString();
    }
}
