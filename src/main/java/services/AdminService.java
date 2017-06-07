package services;

import controller.AdminController;
import exceptions.ExceptionValidator;
import model.Admin;

import javax.xml.bind.ValidationException;
import java.util.List;

/**
 * Created by Dragos on 5/8/2017.
 */
public class AdminService {
    private AdminController adminController;
    public AdminService(AdminController adminController) {
        this.adminController = adminController;
    }
    public boolean login(String username, String password) {
        return adminController.login(username, password);
    }
    public void save(String username, String password) {
        adminController.save(username, password);
    }
    public List<Admin> getAll() {
        return adminController.getAll();
    }
    public void delete(String username) {
        adminController.delete(username);
    }
    public Admin findOne(String username) {
        return adminController.findOne(username);
    }
}
