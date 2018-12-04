package co.grandcircus.selfcareapp.Entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import co.grandcircus.selfcareapp.Entity.UserLikes;

public class GfyItem {
	@JsonProperty("gfycats")
	private String gifUrl;
	@JsonProperty("gfycats")
	private List<String> tags;
	@JsonProperty("gfycats")
	private String WebPUrl;
	private List<UserLikes> userLikes;
	@JsonProperty("gfycats")
	private List<String> languageCategories;
	private String gfyId;

	public String getGifUrl() {
		return gifUrl;
	}
	@JsonProperty("gfycats")
	public void setGifUrl(String gifUrl) {
		this.gifUrl = gifUrl;
	}
	@JsonProperty("gfycats")
	public List<String> getTags() {
		return tags;
	}
	@JsonProperty("gfycats")
	public void setTags(List<String> tags) {
		this.tags = tags;
	}
	@JsonProperty("gfycats")
	public String getWebPUrl() {
		return WebPUrl;
	}
	@JsonProperty("gfycats")
	public void setWebPUrl(String webPUrl) {
		WebPUrl = webPUrl;
	}
	public List<UserLikes> getUserLikes() {
		return userLikes;
	}
	public void setUserLikes(List<UserLikes> userLikes) {
		this.userLikes = userLikes;
	}

	@Override
	public String toString() {
		return "GfyItem [gifUrl=" + gifUrl + ", tags=" + tags + ", WebPUrl=" + WebPUrl + ", userLikes=" + userLikes
				+ "]";
	}
	public List<String> getLanguageCategories() {
		return languageCategories;
	}
	public void setLanguageCategories(List<String> languageCategories) {
		this.languageCategories = languageCategories;
	}
	public String getGfyId() {
		return gfyId;
	}
	public void setGfyId(String gfyId) {
		this.gfyId = gfyId;
	}
	

	
}
