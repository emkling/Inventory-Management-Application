package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Inventory;
import model.Part;
import model.Product;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;


/** Controller class that dictates the main screen function of the application.
 *
 */
public class MainFormController implements Initializable {

        /**
         * Table for the part elements
         */
        @FXML
        private TableView<Part> partTableView;

        /**
         * Table for the product elements
         */
        @FXML
        private TableView<Product> productTableView;

        /**
         * Column for inventory within the part table
         */
        @FXML
        private TableColumn<Part, Integer> mfPartInventoryColumn;

        /**
         * Column for part ID within the part table
         */
        @FXML
        private TableColumn<Part, Integer> mfPartIDColumn;

        /**
         * Column for part name within the part table
         */
        @FXML
        private TableColumn<Part, String> mfPartNameColumn;

        /**
         * Column for part price within the part table
         */
        @FXML
        private TableColumn<Part, Double> mfPartPriceColumn;

        /**
         * Column for product ID within the product table
         */
        @FXML
        private TableColumn<Product, Integer> mfProductIDColumn;

        /**
         * Column for product inventory within the product table
         */
        @FXML
        private TableColumn<Product, Integer> mfProductInventoryColumn;

        /**
         * Column for the product name within the product table
         */
        @FXML
        private TableColumn<Product, String> mfProductNameColumn;

        /**
         * Column for the product price within the product table
         */
        @FXML
        private TableColumn<Product, Double> mfProductPriceColumn;

        /**
         * Text-field where users search for parts
         */
        @FXML
        private TextField mfSearchPartID;

        /**
         * Text-field where users search for products
         */
        @FXML
        private TextField mfSearchProductID;

        /**
         * Part selected by user for modification
         */
        private static Part chosenPart;

        /**
         * Product selected by user for modification
         */
        private static Product chosenProduct;

        /**
         * Stage for loading
         */
        Stage stage;

        /**
         * Parent for loading
         */
        Parent scene;


        /**
         * Loads the modifyPartController
         *
         * @param event Modify part button
         * @throws IOException From FXMLLoader
         *
         */
        @FXML
        void onActionMFModifyPartBtn(ActionEvent event) throws IOException
        {
                try
                {
                chosenPart = partTableView.getSelectionModel().getSelectedItem();

                stage = ((Stage)((Button)event.getSource()).getScene().getWindow());
                scene = FXMLLoader.load(getClass().getResource("/view/ModifyPart.fxml"));
                stage.setScene(new Scene(scene));
                stage.show();
                }
                catch (Exception e)
                {
                        alertNotice(5);
                }
        }

        /**
         * Loads the add part controller
         *
         * @param event Add part button action
         * @throws IOException From FXMLLoader
         */
        @FXML
        void onActionMfAddPartBtn(ActionEvent event) throws IOException
        {
                stage = ((Stage)((Button)event.getSource()).getScene().getWindow());
                scene = FXMLLoader.load(getClass().getResource("/view/AddPart.fxml"));
                stage.setScene(new Scene(scene));
                stage.show();
        }

        /**
         * Loads addProductController
         *
         * @param event Add product button action
         * @throws IOException From FXMLLoader
         */
        @FXML
        void onActionMfAddProductBtn(ActionEvent event) throws IOException
        {

                stage = ((Stage)((Button)event.getSource()).getScene().getWindow());
                scene = FXMLLoader.load(getClass().getResource("/view/AddProduct.fxml"));
                stage.setScene(new Scene(scene));
                stage.show();
        }

        /**
         * Loads modifyProductController
         *
         * @param event Add product button action
         * @throws IOException From FXMLLoader
         *
         */
        @FXML
        void onActionMfModifyProductBtn(ActionEvent event) throws IOException
        {
                try
                {
                        chosenProduct = productTableView.getSelectionModel().getSelectedItem();

                        stage = ((Stage) ((Button) event.getSource()).getScene().getWindow());
                        scene = FXMLLoader.load(getClass().getResource("/view/ModifyProduct.fxml"));
                        stage.setScene(new Scene(scene));
                        stage.show();
                }
                catch (Exception e)
                {
                        alertNotice(2);
                }
        }

        /**
         * Searches for matching elements between the part table and search TextField
         *
         * @param event Part search button action
         */
        @FXML
        void onActionSearchPartBtn(ActionEvent event)
        {
                if (mfSearchPartID.getText().isEmpty())
                {
                        partTableView.setItems(Inventory.getAllParts());
                }

                ObservableList<Part> partSearchResults = FXCollections.observableArrayList();

                for (Part part: Inventory.getAllParts())
                {
                        if (part.getName().contains(mfSearchPartID.getText()) || String.valueOf(part.getId()).contains(mfSearchPartID.getText()))
                        {
                                partSearchResults.add(part);
                        }

                }
                if (partSearchResults.size()==0)
                {
                        alertNotice(4);
                }
               partTableView.setItems(partSearchResults);
        }

