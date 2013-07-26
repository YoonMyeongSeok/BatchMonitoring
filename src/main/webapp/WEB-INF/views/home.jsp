<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page session="false" %>

<html>
<head>
	<title>Home</title>
	<link rel="stylesheet" type="text/css" href="/resources/css/global.css" />
	<link rel="stylesheet" type="text/css" href="/resources/css/jquery-ui-1.8.18.custom.css" />
	
</head>
<body>

<div id="divHeader">
	<span id="divTitle"><h1><a href="/">배치모니터링</a></h1></span>
	<div id="divInfoLogin"></div>
</div>

<div id="divContents">
</div>

<div id="popup_layer"
	style="position:absolute; border:double; top:90px; left:180px; width:500px; height:475px; 
	z-index:1; visibility:hidden; background-color:white; border-radius:15px 15px 15px 15px;"
	onmouseover="style.cursor='move'" onmousedown="start_drag();"
>
</div>

	<script type="text/javascript" src="/resources/js/jquery/jquery-1.7.1.js"></script>
	<script type="text/javascript" src="/resources/js/jquery/jquery-ui-1.8.18.custom.js"></script>
	<script type="text/javascript" src="/resources/js/boneis/boneis.core.js"></script>
	<script type="text/javascript">
	jQuery(document).ready(function() {
		A.getHtml("/home/board.b1", "tgp=divContents");
	});
	function popOpen(width, height){
		$("#popup_layer").css('width', width);
		$("#popup_layer").css('height', height);
		$("#popup_layer").css('visibility', 'visible');
	}
	function popClose(){
		$("#popup_layer").html("");
		$("#popup_layer").css('visibility', 'hidden');
	}
	
	/* ------------ DIV객체 이동관련 함수 ------------ */
	//POPUP_LAYER 이동 함수
	var mouseDown;
	var startDrag= false;
	
	function move(){
		if(startDrag){
	  		mouseDown.style.left = x + event.clientX - pre_x;
	  		mouseDown.style.top  = y + event.clientY - pre_y;
	  		return false;
	 	}
	}
	
	function start_drag(){
		mouseDown = $("#popup_layer")[0];
	 	x = parseInt(mouseDown.style.left);
		y = parseInt(mouseDown.style.top);
		pre_x = event.clientX;
		pre_y = event.clientY;
		 
		startDrag = true;
		mouseDown.onmousemove = move;
	 	mouseDown.onmouseup = stop;
	}
	
	function stop(){
	 	startDrag=false;
	}
	/* ------------ DIV객체 이동관련 함수 ------------ */
	</script>
</body>	
</html>

