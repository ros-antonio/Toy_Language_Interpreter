import controller.Controller;
import model.containers.*;
import model.expression.*;
import model.statement.*;
import model.state.ProgramState;
import model.type.BoolType;
import model.type.IntType;
import model.type.StringType;
import model.value.*;
import repository.IRepository;
import repository.Repository;
import view.ExitCommand;
import view.RunExample;
import view.TextMenu;
import java.io.BufferedReader;

public class Interpreter {
    public static void main(String[] args) {
        IStatement ex1 = buildExample1();
        IStack<IStatement> exeStack1 = new GenericStack<>();
        IDictionary<String, IValue> symTable1 = new GenericDictionary<>();
        IList<IValue> out1 = new GenericList<>();
        IDictionary<StringValue, BufferedReader> fileTable1 = new GenericDictionary<>();
        IHeap<IValue> heap1 = new GenericHeap<>();
        ProgramState prg1 = new ProgramState(exeStack1, symTable1, out1, fileTable1, heap1);
        exeStack1.push(ex1);
        IRepository repo1 = new Repository(prg1, "log1.txt");
        Controller ctr1 = new Controller(repo1);

        IStatement ex2 = buildExample2();
        IStack<IStatement> exeStack2 = new GenericStack<>();
        IDictionary<String, IValue> symTable2 = new GenericDictionary<>();
        IList<IValue> out2 = new GenericList<>();
        IDictionary<StringValue, BufferedReader> fileTable2 = new GenericDictionary<>();
        IHeap<IValue> heap2 = new GenericHeap<>();
        ProgramState prg2 = new ProgramState(exeStack2, symTable2, out2, fileTable2, heap2);
        exeStack2.push(ex2);
        IRepository repo2 = new Repository(prg2, "log2.txt");
        Controller ctr2 = new Controller(repo2);

        IStatement ex3 = buildExample3();
        IStack<IStatement> exeStack3 = new GenericStack<>();
        IDictionary<String, IValue> symTable3 = new GenericDictionary<>();
        IList<IValue> out3 = new GenericList<>();
        IDictionary<StringValue, BufferedReader> fileTable3 = new GenericDictionary<>();
        IHeap<IValue> heap3 = new GenericHeap<>();
        ProgramState prg3 = new ProgramState(exeStack3, symTable3, out3, fileTable3,  heap3);
        exeStack3.push(ex3);
        IRepository repo3 = new Repository(prg3, "log3.txt");
        Controller ctr3 = new Controller(repo3);

        IStatement ex4 = buildExample4();
        IStack<IStatement> exeStack4 = new GenericStack<>();
        IDictionary<String, IValue> symTable4 = new GenericDictionary<>();
        IList<IValue> out4 = new GenericList<>();
        IDictionary<StringValue, BufferedReader> fileTable4 = new GenericDictionary<>();
        IHeap<IValue> heap4 = new GenericHeap<>();
        ProgramState prg4 = new ProgramState(exeStack4, symTable4, out4, fileTable4,  heap4);
        exeStack4.push(ex4);
        IRepository repo4 = new Repository(prg4, "log4.txt");
        Controller ctr4 = new Controller(repo4);

        TextMenu menu = new TextMenu();
        menu.addCommand(new ExitCommand("0", "Exit"));
        menu.addCommand(new RunExample("1", ex1.toString(), ctr1));
        menu.addCommand(new RunExample("2", ex2.toString(), ctr2));
        menu.addCommand(new RunExample("3", ex3.toString(), ctr3));
        menu.addCommand(new RunExample("4", ex4.toString(), ctr4));

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
}