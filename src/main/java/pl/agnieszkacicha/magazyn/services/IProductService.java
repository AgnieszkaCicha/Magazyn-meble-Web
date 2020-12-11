package pl.agnieszkacicha.magazyn.services;

import pl.agnieszkacicha.magazyn.model.Product;

import java.util.List;

public interface IProductService {
    AddProductResult addProduct(Product product);
    Product getProductByCode(String code);
    Product getProductById(int id);
    void updateProduct(Product product);
    List<Product> getProductsByCategoryWithFilter(String category);

    enum AddProductResult {
        PIECES_ADDED,
        PRODUCT_ADDED
    }
}
