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
		em.merge("update count FROM UserLikes WHERE user=:user AND like=:like", UserLikes.class)
		.setParameter("user", user);
	}
	
	@SuppressWarnings("unused")
	public UserLikes getUserLikes(User user, Like like) {
		
//		try {
		return em.createQuery("FROM UserLikes AS ul WHERE ul.user.id=:user AND ul.like.id=:like", UserLikes.class)
		.setParameter("user", user.getId()).setParameter("like", like.getId()).getSingleResult();
//		List<UserLikes> x = em.createQuery("FROM UserLikes As ul WHERE ul.user.id=1", UserLikes.class).getResultList();
//		System.out.println(x);
//		} catch(MismatchedTokenException ex) {
//			count = 0;
//		}
//		if (count == null) {
//			count = 0;
//		}
//		return count;
	}
	
	public Like findById(Long id) {
		return em.createQuery("FROM Like WHERE id = :id", Like.class)
				.setParameter("id", id)
				.getSingleResult();
	}
	
	
}
