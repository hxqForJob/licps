<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>

    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
        <title>amCharts examples</title>
        <link rel="stylesheet" href="${pageContext.request.contextPath }/components/newAmcharts/style.css" type="text/css">
        <script src="${pageContext.request.contextPath }/components/newAmcharts/amcharts/amcharts.js" type="text/javascript"></script>
        <script src="${pageContext.request.contextPath }/components/newAmcharts/amcharts/pie.js" type="text/javascript"></script>

        <script>
            var chart;
			var chartData = ${myChartData};
            /* var chartData = [
                {
                    "country": "United States",
                    "visits": 9252
                },
                {
                    "country": "China",
                    "visits": 1882
                },
                {
                    "country": "Japan",
                    "visits": 1809
                },
                {
                    "country": "Germany",
                    "visits": 1322
                },
                {
                    "country": "United Kingdom",
                    "visits": 1122
                },
                {
                    "country": "France",
                    "visits": 1114
                },
                {
                    "country": "India",
                    "visits": 984
                },
                {
                    "country": "Spain",
                    "visits": 711
                }
            ]; */

            


            AmCharts.ready(function () {
                // PIE CHART
                chart = new AmCharts.AmPieChart();

                // title of the chart
                chart.addTitle("统计销售量的前15名", 16);

                chart.dataProvider = chartData;
                chart.titleField = "productNo";//===============================
                chart.valueField = "amount";//==================================
                chart.sequencedAnimation = true;
                chart.startEffect = "elastic";
                chart.innerRadius = "40%";
                chart.startDuration = 5;
                chart.labelRadius = 15;
                chart.balloonText = "[[title]]<br><span style='font-size:14px'><b>[[value]]</b> ([[percents]]%)</span>";
                // the following two lines makes the chart 3D
                chart.depth3D = 10;
                chart.angle = 15;
				
                chart.creditsPosition = "top-right";
                
                // WRITE
                chart.write("chartdiv");
            });
        </script>
    </head>

    <body>
        <div id="chartdiv" style="width:100%; height:100%;position: absolute;left: -10px;top: 10px"></div>
    </body>

</html>