package model.value;

import model.type.Type;

public interface IValue {
    Type getType();

    // equals method is overwritten by the record class
}
