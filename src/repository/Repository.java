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
    private List<ProgramState> programStates;
    private final String logFilePath;

    public Repository(ProgramState initialProgram, String logFilePath) {
        this.programStates = new ArrayList<>();
        this.programStates.add(initialProgram);
        this.logFilePath = logFilePath;
    }

    @Override
    public List<ProgramState> getPrgList() {
        return this.programStates;
    }

    @Override
    public void setPrgList(List<ProgramState> programStates) {
        this.programStates = programStates;
    }

    @Override
    public void logPrgStateExec(ProgramState programState) throws RepositoryException {
        try {
            PrintWriter logFile = new PrintWriter(new BufferedWriter(new FileWriter(logFilePath, true)));
            logFile.println(programState.toString());
            logFile.println("----------------------------------------");
            logFile.close();
        } catch (IOException e) {
            throw new RepositoryException("Could not log program state: " + e.getMessage());
        }
    }
}