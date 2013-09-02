<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page session="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<jsp:include page="/WEB-INF/views/header.jsp" flush="fasle" />

<div id ="logLayer" border=1 style="overflow:scroll; width:670px; height:400px; padding:10px;">
	<br><br>
	<font color="black">${log}</font>
</div>