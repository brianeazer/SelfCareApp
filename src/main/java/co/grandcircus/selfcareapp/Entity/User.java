package co.grandcircus.selfcareapp.Entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "user")
public class User {
	
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY) 
	@Column(name= "id")
	private Long id;
	@Column(name= "username")
	private String username;
	@Column(name= "password")
	private String password;
	@OneToMany(mappedBy="user")
	private List<UserEmotion> userEmotions;
	@OneToMany(mappedBy="user")
	private List<UserLikes> userLikes;
	
	public User(Long id, String username, String password) {
		super();
		this.id = id;
		this.username = username;
		this.password = password;
	}
	public User() {
		super();		
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public List<UserEmotion> getUserEmotions() {
		return userEmotions;
	}
	public void setUserEmotions(List<UserEmotion> userEmotions) {
		this.userEmotions = userEmotions;
	}
	public List<UserLikes> getUserLikes() {
		return userLikes;
	}
	public void setUserLikes(List<UserLikes> userLikes) {
		this.userLikes = userLikes;
	}
}
