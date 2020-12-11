package pl.agnieszkacicha.magazyn.dao;

import pl.agnieszkacicha.magazyn.model.Product;

import java.util.List;

public interface IProductDAO {
    Product getProductByCode(String code);
    void updateProduct(Product product);
    void persistProduct(Product product);
    Product getProductById(int id);
    List<Product> getProductsByCategory(Product.Category category);
    List<Product> getAllProducts();
}
