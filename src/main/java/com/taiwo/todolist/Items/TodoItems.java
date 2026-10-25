package com.taiwo.todolist.Items;

import java.time.LocalDate;

public class TodoItems {


    private String itemName;
    private String description;
    private LocalDate localDate;


    public TodoItems(String itemName, String description, LocalDate localDate) {
        this.itemName = itemName;
        this.description = description;
        this.localDate = localDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getLocalDate() {
        return localDate;
    }

    public void setLocalDate(LocalDate localDate) {
        this.localDate = localDate;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    @Override
    public String toString() {
        return  itemName;
    }
}
