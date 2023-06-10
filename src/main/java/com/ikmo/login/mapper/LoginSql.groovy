package com.ikmo.login.mapper

class LoginSql {
	public static final String LOGIN_CHECK = """
		SELECT
			*
		  FROM USER_INFO AS A
         WHERE 1 = 1
		   AND ID = ?;
""";

	public static final String INSERT_USER_INFO = """
		INSERT INTO USER_INFO (ID, PASSWORD, TEAM_NAME, CREATE_DATE)
		VALUES(?, ?, ?, NOW());
""";
}
