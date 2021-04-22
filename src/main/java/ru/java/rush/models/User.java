package ru.java.rush.models;

import ru.java.rush.entities.UserCommands;
import ru.java.rush.structure.MapQueue;
import ru.java.rush.structure.Pair;

public class User {
    private String name;
    private final int id;
    private final MapQueue hash;  //Структура хранящая (время, хэш)
    private boolean ready = false; //Флаг готовности участника комнаты
    private String individualCommand = UserCommands.STOP.com;    //Индивидуальная команда для пользователя

    public String getIndividualCommand() {
        if(individualCommand.equals(UserCommands.PLAY.com)){
            individualCommand = UserCommands.SET_HASH.com;
            return UserCommands.PLAY.com;
        }else if(individualCommand.equals(UserCommands.STOP.com)){
            individualCommand = UserCommands.WAITING_FOR_SYNCHRONIZATION.com;   //Ожидаем сихронизации
            return UserCommands.STOP.com;
        }else{
            return individualCommand;
        }
    }

    public boolean isEqualsIndividualCommand(String command){
        return individualCommand.equals(command);
    }

    public void setIndividualCommand(String individualCommand) {
        //Если пришла команда установить "Остановка" и текущая команда равна "Ожидаем синхронизации"
        if(individualCommand.equals(UserCommands.STOP.com) &&
                this.individualCommand.equals(UserCommands.WAITING_FOR_SYNCHRONIZATION.com))
            return;                                 //То это обозначаем что остановка уже выполнена и мы продолжаем ожилать
        this.individualCommand = individualCommand; //Иначе остановка ещё не выполнялась и мы устанавливаем команду "Остановка" или иную команду
    }

    public boolean isReady() {
        return ready;
    }

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
