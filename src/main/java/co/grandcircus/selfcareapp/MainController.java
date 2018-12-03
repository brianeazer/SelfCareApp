package co.grandcircus.selfcareapp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
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
		String gifId = "vibrantuniquekiwi";
		String gifUrl = apiService.getAGif(gifId).getGfyItem().getGifUrl();
		return new ModelAndView("test", "gif", gifUrl);
	}

}
