package model.expression;

import model.containers.IDictionary;
import model.type.Type;
import model.value.BooleanValue;
import model.value.IntegerValue;
import model.value.IValue;
import exceptions.ExpressionException;

public record BinaryOperatorExpression
        (String operator, IExpression left, IExpression right)
        implements IExpression {

    @Override
    public IValue evaluate(IDictionary<String, IValue> symTable) {
        var leftTerm = left.evaluate(symTable);
        var rightTerm = right.evaluate(symTable);

        switch (operator) {
            case "+", "-", "*", "/":
                checkTypes(leftTerm, rightTerm, Type.INTEGER);
                var leftValue = (IntegerValue) leftTerm;
                var rightValue = (IntegerValue) rightTerm;
                return evaluateArithmeticExpression(leftValue, rightValue);
            case "&&", "||":
                checkTypes(leftTerm, rightTerm, Type.BOOLEAN);
                var leftValueB = (BooleanValue) leftTerm;
                var rightValueB = (BooleanValue) rightTerm;
                return evaluateBooleanExpression(leftValueB, rightValueB);
        }

        throw new ExpressionException("Unknown operator" + operator);
    }

    private void checkTypes(IValue leftTerm, IValue rightTerm, Type type) {
        if (leftTerm.getType() != type ||
                rightTerm.getType() != type) {
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

    @Override
    public String toString() {
        return "(" + left.toString() + " " + operator + " " + right.toString() + ")";
    }
}
