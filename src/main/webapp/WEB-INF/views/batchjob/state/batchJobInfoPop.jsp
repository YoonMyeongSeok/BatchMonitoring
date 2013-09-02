<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<jsp:include page="/WEB-INF/views/header.jsp" flush="fasle" />

<div id="divBoardHome">

	<form id="from_savejob" name="from_savejob">
		<input type="hidden" name="setseq" id="setseq" value="${list.seq}">

		<!--  
			name : 배치명
			clazz : 배치 클래스명
			serverip : 배치가 실행될 서버의 IP
			serveros : 배치가 실행될 서버의 OS명
			useyn : 배치 사용유무
			cronexpression : 배치 실행 스케쥴 시간
			smslist : SMS 목록
			emaillist : E-Mail 목록
		-->

		<table class="table-condensed muted ">
			<tr>
				<td bgcolor="#d9edf7">배치명</td>
				<td><input type="text" name="name" id="name"
					style="width: 300px" maxlength="40" value="${list.name}"></td>
			</tr>
			<tr>
				<td bgcolor="#d9edf7">배치클래스명</td>
				<td><input type="text" name="clazz" id="clazz"
					style="width: 300px" maxlength="160" value="${list.clazz}"></td>
			</tr>
			<tr>
				<td bgcolor="#d9edf7">서버IP</td>
				<td><input type="text" name="serverip" id="serverip"
					style="width: 300px" maxlength="40" value="${list.serverip}"></td>
			</tr>
			<tr>
				<td bgcolor="#d9edf7">서버OS</td>
				<td><select name="serveros" style="width: 110px">
						<option value="Linux"
							<c:if test="${list.serveros == 'Linux'}">selected="selected"</c:if>>Linux</option>
						<option value="Windows"
							<c:if test="${list.serveros =='Windows'}">selected="selected"</c:if>>Windows</option>
				</select>
			</tr>
			<tr>
				<td bgcolor="#d9edf7">사용유무</td>
				<td><select name="useyn" style="width: 110px">
						<option value="1"
							<c:if test="${list.useyn eq 1}">selected="selected"</c:if>>사용</option>
						<option value="2"
							<c:if test="${list.useyn eq 2}">selected="selected"</c:if>>미사용</option>
				</select></td>
			</tr>
			<tr>
				<td bgcolor="#d9edf7">실행계획</td>
				<td><input type="text" name="cronexpression"
					id="cronexpression" style="width: 200px" maxlength="40"
					value="${list.cronexpression}"></td>
			</tr>
			<tr>
				<td bgcolor="#d9edf7">SMS 발송리스트</td>
				<td><textarea name="smslist" id="smslist" cols="28" rows="2"
						style="resize: none;">${list.smslist}</textarea></td>
			</tr>
			<tr>
				<td bgcolor="#d9edf7">E-MAIL 발송리스트</td>
				<td><textarea name="emaillist" id="emaillist" cols="28"
						rows="2" style="resize: none;">${list.emaillist}</textarea></td>
			</tr>
			<tr>
				<td colspan="2">
					<p align="right">
						<c:if test="${list.seq ne null}">
							<a id="btn_delete" class="btn btn-danger">삭제하기</a>
							<a id="btn_save" class="btn btn-info">수정하기</a>
						</c:if>
						<c:if test="${list.seq eq null}">
							<a id="btn_save" class="btn btn-info">신규등록</a>
						</c:if>
					<p>
				</td>
			</tr>
		</table>

	</form>
	<!-- from_savejob -->

	<script type="text/javascript">
		jQuery(document).ready(function() {
		});
		
		//저장하기 버튼
		jQuery("#btn_save").bind("click", function() {

			var paramList = $("#from_savejob").serialize();
			var save = confirm("저장하시겠습니까?");
			if (save) {
				if ($("#name").val().length == 0) {
					alert("배치명을 입력해주세요.");
					return;
				}
				if ($("#clazz").val().length == 0) {
					alert("배치 클랙스명을 입력해주세요.");
					return;
				}
				if ($("#serverip").val().length == 0) {
					alert("IP를 입력해주세요.");
					return;
				}
				if ($("#cronexpression").val().length == 0) {
					alert("실행계획을 입력해주세요.");
					return;
				}

				$.ajax({
					type : "post",
					url : "/state/updateBatchJob.b1",
					data : paramList,
					error : function() {
						alert("저장 완료");
						window.close();
						opener.pageReload();
					}
				});
			}
		});

		//삭제하기 버튼
		jQuery("#btn_delete").bind("click", function() {

			var paramList = $("#from_savejob").serialize();
			var remove = confirm("삭제하시겠습니까?");
			if (remove) {

				$.ajax({
					type : "post",
					url : "/state/deleteBatchJob.b1",
					data : paramList,
					error : function() {
						alert("삭제 완료");
						window.close();
						opener.pageReload();
					}
				});
			}
		});
	</script>

</div>
<!-- divBoardHome -->