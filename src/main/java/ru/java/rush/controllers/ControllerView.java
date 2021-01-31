package ru.java.rush.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.java.rush.service.Server;

@RestController
@RequestMapping("/server")
public class ControllerView {
    @Autowired
    Server server;

    @PostMapping("/creatRoom/{NameAdmin}")
    public ResponseEntity<String> creatRoom(@PathVariable("NameAdmin") String name, Model model){
        return server.creatRoom(name);
    }

    @PostMapping("/hash/{code}/{id}/{hash}")
    public ResponseEntity<String> setHash(@PathVariable("code") String code,
                          @PathVariable("id") int id,
                          @PathVariable("hash") String hash,
                          Model model){
        return  new ResponseEntity<>("{\"command\" : \""+server.setHash(code,id,hash)+"\"}", HttpStatus.OK);
    }

    //Подключение к комнате и добавление пользователя в комнату
    @PostMapping("/login/{code}/{name}")
    public ResponseEntity<String> login(@PathVariable("code") String code,
                                        @PathVariable("name") String name,
                                        Model model){
        return  new ResponseEntity<>("{\"id\" : \""+server.login(code,name)+"\"}", HttpStatus.OK);
    }

    //Отоладка
    @PostMapping("/view/{code}")
    public ResponseEntity<String> viewHashs(@PathVariable("code") String code){
        server.displayHashs(code);
        return  new ResponseEntity<>("", HttpStatus.OK);
    }

}
