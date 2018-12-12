package co.grandcircus.selfcareapp.Entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="user_emotions")
public class UserEmotion {
	
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY) 
	@Column(name= "id")
	private Long id;
	@ManyToOne
	private User user;
	private Integer emotionRating;
	@Column(name= "date")
	private Date date;
	private String category;
	
	public UserEmotion() {}

	public UserEmotion(Long id, User user, Integer emotionRating, Date date, String category) {
		super();
		this.id = id;
		this.user = user;
		this.emotionRating = emotionRating;
		this.date = date;
		this.category = category;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Integer getEmotionRating() {
		return emotionRating;
	}

	public void setEmotionRating(Integer emotionRating) {
		this.emotionRating = emotionRating;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	@Override
	public String toString() {
		return "UserEmotion [id=" + id + ", user=" + user + ", emotionRating=" + emotionRating + ", date=" + date + "category" + category+ "]";
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}
}
