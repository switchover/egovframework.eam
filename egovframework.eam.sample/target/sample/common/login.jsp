<%@ page contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="org.springframework.security.ui.AbstractProcessingFilter" %>
<%@ page import="org.springframework.security.AuthenticationException" %>
<html>
<head>
<title>Login</title>
<link href="<c:url value='/css/egovframework/sample.css' />" rel="stylesheet" type="text/css">
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<script language="JavaScript">
function init() {
	document.loginForm.j_username.focus();
}

function fncLogin() {
	    document.loginForm.action="<c:url value='/j_spring_security_check'/>";
	    document.loginForm.submit();
}
</script>

</head>

<body bgcolor="#ffffff" text="#000000" onload="init();">

<br>
<br>

<form name="loginForm" action="<c:url value='/j_spring_security_check'/>" method="POST">

<div align="center">
<table width="300" border="0" cellpadding="0" cellspacing="0" align="center">
	<tr>
		<td>
			<table border="0" width="100%" align="center">
				<tr>
					<td colspan="2">
						<c:if test="${not empty param.fail}">
							<font color="red">
							Your login attempt was not successful, try again.<BR><BR>
							Reason: <%= ((AuthenticationException) session.getAttribute(AbstractProcessingFilter.SPRING_SECURITY_LAST_EXCEPTION_KEY)).getMessage() %>
							</font>
						</c:if>	  
					</td>
				</tr>
				<tr>
					<td height="30" colspan="2">&nbsp;</td>
				</tr>
				<tr>
					<td align="right" height="25">
						아이디 : 
					</td>
					<td align="left">
						<input type='text' name='j_username' <c:if test="${not empty param.fail}">value='<c:out value="${SPRING_SECURITY_LAST_USERNAME}"/>'</c:if>
			                                       								style='width:180px; height:19px' required="required"  maxLength='50'>          
					</td>
				</tr>
				<tr>
					<td align="right" height="25">
						비밀번호 : 
					</td>
					<td align="left">
						<input type="password" name="j_password" style="width:180px; height:19px" required="required" maxLength="50" onKeyPress="if(event.keyCode==13) fncLogin();">   
					</td>
				</tr>
				<tr>
					<td colspan="2" align="center" height="25">
						<a href="javascript:fncLogin();">로그인</a> 
					</td>
				</tr>
			</table>
		</td>
	</tr>
</table>
</div>

</form>
</body>
</html>
