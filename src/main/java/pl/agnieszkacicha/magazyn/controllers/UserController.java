package pl.agnieszkacicha.magazyn.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import pl.agnieszkacicha.magazyn.database.IUserRepository;
import pl.agnieszkacicha.magazyn.model.User;
import pl.agnieszkacicha.magazyn.model.view.ChangePassData;
import pl.agnieszkacicha.magazyn.model.view.UserRegistrationData;
import pl.agnieszkacicha.magazyn.session.SessionObject;

import javax.annotation.Resource;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Controller
public class UserController {

    @Autowired
    IUserRepository userRepository;

    @Resource
    SessionObject sessionObject;

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String loginForm(Model model) {
        model.addAttribute("userModel", new User());
        model.addAttribute("info", this.sessionObject.getInfo());
        return "login";
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String authentication(@ModelAttribute User user) {

        Pattern regexPattern = Pattern.compile(".{3}.*");
        Matcher loginMatcher = regexPattern.matcher(user.getLogin());
        Matcher passMatcher = regexPattern.matcher(user.getPass());

        if(!loginMatcher.matches() || !passMatcher.matches()) {
            this.sessionObject.setInfo("Nieprawidłowe dane !!");
            return "redirect:/login";
        }

        this.sessionObject.setUser(this.userRepository.authenticate(user));

        if(this.sessionObject.getUser() != null) {
            return "redirect:/main";
        } else {
            this.sessionObject.setInfo("Nieprawidłowe dane !!");
            return "redirect:/login";
        }
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logout() {
        this.sessionObject.setUser(null);
        return "redirect:/login";
    }

    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    public String edit(Model model) {
        if(this.sessionObject.isLogged()) {
            model.addAttribute("user", this.sessionObject.getUser());
            model.addAttribute("passModel", new ChangePassData());
            model.addAttribute("info", this.sessionObject.getInfo());
            return "edit";
        } else {
            return "redirect:/login";
        }
    }

    @RequestMapping(value = "/changeData", method = RequestMethod.POST)
    public String changeData(@ModelAttribute User user) {

        Pattern regexPattern = Pattern.compile("[A-Z]{1}[A-Za-z]*");
        Matcher nameMatcher = regexPattern.matcher(user.getName());
        Matcher surnameMatcher = regexPattern.matcher(user.getSurname());

        if(!nameMatcher.matches() || !surnameMatcher.matches()) {
            return "redirect:/edit";
        }

        user.setLogin(this.sessionObject.getUser().getLogin());
        User updatedUser = this.userRepository.updateUserData(user);
        this.sessionObject.setUser(updatedUser);
        return "redirect:/edit";
    }

    @RequestMapping(value = "/changePass", method = RequestMethod.POST)
    public String changePass(@ModelAttribute ChangePassData changePassData, Model model) {

        Pattern regexPattern = Pattern.compile(".{3}.*");
        Matcher currentPassMatcher = regexPattern.matcher(changePassData.getPass());
        Matcher newPassMatcher = regexPattern.matcher(changePassData.getNewPass());

        if(!changePassData.getNewPass().equals(changePassData.getRepeatedNewPass())) {
            this.sessionObject.setInfo("Nieprawidłowo powtórzone hasło !!");
            return "redirect:/edit";
        }

        User user = new User();
        user.setPass(changePassData.getPass());
        user.setLogin(this.sessionObject.getUser().getLogin());

        User authenticatedUser = this.userRepository.authenticate(user);

        if(authenticatedUser == null || !currentPassMatcher.matches() || !newPassMatcher.matches()) {
            this.sessionObject.setInfo("Nieprawidłowe hasło !!");
            return "redirect:/edit";
        }

        user.setPass(changePassData.getNewPass());
        User updatedUser = this.userRepository.updateUserPass(user);
        this.sessionObject.setUser(updatedUser);

        return "redirect:/edit";
    }

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public String register(Model model) {
        model.addAttribute("registerModel", new UserRegistrationData());
        model.addAttribute("info", this.sessionObject.getInfo());
        return "register";
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String processRegister(@ModelAttribute UserRegistrationData userRegistrationData) {
        if(!userRegistrationData.getPass().equals(userRegistrationData.getRepeatedPass())) {
            this.sessionObject.setInfo("Nieprawidłowo powtórzone hasła !!");
            return "redirect:/register";
        }

        boolean checkResult = this.userRepository.checkIfLoginExist(userRegistrationData.getLogin());

        if(checkResult) {
            this.sessionObject.setInfo("Login zajęty !!");
            return "redirect:/register";
        }

        User user = new User(userRegistrationData.getName(),
                userRegistrationData.getSurname(),
                userRegistrationData.getLogin(),
                userRegistrationData.getPass(),
                User.Role.USER);

        this.userRepository.addUser(user);

        this.sessionObject.setInfo("Rejestracja udana !!");
        return "redirect:/login";
    }
}