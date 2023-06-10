$(document).ready(function(){
/** 전역번수 관리자 START */
	
	let gameContent		= document.getElementById("gameContent");
	let entrySubmission = document.getElementById("entrySubmission");
	let gameTab         = document.getElementById("game_tab");
	let entryTab		= document.getElementById("entry_tab");
	let teamModal       = document.querySelector(".team_modal");
	let teamBtn         = document.getElementById("teamBtn");
	let teamModalBtn    = document.querySelectorAll(".teamModalBtn");
	let teamBg          = document.querySelectorAll('.team_bg');
	let tabItem 		= document.querySelectorAll(".tab-container__item");
	let tabContent 		= document.querySelectorAll(".content-container__content");
	let teamContent     = document.getElementById("teamContent");
	let player1Btn      = document.getElementById("player1Btn");
	let player1Content  = document.getElementById("player1Content");
	let player1Modal    = document.querySelector(".player1_modal")
	let player2Btn      = document.getElementById("player2Btn");
	let player2Content  = document.getElementById("player2Content");
	let player2Modal    = document.querySelector(".player2_modal")
	let gameTable       = document.getElementById("gameTable");
	
/** 전역번수 관리자 END */

/** 커스텀 함수 관리자 START */

	function tabHandler(item) {
	  	const tabTarget = item.currentTarget;
	  	const target = tabTarget.dataset.tab;
	  	
	  	tabItem.forEach((title) => {
	    	title.classList.remove("active");
	  	});
	  	
	  	tabContent.forEach((target) => {
	    	target.classList.remove("target");
	  	});
	  	
	  	document.querySelector("#" + target).classList.add("target");
	  	tabTarget.classList.add("active");
	}
	
	tabItem.forEach((item) => {
  		item.addEventListener("click", tabHandler);
	});
	
	let gameInterval = setInterval(() => 
		$.ajax({
			url : "/gameInfo",
			method : "POST",
			dataType : "json",
			contentType : "application/json; charset=utf-8;",
			success : function(result){
				gameTable.innerHTML = "";
				gameTable.innerHTML = result.gameInfo;
			}
		}), 10000
	);
	
	
	/** open/close 관리 START*/
	
		//팀 팝업 오픈
		function team_open(){
			teamContent.innerHTML = "";
			$.ajax({
				url: "/selectTeam",
				method: "POST",
				dataType : 'json',
				contentType : 'application/json; charset=utf-8;',
				success: function(result){
					let table = "";
						table += "<table>"
						table += "	<tr>"
						table += "		<th>팀명</th>"
						table += "		<th>감독</th>"
						table += "	</tr>"
						
					let size = result.result.length;
					
					if(size == 0){
						table += "	<tr>"
						table += "		<td>등록되어 있는 팀이 존재하지 않습니다.</td>"
						table += "	</tr>"
					}else{
						for(let i = 0; i < size; i++){
							let data = result.result[i];
						
							table += "	<tr>"
							table += "		<td>" + data.teamName + "</td>"
							table += "		<td>" + data.id + "</td>"
							table += "	</tr>"
						}
					}
					
					table += "</table>"
					
					teamContent.innerHTML = table;
					
					document.querySelectorAll("#teamContent table tr")[1].classList.add("selectRow");
					
					teamModal.classList.remove("hidden");
				},
				error: function(){
					alert("팝업창 조회 문제가 발생하였습니다.\n다시 시도해주시길 바랍니다.")
				}
			});
		}
		
		//팀 팝업 클로즈
		function team_close() {
			$("#teamContent table *").find('.selectRow').removeClass("selectRow")
			teamModal.classList.add("hidden");
		}
		
		//선수1 팝업 오픈
		function player1_open(){
			player1Content.innerHTML = "";
			$.ajax({
				url: "/selectPlayer",
				method: "POST",
				dataType: "json",
				contentType : 'application/json; charset=utf-8;',
				success: function(result){
					let table = "";
						table += "<table>"
						table += "	<tr>"
						table += "		<th>선수ID</th>"
						table += "	</tr>"
						
					let size = result.result.length;
					
					if(size == 0){
						table += "	<tr>"
						table += "		<td>팀에 존재하는 선수가 없습니다.</td>"
						table += "	</tr>"
					}else{
						for(let i = 0; i < size; i++){
							let data = result.result[i];
						
							table += "	<tr>"
							table += "		<td>" + data.id + "</td>"
							table += "	</tr>"
						}
					}
					
					table += "</table>"
					
					player1Content.innerHTML = table;
					
					document.querySelectorAll("#player1Content table tr")[1].classList.add("selectRow");
					
					player1Modal.classList.remove("hidden");
				},
				error: function(){
					alert("팝업창 조회 문제가 발생하였습니다.\n다시 시도해주시길 바랍니다.")
				}
			});
		}
		
		//선수1 팝업 클로즈
		function player1_close() {
			$("#player1Content table *").find('.selectRow').removeClass("selectRow")
			player1Modal.classList.add("hidden");
		}
		
		//선수2 팝업 오픈
		function player2_open(){
			player2Content.innerHTML = "";
			$.ajax({
				url: "/selectPlayer",
				method: "POST",
				dataType: "json",
				contentType : 'application/json; charset=utf-8;',
				success: function(result){
					let table = "";
						table += "<table>"
						table += "	<tr>"
						table += "		<th>선수ID</th>"
						table += "	</tr>"
						
					let size = result.result.length;
					
					if(size == 0){
						table += "	<tr>"
						table += "		<td>팀에 존재하는 선수가 없습니다.</td>"
						table += "	</tr>"
					}else{
						for(let i = 0; i < size; i++){
							let data = result.result[i];
						
							table += "	<tr>"
							table += "		<td>" + data.id + "</td>"
							table += "	</tr>"
						}
					}
					
					table += "</table>"
					
					player2Content.innerHTML = table;
					
					document.querySelectorAll("#player2Content table tr")[1].classList.add("selectRow");
					
					player2Modal.classList.remove("hidden");
				},
				error: function(){
					alert("팝업창 조회 문제가 발생하였습니다.\n다시 시도해주시길 바랍니다.")
				}
			});
		}
		
		//선수2 팝업 클로즈
		function player2_close() {
			$("#player2Content table *").find('.selectRow').removeClass("selectRow")
			player2Modal.classList.add("hidden");
		}
		
	/** open/close 관리 END */
	
	function validation(){
		let team 		 = $("#competeTeam").val()
		let firstPlayer  = $("#firstPlayer").val()
		let secondPlayer = $("#secondPlayer").val()
		
		if(team === ''){
			alert("팀을 선택하여 주세요.");
			return false;
		}
		
		if(firstPlayer === ''){
			alert("첫 경기 선수를 선택하여 주세요.");
			return false;
		}
		
		if(secondPlayer === ''){
			alert("두번째 경기 선수를 선택하여 주세요.");
			return false;
		}
		
		return true;
	}
	
/** 커스텀 함수 관리자 END */


/** EventListener 관리자 START */
	
	$("#teamBtn").click(function(){
		team_open();
	});
	
	$("#teamContent").on("click", "table tr", function(){
		let index = $(this).index();
		
		if(index === 0){return false;}
		
		$("#teamContent table *").find('.selectRow').removeClass("selectRow")
		
		$(this).addClass("selectRow");
	});
 	
	$("#team_modal_apply_btn").click(function(){
		 $("#competeTeam").val( $("#teamContent table tr.selectRow td").eq(0).text() );
		 team_close()
	});
	
	$("#team_modal_close_btn").click(function(){
		team_close()
	});
	
	$("#player1Btn").click(function(){
		player1_open();
	})
	
	$("#player1Content").on("click", "table tr", function(){
		let index = $(this).index();
		
		if(index === 0){return false;}
		
		$("#player1Content table *").find('.selectRow').removeClass("selectRow")
		
		$(this).addClass("selectRow");
	})
	
	$("#player1_modal_apply_btn").click(function(){
		let player1 = $("#player1Content table tr.selectRow td").eq(0).text();
		let player2 = $("#secondPlayer").val();
		
		if(player1 === player2){
			alert("같은 선수가 존재합니다.\n다른 선수를 선택하여 주세요.");
		}else{
			$("#firstPlayer").val( player1 );
		 	player1_close()
		}
	});
	
	$("#player1_modal_close_btn").click(function(){
		player1_close()
	});
	
	$("#player2Btn").click(function(){
		player2_open();
	})
	
	$("#player2Content").on("click", "table tr", function(){
		let index = $(this).index();
		
		if(index === 0){return false;}
		
		$("#player2Content table *").find('.selectRow').removeClass("selectRow")
		
		$(this).addClass("selectRow");
	})
	
	$("#player2_modal_apply_btn").click(function(){
		let player1 = $("#firstPlayer").val();
		let player2 = $("#player2Content table tr.selectRow td").eq(0).text();
		
		if(player1 === player2){
			alert("같은 선수가 존재합니다.\n다른 선수를 선택하여 주세요.");
		}else{
			$("#secondPlayer").val( player2 );
			player2_close()
		}
	});
	
	$("#player2_modal_close_btn").click(function(){
		player2_close()
	});
	
	$("#entryBtn").click(function(){
		if(validation()){
			if(confirm("엔트리를 제출하시겠습니까?")){
				let team 		 = $("#competeTeam").val()
				let firstPlayer  = $("#firstPlayer").val()
				let secondPlayer = $("#secondPlayer").val()
				let homeAway     = $("#home_away option:selected").val()
				
				let data = {};
				
				if(homeAway === "home"){
					data.awayTeam = team;
					data.homeFirstPlayer = firstPlayer;
					data.homeSecondPlayer = secondPlayer;
				}else if(homeAway === "away"){
					data.homeTeam = team;
					data.awayFirstPlayer = firstPlayer;
					data.awaySecondPlayer = secondPlayer;
				}else{
					alert("데이터 전송 문제가 발생하였습니다. 새로고침 후 다시 시도해주시길 바랍니다.")
					return false;
				}
				
				$.ajax({
					url: "/entrySubmit",
					method: "POST",
					data: JSON.stringify(data),
					dataType: "json",
					contentType : 'application/json; charset=utf-8;',
					success: function(result){
						if(result.insertChk){
							alert("엔트리가 제출되었습니다.");

							let tab_title_1 = $(".tab-container__item").eq(0);
							let tab_title_2 = $(".tab-container__item").eq(1);
							
							let tab_content_1 = $("#tab1");
							let tab_content_2 = $("#tab2");
							
						  	tab_title_2.removeClass("active");
						  	tab_content_2.removeClass("target");
						  	
						  	tab_title_1.addClass("active");
						  	tab_content_1.addClass("target");
						  	
						  	//임시방편
						  	//tab_title_2.css({'display' : 'none'});
							
						}else{
							alert("엔트리 제출 과정에서 빠진 데이터가 존재합니다.\n다시 입력해주시길 바랍니다.");
							return false;
						}
						
					},
					error: function(request){
						alert(request.responseJSON.Message)
						return false;
					}
				})
			}
		}
	})

/** EventListener 관리자 END */
})






