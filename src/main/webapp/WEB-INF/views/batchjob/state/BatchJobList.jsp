<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<jsp:include page="/WEB-INF/views/header.jsp" flush="fasle" />

<div id="divBoardHome">

	<div>
		<table width="100%">
			<tr>
				<td align="left"><a id="btn_create" class="btn btn-success">등록</a></td>
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
					<td>배치상태</td>
					<td>실행상태</td>
					<td>실행 시작시간</td>
					<td>최종완료 처리시간</td>
					<td>최근종료상태</td>
					<td>실행계획</td>
					<td>조작</td> 
				</tr>
				
				<!--  
					list : BatchJobController 에서 넘어온 BatchJob.getList()
					seq : 배치실행번호
					name : 배치명
					serverip : 배치가 돌아갈 서버의 IP
					serveros : 배치가 돌아갈 서버의 OS
					useyn : 배치 사용여부
					execyn : 배치 실행 상태
					processtime : 배치 총 실행 시간
					lastresultyn : 배치 최근 결과 이력
					paging : BatchJobController 에서 넘어온 BatchJob의 페이징 처리
				-->
				<c:forEach var="item" items="${list}" varStatus="rowCount">
				<tr id="trBatchJobList_${item.seq}" class="<c:choose><c:when test="${item.lastresultyn eq 2}">error</c:when><c:otherwise>warning</c:otherwise></c:choose>">
					<td><a id="btn_seq_${item.seq}" style="cursor:pointer" value="${item.seq}" >${item.name}</a></td>
					<td><a id="btn_serverInfo_${item.seq}" style="cursor:pointer" value="${item.seq}"> ${item.serverip} </a> / ${item.serveros} </td>
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
							<c:when test="${item.lastresultyn eq 4}">중지</c:when>
							<c:when test="${item.lastresultyn eq 5}">연결실패</c:when>
						</c:choose>
					</td>
					<td>${item.cronexpression}</td>
					<td><a id="btn_joblog_${item.seq}" style="cursor:pointer" value="${item.seq}">실행기록</a><br>
						  <a id="btn_jobstart_${item.seq}" style="cursor:pointer" value="${item.seq}">배치실행</a><br>
						  <a id="btn_jobstop_${item.seq}" style="cursor:pointer" value="${item.seq}">배치중지</a> </td>
				</tr>
				</c:forEach>
				
				<!-- 페이징 처리 -->
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
			
			//검색액션 keydown -> Enter키 클릭 시
			jQuery("#search_name").keydown(function(){
				if(event.keyCode==13){
					A.getHtml("/state/batchjobList.b1", {"search_name" : $("#search_name").val() , "tgp" : "divContents"} );					
				}
			});
			
			//검색액션 입력폼 포커스 -> 엔터키 액션 후
			$('input[name="search_name"]').focus();
			
			//배치신규등록
			jQuery("#btn_create").bind("click",function(){
				window.open("/state/getBatchJobInfo.b1", "create", "width=470px,height=424px,top=100px,left=300px");
			});
			
			//배치명클릭시 - 배치정보얻기
			jQuery("a[id^='btn_seq']").bind("click",function(){
				var seq = $(this).attr("value")
				window.open("/state/getBatchJobInfo.b1?seq="+seq, "update_"+seq, "width=470px,height=424px,top=100px,left=300px");
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
				//A.getHtml("/state/exec.b1", {seq:$(this).attr("value")});
				//setTimeout("pageReload();", 1000);
				var exec = confirm("실행하시겠습니까?");
				if(exec){
					setTimeout("pageReload();", 1000);
					$.ajax({
						type:"post",
						url:"/state/exec.b1",
						data:{seq:$(this).attr("value")},
						error:function(){
							//error:404NotFound에러지만 원인을 모르겠음.
							setTimeout("pageReload();", 1000);
						}								
					});
					
				}				
			});			
			
			//배치중지
			jQuery("a[id^='btn_jobstop']").bind("click",function(){
				A.getHtml("/state/stop.b1", {seq:$(this).attr("value")});
			});

			//페이지 새로고침
			function pageReload(){
				$("#divBoardHome").load("/state/batchjobList.b1");
			}
			
			//서버 및 배치의 CPU 및 Memory 정보 초기화 및 생성
			jQuery("a[id^='btn_serverInfo']").bind("click",function(){
				var batchSeq = $(this).attr("value");
				window.open("/state/monitoringPop.b1?seq="+batchSeq, "Server"+batchSeq, "width=810px,height=330px,top=100px,left=300px");
			});
				
		</script>
		
	</div>
</div><!-- divBoardHome -->