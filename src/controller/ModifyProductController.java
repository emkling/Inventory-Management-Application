package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import main.Main;
import model.Inventory;
import model.Part;
import model.Product;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * Controller class that dictates the modify product window of the application
 *
 */

public class ModifyProductController implements Initializable
{
        /**
         * Tableview for the associated parts table
         */
        @FXML
        private TableView<Part> modifyProductBottomTable;

        /**
         * Tableview for all parts table
         */
        @FXML
        private TableView<Part> modifyProductTopTable;

        /**
         * Inventory column for the associated parts table
         */
        @FXML
        private TableColumn<Part, Integer> bottomTableInventoryColumn;

        /**
         * Part ID column for the associated parts table
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
         * Product ID text-field for user input
         */
        @FXML
        private TextField modifyProductIdInput;

        /**
         * Inventory text-field for user input
         */
        @FXML
        private TextField modifyProductInvInput;

        /**
         * Maximum text-field for user input
         */
        @FXML
        private TextField modifyProductMaxInput;

        /**
         * Minimum text-field for user input
         */
        @FXML
        private TextField modifyProductMinInput;

        /**
         * Name text-field for user input
         */
        @FXML
        private TextField modifyProductNameInput;

        /**
         * Price text-field for user input
         */
        @FXML
        private TextField modifyProductPriceInput;

        /**
         * Search text-field for the all parts table
         */
        @FXML
        private TextField modifyProductSearchInput;

        /**
         * Inventory column for the all parts table
         */
        @FXML
        private TableColumn<Part, Integer> topTableInventoryLevelColumn;

        /**
         * Part ID column for the all parts table
         */
        @FXML
        private TableColumn<Part, Integer> topTablePartIdColumn;

        /**
         * Name column for the all parts table
         */
        @FXML
        private TableColumn<Part, String> topTablePartNameColumn;

        /**
         * Price column for the all parts table
         */
        @FXML
        private TableColumn<Part, Double> topTablePriceColumn;

        /**
         * List of parts that are added to a product, but will be removed if cancellation occurs
         */
        ObservableList<Part> tentativePartChanges = FXCollections.observableArrayList();

        /**
         * Stage for loading
         */
        Stage stage;

        /**
          * Count of the changes made to the associated list
          */
         int changeCount = 0;

        /**
         * Adds parts to the associated parts list of a product
         *
         * @param event Add button action
         *
         */
        @FXML
        void onActionAddBtn(ActionEvent event)
            {
                Part chosenPart= modifyProductTopTable.getSelectionModel().getSelectedItem();

                if (chosenPart == null)
                {
                    alertNotice(5);
                }
                else
                {
                    MainFormController.getChosenProduct().getAllAssociatedParts().add(chosenPart);
                    modifyProductBottomTable.setItems(MainFormController.getChosenProduct().getAllAssociatedParts());
                    changeCount++;
                    tentativePartChanges.add(chosenPart);
                }
            }

        /**
         * Cancels any user modifications and loads mainFormController
         *
         * @param event Cancel button event
         */
        @FXML
        void onActionModifyProductCancelBtn(ActionEvent event) throws IOException
            {
                if (changeCount != 0)
                {
                    // removes tentative changes initialized by the add association table
                    for (Part part: tentativePartChanges)
                    {
                    MainFormController.getChosenProduct().deleteAssociatedPart(part);
                    }
                }
                stage = ((Stage)((Button)event.getSource()).getScene().getWindow());
                Main.loadMainForm(stage);
            }

        /**
         * Saves user changes to inventory and loads mainFormController
         *
         * @param event Save button event
         */
        @FXML
        void onActionModifyProductSaveBtn(ActionEvent event) {
                try
                {

                    int id = MainFormController.getChosenProduct().getId();
                    String name = modifyProductNameInput.getText();
                    int inv = Integer.parseInt(modifyProductInvInput.getText());
                    int max = Integer.parseInt(modifyProductMaxInput.getText());
                    int min = Integer.parseInt(modifyProductMinInput.getText());
                    double price = Double.parseDouble(modifyProductPriceInput.getText());

                    if (name.isEmpty())
                    {
                        alertNotice(2);
                    }
                    else
                    {
                        if (minMaxCheck(min, max) && inventoryCheck(min, max, inv))
                        {

                            Product newProduct = new Product(id, name, price, inv, min, max);

                            for (Part part : MainFormController.getChosenProduct().getAllAssociatedParts())
                            {
                                newProduct.addAssociatedPart(part);
                            }
                            Inventory.addProduct(newProduct);
                            Inventory.deleteProduct(MainFormController.getChosenProduct());

                            stage = ((Stage) ((Button) event.getSource()).getScene().getWindow());
                            Main.loadMainForm(stage);
                        }
                    }
                }
                catch (Exception e)
                {
                    alertNotice(1);
                }
            }

