package co.grandcircus.selfcareapp.model;

public class Category {
	
	private String cursor;

	public Category(String cursor) {
		super();
		this.cursor = cursor;
	}

	public String getCursor() {
		return cursor;
	}

	public void setCursor(String cursor) {
		this.cursor = cursor;
	}

	@Override
	public String toString() {
		return "Category [cursor=" + cursor + "]";
	}
	
}
