package app;

public class App {

    public static void main(String[] args) {
        InventoryManager<SalableProduct> manager = new InventoryManager<>();

       
        manager.addProduct(new Product("g1", "Elder Scrolls", "GAME", 59.99), 10);
        manager.addProduct(new Product("g2", "Mario Odyssey", "GAME", 49.99), 5);
        manager.addProduct(new Product("c1", "NextGen Console", "CONSOLE", 399.99), 3);
        manager.addProduct(new Product("a1", "Wireless Controller", "ACCESSORY", 59.99), 15);
        manager.addProduct(new Product("a2", "Headset", "ACCESSORY", 89.99), 8);

        StoreFront storeFront = new StoreFront(manager);
        storeFront.runDemo();
    }
}
