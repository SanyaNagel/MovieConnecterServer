package ru.java.rush.models;

import ru.java.rush.models.structure.MapQueue;
import ru.java.rush.models.structure.Pair;

public class User {
    private String name;
    private final int id;
    private final MapQueue hash;  //Структура хранящая (время, хэш)

    public String getIndividualCommand() {
        if(individualCommand.equals("Запуск")){
            individualCommand = "Кидай хэш";
            return "Запуск";
        }else{
            return individualCommand;
        }
    }

    public void setIndividualCommand(String individualCommand) {
        this.individualCommand = individualCommand;
    }

    private String individualCommand = "Остановка";    //Индивидуальная команда для пользователя

    public boolean isReady() {
        return ready;
    }

    private boolean ready = false; //Флаг готовности усатника комнаты

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

    public void setReady(boolean ready) {
        this.ready = ready;
    }

    public Pair<Long, String> getHasIx(int ix){
        return hash.get(ix);
    }

    public int getSizeHashMap(){
        return hash.size();
    }
}
