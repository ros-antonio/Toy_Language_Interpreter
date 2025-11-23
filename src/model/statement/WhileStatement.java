package model.statement;

import exceptions.ExpressionException;
import exceptions.StatementException;
import model.expression.IExpression;
import model.state.ProgramState;
import model.type.BoolType;
import model.value.BooleanValue;
import model.value.IValue;

public record WhileStatement(IExpression expression, IStatement statement) implements IStatement {
    @Override
    public ProgramState execute(ProgramState state) throws StatementException, ExpressionException {
        IValue value = expression.evaluate(state.getSymTable(), state.getHeap());
        if (!value.getType().equals(new BoolType()))
            throw new StatementException("The expression is not a boolean");

        BooleanValue boolValue = (BooleanValue) value;
        if (boolValue.value()) {
            state.getExeStack().push(this);
            state.getExeStack().push(statement);
        }

        return state;
    }

    @Override
    public String toString() {
        return "while(" + expression.toString() + ") " + statement.toString();
    }
}
