package ru.java.rush.models;


import ru.java.rush.models.structure.Pair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Room {
    private String code;

    private Map<Integer, User> users = new HashMap<Integer, User>();
    private int USER_COUNT = 0;

    public Room(String code){
        this.code = code;
    }

    public User setUser(String nameUser){
        User user = new User(nameUser,USER_COUNT);
        users.put(USER_COUNT++,user);
        return user;
    }

    public String setHash(Integer id, String hash){

        users.get(id).setHash(System.currentTimeMillis(),hash);

        return getCommand(id);
    }

    public String getCommand(int id){


        return "resume";    //Комманда продолжить
    }

    //Отображение всех хэшей пользователей для отладки
    public void displayHashUsers(){
        Set<Integer> keys = users.keySet();
        for(Integer id : keys){
            System.out.print("\t\t\t\t\t\t\t"+id);
        }
        System.out.println();

        ArrayList<User> values = new ArrayList<>(users.values());
        for(int i = 50-1; i >= 0; --i){
            for(User user : values){
                Pair<Long, String> hashCurrent = user.getHasIx(i);
                System.out.print(hashCurrent.fst + "\t" + hashCurrent.snd);
                System.out.print("\t\t\t");
            }
            System.out.println();
        }

    }

    public Map<Integer, User> getUsers() {
        return users;
    }
}
