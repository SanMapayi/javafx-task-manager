package com.taiwo.todolist;

import com.taiwo.todolist.Items.TodoData;
import com.taiwo.todolist.Items.TodoItems;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.time.LocalDate;

public class DialogController {

    @FXML
    private TextField itemTextId;
    @FXML
    private  DatePicker datePickerId;
    @FXML
    private TextArea itemDescriptionId;
    @FXML
    private DialogPane dialogWindow;
    @FXML
    private Button clearButton;

    public TodoItems processResult() {
        String itemId = itemTextId.getText();
        String description = itemDescriptionId.getText();
        LocalDate datePicker = datePickerId.getValue();
        TodoItems item =
                new TodoItems(
                        itemId, description, datePicker
                );
        TodoData.todoDataInstance.addTodoItem(
                item
        );

        return item;


    }
//    public void clearDate() {
//
//
//        Button button = (Button) dialogWindow.lookupButton(ButtonType.OK);
//        System.out.println(dialogWindow.getButtonTypes());
//
////        button.disableProperty().bind(
////                Bindings.isNull(datePickerId.valueProperty())
////        );
//
//        clearButton.setOnAction(e -> {
//            datePickerId.setValue(null);
//            System.out.println("CLEAR button is pressed");
//        }
//        );
//
//    }

}
