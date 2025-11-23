package model.expression;

import exceptions.ADTException;
import exceptions.ExpressionException;
import model.containers.IDictionary;
import model.containers.IHeap;
import model.value.IValue;
import model.value.RefValue;

public record ReadHeapExpression(IExpression expression) implements IExpression {

    @Override
    public IValue evaluate(IDictionary<String, IValue> symTable, IHeap<IValue> heap) throws ExpressionException {
        IValue value = expression.evaluate(symTable, heap);
        if (!(value instanceof RefValue))
            throw new ExpressionException("The expression is not a ref value");

        RefValue ref = (RefValue) value;
        int address = ref.address();
        if (!heap.containsKey(address))
            throw new ExpressionException("The address of the heap entry does not exist");

        try {
            return heap.get(address);
        } catch (ADTException e) {
            throw new ExpressionException(e.getMessage());
        }
    }

    @Override
    public String toString() {
        return "rH(" + expression.toString() + ")";
    }
}
