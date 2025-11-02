package model.containers;

import java.util.LinkedList;
import exceptions.ADTException;

public class GenericList<T> implements IList<T> {
    LinkedList<T> list;

    public GenericList() {
        list = new LinkedList<>();
    }

    @Override
    public void add(T val) {
        list.add(val);
    }

    @Override
    public T popFront() throws ADTException {
        if(list.isEmpty()) throw new ADTException("List is empty");
        return list.removeFirst();
    }

    @Override
    public T front() throws ADTException {
        if(list.isEmpty()) throw new ADTException("List is empty");
        return list.getFirst();
    }

    @Override
    public boolean isEmpty() {
        return list.isEmpty();
    }

    @Override
    public String toString() {
        return list.toString();
    }
}