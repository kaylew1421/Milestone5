package app;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;


public class ShoppingCart<T extends SalableProduct> {

    private final Map<T, Integer> lines = new LinkedHashMap<>();

  
    public void add(T product, int qty) {
        if (qty <= 0) throw new IllegalArgumentException("qty must be > 0");
        lines.merge(product, qty, Integer::sum);
    }

  
    public void remove(T product, int qty) {
        if (qty <= 0) throw new IllegalArgumentException("qty must be > 0");
        lines.computeIfPresent(product, (p, currentQty) -> {
            int left = currentQty - qty;
            return left > 0 ? left : null;
        });
    }

    public Map<T, Integer> getLines() {
        return Collections.unmodifiableMap(lines);
    }

    
    public double total() {
        return lines.entrySet().stream()
                .mapToDouble(e -> e.getKey().getPrice() * e.getValue())
                .sum();
    }

  
    public void clear() {
        lines.clear();
    }

    public boolean isEmpty() {
        return lines.isEmpty();
    }
}
