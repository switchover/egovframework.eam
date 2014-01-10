<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<c:choose>
    <c:when test="${param.development == 'true'}">
    	<c:redirect url="/Main.html?gwt.codesvr=127.0.0.1:9997"/>
    </c:when>
    <c:otherwise>
    	<c:redirect url="/Main.html"/>
    </c:otherwise>
</c:choose>