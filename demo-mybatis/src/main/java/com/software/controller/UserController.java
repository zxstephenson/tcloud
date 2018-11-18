package com.software.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.software.init.InitSystemMappings;
import com.software.service.UserService;

@Controller
public class UserController {

	@Autowired
	private UserService userService;
	
	@RequestMapping("/login")
	public String login(){
		
		return "login";
	}
	
	@PreAuthorize("hasRole('USER')")
	@RequestMapping("/test01")
	public String test01(){
		return "test01";
	}
	
	
	@RequestMapping("/test02")
	public String test02(){
		return "test02";
	}
	
	@RequestMapping("/test03")
	public String test03(){
		return "test03";
	}
	
	@RequestMapping("/test04")
	public String test04(){
		return "test04";
	}
	
	@RequestMapping("/queryAllMappings")
	public ModelAndView queryAllMappings(){
		
		List<String> mappings = InitSystemMappings.getListMappings();
		ModelAndView mav = new ModelAndView("");
		mav.addObject("mappings", mappings);
		return mav;
	}
}
