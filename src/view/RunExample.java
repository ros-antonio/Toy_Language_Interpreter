package view;

import controller.Controller;
import exceptions.ADTException;
import exceptions.ExpressionException;
import exceptions.RepositoryException;
import exceptions.StatementException;

public class RunExample extends Command {
    private final Controller controller;

    public RunExample(String key, String description, Controller controller) {
        super(key, description);
        this.controller = controller;
    }

    @Override
    public void execute() {
        try {
            controller.allSteps();
        } catch (RepositoryException | StatementException | ExpressionException | ADTException e) {
            System.out.println("\n--- ERROR ---");
            System.out.println(e.getMessage());
            System.out.println("-------------");
        }
        catch (Exception e) {
            System.out.println("\n--- UNEXPECTED ERROR ---");
            System.out.println(e.getMessage());
            e.printStackTrace();
            System.out.println("------------------------");
        }
    }
}