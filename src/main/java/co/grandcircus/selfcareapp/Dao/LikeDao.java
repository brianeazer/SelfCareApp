package co.grandcircus.selfcareapp.Dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import org.springframework.stereotype.Repository;

import co.grandcircus.selfcareapp.Entity.Like;


@Repository
@Transactional
public class LikeDao {

	@PersistenceContext
	private EntityManager em;
	
	public List<Like> findByCat(String likesCat) {
		return em.createQuery("FROM  WHERE likes_cat = :likes_cat", Like.class)
				.setParameter("likes_cat", likesCat)
				.getResultList();
	}
	public List<Like> findByLike(String likesIt) {
		return em.createQuery("FROM  WHERE likes_it = :likes_it", Like.class)
				.setParameter("likes_it", likesIt)
				.getResultList();
	}
	public List<Like> findByQuote(String likesQuotes) {
		return em.createQuery("FROM  WHERE likes_quotes = :likes_quotes", Like.class)
				.setParameter("likes_quotes", likesQuotes)
				.getResultList();
	}
	
	
}
