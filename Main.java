package com.test2;

import com.mongodb.client.MongoCollection;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import org.bson.Document;
import org.bson.conversions.Bson;

public class Main extends Application {

    Stage window;
    Scene scene1, scene0, defineScene, modifyScene;
    MongoDB database;

    TextField nameInput, producerInput, priceInput, weightInput, searchById, newValue, idInput;

    public static void main(String[] args) {
	    launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        // Window
        window = primaryStage;
        // Database
        database = new MongoDB();

        ////////////////////////// Some useful buttons //////////////////////////

        // Confirm box
        Button button4 = new Button("ConfirmBox");
        button4.setOnAction(e -> {
            boolean result = ConfirmBox.display("Title ConfirmBox", "Are you sure about that?");
            System.out.println(result);
        });

        // Close program
        Button closeButton = new Button("Close program");
        closeButton.setOnAction(e -> closeProgram());

        // Button back to scene 1 from modify
        Button buttonBack = new Button("Back");
        buttonBack.setOnAction(e -> {
            window.setScene(scene1);
            searchById.clear();
            newValue.clear();
        });

        // Button back2 to scene 1 from define
        Button buttonBack2 = new Button("Back");
        buttonBack2.setOnAction(e -> {
            window.setScene(scene1);
            idInput.clear();
            nameInput.clear();
            producerInput.clear();
            priceInput.clear();
            weightInput.clear();
        });


        ////////////////////////// First scene //////////////////////////

        // GridPane layout of the scene
        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(10, 10, 10, 10));
        gridPane.setVgap(8);
        gridPane.setHgap(10);

        // Name label
        Label nameLabel = new Label("Username:");
        GridPane.setConstraints(nameLabel, 0, 0);

        // Name input
        TextField userInput = new TextField();
        userInput.setPromptText("username");
        GridPane.setConstraints(userInput,1, 0);

        // Password label
        Label passwordLabel = new Label("Password:");
        GridPane.setConstraints(passwordLabel, 0, 1);

        // Password input
        TextField passInput = new TextField();
        passInput.setPromptText("password");
        GridPane.setConstraints(passInput, 1, 1);

        gridPane.getChildren().addAll(nameLabel, userInput, passwordLabel, passInput);

        Label enterLabel = new Label("Please register");

        // Button to scene 1
        Button button2 = new Button("Login");

        // Try to login
        button2.setOnAction(e -> {
            String pass = passInput.getText();
            String user = userInput.getText();

            if (database.check(user, pass)) {
                window.setScene(scene1);
                passInput.clear();
                userInput.clear();
            } else {
                AlertBox.display("Alert", "Wrong username or password");
            }
        });

        // Button to clear text fields
        Button buttonClear = new Button("Clear");
        buttonClear.setOnAction(e -> {
            passInput.clear();
            userInput.clear();
        });

        // GridPane for right menu
        GridPane rightMenu = new GridPane();
        rightMenu.setPadding(new Insets(10, 10, 10, 10));
        rightMenu.setVgap(8);
        rightMenu.setHgap(10);

        GridPane.setConstraints(button2, 0,0);
        GridPane.setConstraints(buttonClear, 0, 1);

        rightMenu.getChildren().addAll(button2, buttonClear);

        BorderPane borderPane2 = new BorderPane();
        borderPane2.setTop(enterLabel);
        borderPane2.setCenter(gridPane);
        borderPane2.setRight(rightMenu);

        // Create first scene
        scene0 = new Scene(borderPane2, 400, 150);

        //// Second  scene ////

        // Label question
        Label label1 = new Label("    What action?    ");
        HBox topMenu = new HBox();
        topMenu.getChildren().addAll(label1);

        // Button return to welcome scene
        Button button1 = new Button("Back to login");
        button1.setOnAction(e -> {
            window.setScene(scene0);
        });

        // Button to define a product
        Button defineButton = new Button("Define new product");
        defineButton.setOnAction(e -> window.setScene(defineScene));

        // Button to modify a product
        Button searchButton = new Button("Modify a product");
        searchButton.setOnAction(e -> window.setScene(modifyScene));

        // GridPane layout of the scene
        GridPane leftMenu = new GridPane();
        leftMenu.setPadding(new Insets(10, 10, 10, 10));
        leftMenu.setVgap(8);
        leftMenu.setHgap(10);

