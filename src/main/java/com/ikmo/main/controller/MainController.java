package com.ikmo.main.controller;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ikmo.main.domain.GameDomain;
import com.ikmo.main.service.MainService;
import com.ikmo.util.GameInfo;

@Controller
public class MainController {
	
	private MainService mainService;
	
	@Autowired
	public MainController(MainService mainService) {
		this.mainService = mainService;
	}
	
	@RequestMapping("/main.do")
	public String main() {
		return "/main/main.html";
	}
	
	@PostMapping("/selectTeam")
	@ResponseBody
	public ResponseEntity<Map<String, Object>> selectTeam(){
		ResponseEntity<Map<String, Object>> result = null;
		Map<String, Object> body = null;
		
		try {
			body = mainService.selectTeam();
		}catch(SQLException e) {
			result = new ResponseEntity<Map<String, Object>>(HttpStatus.INTERNAL_SERVER_ERROR);
			return result;
		}
		
		result = new ResponseEntity<Map<String, Object>>(body, HttpStatus.OK);
		return result;
	}
	
	@PostMapping("/selectPlayer")
	@ResponseBody
	public ResponseEntity<Map<String, Object>> selectPlayer(HttpSession session){
		ResponseEntity<Map<String, Object>> result = null;
		Map<String, Object> body = null;
		
		try {
			String teamName = session.getAttribute("teamName").toString();
			body = mainService.selectPlayer(teamName);
		}catch(SQLException e) {
			result = new ResponseEntity<Map<String, Object>>(HttpStatus.INTERNAL_SERVER_ERROR);
			return result;
		}
		
		result = new ResponseEntity<Map<String, Object>>(body, HttpStatus.OK);
		return result;
	}
	
	@PostMapping("/entrySubmit")
	@ResponseBody
	public ResponseEntity<Map<String, Object>> entrySubmit(@RequestBody GameDomain game, HttpSession session){
		ResponseEntity<Map<String, Object>> result = null;
		Map<String, Object> body = new HashMap<String, Object>();
		try {
			mainService.entrySubmit(game, session);
			
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
	
	//@Scheduled(cron = "0 55 20 * * *")
	@Scheduled(cron = "0 43 14 * * *")
	public void addScheduled(){
		try {
			GameInfo.gameResult = mainService.gameInfo();
		}catch(SQLException e) {
			GameInfo.gameResult = new HashMap<String, Object>();
		}
	}
	
	@Scheduled(cron = "59 59 23 * * *")
	public void removeScheduled() {
		GameInfo.gameResult = null;
	}
	
	@PostMapping("/gameInfo")
	@ResponseBody
	public ResponseEntity<Map<String, Object>> gameInfo(){
		ResponseEntity<Map<String, Object>> result = new ResponseEntity<Map<String,Object>>(GameInfo.gameResult, HttpStatus.OK);
		return result;
	}
}
