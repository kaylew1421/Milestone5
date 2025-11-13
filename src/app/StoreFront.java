package app;

import java.util.List;
import java.util.Scanner;


public class StoreFront {

    private final InventoryManager<SalableProduct> inventory;
    private final Scanner scanner = new Scanner(System.in);

    public StoreFront(InventoryManager<SalableProduct> inventory) {
        this.inventory = inventory;
    }

    
    public void runDemo() {
        GameUser user = new GameUser("u1", "PlayerOne");

        boolean running = true;
        while (running) {
            printMenu();
            String choice = scanner.nextLine().trim();
            switch (choice) {
                case "1" -> listProducts(inventory.getProductsSortedByNameAsc(), "All products (name A-Z)");
                case "2" -> listProducts(inventory.getProductsSortedByNameAsc(), "Products by name A-Z");
                case "3" -> listProducts(inventory.getProductsSortedByNameDesc(), "Products by name Z-A");
                case "4" -> listProducts(inventory.getProductsSortedByPriceAsc(), "Products by price low-high");
                case "5" -> listProducts(inventory.getProductsSortedByPriceDesc(), "Products by price high-low");
                case "6" -> handleAddToCart(user);
                case "7" -> showCart(user);
                case "8" -> handleCheckout(user);
                case "9" -> running = false;
                default -> System.out.println("Invalid choice. Try again.");
            }
        }

        System.out.println("Goodbye!");
    }

    private void printMenu() {
        System.out.println();
        System.out.println("=== Game Store ===");
        System.out.println("1) View all products");
        System.out.println("2) View products sorted by NAME (A-Z)");
        System.out.println("3) View products sorted by NAME (Z-A)");
        System.out.println("4) View products sorted by PRICE (low-high)");
        System.out.println("5) View products sorted by PRICE (high-low)");
        System.out.println("6) Add product to cart");
        System.out.println("7) View cart");
        System.out.println("8) Checkout");
        System.out.println("9) Exit");
        System.out.print("Enter choice: ");
    }

    private void listProducts(List<SalableProduct> products, String title) {
        System.out.println();
        System.out.println("=== " + title + " ===");
        for (SalableProduct p : products) {
            System.out.printf("%s | id=%s | $%.2f%n", p.getName(), p.getId(), p.getPrice());
        }
    }

    private void handleAddToCart(GameUser user) {
        System.out.print("Enter product id to add: ");
        String id = scanner.nextLine().trim();
        System.out.print("Enter quantity: ");
        int qty = Integer.parseInt(scanner.nextLine().trim());
        try {
            if (!inventory.inStock(id, qty)) {
                System.out.println("Not enough stock.");
                return;
            }
            SalableProduct p = inventory.getById(id).getProduct();
            user.getCart().add(p, qty);
            System.out.println("Added to cart.");
        } catch (Exception ex) {
            System.out.println("Error adding to cart: " + ex.getMessage());
        }
    }

    private void showCart(GameUser user) {
        System.out.println();
        System.out.println("=== Cart for " + user.getUsername() + " ===");
        if (user.getCart().isEmpty()) {
            System.out.println("Cart is empty.");
            return;
        }
        user.getCart().getLines().forEach((p, qty) -> {
            System.out.printf("%s x %d = $%.2f%n", p.getName(), qty, p.getPrice() * qty);
        });
        System.out.printf("Total: $%.2f%n", user.getCart().total());
    }

    private void handleCheckout(GameUser user) {
        if (user.getCart().isEmpty()) {
            System.out.println("Cart is empty.");
            return;
        }
        double total = user.getCart().total();
        user.getCart().getLines().forEach((p, qty) -> {
            try {
                inventory.decrementStock(p.getId(), qty);
            } catch (Exception ex) {
                System.out.println("Inventory error during checkout: " + ex.getMessage());
            }
        });
        user.getCart().clear();
        System.out.printf("Checked out! Total: $%.2f%n", total);
    }
}
