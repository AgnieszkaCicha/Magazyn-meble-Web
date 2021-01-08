package pl.agnieszkacicha.magazyn.utils;

import org.junit.Assert;
import org.junit.Test;
import pl.agnieszkacicha.magazyn.model.Product;

import java.util.ArrayList;
import java.util.List;

public class FilterUtilsTest {
    @Test
    public void filterProductsWithNullPattern() {
        String pattern = null;
        List<Product> products = generateTestProductsList();

        List<Product> result = FilterUtils.filterProducts(products, pattern);

        Assert.assertSame(products, result);
    }
    

    private List<Product> generateTestProductsList() {
        List<Product> products = new ArrayList<>();
        products.add(new Product(1,
                "101",
                "Sofa",
                2,
                880.00,
                Product.Category.FURNITURE));
        products.add(new Product(2,
                "102",
                "Fotel rozkładany",
                2,
                1200.00,
                Product.Category.FURNITURE));
        products.add(new Product(3,
                "201",
                "Zmywarka",
                2,
                1200.00,
                Product.Category.AGD));
        products.add(new Product(4,
                "202",
                "Płyta indukcyjna",
                2,
                1500.00,
                Product.Category.AGD));
        return products;
    }

    private List<Product> generateResultProductsList() {
        List<Product> expectedResult = new ArrayList<>();
        expectedResult.add(new Product(2,
                "102",
                "Fotel rozkładany",
                2,
                1200.00,
                Product.Category.FURNITURE));
        return expectedResult;
    }
}
