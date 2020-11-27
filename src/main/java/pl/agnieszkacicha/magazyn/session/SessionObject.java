package pl.agnieszkacicha.magazyn.session;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;
import pl.agnieszkacicha.magazyn.model.Product;
import pl.agnieszkacicha.magazyn.model.User;

import java.util.ArrayList;
import java.util.List;

@Component
@SessionScope
public class SessionObject {
    private User user = null;
    private String info = null;
    private String filter = null;
    private List<Product> basket = new ArrayList<>();

    public boolean isLogged() {
        return !(this.user == null);
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getInfo() {
        String result = this.info;
        this.info = null;
        return result;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getFilter() {
        return filter;
    }

    public void setFilter(String filter) {
        this.filter = filter;
    }

    public List<Product> getBasket() {
        return basket;
    }

    public void setBasket(List<Product> basket) {
        this.basket = basket;
    }
}