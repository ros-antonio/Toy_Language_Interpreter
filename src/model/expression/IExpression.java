package model.expression;

import model.containers.IDictionary;
import model.value.IValue;
import exceptions.ExpressionException;

public interface IExpression {
    IValue evaluate(IDictionary<String, IValue> symTable) throws ExpressionException;
}
