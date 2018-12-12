package co.grandcircus.selfcareapp.apiservice;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import co.grandcircus.selfcareapp.Entity.GfyItem;
import co.grandcircus.selfcareapp.model.GifResponse;

@Component
public class ApiService {

	@Value("${client_secret}")
	private String clientSecret;

	private RestTemplate restTemplate = new RestTemplate();

	private HttpHeaders createHttpHeaders(String username, String password) {
		String notEncoded = username + ":" + password;
		String encodedAuth = Base64.getEncoder().encodeToString(notEncoded.getBytes());
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", "Basic " + encodedAuth);
		return headers;
	}

	public GifResponse getAGif(String gifId) {
		String url = "https://api.gfycat.com/v1/gfycats/" + gifId;
		HttpHeaders headers = createHttpHeaders("fred", "1234");
		HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);
		ResponseEntity<GifResponse> response = restTemplate.exchange(url, HttpMethod.GET, entity, GifResponse.class);
		GifResponse gifResponse = response.getBody();
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

	public GifResponse options(String keyword, Integer amount) {
		String url = "https://api.gfycat.com/v1/gfycats/";
		String charset = java.nio.charset.StandardCharsets.UTF_8.name();
		String search_text = keyword;
		String count = amount.toString();
		
		try {
			String query = String.format("search_text=%s&count=%s", URLEncoder.encode(search_text, charset),
					URLEncoder.encode(count, charset));
			String fullUrl = url + "search?" + query;
			
			GifResponse gifResponse = restTemplate.getForObject(fullUrl, GifResponse.class);
			GifResponse filteredGifResponse = isClean(gifResponse);
			return filteredGifResponse;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return null;
		}

	}	
	
	public GifResponse isClean(GifResponse gifResponse) {
		List<GfyItem> gifs = gifResponse.getGfycats();
		//filter nsfw gfyItems
		for (int i = 0; i < gifs.size(); i++) {
			if (!gifs.get(i).getNsfw().equals("0")) {
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
		avoidables.add("k-pop");
		avoidables.add("kpop");
		for (int i = 0; i < gifs.size(); i++) {
			if (gifs.get(i).getTags()!=null) {
				for (String tag : gifs.get(i).getTags()) {
					if (avoidables.contains(tag)) {
						gifs.remove(i);
					}
				}
			}
		}
		
		// create a gifResponse and set filtered list of gifItems to that response
		GifResponse gifResponse1 = new GifResponse();
		gifResponse1.setGfycats(gifs);
		return gifResponse1;
	}

}
