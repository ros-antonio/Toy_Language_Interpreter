package model.statement;

import model.expression.IExpression;
import model.state.ProgramState;
import model.value.BooleanValue;
import model.value.IValue;
import exceptions.StatementException;

public record IfStatement(IExpression condition, IStatement thenBranch, IStatement elseBranch) implements IStatement {

    @Override
    public ProgramState execute(ProgramState state) {
        IValue result = condition.evaluate(state.getSymTable());
        if (result instanceof BooleanValue booleanValue) {
            if (booleanValue.value()) {
                state.getExeStack().push(thenBranch);
            } else {
                state.getExeStack().push(elseBranch);
            }
        } else {
            throw new StatementException("Condition expression does not evaluate to a boolean.");
        }
        return state;
    }
}
