package br.com.fo2app.springboot.oauth2.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.error.OAuth2AccessDeniedHandler;
import org.springframework.security.oauth2.provider.token.ResourceServerTokenServices;

@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

	@Autowired
	private ResourceServerTokenServices tokenServices;

	private static final String RESOURCE_ID = "br.com.fo2app.springboot.oauth2";

	@Override
	public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
		resources
			.resourceId(RESOURCE_ID)
			.stateless(false)
			.tokenServices(tokenServices);
	}

	@Override
	public void configure(HttpSecurity http) throws Exception {
		http
			.requestMatchers().and().authorizeRequests()
			.antMatchers("/api-info", "/info", "/actuator/**", "/swagger-ui.html", "/v2/api-docs/**", "/webjars/**", "/swagger-resources/**").permitAll()
			.anyRequest().authenticated().and()
			.exceptionHandling().accessDeniedHandler(new OAuth2AccessDeniedHandler());
		
		http.headers().frameOptions().disable();
	}

}