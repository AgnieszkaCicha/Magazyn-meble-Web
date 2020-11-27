package pl.agnieszkacicha.magazyn.database;
import pl.agnieszkacicha.magazyn.model.Product;
import java.util.List;

public interface IProductRepository {

    List<Product> getAllProducts();
    List<Product> getFurniture();
    List<Product> getAGD();
    Product getProductByCode(String filter);
    void addProduct(Product product);

}
