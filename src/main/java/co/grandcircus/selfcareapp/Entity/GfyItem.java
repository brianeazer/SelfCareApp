package co.grandcircus.selfcareapp.Entity;

import java.util.List;

import co.grandcircus.selfcareapp.Entity.UserLikes;

public class GfyItem {
	
	private String gifUrl;
	private List<String> tags;
	public String WebPUrl;
	private List<UserLikes> userLikes;
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

	
	
	
}
