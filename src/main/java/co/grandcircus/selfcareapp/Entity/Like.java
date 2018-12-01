package co.grandcircus.selfcareapp.Entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

@Entity
//@Repository
//@Transactional
@Table(name="like_table")
public class Like {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	@Column(name = "likes_cat")
	private boolean likesCat;
	@Column(name = "likes_quotes")
	private boolean likesQuotes;
	@Column(name = "likes_it")
	private boolean likesIt;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public boolean getLikesCat() {
		return likesCat;
	}

	public void setLikesCat(boolean likesCat) {
		this.likesCat = likesCat;
	}

	public boolean getLikesQuotes() {
		return likesQuotes;
	}

	public void setLikesQuotes(boolean likesQuotes) {
		this.likesQuotes = likesQuotes;
	}

	public boolean getLikesIt() {
		return likesIt;
	}

	public void setLikesIt(boolean likesIt) {
		this.likesIt = likesIt;
	}

	public Like(boolean likesCat, boolean likesQuotes, boolean likesIt) {
		super();
		this.likesCat = likesCat;
		this.likesQuotes = likesQuotes;
		this.likesIt = likesIt;
	}

	public Like() {
		super();
	}

	@Override
	public String toString() {
		return "Like [likesCat=" + likesCat + ", likesQuotes=" + likesQuotes + ", likesIt=" + likesIt + "]";
	}

}
