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
import co.grandcircus.selfcareapp.apiservice.ApiService;
import co.grandcircus.selfcareapp.model.GifResponse;

@Controller
public class MainController {
	
	@Autowired
	ApiService apiService;
	
	@RequestMapping("/")
	public ModelAndView index() {
		String token = apiService.getGfycatAccessToken("").getAccess_token();
		apiService.getGifInCategory("cat");
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
		String[] gifIds = {"vibrantuniquekiwi", "enviousimmaculateflatcoatretriever", "tightfluffyaustraliankelpie", "masculinecalmeelelephant"};
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
		String[] gifIds = {"vibrantuniquekiwi", "enviousimmaculateflatcoatretriever", "tightfluffyaustraliankelpie", "masculinecalmeelelephant"};
		String gifId = gifIds[count];
		String gifUrl = apiService.getAGif(gifId).getGfyItem().getGifUrl();
		return new ModelAndView("flavorProfile", "gif", gifUrl);
	}
	@RequestMapping("/store-info") 
	public ModelAndView addToDatabase(@RequestParam(name = "like") boolean like) {
		
		System.out.println(like);
		
		
		return new ModelAndView("redirect:/flavorprofile");
	}
	
	
}
