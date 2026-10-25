package com.taiwo.todolist.Items;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Iterator;

public class TodoData {

    public final static TodoData todoDataInstance = new TodoData();
    private ObservableList<TodoItems> itemsList;
    private final DateTimeFormatter formatter;
    private final Path storagePath;

    public TodoData() { this(null); }

    public TodoData(Path storagePath) {
        this.storagePath = storagePath;
        formatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL);
    }

    public void saveItemsToFile() throws IOException {

        Path filePath = getFilePath();
        if (!Files.exists(filePath)) {
            Files.createFile(filePath);
        }
        try (BufferedWriter wr = new BufferedWriter(Files.newBufferedWriter(filePath,
                StandardOpenOption.WRITE, StandardOpenOption.TRUNCATE_EXISTING))) {
            Iterator<TodoItems> iterator = itemsList.listIterator();
            while (iterator.hasNext()) {
                TodoItems item = iterator.next();
                String itemData = String.format("%s\t%s\t%s",
                        item.getItemName(), item.getDescription(), item.getLocalDate().format(formatter));
                wr.write(itemData);
                wr.newLine();
            }
        }
    }

    public void loadDataFromFile() {


        itemsList = FXCollections.observableArrayList();

        try {
            if (!Files.exists(getFilePath())) return;
        } catch (IOException e) { throw new java.io.UncheckedIOException(e); }
        try (BufferedReader br = new BufferedReader(Files.newBufferedReader(getFilePath()))) {
            String line;

            while ((line = br.readLine()) != null) {
                String[] itemDataLine = line.split("\t");
                String itemName = itemDataLine[0];
                String description = itemDataLine[1];
                LocalDate dueDate = LocalDate.parse(itemDataLine[2], formatter);
                itemsList.add(new TodoItems(itemName, description, dueDate));

            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Path getFilePath() throws IOException {
        if (storagePath != null) {
            Files.createDirectories(storagePath.toAbsolutePath().getParent());
            return storagePath;
        }
        Path root = Path.of(System.getProperty("user.dir"));
        Path paths = Paths.get("src", "main", "java");
        Path textFileLocation = Path.of("textFileLocation");
        Path directory = root.resolve(paths).resolve(textFileLocation);

        if (!(Files.exists(directory) && Files.isDirectory(directory))) {
            Files.createDirectories(directory);
        }
        ;

        Path fileNamePath = directory.resolve("todoItems.txt");
        return fileNamePath;
    }

    public ObservableList<TodoItems> getItemsList() {
        return itemsList;
    }

    public void setItemsList(ObservableList<TodoItems> itemsList) {
        this.itemsList = itemsList;
    }

    public DateTimeFormatter getFormatter() {
        return formatter;
    }

    public void addTodoItem(TodoItems item) {
        itemsList.add(item);
    }
}
