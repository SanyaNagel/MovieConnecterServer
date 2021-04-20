package ru.java.rush.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.java.rush.handlers.MainControllerHandler;

@RestController
@RequestMapping("/server")
public class MainController {
    @Autowired
    MainControllerHandler mainControllerHandler;

    @PostMapping("/createRoom/{NameAdmin}")
    public ResponseEntity<String> createRoom(@PathVariable("NameAdmin") String name, Model model){
        return mainControllerHandler.createRoom(name);
    }

    @PostMapping("/hash/{code}/{id}/{hash}")
    public ResponseEntity<String> setHash(@PathVariable("code") String code,
                          @PathVariable("id") int id,
                          @PathVariable("hash") String hash,
                          Model model){
        return new ResponseEntity<>("{\"command\" : \""+ mainControllerHandler.setHash(code,id,hash)+"\"}", HttpStatus.OK);
    }

    //Подключение к комнате и добавление пользователя в комнату
    @PostMapping("/login/{code}/{name}")
    public ResponseEntity<String> login(@PathVariable("code") String code,
                                        @PathVariable("name") String name,
                                        Model model){
        return new ResponseEntity<>("{\"id\" : \""+ mainControllerHandler.login(code,name)+"\"}", HttpStatus.OK);
    }

    //Отправка статуса готовности пользователя комнаты
    @PutMapping("/ready/{code}/{id}/{flag}")
    public ResponseEntity<String> setReady(@PathVariable("code") String code,
                                               @PathVariable("id") int id,
                                               @PathVariable("flag") boolean ready){
        mainControllerHandler.setReadyUser(code,id,ready); //Отправляем юзеру статус готовности
        return new ResponseEntity<>("",HttpStatus.OK);
    }

    //Отладка
    @PostMapping("/view/{code}")
    public ResponseEntity<String> viewHashs(@PathVariable("code") String code){
        return new ResponseEntity<>("{\"debux\" : \""+ mainControllerHandler.displayHashes(code)+"\"}",HttpStatus.OK);
    }

}
