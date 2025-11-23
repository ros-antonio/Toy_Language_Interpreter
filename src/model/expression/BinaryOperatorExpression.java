package model.expression;

import model.containers.IDictionary;
import model.containers.IHeap;
import model.type.BoolType;
import model.type.IType;
import model.type.IntType;
import model.value.BooleanValue;
import model.value.IntegerValue;
import model.value.IValue;
import exceptions.ExpressionException;

public record BinaryOperatorExpression
        (String operator, IExpression left, IExpression right)
        implements IExpression {

    @Override
    public IValue evaluate(IDictionary<String, IValue> symTable, IHeap<IValue> heap) throws ExpressionException {
        var leftTerm = left.evaluate(symTable, heap);
        var rightTerm = right.evaluate(symTable, heap);

        switch (operator) {
            case "+", "-", "*", "/":
                checkTypes(leftTerm, rightTerm, new IntType());
                var leftValue = (IntegerValue) leftTerm;
                var rightValue = (IntegerValue) rightTerm;
                return evaluateArithmeticExpression(leftValue, rightValue);
            case "&&", "||":
                checkTypes(leftTerm, rightTerm, new BoolType());
                var leftValueB = (BooleanValue) leftTerm;
                var rightValueB = (BooleanValue) rightTerm;
                return evaluateBooleanExpression(leftValueB, rightValueB);
            case "<", "<=", "==", "!=", ">", ">=":
                checkTypes(leftTerm, rightTerm, new IntType());
                var leftValueC = (IntegerValue) leftTerm;
                var rightValueC = (IntegerValue) rightTerm;
                return evaluateRelationalExpression(leftValueC, rightValueC);
        }

        throw new ExpressionException("Unknown operator" + operator);
    }

    private void checkTypes(IValue leftTerm, IValue rightTerm, IType type) {
        if (!leftTerm.getType().equals(type) ||
                !rightTerm.getType().equals(type)) {
            throw new ExpressionException("Wrong types for operator " + operator);
        }
    }

    private IntegerValue evaluateArithmeticExpression(IntegerValue leftValue, IntegerValue rightValue) {
        return switch (operator) {
            case "+" -> new IntegerValue(leftValue.value() + rightValue.value());
            case "-" -> new IntegerValue(leftValue.value() - rightValue.value());
            case "*" -> new IntegerValue(leftValue.value() * rightValue.value());
            case "/" -> {
                if (rightValue.value() == 0) {
                    throw new ExpressionException("Division by zero");
                }
                yield new IntegerValue(leftValue.value() / rightValue.value());
            }
            default -> throw new IllegalStateException("Unreachable code");
        };
    }

    private BooleanValue evaluateBooleanExpression(BooleanValue leftValue, BooleanValue rightValue) {
        return switch (operator) {
            case "&&" -> new BooleanValue(leftValue.value() && rightValue.value());
            case "||" -> new BooleanValue(leftValue.value() || rightValue.value());
            default -> throw new IllegalStateException("Unreachable code");
        };
    }

    private IValue evaluateRelationalExpression(IntegerValue leftValueC, IntegerValue rightValueC) {
        return switch (operator) {
            case "<" -> new BooleanValue(leftValueC.value() < rightValueC.value());
            case "<=" -> new BooleanValue(leftValueC.value() <= rightValueC.value());
            case "==" -> new BooleanValue(leftValueC.value() == rightValueC.value());
            case "!=" -> new BooleanValue(leftValueC.value() != rightValueC.value());
            case ">" -> new BooleanValue(leftValueC.value() > rightValueC.value());
            case ">=" -> new BooleanValue(leftValueC.value() >= rightValueC.value());
            default -> throw new IllegalStateException("Unreachable code");
        };
    }

    @Override
    public String toString() {
        return "(" + left.toString() + " " + operator + " " + right.toString() + ")";
    }
}