        // Buttons of the scene
        GridPane.setConstraints(button1, 0,0);
        GridPane.setConstraints(defineButton, 0,1);
        GridPane.setConstraints(searchButton, 0,2);
        GridPane.setConstraints(closeButton, 0,3);

        leftMenu.getChildren().addAll(button1, defineButton, searchButton, closeButton);

        BorderPane borderPane = new BorderPane();
        borderPane.setTop(topMenu);
        borderPane.setCenter(leftMenu);

        // Create second scene
        scene1 = new Scene(borderPane, 400, 200);

        ////////////////////////// Define Scene //////////////////////////

        // Get the database
        MongoCollection collectionDB = database.connect();

        // Text fields for define
        idInput = new TextField();
        idInput.setPromptText("Barcode");

        nameInput = new TextField();
        nameInput.setPromptText("Name");

        producerInput = new TextField();
        producerInput.setPromptText("Producer");

        weightInput = new TextField();
        weightInput.setPromptText("Weight(GR)");

        priceInput = new TextField();
        priceInput.setPromptText("Price(RON)");

        // Button to confirm
        Button defineOk = new Button("Ok");
        defineOk.setOnAction(e -> defineButtonClicked(collectionDB));

        // Labels for define
        Label idLabel = new Label("Barcode");
        Label nameLabelD = new Label("Name");
        Label producerLabel = new Label("Producer");
        Label weightLabel = new Label("Weight");
        Label priceLabel = new Label("Price");

        // Grid pane layout of define scene
        GridPane gridPaneDefine = new GridPane();
        gridPaneDefine.setPadding(new Insets(10, 10, 10, 10));
        gridPaneDefine.setVgap(8);
        gridPaneDefine.setHgap(10);

        GridPane.setConstraints(idLabel, 0, 0);
        GridPane.setConstraints(idInput, 1, 0);

        GridPane.setConstraints(nameLabelD, 0, 1);
        GridPane.setConstraints(nameInput, 1, 1);

        GridPane.setConstraints(producerLabel, 0, 2);
        GridPane.setConstraints(producerInput, 1, 2);

        GridPane.setConstraints(weightLabel, 0, 3);
        GridPane.setConstraints(weightInput, 1, 3);

        GridPane.setConstraints(priceLabel, 0, 4);
        GridPane.setConstraints(priceInput, 1, 4);

        GridPane.setConstraints(defineOk, 3, 0);
        GridPane.setConstraints(buttonBack2, 3, 1);

        gridPaneDefine.getChildren().addAll(idInput, nameInput, producerInput, weightInput, priceInput,
                idLabel, nameLabelD, producerLabel, weightLabel, priceLabel, defineOk, buttonBack2);

        // Create define scene
        defineScene = new Scene(gridPaneDefine);

        ////////////////////////// Modify Scene //////////////////////////

        // Text fields of modify scene
        searchById = new TextField();
        searchById.setPromptText("Barcode");

        newValue = new TextField();
        newValue.setPromptText("Value");

        // ChoiceBox to select the type
        ChoiceBox<String> choiceBox = new ChoiceBox<>();
        choiceBox.getItems().addAll("Name", "Producer", "Weight", "Price");

        // Button to confirm
        Button modifyOk = new Button("Ok");
        modifyOk.setOnAction(e -> modifyButtonClicked(collectionDB, choiceBox));

        // Grid pane layout of modify scene
        GridPane gridPaneModify = new GridPane();
        gridPaneModify.setPadding(new Insets(10, 10, 10, 10));
        gridPaneModify.setVgap(8);
        gridPaneModify.setHgap(10);

        Label searchLabel = new Label("Barcode of product");
        Label newValueLabel = new Label("New value");
        Label typeLabel = new Label("Type");


        GridPane.setConstraints(searchLabel, 0, 0);
        GridPane.setConstraints(searchById, 1, 0);

        GridPane.setConstraints(newValueLabel, 0, 1);
        GridPane.setConstraints(newValue, 1, 1);

        GridPane.setConstraints(typeLabel, 0, 2);
        GridPane.setConstraints(choiceBox, 1, 2);

