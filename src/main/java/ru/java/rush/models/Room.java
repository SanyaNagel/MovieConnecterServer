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

        //Если у нас недостаточно хэшей т.е. прога только что запустилась
        if(user1.getSizeHashMap() < 20)
            return "Кидай хэш"; //Тогда пускай докидывает хэши

        //int sizeMap = user1.getSizeHashMap() - 1;
        Pair<Long, String> pair1 = user1.getHasIx(0);
        Pair<Long, String> pairCurrent = user1.getHasIx(0);
        boolean synchroniz = false;
        Long maxTime = 3000l;

        // Берём первого пользователя и проверяем -
        // за последнюю секунду у всех пользователей имеется похожий хэш
        for(int i = 0; Math.abs(pair1.fst - pairCurrent.fst) > maxTime; ++i){
            pairCurrent = user1.getHasIx(i);
            int be = 0;

            //Проверка - есть ли такой хэш в последней секунде у каждого пользователя
            for(User user : values){
                Long hashFirst = user.getHasIx(0).fst;
                Pair<Long, String> hashCurrent = user.getHasIx(0);
                for(int j = 0; Math.abs(hashFirst - hashCurrent.fst) > maxTime; ++j){
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
            if(currentCommand.equals("Индивидуальные комманды"))
                play(id);

            currentCommand = "Кидай хэш"; //для всех устанавливае команду
            return "Кидай хэш"; //То можно продолжать работу
        }

        //Иначе устанавливаем команды для каждого пользователя
        currentCommand = "Индивидуальные комманды";

        //Устанавливаем индивидуальные команды
        searchForLatecomer();

        return values.get(id).getIndividualCommand();
    }

    // Поиск опоздавшего пользователя и
    // установка индивидуальных команд
    public void searchForLatecomer(){
        ArrayList<User> values = new ArrayList<>(users.values());
        for(User user : values) {
            user.setIndividualCommand("Остановка");
        }

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
                if(start) {
                    return play(id);
                }
                return currentCommand;

            case "Запуск":
                ArrayList<User> values2 = new ArrayList<>(users.values());
                boolean ful = true; //Если все уже запущены
                for(User user : values2) {
                    if(user.isEqualsIndividualCommand("Запуск"))    //Если какой то не запущен
                        ful = false;
                }
                if(ful) //Если все запущены
                    currentCommand = "Кидай хэш";
                return users.get(id).getIndividualCommand();

            case "Индивидуальные комманды":
                return syncing(id); //Пытаемся синхронизировать

            default: return "Ошибка, данной команды не существует код: 11";
        }
    }

    public String play(int id){
        ArrayList<User> values = new ArrayList<>(users.values());
        currentCommand = "Запуск";   //Все участники готовы и им даётся команда начать передачу хэшей
        for(User user : values) {    //Устанавливаем индивидуальную команду "запуск" для каждого
            user.setIndividualCommand("Запуск");
        }
        return users.get(id).getIndividualCommand();    //Комманда - чтобы в клиенте сработал пробел

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
        for(int i = users.get(0).getSizeHashMap()-1; i >= 0; --i){
            for(User user : values){
                if(user.getSizeHashMap()<= i){
                    respons += "null" + "\t\t\t\t" + "null";
                    respons += "\t\t\t";
                }else{
                    Pair<Long, String> hashCurrent = user.getHasIx(i);
                    respons += hashCurrent.fst + "\t" + hashCurrent.snd;
                    respons += "\t\t\t";
                }
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
