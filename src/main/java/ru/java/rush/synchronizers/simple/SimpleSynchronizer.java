package ru.java.rush.synchronizers.simple;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.Level;
import ru.java.rush.entities.UserCommands;
import ru.java.rush.models.User;
import ru.java.rush.structure.Pair;
import ru.java.rush.synchronizers.CommandController;

import java.util.ArrayList;
import java.util.Set;

public class SimpleSynchronizer extends CommandController {
    private ArrayList<User> values;

    public SimpleSynchronizer(String code) {
        super(code);
    }

    @Override
    public String syncing(int id) {
        //Проверим есть ли хотябы один одинаковый кадр за последнюю секунду
        values = new ArrayList<>(users.values());
        User firstUser = values.get(0);

        //Если у нас недостаточно хэшей т.е. прога только что запустилась
        if (firstUser.getSizeHashMap() < 20)
            return UserCommands.SET_HASH.com; //Тогда пускай докидывает хэши

        boolean synchronize = isAllUserHash();

        // Если все пользователи синхронизированы -
        // У всех нашёлся одинаковый хэш
        if (synchronize) {
            if (currentCommand.equals("Индивидуальные комманды"))
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

    // Проверка, у всех юзеров имеется хотябы один одинаковый
    // хэш за определённый интервал времени?
    public boolean isAllUserHash() {
        User firstUser = values.get(0);
        Pair<Long, String> pair1 = firstUser.getHasIx(0);
        Pair<Long, String> pairCurrent = firstUser.getHasIx(0);

        final long maxTime = 2000L;
        for (int i = 0; Math.abs(pair1.fst - pairCurrent.fst) < maxTime; ++i) {
            pairCurrent = firstUser.getHasIx(i);
            int be = 0;

            //Проверка - есть ли такой хэш в последней секунде у каждого пользователя
            for (User user : values) {
                Long hashFirst = user.getHasIx(0).fst;
                Pair<Long, String> hashCurrent = user.getHasIx(0);
                for (int j = 0; Math.abs(hashFirst - hashCurrent.fst) < maxTime; ++j) {
                    hashCurrent = user.getHasIx(j);
                    if (hashEquals(hashCurrent.snd, pairCurrent.snd)) {
                        ++be;
                        break;
                    }
                }
            }
            if (be == values.size()) { //У всех пользователей нашёлся одинаковый хэш
                return true;
            }
        }
        return false;
    }

    //функция нормированного расстояния хемминга
    private boolean hashEquals(String h1, String h2) {
        return true;
    }

    //Отображение всех хэшей пользователей для отладки
    public String displayHashUsers() {
        String response = "";
        ArrayList<User> values = new ArrayList<>(users.values());
        for (int i = users.get(0).getSizeHashMap() - 1; i >= 0; --i) {
            for (User user : values) {
                if (user.getSizeHashMap() <= i) {
                    response += user.getID() + ": null" + "\t\t\t\t" + "null\n";
                } else {
                    Pair<Long, String> hashCurrent = user.getHasIx(i);
                    response += user.getID()+":"+ hashCurrent.snd + "\t" + hashCurrent.fst + "\n";
                }
            }
            response += "\n";
        }

        LogManager.getLogger(this.getClass()).info(response);
        return response;
    }

}
