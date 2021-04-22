package ru.java.rush.structure;


import java.util.ArrayList;

//Структура хранящая очередь пар - map очередь
public class MapQueue extends ArrayList<Pair<Long, String>>{
    private int maxSize;

    public MapQueue(int size){
        this.maxSize = size;
    }

    @Override
    public boolean add(Pair<Long, String> longStringPair) {
        if(super.size() >= maxSize){
            super.remove(0);
        }
        return super.add(longStringPair);
    }
}
