package com.boneis.contoller;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.boneis.support.batchlauncher.BatchJobScheduler;
import com.boneis.support.config.Config;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {
	
	@RequestMapping(value = {"/", "/home.b1"}, method = RequestMethod.GET)
	public ModelAndView home(Locale locale, Model model) {
		ModelAndView mav = new ModelAndView("home");
		System.out.println(Config.get("test"));
		return mav;
	}
	
	@RequestMapping(value = {"/home/board.b1"})
	public String board(Locale locale, Model model) {
		try {
			BatchJobScheduler.main(null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "board";
	}
	
	@RequestMapping(value = {"/home/popPage.b1"})
	public ModelAndView popPage(HttpServletRequest request, Locale locale, Model model) {
		ModelAndView mav = new ModelAndView("popPage");
		
		mav.addObject("poptype", request.getParameter("popType"));
		mav.addObject("popparam", request.getParameter("popparam"));
				
		return mav;
	}
	
}
