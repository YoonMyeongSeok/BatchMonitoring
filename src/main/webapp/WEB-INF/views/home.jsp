<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page session="false" %>
<jsp:include page="/WEB-INF/views/header.jsp" flush="fasle" />
<html>
	
	<head>
		<title>Batch Monitoring</title>
	</head>
	
	<body>
		<div id="divHeader">
			<span id="divTitle"><h1><a href="/">배치모니터링</a></h1></span>
			<div id="divInfoLogin"></div>
		</div><!-- divHeader -->
		
		<div id="divContents">
			<!-- 각 페이지의 html이 들어 갈 곳 -->
		</div>

		<script type="text/javascript">
			jQuery(document).ready(function() {
				A.getHtml("/home/board.b1", "tgp=divContents");
			});
		</script>
	</body>	
</html>