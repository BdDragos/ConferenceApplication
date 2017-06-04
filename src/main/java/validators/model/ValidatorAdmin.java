package validators.model;

import model.Admin;
import validators.IValidator;
import validators.Validators;

/**
 * Created by Cosmin on 6/3/2017.
 */
public class ValidatorAdmin implements IValidator<Admin> {
    public boolean validate(Admin admin) {
        if (admin == null)
            return false;
        if (!Validators.ValidateName(admin.getUsername()))
            return false;
        if (!Validators.ValidateName(admin.getPassword()))
            return false;
        return true;
    }
}
