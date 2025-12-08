package repository;

import exceptions.RepositoryException;
import model.state.ProgramState;

import java.util.List;

public interface IRepository {
    List<ProgramState> getPrgList();
    void setPrgList(List<ProgramState> programStates);
    void logPrgStateExec(ProgramState programState) throws RepositoryException;
}