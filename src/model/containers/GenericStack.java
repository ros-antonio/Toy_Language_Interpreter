package model.containers;
import java.util.Stack;

import exceptions.ADTException;

public class GenericStack<T> implements IStack<T> {
    Stack<T> stack;

    public GenericStack() {
        stack = new Stack<>();
    }

    @Override
    public void push(T val) {
        stack.push(val);
    }

    @Override
    public T pop() throws ADTException {
        if(stack.isEmpty()) throw new ADTException("Stack is empty");
        return stack.pop();
    }

    @Override
    public T top() throws ADTException {
        if(stack.isEmpty()) throw new ADTException("Stack is empty");
        return stack.peek();
    }

    @Override
    public boolean isEmpty() {
        return stack.isEmpty();
    }

    @Override
    public String toString() {
        return stack.toString();
    }
}