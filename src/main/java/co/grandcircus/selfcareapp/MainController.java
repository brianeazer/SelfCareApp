package co.grandcircus.selfcareapp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import co.grandcircus.selfcareapp.apiservice.ApiService;

@Controller
public class MainController {
	
	@Autowired
	ApiService apiService;
	
	@RequestMapping("/")
	public ModelAndView index() {
		apiService.getGfycatAccessToken(null);
		
		return new ModelAndView("index");
	}

	@RequestMapping("/register")
	public ModelAndView registerUser() {
		ModelAndView mav = new ModelAndView("register");
		return mav;
	}
	
	@RequestMapping("/mood")
	public ModelAndView findUserMood() {
		ModelAndView mav = new ModelAndView("mood");
	}
	
}
