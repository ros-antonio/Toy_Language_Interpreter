package model.statement;

import exceptions.StatementException;
import model.containers.IDictionary;
import model.expression.IExpression;
import model.state.ProgramState;
import model.type.IntType;
import model.type.IType;
import model.value.IValue;
import model.value.IntegerValue;
import model.value.StringValue;
import java.io.BufferedReader;
import java.util.Objects;

public record ReadFileStatement(IExpression expression, String varName) implements IStatement {
    @Override
    public ProgramState execute(ProgramState state) {
        IDictionary<String, IValue> symTable = state.getSymTable();
        IDictionary<StringValue, BufferedReader> fileTable = state.getFileTable();
        if (!symTable.hasKey(varName)) {
            throw new StatementException("Variable not defined: " + varName);
        }

        if (!symTable.get(varName).getType().equals(new IntType())) {
            throw new StatementException("Variable '" + varName + "' is not of type Integer.");
        }

        IValue value = expression.evaluate(state.getSymTable(), state.getHeap());
        if (!value.getType().equals(new IntType())) {
            throw new StatementException("File path expression '" + expression + "' is not a string type.");
        }

        StringValue filePath = (StringValue) value;
        if (!fileTable.hasKey(filePath)) {
            throw new StatementException("File '" + filePath + "' is not opened.");
        }
        BufferedReader bufferedReader = fileTable.get(filePath);
        try {
            String line = bufferedReader.readLine();
            int intValue;
            if (line == null) {
                intValue = 0;
            } else {
                try {
                    intValue = Integer.parseInt(line);
                } catch (NumberFormatException e) {
                    throw new StatementException("The line read from file '" + filePath + "' is not a valid integer.");
                }
            }
            symTable.update(varName, new IntegerValue(intValue));
        } catch (Exception e) {
            throw new StatementException("Error reading from file '" + filePath + "': " + e.getMessage());
        }
        return null;
    }

    @Override
    public String toString() {
        return "readFile(" + expression.toString() + ", " + varName + ")";
    }
}
