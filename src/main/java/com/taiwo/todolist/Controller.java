package com.taiwo.todolist;

import com.taiwo.todolist.Items.TodoData;
import com.taiwo.todolist.Items.TodoItems;
import javafx.beans.binding.Bindings;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public class Controller {

    private List<TodoItems> todoItemsList;
    @FXML
    private TextArea showTextArea,itemDescriptionId;
    @FXML
    public ListView<TodoItems> itemsListView;
    @FXML
    private Label showLabel, showDueDate;
    @FXML
    BorderPane mainWindow;
    @FXML
    private TextField itemTextId;
    @FXML
    private  DatePicker datePickerId;
    @FXML
    private Button clearButton;


    public void initialize() {


        itemsListView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<TodoItems>() {
            @Override
            public void changed(ObservableValue<? extends TodoItems> observableValue, TodoItems oldValue, TodoItems newValue) {
                if(newValue != null) {
                    var item = itemsListView.getSelectionModel().getSelectedItem();
                    showTextArea.setText(item.getDescription());
                    showDueDate.setText(item.getLocalDate().format(
                            TodoData.todoDataInstance.getFormatter()
                    ));
                }
            }
        });


        itemsListView.getItems().setAll(
                TodoData.todoDataInstance.getItemsList()
        );

        itemsListView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        itemsListView.getSelectionModel().selectFirst();


    }

    @FXML
    public void showLabelWhenClicked() {
        showLabel.setText("Welcome to the Java FX programme");
    }

    @FXML
    public void showSelectedItems() {
      TodoItems item = itemsListView.getSelectionModel().getSelectedItem();
        showTextArea.setText(item.getDescription());
        showDueDate.setText(item.getLocalDate().format(
                TodoData.todoDataInstance.getFormatter()
        ));

    }

    @FXML
    public void openDialog() throws IOException {
        Dialog<ButtonType> dialog = new Dialog<>();

        dialog.initOwner(mainWindow.getScene().getWindow());
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("dialogTodo.fxml"));
        Parent root = fxmlLoader.load();
        dialog.initModality(Modality.WINDOW_MODAL);
        dialog.setTitle("Todo List Modal");
        dialog.getDialogPane().setContent(root);
        ButtonType okButtonType = ButtonType.OK;
       // ButtonType clearButtonType = new ButtonType("CLEAR");

        dialog.getDialogPane().getButtonTypes().add(0, okButtonType);
        dialog.getDialogPane().getButtonTypes().add(1, ButtonType.CANCEL);
//        dialog.getDialogPane().getButtonTypes().add(2, clearButtonType);

        Button okButton = (Button) dialog.getDialogPane().lookupButton(okButtonType);
        okButton.setDisable(true);
        itemDescriptionId = (TextArea) dialog.getDialogPane().lookup("#itemDescriptionId");

        okButton.disableProperty().bind(
                Bindings.createBooleanBinding(
                        () -> itemDescriptionId.getText().trim().isEmpty() || itemTextId.getText().isBlank()
                        || itemDescriptionId.getText() == null,
                        itemDescriptionId.textProperty()
                )
        );


        itemTextId = (TextField) dialog.getDialogPane().lookup("#itemTextId");

        okButton.disableProperty().bind(
                Bindings.createBooleanBinding(
                        () -> itemTextId.getText().trim().isEmpty() || itemTextId.getText().isBlank() ||
                        itemTextId.getText() == null,
                        itemTextId.textProperty()
                )
        );

        datePickerId = (DatePicker) dialog.getDialogPane().lookup("#datePickerId");


        okButton.disableProperty().bind(
                Bindings.isNull(datePickerId.valueProperty())
        );

        okButton.disableProperty().bind(
                Bindings.createBooleanBinding(
                        () -> {
                            if(datePickerId.getEditor().getText().trim().isBlank() || itemTextId.getText().trim().isBlank() || itemDescriptionId.getText().isBlank()) {
                                return true;
                            }
                            else if(datePickerId.getEditor().getText().isEmpty() || itemTextId.getText().trim().isEmpty() || itemDescriptionId.getText().isEmpty()) {
                                return true;
                            }
                            else return datePickerId.getEditor().getText() == null &&
                                        itemDescriptionId.getText() == null && itemTextId.getText()==null;
                        }, datePickerId.getEditor().textProperty()
                )
        );

        datePickerId.getEditor().setOnKeyPressed(event -> {
                switch (event.getCode())
                { case DELETE, BACK_SPACE -> datePickerId.setValue(null);}});


        clearButton = (Button) dialog.getDialogPane().lookup("#clearButton");
        System.out.println(clearButton);

        clearButton.setOnAction(e ->{
                    datePickerId.setValue(null);
                    System.out.println("CLEAR button is pressed");
        } );

        Optional<ButtonType> result = dialog.showAndWait();

//                datePickerId.valueProperty().addListener(new ChangeListener<LocalDate>() {
//            @Override
//            public void changed(ObservableValue<? extends LocalDate> observableValue, LocalDate oldValue, LocalDate newValue) {
//                if (newValue == null || newValue.toString().trim().isEmpty() || newValue.toString().isBlank()) {
//                    okButton.setDisable(true);
//                }
//                else {
//                    okButton.setDisable(false);
//                }
//
//            }
//        });





        if (result.isPresent() && result.get() == ButtonType.OK) {
            System.out.println("The OK button is pressed.");
            DialogController controller = fxmlLoader.getController();
            TodoItems items = controller.processResult();
            itemsListView.getItems().setAll(
                    TodoData.todoDataInstance.getItemsList()
            );
            itemsListView.getSelectionModel().select(items);

        } else if (result.isPresent() && result.get() == ButtonType.CANCEL){
            System.out.println("The CANCEL button is pressed.");
        }


    }


}