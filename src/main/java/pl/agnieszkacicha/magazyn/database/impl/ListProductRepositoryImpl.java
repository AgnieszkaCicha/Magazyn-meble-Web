package pl.agnieszkacicha.magazyn.database.impl;

import org.springframework.stereotype.Component;
import pl.agnieszkacicha.magazyn.database.IProductRepository;
import pl.agnieszkacicha.magazyn.model.Product;

import java.util.ArrayList;
import java.util.List;

@Component
public class ListProductRepositoryImpl implements IProductRepository {

    private final List<Product> products = new ArrayList<>();

    public ListProductRepositoryImpl() {
        this.products.add(new Product
                ("101", "Fotel", 1, 400.0, Product.Category.FURNITURE));
        this.products.add(new Product
                ("102", "Szafka", 2, 80.0, Product.Category.FURNITURE));
        this.products.add(new Product
                ("201", "Płyta indukcyjna", 1, 1200.0, Product.Category.AGD));
        this.products.add(new Product
                ("202", "Zmywarka", 1, 1150.0, Product.Category.AGD));
    }

    @Override
    public List<Product> getAllProducts() {
        return this.products;
    }

    @Override
    public List<Product> getFurniture() {
        List<Product> furniture = new ArrayList<>();

        for (Product product : this.products) {
            if (product.getCategory() == Product.Category.FURNITURE) {
                furniture.add(product);
            }
        }
        return furniture;
    }

    @Override
    public List<Product> getAGD() {
        List<Product> AGD = new ArrayList<>();

        for (Product product : this.products) {
            if (product.getCategory() == Product.Category.AGD) {
                AGD.add(product);
            }
        }
        return AGD;
    }

    @Override
    public Product getProductByCode(String code) {
        for(Product product : this.products) {
            if(product.getCode().equals(code)) {
                return product;
            }
        }

        return null;
    }

    @Override
    public void addProduct(Product product) {
        this.products.add(product);
    }
}



