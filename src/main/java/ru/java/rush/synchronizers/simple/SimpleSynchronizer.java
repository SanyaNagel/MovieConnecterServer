package ru.java.rush.synchronizers.simple;

import ru.java.rush.entities.UserCommands;
import ru.java.rush.models.User;
import ru.java.rush.structure.Pair;
import ru.java.rush.synchronizers.CommandController;

import java.util.ArrayList;
import java.util.Set;

public class SimpleSynchronizer extends CommandController {

    public SimpleSynchronizer(String code){
        super(code);
    }

    @Override
    public String syncing(int id){
        //Проверим есть ли хотябы один одинаковый кадр за последнюю секунду
        ArrayList<User> values = new ArrayList<>(users.values());
        User user1 = values.get(0);

        //Если у нас недостаточно хэшей т.е. прога только что запустилась
        if(user1.getSizeHashMap() < 20)
            return UserCommands.SET_HASH.com; //Тогда пускай докидывает хэши

        //int sizeMap = user1.getSizeHashMap() - 1;
        Pair<Long, String> pair1 = user1.getHasIx(0);
        Pair<Long, String> pairCurrent = user1.getHasIx(0);
        boolean synchroniz = false;
        long maxTime = 4000L;

        // Берём первого пользователя и проверяем -
        // за последнюю секунду у всех пользователей имеется похожий хэш
        for(int i = 0; Math.abs(pair1.fst - pairCurrent.fst) < maxTime; ++i){
            pairCurrent = user1.getHasIx(i);
            int be = 0;

            //Проверка - есть ли такой хэш в последней секунде у каждого пользователя
            for(User user : values){
                Long hashFirst = user.getHasIx(0).fst;
                Pair<Long, String> hashCurrent = user.getHasIx(0);
                for(int j = 0; Math.abs(hashFirst - hashCurrent.fst) < maxTime; ++j){
                    hashCurrent = user.getHasIx(j);
                    if(hashCurrent.snd.equals(pairCurrent.snd)){
                        ++be;
                        break;
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

}