        GridPane.setConstraints(modifyOk, 2, 0);
        GridPane.setConstraints(buttonBack, 2, 1);

        gridPaneModify.getChildren().addAll(searchById, newValue, choiceBox,
                searchLabel, newValueLabel, typeLabel, modifyOk, buttonBack);

        // Create modify scene
        modifyScene = new Scene(gridPaneModify);

        ////////////////////////// Close program //////////////////////////
        window.setOnCloseRequest(e -> {
            e.consume();
            closeProgram();
        });

        ////////////////////////// Start first scene //////////////////////////
        window.setScene(scene0);
        window.setTitle("ChainSupply Application");
        window.show();
    }

    // Confirm the action
    private void closeProgram() {
        Boolean answer = ConfirmBox.display("  ", "Are you sure?");
        if (answer)
            window.close();
    }

    // Define a product
    public void defineButtonClicked(MongoCollection collection) {
        boolean result = ConfirmBox.display("Title ConfirmBox", "Are you sure about that?");

        // Check the answer
        if (result == true) {
            // Check if all fields are completed
            if (nameInput.getText().length() == 0 || idInput.getText().length() == 0 || idInput.getText().length() == 0 ||
                    weightInput.getText().length() == 0 || priceInput.getText().length() == 0) {
                AlertBox.display("Alert", "You must complete all fields");
                return;
            }

            // Crate the product
            Document document = new Document("_id", idInput.getText());
            document.append("name", nameInput.getText());
            document.append("producer", producerInput.getText());

            // Check if weight is double
            try {
                double convertWeight = Double.parseDouble(weightInput.getText());
                document.append("weight(GR)", convertWeight);
            }
            catch (NumberFormatException nfe) {
                AlertBox.display("Alert", "Weight must be a number");
                return;
            }

            // Check if price is double
            try {
                double convertPrice = Double.parseDouble(priceInput.getText());
                document.append("price(RON)", convertPrice);
            }
            catch (NumberFormatException nfe) {
                AlertBox.display("Alert", "Price must be a number");
                return;
            }

            // Add with stock 0
            int zero = 0;
            document.append("stock", zero);

            // Add the product in database
            collection.insertOne(document);

            // Clear text fields
            nameInput.clear();
            priceInput.clear();
            producerInput.clear();
            weightInput.clear();
            idInput.clear();

            // Back to scene 1
            window.setScene(scene1);
        }
    }

    // Modify a product
    public void modifyButtonClicked(MongoCollection collection, ChoiceBox<String> choiceBox) {
        // Get type of product
        String type = choiceBox.getValue();

        // Search by id in database
        Document search = new Document("_id", searchById.getText());
        Document found = (Document) collection.find(search).first();

        // Update database if it is found
        if (found != null) {
            // Modify depending on type
            if (type.equals("Name")) {
                Bson updatedValue = new Document("name", newValue.getText());
                Bson updateOperation = new Document("$set", updatedValue);
                collection.updateOne(found, updateOperation);
            } else if (type.equals("Producer")) {
                Bson updatedValue = new Document("producer", newValue.getText());
                Bson updateOperation = new Document("$set", updatedValue);
                collection.updateOne(found, updateOperation);
            } else if (type.equals("Weight")) {
                // Check if the value is double
                try {
                    double convert = Double.parseDouble(newValue.getText());

                    Bson updatedValue = new Document("weight(GR)", convert);
                    Bson updateOperation = new Document("$set", updatedValue);
                    collection.updateOne(found, updateOperation);
                }
                catch (NumberFormatException nfe) {
                    AlertBox.display("Alert", "Weight must be a number");
                    return;
                }
            } else if (type.equals("Price")) {
                // Check if the value is double
                try {
                    double convert = Double.parseDouble(newValue.getText());

                    Bson updatedValue = new Document("price(RON)", convert);
                    Bson updateOperation = new Document("$set", updatedValue);
                    collection.updateOne(found, updateOperation);
                }
                catch (NumberFormatException nfe) {
                    AlertBox.display("Alert", "Price must be a number");
                    return;
                }
            }
        } else {
            // Item not found
            AlertBox.display("Alert", "Item not found");
            return;
        }

        // Clear text fields
        searchById.clear();
        newValue.clear();

        // back to scene 1
        window.setScene(scene1);
    }
}
