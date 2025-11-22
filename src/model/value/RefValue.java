package model.value;

import model.type.RefType;
import model.type.IType;

public record RefValue(int address, IType locationType) implements IValue {

    @Override
    public IType getType() {
        return new RefType(locationType);
    }

    @Override
    public String toString() {
        return "(" + address + ", " + locationType.toString() + ")";
    }
}