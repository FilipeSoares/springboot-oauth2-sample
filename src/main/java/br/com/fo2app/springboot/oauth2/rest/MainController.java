package br.com.fo2app.springboot.oauth2.rest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainController {
    
    @GetMapping("/")
    public ResponseEntity<String> index() {
        return new ResponseEntity<String>("SpringBoot Oauth2 Sample - 1.0.0", HttpStatus.OK);
    }
    
}
