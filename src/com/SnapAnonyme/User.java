package com.SnapAnonyme;

import java.io.*;
import java.util.ArrayList;

public class User implements Serializable {

    private int id;
    private String login, password;
    public static final String FILE_PATH= "User.ser";
    private static ArrayList listIdUser = Function.ReadIds(FILE_PATH);

    //region Accesseurs
    private void setId() {
        this.id = Function.generateId(listIdUser);
        listIdUser.add(this.id);
    }

    public int getId() {
        return id;
    }

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

    public static ArrayList getListIdUser() {
        return listIdUser;
    }

//endregion

    //region Constructeurs
    public User(String login, String password) {
        this.setId();
        this.login = login;
        this.password = password;
        Function.Create(this, FILE_PATH);
    }
    //endregion

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
