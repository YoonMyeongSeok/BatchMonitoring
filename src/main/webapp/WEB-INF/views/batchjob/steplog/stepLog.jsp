<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div class="divBoardHome">
	<div class="table" id="frm">
<%-- 	
		<table class="table muted" id="header" style="<c:if test="${headerYn eq 'N'}">display:none;</c:if>">
			<tr class="info" style="color: black;">
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
				<td><c:choose>
						<c:when test="${item.useyn eq 1}">사용</c:when>
						<c:when test="${item.useyn eq 2}">미사용</c:when>
					</c:choose>
				</td>
				<td><c:choose>
						<c:when test="${item.execyn eq 1}">실행중</c:when>
						<c:when test="${item.execyn eq 2}">대기중</c:when>
					</c:choose>
				</td>
				<td>${item.starttime}</td>
				<td><c:choose>
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
				<td>단계번호</td>
				<td>시작시간</td>
				<td>종료시간</td>
				<td>처리시간</td>
				<td>결과구분</td>
				<td>결과메세지</td>
				<td>로그파일링크</td>
			</tr>
			<c:forEach var="item" items="${list}" varStatus="rowCount">
				<tr class="<c:choose><c:when test="${item.resultyn eq 2}">error</c:when><c:otherwise>warning</c:otherwise></c:choose>">
					<td>${item.batchjoblog.seq}</td>
					<td>
						<c:choose>
							<c:when test="${headerYn eq 'N'}">${rowCount.count}</c:when>
							<c:when test="${headerYn ne 'N'}">${item.seq}</c:when>
						</c:choose>			
					</td>					
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
				</tr>
			</c:forEach>
			<tr>
				<td colspan="8" style="cursor:pointer; <c:if test="${headerYn eq 'N'}">display:none;</c:if>">${paging}</td>
			</tr>
		</table>
	</div>

	<script type="text/javascript">
		jQuery(document).ready(function() {
			$(".btnPageStepLog").bind("click",function() {
				A.getHtml("/batchsteplog/board.b1",	"tgp=divContents&np="+ $(this).attr("np"));
			});
			
			$("a[id^='fileDown']").bind("click",function() {
				A.getHtml("/home/popPage.b1", {popType:"log" , popparam:$(this).attr("value"), tgp:"popup_layer"});
			});
		});
	</script>
</div>