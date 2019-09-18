package autoTests;

import com.google.gson.annotations.SerializedName;

public class User {
    @SerializedName("login")
    public String login;
    @SerializedName("password")
    public String password;

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}