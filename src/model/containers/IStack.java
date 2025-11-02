package model.containers;

import exceptions.ADTException;

public interface IStack<T> {
    void push(T val);
    T pop() throws ADTException;
    T top() throws ADTException;
    boolean isEmpty();
    String toString();
}