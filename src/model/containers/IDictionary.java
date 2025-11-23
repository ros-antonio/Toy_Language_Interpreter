package model.containers;

import exceptions.ADTException;
import java.util.Map;

public interface IDictionary<TKey, TValue> {
    void insert(TKey key, TValue value);
    void remove(TKey key) throws ADTException;
    void update(TKey key, TValue value) throws ADTException;
    TValue get(TKey key) throws ADTException;
    boolean hasKey(TKey key);
    Map<TKey, TValue> getContent();
    String toString();
}