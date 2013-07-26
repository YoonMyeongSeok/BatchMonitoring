<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div class="divBoardHome">
	<div class="table" id="frm">
		<input type="hidden" id="tgp" value="${tgp}" />
		<input type="hidden" id="seq" value="${seq}" />
<%-- 	
		<table class="table table-condensed muted" id="header" style="<c:if test="${headerYn eq 'N'}">display:none;</c:if>">
			<tr class="info">
				<td>배치명</td>
				<td>서버(IP/OS)</td>
				<td>배치상태</td>
				<td>실행상태</td>
				<td>시작시간</td>
				<td>최근종료상태</td>
				<td>실행계획</td>
			</tr>
			<c:forEach var="item" items="${jobInfo}" varStatus="rowCount">
			<tr class="<c:choose><c:when test="${item.lastresultyn eq 2}">error</c:when><c:otherwise>warning</c:otherwise></c:choose>">
				<td>${item.name}</td>
				<td>${item.serverip}/${item.serveros}</td>
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
						<c:when test="${item.lastresultyn eq 1}">성공</c:when>
						<c:when test="${item.lastresultyn eq 2}">실패</c:when>
						<c:when test="${item.lastresultyn eq 3}">처리중</c:when>
					</c:choose>
				</td>
				<td>${item.cronexpression}</td>
			</tr>
			</c:forEach>
		</table>
 --%>		
		<table class="table table-bordered table-condensed muted" id="list">
			<tr class="info">
				<td>실행번호</td>
				<td>시작시간</td>
				<td>종료시간</td>
				<td>처리시간</td>
				<td>결과구분</td>
				<td>결과메세지</td>
				<td>로그파일링크</td>
				<td>SMS, E-MAIL 송신구분</td>
			</tr>
			<c:forEach var="item" items="${list}" varStatus="rowCount">
				<tr id="trBatchJobLog${item.seq}" class="<c:choose><c:when test="${item.resultyn eq 2}">error</c:when><c:otherwise>warning</c:otherwise></c:choose>">
					<td><a id="btnBatchStepLog_${item.seq}"class="btn btn-inverse" value="${item.seq}">${item.seq}</a></td>
					<td>${item.starttime}</td>
					<td>${item.endtime}</td>
					<td>${item.processtime}<c:if test="${item.processtime gt 0}">초</c:if></td>
					<td>
						<c:choose>
							<c:when test="${item.resultyn eq 1}">성공</c:when>
							<c:when test="${item.resultyn eq 2}">실패</c:when>
						</c:choose>
					</td>
					<td>${item.resultmsg}</td>
					<td><a id="fileDown${item.logpath}" style="cursor:pointer" value="${item.logpath}">${item.logpath}</a></td>
					<td>${item.batchjob.smslist}/${item.batchjob.emaillist}</td>
				</tr>
			</c:forEach>
			<tr>
				<td colspan="8" style="cursor:pointer">${paging}</td>
			</tr>
		</table>
	</div>
	<script type="text/javascript">
		jQuery(document).ready(function() {	
			$("a[id^='fileDown']").bind("click",function() {
				A.getHtml("/home/popPage.b1", {popType:"log" , popparam:$(this).attr("value"), tgp:"popup_layer"});
			});
			
			$("a[id^='btnBatchStepLog']").bind("click",function() {
				var insertTag = $("<tr id='tr"+ $(this).attr("value")+ "'><td colspan='8'><div id='BatchStepLog"+ $(this).attr("value")+ "'></div></td></tr>");
				if (!$("#tr"+ $(this).attr("value")).length > 0) {
					insertTag.insertAfter("#trBatchJobLog"+ $(this).attr("value"));
				} else {
					$("#tr"+$(this).attr("value")).remove();
				}
				A.getHtml("/batchsteplog/indivLog.b1",{headerYn:"N", seq:$(this).attr("value"), tgp:"BatchStepLog"+ $(this).attr("value")});
			});
			
			//전체 배치 로그 페이지번호 클릭 시
			$(".btnPageJobLog").bind("click",function() {
				A.getHtml("/batchjoblog/board.b1","tgp=divContents&np="+ $(this).attr("np"));
			});
			
			//특정 배치 로그 페이지번호 클릭 시 
			$(".btnPageJobLog"+$("#seq").val()).bind("click",function() {
				A.getHtml("/batchjoblog/indivLog.b1", {np:$(this).attr("np"), seq:$("#seq").val() ,tgp:$("#tgp").val(), headerYn:"N"} );
			});
		});
	</script>
</div>