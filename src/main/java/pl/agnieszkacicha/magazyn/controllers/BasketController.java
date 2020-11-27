package pl.agnieszkacicha.magazyn.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import pl.agnieszkacicha.magazyn.database.IProductRepository;
import pl.agnieszkacicha.magazyn.model.Product;
import pl.agnieszkacicha.magazyn.session.SessionObject;

import javax.annotation.Resource;

@Controller
public class BasketController {

    @Autowired
    IProductRepository productRepository;

    @Resource
    SessionObject sessionObject;

    @RequestMapping(value = "/addToBasket/{code}", method = RequestMethod.GET)
    public String addProductToBasket(@PathVariable String code) {

        for(Product product : this.sessionObject.getBasket()) {
            if(product.getCode().equals(code)) {
                product.setPieces(product.getPieces()+1);
                return "redirect:/main";
            }
        }

        Product product = (Product) this.productRepository.getProductByCode(code).clone();
        product.setPieces(1);
        this.sessionObject.getBasket().add(product);
        return "redirect:/main";
    }

    @RequestMapping(value = "/basket", method = RequestMethod.GET)
    public String showBasket(Model model) {
        if(!this.sessionObject.isLogged()) {
            return "redirect:/login";
        }
        model.addAttribute("products", this.sessionObject.getBasket());
        model.addAttribute("user", this.sessionObject.getUser());
        double bill = 0;
        for(Product product : this.sessionObject.getBasket()) {
            bill = bill + product.getPrice() * product.getPieces();
        }
        model.addAttribute("bill", bill);
        return "basket";
    }
}

