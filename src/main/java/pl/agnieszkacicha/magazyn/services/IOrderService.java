package pl.agnieszkacicha.magazyn.services;

import pl.agnieszkacicha.magazyn.model.Order;

import java.util.List;

public interface IOrderService {
    void confirmOrder();
    List<Order> getOrdersForCurrentUser();
}
