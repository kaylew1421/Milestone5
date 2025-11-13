package app;


public class InventoryItem<T extends SalableProduct> {

    private final T product;
    private int quantity;

    
    public InventoryItem(T product, int quantity) {
        this.product = product;
        this.quantity = Math.max(0, quantity);
    }

   
    public T getProduct() {
        return product;
    }

  
    public int getQuantity() {
        return quantity;
    }

   
    public void increment(int amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("amount must be >= 0");
        }
        quantity += amount;
    }

 
    public void decrement(int amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("amount must be >= 0");
        }
        if (amount > quantity) {
            throw new IllegalArgumentException("cannot decrement more than in stock");
        }
        quantity -= amount;
    }
}
