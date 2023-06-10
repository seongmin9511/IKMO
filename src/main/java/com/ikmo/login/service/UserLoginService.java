package com.ikmo.login.service;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ikmo.login.domain.LoginDomain;
import com.ikmo.login.repository.LoginRepository;

@Service
public class UserLoginService implements LoginService{
	
	private LoginRepository loginRepository;
	
	@Autowired
	public UserLoginService(LoginRepository loginRepository) {
		this.loginRepository = loginRepository;
	}

	@Override
	public Map<String, Object> loginCheck(LoginDomain login, HttpSession session) throws SQLException {
		Map<String, Object> result = new HashMap<String, Object>();
		
		PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		
		String inputPassword = login.getPassword();
		
		LoginDomain userInfo = loginRepository.loginCheck(login);
		
		if(userInfo != null) {
			String userPassword = userInfo.getPassword();
			
			Boolean loginCheck = passwordEncoder.matches(inputPassword, userPassword);
			
			if(loginCheck) {
				session.setAttribute("userId", userInfo.getUserId());
				session.setAttribute("teamName", userInfo.getTeamName());
				session.setAttribute("grade", userInfo.getGrade());
				session.setAttribute("password", userPassword);
				
				session.setMaxInactiveInterval(60 * 60 * 3);
				
				result.put("data", userInfo);
			}
			
			result.put("loginCheck", loginCheck);
		}
		
		return result;
	}
	
	@Override
	public Map<String, Object> idCheck(LoginDomain login) throws SQLException {
		Map<String, Object> result = new HashMap<String, Object>();
		
		LoginDomain userInfo = loginRepository.loginCheck(login);
		boolean useCheck = false;
		
		if(userInfo != null) {
			useCheck = false;
		}else {
			useCheck = true;
		}
		
		result.put("useCheck", useCheck);
		
		return result;
	}
	
	@Override
	public void insertUserInfo(LoginDomain login) throws SQLException{
		if(login.getUserId() == null || login.getPassword() == null) {
			throw new NullPointerException();
		}
		
		PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		
		String encodePassword = passwordEncoder.encode( login.getPassword() );
		
		login.setPassword(encodePassword);
		
		loginRepository.insertUserInfo(login);
	}
}
