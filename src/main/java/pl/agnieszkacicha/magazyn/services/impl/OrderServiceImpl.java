package pl.agnieszkacicha.magazyn.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import pl.agnieszkacicha.magazyn.dao.IOrderDAO;
import pl.agnieszkacicha.magazyn.dao.IProductDAO;
import pl.agnieszkacicha.magazyn.model.Order;
import pl.agnieszkacicha.magazyn.model.OrderPosition;
import pl.agnieszkacicha.magazyn.model.Product;
import pl.agnieszkacicha.magazyn.services.IOrderService;
import pl.agnieszkacicha.magazyn.session.SessionObject;

import javax.annotation.Resource;
import java.util.List;

@Service
public class OrderServiceImpl implements IOrderService {

    @Resource
    SessionObject sessionObject;

    @Autowired
    IProductDAO productDAO;

    @Autowired
    IOrderDAO orderDAO;

    @Override
    public void confirmOrder() {

        List<Product> orderedProducts = this.sessionObject.getBasket();

        for(Product productFromBasket : orderedProducts) {
            Product productFromDB = this.productDAO.getProductById(productFromBasket.getId());
            if(productFromDB.getPieces() < productFromBasket.getPieces()) {
                return;
            }
        }

        Order order = new Order();
        order.setUser(this.sessionObject.getUser());

        double bill = 0;
        for (Product product : orderedProducts) {
            bill = bill + product.getPrice() * product.getPieces();
            }
        order.setPrice(bill);
        order.setStatus(Order.Status.ORDERED);
        for (Product product : orderedProducts) {
            OrderPosition orderPosition = new OrderPosition();
            orderPosition.setPieces(product.getPieces());
            orderPosition.setOrder(order);
            orderPosition.setProduct(product);

            order.getPositions().add(orderPosition);
        }

        this.orderDAO.persistOrder(order);

        for(Product product : orderedProducts) {
            Product productFromDB = this.productDAO.getProductById(product.getId());
            productFromDB.setPieces(productFromDB.getPieces() - product.getPieces());
            this.productDAO.updateProduct(productFromDB);
        }

        this.sessionObject.getBasket().clear();
    }

    @Override
    public List<Order> getOrdersForCurrentUser() {
        return this.orderDAO.getOrderByUser(this.sessionObject.getUser().getId());
    }

}
