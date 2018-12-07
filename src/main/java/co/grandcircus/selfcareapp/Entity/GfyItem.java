package co.grandcircus.selfcareapp.Entity;

import java.util.List;
import co.grandcircus.selfcareapp.Entity.UserLikes;

public class GfyItem {
	private String gifUrl;
	private List<String> tags;
	private String WebPUrl;
	private String max5mbGif;
	private List<UserLikes> userLikes;
	private List<String> languageCategories;
	private String gfyId;
	private String nsfw;
	
	public GfyItem() {}

	public GfyItem(String gifUrl, List<String> tags, String webPUrl, String max5mbGif, List<UserLikes> userLikes,
			List<String> languageCategories, String gfyId, String nsfw) {
		super();
		this.gifUrl = gifUrl;
		this.tags = tags;
		WebPUrl = webPUrl;
		this.max5mbGif = max5mbGif;
		this.userLikes = userLikes;
		this.languageCategories = languageCategories;
		this.gfyId = gfyId;
		this.nsfw = nsfw;
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
	public String getMax5mbGif() {
		return max5mbGif;
	}

	public void setMax5mbGif(String max5mbGif) {
		this.max5mbGif = max5mbGif;
	}

	@Override
	public String toString() {
		return "GfyItem [gifUrl=" + gifUrl + ", tags=" + tags + ", WebPUrl=" + WebPUrl + ", max5mbGif=" + max5mbGif
				+ ", userLikes=" + userLikes + ", languageCategories=" + languageCategories + ", gfyId=" + gfyId + "nsfw" + nsfw+ "]";
	}

	public String getNsfw() {
		return nsfw;
	}

	public void setNsfw(String nsfw) {
		this.nsfw = nsfw;
	}
	
}
