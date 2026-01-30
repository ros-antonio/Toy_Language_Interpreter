package model.containers;

import java.util.LinkedList;
import java.util.List;
import exceptions.ADTException;

public class GenericList<T> implements IList<T> {
    private final List<T> list;

    public GenericList() {
        list = new LinkedList<>();
    }

    @Override
    public synchronized void add(T val) {
        list.add(val);
    }

    @Override
    public synchronized T popFront() throws ADTException {
        if (list.isEmpty()) {
            throw new ADTException("List is empty");
        }
        return list.removeFirst();
    }

    @Override
    public synchronized T front() throws ADTException {
        if (list.isEmpty()) {
            throw new ADTException("List is empty");
        }
        return list.getFirst();
    }

    @Override
    public synchronized boolean isEmpty() {
        return list.isEmpty();
    }

    @Override
    public synchronized String toString() {
        StringBuilder builder = new StringBuilder();
        for (T element : list) {
            builder.append(element.toString()).append("\n");
        }
        return builder.toString();
    }
}