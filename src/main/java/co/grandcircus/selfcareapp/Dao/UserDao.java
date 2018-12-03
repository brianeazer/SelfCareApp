package co.grandcircus.selfcareapp.Dao;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import org.springframework.stereotype.Repository;
import co.grandcircus.selfcareapp.Entity.User;

@Repository
@Transactional
public class UserDao {

	@PersistenceContext
	private EntityManager em;

	public User findByUsername(String username) {
		try {
			return em.createQuery("FROM User WHERE username = :username", User.class)
				.setParameter("username", username)
				.getSingleResult();
		} catch (NoResultException | NonUniqueResultException ex) {
			return null;
		}
	}
	public List<User> findByPassword(String password) {
		return em.createQuery("FROM  WHERE password = :password", User.class)
				.setParameter("password", password)
				.getResultList();
	}
	public User findById(Long id) {
		return em.createQuery("FROM User WHERE id = :id", User.class)
				.setParameter("id", id)
				.getSingleResult();
	}
	public void create(User user) {
		em.persist(user);
	}
	
	

}
