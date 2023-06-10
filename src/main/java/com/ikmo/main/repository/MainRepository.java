package com.ikmo.main.repository;

import java.sql.SQLException;
import java.util.List;

import com.ikmo.main.domain.GameDomain;
import com.ikmo.main.domain.TeamDomain;

public interface MainRepository {
	public List<TeamDomain> selectTeam() throws SQLException;
	public List<TeamDomain> selectPlayer(String teamName) throws SQLException;
	public int entrySubmit(GameDomain game) throws SQLException;
	public List<GameDomain> gameInfo() throws SQLException;
	public List<GameDomain> selectDetailGameInfo(String teamName, String sql) throws SQLException;
	public int updateEntryHome(GameDomain game) throws SQLException;
	public int updateEntryAway(GameDomain game) throws SQLException;
}
