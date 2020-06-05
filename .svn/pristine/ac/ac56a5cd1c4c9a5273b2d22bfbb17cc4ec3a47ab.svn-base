<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="../base.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<title>生产厂家销售情况</title>

			<script type="text/javascript" src="../components/highCharts/jquery.min.js"></script>
		<style type="text/css">
			${demo.css}
		</style>
		<script type="text/javascript">
$(function () {
	$.ajax({
		url:'../stat/statChartAction_getFactorySale',
		type:'post',
		dataType:'json',
		success:function(val)
		{
			$('#container').highcharts({
		        chart: {
		            plotBackgroundColor: null,
		            plotBorderWidth: null,
		            plotShadow: false,
		            type: 'pie'
		        },
		        title: {
		            text: '生产厂家销售情况'
		        },
		        tooltip: {
		            pointFormat: '{series.name}: <b>{point.percentage:.1f}%</b>'
		        },
		        plotOptions: {
		            pie: {
		                allowPointSelect: true,
		                cursor: 'pointer',
		                dataLabels: {
		                    enabled: true,
		                    format: '<b>{point.name}</b>: {point.percentage:.1f} %',
		                    style: {
		                        color: (Highcharts.theme && Highcharts.theme.contrastTextColor) || 'black'
		                    }
		                }
		            }
		        },
		        series: [{
		            name: "所占比例",
		            colorByPoint: true,
		            data: val
		        }]
		    });
		}
	})
    
});
		</script>
	</head>
	<body>
<script src="../components/highCharts/highcharts.js"></script>
<script src="../components/highCharts/modules/exporting.js"></script>

<div id="container" style="min-width: 310px; height: 400px; max-width: 600px; margin: 0 auto"></div>

	</body>
</html>