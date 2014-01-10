<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">

<head>

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />

<title>Welcome eGov Access Management System</title>

<link href="<c:url value='/common/css/login.css' />" rel="stylesheet" type="text/css" />

<script type="text/javascript">
<!--
<c:if test="${param.fail == 'true'}">
    alert("Your login attempt was not successful, try again!");
</c:if>

function init_imageload() {
    var userId = document.getElementById('j_username');
    var password  = document.getElementById('j_password');
    
    userId.className = 'bg_id';
    password.className = 'bg_pw';
    
    userId.focus();
};

// 포커스시에 배경이미지 삭제
function userIdFocus() {
    var userId = document.getElementById('j_username');
    var password = document.getElementById('j_password');
	
    userId.className == '';	
}

function passwordFocus() {
    var userId = document.getElementById('j_username');
    var password = document.getElementById('j_password');
	
    userId.className == '';	
}

// input의 id값에 따른 배경 추가,삭제:input필드간의 이동(value=null)
function userIdBlur() {
    var userId = document.getElementById('j_username');
    var password = document.getElementById('j_password');
    
    if (userId.value == '') {
    	 userId.className == 'bg_id';	
    }
}

function passwordBlur() {
    var userId = document.getElementById('j_username');
    var password  = document.getElementById('j_password');
	
    if (password.value == '') {
    	password.className == 'bg_pw';	
   }
}

function frmSubmin() {
    var userId = document.getElementById('j_username');
    var password  = document.getElementById('j_password');

    if( userId.value == "" ) {
        alert("아이디를 입력해 주세요.");
        userId.focus();
        return false;
    }

    if( password.value == "" ){
        alert("암호를 입력해 주세요.");
        password.focus();
        return false;
    }

    return true;	
}
//-->
</script>

</head>

<body onload="init_imageload();">

<form name="loginForm" id="loginForm" method="post" onsubmit="return frmSubmin();" action="<c:url value='/j_spring_security_check'/>" >
<!-- 전체 DIV시작 -->
<div id="wrap">
    <!-- 전체 로그인 배경 시작 -->
    <div id="index_bg">
        <div id="login_bg">
            <div id="login_f_area">
                <ul>
                    <li><label for="j_username">user id</label><input type="text" name="j_username" id="j_username" size="15" maxlength="30" title="ID" onfocus='userIdFocus()' onblur='usrIdBlur()' /></li>
                    <li><label for="j_password">password</label><input type="password" name="j_password" id="j_password" size="15" maxlength="30"  title="Password" onfocus='passwordFocus()' onblur='passwordBlur()' /></li>
                </ul>               
            </div>
            <div id="login_btn_area" >
                <input type="image" id="btnLogin" src="<c:url value='/common/images/btn_login.jpg'/>" style="cursor:pointer;" alt="Login" />
            </div>
        </div>
    </div>
    <!-- //전체 로그인 배경 끝 -->
    <!-- 카피라이트 시작 -->
    <div id="index_footer">
        <div id="logo_div"><img src="<c:url value='/common/images/img_logo_egovframe.jpg'/>" alt="logo" /></div>
    </div>
    <!-- //카피라이트 끝 -->  
</div>
<!-- //전체 DIV끝 -->
</form>

</body>

</html>