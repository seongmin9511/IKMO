$(function(){
	const loginBtn  = document.getElementById("loginBtn");
	const signUpBtn  = document.getElementById("signUpBtn");
	
	loginBtn.addEventListener("click", function(){
		let data = {
						userId : document.getElementById("userId").value,
						password : document.getElementById("password").value
					}
		loginCheck(data);
	});
	
	signUpBtn.addEventListener("click", function(){
		let form = document.loginForm;
			form.method = "post"
			form.action = "/signUp.do";
			form.submit();
	});
	
	function loginCheck(data){
		$.ajax({
			url:"/loginCheck",
			data: JSON.stringify(data),
			method:"POST",
			dataType: "json",
			contentType: "application/json; charset=utf-8;",
			success: function(result){
				if(Object.entries(result).length === 0){
					alert("아이디가 존재하지 않습니다.\n회원가입 하신 후 다시 시도해주세요.");
					return false;
				}
				if(result.loginCheck === true){
					let form = document.loginForm;
						form.method = "POST"
						form.action = "/main.do"
						form.submit();
				}else{
					alert("로그인 정보가 일치하지 않습니다.\n다시 입력해주세요.");
					return false;
				}
			},
			error: function(){
				alert("실패");
			}
		});
	}
});

