package com.ikmo.login.repository;


import com.ikmo.login.domain.LoginDomain;

public interface LoginRepository {
	public LoginDomain loginCheck(LoginDomain login);
	public int insertUserInfo(LoginDomain login);
}
