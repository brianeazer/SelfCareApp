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
@Table(name="like_table")
public class Like {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	private String tag;
	@OneToMany(mappedBy="like")
	private List<UserLikes> userLikes;

	public Like() {
		
	}
	
	public Like(Long id, String tag) {
		super();
		this.id = id;
		this.tag = tag;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public List<UserLikes> getUserLikes() {
		return userLikes;
	}

	public void setUserLikes(List<UserLikes> userLikes) {
		this.userLikes = userLikes;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}
	
}
