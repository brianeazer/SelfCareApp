package co.grandcircus.selfcareapp.Dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

@Repository
@Transactional
public class LikeDao {

	@PersistenceContext
	private EntityManager em;
	
	
	
	
}
