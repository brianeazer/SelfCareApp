package co.grandcircus.selfcareapp.Dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import org.springframework.stereotype.Repository;

@Repository
@Transactional
public class EmotionDao {
	
	@PersistenceContext
	private EntityManager em;
	
	
	
}
