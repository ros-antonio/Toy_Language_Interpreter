package repository;

import exceptions.RepositoryException;
import model.state.ProgramState;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class Repository implements IRepository {
    private final List<ProgramState> programStates;
    private final String logFilePath;

    public Repository(ProgramState initialProgram, String logFilePath) {
        this.programStates = new ArrayList<>();
        this.programStates.add(initialProgram);
        this.logFilePath = logFilePath;
    }

    @Override
    public ProgramState getCrtPrg() {
        return this.programStates.getFirst();
    }

    @Override
    public void logPrgStateExec() throws RepositoryException {
        try {
            PrintWriter logFile = new PrintWriter(new BufferedWriter(new FileWriter(logFilePath, true)));
            ProgramState programState = getCrtPrg();
            logFile.println(programState.toString());
            logFile.println("----------------------------------------");
            logFile.close();
        } catch (IOException e) {
            throw new RepositoryException("Could not log program state: " + e.getMessage());
        }
    }
}