package ru.java.rush.models;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import ru.java.rush.structure.Pair;

class UserTest {
    private User user = new User("Name",0);

    @Test
    void getID() {
       // System.out.println(user.getID());
        Assert.assertNotNull(user.getID());
        Assert.assertTrue(user.getID() >= 0);
    }

    @Test
    void setHash() {
        for (int i = 0; i < 150; ++i){
            user.setHash(333L,String.valueOf(i));
        }
        for(int i = 0; i < 50;++i){
            Pair<Long, String> hashCurrent = user.getHasIx(i);
            //System.out.println(hashCurrent.snd);
            Assert.assertNotNull(hashCurrent.snd); //Проверяем все ли элементы в очереди заполнены
        }
    }

    @Test
    void getHasIx() {
    }
}