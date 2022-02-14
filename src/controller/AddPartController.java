package controller;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;

import java.net.URL;
import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import main.Main;
import model.InHouse;
import model.Inventory;
import model.Outsourced;

import java.util.ResourceBundle;

/**
 * Controller class that dictates the add part window of the application
 *
 */

public class AddPartController implements Initializable
{
    /**
     * Inventory text-field for user input
     */
    @FXML
    private TextField addPartInvInput;

    /**
     * Maximum text-field for user input
     */
    @FXML
    private TextField addPartMaxInput;

    /**
     * Minimum text-field for user input
     */
    @FXML
    private TextField addPartMinInput;

    /**
     * Name text-field for user input
     */
    @FXML
    private TextField addPartNameInput;

    /**
     * Price text-field for price input
     */
    @FXML
    private TextField addPartPriceInput;

    /**
     * Machine ID or Company ID identifier text-field for user input
     */
    @FXML
    private TextField addPartIdentifierInput;

    /**
     * In-house radio button for user selection
     */
    @FXML
    private RadioButton addPartInHouseRBtn;

    /**
     * Label for the identifier text-field
     */
    @FXML
    private Label addPartIdentifier;

    /**
     * Stage for loading
     */
    Stage stage;

    /**
     * Scene for loading
     */
    Parent scene;

    /**
     * Returns to mainFormController
     *
     * @param event Cancel button action
     * @throws Exception From FXMLLoader
     */
    @FXML
    void onActionAddPartCancelBtn(ActionEvent event) throws Exception
    {
        stage = ((Stage)((Button)event.getSource()).getScene().getWindow());
        Main.loadMainForm(stage);
    }

    /**
     * Saves user inputs and loads mainFormController
     *
     * @param event Save button action
     */
    @FXML
    void onActionAddPartSaveBtn(ActionEvent event) {
        try
        {
            int inv = Integer.parseInt(addPartInvInput.getText());
            int max = Integer.parseInt(addPartMaxInput.getText());
            int min = Integer.parseInt(addPartMinInput.getText());
            String name = addPartNameInput.getText();
            double price = Double.parseDouble(addPartPriceInput.getText());
            String companyIdentifier;
            int machineIdentifier;
            if (name.isEmpty()) {
                alertNotice(2);
            }
            else
            {
                if (minMaxCheck(min, max) && inventoryCheck(min, max, inv))
                {
                    if (addPartInHouseRBtn.isSelected())
                    {
                        try
                        {
                            int id = newPartIdGen();
                            machineIdentifier = Integer.parseInt(addPartIdentifierInput.getText());
                            InHouse newInHousePart = new InHouse(id, name, price, inv, min, max, machineIdentifier);
                            Inventory.addPart(newInHousePart);

                            stage = ((Stage) ((Button) event.getSource()).getScene().getWindow());
                            scene = FXMLLoader.load(getClass().getResource("/view/MainForm.fxml"));
                            stage.setScene(new Scene(scene));
                            stage.show();
                        }
                        catch (Exception e)
                        {
                            alertNotice(4);
                        }
                    }
                    else
                    {
                        int id = newPartIdGen();
                        companyIdentifier = addPartIdentifierInput.getText();
                        Outsourced newOutsourcedPart = new Outsourced(id, name, price, inv, min, max, companyIdentifier);
                        Inventory.addPart(newOutsourcedPart);

                        stage = ((Stage) ((Button) event.getSource()).getScene().getWindow());
                        Main.loadMainForm(stage);
                    }
                }
            }
        }
        catch (Exception e)
        {
            alertNotice(1);
        }
    }

    /**
     * Checks the validity of the inventory input
     *
     * @param inv The inventory value
     * @param min The minimum value
     * @param max The maximum value
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
     * Checks the validity of the minimum and maximum inputs
     *
     * @param min The minimum value
     * @param max The maximum value
     * @return Boolean indicating the validity of the min value
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
     * Alerts the user of error or invalid inputs
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
     * Generates new part ID
     *
     * @return New Part ID
     */
    public static int newPartIdGen() { return Inventory.getAllParts().size()+1; }

    /**
     * Sets the addPartIdentifier label to machine ID
     *
     * @param event In-house radio button action
     */
    @FXML
    void onActionInHouseRBtn(ActionEvent event) { addPartIdentifier.setText("Machine ID"); }

    /**
     * Sets the addPartIdentifier label to company name
     *
     * @param event Outsourced radio button action
     */
    @FXML
    void onActionOutSourcedRBtn(ActionEvent event) { addPartIdentifier.setText("Company Name"); }

    /**
     * Initializes the controller
     *
     * @param url Relative path for root object
     * @param resourceBundle Resources used to localize root object
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) { }
}
