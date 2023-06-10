package com.ikmo.login.repository;

import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.ikmo.login.domain.LoginDomain;
import com.ikmo.login.mapper.LoginSql;

@Repository
public class UserLoginRepository implements LoginRepository{

	private final JdbcTemplate jdbcTemplate;
	
	@Autowired
	public UserLoginRepository(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}
	
	@Override
	public LoginDomain loginCheck(LoginDomain login) {
		String id = login.getUserId();
		List<LoginDomain> result = jdbcTemplate.query(LoginSql.LOGIN_CHECK , loginRowMapper(), id);
		return result.isEmpty() ? null : result.get(0);
	}
	
	@Override
	public int insertUserInfo(LoginDomain login) {
		return this.jdbcTemplate.update(LoginSql.INSERT_USER_INFO, login.getUserId(), login.getPassword()); 
	}
	
	private RowMapper<LoginDomain> loginRowMapper(){
		return (rs, rowNum) -> {
			LoginDomain login = new LoginDomain();
			try {
				login.setUserId( rs.getString("ID") );
				login.setPassword( rs.getString("PASSWORD") );
				login.setTeamName( rs.getString("TEAM_NAME") );
			}catch(SQLException e) {
				login.setUserId( "" );
				login.setPassword( "" );
				login.setTeamName( "" );
			}
			return login;
		};
	}
	
}
