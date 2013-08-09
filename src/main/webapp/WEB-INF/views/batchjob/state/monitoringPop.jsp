<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page session="false" %>

<html>
<head>
<!-- Start JqPlot Chart 관련 CSS & JS files -->
<script type="text/javascript" src="/resources/js/chart/jquery.js"></script>
<script type="text/javascript" src="/resources/js/chart/jquery.jqplot.js"></script>
<script type="text/javascript" src="/resources/js/chart/plugins/jqplot.barRenderer.js"></script>
<script type="text/javascript" src="/resources/js/chart/plugins/jqplot.categoryAxisRenderer.js"></script>
<script type="text/javascript" src="/resources/js/chart/plugins/jqplot.pointLabels.js"></script>
<link rel="stylesheet" type="text/css" href="/resources/css/chart/jquery.jqplot.css" />
	<!--  End  JqPlot Chart 관련 CSS & JS files -->	
<script type="text/javascript">
$(document).ready(function() {	
	var batchSeq = ${seq};
	var batchNm = "${batchName}";
	var serverUseCpu = ${serverUseCpu};
	var batchUseCpu = ${batchUseCpu};
	var serverUseMem = ${serverUseMem / 1000};
	var batchUseMem = ${batchUseMem / 1000};
	document.title = "Server Monitoring_" + batchNm;
	
	if(serverUseCpu==null) serverUseCpu = 0;
	if(batchUseCpu==null) batchUseCpu = 0;
	if(serverUseMem==null) serverUseMem = 0;
	if(batchUseMem==null) batchUseMem = 0;

	$("#cpuInfo").remove();
	$("#memoryInfo").remove();
	var insertTag = $("<td id='cpuInfo'></td><td id='memoryInfo'></td>");
	insertTag.insertAfter($("#graph"));
	
	getServerInfo(batchSeq, batchNm, serverUseCpu, batchUseCpu, serverUseMem, batchUseMem);
		
});

//자동새로고침
setTimeout("history.go(0);", 3000); //1000=1초 60000=60초
start = 3; //타이머의 시간. 상단의 0세개를 지운것과 같아야함
function timer(){ 
    if(start>=0){ 
        times.innerHTML=start; 
        setTimeout('timer()',3000); 
    } 
    start--; 
}

//서버 및 배치의 CPU & Memory 정보 취득
function getServerInfo(batchSeq, batchNm, serverUseCpu, batchUseCpu, serverUseMem, batchUseMem){
	
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
            {label:'Server'},
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
            {label:'Server'},
            {label:batchNm}
        ],
        legend: {
            show: true,
            placement: 'outsideGrid'
        },
        
        axesDefaults:{  
			max: 1000,
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

</script>	
</head>
<body>
<div>
	<table>
		<tr id="graph">
			<td id="cpuInfo"></td>
			<td id="memoryInfo"></td>
		</tr>
	</table>	
</div>
<br>

</body>
</html>