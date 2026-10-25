package com.taiwo.todolist.Items;

import javafx.collections.FXCollections;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import java.nio.file.Path;
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.*;

class TodoDataPersistenceTest {
    @TempDir Path directory;

    @Test
    void savingAShorterListDoesNotLeaveOldRecordsBehind() throws Exception {
        Path file = directory.resolve("tasks.tsv");
        TodoData data = new TodoData(file);
        data.setItemsList(FXCollections.observableArrayList(
            new TodoItems("Long original task", "A longer description", LocalDate.of(2026, 1, 1)),
            new TodoItems("Another task", "Another description", LocalDate.of(2026, 1, 2))));
        data.saveItemsToFile();
        data.setItemsList(FXCollections.observableArrayList(
            new TodoItems("New", "Short", LocalDate.of(2026, 1, 3))));
        data.saveItemsToFile();
        TodoData loaded = new TodoData(file);
        loaded.loadDataFromFile();
        assertEquals(1, loaded.getItemsList().size());
        assertEquals("New", loaded.getItemsList().get(0).getItemName());
        assertEquals(LocalDate.of(2026, 1, 3), loaded.getItemsList().get(0).getLocalDate());
    }

    @Test
    void missingFileStartsWithAnEmptyList() {
        TodoData data = new TodoData(directory.resolve("not-created.tsv"));
        assertDoesNotThrow(data::loadDataFromFile);
        assertTrue(data.getItemsList().isEmpty());
    }
}