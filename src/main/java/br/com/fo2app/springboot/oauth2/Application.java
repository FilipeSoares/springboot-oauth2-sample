package br.com.fo2app.springboot.oauth2;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import br.com.fo2app.springboot.oauth2.model.User;
import br.com.fo2app.springboot.oauth2.repository.IUserRepository;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
    
    @Bean
    CommandLineRunner init(IUserRepository userRepository) {

        return args -> {
        	// Clear Data
        	userRepository.deleteAll(); 
        	
        	BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        	// Users
            userRepository.save(new User("admin", encoder.encode("password")));
            userRepository.save(new User("user", encoder.encode("password")));            

        };
		
    }
    
}
