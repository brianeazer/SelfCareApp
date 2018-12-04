package co.grandcircus.selfcareapp.Dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import org.springframework.stereotype.Repository;
import co.grandcircus.selfcareapp.Entity.Like;
import co.grandcircus.selfcareapp.Entity.User;
import co.grandcircus.selfcareapp.Entity.UserLikes;


@Repository
@Transactional
public class LikeDao {

	@PersistenceContext
	private EntityManager em;

	public void update(UserLikes userLikes) {
		em.merge(userLikes);
	}
	public void create(Like like) {
		em.persist(like);
	}
	
	@SuppressWarnings("unused")
	public UserLikes getUserLikes(User user, Like like) {
		try {
			return em.createQuery("FROM UserLikes AS ul WHERE ul.user.id=:user AND ul.like.id=:like", UserLikes.class)
		.setParameter("user", user.getId()).setParameter("like", like.getId()).getSingleResult();
		} catch(NoResultException | NullPointerException ex) {
			return null;
		}
	}
	
	public Like findById(Long id) {
		return em.createQuery("FROM Like WHERE id = :id", Like.class)
				.setParameter("id", id)
				.getSingleResult();
	}
	
	public String findByTag(String tag) {
		try {
		return em.createQuery("FROM Like WHERE tag = :tag", Like.class)
				.setParameter("tag", tag)
				.getSingleResult().getTag();
		} catch(NoResultException ex) {
			return null;
		}
	}
	public void createUserLike(UserLikes userLike) {
		em.persist(userLike);
	}
	
	
}
