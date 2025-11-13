package app;

import java.util.*;


public class InventoryManager<T extends SalableProduct> {

    private final Map<String, InventoryItem<T>> items = new HashMap<>();


    public void addProduct(T product, int quantity) {
        items.merge(
                product.getId(),
                new InventoryItem<>(product, quantity),
                (existing, incoming) -> {
                    existing.increment(incoming.getQuantity());
                    return existing;
                }
        );
    }

  
    public InventoryItem<T> getById(String productId) {
        InventoryItem<T> item = items.get(productId);
        if (item == null) {
            throw new NoSuchElementException("No product with id " + productId);
        }
        return item;
    }

   
    public Collection<InventoryItem<T>> getAllItems() {
        return Collections.unmodifiableCollection(items.values());
    }

    public List<T> getProductsSortedByNameAsc() {
        List<T> list = collectProducts();
        list.sort(SalableProductComparators.BY_NAME_ASC);
        return list;
    }

   
    public List<T> getProductsSortedByNameDesc() {
        List<T> list = collectProducts();
        list.sort(SalableProductComparators.BY_NAME_DESC);
        return list;
    }

 
    public List<T> getProductsSortedByPriceAsc() {
        List<T> list = collectProducts();
        list.sort(SalableProductComparators.BY_PRICE_ASC);
        return list;
    }

  
    public List<T> getProductsSortedByPriceDesc() {
        List<T> list = collectProducts();
        list.sort(SalableProductComparators.BY_PRICE_DESC);
        return list;
    }

    private List<T> collectProducts() {
        List<T> list = new ArrayList<>();
        for (InventoryItem<T> item : items.values()) {
            list.add(item.getProduct());
        }
        return list;
    }

   
    public boolean inStock(String productId, int qty) {
        InventoryItem<T> item = items.get(productId);
        return item != null && item.getQuantity() >= qty;
    }

   
    public void decrementStock(String productId, int qty) {
        InventoryItem<T> item = getById(productId);
        if (item.getQuantity() < qty) {
            throw new IllegalStateException("Not enough in stock");
        }
        item.decrement(qty);
    }
}
