package com.ya;

public class CourierCredentials {
    public CourierCredentials(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    private final String login;
    private final String password;
}
