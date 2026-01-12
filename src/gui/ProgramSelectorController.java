package gui;

import controller.Controller;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import model.containers.*;
import model.expression.*;
import model.statement.*;
import model.state.ProgramState;
import model.type.*;
import model.value.*;
import repository.Repository;
import repository.IRepository;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.List;

public class ProgramSelectorController {

    @FXML
    private ListView<IStatement> programsListView;

    @FXML
    private Button displayButton;

    @FXML
    public void initialize() {
        List<IStatement> allStatements = new ArrayList<>();
        allStatements.add(buildExample1());
        allStatements.add(buildExample2());
        allStatements.add(buildExample3());
        allStatements.add(buildExample4());
        allStatements.add(buildExample5());
        allStatements.add(buildExample6());
        allStatements.add(buildExample7());

        programsListView.setItems(FXCollections.observableArrayList(allStatements));
    }

    @FXML
    private void displayProgram() {
        IStatement selectedStatement = programsListView.getSelectionModel().getSelectedItem();

        if (selectedStatement == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("No program selected!");
            alert.showAndWait();
            return;
        }

        int index = programsListView.getSelectionModel().getSelectedIndex() + 1;

        try {
            selectedStatement.typecheck(new GenericDictionary<String, IType>());

            IStack<IStatement> exeStack = new GenericStack<>();
            exeStack.push(selectedStatement);
            IDictionary<String, IValue> symTable = new GenericDictionary<>();
            IList<IValue> out = new GenericList<>();
            IDictionary<StringValue, BufferedReader> fileTable = new GenericDictionary<>();
            IHeap<IValue> heap = new GenericHeap<>();
            ProgramState prg = new ProgramState(exeStack, symTable, out, fileTable, heap);

            IRepository repo = new Repository(prg, "log" + index + ".txt");
            Controller controller = new Controller(repo);

            FXMLLoader loader = new FXMLLoader(getClass().getResource("MainWindow.fxml"));
            Parent root = loader.load();

            MainWindowController mainWindowController = loader.getController();
            mainWindowController.setController(controller);

            Stage stage = new Stage();
            stage.setTitle("Program Execution");
            stage.setScene(new Scene(root, 900, 650));

            stage.setOnCloseRequest(event -> {
                controller.shutdown();
            });

            stage.show();

        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Program Setup Failed");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }

    private IStatement buildExample1() {
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

    private IStatement buildExample2() {
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

    private IStatement buildExample3() {
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

    private IStatement buildExample4() {
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

    private IStatement buildExample5() {
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

    private IStatement buildExample6() {
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

    private IStatement buildExample7() {
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