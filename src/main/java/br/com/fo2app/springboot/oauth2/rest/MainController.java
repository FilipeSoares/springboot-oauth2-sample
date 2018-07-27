package br.com.fo2app.springboot.oauth2.rest;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
public class MainController {
    
    @RequestMapping("/")
    public ResponseEntity<String> index() {
        return new ResponseEntity<String>("Welcome to Rest API!", HttpStatus.OK);
    }
    
    @RequestMapping("/_api")
    public ResponseEntity<String> login() {
        return new ResponseEntity<String>("Welcome to Service Page!", HttpStatus.OK);
    }
    
}
