package co.grandcircus.selfcareapp.Entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import co.grandcircus.selfcareapp.Entity.UserLikes;

public class GfyItem {
	private String gifUrl;
	private List<String> tags;
	private String WebPUrl;
	private List<UserLikes> userLikes;
	private List<String> languageCategories;
	private String gfyId;
	
	
	public GfyItem() {}
	
	public GfyItem(String gifUrl, List<String> tags, String webPUrl) {
		super();
		this.gifUrl = gifUrl;
		this.tags = tags;
		WebPUrl = webPUrl;
	}

	public String getGifUrl() {
		return gifUrl;
	}
	public void setGifUrl(String gifUrl) {
		this.gifUrl = gifUrl;
	}
	public List<String> getTags() {
		return tags;
	}
	public void setTags(List<String> tags) {
		this.tags = tags;
	}
	public String getWebPUrl() {
		return WebPUrl;
	}
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
