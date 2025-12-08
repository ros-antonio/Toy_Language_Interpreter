package model.containers;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import exceptions.ADTException;

public class GenericHeap<TValue> implements IHeap<TValue> {
    private Map<Integer, TValue> map;
    private final AtomicInteger freeLocation;

    public GenericHeap() {
        this.map = new ConcurrentHashMap<>();
        this.freeLocation = new AtomicInteger(1);
    }

    @Override
    public int allocate(TValue value) {
        int newAddress = freeLocation.getAndIncrement();
        map.put(newAddress, value);
        return newAddress;
    }

    @Override
    public TValue get(int address) throws ADTException {
        if (!map.containsKey(address))
            throw new ADTException("Heap address " + address + " not found.");
        return map.get(address);
    }

    @Override
    public void put(int address, TValue value) {
        map.put(address, value);
    }

    @Override
    public void update(int address, TValue value) throws ADTException {
        if (!map.containsKey(address))
            throw new ADTException("Heap address " + address + " not found.");
        map.put(address, value);
    }

    @Override
    public boolean containsKey(int address) {
        return map.containsKey(address);
    }

    @Override
    public void setContent(Map<Integer, TValue> content) {
        this.map = new ConcurrentHashMap<>(content);
    }

    @Override
    public Map<Integer, TValue> getContent() {
        return map;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (Map.Entry<Integer, TValue> entry : map.entrySet()) {
            builder.append(entry.getKey())
                    .append(" --> ")
                    .append(entry.getValue())
                    .append("\n");
        }
        return builder.toString();
    }
}