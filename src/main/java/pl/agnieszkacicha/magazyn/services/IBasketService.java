package pl.agnieszkacicha.magazyn.services;

public interface IBasketService {
    void addToBasket(int productId);
    double calculateBill();
    void removeFromBasket(int productId);


}
