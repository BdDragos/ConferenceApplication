package exceptions;

/**
 * Created by Cosmin on 6/3/2017.
 */
public class ExceptionValidator extends Exception {
    public ExceptionValidator(String message) {
        super(message);
    }
    public String getMessage() {
        return super.getMessage();
    }
}