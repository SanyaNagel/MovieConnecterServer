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
        //Проверим есть ли хотябы один одинаковый кадр за последнюю секунду
        ArrayList<User> values = new ArrayList<>(users.values());
        User user1 = values.get(0);

        Pair<Long, String> pair1 = user1.getHasIx(0);
        Pair<Long, String> pairCurrent = user1.getHasIx(0);
        boolean synchroniz = false;
        // Берём первого пользователя и проверяем -
        // за последнюю секунду у всех пользователей имеется похожий хэш
        for(int i = 0; pair1.fst - pairCurrent.fst > 1000; ++i){
            pairCurrent = user1.getHasIx(i);
            int be = 0;

            //Проверка - есть ли такой хэш в последней секунде у каждого пользователя
            for(User user : values){
                Long hashFirst = user.getHasIx(0).fst;
                Pair<Long, String> hashCurrent = user.getHasIx(0);
                for(int j = 0; hashFirst - hashCurrent.fst > 1000; ++j){
                    hashCurrent = user.getHasIx(j);
                    if(hashCurrent.snd.equals(pairCurrent.snd)){
                        ++be;
                    }
                }
            }
            if(be == values.size()) { //У всех пользователей нашёлся одинаковый хэш
                synchroniz = true;
                break;
            }
        }
        // Усли все пользователи синхронизированы -
        // У всех нашёлся одинаковый хэш
        if(synchroniz){
            currentCommand = "Кидай хэш"; //для всех устанавливае команду
            return "Кидай хэш"; //То можно продолжать работу
        }

        //Иначе устанавливаем команды для каждого пользователя
        currentCommand = "Индивидуальные комманды";

        //Устанавливаем индивидуальные команды
        searchForLatecomer();

        return values.get(id).individualCommand;
    }

    // Поиск опоздавшего пользователя и
    // установка индивидуальных команд
    public void searchForLatecomer(){
        //ArrayList<User> values = new ArrayList<>(users.values());
        //for(User user : values) {

        //}

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
                return syncing(id); //Пытаемся синхронизировать

            default: return "Ошибка, данной команды не существует код: 11";
        }
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
