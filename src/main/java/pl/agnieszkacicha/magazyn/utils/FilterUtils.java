package pl.agnieszkacicha.magazyn.utils;

import pl.agnieszkacicha.magazyn.model.Product;

import java.util.ArrayList;
import java.util.List;

public class FilterUtils {

    public static List<Product> filterProducts(List<Product> products, String filter) {
        if(filter == null) {
            return products;
        }

        List<Product> filteredProducts = new ArrayList<>();

        for(Product product : products) {
            if(product.getName().toUpperCase().contains(filter.toUpperCase())) {
                filteredProducts.add(product);
            }
        }

        return filteredProducts;
    }
}
