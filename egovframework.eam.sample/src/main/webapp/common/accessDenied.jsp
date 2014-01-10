<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ page isErrorPage="true"%>
<%@ page import="org.springframework.security.context.SecurityContextHolder" %>
<%@ page import="org.springframework.security.Authentication" %>
<%@ page import="org.springframework.security.ui.AccessDeniedHandlerImpl" %> 
<%@ page import="org.springframework.security.userdetails.UserDetails" %>
<%@ page import="org.springframework.security.userdetails.UserDetailsService" %>
<%@ taglib prefix='c' uri='http://java.sun.com/jsp/jstl/core' %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<link href="<c:url value='/css/egovframework/sample.css' />" rel="stylesheet" type="text/css">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
</head>

<body bgcolor="#ffffff" text="#000000">

<br>
<br>

<div align="center">
<table width="500" border="0" cellpadding="0" cellspacing="0" align="center">
	<tr>
		<td align="center">
			<table border="0" width="100%" align="center">
				<tr>
					<td colspan="2" align="center">
						접근권한이 없습니다.<br> 담당자에게 문의하여 주시기 바랍니다. <br>							
						<%= request.getAttribute(AccessDeniedHandlerImpl.SPRING_SECURITY_ACCESS_DENIED_EXCEPTION_KEY)%><br>
						<%
						Authentication auth = SecurityContextHolder.getContext().getAuthentication();
						Object principal = auth.getPrincipal();
						if (principal instanceof UserDetails) {
							String username = ((UserDetails) principal).getUsername();
							String password = ((UserDetails) principal).getPassword();
							out.println("Account : " + username.toString() + " / " + password.toString() + "<br>");
						}
						%>	
					</td>
				</tr>
				<tr>
					<td height="30" colspan="2">&nbsp;</td>
				</tr>
				<tr>
					<td colspan="2" align="center" height="25">
						<a href="<c:url value='/'/>">확인</a>
					</td>
				</tr>
			</table>
		</td>
	</tr>
</table>
</div>

</body>
</html>