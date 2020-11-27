package pl.agnieszkacicha.magazyn.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import pl.agnieszkacicha.magazyn.database.IProductRepository;
import pl.agnieszkacicha.magazyn.model.Product;
import pl.agnieszkacicha.magazyn.model.User;
import pl.agnieszkacicha.magazyn.session.SessionObject;

import javax.annotation.Resource;

@Controller
public class AdminController {

    @Autowired
    IProductRepository productRepository;

    @Resource
    SessionObject sessionObject;

    @RequestMapping(value = "/addProduct", method = RequestMethod.GET)
    public String addProductForm(Model model) {
        if(!this.sessionObject.isLogged()) {
            return "redirect:/login";
        }
        model.addAttribute("user", this.sessionObject.getUser());
        model.addAttribute("info", this.sessionObject.getInfo());
        model.addAttribute("product", new Product());
        return "addProduct";
    }

    @RequestMapping(value = "/addProduct", method = RequestMethod.POST)
    public String addProduct(@ModelAttribute Product product) {
        if(!this.sessionObject.isLogged()) {
            return "redirect:/login";
        }
        Product productFromDB = this.productRepository.getProductByCode(product.getCode());

        if(productFromDB != null) {
            productFromDB.setPieces(productFromDB.getPieces() + product.getPieces());
            this.sessionObject.setInfo("Zwiększono ilość sztuk !!");
        } else {
            if(product.getName().equals("") ||
                    product.getCode().equals("") ||
                    product.getPrice() == 0.0) {
                this.sessionObject.setInfo("Uzupełnij formularz !!");
            } else {
                this.productRepository.addProduct(product);
                this.sessionObject.setInfo("Dodano nowy produkt !!");
            }
        }
        return "redirect:/addProduct";
    }

    @RequestMapping(value = "/editProduct/{code}", method = RequestMethod.GET)
    public String editProductPage(@PathVariable String code, Model model) {
        Product product = this.productRepository.getProductByCode(code);
        model.addAttribute("product", product);
        model.addAttribute("user", this.sessionObject.getUser());
        return "editProduct";
    }

    @RequestMapping(value = "/editProduct/{code}", method = RequestMethod.POST)
    public String editProduct(@ModelAttribute Product product, @PathVariable String code) {
        Product productFromDataBase = this.productRepository.getProductByCode(code);
        productFromDataBase.setPieces(product.getPieces());
        productFromDataBase.setCategory(product.getCategory());
        productFromDataBase.setPrice(product.getPrice());

        return "redirect:/main";
    }
}
