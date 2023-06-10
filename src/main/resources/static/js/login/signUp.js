$(function(){
	const idCheckBtn = document.getElementById("idCheckBtn");
	const signUpBtn  = document.getElementById("signUpBtn");
	
	idCheckBtn.addEventListener("click", function(){
		let data = {
						userId : document.getElementById("userId").value
				   }
		$.ajax({
			url : '/idCheck',
			method : 'post',
			data : JSON.stringify(data),
			dataType : 'json',
			contentType : 'application/json; charset=utf-8;',
			success : function( result ){
				if(result.useCheck){
					alert("해당 ID를 사용하실 수 있습니다.")
					document.getElementById("userId").setAttribute("disable", "disable");
					document.getElementById("password").focus();
				}else{
					alert("중복되는 ID가 존재합니다.");
					document.getElementById("userId").focus();
				}
			},
			error : function(){
				alert("중복체크 오류 발생");
				return false;
			}
		});
	})
	
	signUpBtn.addEventListener("click", function(){
		let data = {
			userId : document.getElementById("userId").value,
			password : document.getElementById("password").value		
		};
		
		$.ajax({
			url : '/insertUserInfo',
			method : 'post',
			data : JSON.stringify(data),
			dataType : 'json',
			contentType : 'application/json; charset=utf-8;',
			success : function(result){
				console.log(result)
				if(result.insertChk){
					alert("등록 신청이 되었습니다.");
					let form = document.signUpForm;
					    form.method = "post";
					    form.action = "/redirectLogin"
					    form.submit();
					    form.submit();
				}
			}
		})
	})
	
	document.getElementById("password").addEventListener("keyup", function(){
		
	})
	
})