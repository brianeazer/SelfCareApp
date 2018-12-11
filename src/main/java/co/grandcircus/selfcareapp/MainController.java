package co.grandcircus.selfcareapp;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

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
import co.grandcircus.selfcareapp.model.MoodSummary;

@Controller
public class MainController {

	private static final int GIF_COUNT_BETWEEN_CHECKIN = 5;

	@Autowired
	ApiService apiService;

	@Autowired
	LikeDao likeDao;

	@Autowired
	UserDao userDao;

	@Autowired
	UserEmotionDao userEmotionDao;

	@Autowired
	GifService gifService;

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
		Set<String> categories = gifService.categoryMap().keySet();
		mav.addObject("categories", categories);
		return mav;
	}

	@RequestMapping("/feels")
	public ModelAndView showFeels(HttpSession session) {
		ModelAndView mav = new ModelAndView("feels");

		// pulls list of user's past emotions from the database
		User user = (User) session.getAttribute("user");
		System.out.println(user.getId());
		List<UserEmotion> userEmotions = userEmotionDao.getUserEmotions(user);
		System.out.println(userEmotions);

		Map<LocalDate, List<UserEmotion>> daysOfWeek = new TreeMap<>(Comparator.reverseOrder());
		Integer howManyDays = 0;

		for (UserEmotion userEmotion : userEmotions) {
			LocalDate date = gifService.convertToLocalDateViaInstant(userEmotion.getDate());
			if (daysOfWeek.containsKey(date)) {
				List<UserEmotion> timeAndFeels = daysOfWeek.get(date);
				timeAndFeels.add(userEmotion);
				daysOfWeek.put(date, timeAndFeels);
			} else {
				daysOfWeek.put(date, new ArrayList<>(Collections.singleton(userEmotion)));
				howManyDays++;
			}

		}

		//Get a list of averages for how the user was feeling on each day
		ArrayList<Double> averageMoodRatings = gifService.getAverageMoodRating(daysOfWeek);
		// find the most liked category on a certain day
		ArrayList<String> categories = gifService.getTopCategories(daysOfWeek);
		System.out.println("marks categories are " + categories);
		mav.addObject("categories", categories);
		mav.addObject("averageMoodRatings", averageMoodRatings);
		mav.addObject("days", howManyDays);
		mav.addObject("daysOfWeek", daysOfWeek);
		mav.addObject("userEmotions", userEmotions);
		return mav;
	}

	@RequestMapping("/gifs")
	public ModelAndView moodCategory(HttpSession session, @RequestParam(name = "category") String category,
			RedirectAttributes redir) {
		User user = (User) session.getAttribute("user");

		ModelAndView mav = new ModelAndView("randomgif");
		// adds the current category to the JSP for display
		mav.addObject("category", category);

		// map of all categories tags, with the category name as key
		Map<String, List<String>> categories = gifService.categoryMap();

		List<GfyItem> gifs = new ArrayList<>();

		// if user selected "top ten" will give them gifs based on their preferences
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
			List<UserLikes> topLikes = gifService.getTopLikes(likes);

			List<GfyItem> gfyItems = gifService.getGifList(topLikes);

			// gets the random index based on the list's size and finds gif at that random
			// index
			int indexGifList = gifService.randomInteger(gfyItems.size());
			GfyItem gifItem = gfyItems.get(indexGifList);

			// adds the gif and the gifId to the view
			mav.addObject("gif", gifItem.getMax5mbGif());
			mav.addObject("gifId", gifItem.getGfyId());
			mav.addObject("categories", categories.keySet());
		} else {
			// grab the list based on the category
			List<String> keywords = categories.get(category);
			// for each keyword in the list...
			for (String keyword : keywords) {
				// grab results, add it to a general list
				GifResponse gifResponse = apiService.options(keyword, 50);
				gifs.addAll(gifResponse.getGfycats());
			}
			// randomly select an index
			int index = gifService.randomInteger(gifs.size());

			// find item at that index & show the gif
			GfyItem gfyItem = gifs.get(index);
			mav.addObject("gif", gfyItem.getMax5mbGif());
			mav.addObject("gifId", gfyItem.getGfyId());
			mav.addObject("categories", categories.keySet());
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

		// set 14 gifs to get a user's initial preference
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

	@RequestMapping("/pastlikegifs")
	public ModelAndView showGif(HttpSession session, RedirectAttributes redir) {
		ModelAndView mv = new ModelAndView("top10likes");
		User user = (User) session.getAttribute("user");

		List<UserLikes> likes = (List<UserLikes>) likeDao.getUserLikes(user);
		if (likes.size() >= 10) {
			// finds user's top tags and then choose one based on weighted probability
			List<UserLikes> topLikes = gifService.getTopLikes(likes);

			List<GfyItem> gfyItems = gifService.getGifList(topLikes);

			int indexGifList = gifService.randomInteger(gfyItems.size());
			GfyItem gifItem = gfyItems.get(indexGifList);
			mv.addObject("gif", gifItem);

			mv.addObject("likes", topLikes);
			return mv;
		} else {
			redir.addFlashAttribute("message", "Sorry, you need to like at least ten tags to view this page!");
			return new ModelAndView("redirect:/mood");
		}
	}

	@RequestMapping("/top10-store-info")
	public ModelAndView addTop10ToDatabase(@RequestParam(name = "count", required = false) Integer count,
			@RequestParam(name = "id") String gifId, HttpSession session) {
		ModelAndView mav = new ModelAndView("redirect:/pastlikegifs");
		GfyItem gfyItem = new GfyItem();
		gfyItem = apiService.getAGif(gifId).getGfyItem();
		ArrayList<String> tags = (ArrayList<String>) gfyItem.getTags();
		for (String tag : tags) {
			gifService.updateUserLikeTable(tag, (User) session.getAttribute("user"), count);
		}
		return mav;
	}

	@RequestMapping("/storelikes")
	public ModelAndView storeLikes(@RequestParam(name = "count", required = false) Integer count,
			@RequestParam(name = "id") String gifId, HttpSession session) {
		ModelAndView mv = new ModelAndView("redirect:/randomgif");
		GfyItem gfyItem = apiService.getAGif(gifId).getGfyItem();
		ArrayList<String> tags = (ArrayList<String>) gfyItem.getTags();
		for (String tag : tags) {
			gifService.updateUserLikeTable(tag, (User) session.getAttribute("user"), count);
		}
		return mv;
	}

	@RequestMapping("/store-info")
	public ModelAndView addToDatabase(@RequestParam(name = "count", required = false) Integer count,
			@RequestParam(name = "id") String gifId, HttpSession session) {
		GfyItem gfyItem = new GfyItem();
		gfyItem = apiService.getAGif(gifId).getGfyItem();
		System.out.println(gfyItem.getGifUrl());
		ArrayList<String> tags = (ArrayList<String>) gfyItem.getTags();
		for (String tag : tags) {
			gifService.updateUserLikeTable(tag, (User) session.getAttribute("user"), count);
		}

		Integer num = (Integer) session.getAttribute("count");

		// 14 is hardcoded from the array of gifIds in the getUserProfile method
		if (num == 14) {
			return new ModelAndView("redirect:/mood");
		}

		return new ModelAndView("redirect:/flavorprofile");
	}

	@RequestMapping("/random-store-info")
	public ModelAndView addRandomToDatabase(@RequestParam(name = "count", required = false) Integer rating,
			@RequestParam(name = "id") String gifId, @RequestParam(name = "category") String category,
			HttpSession session, RedirectAttributes redir) {

		GfyItem gfyItem = new GfyItem();
		gfyItem = apiService.getAGif(gifId).getGfyItem();
		ArrayList<String> tags = (ArrayList<String>) gfyItem.getTags();
		for (String tag : tags) {
			gifService.updateUserLikeTable(tag, (User) session.getAttribute("user"), rating);
		}

		if (session.getAttribute("count") == null) {
			session.setAttribute("count", 1);
			return new ModelAndView("redirect:/gifs", "category", category);
		} else {
			session.setAttribute("count", (int) (session.getAttribute("count")) + 1);
			if ((int) session.getAttribute("count") % GIF_COUNT_BETWEEN_CHECKIN == 0) {
				redir.addFlashAttribute("message", "Please pic another category and select your mood");
				return new ModelAndView("redirect:/mood");
			} else {
				return new ModelAndView("redirect:/gifs", "category", category);
			}
		}
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

	@RequestMapping("/checkin")
	public ModelAndView addGif(HttpSession session) {
		ModelAndView mv = new ModelAndView("checkin");

		Set<String> categories = gifService.categoryMap().keySet();
		mv.addObject("categories", categories);

		return mv;
	}

	@RequestMapping("/mood-summary")
	public ModelAndView moodSummary(HttpSession session,
			@RequestParam(name = "slidervalue", required = false) Integer moodRating,
			@RequestParam("category") String category) {
		User user = (User) session.getAttribute("user");

		// adds user's new emotion from mood page in the parameter to the database w/ a
		// date
		if (moodRating != null) {
			Date today = new Date();
			UserEmotion userEmotion = new UserEmotion();
			userEmotion.setEmotionRating(moodRating);
			userEmotion.setDate(today);
			userEmotion.setUser(user);
			userEmotionDao.createUserEmotion(userEmotion);
		}

		// grab finished tracking "timeframe"
		MoodSummary prevMoodSummary = (MoodSummary) session.getAttribute("moodSummary");
		// start a new tracking timeframe
		session.setAttribute("moodSummary", new MoodSummary());

		if (prevMoodSummary == null) {
			// It's the first time. Skip the summary.
			return new ModelAndView("redirect:/gifs", "category", category);
		} else {
			ModelAndView mv = new ModelAndView("mood-summary");
			mv.addObject("moodSummary", prevMoodSummary);
			return mv;
		}
	}

	@RequestMapping("/test")
	public ModelAndView test() {
		Date date = new Date();
		date.setYear(2018);
		date.setMonth(12);
		date.setDate(10);
		List<UserEmotion> userEmotions = userEmotionDao.getEmotionByDate(date);
		System.out.println(userEmotions.get(0));
		return new ModelAndView("test");

	}

}
