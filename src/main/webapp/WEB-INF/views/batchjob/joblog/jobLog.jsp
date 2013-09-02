<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div class="divBoardHome">

	<div class="table" id="frm">
	
		<!-- tgp : TargetPoint, 해당 html내용이 뿌려질 div id -->
		<!-- seq : 각 배치의 Sequence 번호 -->
		<input type="hidden" id="tgp" value="${tgp}" />
		<input type="hidden" id="seq" value="${seq}" />
	
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
			
			<!--  
				list : BatchJobLogController 에서 넘어온 BatchJobLog.getList()
				seq : 실행번호
				resultyn : 실행결과
				starttime : 배치 시작 시간
				endtime : 배치 종료 시간
				processtiem : 배치 실행 소요 시간
				resultmsg : 결과메세지
				logpath : 로그파일 경로
				smslist : SMS 목록
				emaillist : E-Mail 목록
				paging : BatchJobLogController 에서 넘어온 BatchJob의 페이징 처리
			-->
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
				<td><a id="logPop${item.logpath}" style="cursor:pointer" value="${item.logpath}">${item.logpath}</a></td>
				<td>${item.batchjob.smslist}/${item.batchjob.emaillist}</td>
			</tr>
			</c:forEach>
			
			<!-- 페이징 처리 -->
			<tr>
				<td colspan="8" style="cursor:pointer">${paging}</td>
			</tr>
			
		</table>
		
	</div><!-- frm -->
	
	<script type="text/javascript">
		jQuery(document).ready(function() {	
						
			//특정 배치의 실행번호를 클릭 시 해당 잡로그의 단계별 로그를 보여준다
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
			
			//특정 배치의 로그보기 버튼 클릭 시
			$("a[id^='logPop']").bind("click",function() {
				var logPath = $(this).attr("value");
				window.open("/batchjoblog/viewLogFile.b1?logPath="+logPath, "log", "width=700px,height=455px,top=100px,left=300px");
			});
			
		});
	</script>
	
</div><!-- divBoardHome -->