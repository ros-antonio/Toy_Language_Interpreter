package model.expression;

import model.containers.IDictionary;
import model.containers.IHeap;
import model.value.IValue;
import exceptions.ExpressionException;

public interface IExpression {
    IValue evaluate(IDictionary<String, IValue> symTable, IHeap<IValue> heap) throws ExpressionException;
}
