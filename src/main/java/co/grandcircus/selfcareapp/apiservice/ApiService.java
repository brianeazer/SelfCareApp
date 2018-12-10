package co.grandcircus.selfcareapp.apiservice;

import java.awt.Cursor;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Base64;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

import co.grandcircus.selfcareapp.Entity.GfyItem;
import co.grandcircus.selfcareapp.model.GifResponse;
import co.grandcircus.selfcareapp.model.TokenRequest;
import co.grandcircus.selfcareapp.model.TokenResponse;

import org.h2.engine.Database;
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

	private RestTemplate restTemplate = new RestTemplate();

	public TokenResponse getGfycatAccessToken(String code) {
		TokenRequest request = new TokenRequest("client_credentials", "2_iD1qPC", clientSecret);
		RestTemplate rest = new RestTemplate();

		@SuppressWarnings("unchecked")
		TokenResponse token = rest.postForObject("https://api.gfycat.com/v1/oauth/token", request, TokenResponse.class);
		// "https://api.gfycat.com/v1/oauth/token"
		// System.out.println(response.getAccess_token());
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

		try {
			HttpHeaders headers = createHttpHeaders("fred", "1234");
			HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);
			ResponseEntity<String> response = restTemplate.exchange(theUrl, HttpMethod.GET, entity, String.class);
			// System.out.println("Result - status ("+ response.getStatusCode() + ") has
			// body: " + response.hasBody());
			System.out.println(response.getBody());
		} catch (Exception eek) {
			System.out.println("** Exception: " + eek.getMessage());
		}
	}

	public GifResponse getAGif(String gifId) {
		String url = "https://api.gfycat.com/v1/gfycats/" + gifId;
		HttpHeaders headers = createHttpHeaders("fred", "1234");
		HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);
		ResponseEntity<GifResponse> response = restTemplate.exchange(url, HttpMethod.GET, entity, GifResponse.class);
		//System.out.println(response);
		GifResponse gifResponse = response.getBody();
		// GfyItem gif = gifResponse.getGfyItem();
		return gifResponse;
	}
	
	public List<String> getTags(String url){
		HttpHeaders headers = createHttpHeaders("fred", "1234");
		HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);
		ResponseEntity<GifResponse> response = restTemplate.exchange(url, HttpMethod.GET, entity, GifResponse.class);
		GifResponse gifResponse = response.getBody();
		// GfyItem gif = gifResponse.getGfyItem();
		return gifResponse.getGfyItem().getTags();
	}

	public String getAPI(String accessToken) {
		return accessToken;
	}

	public GifResponse options(String keyword, Integer amount) {
		String url = "https://api.gfycat.com/v1/gfycats/";
		String charset = java.nio.charset.StandardCharsets.UTF_8.name();
		String search_text = keyword;
		String count = amount.toString();
		//String cursor;
		try {
			String query = String.format("search_text=%s&count=%s", URLEncoder.encode(search_text, charset),
					URLEncoder.encode(count, charset));
			String fullUrl = url + "search?" + query;
			
			GifResponse gifResponse = restTemplate.getForObject(fullUrl, GifResponse.class);
			GifResponse filteredGifResponse = isClean(gifResponse);
			System.out.println("This is the full url" + fullUrl);
			return filteredGifResponse;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return null;
		}

	}	
	
//	public int optionsLength(String keyword) throws UnsupportedEncodingException {
//		String url = "https://api.gfycat.com/v1/gfycats/";
//		System.out.println("options length was called");
//		String charset = java.nio.charset.StandardCharsets.UTF_8.name();
//		String search_text = keyword;
//		String query = String.format("search_text=%s", URLEncoder.encode(search_text, charset));
//		
//		String fullUrl = url + "search?" + query;
//		HttpHeaders headers = createHttpHeaders("Fred", "1234");
//		HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);
//		
//		GifResponse gifResponse = restTemplate.getForObject(fullUrl, GifResponse.class);
//		GifResponse filteredGifResponse = isClean(gifResponse);
//		return filteredGifResponse.getGfycats().size();
//	}
	
public GifResponse isClean(GifResponse gifResponse) {
	System.out.println("start cleaning");
	List<GfyItem> gifs = gifResponse.getGfycats();
	//filter nsfw gfyItems
	for (int i = 0; i < gifs.size(); i++) {
		if (!gifs.get(i).getNsfw().equals("0")) {
			System.out.println(gifs.get(i).getGifUrl());
			gifs.remove(i);
		}
	}
	
	//filter certain tags
	ArrayList<String> avoidables = new ArrayList<>();
	avoidables.add("politics");
	avoidables.add("religion");
	avoidables.add("sex");
	avoidables.add("nsfw");
	avoidables.add("violence");
	for (int i = 0; i < gifs.size(); i++) {
		if (gifs.get(i).getTags()!=null) {
			for (String tag : gifs.get(i).getTags()) {
				if (avoidables.contains(tag)) {
					System.out.println("We removed this gif because it was tagged " + tag);
					gifs.remove(i);
				}
			}
		}
	}
	
	// create a gifResponse and set filtered list of gifItems to that response
	GifResponse gifResponse1 = new GifResponse();
	System.out.println("Now the gif response has " + gifs.size());
	gifResponse1.setGfycats(gifs);
	return gifResponse1;
	}

}
