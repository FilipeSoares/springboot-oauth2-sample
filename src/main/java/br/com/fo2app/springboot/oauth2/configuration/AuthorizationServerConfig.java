package br.com.fo2app.springboot.oauth2.configuration;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {
	
	@Autowired
	private TokenStore tokenStore;
	
	@Autowired
	private JwtAccessTokenConverter accessTokenConverter;

	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	@Qualifier("userService")
	private UserDetailsService userDetailsService;
	
	static final String INTERNAL_CLIENT_ID = "internal-client-id";
	static final String EXTERNAL_CLIENT_ID = "external-client-id";
	
	static final String INTERNAL_CLIENT_SECRET = "$2a$10$ctjezj79XwFWuDx4UMYKguTMYjbv9zF6HaZrBayHLd.H7m0.EDos6";
	
	static final String GRANT_TYPE_PASSWORD = "password";
	static final String AUTHORIZATION_CODE = "authorization_code";
    static final String REFRESH_TOKEN = "refresh_token";
    static final String IMPLICIT = "implicit";
	static final String SCOPE_READ = "read";
	static final String SCOPE_WRITE = "write";
	static final String RESOURCE_ID = "br.com.fo2app.springboot.oauth2";
	static final int TOKEN_VALIDATION = 3600;
	static final String[] AUTHORITIES = {"ROLE_TRUSTED_CLIENT"};
	
	@Override
	public void configure(final ClientDetailsServiceConfigurer configurer) throws Exception {
		configurer
			.inMemory()
			.withClient(INTERNAL_CLIENT_ID)
			.secret(INTERNAL_CLIENT_SECRET)
			.authorizedGrantTypes(AUTHORIZATION_CODE, REFRESH_TOKEN, IMPLICIT, GRANT_TYPE_PASSWORD)
		 	.scopes(SCOPE_READ, SCOPE_WRITE)
		 	.resourceIds(RESOURCE_ID)
		 	.accessTokenValiditySeconds(TOKEN_VALIDATION)
		 	.authorities(AUTHORITIES)
		 	.autoApprove(true)
		 	.redirectUris("http://localhost:4200")
		 	.and()
		 	.withClient(EXTERNAL_CLIENT_ID)
			.authorizedGrantTypes(AUTHORIZATION_CODE, IMPLICIT)
		 	.scopes(SCOPE_READ, SCOPE_WRITE)
		 	.autoApprove(true)
		 	.redirectUris("http://localhost:4200");
	}

	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
		TokenEnhancerChain enhancerChain = new TokenEnhancerChain();
		enhancerChain.setTokenEnhancers(Arrays.asList(accessTokenConverter));
		endpoints.tokenStore(tokenStore).accessTokenConverter(accessTokenConverter).tokenEnhancer(enhancerChain)
				.authenticationManager(authenticationManager).userDetailsService(userDetailsService);
	}
	
	@Override
	public void configure(AuthorizationServerSecurityConfigurer oauthServer) throws Exception {
		oauthServer
			.tokenKeyAccess("isAnonymous() || permitAll()")
			.checkTokenAccess("hasAuthority('ROLE_TRUSTED_CLIENT')")
			.passwordEncoder(new BCryptPasswordEncoder());
	}

}
