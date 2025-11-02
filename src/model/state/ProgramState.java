package model.state;

import model.containers.IStack;
import model.containers.IList;
import model.containers.IDictionary;
import model.statement.IStatement;
import model.value.IValue;

public class ProgramState {
    private IStack<IStatement> exeStack;
    private IDictionary<String, IValue> symTable;
    private IList<IValue> out;

    public ProgramState(IStack<IStatement> exeStack, IDictionary<String, IValue> symTable, IList<IValue> out) {
        this.exeStack = exeStack;
        this.symTable = symTable;
        this.out = out;
    }

    public IStack<IStatement> getExeStack() {
        return exeStack;
    }

    public void setExeStack(IStack<IStatement> exeStack) {
        this.exeStack = exeStack;
    }

    public IDictionary<String, IValue> getSymTable() {
        return symTable;
    }

    public void setSymTable(IDictionary<String, IValue> symTable) {
        this.symTable = symTable;
    }

    public IList<IValue> getOut() {
        return out;
    }

    public void setOut(IList<IValue> out) {
        this.out = out;
    }

    @Override
    public String toString() {
        return "ExeStack:\n" + exeStack.toString() + "\nSymTable:\n" + symTable.toString() + "\nOut:\n" + out.toString();
    }
}