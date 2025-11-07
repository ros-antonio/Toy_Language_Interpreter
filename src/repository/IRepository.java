package repository;

import exceptions.RepositoryException;
import model.state.ProgramState;

public interface IRepository {
    ProgramState getCrtPrg();
    void logPrgStateExec() throws RepositoryException;
}