package ru.java.rush.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import ru.java.rush.models.Room;
import ru.java.rush.models.User;

import java.util.HashMap;
import java.util.Map;

@Component("server")
public class Server {
    private String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789abcdefghijklmnopqrstuvxyz";
    private String port = "8080";
    private String host = "localhost";

    public void setPort(String port) {
        this.port = port;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getPort() {
        return port;
    }

    public String getHost() {
        return host;
    }

    private int ROOM_COUNT = 0;

    public Map<String, Room> getRooms() {
        return rooms;
    }

    private Map<String, Room> rooms = new HashMap<String, Room>();


    public Server() {

    }

    public ResponseEntity<String> creatRoom(String userAdmin) {
        String code = generationCodeRoom()+ROOM_COUNT;    //Рандомный код + порядковый номер комнаты
        Room room = new Room(code);
        User user = room.setUser(userAdmin);
        rooms.put(code, room);
        return new ResponseEntity<>("{\"code\" : \""+code+"\", \"id\" : \""+user.getID()+"\"}", HttpStatus.OK);
    }

    public int login(String codeRoom, String nameNice){
        return rooms.get(codeRoom).setUser(nameNice).getID();
    }

    // функция для генерации случайной строки длиной 4
    public String generationCodeRoom() {
        StringBuilder sb = new StringBuilder(4);
        for (int i = 0; i < 4; i++) {
            int index = (int) (AlphaNumericString.length() * Math.random());
            sb.append(AlphaNumericString.charAt(index));
        }
        return sb.toString();
    }

    public String setHash(String codeRoom, int id, String hash){
        return rooms.get(codeRoom).setHash(id,hash);
    }

    //Отображение всех хешей пользователей
    public void displayHashs(String codeRoom){
        rooms.get(codeRoom).displayHashUsers();
    }
}
