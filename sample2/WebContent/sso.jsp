<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ page import="java.util.*" %>
<%@ page import="egovframework.eam.api.sso.client.SSOClient" %>
<%
	String systemId = request.getParameter("systemId");
	String ticket = request.getParameter("ticket");

	SSOClient sso = new SSOClient("127.0.0.1", "1099");
	
	Map<String, String> user = sso.getSSOLoginedUser(systemId, ticket);
	
	String userId = user.get(SSOClient.USER_ID);
	String userName = user.get(SSOClient.USER_NAME);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Single Sign-On</title>
<script type="text/javaScript" language="javascript" defer="defer">
<!--
<%
if (userId == null) {
%>
	alert('SSO Error.. Contact System Administractor...');
<%
} else {
	// Login 처리...
	System.out.println("userId = " + userId);
	
	session.setAttribute("userId", userId);
	session.setAttribute("userName", userName);
	// 기타 필요한 정보 추가...
%>
	// 로그인 처리 후.. 원하는 페이지로 이동...
	location.href = "welcome.jsp";
<%
}
%>
-->
</script>
</head>
<body style="text-align:center; margin:0 auto; display:inline; padding-top:100px;">
</body>
</html>
