package br.com.fo2app.springboot.oauth2.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;

@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {
	
	@Autowired
	private TokenStore tokenStore;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Value("${security.client-id}")
	private String clientId;

	@Value("${security.client-secret}")
	private String clientSecret;

	@Value("${security.grant-type}")
	private String grantType;

	@Value("${security.scope-read}")
	private String scopeRead;

	@Value("${security.scope-write}")
	private String scopeWrite = "write";

	@Value("${security.resource-ids}")
	private String resourceIds;
	
	@Value("${security.accessTokenValiditySeconds}")
	private String accessTokenValiditySeconds;
	
	@Override
	public void configure(final ClientDetailsServiceConfigurer configurer) throws Exception {
		configurer
			.inMemory()
			.withClient(clientId)
			.secret(clientSecret)
			.authorizedGrantTypes(grantType)
		 	.scopes(scopeRead, scopeWrite)
		 	.resourceIds(resourceIds)
		 	.accessTokenValiditySeconds(Integer.valueOf(accessTokenValiditySeconds));		 	
	}

	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
		endpoints
			.tokenStore(tokenStore)
			.authenticationManager(authenticationManager);
	}	

}
