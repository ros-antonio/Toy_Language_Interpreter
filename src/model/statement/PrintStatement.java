package model.statement;

import model.expression.IExpression;
import model.state.ProgramState;

public record PrintStatement(IExpression expression) implements IStatement {

    @Override
    public ProgramState execute(ProgramState state) {
        state.getOut().add(expression.evaluate(state.getSymTable(), state.getHeap()));
        return state;
    }

    @Override
    public String toString() {
        return "print(" + expression.toString() + ")";
    }
}
