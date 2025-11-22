package model.containers;

import exceptions.ADTException;
import java.util.Map;

public interface IHeap<TValue> {
    int allocate(TValue value);
    TValue get(int address) throws ADTException;
    void put(int address, TValue value);
    void update(int address, TValue value) throws ADTException;
    boolean containsKey(int address);
    void setContent(Map<Integer, TValue> content);
    Map<Integer, TValue> getContent();
    String toString();
}