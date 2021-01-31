package ru.java.rush.models;

import ru.java.rush.models.structure.MapQueue;
import ru.java.rush.models.structure.Pair;

public class User {
    private String name;
    private int id;
    private MapQueue hash;  //Структура хранящая (время, хэш)

    public User(String name, int number){
        this.name = name;
        this.id = number;
        hash = new MapQueue(50);
    }

    public int getID(){
        return id;
    }

    //Записываем хэш в список
    public void setHash(Long time, String hash) {
        this.hash.add(new Pair<>(time, hash));
    }

    public Pair<Long, String> getHasIx(int ix){
        return hash.get(ix);
    }
}
