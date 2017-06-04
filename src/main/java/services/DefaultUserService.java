package services;

import controller.DefaultUserController;
import exceptions.ExceptionValidator;
import model.DefaultUser;

import java.util.List;

/**
 * Created by Cosmin on 6/3/2017.
 */
public class DefaultUserService {
    private DefaultUserController defaultUserController;
    public DefaultUserService(DefaultUserController defaultUserController) {
        this.defaultUserController = defaultUserController;
    }
    public List<DefaultUser> getAll() {
        return defaultUserController.getAll();
    }
    public DefaultUser findOneByUsername(String username) {
        return defaultUserController.findOneByUsername(username);
    }
    public DefaultUser findOneByPassword(String password) {
        return defaultUserController.findOneByPassword(password);
    }
    public void delete(String username) {
        defaultUserController.delete(username);
    }
    public void save(String username, String password) {
        defaultUserController.save(username, password);
    }
}
