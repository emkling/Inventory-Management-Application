package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.stage.Stage;
import main.Main;
import model.*;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controller class that dictates the modify parts window of the application
 *
 */
public class ModifyPartController implements Initializable {

        /**
         * ID text-field for user input
         */
        @FXML
        private TextField modifyPartIdInput;

        /**
         * Identifier label that signifies machine ID or company name
         */
        @FXML
        private Label modifyPartIdentifier;

        /**
         * In-house radio button for user selection
         */
        @FXML
        private RadioButton modifyPartInHouseRBtn;

        /**
         * Outsourced radio button for user selection
         */
        @FXML
        private RadioButton modifyPartOutSourcedRBtn;

        /**
         * Identifier text-field that signifies either the machine ID or company name of a part
         */
        @FXML
        private TextField modifyPartIdentifierInput;

        /**
         * Inventory text-field for user-input
         */
        @FXML
        private TextField modifyPartInvInput;

        /**
         * Maximum text-field for user input
         */
        @FXML
        private TextField modifyPartMaxInput;

        /**
         * Minimum text-field for user input
         */
        @FXML
        private TextField modifyPartMinInput;

        /**
         * Name text-field for user input
         */
        @FXML
        private TextField modifyPartNameInput;

        /**
         * Price text-field for user input
         */
        @FXML
        private TextField modifyPartPriceInput;

        /**
         * Stage for loading
         */
        Stage stage;

        /**
         * Part selected by user for modification
         */
        Part modifyPart;

        /**
         * Saves changes of modifications to inventory and loads mainFormController
         *
         * @param event Save button action
         */
        @FXML
        void onActionModifyPartSaveBtn(ActionEvent event)
        {
                try
                {
                        int id = MainFormController.getChosenPart().getId();
                        int inv = Integer.parseInt(modifyPartInvInput.getText());
                        int max = Integer.parseInt(modifyPartMaxInput.getText());
                        int min = Integer.parseInt(modifyPartMinInput.getText());
                        String name = modifyPartNameInput.getText();
                        double price = Double.parseDouble(modifyPartPriceInput.getText());
                        String companyIdentifier = null;
                        int machineIdentifier;

                        if(name.isEmpty())
                        {
                                alertNotice(2);
                        }
                        else
                        {
                                if (minMaxCheck(min, max) && inventoryCheck(min, max, inv))
                                {
                                        if (MainFormController.getChosenPart() instanceof Outsourced)
                                        {
                                                companyIdentifier = modifyPartIdentifierInput.getText();
                                                Outsourced newModPart = new Outsourced(id, name, price, inv, min, max, companyIdentifier);
                                                Inventory.addPart(newModPart);
                                                Inventory.deletePart(MainFormController.getChosenPart());
                                                stage = ((Stage)((Button) event.getSource()).getScene().getWindow());
                                                Main.loadMainForm(stage);
                                        }
                                        if (MainFormController.getChosenPart() instanceof InHouse)
                                        {

                                               try
                                                {
                                                        machineIdentifier = Integer.parseInt(modifyPartIdentifierInput.getText());
                                                        InHouse newPart = new InHouse(id, name, price, inv, min, max, machineIdentifier);
                                                        Inventory.addPart(newPart);
                                                        Inventory.deletePart(MainFormController.getChosenPart());

                                                        stage = ((Stage) ((Button) event.getSource()).getScene().getWindow());
                                                        Main.loadMainForm(stage);
                                                }
                                                  catch (Exception e)
                                                {
                                                        alertNotice(4);
                                                }

                                        }
                                }
                        }
                }
                catch (Exception e){
                        alertNotice(1);
                }
        }
        /**
         * Cancels user modifications and loads mainFormController
         *
         * @param event Cancel button action
         * @throws Exception From FXMLLoader
         */
        @FXML
        void onActionModifyPartCancelBtn(ActionEvent event) throws Exception {

                stage = ((Stage) ((Button) event.getSource()).getScene().getWindow());
                Main.loadMainForm(stage);
        }

        /**
         * Checks for the validity of the inventory value
         *
         * @param inv The inventory value
         * @param max The maximum value
         * @param min The minimum value
         * @return Boolean indicating the validity of the inventory value
         */
        private boolean inventoryCheck(int min, int max, int inv) {
                boolean valid = true;
                if (inv <= min || inv >= max) {
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
        private boolean minMaxCheck(int min, int max) {
                boolean valid = true;
                if (min >= max || min <= 0) {
                        alertNotice(4);
                        valid = false;
                }
                return valid;
        }

        /**
         * Creates and displays alert for user or input errors
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
                }

        /**
         * Changes the part identifier label to machine ID
         *
         * @param event In-house radio button action
         */
        @FXML
        void onActionInHouseRBtn (ActionEvent event){
                        modifyPartIdentifier.setText("Machine ID");
                }

        /**
         * Changes the part identifier label to company name
         *
         * @param event Outsourced radio button action
         */
        @FXML
        void onActionOutSourcedRBtn (ActionEvent event){
                        modifyPartIdentifier.setText("Company Name");
                }

        /**
         *Sets the table views and initializes the controller
         *
         * @param url Relative path for root object
         * @param resourceBundle Resources used to localize root object
         */
        @Override
        public void initialize (URL url, ResourceBundle resourceBundle)
        {
                modifyPart = MainFormController.getChosenPart();

                // sets the text-fields to current element attributes
                modifyPartIdInput.setText(String.valueOf(modifyPart.getId()));
                modifyPartNameInput.setText(String.valueOf(modifyPart.getName()));
                modifyPartInvInput.setText(String.valueOf(modifyPart.getStock()));
                modifyPartMaxInput.setText(String.valueOf(modifyPart.getMax()));
                modifyPartMinInput.setText(String.valueOf(modifyPart.getMin()));
                modifyPartPriceInput.setText(String.valueOf(modifyPart.getPrice()));

                if (modifyPart instanceof Outsourced)
                {
                        modifyPartIdentifierInput.setText(((Outsourced) modifyPart).getCompanyName());
                        modifyPartIdentifier.setText("Company Name");
                        modifyPartOutSourcedRBtn.setSelected(true);
                } else if (modifyPart instanceof InHouse)
                {
                        modifyPartIdentifierInput.setText(String.valueOf(((InHouse) modifyPart).getMachineID()));
                        modifyPartIdentifier.setText("Machine ID");
                        modifyPartInHouseRBtn.setSelected(true);
                }

        }

}