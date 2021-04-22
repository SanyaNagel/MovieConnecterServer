package ru.java.rush.models;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import ru.java.rush.entities.Room;
import ru.java.rush.structure.Pair;
import ru.java.rush.synchronizers.simple.SimpleSynchronizer;

import static org.junit.jupiter.api.Assertions.*;

class RoomTest {
    private Room room = new SimpleSynchronizer("code");

    @Test
    void setUser() {
        room.setUser("Name");
        assertTrue(room.getUsers().size() > 0);
        assertNotNull(room.getUsers().get(0));
    }

    @Test
    void setHash() {
        setUser();
        room.setHash(0, "Hash");
        Pair<Long, String> hashCurrent = room.getUsers().get(0).getHasIx(0);
        Assert.assertNotNull(hashCurrent.snd);
       // System.out.println(hashCurrent.snd);

    }

}