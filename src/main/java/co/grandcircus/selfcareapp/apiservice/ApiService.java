package co.grandcircus.selfcareapp.apiservice;


import java.awt.PageAttributes.MediaType;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import co.grandcircus.selfcareapp.model.Category;
import co.grandcircus.selfcareapp.model.Request;
import co.grandcircus.selfcareapp.model.Response;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Component
public class ApiService {


	@Value("${client_secret}")
	private String clientSecret;

	public Response getGfycatAccessToken(String code) {
		Request request = new Request("client_credentials", "2_iD1qPC",
				clientSecret);
		System.out.println("This is the client secret: " + clientSecret);
		RestTemplate rest = new RestTemplate();
		
		@SuppressWarnings("unchecked")
		Response response = rest.postForObject("https://api.gfycat.com/v1/oauth/token", request, Response.class);
				//"https://api.gfycat.com/v1/oauth/token"
		System.out.println(response.getAccess_token());
		return response;
	}

	private HttpHeaders createHttpHeaders(String username, String password) {
	    String notEncoded = username + ":" + password;
	    String encodedAuth = Base64.getEncoder().encodeToString(notEncoded.getBytes());
	    HttpHeaders headers = new HttpHeaders();
	   // headers.setContentType();
	    headers.add("Authorization", "Basic " + encodedAuth);
	    return headers;
	}


public void getAllCategories(String accessToken) {
	// We'll talk more about rest template in the coming days.
	String theUrl = "https://api.gfycat.com/v1/reactions/populated";
	RestTemplate restTemplate = new RestTemplate();
	try {
        HttpHeaders headers = createHttpHeaders("fred","1234");
        HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);
        ResponseEntity<String> response = restTemplate.exchange(theUrl, HttpMethod.GET, entity, String.class);
        System.out.println("Result - status ("+ response.getStatusCode() + ") has body: " + response.hasBody());
        System.out.println(response.getBody());
    }
    catch (Exception eek) {
        System.out.println("** Exception: "+ eek.getMessage());
    }
}


}
