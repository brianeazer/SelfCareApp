package co.grandcircus.selfcareapp.apiservice;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import co.grandcircus.selfcareapp.model.Request;
import co.grandcircus.selfcareapp.model.Response;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
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



/**
 * Make an HTTP request to Github's server. Use the access token to get the user details.
 */
public getResponseFromGfyCatApi(String accessToken) {
	// We'll talk more about rest template in the coming days.
	RestTemplate rest = new RestTemplate();
	String uri = "https://api.github.com/user?access_token=" + accessToken;
	Response response = rest.getForObject(uri, Response.class);
	
	
	return response.getResponse();
}


}
