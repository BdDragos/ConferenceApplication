package model;

/**
 * Created by Cosmin on 5/13/2017...
 */
public class Attendant {
    public Attendant() {}
    public Attendant(int idat, String username, String password) {
        this.idat = idat;
        this.username = username;
        this.password = password;
    }

    public int getIdat() {
        return idat;
    }

    public void setIdat(int idat) {
        this.idat = idat;
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

    private int idat;
    private String username;
    private String password;
    @Override
    public String toString() {
        return username;
    }
}
