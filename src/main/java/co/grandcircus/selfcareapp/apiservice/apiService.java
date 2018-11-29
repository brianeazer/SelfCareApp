package co.grandcircus.selfcareapp.apiservice;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.ws.Response;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class apiService {

	@Value("${gfycat.client_secret}")
	private String clientSecret;

	public String getGfycatAccessToken(String code) {
		Map<String, String> params = new HashMap<>();
		params.put("code", code);
		params.put("client_id", "5kTbDwUR-RJeKzHxSVr9Q1O3hTKGyqQun8LGLbGOOPP9plqq7BHDLxcl_zt45Grv");
		params.put("client_secret", clientSecret);
		RestTemplate rest = new RestTemplate();
		@SuppressWarnings("unchecked")
		Map<String, String> response = rest.postForObject("https://api.gfycat.com/v1", params, Map.class);
		return response.get("access_token");
	}

/**
 * Make an HTTP request to Github's server. Use the access token to get the user details.
 */
public getResponseFromGfyCatApi(String accessToken) {
	// We'll talk more about rest template in the coming days.
	RestTemplate rest = new RestTemplate();
	String uri = "https://api.github.com/user?access_token=" + accessToken;
	Response response = rest.getForObject(uri, Response.class);
	
	response.getResponse();
	return response;
}

}
