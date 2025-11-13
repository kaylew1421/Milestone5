package app;


public class GameUser {

    private final String id;
    private final String username;
    private final ShoppingCart<SalableProduct> cart = new ShoppingCart<>();

    public GameUser(String id, String username) {
        this.id = id;
        this.username = username;
    }

    public String getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public ShoppingCart<SalableProduct> getCart() {
        return cart;
    }
}
