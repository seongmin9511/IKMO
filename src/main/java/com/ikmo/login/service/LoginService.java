package com.ikmo.login.service;

import java.sql.SQLException;
import java.util.Map;

import javax.servlet.http.HttpSession;

import com.ikmo.login.domain.LoginDomain;

public interface LoginService {
	public Map<String, Object> loginCheck(LoginDomain login, HttpSession session) throws SQLException;
	public Map<String, Object> idCheck(LoginDomain login) throws SQLException;
	public void insertUserInfo(LoginDomain login) throws SQLException;
}
