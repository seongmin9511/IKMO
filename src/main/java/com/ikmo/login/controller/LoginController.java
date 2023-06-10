package com.ikmo.login.controller;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ikmo.login.domain.LoginDomain;
import com.ikmo.login.service.LoginService;

@Controller
public class LoginController {
	
	private LoginService loginService;
	
	@Autowired
	public LoginController(LoginService loginService) {
		this.loginService = loginService;
	}
	
	@RequestMapping({"/", "/login.do"})
	public String login() {
		return "/login/login.html";
	}
	
	@RequestMapping("/signUp.do")
	public String signUp() {
		return "/login/signUp.html";
	}
	
	@RequestMapping("/redirectLogin")
	public String redirectLogin() {
		return "redirect:/login.do";
	}
	
	@PostMapping("/loginCheck")
	@ResponseBody
	public ResponseEntity<Map<String, Object>> loginCheck(@RequestBody LoginDomain login,
										  				  HttpSession session){
		ResponseEntity<Map<String, Object>> result = null;
		Map<String, Object> body = null;
		
		try {
			body = loginService.loginCheck(login, session);
		}catch(SQLException e) {
			result = new ResponseEntity<Map<String,Object>>(HttpStatus.INTERNAL_SERVER_ERROR);
			return result;
		}
		
		result = new ResponseEntity<Map<String, Object>>(body, HttpStatus.OK);
		
		
		return result;
	}
	
	@PostMapping("/idCheck")
	@ResponseBody
	public ResponseEntity<Map<String, Object>> idCheck(@RequestBody LoginDomain login){
		ResponseEntity<Map<String, Object>> result = null;
		Map<String, Object> body = null;
		
		try {
			body = loginService.idCheck(login);
		}catch(SQLException e) {
			result = new ResponseEntity<Map<String,Object>>(HttpStatus.INTERNAL_SERVER_ERROR);
			return result;
		}
		
		result = new ResponseEntity<Map<String,Object>>(body, HttpStatus.OK);
		
		return result;
	}
	
	@PostMapping("/insertUserInfo")
	@ResponseBody
	public ResponseEntity<Map<String, Object>> insertUserInfo(@RequestBody LoginDomain login){
		ResponseEntity<Map<String, Object>> result = null;
		Map<String, Object> body = new HashMap<String, Object>();
		try {
			loginService.insertUserInfo(login);
			
		}catch(SQLException e) {
			body.put("insertChk", false);
			body.put("Message", e.getMessage());
			result = new ResponseEntity<Map<String,Object>>(body, HttpStatus.INTERNAL_SERVER_ERROR);
			return result;
		}catch(NullPointerException e) {
			body.put("insertChk", false);
			body.put("Message", e.getMessage());
			result = new ResponseEntity<Map<String,Object>>(body, HttpStatus.INTERNAL_SERVER_ERROR);
			return result;
		}
		body.put("insertChk", true);
		result = new ResponseEntity<Map<String,Object>>(body, HttpStatus.OK);
		
		return result;
	}
	
	
}
