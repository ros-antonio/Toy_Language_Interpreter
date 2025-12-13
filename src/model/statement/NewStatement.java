package model.statement;

import exceptions.ExpressionException;
import exceptions.StatementException;
import model.containers.IDictionary;
import model.containers.IHeap;
import model.expression.IExpression;
import model.state.ProgramState;
import model.type.IType;
import model.type.RefType;
import model.value.IValue;
import model.value.RefValue;

public record NewStatement(String varName, IExpression expression) implements IStatement {
    @Override
    public ProgramState execute(ProgramState state) throws StatementException, ExpressionException {
        IDictionary<String, IValue> symTable = state.getSymTable();
        IHeap<IValue> heap = state.getHeap();

        if (!symTable.hasKey(varName)) {
            throw new StatementException("Variable " + varName + " not found");
        }
        IValue varValue = symTable.get(varName);
        if (!(varValue.getType() instanceof RefType)) {
            throw new StatementException("Variable " + varName + " not of type RefType");
        }

        IValue eval = expression.evaluate(symTable, heap);
        RefType refType = (RefType) varValue.getType();
        IType locationType = refType.getInner();
        if (!eval.getType().equals(locationType)) {
            throw new StatementException("Variable " + varName + " is not of type " + locationType);
        }

        int newAddress = heap.allocate(eval);
        symTable.update(varName, new RefValue(newAddress, locationType));

        return null;
    }

    @Override
    public IDictionary<String, IType> typecheck(IDictionary<String, IType> typeEnv) throws StatementException {
        if (!typeEnv.hasKey(varName)) {
            throw new StatementException("Variable " + varName + " not found in type environment");
        }
        IType varType = typeEnv.get(varName);
        IType exprType = expression.typecheck(typeEnv);

        if(varType.equals(new RefType(exprType)))
            return typeEnv;
        else
            throw new StatementException("NEW statement: right hand side and left hand side have different types");
    }

    @Override
    public String toString() {
        return "new(" + varName + ", " + expression.toString() + ")";
    }
}
