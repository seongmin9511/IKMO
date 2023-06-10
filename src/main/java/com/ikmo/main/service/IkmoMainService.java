package com.ikmo.main.service;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ikmo.main.domain.GameDomain;
import com.ikmo.main.mapper.GameSql;
import com.ikmo.main.repository.MainRepository;

@Service
public class IkmoMainService implements MainService{

	private MainRepository mainRepository;
	
	@Autowired
	public IkmoMainService(MainRepository mainRepository) {
		this.mainRepository = mainRepository;
	}
	
	@Override
	public Map<String, Object> selectTeam() throws SQLException{
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("result", mainRepository.selectTeam());
		return result;
	}
	
	@Override
	public Map<String, Object> selectPlayer(String teamName) throws SQLException{
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("result", mainRepository.selectPlayer(teamName));
		return result;
	}
	
	@Override
	public void entrySubmit(GameDomain game, HttpSession session) throws SQLException{
		String homeTeam = game.getHomeTeam();
		String awayTeam = game.getAwayTeam();
		
		String myTeam  = session.getAttribute("teamName").toString();
		
		int cnt = 0;
		
		if(myTeam == null || "".equals(myTeam)) {
			throw new NullPointerException("다시 로그인을 해주시길 바랍니다.");
		}
		
		if(homeTeam != null && !"".equals(homeTeam)) {
			if(homeTeam == null || game.getAwayFirstPlayer() == null || game.getAwaySecondPlayer() == null ||
			   "".equals(homeTeam) || "".equals(game.getAwayFirstPlayer()) || "".equals(game.getAwaySecondPlayer())) {
				throw new NullPointerException("엔트리 입력 시, 필수 값에서 빠진 내용이 있습니다.\n 다시 시도해주시길 바랍니다.");
			}
			
			int check = mainRepository.selectDetailGameInfo(myTeam, GameSql.ENTRY_CHECK_AWAY).size();
			if(check > 0) {
				throw new NullPointerException("이미 엔트리를 등록하셨습니다.");
			}
			
			game.setAwayTeam(myTeam);
			cnt = mainRepository.selectDetailGameInfo(homeTeam, GameSql.SELECT_DETAIL_GAME_INFO_HOME).size();
			
			if(cnt > 0) {
				mainRepository.updateEntryAway(game);
			}else {
				mainRepository.entrySubmit(game);
			}
		}else if(awayTeam != null && !"".equals(awayTeam)) {
			if(awayTeam == null || game.getHomeFirstPlayer() == null || game.getHomeSecondPlayer() == null ||
			   "".equals(awayTeam) || "".equals(game.getHomeFirstPlayer()) || "".equals(game.getHomeSecondPlayer())) {
				throw new NullPointerException("엔트리 입력 시, 필수 값에서 빠진 내용이 있습니다.\n 다시 시도해주시길 바랍니다.");
			}
			
			int check = mainRepository.selectDetailGameInfo(myTeam, GameSql.ENTRY_CHECK_HOME).size();
			if(check > 0) {
				throw new NullPointerException("이미 엔트리를 등록하셨습니다.");
			}
			
			game.setHomeTeam(myTeam);
			cnt = mainRepository.selectDetailGameInfo(awayTeam, GameSql.SELECT_DETAIL_GAME_INFO_AWAY).size();
			
			if(cnt > 0) {
				mainRepository.updateEntryHome(game);
			}else {
				mainRepository.entrySubmit(game);
			}
		}else {
			throw new SQLException("엔트리 입력 시, 필수 값에서 빠진 내용이 있습니다.\\n 다시 시도해주시길 바랍니다.");
		}
	}
	
	@Override
	public Map<String, Object> gameInfo() throws SQLException{
		Map<String, Object> result = new HashMap<String, Object>();
		List<GameDomain> domainList = mainRepository.gameInfo();
		int domainSize = domainList.size();
		String table = "";
			   table += "<table>";
			   table += "	<tr>";
			   table += "		<th>AWAY팀명</th>";
			   table += "		<th>AWAY선수명</th>";
			   table += "		<th>" + domainList.get(0).getGameDate() + "</th>";
			   table += "		<th>HOME선수명</th>";
			   table += "		<th>HOME팀명</th>";
			   table += "	</tr>";
		for(int i = 0; i < domainSize; i++) {
			GameDomain gameDomain = domainList.get(i);
			
			table += "<tr>";
			table += "	<td Rowspan='2'>" + gameDomain.getAwayTeam() + "</td>";
			table += "	<td>" + gameDomain.getAwayFirstPlayer() + "</td>";
			table += "	<td Rowspan='2'> VS \n" + gameDomain.getStadium() + "</td>";
			table += "	<td>" + gameDomain.getHomeFirstPlayer() + "</td>";
			table += "	<td></td>";
			table += "</tr>";
			table += "<tr>";
			table += "	<td></td>";
			table += "	<td>" + gameDomain.getAwaySecondPlayer() + "</td>";
			table += "	<td></td>";
			table += "	<td>" + gameDomain.getHomeSecondPlayer() + "</td>";
			table += "  <td Rowspan='2'>" + gameDomain.getHomeTeam() + "</td>";
			table += "</tr>";
			
		}
		
		table += "</table>";
		
		result.put("gameInfo", table);
		
		return result;
	}
	
}
