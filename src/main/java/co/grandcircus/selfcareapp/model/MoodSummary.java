package co.grandcircus.selfcareapp.model;

import java.io.Serializable;
import java.util.Map;

/**
 * This goes on the session and keeps track of GIFs seen between two mood ratings
 */
public class MoodSummary implements Serializable {

	private static final long serialVersionUID = 1L;

	private Map<String, Integer> likesPerCategory;

	public Map<String, Integer> getLikesPerCategory() {
		return likesPerCategory;
	}

	public void setLikesPerCategory(Map<String, Integer> likesPerCategory) {
		this.likesPerCategory = likesPerCategory;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		return "MoodSummary [likesPerCategory=" + likesPerCategory + "]";
	}

	

}
