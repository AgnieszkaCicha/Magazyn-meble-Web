package pl.agnieszkacicha.magazyn.dao;

import pl.agnieszkacicha.magazyn.model.Order;

import java.util.List;

public interface IOrderDAO {
    void persistOrder(Order order);
    List<Order> getOrderByUser(int userId);


}
