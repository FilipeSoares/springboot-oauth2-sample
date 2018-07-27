package br.com.fo2app.springboot.oauth2.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import br.com.fo2app.springboot.oauth2.model.User;

@Repository
public interface IUserRepository extends CrudRepository<User, Long>{
	
	User findByUsername(String username);

}
