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
		url:'../stat/statChartAction_getProductSale',
		type:'post',
		dataType:'json',
		success:function(value)
		{
			 $('#container').highcharts({
			        chart: {
			            type: 'column'
			        },
			        title: {
			            text: '产品销售情况'
			        },
			        subtitle: {
			            text: ''
			        },
			        xAxis: {
			            categories: value.x,
			            crosshair: true,
			            title: {
			                text: '货号'
			            }
			        },
			        yAxis: {
			            min: 0,
			            title: {
			                text: '销售额 (元)'
			            }
			        },
			        tooltip: {
			            headerFormat: '<span style="font-size:10px">{point.key}</span><table>',
			            pointFormat: '<tr><td style="color:{series.color};padding:0">总销售额: </td>' +
			                '<td style="padding:0"><b>{point.y:.1f} 元</b></td></tr>',
			            footerFormat: '</table>',
			            shared: true,
			            useHTML: true
			        },
			        plotOptions: {
			            column: {
			                pointPadding: 0.2,
			                borderWidth: 0
			            }
			        },
			        series: [{
			            name: '货号',
			            data: value.data

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