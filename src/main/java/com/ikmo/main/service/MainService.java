package com.ikmo.main.service;

import java.sql.SQLException;
import java.util.Map;

import javax.servlet.http.HttpSession;

import com.ikmo.main.domain.GameDomain;

public interface MainService {
	
	public Map<String, Object> selectTeam() throws SQLException;
	public Map<String, Object> selectPlayer(String teamName) throws SQLException;
	public void entrySubmit(GameDomain game, HttpSession session) throws SQLException;
	public Map<String, Object> gameInfo() throws SQLException; 
}
