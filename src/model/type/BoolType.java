package model.type;

import model.value.BooleanValue;
import model.value.IValue;

public class BoolType implements IType {
    @Override
    public boolean equals(Object another) {
        return another instanceof BoolType;
    }

    @Override
    public String toString() {
        return "bool";
    }

    @Override
    public IValue getDefaultValue() {
        return new BooleanValue(false);
    }
}