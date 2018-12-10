package co.grandcircus.selfcareapp.Entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity @Table
public class UserDate {
	
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY) 
	private Long id;
	@ManyToOne
	private User user;
	private Date date;
	
	
	
	

}
