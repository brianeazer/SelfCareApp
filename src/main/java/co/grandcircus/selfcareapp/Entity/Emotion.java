//package co.grandcircus.selfcareapp.Entity;
//
//import java.util.Date;
//import java.util.List;
//
//import javax.persistence.Column;
//import javax.persistence.Entity;
//import javax.persistence.GeneratedValue;
//import javax.persistence.GenerationType;
//import javax.persistence.Id;
//import javax.persistence.OneToMany;
//import javax.persistence.Table;
//
//@Entity
//@Table(name="emotion")
//public class Emotion {
//	
//	@Id @GeneratedValue(strategy=GenerationType.IDENTITY) 
//	@Column(name= "id")
//	private Long id;
//	@Column(name= "emotion")
//	private String emotion;
//	@Column(name= "date")
//	private Date date;
//	@OneToMany(mappedBy="emotion")
//	private List<UserEmotion> userEmotion;
//	
//	public Long getId() {
//		return id;
//	}
//	public void setId(Long id) {
//		this.id = id;
//	}
//	public String getEmotion() {
//		return emotion;
//	}
//	public void setEmotion(String emotion) {
//		this.emotion = emotion;
//	}
//	public Date getDate() {
//		return date;
//	}
//	public void setDate(Date date) {
//		this.date = date;
//	}
//	public Emotion(Long id, String emotion, Date date) {
//		super();
//		this.id = id;
//		this.emotion = emotion;
//		this.date = date;
//	}
//	public Emotion() {
//		super();
//	
//	}
//	@Override
//	public String toString() {
//		return "Emotion [id=" + id + ", emotion=" + emotion + ", date=" + date + "]";
//	}
//	
//	
//
//}
