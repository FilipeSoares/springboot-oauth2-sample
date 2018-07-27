package br.com.fo2app.springboot.oauth2.configuration;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {
	
	@Autowired
	private TokenStore tokenStore;
	
	/*@Autowired
	private JwtAccessTokenConverter accessTokenConverter;*/

	@Autowired
	private AuthenticationManager authenticationManager;
	
	// To JdbcToken
	// @Autowired
	// private DataSource dataSource;
	
	// To InMemory Token
	@Value("${security.jwt.client-id}")
	private String clientId;

	@Value("${security.jwt.client-secret}")
	private String clientSecret;

	@Value("${security.jwt.grant-type}")
	private String grantType;

	@Value("${security.jwt.scope-read}")
	private String scopeRead;

	@Value("${security.jwt.scope-write}")
	private String scopeWrite = "write";

	@Value("${security.jwt.resource-ids}")
	private String resourceIds;
	
	@Value("${security.jwt.accessTokenValiditySeconds}")
	private String accessTokenValiditySeconds;
	
	@Value("${security.jwt.authorities}")
	private String[] authorities;
	
	@Override
	public void configure(final ClientDetailsServiceConfigurer configurer) throws Exception {
		// To clients in memory
		configurer
			.inMemory()
			.withClient(clientId)
			.secret(clientSecret)
			.authorizedGrantTypes("password")
		 	.scopes(scopeRead, scopeWrite)
		 	.resourceIds(resourceIds)
		 	.accessTokenValiditySeconds(Integer.valueOf(accessTokenValiditySeconds));
		 	//.authorities(authorities);
		
		// To Jdbc Clients
		// configurer.jdbc(dataSource);
	}

	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
		/*TokenEnhancerChain enhancerChain = new TokenEnhancerChain();
		enhancerChain.setTokenEnhancers(Arrays.asList(accessTokenConverter));
		
		endpoints.tokenStore(tokenStore).accessTokenConverter(accessTokenConverter)
			.tokenEnhancer(enhancerChain)
			.authenticationManager(authenticationManager);*/
		
		endpoints.tokenStore(tokenStore).authenticationManager(authenticationManager);
	}
	/*
	@Override
	public void configure(AuthorizationServerSecurityConfigurer oauthServer) throws Exception {
		oauthServer.tokenKeyAccess("isAnonymous() || hasAuthority('ROLE_TRUSTED_CLIENT')")
			.checkTokenAccess("hasAuthority('ROLE_TRUSTED_CLIENT')");
	}*/

}
