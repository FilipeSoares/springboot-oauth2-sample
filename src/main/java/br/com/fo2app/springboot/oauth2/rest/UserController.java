package br.com.fo2app.springboot.oauth2.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.fo2app.springboot.oauth2.model.User;
import br.com.fo2app.springboot.oauth2.repository.IUserRepository;

@RestController
@RequestMapping("/users")
public class UserController {
	
	@Autowired
	private IUserRepository repository;
	
	@GetMapping
	public ResponseEntity<List<User>> list(){
		return new ResponseEntity<List<User>>((List<User>) repository.findAll(), HttpStatus.OK);
	}

}
