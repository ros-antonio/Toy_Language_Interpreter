package model.type;

import model.value.IntegerValue;
import model.value.BooleanValue;
import model.value.IValue;

public enum Type {
    INTEGER,
    BOOLEAN;

    public IValue getDefaultValue() {
        switch (this) {
            case INTEGER:
                return new IntegerValue(0);
            case BOOLEAN:
                return new BooleanValue(false);
        }
        return null;
    }
}
