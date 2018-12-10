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
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public UserDate(Long id, User user, Date date) {
		super();
		this.id = id;
		this.user = user;
		this.date = date;
	}
	public UserDate() {
		super();
	}
	@Override
	public String toString() {
		return "UserDate [id=" + id + ", user=" + user + ", date=" + date + "]";
	}
	
	
	
	
	
	
	

}
