package validators;

/**
 * Created by Cosmin on 6/3/2017.
 */
public class Validators {
    public static boolean ValidateName(String name) {
        name = name.trim();
        if (name == null || name.equals(""))
            return false;
        final String pattern = "^([0-9 \\u00c0-\\u01ffa-zA-Z'\\-])+$";
        if (!name.matches(pattern)) {
            return false;
        }
        return true;
    }
    public static boolean ValidateEmail(String email) {
        email = email.trim();
        if(email == null || email.equals(""))
            return false;
        final String pattern = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$";
        if(!email.matches(pattern))
            return false;
        return true;
    }
    public static boolean ValidateURL(String url) {
        if (url == null || url.equals(""))
            return false;
        final String pattern = "^(http:\\/\\/|https:\\/\\/)?(www.)?([a-zA-Z0-9]+).[a-zA-Z0-9]*.[a-z]{3}.?([a-z]+)?$";
        if(!url.matches(pattern))
            return false;
        return true;
    }
    private static boolean isInteger(String s) {
        return isInteger(s,10);
    }

    private static boolean isInteger(String s, int radix) {
        if(s.isEmpty()) return false;
        for(int i = 0; i < s.length(); i++) {
            if(i == 0 && s.charAt(i) == '-') {
                if(s.length() == 1) return false;
                else continue;
            }
            if(Character.digit(s.charAt(i),radix) < 0) return false;
        }
        return true;
    }
    public static boolean ValidateInteger(String number) {
        return isInteger(number);
    }
    public static boolean ValidatePassword(String password) {
        return ValidateName(password);
    }
}
