package app;

import java.util.Comparator;


public final class SalableProductComparators {

    private SalableProductComparators() {
       
    }

 
    public static final Comparator<SalableProduct> BY_NAME_ASC =
            Comparator.comparing(p -> p.getName().toLowerCase());


    public static final Comparator<SalableProduct> BY_NAME_DESC =
            BY_NAME_ASC.reversed();

   
    public static final Comparator<SalableProduct> BY_PRICE_ASC =
            Comparator.comparingDouble(SalableProduct::getPrice);

   
    public static final Comparator<SalableProduct> BY_PRICE_DESC =
            BY_PRICE_ASC.reversed();
}
