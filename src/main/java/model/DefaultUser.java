package model;

/**
 * Created by Cosmin on 5/22/2017.
 */
public class DefaultUser {
    public DefaultUser(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public DefaultUser() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    private String username, password;

    @Override
    public String toString() {
        return username;
    }
}
