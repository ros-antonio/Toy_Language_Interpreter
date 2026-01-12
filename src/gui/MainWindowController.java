package gui;

import controller.Controller;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.util.Pair;
import model.state.ProgramState;
import model.value.IValue;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class MainWindowController {

    @FXML private TextField numberOfProgramStatesTextField;
    @FXML private TableView<Pair<Integer, IValue>> heapTableView;
    @FXML private TableColumn<Pair<Integer, IValue>, String> heapAddressColumn;
    @FXML private TableColumn<Pair<Integer, IValue>, String> heapValueColumn;
    @FXML private ListView<String> outputListView;
    @FXML private ListView<String> fileTableListView;
    @FXML private ListView<Integer> programStateIdentifiersListView;
    @FXML private TableView<Pair<String, IValue>> symbolTableView;
    @FXML private TableColumn<Pair<String, IValue>, String> variableNameColumn;
    @FXML private TableColumn<Pair<String, IValue>, String> variableValueColumn;
    @FXML private ListView<String> executionStackListView;
    @FXML private Button runOneStepButton;

    private Controller controller;

    public void setController(Controller controller) {
        this.controller = controller;
        populate();
    }

    @FXML
    public void initialize() {
        heapAddressColumn.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().getKey().toString()));
        heapValueColumn.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().getValue().toString()));

        variableNameColumn.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().getKey()));
        variableValueColumn.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().getValue().toString()));

        programStateIdentifiersListView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
    }

    @FXML
    private void runOneStep() {
        if (controller == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "No program selected", ButtonType.OK);
            alert.showAndWait();
            return;
        }

        List<ProgramState> programStates = controller.repository.getPrgList();
        programStates = controller.removeCompletedPrg(programStates);
        controller.repository.setPrgList(programStates);
        if (programStates.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Program finished", ButtonType.OK);
            alert.showAndWait();
            return;
        }

        try {
            controller.oneStepForAllPrg(programStates);
            populate();
        } catch (InterruptedException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage(), ButtonType.OK);
            alert.showAndWait();
        }
    }

    @FXML
    private void changeProgramState() {
        updateSpecificStateViews();
    }

    private void populate() {
        List<ProgramState> programStates = controller.repository.getPrgList();

        numberOfProgramStatesTextField.setText(String.valueOf(programStates.size()));

        if (programStates.isEmpty()) {
            programStateIdentifiersListView.getItems().clear();
            return;
        }

        ProgramState firstState = programStates.getFirst();

        Map<Integer, IValue> heapContent = firstState.getHeap().getContent();
        List<Pair<Integer, IValue>> heapList = new ArrayList<>();
        for (Map.Entry<Integer, IValue> entry : heapContent.entrySet()) {
            heapList.add(new Pair<>(entry.getKey(), entry.getValue()));
        }
        heapTableView.setItems(FXCollections.observableArrayList(heapList));
        heapTableView.refresh();

        outputListView.setItems(FXCollections.observableArrayList(
                firstState.getOut().toString().split("\n")
        ));

        List<String> files = new ArrayList<>();
        for(var entry : firstState.getFileTable().getContent().keySet()) {
            files.add(entry.toString());
        }
        fileTableListView.setItems(FXCollections.observableArrayList(files));

        List<Integer> idList = programStates.stream()
                .map(ProgramState::getId)
                .collect(Collectors.toList());
        programStateIdentifiersListView.setItems(FXCollections.observableArrayList(idList));

        if (programStateIdentifiersListView.getSelectionModel().getSelectedItem() == null && !programStates.isEmpty()) {
            programStateIdentifiersListView.getSelectionModel().select(0);
        }

        updateSpecificStateViews();
    }

    private void updateSpecificStateViews() {
        Integer selectedId = programStateIdentifiersListView.getSelectionModel().getSelectedItem();
        if (selectedId == null) {
            symbolTableView.getItems().clear();
            executionStackListView.getItems().clear();
            return;
        }

        ProgramState selectedState = null;
        for (ProgramState state : controller.repository.getPrgList()) {
            if (state.getId() == selectedId) {
                selectedState = state;
                break;
            }
        }

        if (selectedState != null) {
            List<Pair<String, IValue>> symList = new ArrayList<>();
            for (Map.Entry<String, IValue> entry : selectedState.getSymTable().getContent().entrySet()) {
                symList.add(new Pair<>(entry.getKey(), entry.getValue()));
            }
            symbolTableView.setItems(FXCollections.observableArrayList(symList));
            symbolTableView.refresh();

            executionStackListView.setItems(FXCollections.observableArrayList(
                    selectedState.getExeStack().toString().split("\n")
            ));
        }
    }
}