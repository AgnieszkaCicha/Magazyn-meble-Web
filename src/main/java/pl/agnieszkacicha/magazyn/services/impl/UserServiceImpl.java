package pl.agnieszkacicha.magazyn.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.agnieszkacicha.magazyn.dao.IUserDAO;
import pl.agnieszkacicha.magazyn.model.User;
import pl.agnieszkacicha.magazyn.model.view.ChangePassData;
import pl.agnieszkacicha.magazyn.model.view.UserRegistrationData;
import pl.agnieszkacicha.magazyn.services.IUserService;
import pl.agnieszkacicha.magazyn.session.SessionObject;

import javax.annotation.Resource;

@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    IUserDAO userDAO;

    @Resource
    SessionObject sessionObject;

    @Override
    public User authenticate(User user) {
        User userFromDatabase = this.userDAO.getUserByLogin(user.getLogin());
        if (userFromDatabase != null && userFromDatabase.getPass().equals(user.getPass())) {
            return userFromDatabase;
        }
        return null;
    }

    @Override
    public User updateUserData(User user) {
        User currentUser = this.sessionObject.getUser();
        currentUser.setName(user.getName());
        currentUser.setSurname(user.getSurname());
        this.userDAO.updateUser(currentUser);
        return currentUser;
    }

    @Override
    public User updateUserPass(ChangePassData changePassData) {
        User user = new User();
        user.setLogin(this.sessionObject.getUser().getLogin());
        user.setPass(changePassData.getPass());
        User authenticatedUser = this.authenticate(user);
        if(authenticatedUser == null) {
            return null;
        }

        authenticatedUser.setPass(changePassData.getNewPass());

        this.userDAO.updateUser(authenticatedUser);

        return authenticatedUser;
    }

    @Override
    public boolean registerUser(UserRegistrationData userRegistrationData) {

        User userFromDatabase = this.userDAO.getUserByLogin(userRegistrationData.getLogin());
        if(userFromDatabase != null) {
            return false;
        }

        User user = new User();
        user.setName(userRegistrationData.getName());
        user.setSurname(userRegistrationData.getSurname());
        user.setLogin(userRegistrationData.getLogin());
        user.setPass(userRegistrationData.getPass());
        user.setRole(User.Role.USER);

        this.userDAO.persistUser(user);
        return true;

    }
}