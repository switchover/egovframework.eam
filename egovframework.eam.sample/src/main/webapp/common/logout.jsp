<%@ page contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.lang.String" %>
<%@ page import="org.springframework.security.ui.AbstractProcessingFilter" %>
<%@ page import="org.springframework.security.AuthenticationException" %>
<%@ page import="egovframework.rte.fdl.security.userdetails.util.EgovUserDetailsHelper" %>
<html>
<head>
<title>Logout</title>
<link href="<c:url value='/css/egovframework/sample.css' />" rel="stylesheet" type="text/css">
<meta http-equiv="content-type" content="text/html; charset=utf-8">
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
						로그아웃되었습니다.
					</td>
				</tr>
				<tr>
					<td height="30" colspan="2">&nbsp;</td>
				</tr>
				<tr>
					<td colspan="2" align="center" height="25">
						<a href="<c:url value='/'/>">재접속</a>
					</td>
				</tr>
			</table>
		</td>
	</tr>
</table>
</div>

</body>
</html>