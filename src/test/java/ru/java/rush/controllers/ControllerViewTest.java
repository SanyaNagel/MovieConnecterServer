package ru.java.rush.controllers;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ru.java.rush.models.Room;
import ru.java.rush.service.ControllerWork;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.internal.bytebuddy.matcher.ElementMatchers.is;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ControllerViewTest {

    @Autowired
    private TestRestTemplate restTemplate;

    private String numberUser = "0";
    private String hash = "11111111";

    @Autowired
    ControllerWork controllerWork;

    @Autowired
    ControllerView controllerView;

    @Test
    void creatRoom() throws URISyntaxException {
        String name = "Alex";
        String resp = "";
        HttpEntity<String> entity = new HttpEntity<String>(resp);
        ResponseEntity<String> response = restTemplate.exchange("/server/creatRoom/{name}",
                HttpMethod.POST, entity, String.class, name);

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void setHash() throws URISyntaxException {
        List<Map.Entry<String, Room>> list = new ArrayList<>(controllerWork.getRooms().entrySet());
        Map.Entry<String, Room> firstInsertedEntry = list.get(0);   //Получаем первую комнату
        String codeRoom = firstInsertedEntry.getKey();

        String resp = "";
        HttpEntity<String> entity = new HttpEntity<String>(resp);
        ResponseEntity<String> response = restTemplate.exchange("/server/hash/{codeRoom}/{numberUser}/{hash}",
                HttpMethod.POST, entity, String.class, codeRoom,numberUser,hash);

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    //Тестирует работу при участии двух пользователей
    @Test
    public void setHashUserTwo() throws URISyntaxException {
        this.creatRoom();   //Создаём комнату и регистрируем 1 пользователя
        this.login();       //Регистрируем второго пользователя
        for(int i = 0 ; i < 150; i++){
            this.setHash();
            if(numberUser.equals("0")){
                numberUser = "1";
            }else{
                numberUser = "0";
            }
        }
        this.viewHashs();
    }

    @Test
    void login() throws URISyntaxException {
        List<Map.Entry<String, Room>> list = new ArrayList<>(controllerWork.getRooms().entrySet());
        Map.Entry<String, Room> firstInsertedEntry = list.get(0);   //Получаем первую комнату
        String codeRoom = firstInsertedEntry.getKey();

        String resp = "";
        HttpEntity<String> entity = new HttpEntity<String>(resp);
        ResponseEntity<String> response = restTemplate.exchange("/server/login/{codeRoom}/TestUser",
                HttpMethod.POST, entity, String.class, codeRoom);

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void viewHashs() {
        List<Map.Entry<String, Room>> list = new ArrayList<>(controllerWork.getRooms().entrySet());
        Map.Entry<String, Room> firstInsertedEntry = list.get(0);   //Получаем первую комнату
        String codeRoom = firstInsertedEntry.getKey();

        String resp = "";
        HttpEntity<String> entity = new HttpEntity<String>(resp);
        ResponseEntity<String> response = restTemplate.exchange("/server/view/{codeRoom}",
                HttpMethod.POST, entity, String.class, codeRoom);

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
    }
}
