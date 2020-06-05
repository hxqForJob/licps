<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="../../base.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title></title>
	<script type="text/javascript" src="${ctx }/js/datepicker/WdatePicker.js"></script>
</head>

<body>
<form name="icform" method="post">
	<input type="hidden" name="id" value="${id}"/>

<div id="menubar">
<div id="middleMenubar">
<div id="innerMenubar">
  <div id="navMenubar">
<ul>
<li id="back"><a href="#" onclick="history.go(-1);">返回</a></li>
</ul>
  </div>
</div>
</div>
</div>
   
  <div class="textbox-title">
	<img src="${ctx }/skin/default/images/icon/currency_yen.png"/>
   浏览财务单
  </div> 
    <div>
		<table class="commonTable" cellspacing="1">
  			<tr>
	            <td class="columnTitle">制单人：</td>
	            <td class="tableContent">${maker}</td>
	     
	            <td class="columnTitle">制单日期：</td>
	            <td class="tableContent">${makeDate}</td>
	        </tr>	
		</table>
	</div>
</form>
</body>
</html>

