package pl.agnieszkacicha.magazyn.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.agnieszkacicha.magazyn.dao.IProductDAO;
import pl.agnieszkacicha.magazyn.model.Product;
import pl.agnieszkacicha.magazyn.services.IProductService;
import pl.agnieszkacicha.magazyn.session.SessionObject;
import pl.agnieszkacicha.magazyn.utils.FilterUtils;

import javax.annotation.Resource;
import java.util.List;

@Service
public class ProductServiceImpl implements  IProductService {

    @Autowired
    IProductDAO productDAO;

    @Resource
    SessionObject sessionObject;

    @Override
    public AddProductResult addProduct(Product product) {
        Product productFromDB = this.productDAO.getProductByCode((product.getCode()));
        if(productFromDB == null) {
            this.productDAO.persistProduct(product);
            return AddProductResult.PRODUCT_ADDED;
        }else{
            productFromDB.setPieces(productFromDB.getPieces() + product.getPieces());
            this.productDAO.updateProduct(productFromDB);
            return AddProductResult.PIECES_ADDED;
        }
    }

    @Override
    public Product getProductByCode(String code) {
        return this.productDAO.getProductByCode(code);
    }

    @Override
    public Product getProductById(int id) {
        return this.productDAO.getProductById(id);
    }

    @Override
    public void updateProduct(Product product) {
        this.productDAO.updateProduct(product);
    }

    @Override
    public List<Product> getProductsByCategoryWithFilter(String category) {
        switch (category) {
            case "FURNITURE":
                return FilterUtils.filterProducts(this.productDAO.getProductsByCategory(Product.Category.FURNITURE),
                        this.sessionObject.getFilter());
            case "AGD":
                return FilterUtils.filterProducts(this.productDAO.getProductsByCategory(Product.Category.AGD),
                        this.sessionObject.getFilter());
            default:
                return FilterUtils.filterProducts(this.productDAO.getAllProducts(),
                        this.sessionObject.getFilter());
        }
    }
}
