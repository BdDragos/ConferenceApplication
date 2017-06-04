package validators.model;

import model.DefaultUser;
import validators.IValidator;
import validators.Validators;

/**
 * Created by Cosmin on 6/3/2017.
 */
public class ValidatorDefaultUser implements IValidator<DefaultUser> {
    public boolean validate(DefaultUser defaultUser) {
        if (!Validators.ValidateName(defaultUser.getUsername()))
            return false;
        if (!Validators.ValidateName(defaultUser.getPassword()))
            return false;
        return true;
    }
}
