package controller;

import exceptions.ExceptionValidator;
import model.Admin;
import repository.AdminRepository;
import validators.Validators;
import validators.model.ValidatorAdmin;

import java.util.List;

/**
 * Created by Cosmin on 6/3/2017.
 */
public class AdminController {
    private AdminRepository adminRepository;
    public AdminController(AdminRepository adminRepository) {
        this.adminRepository = adminRepository;
    }
    public boolean login(String username, String password) {
        Admin admin = adminRepository.findOne(username);
        if (admin == null)
            return false;
        if (admin.getPassword().equals(password))
            return true;
        return false;
    }
    public void save(String username, String password) {
        Admin admin = new Admin(0, username, password);
        adminRepository.save(admin);
    }
    public List<Admin> getAll() {
        return adminRepository.getAll();
    }
    public void delete(String username) {
        adminRepository.delete(username);
    }
    public Admin findOne(String username) {
        return adminRepository.findOne(username);
    }
}
