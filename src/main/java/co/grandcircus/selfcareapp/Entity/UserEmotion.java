package co.grandcircus.selfcareapp.Entity;

import java.time.Instant;
import java.util.Calendar;
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
	private Instant instant;
	
	public UserEmotion() {}

	public UserEmotion(Long id, User user, Integer emotionRating, Instant instant) {
		super();
		this.id = id;
		this.user = user;
		this.emotionRating = emotionRating;
		this.instant = instant;
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

	public Instant getInstant() {
		return instant;
	}

	public void setInstant(Instant instant) {
		this.instant = instant;
	}

	@Override
	public String toString() {
		return "UserEmotion [id=" + id + ", user=" + user + ", emotionRating=" + emotionRating + ", instant=" + instant
				+ "]";
	}
}
