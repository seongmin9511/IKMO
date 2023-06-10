package com.ikmo.main.mapper

class TeamSql {
	public static final String SELECT_TEAM_LIST_INFO = """
		SELECT
			TEAM_NAME AS teamName
			,ID AS id
		  FROM USER_INFO
		 WHERE 1 = 1
		   AND USE_YN = 'Y'
		   AND GRADE = '2'
	""";

	public static final String SELECT_PLAYER_LIST_INFO = """
		SELECT
			TEAM_NAME AS teamName
			,ID AS id
		  FROM USER_INFO
		 WHERE 1 = 1
		   AND USE_YN = 'Y'
		   AND TEAM_NAME = ?
	""";
}
