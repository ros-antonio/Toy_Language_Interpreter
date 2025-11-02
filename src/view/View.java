package view;

import controller.Controller;
import exceptions.ADTException;
import exceptions.ExpressionException;
import exceptions.StatementException;
import model.containers.*;
import model.expression.*;
import model.statement.*;
import model.state.ProgramState;
import model.type.Type;
import model.value.BooleanValue;
import model.value.IntegerValue;
import model.value.IValue;
import repository.IRepository;
import repository.Repository;

import java.util.Scanner;

public class View {

    public void run() {
        while (true) {
            printMenu();
            Scanner scanner = new Scanner(System.in);
            System.out.print("Enter your option: ");
            String option = scanner.nextLine();

            switch (option) {
                case "0":
                    System.out.println("Exiting...");
                    return;
                case "1":
                    runExample(buildExample1());
                    break;
                case "2":
                    runExample(buildExample2());
                    break;
                case "3":
                    runExample(buildExample3());
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
                    break;
            }
        }
    }

    private void runExample(IStatement example) {
        try {
            Scanner scanner = new Scanner(System.in);
            System.out.print("Display execution steps? (y/n) [default: n]: ");
            String displayChoice = scanner.nextLine().trim().toLowerCase();
            boolean displayFlag = displayChoice.equals("y");
            System.out.println();

            IStack<IStatement> exeStack = new GenericStack<>();
            IDictionary<String, IValue> symTable = new GenericDictionary<>();
            IList<IValue> out = new GenericList<>();

            ProgramState programState = new ProgramState(exeStack, symTable, out);
            exeStack.push(example);

            IRepository repository = new Repository(programState);
            Controller controller = new Controller(repository, displayFlag);

            System.out.println("Running program...");
            controller.allSteps();

            if (!displayFlag) {
                System.out.println("Program finished. Final Output:");
                System.out.println(out);
            } else {
                System.out.println("Program finished.");
            }

        } catch (StatementException | ExpressionException | ADTException e) {
            System.out.println("\n--- ERROR ---");
            System.out.println(e.getMessage());
            System.out.println("-------------");
        } catch (Exception e) {
            System.out.println("\n--- UNEXPECTED ERROR ---");
            System.out.println(e.getMessage());
            e.printStackTrace();
            System.out.println("------------------------");
        }
        System.out.println("\n");
    }

    private void printMenu() {
        System.out.println("--- Toy Language Interpreter Menu ---");
        System.out.println("0. Exit");
        System.out.println("1. Run Example 1: int v; v=2; Print(v)");
        System.out.println("2. Run Example 2: int a; int b; a=2+3*5; b=a-4/2+7; Print(b)");
        System.out.println("3. Run Example 3: bool a; a=false; int v; If a Then v=2 Else v=3; Print(v)");
    }

    private IStatement buildExample1() {
        return new CompoundStatement(
                new VariableDeclarationStatement(Type.INTEGER, "v"),
                new CompoundStatement(
                        new AssignmentStatement(
                                new ConstantExpression(new IntegerValue(2)), "v"
                        ),
                        new PrintStatement(new VariableExpression("v"))
                )
        );
    }

    private IStatement buildExample2() {
        return new CompoundStatement(
                new VariableDeclarationStatement(Type.INTEGER, "a"),
                new CompoundStatement(
                        new VariableDeclarationStatement(Type.INTEGER, "b"),
                        new CompoundStatement(
                                new AssignmentStatement(
                                        new BinaryOperatorExpression("+",
                                                new ConstantExpression(new IntegerValue(2)),
                                                new BinaryOperatorExpression("*",
                                                        new ConstantExpression(new IntegerValue(3)),
                                                        new ConstantExpression(new IntegerValue(5))
                                                )
                                        ), "a"
                                ),
                                new CompoundStatement(
                                        new AssignmentStatement(
                                                new BinaryOperatorExpression("+",
                                                        new BinaryOperatorExpression("-",
                                                                new VariableExpression("a"),
                                                                new BinaryOperatorExpression("/",
                                                                        new ConstantExpression(new IntegerValue(4)),
                                                                        new ConstantExpression(new IntegerValue(2))
                                                                )
                                                        ),
                                                        new ConstantExpression(new IntegerValue(7))
                                                ), "b"
                                        ),
                                        new PrintStatement(new VariableExpression("b"))
                                )
                        )
                )
        );
    }

    private IStatement buildExample3() {
        return new CompoundStatement(
                new VariableDeclarationStatement(Type.BOOLEAN, "a"),
                new CompoundStatement(
                        new AssignmentStatement(
                                new ConstantExpression(new BooleanValue(false)), "a"
                        ),
                        new CompoundStatement(
                                new VariableDeclarationStatement(Type.INTEGER, "v"),
                                new CompoundStatement(
                                        new IfStatement(
                                                new VariableExpression("a"),
                                                new AssignmentStatement(new ConstantExpression(new IntegerValue(2)), "v"),
                                                new AssignmentStatement(new ConstantExpression(new IntegerValue(3)), "v")
                                        ),
                                        new PrintStatement(new VariableExpression("v"))
                                )
                        )
                )
        );
    }
}