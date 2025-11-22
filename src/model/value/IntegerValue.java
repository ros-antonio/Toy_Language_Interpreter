package model.value;

import model.type.IntType;
import model.type.IType;

public record IntegerValue(int value) implements IValue {

    @Override
    public IType getType() {
        return new IntType();
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }
}
