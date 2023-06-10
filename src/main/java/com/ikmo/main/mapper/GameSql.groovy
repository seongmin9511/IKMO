package com.ikmo.main.mapper

class GameSql {
	/**
	 * 쿼리 내용 :게임 엔트리 저장
	 * */
	public static final String ENTRY_SUBMIT = """
		INSERT INTO GAME_INFO
		(
			GAME_DATE
			, AWAY_TEAM
			, AWAY_FIRST_PLAYER
			, AWAY_SECOND_PLAYER
			, HOME_TEAM
			, HOME_FIRST_PLAYER
			, HOME_SECOND_PLAYER
			, AWAY_GAME_RESULT
			, HOME_GAME_RESULT			
			, CREATE_DATE
		)
		SELECT
			DATE_FORMAT(NOW(), '%Y-%m-%d')
			,?
			,?
			,?
			,?
			,?
			,?
			,?
			,?
			,NOW()
		;
	""";
	
	/**
	 * 쿼리 내용 : 게임 엔트리 조회
	 * */
	public static final String SELECT_GAME_INFO = """
		SELECT
			T1.GAME_DATE as gameDate
			, T1.AWAY_TEAM as awayTeam
			, T1.AWAY_FIRST_PLAYER as awayFirstPlayer
			, T1.AWAY_SECOND_PLAYER as awaySecondPlayer
			, T1.HOME_TEAM as homeTeam
			, T1.HOME_FIRST_PLAYER as homeFirstPlayer
			, T1.HOME_SECOND_PLAYER as homeSecondPlayer
			, T1.AWAY_GAME_RESULT as awayGameResult
			, T1.HOME_GAME_RESULT as homeGameResult
			, T2.BASEBALL_STADIUM as stadium
		  FROM GAME_INFO T1
		  LEFT JOIN TEAM_INFO T2
			ON T1.HOME_TEAM = T2.TEAM_NAME
		 WHERE 1 = 1
		   AND T1.GAME_DATE = DATE_FORMAT(NOW(), '%Y-%m-%d')
		   AND ( T1.HOME_FIRST_PLAYER IS NOT NULL OR T1.HOME_SECOND_PLAYER IS NOT NULL )
		   AND ( T1.AWAY_FIRST_PLAYER IS NOT NULL OR T1.AWAY_SECOND_PLAYER IS NOT NULL );
	""";
	
	/**
	 * 쿼리 내용 : 게임 엔트리 상세 조회
	 * 목적     : 상대 팀에서 등록한 정보가 존재한다면 맞상대 팀으로 등록해주기 위해 조회
	 * */
	public static final String SELECT_DETAIL_GAME_INFO_HOME = """
		SELECT
			T1.GAME_DATE as gameDate
			, T1.AWAY_TEAM as awayTeam
			, T1.AWAY_FIRST_PLAYER as awayFirstPlayer
			, T1.AWAY_SECOND_PLAYER as awaySecondPlayer
			, T1.HOME_TEAM as homeTeam
			, T1.HOME_FIRST_PLAYER as homeFirstPlayer
			, T1.HOME_SECOND_PLAYER as homeSecondPlayer
			, T1.AWAY_GAME_RESULT as awayGameResult
			, T1.HOME_GAME_RESULT as homeGameResult
			, T2.BASEBALL_STADIUM as stadium
		  FROM GAME_INFO T1
		  LEFT JOIN TEAM_INFO T2
			ON T1.HOME_TEAM AND T2.TEAM_NAME
		 WHERE 1 = 1
		   AND T1.GAME_DATE = DATE_FORMAT(NOW(), '%Y-%m-%d')
		   AND T1.HOME_TEAM = ?
		   AND ( T1.HOME_FIRST_PLAYER IS NOT NULL OR T1.HOME_SECOND_PLAYER IS NOT NULL );
	""";
	
	/**
	 * 쿼리 내용 : 게임 엔트리 상세 조회
	 * 목적     : 상대 팀에서 등록한 정보가 존재한다면 맞상대 팀으로 등록해주기 위해 조회
	 * */
	public static final String SELECT_DETAIL_GAME_INFO_AWAY = """
		SELECT
			T1.GAME_DATE as gameDate
			, T1.AWAY_TEAM as awayTeam
			, T1.AWAY_FIRST_PLAYER as awayFirstPlayer
			, T1.AWAY_SECOND_PLAYER as awaySecondPlayer
			, T1.HOME_TEAM as homeTeam
			, T1.HOME_FIRST_PLAYER as homeFirstPlayer
			, T1.HOME_SECOND_PLAYER as homeSecondPlayer
			, T1.AWAY_GAME_RESULT as awayGameResult
			, T1.HOME_GAME_RESULT as homeGameResult
			, T2.BASEBALL_STADIUM as stadium
		  FROM GAME_INFO T1
		  LEFT JOIN TEAM_INFO T2
		    ON T1.AWAY_TEAM AND T2.TEAM_NAME
		 WHERE 1 = 1
		   AND T1.GAME_DATE = DATE_FORMAT(NOW(), '%Y-%m-%d')
		   AND T1.AWAY_TEAM = ?
		   AND ( T1.AWAY_FIRST_PLAYER IS NOT NULL OR T1.AWAY_SECOND_PLAYER IS NOT NULL );
	""";
	
