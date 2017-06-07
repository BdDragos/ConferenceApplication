package validators;

/**
 * Created by Cosmin on 6/3/2017.
 */
public interface IValidator<T> {
    public boolean validate(T what);
}