        /**
         * Searches for matches between the all parts table and the search text-field
         *
         * @param event Search button action
         */
        @FXML
        void onActionMpSearchBtn(ActionEvent event)
            {
                if (modifyProductSearchInput.getText().isEmpty())
                {
                    modifyProductTopTable.setItems(Inventory.getAllParts());
                }

                ObservableList<Part> results = FXCollections.observableArrayList();
                for (Part part: Inventory.getAllParts())
                {
                    if (part.getName().contains(modifyProductSearchInput.getText()) || String.valueOf(part.getId()).contains(modifyProductSearchInput.getText()))
                    {
                        results.add(part);
                    }
                }
                modifyProductTopTable.setItems(results);
            }

        /**
         * Removes parts from associated parts table
         *
         * @param event Remove association button
         */
        @FXML
        void onActionRemoveAssociatedBtn(ActionEvent event)
            {
                Part selectedPart = modifyProductBottomTable.getSelectionModel().getSelectedItem();

                if (selectedPart != null)
                {

                    Alert confirmNotice = new Alert(Alert.AlertType.CONFIRMATION);
                    confirmNotice.setTitle("Product");
                    confirmNotice.setHeaderText("Remove");
                    confirmNotice.setContentText("Remove: " + selectedPart.getName() + "?");
                    Optional<ButtonType> userChoice = confirmNotice.showAndWait();

                    if (userChoice.get() == ButtonType.OK && userChoice.isPresent())
                    {
                    MainFormController.getChosenProduct().getAllAssociatedParts().remove(modifyProductBottomTable.getSelectionModel().getSelectedItem());
                    }

                }
            }

        /**
        * Checks for the validity of the inventory value
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
        * Checks the validity of the min and max values
        *
        * @param min The minimum value
        * @param max The maximum value
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
         *Creates and displays alert for user or input errors
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
            else if (alertNumber == 3)
            {
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
            else if (alertNumber == 5)
            {
                errorNotice.setTitle("Error");
                errorNotice.setHeaderText("No part Selected");
                errorNotice.showAndWait();

            }
        }

        /**
         *Sets the table views and initializes the controller
         *
         * @param url Relative path for root object
         * @param resourceBundle Resources used to localize root object
         */
        @Override
        public void initialize(URL url, ResourceBundle resourceBundle) {

            //sets the all parts table
            modifyProductTopTable.setItems(Inventory.getAllParts());
            topTablePartIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
            topTablePartNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
            topTableInventoryLevelColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
            topTablePriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

            //sets the associated parts table
            modifyProductBottomTable.setItems(MainFormController.getChosenProduct().getAllAssociatedParts());
            bottomTablePartIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
            bottomTablePartNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
            bottomTableInventoryColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
            bottomTablePriceTable.setCellValueFactory(new PropertyValueFactory<>("price"));

            //sets the current attributes for the selected product
            modifyProductIdInput.setText(String.valueOf(MainFormController.getChosenProduct().getId()));
            modifyProductNameInput.setText(String.valueOf(MainFormController.getChosenProduct().getName()));
            modifyProductInvInput.setText(String.valueOf(MainFormController.getChosenProduct().getStock()));
            modifyProductMaxInput.setText(String.valueOf(MainFormController.getChosenProduct().getMax()));
            modifyProductMinInput.setText(String.valueOf(MainFormController.getChosenProduct().getMin()));
            modifyProductPriceInput.setText(String.valueOf(MainFormController.getChosenProduct().getPrice()));


    }
}
