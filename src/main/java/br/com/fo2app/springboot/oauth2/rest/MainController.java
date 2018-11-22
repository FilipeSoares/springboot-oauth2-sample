package br.com.fo2app.springboot.oauth2.rest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class MainController {
	
	@Value("${config.app.title}")
	String title;
    
    @GetMapping("/info")
    public ResponseEntity<String> index() {
        return new ResponseEntity<String>(title, HttpStatus.OK);
    }
    
}
