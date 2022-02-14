package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import main.Main;
import model.Inventory;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import model.Part;
import model.Product;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * Controller class that dictates the add product window of the application
 *
 */
public class AddProductController implements Initializable {

        /**
         * Associated parts tableview
         */
        @FXML
        private TableView<Part> addProductBottomTable;

        /**
         * Inventory text-field for user input
         */
        @FXML
        private TextField addProductInvInput;

        /**
         * Maximum text-field for user input
         */
        @FXML
        private TextField addProductMaxInput;

        /**
         * Minimum text-field for user input
         */
        @FXML
        private TextField addProductMinInput;

        /**
         * Name text-field for user input
         */
        @FXML
        private TextField addProductNameInput;

        /**
         * Price text-field for user input
         */
        @FXML
        private TextField addProductPriceInput;

        /**
         * All available part tableview
         */
        @FXML
        private TableView<Part> addProductTopTable;

        /**
         * Inventory column for the associated parts table
         */
        @FXML
        private TableColumn<Part, Integer> bottomTableInventoryColumn;

        /**
         * ID column for the associated parts table
         */
        @FXML
        private TableColumn<Part, Integer> bottomTablePartIdColumn;

        /**
         * Name column for the associated parts table
         */
        @FXML
        private TableColumn<Part, String> bottomTablePartNameColumn;

        /**
         * Price column for the associated parts table
         */
        @FXML
        private TableColumn<Part, Double> bottomTablePriceTable;

        /**
         * Inventory column for the all parts table
         */
        @FXML
        private TableColumn<Part, Integer> topTableInventoryLevelColumn;

        /**
         * ID column for the all parts table
         */
        @FXML
        private TableColumn<Part, Integer> topTablePartIdColumn;

        /**
         * Name column for the all parts table
         */
        @FXML
        private TableColumn<Part, String> topTablePartNameColumn;

        /**
         * Price for the all parts table
         */
        @FXML
        private TableColumn<Part, Double> topTablePriceColumn;

        /**
         * Search text-field for user input
         */
        @FXML
        private TextField addProductSearchInput;

        /**
         * List of parts selected for modification or retrieval
         */
        ObservableList<Part> chosenPartList = FXCollections.observableArrayList();

        /**
         * Stage for loading
         */
        Stage stage;

        /**
         * Adds the selected part from the all parts table, and copies it to the associated parts table
         *
         * @param event Add button action
         */
        @FXML
        void onActionAddBtn(ActionEvent event)
        {
                Part chosenPart = addProductTopTable.getSelectionModel().getSelectedItem();
                chosenPartList.add(chosenPart);
                addProductBottomTable.setItems(chosenPartList);
        }

        /**
         * Loads the mainFormController and cancels any input changes
         *
         * @param event Cancel button action
         * @throws IOException from FXMLLoader
         */
        @FXML
        void onActionAddProductCancelBtn(ActionEvent event) throws IOException
        {
                stage = ((Stage)((Button)event.getSource()).getScene().getWindow());
                Main.loadMainForm(stage);
        }

        /**
         * Adds the product created by the user to the system inventory and loads the mainFormController
         *
         * @param event Save button action
         * @throws IOException From FXMLLoader
         */
        @FXML
        void onActionAddProductSaveBtn(ActionEvent event) throws IOException
        {
                try {
                        String name = addProductNameInput.getText();
                        int inv = Integer.parseInt(addProductInvInput.getText());
                        int max = Integer.parseInt(addProductMaxInput.getText());
                        int min = Integer.parseInt(addProductMinInput.getText());
                        double price = Double.parseDouble(addProductPriceInput.getText());

                        if (name.isEmpty())
                        {
                                alertNotice(2);
                        }
                        else {
                                if (minMaxCheck(min, max) && inventoryCheck(min, max, inv))
                                {
                                        Product newProduct = new Product(newProductIdGen(), name, price, inv, min, max);
                                        Inventory.addProduct(newProduct);
                                        for (Part part : chosenPartList)
                                        {
                                                newProduct.addAssociatedPart(part);
                                        }

                                        stage = ((Stage)((Button)event.getSource()).getScene().getWindow());
                                        Main.loadMainForm(stage);
                                }
                        }
                }
                catch(Exception e)
                {
                        alertNotice(1);
                }
        }

