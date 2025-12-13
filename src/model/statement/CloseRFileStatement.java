package model.statement;

import exceptions.StatementException;
import model.containers.IDictionary;
import model.expression.IExpression;
import model.state.ProgramState;
import model.type.IType;
import model.type.StringType;
import model.value.IValue;
import model.value.StringValue;

import java.io.BufferedReader;

public record CloseRFileStatement(IExpression expression) implements IStatement {

    @Override
    public ProgramState execute(ProgramState state) {
        IDictionary<StringValue, BufferedReader> fileTable = state.getFileTable();

        IValue value = expression.evaluate(state.getSymTable(), state.getHeap());
        if(!value.getType().equals(new StringType())) {
            throw new RuntimeException("File path expression '" + expression + "' is not a string type.");
        }

        StringValue filePath = (StringValue) value;
        if(!fileTable.hasKey(filePath)) {
            throw new StatementException("File '" + filePath + "' is not opened.");
        }

        BufferedReader reader = fileTable.get(filePath);
        try {
            reader.close();
            fileTable.remove(filePath);
        } catch (Exception e) {
            throw new StatementException("Could not close file '" + filePath + "': " + e.getMessage());
        }
        return null;
    }

    @Override
    public IDictionary<String, IType> typecheck(IDictionary<String, IType> typeEnv) throws StatementException {
        IType typeExpr = expression.typecheck(typeEnv);
        if (typeExpr.equals(new StringType())) {
            return typeEnv;
        } else {
            throw new StatementException("The file path expression of CLOSERFILE does not have the type String.");
        }
    }

    @Override
    public String toString() {
        return "closeRFile(" + expression.toString() + ")";
    }
}
