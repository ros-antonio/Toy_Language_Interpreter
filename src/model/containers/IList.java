package model.containers;

import exceptions.ADTException;

public interface IList<T> {
    void add(T val);
    T popFront() throws ADTException;
    T front() throws ADTException;
    boolean isEmpty();
    String toString();
}