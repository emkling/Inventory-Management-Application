package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import model.*;

import java.io.IOException;

/**
 * Main application class that dictates the inventory of products and parts
 *
 */
public class Main extends Application

{
    /**
     * Checks if data has been entered
     *
     * @return Boolean indicating whether data has been added to the application
     */
    private static boolean dataEntered = true;

    /**
     * Creates the stage and loads the scene
     *
     * @param stage
     * @throws Exception from FXMLLoader
     */
    @Override
    public void start(Stage stage) throws Exception
    {
        Parent root = FXMLLoader.load(getClass().getResource("/view/MainForm.fxml"));
        stage.setTitle("First Screen");
        stage.setScene(new Scene(root, 1138, 690));
        stage.show();
    }

    public static void loadMainForm(Stage stageInput) throws IOException
    {
        Stage stage = stageInput;
        Parent scene = FXMLLoader.load(Main.class.getResource("/view/MainForm.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * Adds test data for application
     */
    public static void addData()
    {
        if(!dataEntered)
        {
            return;
        }
        // Parts data
        dataEntered = false;
        InHouse lcdScreens = new InHouse(1,"LCD Screens", 50,10,1,15, 1);
        InHouse ramCards = new InHouse(2,"RAM Cards", 35,12,7,20, 2);
        InHouse cpus = new InHouse(3,"CPUs", 45,17,5,50, 3);
        InHouse gpus = new InHouse(4,"Graphic Cards", 90,17,5,50, 4);
        InHouse hardDrives = new InHouse(4,"Hard Drives", 75,35,5,50, 5);
        Outsourced cases = new Outsourced(5,"Cases",17,19,2,26,"Tech Solutions");
        Outsourced powerCords = new Outsourced(6,"Power Cords",27,23,2,50,"Bradford");
        Outsourced battery = new Outsourced(7,"Batteries",34,4,1,15,"Hyper Tech");

        // Adds parts to inventory
        Inventory.addPart(lcdScreens);
        Inventory.addPart(ramCards);
        Inventory.addPart(cpus);
        Inventory.addPart(cases);
        Inventory.addPart(powerCords);
        Inventory.addPart(gpus);
        Inventory.addPart(battery);

        // Adds parts to desktop product
        Product desktop = new Product(1,"Desktop",400,20,1,10);
        desktop.addAssociatedPart(cpus);
        desktop.addAssociatedPart(cases);
        desktop.addAssociatedPart(ramCards);
        desktop.addAssociatedPart(powerCords);
        desktop.addAssociatedPart(hardDrives);
        Inventory.addProduct(desktop);

        // Adds parts to work laptop product
        Product workLaptop = new Product (2,"Work Laptop", 700, 30,1,15);
        workLaptop.addAssociatedPart(lcdScreens);
        workLaptop.addAssociatedPart(ramCards);
        workLaptop.addAssociatedPart(battery);
        workLaptop.addAssociatedPart(cpus);
        workLaptop.addAssociatedPart(hardDrives);
        Inventory.addProduct(workLaptop);

        // Adds parts to premiumlaptop product
        Product premiumLaptop = new Product(3,"Premium Laptop", 500,20,1,15);
        premiumLaptop.addAssociatedPart(gpus);
        premiumLaptop.addAssociatedPart(lcdScreens);
        premiumLaptop.addAssociatedPart(ramCards);
        premiumLaptop.addAssociatedPart(battery);
        premiumLaptop.addAssociatedPart(hardDrives);
        Inventory.addProduct(premiumLaptop);
    }

    /**
     * Entry method that launches the application and adds the applicable test data
     *
     * @param args String [] args
     */
    public static void main(String[] args) {
        addData();
        launch(args);
    }
}
