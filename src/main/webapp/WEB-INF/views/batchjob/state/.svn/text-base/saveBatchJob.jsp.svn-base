<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div id="divBoardHome">		
	<form id="from_savejob" name="from_savejob">
		<input type="hidden" name="setseq" id="setseq" value="${list.seq}">
		<table class="table table-condensed muted " >
			<tr>
				<td bgcolor="#d9edf7">배치명</td>
				<td ><input type="text" name="name" id="name" style="width:300px" maxlength="40" value="${list.name}"></td>
			</tr>
			<tr>
				<td bgcolor="#d9edf7">배치클래스명</td>
				<td ><input type="text" name="clazz" id="clazz" style="width:300px" maxlength="160" value="${list.clazz}"></td>
			</tr>
			<tr>
				<td bgcolor="#d9edf7">IP</td>
				<td ><input type="text" name="serverip" id="serverip" style="width:300px" maxlength="40" value="${list.serverip}"></td>
			</tr>
			<tr>
				<td bgcolor="#d9edf7">서버OS</td>
				<td >
				
					<select name="serveros"  style="width:110px">
						<option value="Linux" <c:if test="${list.serveros == 'Linux'}">selected="selected"</c:if>>Linux</option>
						<option value="Windows" <c:if test="${list.serveros =='Windows'}">selected="selected"</c:if>>Windows</option>
					</select>
			</tr>
			<tr>
				<td bgcolor="#d9edf7">사용유무</td>
				<td >
					<select name="useyn"  style="width:110px">
						<option value="1" <c:if test="${list.useyn eq 1}">selected="selected"</c:if>>사용</option>
						<option value="2" <c:if test="${list.useyn eq 2}">selected="selected"</c:if>>미사용</option>
					</select>
				</td>
			</tr>
			<tr>
				<td bgcolor="#d9edf7">실행위치</td>
				<td >
					<select name="execplace"  style="width:110px">
						<option value="1" <c:if test="${list.execplace eq 1}">selected="selected"</c:if>>내부</option>
						<option value="2" <c:if test="${list.execplace eq 2}">selected="selected"</c:if>>외부</option>
					</select>
				</td>
			</tr>
			<tr>
				<td bgcolor="#d9edf7">실행계획</td>
				<td ><input type="text" name="cronexpression" id="cronexpression" style="width:200px" maxlength="40" value="${list.cronexpression}"></td>
			</tr>
			<tr>
				<td bgcolor="#d9edf7">SMS 발송리스트</td>
				<td ><textarea name="smslist" id="smslist" cols="28" rows="2" style="resize:none;">${list.smslist}</textarea> </td>
			</tr>
			<tr>
				<td bgcolor="#d9edf7">E-MAIL 발송리스트</td>
				<td ><textarea name="emaillist"  id="emaillist" cols="28" rows="2" style="resize:none;">${list.emaillist}</textarea> </td>
			</tr>
			<tr>
				<td colspan="2"><p class="text-center">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a id="btn_save"  class="btn btn-success">저장하기</a></p></td>
			</tr>
		</table>
	</form>
		
	<script type="text/javascript">
		jQuery(document).ready(function(){
			//저장하기 버튼
			jQuery("#btn_save").bind("click",function(){
				var paramList = $("#from_savejob").serialize();
					paramList =  paramList + "&tgp=popup_layer";
				if($("#name").val().length == 0){
					alert("배치명을 입력해주세요.");
					return;
				}
				if($("#clazz").val().length == 0){
					alert("배치 클랙스명을 입력해주세요.");
					return;
				}
				if($("#serverip").val().length == 0){
					alert("IP를 입력해주세요.");
					return;
				}
				if($("#cronexpression").val().length == 0){
					alert("실행계획을 입력해주세요.");
					return;
				}
				
				//seq 값에 유무에 따라 신규와 수정변경
				if($("#setseq").val() == "") {
					// 신규등록
					A.getHtml("/state/createBatchJob.b1", paramList, saveAjaxCallBack());
				}
				else {
					// 수정
					A.getHtml("/state/updateBatchJob.b1", paramList, saveAjaxCallBack());
				}
			});
			
			function saveAjaxCallBack(){
				alert("저장이 완료되었습니다.");
				//popClose();
			}
			
		});
		
	</script>
</div>