        /**
         * Searches for matching elements between the part table and search TextField
         *
         * @param event Product search button action
         */
        @FXML
        void onActionSearchProductBtn(ActionEvent event)
        {

                if (mfSearchProductID.getText().isEmpty())
                {
                        productTableView.setItems(Inventory.getAllProducts());
                }

                ObservableList<Product> productSearchResults = FXCollections.observableArrayList();

                for (Product product: Inventory.getAllProducts())
                {
                        if (product.getName().contains(mfSearchProductID.getText()) || String.valueOf(product.getId()).contains(mfSearchProductID.getText()))
                        {
                                productSearchResults.add(product);
                        }
                }
                if (productSearchResults.size()== 0)
                {
                        alertNotice(1);
                }
                productTableView.setItems(productSearchResults);
        }


        /**
         * Deletes selected part
         *
         * @param event Delete part button action
         */
        @FXML
        void onActionMfDeletePartBtn(ActionEvent event)
        {
                Part deletePart =  partTableView.getSelectionModel().getSelectedItem();

                if (deletePart == null)
                {
                        alertNotice(5);
                        return;
                }
                else
                {
                        Alert confirmNotice = new Alert(Alert.AlertType.CONFIRMATION);
                        confirmNotice.setTitle("Part");
                        confirmNotice.setHeaderText("Delete");
                        confirmNotice.setContentText("Do you want to delete: "+ deletePart.getName() + "?");
                        Optional<ButtonType> userChoice = confirmNotice.showAndWait();

                        if (userChoice.get() == ButtonType.OK && userChoice.isPresent())
                        {
                                Inventory.deletePart(deletePart);
                        }
                }
        }

        /**
         * Deletes selected product
         *
         * @param event Delete product button action
         */
        @FXML
        void onActionMfDeleteProductBtn(ActionEvent event)
        {
                Product deleteProduct =  productTableView.getSelectionModel().getSelectedItem();

                if (deleteProduct == null)
                {
                        alertNotice(2);
                        return;
                }

                if (deleteProduct.getAllAssociatedParts().size() >= 1)
                {
                        alertNotice(3);
                }

                else
                {
                        Alert confirmNotice = new Alert(Alert.AlertType.CONFIRMATION);
                        confirmNotice.setTitle("Product");
                        confirmNotice.setHeaderText("Delete");
                        confirmNotice.setContentText("Do you want to delete: "+ deleteProduct.getName() + "?");
                        Optional<ButtonType> userChoice = confirmNotice.showAndWait();

                        if (userChoice.get() == ButtonType.OK && userChoice.isPresent())
                        {
                                Inventory.deleteProduct(deleteProduct);
                        }
                }

        }

        /**
         * Creates and displays alert for user or input errors
         *
         * @param alertNumber The alert selector
         */
        private void alertNotice(int alertNumber)
        {

                  Alert alertNotice = new Alert(Alert.AlertType.INFORMATION);
                  Alert errorNotice = new Alert(Alert.AlertType.ERROR);

                if (alertNumber == 1)

                  {
                    alertNotice.setTitle("Product");
                    alertNotice.setHeaderText("No Products Found");
                    alertNotice.showAndWait();
                  }
                  else if (alertNumber == 2)
                  {
                    errorNotice.setTitle("Error");
                    errorNotice.setHeaderText("No Product Selected");
                    errorNotice.showAndWait();
                  }
                  else if (alertNumber == 3)
                  {
                    errorNotice.setTitle("Error");
                    errorNotice.setHeaderText("Associated parts must be removed from product before deletion");
                    errorNotice.showAndWait();
                  }
                  else if (alertNumber == 4)
                  {
                    alertNotice.setTitle("Part");
                    alertNotice.setHeaderText("No Parts Found");
                    alertNotice.showAndWait();
                  }
                  else if (alertNumber == 5)
                  {
                    errorNotice.setTitle("Error");
                    errorNotice.setHeaderText("No Part Selected");
                    errorNotice.showAndWait();
                  }

        }

        /**
         * Closes application
         *
         * @param event Exit button action
         */
        @FXML
        void onActionMfExitBtn(ActionEvent event) { System.exit(0); }

        /**
         * Retrieves the chosenPart that is selected by the user for modification
         *
         * @return Part that is selected by user
         */
        public static Part getChosenPart() { return chosenPart; }

        /**
         * Retrieves the chosenProduct that is selected by the user for modification
         *
         * @return Product that is selected by user
         */
        public static Product getChosenProduct() { return chosenProduct; }

        /**
         * Sets the table views and initializes the controller
         *
         * @param url Relative path for root object
         * @param resourceBundle Resources used to localize root object
         */
        @Override
        public void initialize(URL url, ResourceBundle resourceBundle)
        {
        // sets the parts table
        partTableView.setItems(Inventory.getAllParts());
        mfPartIDColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        mfPartNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        mfPartInventoryColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        mfPartPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

        // sets the product table
        productTableView.setItems(Inventory.getAllProducts());
        mfProductIDColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        mfProductNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        mfProductInventoryColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        mfProductPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
    }
}
