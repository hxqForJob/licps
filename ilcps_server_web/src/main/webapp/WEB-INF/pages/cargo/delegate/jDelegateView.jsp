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
   浏览委托单
  </div> 
  

 
    <div>
		<table class="commonTable" cellspacing="1">
  <tr>
	            <td class="columnTitle">货主：</td>
	            <td class="tableContent">${shipper}</td>
	     
	            <td class="columnTitle">提单抬头：</td>
	            <td class="tableContent">${consihnee}</td>
	        </tr>	
	        <tr>
	            <td class="columnTitle">正本通知人：</td>
	            <td class="tableContent">${originalNotifyParty}</td>
	      
	            <td class="columnTitle">信用证：</td>
	            <td class="tableContent">
	            ${lcNo}
	            </td>
	        </tr>
	         <tr>
	            <td class="columnTitle">装运港：</td>
	            <td class="tableContent">${portLoading}</td>
	      
	            <td class="columnTitle">转船港：</td>
	            <td class="tableContent">${portTrans}</td>
	        </tr>	
	      	<tr>
	            <td class="columnTitle">卸货港：</td>
	            <td class="tableContent">${portDischange}</td>
	      
	            <td class="columnTitle">运输方式：</td>
	            <td class="tableContent">
	            ${transportMode}
	            </td>
	        </tr>
	        <tr>
	            <td class="columnTitle">装期：</td>
	            <td class="tableContent">
	         
	            <fmt:formatDate value='${installPeriod}' pattern="yyyy-MM-dd"/>
	            </td>
	      
	            <td class="columnTitle">效期：</td>
	            <td class="tableContent">
	           <fmt:formatDate value='${effectPeriod}' pattern="yyyy-MM-dd"/>
	            </td>
	        </tr>
	         <tr>
	            <td class="columnTitle">是否分批：</td>
	            <td class="tableContent">
	            ${isBatches==1?'是':'否'}
	            </td>
	      
	            <td class="columnTitle">是否转船：</td>
	            <td class="tableContent">
	            ${isTransshipment==1?'是':'否'}
	            </td>
	        </tr>
	         <tr>
	            <td class="columnTitle">份数：</td>
	            <td class="tableContent">${copies}</td>
	      
	            <td class="columnTitle">复核人：</td>
	            <td class="tableContent">${reviewer}</td>
	        </tr>
	         <tr>
	            <td class="columnTitle">扼要说明：</td>
	            <td class="tableContent">
	            	${marksAndNos}
	            </td>
	            <td class="columnTitle">运输要求：</td>
	            <td class="tableContent">
	            ${specialCondition}
	            </td>
	        </tr>
	         <tr>
	            <td class="columnTitle">运费说明：</td>
	            <td class="tableContent">
	            ${description}
	            </td>
	        </tr>								
		</table>
	</div>
</form>
</body>
</html>

