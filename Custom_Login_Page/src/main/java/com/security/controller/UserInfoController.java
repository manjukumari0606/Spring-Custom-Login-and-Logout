package com.security.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.security.model.UserInfo;
import com.security.service.UserInfoService;

@RestController
@RequestMapping("/user")
public class UserInfoController 
{
	@Autowired
	private UserInfoService userInfoService;
	
	@GetMapping("/msg")
	public String msg() {
		return "Hiiiiiiiiiiiiiiiiiiiiiiii";
	}
	
	@PostMapping("/new")
	public String addUser(@RequestBody UserInfo userInfo) {
		return userInfoService.adduser(userInfo);
	}
	
	@GetMapping("/signin")
	public String login() {
		System.out.println("Sign in page coming.....");
		return "login";
	}
}
