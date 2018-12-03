package co.grandcircus.selfcareapp.model;

import co.grandcircus.selfcareapp.Entity.GfyItem;

public class GifResponse {
	private GfyItem gfyItem;
	

	public GifResponse(GfyItem gfyItem) {
		super();
		this.gfyItem = gfyItem;
	}
	
	public GifResponse() {
		
	}

	public GfyItem getGfyItem() {
		return gfyItem;
	}

	public void setGfyItem(GfyItem gfyItem) {
		this.gfyItem = gfyItem;
	}

	@Override
	public String toString() {
		return "GifResponse [gfyItem=" + gfyItem + "]";
	}
	
	
}
