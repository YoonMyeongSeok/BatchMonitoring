<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div class="divBoardHome">

	<div class="table" id="frm">
	
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
			
			<!--  
				list : BatchStepLogController 에서 넘어온 BatchStepLog.getList()
				seq : 실행번호
				headerYn : 헤더사용 유무 - 현재 사용하지 않음
				resultyn : 실행결과
				starttime : 배치 시작 시간
				endtime : 배치 종료 시간
				processtiem : 배치 실행 소요 시간
				resultmsg : 결과메세지				
				logpath : 로그파일 경로
				paging : BatchJobLogController 에서 넘어온 BatchJob의 페이징 처리
			-->
			<!-- 리스트 부분 -->
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
				<td><a id="logPop${item.logpath}" style="cursor:pointer" value="${item.logpath}">${item.logpath}</a></td>
			</tr>
			</c:forEach>
			
			<!-- 페이징 처리 부분 -->
			<tr>
				<td colspan="8" style="cursor:pointer; <c:if test="${headerYn eq 'N'}">display:none;</c:if>">${paging}</td>
			</tr>
			
		</table><!-- list -->
		
	</div><!-- frm --> 


	<script type="text/javascript">
	
		jQuery(document).ready(function() {
			
			//페이지 번호 클릭 시
			$(".btnPageStepLog").bind("click",function() {
				A.getHtml("/batchsteplog/board.b1",	"tgp=divContents&np="+ $(this).attr("np"));
			});
			
			//로그파일 경로 클릭 시
			$("a[id^='logPop']").bind("click",function() {
				var logPath = $(this).attr("value");
				window.open("/batchjoblog/viewLogFile.b1?logPath="+logPath, "log", "width=700px,height=455px,top=100px,left=300px");
			});
			
		});
		
	</script>
	
</div><!-- divBoardHome -->