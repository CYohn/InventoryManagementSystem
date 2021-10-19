package main;

import model.InHouse;
import model.Inventory;
import model.Outsourced;
import model.Product;

public class TestData {

    /**This class provides the methods to initialize the application with test data for development purposes*/

    /**Adds test data for In House parts*/
    public static void addTestInHousePartsData(){
        InHouse sprocket = new InHouse(1, "Sprocket", 21.20, 5, 1, 10, 123);
        Inventory.addPart(sprocket);
        InHouse widget = new InHouse(2, "Widget", 10.95, 3, 0, 15, 345);
        Inventory.addPart(widget);
        InHouse bobbles = new InHouse(3, "Bobbles", 1.95, 6, 0, 100,678);
        Inventory.addPart(bobbles);
    }

    /**Adds test data for products*/
    public static void addTestProductsData(){
        Product sprocketBox = new Product(4, "Sprocket Gift Pack", 21.20, 5, 1, 10);
        Inventory.addProduct(sprocketBox);
        Product widgetBasket = new Product(5, "Widget Basket", 10.95, 3, 0, 15);
        Inventory.addProduct(widgetBasket);
        Product bobbleHeads = new Product(6, "Bobble Heads", 1.95, 6, 0, 100);
        Inventory.addProduct(bobbleHeads);
    }

    /**Adds test data for Outsourced parts*/
    public static void addTestOutsourcedParts(){
        Outsourced strawberryFabric = new Outsourced(7, "Strawberry Print Fabric", 21.20, 5, 1, 10,"Fabric Warehouse" );
        Inventory.addPart(strawberryFabric);
        Outsourced flowers = new Outsourced(8, "Flowers", 10.95, 3, 0, 15, "Flowers Direct");
        Inventory.addPart(flowers);
        Outsourced ribbons = new Outsourced(9, "Ribbons", 1.95, 6, 0, 100,"Fabric Warehouse");
        Inventory.addPart(ribbons);
    }

}