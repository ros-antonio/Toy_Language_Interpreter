package model.type;

import model.value.IntegerValue;
import model.value.IValue;

public class IntType implements IType {
    @Override
    public boolean equals(Object another) {
        return another instanceof IntType;
    }

    @Override
    public String toString() {
        return "int";
    }

    @Override
    public IValue getDefaultValue() {
        return new IntegerValue(0);
    }
}