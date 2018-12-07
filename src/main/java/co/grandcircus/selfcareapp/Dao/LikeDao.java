package co.grandcircus.selfcareapp.Dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import org.springframework.stereotype.Repository;
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

	public UserLikes getUserLikes(User user, String tag) {
		try {
			return em.createQuery("FROM UserLikes AS ul WHERE ul.user.id=:user AND ul.tag=:tag", UserLikes.class)
					.setParameter("user", user.getId()).setParameter("tag", tag).getSingleResult();
		} catch (NoResultException | NullPointerException ex) {
			return null;
		}
	}


	public void createUserLike(UserLikes userLike) {
		em.persist(userLike);
	}

	public List<UserLikes> getUserLikes(User user) {
		return em.createQuery("FROM UserLikes WHERE user = :user", UserLikes.class)
				.setParameter("user", user)
				.getResultList();
	}

}
