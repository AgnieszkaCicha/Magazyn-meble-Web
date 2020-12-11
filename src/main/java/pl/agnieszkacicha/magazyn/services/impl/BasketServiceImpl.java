package pl.agnieszkacicha.magazyn.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.agnieszkacicha.magazyn.dao.IProductDAO;
import pl.agnieszkacicha.magazyn.model.Product;
import pl.agnieszkacicha.magazyn.services.IBasketService;
import pl.agnieszkacicha.magazyn.services.IProductService;
import pl.agnieszkacicha.magazyn.session.SessionObject;

import javax.annotation.Resource;

@Service
public class BasketServiceImpl implements IBasketService {

    @Resource
    SessionObject sessionObject;

    @Autowired
    IProductDAO productDAO;

    @Override
    public void addToBasket(int productId) {
        for(Product product : this.sessionObject.getBasket()) {
            if(product.getId() == productId) {
                product.setPieces(product.getPieces()+1);
            }
        }

        Product product = this.productDAO.getProductById(productId);
        product.setPieces(1);
        this.sessionObject.getBasket().add(product);
    }

    @Override
    public double calculateBill() {
        double bill = 0;
        for(Product product : this.sessionObject.getBasket()) {
            bill = bill + product.getPrice() * product.getPieces();
        }
        return bill;
    }
}
