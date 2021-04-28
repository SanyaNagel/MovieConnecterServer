package ru.java.rush.controllers;

import org.apache.logging.log4j.LogManager;
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
        LogManager.getRootLogger().debug("������ ������ �� �������� ������� "+name);
        return mainControllerHandler.createRoom(name);
    }

    @PostMapping("/hash/{code}/{id}/{hash}")
    public ResponseEntity<String> setHash(@PathVariable("code") String code,
                          @PathVariable("id") int id,
                          @PathVariable("hash") String hash,
                          Model model){
        LogManager.getRootLogger().debug("������ ��� "+hash+" �� ������������: "+id+" �������: "+ code);
        return new ResponseEntity<>("{\"command\" : \""+ mainControllerHandler.setHash(code,id,hash)+"\"}", HttpStatus.OK);
    }

    //����������� � ������� � ���������� ������������ � �������
    @PostMapping("/login/{code}/{name}")
    public ResponseEntity<String> login(@PathVariable("code") String code,
                                        @PathVariable("name") String name,
                                        Model model){
        LogManager.getRootLogger().debug("������ ������ �� ���������� ������������ "+name+" � �������:"+code);
        return new ResponseEntity<>("{\"id\" : \""+ mainControllerHandler.login(code,name)+"\"}", HttpStatus.OK);
    }

    //�������� ������� ���������� ������������ �������
    @PutMapping("/ready/{code}/{id}/{flag}")
    public ResponseEntity<String> setReady(@PathVariable("code") String code,
                                               @PathVariable("id") int id,
                                               @PathVariable("flag") boolean ready){
        LogManager.getRootLogger().debug("������ �� ��������� ������ ���������� id:"+id+" code:"+code+" flag:"+ready);
        mainControllerHandler.setReadyUser(code,id,ready); //���������� ����� ������ ����������
        return new ResponseEntity<>("",HttpStatus.OK);
    }

    //�������
    @PostMapping("/view/{code}")
    public ResponseEntity<String> viewHashs(@PathVariable("code") String code){
        LogManager.getRootLogger().debug("������ ������ �� ����������� ����� ��� �������");
        return new ResponseEntity<>("{\"debux\" : \""+ mainControllerHandler.displayHashes(code)+"\"}",HttpStatus.OK);
    }

}
