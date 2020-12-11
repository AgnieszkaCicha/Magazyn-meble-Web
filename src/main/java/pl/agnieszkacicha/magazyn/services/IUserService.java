package pl.agnieszkacicha.magazyn.services;

import pl.agnieszkacicha.magazyn.model.User;
import pl.agnieszkacicha.magazyn.model.view.ChangePassData;
import pl.agnieszkacicha.magazyn.model.view.UserRegistrationData;

public interface IUserService {
    User authenticate (User user);
    User updateUserData(User user);
    User updateUserPass(ChangePassData changePassData);
    boolean registerUser (UserRegistrationData userRegistrationData);
}
