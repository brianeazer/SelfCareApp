package co.grandcircus.selfcareapp.Entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class UserEmotion {
	
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY) 
	@Column(name= "id")
	private Long id;
	@Column(name= "user_id")
	private String userId;
	@Column(name= "emotion_id")
	private String emotionId;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getEmotionId() {
		return emotionId;
	}
	public void setEmotionId(String emotionId) {
		this.emotionId = emotionId;
	}
	@Override
	public String toString() {
		return "UserEmotion [id=" + id + ", userId=" + userId + ", emotionId=" + emotionId + "]";
	}
	public UserEmotion() {
		super();
	}
	public UserEmotion(Long id, String userId, String emotionId) {
		super();
		this.id = id;
		this.userId = userId;
		this.emotionId = emotionId;
	}
	
	
	
	
}
