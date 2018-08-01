package br.com.fo2app.springboot.oauth2.rest;

import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.nio.charset.Charset;
import java.util.Arrays;

import org.apache.tomcat.util.codec.binary.Base64;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.JacksonJsonParser;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class UserControllerTest {

	@Autowired
	private MockMvc mvc;

	@SuppressWarnings("rawtypes")
	private HttpMessageConverter mappingJackson2HttpMessageConverter;
	
	protected static String authorization;
	protected static final String clientId = "BFBE45C41E29EFB5FF27988644FAEA5F";
	protected static final String clientSecret = "F0E4A978866280C0E7D911AC2490EDDB12E2D4399A11833A326C7C2D553DC51B";
	protected HttpHeaders headers = new HttpHeaders();
	
	protected static String token;
	

	@Autowired
	void setConverters(HttpMessageConverter<?>[] converters) {

		this.mappingJackson2HttpMessageConverter = Arrays.asList(converters).stream()
				.filter(hmc -> hmc instanceof MappingJackson2HttpMessageConverter).findAny().orElse(null);

		assertNotNull("the JSON message converter must not be null", this.mappingJackson2HttpMessageConverter);
	}
	
	@Before
	public void setUp() throws Exception {

		String auth = clientId + ":" + clientSecret;
		byte[] encodedAuth = Base64.encodeBase64(auth.getBytes(Charset.forName("UTF-8")));
		authorization = "Basic " + new String(encodedAuth);
		headers.add("Accept", "application/json");
		headers.add("Authorization", authorization);
		
		token = obtainAccessToken("admin", "password");
		
		headers.clear();
		
		headers.add("Accept", "application/json");
		headers.add("Authorization", "Bearer " + token);

	}

	@Test
	public void listTest() throws Exception {

		mvc.perform(get("/users").accept(MediaType.APPLICATION_JSON)
				.headers(headers))
				.andExpect(status().isOk())
				.andExpect(content().string(notNullValue()));

	}
	
	private String obtainAccessToken(String username, String password) throws Exception {
		  
	    MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
	    params.add("grant_type", "password");
	    params.add("username", username);
	    params.add("password", password);
	 
	    ResultActions result 
	      = mvc.perform(post("/oauth/token")
	        .params(params)
	        .headers(headers)
	        .accept("application/json;charset=UTF-8"))
	        .andExpect(status().isOk())
	        .andExpect(content().contentType("application/json;charset=UTF-8"));
	 
	    String resultString = result.andReturn().getResponse().getContentAsString();
	 
	    JacksonJsonParser jsonParser = new JacksonJsonParser();
	    return jsonParser.parseMap(resultString).get("access_token").toString();
	}

	
}
