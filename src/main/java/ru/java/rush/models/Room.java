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
    private String currentCommand = "Ожидаем готовности всех";
    public Room(String code){
        this.code = code;
    }

    public User setUser(String nameUser){
        User user = new User(nameUser,USER_COUNT);
        users.put(USER_COUNT++,user);
        return user;
    }

    public String setHash(Integer id, String hash){
        String command = getCommand(id);
        if(command == "Кидай хэш")  //Если можно сохранять хэш то записываем его
            users.get(id).setHash(System.currentTimeMillis(),hash);

        return command;
    }

    //Функция контроля синхронизации
    public String syncing(int id){

        return "Кидай хэш";
    }

    public String getCommand(int id){
        switch (currentCommand){
            case "Кидай хэш":
                return syncing(id);

            case "Ожидаем готовности всех":
                //Проверяем готовы ли все
                ArrayList<User> values = new ArrayList<>(users.values());
                boolean start = true;
                for(User user : values){
                    if (!user.isReady()){   //Если какой то "из" не готов, то мы -
                        start = false;      //Не запускаем
                        break;
                    }
                }
                if(start)
                    currentCommand = "Кидай хэш";   //Все участники готовы и им даётся команда начать передачу хэшей
                return currentCommand;

            case "Индивидуальные комманды":

                break;

            default: return "Ошибка, данной команды не существует код: 11";
        }
        return "Ошибка команды код: 22";
    }

    //Отображение всех хэшей пользователей для отладки
    public String displayHashUsers(){
        Set<Integer> keys = users.keySet();
        String respons = "";
        for(Integer id : keys){
            respons += "\t\t\t\t\t\t\t"+id;
        }

        respons +="\n";

        ArrayList<User> values = new ArrayList<>(users.values());
        for(int i = 50-1; i >= 0; --i){
            for(User user : values){
                Pair<Long, String> hashCurrent = user.getHasIx(i);
                respons += hashCurrent.fst + "\t" + hashCurrent.snd;
                respons += "\t\t\t";
            }
            respons += "\n";
        }
        System.out.println(respons);
        return respons;
    }

    public Map<Integer, User> getUsers() {
        return users;
    }
}
