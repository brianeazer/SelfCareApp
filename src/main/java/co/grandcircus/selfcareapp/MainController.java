package co.grandcircus.selfcareapp;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import co.grandcircus.selfcareapp.Dao.LikeDao;
import co.grandcircus.selfcareapp.Dao.UserDao;
import co.grandcircus.selfcareapp.Dao.UserEmotionDao;
import co.grandcircus.selfcareapp.Entity.GfyItem;
import co.grandcircus.selfcareapp.Entity.User;
import co.grandcircus.selfcareapp.Entity.UserEmotion;
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

	@Autowired
	UserEmotionDao userEmotionDao;

	@RequestMapping("/")
	public ModelAndView index() {
		return new ModelAndView("index");
	}

	@PostMapping("/")
	public ModelAndView submitLogin(@RequestParam(name = "username") String username,
			@RequestParam(name = "password") String password, RedirectAttributes redir, HttpSession session) {
		session.setMaxInactiveInterval(20 * 60);

		// checks if user exists in the database and that the username and password are
		// correct
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

		// list of all categories
		List<String> categories = new ArrayList<String>(
				Arrays.asList("Your Top Ten", "Food", "Cats", "Sports", "Fails", "Nature", "Chill"));
		mav.addObject("categories", categories);

		return mav;
	}

	@RequestMapping("/feels")
	public ModelAndView showFeels(HttpSession session) {
		ModelAndView mav = new ModelAndView("feels");

		// pulls list of user's past emotions from the database
		User user = (User) session.getAttribute("user");
		ArrayList<UserEmotion> userEmotions = (ArrayList<UserEmotion>) userEmotionDao.getUserEmotions(user);
		mav.addObject("userEmotions", userEmotions);

		return mav;
	}

	@RequestMapping("/gifs")
	public ModelAndView moodCategory(HttpSession session, @RequestParam(name = "category") String category,
			RedirectAttributes redir, @RequestParam(name = "slidervalue", required = false) Integer moodRating) {
		User user = (User) session.getAttribute("user");

		// adds user's new emotion from mood page in the parameter to the database w/ a
		// date
		if (moodRating != null) {
			DateFormat df = new SimpleDateFormat("MM/dd/yy");
			Date today = new Date();
			System.out.println(df.format(today));
			UserEmotion userEmotion = new UserEmotion();
			userEmotion.setEmotionRating(moodRating);
			userEmotion.setDate(today);
			userEmotion.setUser(user);
			userEmotionDao.createUserEmotion(userEmotion);
		}

		ModelAndView mav = new ModelAndView("randomgif");
		mav.addObject("category", category);

		// map of all categories tags, with the category name as key
		Map<String, List<String>> categories = new HashMap<>();
		categories.put("Food", Arrays.asList("recipe, food", "foodnetwork"));
		categories.put("Cats", Arrays.asList("kittens", "cute kittens", "cats, aww"));
		categories.put("Sports", Arrays.asList("sports"));
		categories.put("Fails", Arrays.asList("fail", "epicfail"));
		categories.put("Nature",
				Arrays.asList("waterfalls", "nature", "forest, aesthetic", "forest, relaxing", "forest, ASMR"));
//		categories.put("Scare", Arrays.asList("crazy"));
		categories.put("Chill", Arrays.asList("lofi", "chillwave", "meditation", "relaxing"));
		categories.put("Your Top Ten", Arrays.asList(""));

		List<GfyItem> gifs = new ArrayList<>();
		// if user selected "random" will give them gifs based on their preferences
		// else will choose randomly from selected category
		if (category.equals("Your Top Ten")) {

			// gets complete list of "likes" and sorts top 10 (if positive) likes
			List<UserLikes> likes = likeDao.getUserLikes(user);
			
			// if list is not at least 10 tags, redirect to mood page to like more gifs
			if (likes.size() < 10) {
				redir.addFlashAttribute("message", "Sorry, you need to like at least ten tags to view this page!");
				return new ModelAndView("redirect:/mood"); 
			}
			
			// if list is at least 10 tags
			List<UserLikes> topLikes = getTopLikes(likes);
			
			// gets random number to select index of a top like
			int indexTopLikes = getIntInRange(topLikes.size());
			UserLikes ul = topLikes.get(indexTopLikes);
			String tag = ul.getTag();

			// gets list of gifs based on chosen tags
			// TODO: figure out a way to possibly get more than 10 thru cursor
			List<GfyItem> gfyItems = apiService.options(tag, 10).getGfycats();

			// gets the random index based on the list's size and finds gif at that random
			// index
			int indexGifList = getIntInRange(gfyItems.size());
			GfyItem gifItem = gfyItems.get(indexGifList);

			// adds the gif and the gifId to the view
			mav.addObject("gif", gifItem.getMax5mbGif());
			mav.addObject("gifId", gifItem.getGfyId());
		} else {
			// grab the list based on the category
			List<String> keywords = categories.get(category);
			// for each keyword in the list...
			for (String keyword : keywords) {
				// grab results, add it to a general list
				GifResponse gifResponse = apiService.options(keyword, 1000);
				gifs.addAll(gifResponse.getGfycats());
			}
			// randomly select an index
			int index = (int) Math.floor(Math.random() * gifs.size());
			// find item at that index & show the gif
			GfyItem gfyItem = gifs.get(index);
			mav.addObject("gif", gfyItem.getMax5mbGif());
			mav.addObject("gifId", gfyItem.getGfyId());
		}
		return mav;
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
		gfyItem = apiService.getAGif(gifId).getGfyItem();
		ArrayList<String> tags = (ArrayList<String>) gfyItem.getTags();
		for (String tag : tags) {
			updateUserLikeTable(tag, (User) session.getAttribute("user"), count);
		}
		Integer num = (Integer) session.getAttribute("count");
		if (num == 14) {
			return new ModelAndView("mood");
		}
		return new ModelAndView("redirect:/flavorprofile");
	}

	@RequestMapping("/random-store-info")
	public ModelAndView addRandomToDatabase(@RequestParam(name = "count", required = false) Integer count,
			@RequestParam(name = "id") String gifId, @RequestParam(name = "category") String category,
			HttpSession session) {
		ModelAndView mav = new ModelAndView("redirect:/gifs");
		GfyItem gfyItem = new GfyItem();
		gfyItem = apiService.getAGif(gifId).getGfyItem();
		System.out.println(category);
		ArrayList<String> tags = (ArrayList<String>) gfyItem.getTags();
		for (String tag : tags) {
			updateUserLikeTable(tag, (User) session.getAttribute("user"), count);
		}
		mav.addObject("category", category);
		return mav;
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
	public ModelAndView showGif(HttpSession session, RedirectAttributes redir) {
		ModelAndView mv = new ModelAndView("top10likes");
		User user = (User) session.getAttribute("user");

		List<UserLikes> likes = (List<UserLikes>) likeDao.getUserLikes(user);

		if (likes.size() >= 10) {
			List<UserLikes> top10 = getTopLikes(likes);

			int indexTopLikes = getIntInRange(top10.size());
			UserLikes ul = top10.get(indexTopLikes);
			String tag = ul.getTag();

			List<GfyItem> gfyItems = apiService.options(tag, 10).getGfycats();
			int indexGifList = getIntInRange(gfyItems.size());
			GfyItem gifItem = gfyItems.get(indexGifList);
			mv.addObject("gif", gifItem);

			mv.addObject("likes", top10);
			return mv;
		} else {
			redir.addFlashAttribute("message", "Sorry, you need to like at least ten tags to view this page!");
			return new ModelAndView("redirect:/mood"); 
		}
	}

	public List<UserLikes> getTopLikes(List<UserLikes> likes) {
		Collections.sort(likes, (l1, l2) -> l1.getCount().compareTo(l2.getCount()));

		List<UserLikes> top10 = new ArrayList<>();
		int count = 0;
		int index = likes.size() - 1;
		while (count < 10 || likes.get(index).getTag() != null) {
			if (likes.get(index).getTag() != null) {
				top10.add(likes.get(index - 1));
				count++;
			}

			index--;
		}
		return top10;
	}

	@RequestMapping("/storelikes")
	public ModelAndView storeLikes(@RequestParam(name = "count", required = false) Integer count,
			@RequestParam(name = "id") String gifId, HttpSession session) {
		ModelAndView mv = new ModelAndView("redirect:/randomgif");
		GfyItem gfyItem = apiService.getAGif(gifId).getGfyItem();
		ArrayList<String> tags = (ArrayList<String>) gfyItem.getTags();
		for (String tag : tags) {
			updateUserLikeTable(tag, (User) session.getAttribute("user"), count);
		}
		return mv;
	}

	public int getIntInRange(int max) {
		int num = (int) Math.floor(Math.random() * max);
		return num;
	}

	@WebServlet("/ErrorHandler")
	public class ErrorHandler extends HttpServlet {
		private static final long serialVersionUID = 1L;

		protected void doGet(HttpServletRequest request, HttpServletResponse response)
				throws ServletException, IOException {
			processError(request, response);
		}

		protected void doPost(HttpServletRequest request, HttpServletResponse response)
				throws ServletException, IOException {
			processError(request, response);
		}

		private void processError(HttpServletRequest request, HttpServletResponse response)
				throws IOException, ServletException {
			// customize error message
			Throwable throwable = (Throwable) request.getAttribute("javax.servlet.error.exception");
//	        Integer statusCode = (Integer) request
//	                .getAttribute("javax.servlet.error.status_code");
			String servletName = (String) request.getAttribute("javax.servlet.error.servlet_name");
			if (servletName == null) {
				servletName = "Unknown";
			}
			String requestUri = (String) request.getAttribute("javax.servlet.error.request_uri");
			if (requestUri == null) {
				requestUri = "Unknown";
			}
			request.setAttribute("error", "Servlet " + servletName + " has thrown an exception "
					+ throwable.getClass().getName() + " : " + throwable.getMessage());
			request.getRequestDispatcher("/error.jsp").forward(request, response);
		}

	}

}
