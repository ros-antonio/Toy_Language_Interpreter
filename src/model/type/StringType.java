package model.type;

import model.value.StringValue;
import model.value.IValue;

public class StringType implements IType {
    @Override
    public boolean equals(Object another) {
        return another instanceof StringType;
    }

    @Override
    public String toString() {
        return "string";
    }

    @Override
    public IValue getDefaultValue() {
        return new StringValue("");
    }
}