package br.com.fo2app.springboot.oauth2.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.com.fo2app.springboot.oauth2.model.User;
import br.com.fo2app.springboot.oauth2.repository.IUserRepository;

@Service("userService")
public class UserService implements UserDetailsService {
	
	@Autowired
	private IUserRepository repository; 

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		User u = this.repository.findByUsername(username);
		
		if (u==null) {
			throw new UsernameNotFoundException("Invalid user data");
		}
		
		return new org.springframework.security.core.userdetails.User(u.getUsername(), u.getPassword(), getGrants());
	}
	
	private List<SimpleGrantedAuthority> getGrants(){
		
		List<SimpleGrantedAuthority> grants = new ArrayList<SimpleGrantedAuthority>();
		
		grants.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
		grants.add(new SimpleGrantedAuthority("ROLE_USER"));
		
		return grants;
		
	}
	
}
