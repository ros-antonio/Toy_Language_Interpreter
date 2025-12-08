package model.containers;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import exceptions.ADTException;

public class GenericList<T> implements IList<T> {
    private final List<T> list;

    public GenericList() {
        list = Collections.synchronizedList(new LinkedList<>());
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
        StringBuilder builder = new StringBuilder();
        for(T element : list) {
            builder.append(element.toString()).append("\n");
        }
        return builder.toString();
    }
}