<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<div class="divBoardHome">
	
	<div class="tile">
		<blockquote>
			<p><b>배치정보</b></p>
			<small>배치작업에 대한 상태 정보 조회 화면.</small>
		</blockquote>
		<p>
			<a id="btnBatchJob" class="btn btn-inverse">보기</a>
		</p>
	</div>
	
	<div class="tile">
		<blockquote>
			<p><b>배치 잡 로그</b></p>
			<small>특정배치에 대한 배치실행 히스토리목록 조회 화면.</small>
		</blockquote>
		<p>
			<a id="btnBatchJobLog" class="btn btn-inverse">보기</a>
		</p>
	</div>

	<div class="tile">
		<blockquote>
			<p><b>배치 스텝 로그</b></p>
			<small>배치실행 한건에 대한 상세 로그 조회 화면.</small>
		</blockquote>
		<p>
			<a id="btnBatchStepLog" class="btn btn-inverse">보기</a>
		</p>
	</div>

	<script type="text/javascript">
		
		jQuery(document).ready(function(){
				
			//배치정보 클릭
			jQuery("#btnBatchJob").bind("click",function(){
				A.getHtml("/state/batchjobList.b1", "tgp=divContents");
			});
			
			//배치 잡 로그 클릭
			jQuery("#btnBatchJobLog").bind("click",function(){
				A.getHtml("/batchjoblog/board.b1", "tgp=divContents");
			});
			
			//배치 스텝 로그 클릭
			jQuery("#btnBatchStepLog").bind("click",function(){
				A.getHtml("/batchsteplog/board.b1", "tgp=divContents");
			});
			
		});
		
	</script>
	
</div><!-- divBoardHome -->