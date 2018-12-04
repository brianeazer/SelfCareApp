package co.grandcircus.selfcareapp.Entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class UserLikes {
	
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY) 
	@Column(name= "id")
	private Long id;
	@ManyToOne
	private User user;
	@ManyToOne(cascade = CascadeType.PERSIST)
	private Like like;
	private Integer count;
	
	
	
	public UserLikes() {
		super();
		like = new Like();
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
	public Like getLike() {
		return like;
	}
	public void setLike(Like like) {
		this.like = like;
	}
	public Integer getCount() {
		return count;
	}
	public void setCount(Integer count) {
		this.count = count;
	}
	@Override
	public String toString() {
		return "UserLikes [id=" + id + ", user=" + user + ", like=" + like + ", count=" + count + "]";
	}
}
