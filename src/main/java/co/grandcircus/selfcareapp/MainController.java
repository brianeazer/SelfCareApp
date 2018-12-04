package co.grandcircus.selfcareapp;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
		//apiService.options("cat,halloween");
		apiService.options("cat");
		return new ModelAndView("index");
	}
	
	@RequestMapping("/register")
	public ModelAndView registration() {
		return new ModelAndView ("register");
	}

	@RequestMapping("/mood")
	public ModelAndView findUserMood() {
		ModelAndView mav = new ModelAndView("mood");
		List <String> categories = new ArrayList<>();
		// index 1,2 are food, 3,4,5,6 are cats, 7 sports, 8,9 fails, 10,11 nature
		categories.add("recipe, food");
		categories.add("foodnetwork");
		categories.add("kittens");
		categories.add("cute kittens");
		categories.add("aww");
		categories.add("kittens");
		categories.add("sports");
		categories.add("fail");
		categories.add("epicfail");
		categories.add("waterfalls");
		categories.add("nature");
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
	public ModelAndView getUserProfile(User user, HttpSession session, 
			RedirectAttributes redir) {
		
		if (userDao.findByUsername(user.getUsername()) == null) {
			userDao.create(user);
		}
		else {
			redir.addFlashAttribute("message", "Username taken, please pick another");
			return new ModelAndView("redirect:/register");
		}
		if (session.getAttribute("count") == null) {
			session.setAttribute("user", user);
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
		User user = userDao.findById(1L);
		Like like = likeDao.findById(1L);
		UserLikes userLikes = likeDao.getUserLikes(user, like);
		int num = userLikes.getCount();
		userLikes.setCount(num+count);
		likeDao.update(userLikes);
		
		return new ModelAndView("redirect:/flavorprofile");
	}
	
	
	
}
