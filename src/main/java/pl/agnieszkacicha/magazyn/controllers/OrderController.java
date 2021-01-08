package pl.agnieszkacicha.magazyn.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import pl.agnieszkacicha.magazyn.model.Order;
import pl.agnieszkacicha.magazyn.services.IOrderService;
import pl.agnieszkacicha.magazyn.session.SessionObject;

import javax.annotation.Resource;
import java.util.List;

@Controller
public class OrderController {

    @Autowired
    IOrderService orderService;

    @Resource
    SessionObject sessionObject;

    @RequestMapping(value = "/confirm-order", method = RequestMethod.GET)
    public String confirmOrder() {
        this.orderService.confirmOrder();
        return "redirect:/main";
    }

    @RequestMapping(value = "/orders", method = RequestMethod.GET)
    public String orders(Model model) {
        if (!this.sessionObject.isLogged()) {
            return "redirect:/login";
        }
        List<Order> orders = orderService.getOrdersForCurrentUser();
        model.addAttribute("orders", orders);
        model.addAttribute("user", this.sessionObject.getUser());
        return "orders";
    }
}
