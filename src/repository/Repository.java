package repository;

import model.state.ProgramState;
import java.util.ArrayList;
import java.util.List;

public class Repository implements IRepository {
    private final List<ProgramState> programStates;

    public Repository(ProgramState initialProgram) {
        this.programStates = new ArrayList<>();
        this.programStates.add(initialProgram);
    }

    @Override
    public ProgramState getCrtPrg() {
        return this.programStates.get(0);
    }
}