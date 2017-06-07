package controller;

import exceptions.ExceptionValidator;
import model.DefaultUser;
import repository.DefaultUserRepository;
import validators.model.ValidatorDefaultUser;

import java.util.List;

/**
 * Created by Cosmin on 6/3/2017.
 */
public class DefaultUserController {
    private DefaultUserRepository defaultUserRepository;
    public DefaultUserController(DefaultUserRepository defaultUserRepository) {
        this.defaultUserRepository = defaultUserRepository;
    }
    public List<DefaultUser> getAll() {
        return defaultUserRepository.getAll();
    }
    public DefaultUser findOneByUsername(String username) {
        return defaultUserRepository.findOneByUsername(username);
    }
    public DefaultUser findOneByPassword(String password) {
        return defaultUserRepository.findOneByPassword(password);
    }
    public void delete(String username) {
        defaultUserRepository.delete(username);
    }
    public void save(String username, String password) {
        DefaultUser defaultUser = new DefaultUser(username, password);
        defaultUserRepository.save(defaultUser);
    }
}
