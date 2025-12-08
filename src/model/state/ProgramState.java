package model.state;

import exceptions.ADTException;
import exceptions.StatementException;
import model.containers.IStack;
import model.containers.IList;
import model.containers.IDictionary;
import model.statement.IStatement;
import model.value.IValue;
import model.value.StringValue;
import model.containers.IHeap;

import java.io.BufferedReader;

public class ProgramState {
    private IStack<IStatement> exeStack;
    private IDictionary<String, IValue> symTable;
    private IList<IValue> out;
    private IDictionary<StringValue, BufferedReader> fileTable;
    private IHeap<IValue> heap;
    private final int id;
    private static int lastId = 0;

    public ProgramState(IStack<IStatement> exeStack, IDictionary<String, IValue> symTable,
                        IList<IValue> out, IDictionary<StringValue, BufferedReader> fileTable, IHeap<IValue> heap) {
        this.exeStack = exeStack;
        this.symTable = symTable;
        this.out = out;
        this.fileTable = fileTable;
        this.heap = heap;
        this.id = setId();
    }

    public synchronized int setId() {
        lastId++;
        return lastId;
    }

    public int getId() {
        return this.id;
    }

    public boolean isNotCompleted() {
        return !exeStack.isEmpty();
    }

    public ProgramState oneStep() throws StatementException, ADTException {
        if (exeStack.isEmpty()) {
            throw new StatementException("Program state stack is empty");
        }
        IStatement currentStatement = exeStack.pop();
        return currentStatement.execute(this);
    }

    public IHeap<IValue> getHeap() {
        return heap;
    }

    public void setHeap(IHeap<IValue> heap) {
        this.heap = heap;
    }

    public IDictionary<StringValue, BufferedReader> getFileTable() {
        return fileTable;
    }

    public void setFileTable(IDictionary<StringValue, BufferedReader> fileTable) {
        this.fileTable = fileTable;
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
        return "Id: " + id + "\n" +
                "ExeStack:\n" + exeStack.toString() +
                "\nSymTable:\n" + symTable.toString() +
                "\nOut:\n" + out.toString() +
                "\nFileTable:\n" + fileTable.toString() +
                "\nHeap:\n" + heap.toString();
    }
}