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

	public void updateCount(User user, Like like, Integer count) {
		em.createQuery("update v.count FROM UserLikes as v WHERE v.user=:user AND v.like=:like", UserLikes.class)
		.setParameter("v.user", user).setParameter("v.like", like);
	}
	
	@SuppressWarnings("unused")
	public Integer getCount(User user, Like like) {
		Integer count;
//		try {
		count = em.createQuery("SELECT v.count FROM UserLikes as v WHERE v.user.id=:user AND v.like.id=:like", Integer.class)
		.setParameter("v.user.id", user).setParameter("v.like.id", like).getFirstResult();
//		} catch(MismatchedTokenException ex) {
//			count = 0;
//		}
		if (count == null) {
			count = 0;
		}
		return count;
	}
	
	public Like findById(Long id) {
		return em.createQuery("FROM Like WHERE id = :id", Like.class)
				.setParameter("id", id)
				.getSingleResult();
	}
	
	
}
