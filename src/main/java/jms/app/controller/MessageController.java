package jms.app.controller;

import jms.app.service.DispatcherService;
import jms.app.service.DummyDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MessageController {

    private final DispatcherService dispatcherService;
    private final DummyDataService dummyDataService;


    public MessageController(DispatcherService dispatcherService, DummyDataService dummyDataService) {
        this.dispatcherService = dispatcherService;
        this.dummyDataService = dummyDataService;
    }


    @PostMapping(value="/send")
    public ResponseEntity<String> send(@RequestBody String message){
        dispatcherService.sendMessage(message);
        return new ResponseEntity<>("Message : " + message, HttpStatus.OK);
    }

    @PostMapping(value = "/generate")
    public ResponseEntity<String> generate() {
        dummyDataService.generateAndStoreDummyData();
        dummyDataService.generateAndStoreDummySales();
        return new ResponseEntity<>("Generating Data ", HttpStatus.OK);
    }

}
