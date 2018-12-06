package co.grandcircus.selfcareapp;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import co.grandcircus.selfcareapp.Dao.LikeDao;
import co.grandcircus.selfcareapp.Dao.UserDao;
import co.grandcircus.selfcareapp.Entity.GfyItem;
import co.grandcircus.selfcareapp.Entity.User;
import co.grandcircus.selfcareapp.Entity.UserLikes;
import co.grandcircus.selfcareapp.apiservice.ApiService;
import co.grandcircus.selfcareapp.model.GifResponse;

@Controller
public class MainController {

	@Autowired
	ApiService apiService;

	@Autowired
	LikeDao likeDao;

	@Autowired
	UserDao userDao;

	@RequestMapping("/")
	public ModelAndView index() throws UnsupportedEncodingException {
		String token = apiService.getGfycatAccessToken("").getAccess_token();
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
			return new ModelAndView("redirect:/mood");
		}
	}

	@RequestMapping("/logout")
	public ModelAndView logout(HttpSession session, RedirectAttributes redir) {
		session.invalidate();
		redir.addFlashAttribute("message", "You've logged out");
		return new ModelAndView("redirect:/");
	}

	@RequestMapping("/register")
	public ModelAndView registration() {
		return new ModelAndView("register");
	}

	@RequestMapping("/mood")
	public ModelAndView findUserMood(HttpSession session) {
		ModelAndView mav = new ModelAndView("mood");
		User user = (User) session.getAttribute("user");

		// arraylist of all categories
		List<String> categories = new ArrayList<String>(Arrays.asList("food", "cats", "sports", "fails", "nature"));
		mav.addObject("categories", categories);
		return mav;
	}

	@RequestMapping("/gifs")
	public ModelAndView moodCategory(HttpSession session, @RequestParam(name = "category") String category,
			RedirectAttributes redir) {
		ModelAndView mav = new ModelAndView("randomgif");

		// search options for user to choose from
		Map<String, List<String>> categories = new HashMap<>();
		categories.put("food", Arrays.asList("recipe, food", "foodnetwork"));
		categories.put("cats", Arrays.asList("kittens", "cute kittens", "aww"));
		categories.put("sports", Arrays.asList("sports"));
		categories.put("fails", Arrays.asList("fail", "epicfail"));
		categories.put("nature", Arrays.asList("waterfalls", "nature"));
		categories.put("random", Arrays.asList(""));

		List<GfyItem> gifs = new ArrayList<>();
		if (category.equals("random")) {
			User user = (User) session.getAttribute("user");

			// gets complete list of "likes" and sorts top 10 (if positive) likes
			List<UserLikes> likes = (ArrayList<UserLikes>) likeDao.getUserLikes(user);
			List<UserLikes> topLikes = getTopLikes(likes);
			
			// gets random number to select index of a top like
			int indexTopLikes = getIntInRange(topLikes.size());
			UserLikes ul = topLikes.get(indexTopLikes);
			String tag = ul.getTag();
			
			// gets list of gifs based on chosen tags
			// TODO: figure out a way to possibly get more than 10 thru cursor
			List<GfyItem> gfyItems = apiService.options(tag, 10).getGfycats();
			int indexGifList = getIntInRange(gfyItems.size());
			GfyItem gifItem = gfyItems.get(indexGifList);
			String url = gifItem.getMax5mbGif();
			String gifId = gifItem.getGfyId();
			mav.addObject("gif", url);
			mav.addObject("gifId", gifId);
		} else {
			// 1. grab the list based on the category
			List<String> keywords = categories.get(category);
			// 2. for each keyword in the list...
			for (String keyword : keywords) {
				// grab results, add it to a general list
				GifResponse gifResponse = apiService.options(keyword, 10);
				gifs.addAll(gifResponse.getGfycats());
			}
			// 3. randomly select an index
			int index = (int) Math.floor(Math.random() * gifs.size());
			// 4. find item at that index & show the gif
			GfyItem gfyItem = gifs.get(index);
			mav.addObject("gif", gfyItem.getMax5mbGif());
			mav.addObject("gifId", gfyItem.getGfyId());
		}

		return mav;
	}

	@RequestMapping("/test")
	public ModelAndView testGifs() {
		String[] gifIds = { "longhandyaxisdeer", "requiredlawfulchupacabra", "mildsardonicasianconstablebutterfly",
				"tightfluffyaustraliankelpie", "masculinecalmeelelephant", "coarseselfassuredboutu" };
		ArrayList<String> gifUrls = new ArrayList<>();
		for (String s : gifIds) {
			String url = apiService.getAGif(s).getGfyItem().getMax5mbGif();
			gifUrls.add(url);
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
		if (count == gifIds.length) {
			return new ModelAndView("mood");
		}
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
		gfyItem = apiService.getAGif(gifId).getGfyItem();
		ArrayList<String> tags = (ArrayList<String>) gfyItem.getTags();
		for (String tag : tags) {
			updateUserLikeTable(tag, (User) session.getAttribute("user"), count);
		}
		return new ModelAndView("redirect:/flavorprofile");
	}
	
	@RequestMapping("/random-store-info")
	public ModelAndView addRandomToDatabase(@RequestParam(name = "count", required = false) Integer count,
			@RequestParam(name = "id") String gifId, HttpSession session) {
		GfyItem gfyItem = new GfyItem();
		gfyItem = apiService.getAGif(gifId).getGfyItem();
		ArrayList<String> tags = (ArrayList<String>) gfyItem.getTags();
		for (String tag : tags) {
			updateUserLikeTable(tag, (User) session.getAttribute("user"), count);
		}
		return new ModelAndView("redirect:/gifs");
	}

	public void updateUserLikeTable(String tag, User user, Integer count) {
		UserLikes userLike = likeDao.getUserLikes(user, tag);
		if (userLike == null) {
			userLike = new UserLikes();
			userLike.setCount(count);
			userLike.setTag(tag);
			userLike.setUser(user);
			likeDao.createUserLike(userLike);
		} else {
			Integer likes = userLike.getCount();
			likes += count;
			userLike.setCount(likes);
			likeDao.update(userLike);
		}

	}

	@RequestMapping("/pastlikegifs")
	public ModelAndView showGif(HttpSession session) {
		ModelAndView mv = new ModelAndView("top10likes");
		User user = (User) session.getAttribute("user");

		List<UserLikes> likes = (List<UserLikes>) likeDao.getUserLikes(user);
		List<UserLikes> top10 = getTopLikes(likes);

		mv.addObject("likes", top10);
		return mv;
	}

	public List<UserLikes> getTopLikes(List<UserLikes> likes) {
		Collections.sort(likes, (l1, l2) -> l1.getCount().compareTo(l2.getCount()));

		List<UserLikes> top10 = new ArrayList<>();

		for (int i = likes.size() - 1; i > likes.size() - 11; i--) {
			if (likes.get(i).getCount() > 0) {
				top10.add(likes.get(i));
			}
		}
		return top10;
	}

//	@RequestMapping("/randomgif")
//	public ModelAndView showGifFromUserPreference(HttpSession session) throws UnsupportedEncodingException {
//		User user = (User) session.getAttribute("user");
//		ModelAndView mv = new ModelAndView("randomgif");
//
//		List<UserLikes> likes = (ArrayList<UserLikes>) likeDao.getUserLikes(user);
//		ArrayList<UserLikes> top10 = getTop10(likes);
//		int randomNumber = getIntInRange(top10.size());
//		UserLikes ul = top10.get(randomNumber);
//		String tag = ul.getTag();
//		int amount = apiService.optionsLength(tag);
//		System.out.println("This is the amount" + amount + " This is the tag " + tag);
//		ArrayList<GfyItem> gfyItems = (ArrayList<GfyItem>) apiService.options(tag, amount).getGfycats();
//		int randomNumber2 = getIntInRange(amount);
//		GfyItem gifItem = gfyItems.get(randomNumber2);
//		String url = gifItem.getMax5mbGif();
//		String gifId = gifItem.getGfyId();
//		mv.addObject("gifUrl", url);
//		mv.addObject("gifId", gifId);
//		return mv;
//	}

	@RequestMapping("/storelikes")
	public ModelAndView storeLikes(@RequestParam(name = "count", required = false) Integer count,
			@RequestParam(name = "id") String gifId, HttpSession session) {
		ModelAndView mv = new ModelAndView("redirect:/randomgif");
		GfyItem gfyItem = new GfyItem();
		gfyItem = apiService.getAGif(gifId).getGfyItem();
		ArrayList<String> tags = (ArrayList<String>) gfyItem.getTags();
		for (String tag : tags) {
			updateUserLikeTable(tag, (User) session.getAttribute("user"), count);
		}
		return mv;
	}

	public int getIntInRange(int max) {
		int num = (int) (Math.random() * max);
		return num;
	}

	@RequestMapping("/feels")
	public ModelAndView feelingsData() {
		ModelAndView mv = new ModelAndView("feels");
		return mv;
	}
}
