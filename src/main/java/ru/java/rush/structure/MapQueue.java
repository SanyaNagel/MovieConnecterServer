package ru.java.rush.structure;


import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

//Структура хранящая очередь пар - map очередь
public class MapQueue extends LinkedList<Pair<Long, String>> {
    private int maxSize;

    public MapQueue(int size){
        this.maxSize = size;
    }

    @Override
    public void addLast(Pair<Long, String> longStringPair) {
        if(super.size() >= maxSize){
            super.pollFirst();
        }
        super.addLast(longStringPair);
    }
}
