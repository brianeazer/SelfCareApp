package co.grandcircus.selfcareapp;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.Cookie;
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
import co.grandcircus.selfcareapp.Entity.GfyItem;
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
	public ModelAndView index() throws UnsupportedEncodingException {
		String token = apiService.getGfycatAccessToken("").getAccess_token();
		apiService.options("cat");
		return new ModelAndView("index");
	}

	@PostMapping("/")
	public ModelAndView submitLogin(@RequestParam(name = "username") String username,
			@RequestParam(name = "password") String password, RedirectAttributes redir, HttpSession session) {
		User user = userDao.findByUsername(username);
		if (user == null) {
			redir.addFlashAttribute("message", "Incorrect username or password");
			return new ModelAndView("redirect:/");
		} else if (!user.getPassword().equals(password)) {
			redir.addFlashAttribute("message", "Incorrect username or password");
			return new ModelAndView("redirect:/");
		} else {
			session.setAttribute("user", user);
			return new ModelAndView("mood");
		}
	}

	@RequestMapping("/register")
	public ModelAndView registration() {
		return new ModelAndView("register");
	}

	@RequestMapping("/mood")
	public ModelAndView findUserMood(HttpSession session) {
		ModelAndView mav = new ModelAndView("mood");
		User user = (User) session.getAttribute("user");
		// System.out.println("User" + user.getUsername() + " Password: " +
		// user.getPassword());
		// List<UserLikes> userLikes = user.getUserLikes();
		// System.out.println(userLikes);
		List<String> categories = new ArrayList<>();
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
		String[] gifIds = { "longhandyaxisdeer", "requiredlawfulchupacabra", "mildsardonicasianconstablebutterfly",
				"tightfluffyaustraliankelpie", "masculinecalmeelelephant", "coarseselfassuredboutu" };
		ArrayList<String> gifUrls = new ArrayList<>();
		for (String s : gifIds) {
			String url = apiService.getAGif(s).getGfyItem().getGifUrl();
			gifUrls.add(url);
			System.out.println(url);
		}
		for (String s : gifUrls) {
			System.out.println(s);
		}
		return new ModelAndView("test", "gifs", gifUrls);
	}

	@RequestMapping("/flavorprofile")
	public ModelAndView getUserProfile(User user, HttpSession session, RedirectAttributes redir) {
		if (session.getAttribute("user") == null && userDao.findByUsername(user.getUsername()) == null) {
			userDao.create(user);
			session.setAttribute("user", user);
			session.setAttribute("count", 0);
		} else if (session.getAttribute("user") == null && userDao.findByUsername(user.getUsername()) != null) {
			redir.addFlashAttribute("message", "Username taken, please pick another");
			return new ModelAndView("redirect:/register");
		} else {
			session.setAttribute("count", (int) (session.getAttribute("count")) + 1);
		}
		int count = (int) session.getAttribute("count");
		String[] gifIds = { "longhandyaxisdeer", "requiredlawfulchupacabra", "mildsardonicasianconstablebutterfly",
				"tightfluffyaustraliankelpie", "masculinecalmeelelephant", "coarseselfassuredboutu",
				"requiredunawarebirdofparadise", "creepydevotedcoral", "thoroughgreedyhagfish",
				"brownannualirishsetter", "rapidultimatedwarfmongoose", "secondhandellipticalaquaticleech",
				"selfishorganichornet", "equatorialdisgustingcassowary", "fakepassionatearacari" };
		String gifId = gifIds[count];
		GfyItem gfyItem = apiService.getAGif(gifId).getGfyItem();
		ModelAndView mv = new ModelAndView("flavorProfile");
		mv.addObject("gif", gfyItem);
		return mv;
	}

	@RequestMapping("/store-info")
	public ModelAndView addToDatabase(@RequestParam(name = "count", required = false) Integer count,
			@RequestParam(name = "id") String gifId, HttpSession session) {
		GfyItem gfyItem = new GfyItem();
		if (count == 1) {
			gfyItem = apiService.getAGif(gifId).getGfyItem();
			ArrayList<String> tags = (ArrayList<String>) gfyItem.getTags();
			for (String tag : tags) {
				Like like = new Like();
				like.setTag(tag);
				updateUserLikeTable(like, (User) session.getAttribute("user"));
			}
		}

		return new ModelAndView("redirect:/flavorprofile");
	}

	@RequestMapping("/pastlikegifs")
	public ModelAndView showGif(HttpSession session) {
		ModelAndView mv = new ModelAndView("pastlikegifs");
		User user = (User) session.getAttribute("user");
		System.out.println(user.getUsername());
		
		List<UserLikes> likes = likeDao.getUserLikes(user);
		System.out.println(likes);
		mv.addObject("Likes", likes);
		return mv;
	}

	public void updateUserLikeTable(Like like, User user) {
		UserLikes userLike = likeDao.getUserLikes(user, like);
		if (userLike == null) {
			userLike = new UserLikes();
			userLike.setCount(0);
			userLike.setLike(like);
			userLike.setUser(user);
			likeDao.createUserLike(userLike);
		} else {
			Integer count = userLike.getCount();
			count++;
			userLike.setCount(count);
		}

	}

}
