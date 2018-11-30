package co.grandcircus.selfcareapp.Entity;

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
	@ManyToOne
	private Emotion emotion;
	
	public UserEmotion() {}
	
	public UserEmotion(Long id, User user, Emotion emotion) {
		super();
		this.id = id;
		this.user = user;
		this.emotion = emotion;
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

	public Emotion getEmotion() {
		return emotion;
	}

	public void setEmotion(Emotion emotion) {
		this.emotion = emotion;
	}

	@Override
	public String toString() {
		return "UserEmotion [id=" + id + ", user=" + user + ", emotion=" + emotion + "]";
	}
	
}
