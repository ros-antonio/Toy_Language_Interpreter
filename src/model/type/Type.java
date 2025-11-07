package model.type;

import model.value.IntegerValue;
import model.value.BooleanValue;
import model.value.IValue;
import model.value.StringValue;

public enum Type {
    INTEGER,
    BOOLEAN,
    STRING;

    public IValue getDefaultValue() {
        return switch (this) {
            case INTEGER -> new IntegerValue(0);
            case BOOLEAN -> new BooleanValue(false);
            case STRING -> new StringValue("");
        };
    }
}
