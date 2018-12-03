package co.grandcircus.selfcareapp;

import java.util.ArrayList;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import co.grandcircus.selfcareapp.Dao.LikeDao;
import co.grandcircus.selfcareapp.Dao.UserDao;
import co.grandcircus.selfcareapp.Entity.Like;
import co.grandcircus.selfcareapp.Entity.User;
import co.grandcircus.selfcareapp.Entity.UserLikes;
import co.grandcircus.selfcareapp.apiservice.ApiService; 

@Controller
public class MainController {
//	User user = new User(1L, "Brian", "password");
//	Like like = new Like(1L, "cat");
	@Autowired
	ApiService apiService;
	
	@Autowired
	LikeDao likeDao;
	
	@Autowired
	UserDao userDao;
	
	@RequestMapping("/")
	public ModelAndView index() {
		String token = apiService.getGfycatAccessToken("").getAccess_token();
		//apiService.getGifInCategory("cat, halloween");
		apiService.getGifInCategory("halloween , cat, dog");
		return new ModelAndView("index");
	}
	
	@RequestMapping("/register")
	public ModelAndView registration() {
		return new ModelAndView ("register");
	}

	@RequestMapping("/mood")
	public ModelAndView findUserMood() {
		ModelAndView mav = new ModelAndView("mood");
		return mav;
	}
	
	@RequestMapping("/test")
	public ModelAndView testGifs() {
		String[] gifIds = {"KeenBriefDairycow","FatherlyClassicGadwall","vibrantuniquekiwi", "enviousimmaculateflatcoatretriever", "tightfluffyaustraliankelpie", "masculinecalmeelelephant"};
		ArrayList<String> gifUrls = new ArrayList<>();
		for (String s : gifIds) {
			String url = apiService.getAGif(s).getGfyItem().getGifUrl();
			gifUrls.add(url);
			System.out.println(url);
		}
		for (String s: gifUrls) {
			System.out.println(s);
		}
		return new ModelAndView("test", "gifs", gifUrls);
	}
	
	@RequestMapping("/flavorprofile")
	public ModelAndView getUserProfile(HttpSession session) {
		if (session.getAttribute("count") == null) {
			session.setAttribute("count", 0);
		}
		else {
			session.setAttribute("count", (int)(session.getAttribute("count"))+1);
		}
		int count = (int) session.getAttribute("count");
		String[] gifIds = {"KeenBriefDairycow","FatherlyClassicGadwall","vibrantuniquekiwi", "enviousimmaculateflatcoatretriever", "tightfluffyaustraliankelpie", "masculinecalmeelelephant"};
		String gifId = gifIds[count];
		String gifUrl = apiService.getAGif(gifId).getGfyItem().getGifUrl();
		return new ModelAndView("flavorProfile", "gif", gifUrl);
	}
	@RequestMapping("/store-info") 
	public ModelAndView addToDatabase(@RequestParam(name = "count", required=false) Integer count) {
		System.out.println(count);
		User user = userDao.findById(1L);
		Like like = likeDao.findById(1L);
		UserLikes userLikes = likeDao.getUserLikes(user, like);
		int num = userLikes.getCount();
		userLikes.setCount(num+count);
		System.out.println(userLikes.getCount());
		
		return new ModelAndView("redirect:/flavorprofile");
	}
	
	
}
