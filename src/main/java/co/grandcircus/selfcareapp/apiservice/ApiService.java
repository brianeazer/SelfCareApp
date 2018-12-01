package co.grandcircus.selfcareapp.apiservice;

import java.util.ArrayList;
import java.util.Base64;


import co.grandcircus.selfcareapp.model.Category;
import co.grandcircus.selfcareapp.model.GfyItem;
import co.grandcircus.selfcareapp.model.GifResponse;
import co.grandcircus.selfcareapp.model.TokenRequest;

import co.grandcircus.selfcareapp.model.TokenRequest;
import co.grandcircus.selfcareapp.model.TokenResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class ApiService {


	@Value("${client_secret}")
	private String clientSecret;

	public TokenResponse getGfycatAccessToken(String code) {
		TokenRequest request = new TokenRequest("client_credentials", "2_iD1qPC",
				clientSecret);
		RestTemplate rest = new RestTemplate();
		
		@SuppressWarnings("unchecked")
		TokenResponse token = rest.postForObject("https://api.gfycat.com/v1/oauth/token", request, TokenResponse.class);
				//"https://api.gfycat.com/v1/oauth/token"
		//System.out.println(response.getAccess_token());
		return token;
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
	String theUrl = "https://api.gfycat.com/v1/gfycats/vibrantuniquekiwi";
	RestTemplate restTemplate = new RestTemplate();
	try {
        HttpHeaders headers = createHttpHeaders("fred","1234");
        HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);
        ResponseEntity<String> response = restTemplate.exchange(theUrl, HttpMethod.GET, entity, String.class);
        //System.out.println("Result - status ("+ response.getStatusCode() + ") has body: " + response.hasBody());
        System.out.println(response.getBody());
    }
    catch (Exception eek) {
        System.out.println("** Exception: "+ eek.getMessage());
    }
}

public GifResponse getAGif(String gifId) {
	String url = "https://api.gfycat.com/v1/gfycats/" + gifId;
	RestTemplate restTemplate = new RestTemplate();
	HttpHeaders headers = createHttpHeaders("fred","1234");
    HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);
	ResponseEntity<GifResponse> response = restTemplate
			.exchange(url, HttpMethod.GET, entity, GifResponse.class);
	GifResponse gifResponse = response.getBody();
	//GfyItem gif = gifResponse.getGfyItem();
	return gifResponse;
}

public String getAPI(String accessToken) {
	return accessToken;
}


}
