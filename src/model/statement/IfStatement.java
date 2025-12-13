package model.statement;

import model.containers.IDictionary;
import model.expression.IExpression;
import model.state.ProgramState;
import model.type.BoolType;
import model.type.IType;
import model.value.BooleanValue;
import model.value.IValue;
import exceptions.StatementException;

public record IfStatement(IExpression condition, IStatement thenBranch, IStatement elseBranch) implements IStatement {

    @Override
    public ProgramState execute(ProgramState state) {
        IValue result = condition.evaluate(state.getSymTable(), state.getHeap());
        if (result instanceof BooleanValue booleanValue) {
            if (booleanValue.value()) {
                state.getExeStack().push(thenBranch);
            } else {
                state.getExeStack().push(elseBranch);
            }
        } else {
            throw new StatementException("Condition expression does not evaluate to a boolean.");
        }
        return null;
    }

    @Override
    public IDictionary<String, IType> typecheck(IDictionary<String, IType> typeEnv) throws StatementException {
        IType conditionType = condition.typecheck(typeEnv);
        if (!conditionType.equals(new BoolType())) {
            throw new StatementException("The condition of IF does not have the type Bool.");
        }
        thenBranch.typecheck(typeEnv.deepCopy());
        elseBranch.typecheck(typeEnv.deepCopy());
        return typeEnv;
    }

    @Override
    public String toString() {
        return "(if (" + condition.toString() + ") then (" + thenBranch.toString() + ") else (" + elseBranch.toString() + "))";
    }
}
