package ru.java.rush.synchronizers;

import ru.java.rush.entities.Room;
import ru.java.rush.entities.UserCommands;
import ru.java.rush.models.User;
import ru.java.rush.structure.Pair;

import java.util.ArrayList;

public abstract class CommandController extends Room {
    public CommandController(String codeRoom){
        super(codeRoom);
    }

    //Функция контроля синхронизации
    public abstract String syncing(int id);

    @Override
    public String setHash(Integer id, String hash){
        String command = runCommand(id);
        if(command.equals(UserCommands.SET_HASH.com))  //Если можно сохранять хэш то записываем его
            users.get(id).setHash(System.currentTimeMillis(),hash);
        return command;
    }

    // Поиск опоздавшего пользователя и
    // установка индивидуальных команд
    public void searchForLatecomer(){
        ArrayList<User> values = new ArrayList<>(users.values());
        for(User user : values) {
            user.setIndividualCommand(UserCommands.STOP.com);
        }
    }

    public String runCommand(int id){
        switch (currentCommand){
            case "Кидай хэш":
                return syncing(id);

            case "Ожидаем готовности всех":
                return waitingForReadiness(id);

            case "Запуск":
                return restart(id);

            case "Индивидуальные комманды":
                return syncing(id); //Пытаемся синхронизировать

            default: return "Ошибка, данной команды не существует код: 11";
        }
    }

    public String play(int id){
        ArrayList<User> values = new ArrayList<>(users.values());
        currentCommand = UserCommands.PLAY.com;   //Все участники готовы и им даётся команда начать передачу хэшей
        for(User user : values) {    //Устанавливаем индивидуальную команду "запуск" для каждого
            user.setIndividualCommand(UserCommands.PLAY.com);
        }
        return users.get(id).getIndividualCommand();    //Комманда - чтобы в клиенте сработал пробел
    }

    //Ожидание готовности всех для первого запуска
    public String waitingForReadiness(int id){
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
    }

    //Функция для проверки готовности всех
    //После того как кто то отвалился
    public String restart(int id){
        ArrayList<User> values2 = new ArrayList<>(users.values());
        boolean ful = true; //Если все уже запущены
        for(User user : values2) {
            if(user.isEqualsIndividualCommand(UserCommands.PLAY.com))    //Если какой то не запущен
                ful = false;
        }
        if(ful) //Если все запущены
            currentCommand = UserCommands.SET_HASH.com;
        return users.get(id).getIndividualCommand();
    }
}
