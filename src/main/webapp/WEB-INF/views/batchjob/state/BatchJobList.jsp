<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<div id="divBoardHome">
<div>
	<table width="100%">
		<tr>
			<td align="left"><a id="btn_create"  class="btn btn-success">등록</a></td>
			<td align="right"><input class="textbox" type="text" name="search_name" id="search_name"  value="${search_name}">
								<a id="btn_search"  class="btn btn-success">검색</a></td>
		</tr>
	</table>
</div>

<div>
	<div>
		<table class="table table-bordered table-condensed muted ">
			<tr class="info">
				<td>배치명</td>
				<td>서버(IP/OS)</td>
				<td>실행위치</td>
				<td>배치상태</td>
				<td>실행상태</td>
				<td>실행 시작시간</td>
				<td>최종완료 처리시간</td>
				<td>최근종료상태</td>
				<td>실행계획</td>
				<td>조작</td> 
			</tr>
			
			<c:forEach var="item" items="${list}" varStatus="rowCount">
			<tr id="trBatchJobList_${item.seq}" class="<c:choose><c:when test="${item.lastresultyn eq 2}">error</c:when><c:otherwise>warning</c:otherwise></c:choose>">
				<td><a id="btn_seq_${item.seq}" style="cursor:pointer" value="${item.seq}" >${item.name}</a></td>
				<td>${item.serverip} / ${item.serveros} </td>
				<td>
					<c:choose>
						<c:when test="${item.execplace eq 1}">내부</c:when>
						<c:when test="${item.execplace eq 2}">외부</c:when>
					</c:choose>
				</td>
				<td>
					<c:choose>
						<c:when test="${item.useyn eq 1}">사용</c:when>
						<c:when test="${item.useyn eq 2}">미사용</c:when>
					</c:choose>
				</td>
				<td>
					<c:choose>
						<c:when test="${item.execyn eq 1}">실행중</c:when>
						<c:when test="${item.execyn eq 2}">대기중</c:when>
					</c:choose>
				</td>
				<td>${item.starttime}</td>
				<td>
					<c:choose>
						<c:when test="${item.processtime eq 0}"> -- </c:when>
						<c:when test="${item.processtime le 60}"> ${item.processtime} 초</c:when>
						<c:when test="${item.processtime le 3600}"> <fmt:formatNumber value="${item.processtime / 60}" pattern=".0"/> 분</c:when>
						<c:otherwise> <fmt:formatNumber value="${item.processtime / 60 / 60}" pattern=".0"/> 시간</c:otherwise>
					</c:choose>
				</td>
				<td>
					<c:choose>
						<c:when test="${item.lastresultyn eq 1}">성공</c:when>
						<c:when test="${item.lastresultyn eq 2}">실패</c:when>
						<c:when test="${item.lastresultyn eq 3}">실행중</c:when>
						<c:when test="${item.lastresultyn eq 4}">실행중지</c:when>
					</c:choose>
				</td>
				<td>${item.cronexpression}</td>
				<td><a id="btn_joblog_${item.seq}" style="cursor:pointer" value="${item.seq}">실행기록</a><br>
					  <a id="btn_jobstart_${item.seq}" style="cursor:pointer" value="${item.seq}">배치실행</a><br>
					  <a id="btn_jobstop_${item.seq}" style="cursor:pointer" value="${item.seq}">배치중지</a> </td>
			</tr>
			</c:forEach>
			<tr>
				<td colspan="8" style="cursor:pointer">${paging}</td>
			</tr>	
		</table>
	</div>
	
	<script type="text/javascript">
		jQuery(document).ready(function(){
		});
			//페이지 처리
			$(".btnPagebatchjob").bind("click",function(){
				A.getHtml("/state/batchjobList.b1", "tgp=divContents&np="+$(this).attr("np") + "&search_name=" + $("#search_name").val());
			});
			
			//검색액션 bind
			jQuery("#btn_search").bind("click",function(){
				A.getHtml("/state/batchjobList.b1", {"search_name" : $("#search_name").val() , "tgp" : "divContents"} );
			});
			
			//검색액션 keydown -> enter
			jQuery("#search_name").keydown(function(){
				if(event.keyCode==13){
					A.getHtml("/state/batchjobList.b1", {"search_name" : $("#search_name").val() , "tgp" : "divContents"} );					
				}
			});
			
			//검색액션 입력폼 포커스
			$('input[name="search_name"]').focus();
			
			// 신규등록
			jQuery("#btn_create").bind("click",function(){
				A.getHtml("/home/popPage.b1", {"popType" : "create" , "tgp" : "popup_layer"});	
			});
			
			// 수정
			jQuery("a[id^='btn_seq']").bind("click",function(){
				A.getHtml("/home/popPage.b1", {"popType" : "update" ,"popparam" : $(this).attr("value"), "tgp" : "popup_layer"});
			});
			
			//실행기록
			jQuery("a[id^='btn_joblog']").bind("click",function(){
			    var insertTag = $("<tr class='trbtjob"+$(this).attr("value")+"'><td colspan='9'><div id='BatchLog"+$(this).attr("value")+"'></div></td></tr>");
			    
			    if(!$(".trbtjob"+$(this).attr("value")).length > 0){
			     insertTag.insertAfter("#trBatchJobList_"+$(this).attr("value"));
			    }else{
			     $(".trbtjob"+$(this).attr("value")).remove();       
			    }
			    A.getHtml("/batchjoblog/indivLog.b1", {seq:$(this).attr("value") ,tgp:"BatchLog"+$(this).attr("value"), headerYn : "N"} );
			});
			
			//배치실행
			jQuery("a[id^='btn_jobstart']").bind("click",function(){
				A.getHtml("/state/exec.b1", {seq:$(this).attr("value")});
				setTimeout(pageReload, 2500);
			});			
			
			//배치중지
			jQuery("a[id^='btn_jobstop']").bind("click",function(){
				A.getHtml("/state/stop.b1", {seq:$(this).attr("value")});
				setTimeout(pageReload, 6000);
			});
			
			//페이지 새로고침
			function pageReload(){
				$("#divBoardHome").load("/state/batchjobList.b1");
			}
			
			//alert($("#test").val())
			//setTimeout(pageReload, 3000);
	</script>
</div>
</div>