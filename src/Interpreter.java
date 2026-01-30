/*
import controller.Controller;
import exceptions.ExpressionException;
import exceptions.RepositoryException;
import exceptions.StatementException;
import model.containers.*;
import model.expression.*;
import model.statement.*;
import model.state.ProgramState;
import model.type.*;
import model.value.*;
import repository.IRepository;
import repository.Repository;
import view.ExitCommand;
import view.RunExample;
import view.TextMenu;

import java.io.BufferedReader;

public class Interpreter {
    public static void main(String[] args) {
        TextMenu menu = new TextMenu();
        menu.addCommand(new ExitCommand("0", "Exit"));

        IStatement ex1 = buildExample1();
        try {
            ex1.typecheck(new GenericDictionary<String, IType>());
            IStack<IStatement> exeStack1 = new GenericStack<>();
            IDictionary<String, IValue> symTable1 = new GenericDictionary<>();
            IList<IValue> out1 = new GenericList<>();
            IDictionary<StringValue, BufferedReader> fileTable1 = new GenericDictionary<>();
            IHeap<IValue> heap1 = new GenericHeap<>();
            ProgramState prg1 = new ProgramState(exeStack1, symTable1, out1, fileTable1, heap1);
            exeStack1.push(ex1);
            IRepository repo1 = new Repository(prg1, "log1.txt");
            Controller ctr1 = new Controller(repo1);
            menu.addCommand(new RunExample("1", ex1.toString(), ctr1));
        } catch (Exception e) {
            System.out.println("Error during type checking or program setup (1): " + e.getMessage());
        }

        IStatement ex2 = buildExample2();
        try {
            ex2.typecheck(new GenericDictionary<String, IType>());
            IStack<IStatement> exeStack2 = new GenericStack<>();
            IDictionary<String, IValue> symTable2 = new GenericDictionary<>();
            IList<IValue> out2 = new GenericList<>();
            IDictionary<StringValue, BufferedReader> fileTable2 = new GenericDictionary<>();
            IHeap<IValue> heap2 = new GenericHeap<>();
            ProgramState prg2 = new ProgramState(exeStack2, symTable2, out2, fileTable2, heap2);
            exeStack2.push(ex2);
            IRepository repo2 = new Repository(prg2, "log2.txt");
            Controller ctr2 = new Controller(repo2);
            menu.addCommand(new RunExample("2", ex2.toString(), ctr2));
        } catch (Exception e) {
            System.out.println("Error during type checking or program setup (2): " + e.getMessage());
        }

        IStatement ex3 = buildExample3();
        try {
            ex3.typecheck(new GenericDictionary<String, IType>());
            IStack<IStatement> exeStack3 = new GenericStack<>();
            IDictionary<String, IValue> symTable3 = new GenericDictionary<>();
            IList<IValue> out3 = new GenericList<>();
            IDictionary<StringValue, BufferedReader> fileTable3 = new GenericDictionary<>();
            IHeap<IValue> heap3 = new GenericHeap<>();
            ProgramState prg3 = new ProgramState(exeStack3, symTable3, out3, fileTable3, heap3);
            exeStack3.push(ex3);
            IRepository repo3 = new Repository(prg3, "log3.txt");
            Controller ctr3 = new Controller(repo3);
            menu.addCommand(new RunExample("3", ex3.toString(), ctr3));
        } catch (Exception e) {
            System.out.println("Error during type checking or program setup (3): " + e.getMessage());
        }

        IStatement ex4 = buildExample4();
        try {
            ex4.typecheck(new GenericDictionary<String, IType>());
            IStack<IStatement> exeStack4 = new GenericStack<>();
            IDictionary<String, IValue> symTable4 = new GenericDictionary<>();
            IList<IValue> out4 = new GenericList<>();
            IDictionary<StringValue, BufferedReader> fileTable4 = new GenericDictionary<>();
            IHeap<IValue> heap4 = new GenericHeap<>();
            ProgramState prg4 = new ProgramState(exeStack4, symTable4, out4, fileTable4, heap4);
            exeStack4.push(ex4);
            IRepository repo4 = new Repository(prg4, "log4.txt");
            Controller ctr4 = new Controller(repo4);
            menu.addCommand(new RunExample("4", ex4.toString(), ctr4));
        } catch (Exception e) {
            System.out.println("Error during type checking or program setup (4): " + e.getMessage());
        }

        IStatement ex5 = buildExample5();
        try {
            ex5.typecheck(new GenericDictionary<String, IType>());
            IStack<IStatement> exeStack5 = new GenericStack<>();
            IDictionary<String, IValue> symTable5 = new GenericDictionary<>();
            IList<IValue> out5 = new GenericList<>();
            IDictionary<StringValue, BufferedReader> fileTable5 = new GenericDictionary<>();
            IHeap<IValue> heap5 = new GenericHeap<>();
            ProgramState prg5 = new ProgramState(exeStack5, symTable5, out5, fileTable5, heap5);
            exeStack5.push(ex5);
            IRepository repo5 = new Repository(prg5, "log5.txt");
            Controller ctr5 = new Controller(repo5);
            menu.addCommand(new RunExample("5", ex5.toString(), ctr5));
        } catch (Exception e) {
            System.out.println("Error during type checking or program setup (5): " + e.getMessage());
        }

        IStatement ex6 = buildExample6();
        try {
            ex6.typecheck(new GenericDictionary<String, IType>());
            IStack<IStatement> exeStack6 = new GenericStack<>();
            IDictionary<String, IValue> symTable6 = new GenericDictionary<>();
            IList<IValue> out6 = new GenericList<>();
            IDictionary<StringValue, BufferedReader> fileTable6 = new GenericDictionary<>();
            IHeap<IValue> heap6 = new GenericHeap<>();
            ProgramState prg6 = new ProgramState(exeStack6, symTable6, out6, fileTable6, heap6);
            exeStack6.push(ex6);
            IRepository repo6 = new Repository(prg6, "log6.txt");
            Controller ctr6 = new Controller(repo6);
            menu.addCommand(new RunExample("6", ex6.toString(), ctr6));
        } catch (Exception e) {
            System.out.println("Error during type checking or program setup (6): " + e.getMessage());
        }

        IStatement ex7 = buildExample7();
        try {
            ex7.typecheck(new GenericDictionary<String, IType>());
            IStack<IStatement> exeStack7 = new GenericStack<>();
            IDictionary<String, IValue> symTable7 = new GenericDictionary<>();
            IList<IValue> out7 = new GenericList<>();
            IDictionary<StringValue, BufferedReader> fileTable7 = new GenericDictionary<>();
            IHeap<IValue> heap7 = new GenericHeap<>();
            ProgramState prg7 = new ProgramState(exeStack7, symTable7, out7, fileTable7, heap7);
            exeStack7.push(ex7);
            IRepository repo7 = new Repository(prg7, "log7.txt");
            Controller ctr7 = new Controller(repo7);
            menu.addCommand(new RunExample("7", ex7.toString(), ctr7));
        } catch (Exception e) {
            System.out.println("Error during type checking or program setup (7): " + e.getMessage());
        }

        menu.show();
    }

    private static IStatement buildExample1() {
        return new CompoundStatement(
                new VariableDeclarationStatement(new IntType(), "v"),
                new CompoundStatement(
                        new AssignmentStatement(
                                new ConstantExpression(new IntegerValue(2)), "v"
                        ),
                        new PrintStatement(new VariableExpression("v"))
                )
        );
    }

    private static IStatement buildExample2() {
        return new CompoundStatement(
                new VariableDeclarationStatement(new IntType(), "a"),
                new CompoundStatement(
                        new VariableDeclarationStatement(new IntType(), "b"),
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

    private static IStatement buildExample3() {
        return new CompoundStatement(
                new VariableDeclarationStatement(new BoolType(), "a"),
                new CompoundStatement(
                        new AssignmentStatement(
                                new ConstantExpression(new BooleanValue(false)), "a"
                        ),
                        new CompoundStatement(
                                new VariableDeclarationStatement(new IntType(), "v"),
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

    private static IStatement buildExample4() {
        return new CompoundStatement(
                new VariableDeclarationStatement(new StringType(), "varf"),
                new CompoundStatement(
                        new AssignmentStatement(new ConstantExpression(new StringValue("test.in")), "varf"),
                        new CompoundStatement(
                                new OpenRFileStatement(new VariableExpression("varf")),
                                new CompoundStatement(
                                        new VariableDeclarationStatement(new IntType(), "varc"),
                                        new CompoundStatement(
                                                new ReadFileStatement(new VariableExpression("varf"), "varc"),
                                                new CompoundStatement(
                                                        new PrintStatement(new VariableExpression("varc")),
                                                        new CompoundStatement(
                                                                new ReadFileStatement(new VariableExpression("varf"), "varc"),
                                                                new CompoundStatement(
                                                                        new PrintStatement(new VariableExpression("varc")),
                                                                        new CloseRFileStatement(new VariableExpression("varf"))
                                                                )
                                                        )
                                                )
                                        )
                                )
                        )
                )
        );
    }

    private static IStatement buildExample5() {
        return new CompoundStatement(
                new VariableDeclarationStatement(new RefType(new IntType()), "v"),
                new CompoundStatement(
                        new NewStatement("v", new ConstantExpression(new IntegerValue(20))),
                        new CompoundStatement(
                                new VariableDeclarationStatement(new RefType(new RefType(new IntType())), "a"),
                                new CompoundStatement(
                                        new NewStatement("a", new VariableExpression("v")),
                                        new CompoundStatement(
                                                new NewStatement("v", new ConstantExpression(new IntegerValue(30))),
                                                new PrintStatement(
                                                        new ReadHeapExpression(
                                                                new ReadHeapExpression(new VariableExpression("a"))
                                                        )
                                                )
                                        )
                                )
                        )
                )
        );
    }

    private static IStatement buildExample6() {
        return new CompoundStatement(
                new VariableDeclarationStatement(new IntType(), "v"),
                new CompoundStatement(
                        new AssignmentStatement(
                                new ConstantExpression(new IntegerValue(4)), "v"
                        ),
                        new CompoundStatement(
                                new WhileStatement(
                                        new BinaryOperatorExpression(">", new VariableExpression("v"), new ConstantExpression(new IntegerValue(0))),
                                        new CompoundStatement(
                                                new PrintStatement(new VariableExpression("v")),
                                                new AssignmentStatement(
                                                        new BinaryOperatorExpression("-", new VariableExpression("v"), new ConstantExpression(new IntegerValue(1))),
                                                        "v"
                                                )
                                        )
                                ),
                                new PrintStatement(new VariableExpression("v"))
                        )
                )
        );
    }

    private static IStatement buildExample7() {
        return new CompoundStatement(
                new VariableDeclarationStatement(new IntType(), "v"),
                new CompoundStatement(
                        new VariableDeclarationStatement(new RefType(new IntType()), "a"),
                        new CompoundStatement(
                                new AssignmentStatement(new ConstantExpression(new IntegerValue(10)), "v"),
                                new CompoundStatement(
                                        new NewStatement("a", new ConstantExpression(new IntegerValue(22))),
                                        new CompoundStatement(
                                                new ForkStatement(new CompoundStatement(
                                                        new WriteHeapStatement("a", new ConstantExpression(new IntegerValue(30))),
                                                        new CompoundStatement(
                                                                new AssignmentStatement(new ConstantExpression(new IntegerValue(32)), "v"),
                                                                new CompoundStatement(
                                                                        new PrintStatement(new VariableExpression("v")),
                                                                        new PrintStatement(new ReadHeapExpression(new VariableExpression("a")))
                                                                )
                                                        )
                                                )),
                                                new CompoundStatement(
                                                        new PrintStatement(new VariableExpression("v")),
                                                        new PrintStatement(new ReadHeapExpression(new VariableExpression("a")))
                                                )
                                        )
                                )
                        )
                )
        );
    }
}
*/