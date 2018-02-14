package br.com.fo2app.springboot.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.com.fo2app.springboot.repository.IUserRepository;

@Service
public class UserService implements UserDetailsService {
	
	@Autowired
	private IUserRepository repository; 

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return this.repository.findByUsername(username);
	}
	
}
