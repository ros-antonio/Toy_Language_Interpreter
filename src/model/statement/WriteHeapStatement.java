package model.statement;

import exceptions.StatementException;
import model.containers.IDictionary;
import model.containers.IHeap;
import model.expression.IExpression;
import model.state.ProgramState;
import model.type.IType;
import model.type.RefType;
import model.value.IValue;
import model.value.RefValue;

public record WriteHeapStatement(String varName, IExpression expression) implements IStatement {
    @Override
    public ProgramState execute(ProgramState state) {
        IDictionary<String, IValue> symTable = state.getSymTable();
        IHeap<IValue> heap = state.getHeap();

        if (!symTable.hasKey(varName)) {
            throw new StatementException("Variable " + varName + " does not exist");
        }
        IValue refValue = symTable.get(varName);
        if (!(refValue.getType() instanceof RefType refType)) {
            throw new StatementException("Variable " + varName + " is not a reference type");
        }
        RefValue ref = (RefValue) refValue;
        int address = ref.address();
        if (!heap.containsKey(address)) {
            throw new StatementException("Heap address " + address + " does not exist");
        }

        IValue value = expression.evaluate(symTable, heap);
        IType locationType = refType.getInner();

        if (!value.getType().equals(locationType)) {
            throw new StatementException("Type mismatch (value type != locationType)");
        }

        heap.update(address, value);
        return null;
    }

    @Override
    public IDictionary<String, IType> typecheck(IDictionary<String, IType> typeEnv) throws StatementException {
        if (!typeEnv.hasKey(varName)) {
            throw new StatementException("Variable " + varName + " does not exist in the type environment");
        }
        IType varType = typeEnv.get(varName);
        if (!(varType instanceof RefType refType)) {
            throw new StatementException("Variable " + varName + " is not of RefType");
        }
        IType exprType = expression.typecheck(typeEnv);
        if (!exprType.equals(refType.getInner())) {
            throw new StatementException("Type mismatch (expression type != locationType)");
        }
        return typeEnv;
    }

    @Override
    public String toString() {
        return "wH(" + varName + ", " + expression.toString() + ")";
    }
}
