package co.grandcircus.selfcareapp.Dao;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import org.springframework.stereotype.Repository;
import co.grandcircus.selfcareapp.Entity.User;

@Repository
@Transactional
public class UserDao {

	@PersistenceContext
	private EntityManager em;

	public List<User> findByUsername(String username) {
		return em.createQuery("FROM  WHERE username = :username", User.class)
				.setParameter("username", username)
				.getResultList();
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
	
	

}
