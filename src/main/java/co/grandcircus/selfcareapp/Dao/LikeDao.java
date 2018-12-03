package co.grandcircus.selfcareapp.Dao;

import java.util.List;

import javax.persistence.EntityManager;
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
	
	@SuppressWarnings("unused")
	public UserLikes getUserLikes(User user, Like like) {
		return em.createQuery("FROM UserLikes AS ul WHERE ul.user.id=:user AND ul.like.id=:like", UserLikes.class)
		.setParameter("user", user.getId()).setParameter("like", like.getId()).getSingleResult();
	}
	
	public Like findById(Long id) {
		return em.createQuery("FROM Like WHERE id = :id", Like.class)
				.setParameter("id", id)
				.getSingleResult();
	}
	
	
}
