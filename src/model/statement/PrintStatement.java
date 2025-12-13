package model.statement;

import exceptions.StatementException;
import model.containers.IDictionary;
import model.expression.IExpression;
import model.state.ProgramState;
import model.type.IType;

public record PrintStatement(IExpression expression) implements IStatement {

    @Override
    public ProgramState execute(ProgramState state) {
        state.getOut().add(expression.evaluate(state.getSymTable(), state.getHeap()));
        return null;
    }

    @Override
    public IDictionary<String, IType> typecheck(IDictionary<String, IType> typeEnv) throws StatementException {
        expression.typecheck(typeEnv);
        return typeEnv;
    }

    @Override
    public String toString() {
        return "print(" + expression.toString() + ")";
    }
}
