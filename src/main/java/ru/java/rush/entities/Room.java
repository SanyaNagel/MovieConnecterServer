package ru.java.rush.entities;


import ru.java.rush.models.User;
import ru.java.rush.models.structure.Pair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public abstract class Room {
    private String code;

    protected Map<Integer, User> users = new HashMap<Integer, User>();
    protected int USER_COUNT = 0;
    protected String currentCommand = "Ожидаем готовности всех";
    public Room(String code){
        this.code = code;
    }

    public User setUser(String nameUser){
        User user = new User(nameUser,USER_COUNT);
        users.put(USER_COUNT++,user);
        return user;
    }

    public Map<Integer, User> getUsers() {
        return users;
    }

    public abstract String setHash(Integer id, String hash);
    public abstract String syncing(int id);
}
