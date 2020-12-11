package pl.agnieszkacicha.magazyn.dao;

import pl.agnieszkacicha.magazyn.model.User;

public interface IUserDAO {
    User getUserByLogin(String login);
    void updateUser(User user);
    void persistUser(User user);
}
