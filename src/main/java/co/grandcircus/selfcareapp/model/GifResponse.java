 package co.grandcircus.selfcareapp.model;

import java.util.List;

import co.grandcircus.selfcareapp.Entity.GfyItem;

public class GifResponse {
	private List<GfyItem> gfycats;
	private GfyItem gfyItem;
	
	public List<GfyItem> getGfycats() {
		return gfycats;
	}

	public void setGfycats(List<GfyItem> gfycats) {
		this.gfycats = gfycats;
	}

	public GfyItem getGfyItem() {
		return gfyItem;
	}

	public void setGfyItem(GfyItem gfyItem) {
		this.gfyItem = gfyItem;
	}

	@Override
	public String toString() {
		return "GifResponse [gfycats=" + gfycats + "]";
	}

}