        /**
         * Removes parts from the associated table
         *
         * @param event Remove button action
         */
        @FXML
        void onActionRemoveAssociatedBtn(ActionEvent event)
        {
                Part selectedPart = addProductBottomTable.getSelectionModel().getSelectedItem();

                if (selectedPart != null)
                {

                        Alert confirmNotice = new Alert(Alert.AlertType.CONFIRMATION);
                        confirmNotice.setTitle("Product");
                        confirmNotice.setHeaderText("Remove");
                        confirmNotice.setContentText("Remove: " + selectedPart.getName() + "?");
                        Optional<ButtonType> userChoice = confirmNotice.showAndWait();

                        if (userChoice.get() == ButtonType.OK && userChoice.isPresent())
                        {
                                chosenPartList.remove(selectedPart);
                        }
                }
        }

        /**
         * Searches for matches between the all parts table and the search text-field
         *
         * @param event Search button action
         */
        @FXML
        void onActionAddProductSearchBtn(ActionEvent event)
        {
                if (addProductSearchInput.getText().isEmpty())
                {
                        addProductTopTable.setItems(Inventory.getAllParts());
                }

                ObservableList<Part> results = FXCollections.observableArrayList();
                for (Part part: Inventory.getAllParts())
                {
                        if (part.getName().contains(addProductSearchInput.getText()) || String.valueOf(part.getId()).contains(addProductSearchInput.getText()))
                        {
                                results.add(part);
                        }
                }

                addProductTopTable.setItems(results);
        }

        /**
         * Checks the validity of the inventory values
         *
         * @param inv The inventory value
         * @param max The maximum value
         * @param min The minimum value
         * @return Boolean indicating the validity of the inventory value
         */
        private boolean inventoryCheck (int min, int max, int inv)
        {
                boolean valid = true;
                if (inv <= min || inv >= max)
                {
                        alertNotice(3);
                        valid = false;
                }
                return valid;
        }

        /**
         * Checks the validity of the min and maximum values
         *
         * @param min The minimum value
         * @param max The maximum value
         * @return Boolean indicating the validity of the min value
         *
         */
        private boolean minMaxCheck (int min, int max)
        {
                boolean valid = true;
                if (min >= max || min <= 0)
                {
                        alertNotice(4);
                        valid = false;
                }
                return valid;
        }

        /**
         * Creates and displays an alert notice upon user error or invalid inputs
         *
         * @param alertNumber The alert selector
         */
        private void alertNotice (int alertNumber)
                {
                        Alert errorNotice = new Alert(Alert.AlertType.ERROR);

                        if (alertNumber == 1)
                        {
                                errorNotice.setTitle("Part");
                                errorNotice.setHeaderText("Fields left blank or invalid values");
                                errorNotice.showAndWait();
                        }
                        else if (alertNumber == 2)
                        {
                                errorNotice.setTitle("Error");
                                errorNotice.setHeaderText("Name needs an input");
                                errorNotice.showAndWait();
                        }
                        else if (alertNumber == 3) {

                                errorNotice.setTitle("Error");
                                errorNotice.setHeaderText("Inventory must be between max and min");
                                errorNotice.showAndWait();
                        }
                        else if (alertNumber == 4)
                        {
                                errorNotice.setTitle("Error");
                                errorNotice.setHeaderText("Min must be less than max and greater than 0");
                                errorNotice.showAndWait();
                        }
                }

        /**
         * Generates new product ID
         *
         * @return New product ID
         */
        public static int newProductIdGen() { return Inventory.getAllProducts().size() + 1; }

        /**
         * Creates table views and initializes controller
         *
         * @param url Relative path for root object
         * @param resourceBundle Resources used to localize root object
         */
        @Override
         public void initialize(URL url, ResourceBundle resourceBundle)
         {
            // sets the upper table of all parts
            addProductTopTable.setItems(Inventory.getAllParts());
            topTablePartIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
            topTablePartNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
            topTableInventoryLevelColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
            topTablePriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

            // set the lower table of associated parts
            bottomTablePartIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
            bottomTablePartNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
            bottomTableInventoryColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
            bottomTablePriceTable.setCellValueFactory(new PropertyValueFactory<>("price"));
          }
}