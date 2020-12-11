package pl.agnieszkacicha.magazyn.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import pl.agnieszkacicha.magazyn.services.IBasketService;
import pl.agnieszkacicha.magazyn.session.SessionObject;

import javax.annotation.Resource;

@Controller
public class BasketController {

    @Autowired
    IBasketService basketService;

    @Resource
    SessionObject sessionObject;

    @RequestMapping(value = "/addToBasket/{id}", method = RequestMethod.GET)
    public String addProductToBasket(@PathVariable int id) {
        if(!this.sessionObject.isLogged()) {
            return "redirect:/login";
        }
        this.basketService.addToBasket(id);
        return "redirect:/main";
    }

    @RequestMapping(value = "/basket", method = RequestMethod.GET)
    public String showBasket(Model model) {
        if(!this.sessionObject.isLogged()) {
            return "redirect:/login";
        }
        model.addAttribute("products", this.sessionObject.getBasket());
        model.addAttribute("user", this.sessionObject.getUser());
        model.addAttribute("bill", basketService.calculateBill());
        return "basket";
    }
}

