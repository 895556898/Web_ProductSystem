package main.java.main.dao;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class user {
    protected int id;

    protected String userName;

    protected String password;

    public user(int id, String userName, String password) {
        this.id = id;
        this.userName = userName;
        this.password = password;
    }
}
