package model.value;

import model.type.BoolType;
import model.type.IType;

public record BooleanValue(boolean value) implements IValue {

    @Override
    public IType getType() {
        return new BoolType();
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }
}
