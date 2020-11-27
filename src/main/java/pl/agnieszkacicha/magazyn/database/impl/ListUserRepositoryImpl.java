package pl.agnieszkacicha.magazyn.database.impl;
import org.springframework.stereotype.Component;
import pl.agnieszkacicha.magazyn.database.IUserRepository;
import pl.agnieszkacicha.magazyn.model.User;

import java.util.ArrayList;
import java.util.List;


@Component
public class ListUserRepositoryImpl implements IUserRepository {

    private final List<User> userList = new ArrayList<>();

    public ListUserRepositoryImpl() {
        this.userList.add(new User("Agnieszka", "Cicha", "admin", "admin", User.Role.ADMIN));
        this.userList.add(new User("Jan", "Kowalski", "jan", "jan", User.Role.USER));
    }

    @Override
    public User authenticate(User user) {
        for(User userFromDatabase : this.userList) {
            if(userFromDatabase.getLogin().equals(user.getLogin())) {
                if(userFromDatabase.getPass().equals(user.getPass())) {
                    return userFromDatabase;
                } else {
                    return null;
                }
            }
        }

        return null;
    }

    @Override
    public User updateUserData(User user) {
        for(User userFromDB : this.userList) {
            if(userFromDB.getLogin().equals(user.getLogin())) {
                userFromDB.setName(user.getName());
                userFromDB.setSurname(user.getSurname());
                return userFromDB;
            }
        }

        return null;
    }

    @Override
    public User updateUserPass(User user) {
        for(User userFormDB : this.userList) {
            if(userFormDB.getLogin().equals(user.getLogin())) {
                userFormDB.setPass(user.getPass());
                return userFormDB;
            }
        }

        return null;
    }

    @Override
    public boolean checkIfLoginExist(String login) {
        for(User userFromDB : this.userList) {
            if(userFromDB.getLogin().equals(login)) {
                return true;
            }
        }

        return false;
    }

    @Override
    public void addUser(User user) {
        this.userList.add(user);
    }
}
