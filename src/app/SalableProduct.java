package app;


public interface SalableProduct extends Comparable<SalableProduct> {

   
    String getId();

  
    String getName();

    double getPrice();

  
    @Override
    default int compareTo(SalableProduct other) {
        int byName = this.getName().compareToIgnoreCase(other.getName());
        if (byName != 0) {
            return byName;
        }
        return Double.compare(this.getPrice(), other.getPrice());
    }
}
