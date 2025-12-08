package model.containers;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import exceptions.ADTException;

public class GenericDictionary<TKey, TValue> implements IDictionary<TKey, TValue> {
    private final Map<TKey, TValue> map;

    public GenericDictionary() {
        map = new ConcurrentHashMap<>();
    }

    @Override
    public void insert(TKey key, TValue value) {
        map.put(key, value);
    }

    @Override
    public void remove(TKey key) throws ADTException {
        if (!map.containsKey(key)) throw new ADTException("Key not found: " + key);
        map.remove(key);
    }

    @Override
    public void update(TKey key, TValue value) throws ADTException {
        if (!map.containsKey(key)) throw new ADTException("Key not found: " + key);
        map.put(key, value);
    }

    @Override
    public TValue get(TKey key) throws ADTException {
        if (!map.containsKey(key)) throw new ADTException("Key not found: " + key);
        return map.get(key);
    }

    @Override
    public boolean hasKey(TKey key) {
        return map.containsKey(key);
    }

    @Override
    public Map<TKey, TValue> getContent() {
        return map;
    }

    @Override
    public IDictionary<TKey, TValue> deepCopy() {
        GenericDictionary<TKey, TValue> newDict = new GenericDictionary<>();
        for (Map.Entry<TKey, TValue> entry : map.entrySet()) {
            newDict.insert(entry.getKey(), entry.getValue());
        }
        return newDict;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (Map.Entry<TKey, TValue> entry : map.entrySet()) {
            builder.append(entry.getKey().toString())
                    .append(" --> ")
                    .append(entry.getValue().toString())
                    .append("\n");
        }
        return builder.toString();
    }
}