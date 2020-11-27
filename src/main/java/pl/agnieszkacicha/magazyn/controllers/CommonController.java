package pl.agnieszkacicha.magazyn.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import pl.agnieszkacicha.magazyn.database.IProductRepository;
import pl.agnieszkacicha.magazyn.session.SessionObject;
import pl.agnieszkacicha.magazyn.utils.FilterUtils;

import javax.annotation.Resource;

@Controller
public class CommonController {

    @Autowired
    IProductRepository productRepository;

    @Resource
    SessionObject sessionObject;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String commonRedirect() {
        return "redirect:/main";
    }

    @RequestMapping(value = "/main", method = RequestMethod.GET)
    public String main(Model model, @RequestParam(defaultValue = "none") String category) {
        if(sessionObject.isLogged()) {
            switch (category) {
                case "FURNITURE":
                    model.addAttribute("products",
                            FilterUtils.filterProducts(this.productRepository.getFurniture(),
                                    this.sessionObject.getFilter()));
                    break;
                case "AGD":
                    model.addAttribute("products",
                            FilterUtils.filterProducts(this.productRepository.getAGD(),
                                    this.sessionObject.getFilter()));
                    break;

                default:
                    model.addAttribute("products",
                            FilterUtils.filterProducts(this.productRepository.getAllProducts(),
                                    this.sessionObject.getFilter()));
                    break;
            }
            model.addAttribute("user", this.sessionObject.getUser());
            model.addAttribute("filter", this.sessionObject.getFilter());
            return "main";
        } else {
            return "redirect:/login";
        }
    }

    @RequestMapping(value = "/filter", method = RequestMethod.POST)
    public String filter(@RequestParam String filter) {
        if(sessionObject.isLogged()) {
            this.sessionObject.setFilter(filter);
            return "redirect:/main";
        } else {
            return "redirect:/login";
        }
    }

    @RequestMapping(value = "/contact", method = RequestMethod.GET)
    public String contact(Model model) {
        if(sessionObject.isLogged()) {
            model.addAttribute("user", this.sessionObject.getUser());
            return "contact";
        } else {
            return "redirect:/login";
        }
    }
}