<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page session="false" %>
<jsp:include page="/WEB-INF/views/header.jsp" flush="fasle" />

<html>
	<div id="graph">	
	</div>
	
	<script type="text/javascript">
	
		var batchSeq = ${seq};
		var batchNm = "${batchName}";
		var serverIp = "${serverIp}";
		var serverUseCpu = ${serverUseCpu};
		var batchUseCpu = ${batchUseCpu};
		var serverUseMem = ${serverUseMem/1000};
		var batchUseMem = ${batchUseMem/1000};
		
		$(document).ready(function() {	
			init();
		});
		
		function init(){
			if(serverUseCpu=="")	serverUseCpu = 0;
			if(batchUseCpu=="")		batchUseCpu = 0;
			if(serverUseMem=="")	serverUseMem = 0;
			if(batchUseMem=="")		batchUseMem = 0;
		
			document.title = "Server Monitoring_" + batchNm;
		
			$("#Info").remove();
		
			var tag1 = "<table id='Info'>";
			var tag2 = "<tr><td id='cpuInfo'></td><td id='memoryInfo'></td></tr>";
			var insertTag = $(tag1+tag2);
			insertTag.insertAfter($("#graph"));
			getServerInfo(batchSeq, batchNm, serverUseCpu, batchUseCpu, serverUseMem, batchUseMem, serverIp);			
		}
		
		//자동새로고침
		setInterval("pageReload();", 3000); //1000=1초 60000=60초
		start = 3; //타이머의 시간. 상단의 0세개를 지운것과 같아야함
		function timer(){ 
		    if(start>=0){ 
		        times.innerHTML=start; 
		        setTimeout('timer()',3000); 
		    } 
		    start--; 
		}
		
		function pageReload(){
			$("#graph").load("/state/monitoringPop.b1?seq="+batchSeq);
		}
		
		/* ------------------------------------------ Chart Plugin ------------------------------------------ */
		//서버 및 배치의 CPU & Memory 정보 취득
		function getServerInfo(batchSeq, batchNm, serverUseCpu, batchUseCpu, serverUseMem, batchUseMem, serverIp){
			
		    var s1 = [parseInt(serverUseCpu)];
		    var s2 = [parseInt(batchUseCpu)];
		    
		    var ticks = [''];
		     
		    var plot1 = $.jqplot('cpuInfo', [s1, s2], {
		    	title: "CPU Information",
		        seriesDefaults:{
		            renderer:$.jqplot.BarRenderer,
		            rendererOptions: {
		            	fillToZero: true,
		            	animation: {                    
		            		show: false                
		            	},
		            	showMarker: false
					},
		    		pointLabels: { show: true, location: 'ne', formatString: '%d%'}
		        },
		        series:[
		            {label:serverIp},
		            {label:batchNm}
		        ],
		        legend: {
		            show: true,
		            placement: 'outsideGrid'
		        },
		        
		        axesDefaults:{  
					max: 100,
					min: 0,
		        	tickRenderer:$.jqplot.CanvasAxisTickRenderer,  
		        	tickOptions: {  
		              	show: true,  
		              	showMark: true,
		              	showLabel: true
		        	},
		        	rendererOptions: {
		        		baselineWidth: 1.5,
		        		baselineColor: '#444444',
		        		drawBaseline: false
		        	}
		      	},
		        
		        axes: {
		            xaxis: {
		                renderer: $.jqplot.CategoryAxisRenderer,
		                ticks: ticks
		            },
		            yaxis: {
		                pad: 1.05,
		                tickOptions: {formatString: '%d%'}
		            }
		        }
		    });
			
		    var s3 = [parseInt(serverUseMem)];
		    var s4 = [parseInt(batchUseMem)];
		    
		    var plot1 = $.jqplot('memoryInfo', [s3, s4], {
		        title: "Memory Information",
		        seriesDefaults:{
		            renderer:$.jqplot.BarRenderer,
		            rendererOptions: {
		            	fillToZero: true,
		            	animation: {                    
		            		show: false                
		            	},
		            	showMarker: false
					},
		    		pointLabels: { show: true, location: 'n', formatString: '%dMB'}
		        },
		        series:[
		            {label:serverIp},
		            {label:batchNm}
		        ],
		        legend: {
		            show: true,
		            placement: 'outsideGrid'
		        },
		        
		        axesDefaults:{  
					max: 2048,
					min: 0,
		        	tickRenderer:$.jqplot.CanvasAxisTickRenderer,  
		        	tickOptions: {  
		              	show: true,  
		              	showMark: true,
		              	showLabel: true
		        	},
		        	rendererOptions: {
		        		baselineWidth: 1.5,
		        		baselineColor: '#444444'//,
		        		//drawBaseline: false
		        	}
		      	},
		        
		        axes: {
		            xaxis: {
		                renderer: $.jqplot.CategoryAxisRenderer,
		                ticks: ticks
		            },
		            yaxis: {
		                pad: 1.05,
		                tickOptions: {formatString: '%dMB'}
		            }
		        }
		    });
		}
		/* ------------------------------------------------------------------------------------------- */
	
	</script>
</html>