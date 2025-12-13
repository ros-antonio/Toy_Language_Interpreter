package model.statement;

import exceptions.ExpressionException;
import exceptions.StatementException;
import model.containers.IDictionary;
import model.expression.IExpression;
import model.state.ProgramState;
import model.type.StringType;
import model.type.IType;
import model.value.IValue;
import model.value.StringValue;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public record OpenRFileStatement(IExpression expression) implements IStatement {

    @Override
    public ProgramState execute(ProgramState state) throws StatementException, ExpressionException {
        IDictionary<StringValue, BufferedReader> fileTable = state.getFileTable();
        IValue value = expression.evaluate(state.getSymTable(), state.getHeap());

        if (!value.getType().equals(new StringType())) {
            throw new StatementException("File path expression '" + expression + "' is not a string type.");
        }

        StringValue filePath = (StringValue) value;

        if (fileTable.hasKey(filePath)) {
            throw new StatementException("File '" + filePath + "' is already open.");
        }

        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath.value()));
            fileTable.insert(filePath, bufferedReader);
        } catch (IOException e) {
            throw new StatementException("Failed to open file '" + filePath.value() + "': " + e.getMessage());
        }

        return null;
    }

    @Override
    public IDictionary<String, IType> typecheck(IDictionary<String, IType> typeEnv) throws StatementException {
        IType typeExpr = expression.typecheck(typeEnv);
        if (typeExpr.equals(new StringType())) {
            return typeEnv;
        } else {
            throw new StatementException("The file path expression of OPENRFILE does not have the type String.");
        }
    }

    @Override
    public String toString() {
        return "openRFile(" + expression.toString() + ")";
    }
}