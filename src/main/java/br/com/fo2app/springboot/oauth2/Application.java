package br.com.fo2app.springboot.oauth2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import br.com.fo2app.springboot.oauth2.model.User;
import br.com.fo2app.springboot.oauth2.repository.IUserRepository;

@SpringBootApplication
public class Application {

	@Autowired
	IUserRepository userRepository;

	private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

	@Component
	class LoadData implements ApplicationRunner {

		@Override
		public void run(ApplicationArguments args) throws Exception {
			userRepository.save(new User("admin", encoder.encode("password")));
			userRepository.save(new User("user", encoder.encode("password")));
		}
	}

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

}
