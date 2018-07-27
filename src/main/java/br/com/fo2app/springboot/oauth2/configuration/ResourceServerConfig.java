package br.com.fo2app.springboot.oauth2.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

	/*@Autowired
	private ResourceServerTokenServices tokenServices;*/

	@Value("${security.jwt.resource-ids}")
	private String resourceId;

	@Override
	public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
		resources
			.resourceId(resourceId)
			.stateless(false);
			// .tokenServices(tokenServices);
	}

	@Override
	public void configure(HttpSecurity http) throws Exception {
		http
			.anonymous().disable()
			.authorizeRequests()
			.antMatchers("/_api/**").authenticated()
			.and().exceptionHandling().accessDeniedHandler(new OAuth2AccessDeniedHandler());
		
		http.headers().frameOptions().disable();
			/*.requestMatchers().and().authorizeRequests().antMatchers("/").permitAll().antMatchers("/**")
				.authenticated();*/
	}

}