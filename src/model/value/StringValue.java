package model.value;

import model.type.Type;

public record StringValue(String value) implements IValue {

    @Override
    public Type getType() {
        return Type.STRING;
    }

    @Override
    public String toString() {
        return "\"" + value + "\"";
    }
}
