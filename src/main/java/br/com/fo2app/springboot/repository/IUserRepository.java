package br.com.fo2app.springboot.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import br.com.fo2app.springboot.model.User;

@Repository
public interface IUserRepository extends CrudRepository<User, Integer>{
	
	User findByUsername(String username);

}
