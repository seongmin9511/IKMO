package com.ikmo.main.repository;

import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.ikmo.main.domain.GameDomain;
import com.ikmo.main.domain.TeamDomain;
import com.ikmo.main.mapper.GameSql;
import com.ikmo.main.mapper.TeamSql;

@Repository
public class IkmoMainRepository implements MainRepository{

	private final JdbcTemplate jdbcTemplate;
	
	public IkmoMainRepository(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}
	
	@Override
	public List<TeamDomain> selectTeam() throws SQLException{
		
		RowMapper<TeamDomain> teamRowMapper = (rs, rowNum) -> {
			TeamDomain team = new TeamDomain();
			team.setTeamName(rs.getString("teamName"));
			team.setId(rs.getString("id"));
			return team;
		};
		
		List<TeamDomain> result = jdbcTemplate.query(TeamSql.SELECT_TEAM_LIST_INFO, teamRowMapper);
		
		return result;
	}
	
	@Override
	public List<TeamDomain> selectPlayer(String teamName) throws SQLException{
		
		RowMapper<TeamDomain> teamRowMapper = (rs, rowNum) -> {
			TeamDomain team = new TeamDomain();
			team.setTeamName(rs.getString("teamName"));
			team.setId(rs.getString("id"));
			return team;
		};
		
		List<TeamDomain> result = jdbcTemplate.query(TeamSql.SELECT_PLAYER_LIST_INFO, teamRowMapper, teamName);
		
		return result;
	}
	
	@Override
	public int entrySubmit(GameDomain game) throws SQLException{
		return this.jdbcTemplate.update(GameSql.ENTRY_SUBMIT
										, game.getAwayTeam()
										, game.getAwayFirstPlayer()
										, game.getAwaySecondPlayer()
										, game.getHomeTeam()
										, game.getHomeFirstPlayer()
										, game.getHomeSecondPlayer()
										, game.getAwayGameResult()
										, game.getHomeGameResult()
										);
	}
	
	@Override
	public List<GameDomain> gameInfo() throws SQLException {
		RowMapper<GameDomain> gameRowMapper = (rs, rowNum) -> {
			GameDomain game = new GameDomain();
			
			game.setGameDate(rs.getString("gameDate"));
			
			game.setAwayTeam(rs.getString("awayTeam"));
			game.setAwayFirstPlayer(rs.getString("awayFirstPlayer"));
			game.setAwaySecondPlayer(rs.getString("awaySecondPlayer"));
			
			game.setStadium(rs.getString("stadium"));
			
			game.setHomeTeam(rs.getString("homeTeam"));
			game.setHomeFirstPlayer(rs.getString("homeFirstPlayer"));
			game.setHomeSecondPlayer(rs.getString("homeSecondPlayer"));
			
			game.setAwayGameResult(rs.getString("awayGameResult"));
			game.setHomeGameResult(rs.getString("HomeGameResult"));
			
			return game;
		};
		
		List<GameDomain> result = jdbcTemplate.query(GameSql.SELECT_GAME_INFO, gameRowMapper);
		
		return result;
	}
	
	@Override
	public List<GameDomain> selectDetailGameInfo(String teamName, String sql) throws SQLException{
		RowMapper<GameDomain> gameRowMapper = (rs, rowNum) -> {
			GameDomain haveGame = new GameDomain();
			
			haveGame.setGameDate(rs.getString("gameDate"));
			
			haveGame.setAwayTeam(rs.getString("awayTeam"));
			haveGame.setAwayFirstPlayer(rs.getString("awayFirstPlayer"));
			haveGame.setAwaySecondPlayer(rs.getString("awaySecondPlayer"));
			
			haveGame.setStadium(rs.getString("stadium"));
			
			haveGame.setHomeTeam(rs.getString("homeTeam"));
			haveGame.setHomeFirstPlayer(rs.getString("homeFirstPlayer"));
			haveGame.setHomeSecondPlayer(rs.getString("homeSecondPlayer"));
			
			haveGame.setAwayGameResult(rs.getString("awayGameResult"));
			haveGame.setHomeGameResult(rs.getString("HomeGameResult"));
			
			return haveGame;
		};
		
		return jdbcTemplate.query(sql, gameRowMapper, teamName);
	}
	
	@Override
	public int updateEntryHome(GameDomain game) throws SQLException{
		
		return this.jdbcTemplate.update(GameSql.UPDATE_ENTRY_HOME
										,game.getHomeTeam()
										,game.getHomeFirstPlayer()
										,game.getHomeSecondPlayer()
										,game.getHomeTeam());
	}
	
	@Override
	public int updateEntryAway(GameDomain game) throws SQLException{
		
		return this.jdbcTemplate.update(GameSql.UPDATE_ENTRY_AWAY
										,game.getAwayTeam()
										,game.getAwayFirstPlayer()
										,game.getAwaySecondPlayer()
										,game.getAwayTeam());
	}
}
