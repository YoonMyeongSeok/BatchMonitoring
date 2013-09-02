package com.boneis.contoller;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HomeController {
		
	/**
	 * 배치모니터링 web.xml로부터 첫 호출되는 메서드
	 * @param locale
	 * @param model
	 * @return
	 */
	@RequestMapping(value = {"/", "/home.b1"}, method = RequestMethod.GET)
	public ModelAndView home(Locale locale, Model model) {
		return new ModelAndView("home");
	}
		
	/**
	 * home.jsp 에서 리다이렉트 되는 페이지로 가기위한 메서드
	 * @param locale
	 * @param model
	 * @return
	 */
	@RequestMapping(value = {"/home/board.b1"})
	public String board(Locale locale, Model model) {
		return "board";
	}
	
}