	/**
	 * 쿼리 내용 : 게임 매칭된 ROW UPDATE
	 * */
	public static final String UPDATE_ENTRY_HOME = """
		UPDATE GAME_INFO SET
			HOME_TEAM = ?
			,HOME_FIRST_PLAYER = ?
			,HOME_SECOND_PLAYER = ?
			,MODIFY_DATE = NOW()
		 WHERE 1 = 1
		   AND GAME_DATE = DATE_FORMAT(NOW(), '%Y-%m-%d')
		   AND HOME_TEAM = ?
		   AND ( HOME_FIRST_PLAYER IS NULL OR HOME_SECOND_PLAYER IS NULL );
	""";
	/**
	 * 쿼리 내용 : 게임 매칭된 ROW UPDATE
	 * */
	public static final String UPDATE_ENTRY_AWAY = """
		UPDATE GAME_INFO SET
			AWAY_TEAM = ?
			,AWAY_FIRST_PLAYER = ?
			,AWAY_SECOND_PLAYER = ?
			,MODIFY_DATE = NOW()
		 WHERE 1 = 1
		   AND GAME_DATE = DATE_FORMAT(NOW(), '%Y-%m-%d')
		   AND AWAY_TEAM = ?
		   AND ( AWAY_FIRST_PLAYER IS NULL OR AWAY_SECOND_PLAYER IS NULL);
	""";
	
	/**
	 * 쿼리 내용 : 중복 체크용 쿼리 조
	 * */
	public static final String ENTRY_CHECK_HOME = """
		SELECT
			T1.GAME_DATE as gameDate
			, T1.AWAY_TEAM as awayTeam
			, T1.AWAY_FIRST_PLAYER as awayFirstPlayer
			, T1.AWAY_SECOND_PLAYER as awaySecondPlayer
			, T1.HOME_TEAM as homeTeam
			, T1.HOME_FIRST_PLAYER as homeFirstPlayer
			, T1.HOME_SECOND_PLAYER as homeSecondPlayer
			, T1.AWAY_GAME_RESULT as awayGameResult
			, T1.HOME_GAME_RESULT as homeGameResult
			, T2.BASEBALL_STADIUM as stadium
		  FROM GAME_INFO T1
		  LEFT JOIN TEAM_INFO T2
			ON T1.HOME_TEAM AND T2.TEAM_NAME
		 WHERE 1 = 1
		   AND T1.GAME_DATE = DATE_FORMAT(NOW(), '%Y-%m-%d')
		   AND T1.HOME_TEAM = ?
		   AND ( T1.HOME_FIRST_PLAYER IS NOT NULL OR T1.HOME_SECOND_PLAYER IS NOT NULL);
	""";
	/**
	 * 쿼리 내용 : 중복 체크용 쿼리 조
	 * */
	public static final String ENTRY_CHECK_AWAY = """
		SELECT
			T1.GAME_DATE as gameDate
			, T1.AWAY_TEAM as awayTeam
			, T1.AWAY_FIRST_PLAYER as awayFirstPlayer
			, T1.AWAY_SECOND_PLAYER as awaySecondPlayer
			, T1.HOME_TEAM as homeTeam
			, T1.HOME_FIRST_PLAYER as homeFirstPlayer
			, T1.HOME_SECOND_PLAYER as homeSecondPlayer
			, T1.AWAY_GAME_RESULT as awayGameResult
			, T1.HOME_GAME_RESULT as homeGameResult
			, T2.BASEBALL_STADIUM as stadium
		 FROM GAME_INFO T1
		 LEFT JOIN TEAM_INFO T2
		   ON T1.AWAY_TEAM AND T2.TEAM_NAME
		WHERE 1 = 1
		  AND T1.GAME_DATE = DATE_FORMAT(NOW(), '%Y-%m-%d')
		  AND T1.AWAY_TEAM = ?
		  AND ( T1.AWAY_FIRST_PLAYER IS NOT NULL OR T1.AWAY_SECOND_PLAYER IS NOT NULL);
	""";
}
