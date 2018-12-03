package co.grandcircus.selfcareapp.model;

import java.util.List;

import co.grandcircus.selfcareapp.Entity.UserLikes;

public class GfyItem {
	
	private String gifUrl;
	private List<UserLikes> userLikes;

	
	public GfyItem(String gifUrl) {
		super();
		this.gifUrl = gifUrl;
	}
	
	public GfyItem() {
		
	}

	public String getGifUrl() {
		return gifUrl;
	}

	public void setGifUrl(String gifUrl) {
		this.gifUrl = gifUrl;
	}

	
	
	
}
