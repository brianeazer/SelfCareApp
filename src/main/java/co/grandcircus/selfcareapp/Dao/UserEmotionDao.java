package co.grandcircus.selfcareapp.Dao;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import org.springframework.stereotype.Repository;
import co.grandcircus.selfcareapp.Entity.UserEmotion;

@Repository
@Transactional
public class UserEmotionDao {

	@PersistenceContext
	private EntityManager em;
	
	public List<UserEmotion> findByEmotionId(String emotionId) {
		return em.createQuery("FROM  WHERE emotion_id = :emotion_id", UserEmotion.class)
				.setParameter("emotion_id", emotionId)
				.getResultList();
	}
	public List<UserEmotion> findByUserId(String userId) {
		return em.createQuery("FROM  WHERE user_id = :user_id", UserEmotion.class)
				.setParameter("emotion_id", userId)
				.getResultList();
	}
	
	
}
