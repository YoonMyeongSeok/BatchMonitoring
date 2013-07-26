<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="popup">    
	 <input type="hidden"  name="poptype"  id="poptype"  value="${poptype}">
	 <input type="hidden"  name="popparam"  id="popparam"  value="${popparam}">
	<div>
	<table width="100%" >
		<tr bgcolor=#EAEAEA>
			<td width="20%" align="right"></td>
			<td class="text-info" width="60%" align="center"><h4><div id="poptitle">&nbsp;</div></h4></td>
			<td width="20%" align="right"><a id="btn_close" class="btn btn-danger">닫기</a></td>
		</tr>
	</table>
	</div>
	
	<div id="popup_page" border=1>
	
	</div>
	
	<script type="text/javascript">
	var popType = $("#poptype").val();
	jQuery(document).ready(function() {

		switch($("#poptype").val()){
			//신규 배치 등록
			case "create":
				$("#poptitle").html("신규 배치 등록");
				A.getHtml("/state/saveBatchJob.b1", "tgp=popup_page");
				
				popOpen(500, 585);
				break;
			//배치 수정
			case "update":
				$("#poptitle").html("배치 정보 수정");
				A.getHtml("/state/saveBatchJob.b1", {"seq" : $("#popparam").val(), "tgp" : "popup_page"});
				
				popOpen(500, 585);
				break;
			//로그 출력
			case "log":
				$("#poptitle").html("로그 출력");
				A.getHtml("/batchjoblog/fileDown.b1", {"logPath":$("#popparam").val(), "tgp":"popup_page"});
				
				popOpen(700, 455); 
				break;			
			default:
				popClose();
		} 
		
		//팝업 닫기 버튼
		jQuery("#btn_close").bind("click",function(){
			popClose();
			if(popType!="log")	A.getHtml("/state/batchjobList.b1", "tgp=divContents");
		});
	});
	</script>
</div>