package co.grandcircus.selfcareapp.Dao;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import org.springframework.stereotype.Repository;
import co.grandcircus.selfcareapp.Entity.Emotion;

@Repository
@Transactional
public class EmotionDao {
	
	@PersistenceContext
	private EntityManager em;
	
	public List<Emotion> findByEmotiopn(String emotion) {
		return em.createQuery("FROM  WHERE emotion = :emotion", Emotion.class)
				.setParameter("emotion", emotion)
				.getResultList();
	}
	public List<Emotion> findByDate(String date) {
		return em.createQuery("FROM  WHERE date = :date", Emotion.class)
				.setParameter("date", date)
				.getResultList();
	}
	public List<Emotion> findByEmotionDate(String emotion) {
		return em.createQuery("FROM  WHERE date = :date", Emotion.class)
				.setParameter("emotion", emotion)
				.getResultList();
	}
	
}
