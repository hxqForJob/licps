<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="../base.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<title>Highcharts Example</title>

			<script type="text/javascript" src="../components/highCharts/jquery.min.js"></script>
		<style type="text/css">
${demo.css}
		</style>
		<script type="text/javascript">
$(function () {
	$.ajax({
		url:'../stat/statChartAction_getOnlineInfo',
		type:'post',
		dataType:'json',
		success:function(value)
		{
			//console.info(value);
   	 $('#container').highcharts({
        title: {
            text: '访问压力图',
            x: -20 //center
        },
        subtitle: {
            text: '访问量',
            x: -20
        },
        xAxis: {
            categories: value.time
        },
        yAxis: {
            title: {
                text: '人数'
            },
            plotLines: [{
                value: 0,
                width: 1,
                color: '#808080'
            }]
        },
        tooltip: {
            valueSuffix: '人数'
        },
        legend: {
            layout: 'vertical',
            align: 'right',
            verticalAlign: 'middle',
            borderWidth: 0
        },
        series: [{
            name: '在线人数',
            data:  value.count
        	}]
    });
		}
	});
});
		</script>
	</head>
	<body>
<script src="../components/highCharts/highcharts.js"></script>
<script src="../components/highCharts/modules/exporting.js"></script>

<div id="container" style="min-width: 310px; height: 400px; margin: 0 auto"></div>

	</body>
</html>