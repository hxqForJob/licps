<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="../../base.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title></title>
	<script type="text/javascript" src="${ctx }/js/datepicker/WdatePicker.js""></script>
</head>

<body>
<form name="icform" method="post">
	<input type="hidden" name="id" value="${id}"/>

<div id="menubar">
<div id="middleMenubar">
<div id="innerMenubar">
  <div id="navMenubar">
<ul>
<li id="save"><a href="#" onclick="formSubmit('delegateListAction_update','_self');this.blur();">保存</a></li>
<li id="back"><a href="#" onclick="history.go(-1);">返回</a></li>
</ul>
  </div>
</div>
</div>
</div>
   
  <div class="textbox-title">
	<img src="${ctx }/skin/default/images/icon/currency_yen.png"/>
   修改委托单
  </div> 
   <div>
		<table class="commonTable" cellspacing="1">
	        <tr>
	            <td class="columnTitle">货主：</td>
	            <td class="tableContent"><input type="text" name="shipper" value="${shipper}"/></td>
	     
	            <td class="columnTitle">提单抬头：</td>
	            <td class="tableContent"><input type="text" name="consihnee" value="${consihnee}"/></td>
	        </tr>	
	        <tr>
	            <td class="columnTitle">正本通知人：</td>
	            <td class="tableContent"><input type="text" name="originalNotifyParty" value="${originalNotifyParty}"/></td>
	      
	            <td class="columnTitle">信用证：</td>
	            <td class="tableContentAuto">
	            	<input type="radio" name="lcNo" <c:if test="${lcNo=='L/C'}">checked</c:if> value="L/C"/>L/C
	            	<input type="radio" name="lcNo" <c:if test="${lcNo=='T/T'}">checked</c:if> value="T/T"/>T/T
	            </td>
	        </tr>
	         <tr>
	            <td class="columnTitle">装运港：</td>
	            <td class="tableContent"><input type="text" name="portLoading" value="${portLoading}"/></td>
	      
	            <td class="columnTitle">转船港：</td>
	            <td class="tableContent"><input type="text" name="portTrans" value="${portTrans}"/></td>
	        </tr>	
	      	<tr>
	            <td class="columnTitle">卸货港：</td>
	            <td class="tableContent"><input type="text" name="portDischange" value="${portDischange}"/></td>
	      
	            <td class="columnTitle">运输方式：</td>
	            <td class="tableContentAuto">
	            	<input type="radio" name="transportMode" <c:if test="${transportMode=='SEA'}">checked</c:if> value="SEA"/>SEA
	            	<input type="radio" name="transportMode" <c:if test="${transportMode=='AIR'}">checked</c:if> value="AIR"/>AIR
	            </td>
	        </tr>
	        <tr>
	            <td class="columnTitle">装期：</td>
	            <td class="tableContent">
	            	<input type="text" style="width:90px;" name="installPeriod"
	            	 value="<fmt:formatDate value='${installPeriod}' pattern="yyyy-MM-dd"/>"
	             	onclick="WdatePicker({el:this,isShowOthers:true,dateFmt:'yyyy-MM-dd'});"/>
	            </td>
	      
	            <td class="columnTitle">效期：</td>
	            <td class="tableContent">
	            	<input type="text" style="width:90px;" name="effectPeriod"
	            	 value="<fmt:formatDate value='${effectPeriod}' pattern="yyyy-MM-dd"/>"
	             	onclick="WdatePicker({el:this,isShowOthers:true,dateFmt:'yyyy-MM-dd'});"/>
	            </td>
	        </tr>
	         <tr>
	            <td class="columnTitle">是否分批：</td>
	            <td class="tableContentAuto">
	            	<input type="radio" name="isBatches" <c:if test="${isBatches==1}">checked</c:if> value="1"/>是
	            	<input type="radio" name="isBatches" <c:if test="${isBatches==0}">checked</c:if> value="0"/>否
	            </td>
	      
	            <td class="columnTitle">是否转船：</td>
	            <td class="tableContentAuto">
	            	<input type="radio" name="isTransshipment" <c:if test="${isTransshipment==1}">checked</c:if> value="1"/>是
	            	<input type="radio" name="isTransshipment" <c:if test="${isTransshipment==0}">checked</c:if> value="0"/>否
	            </td>
	        </tr>
	         <tr>
	            <td class="columnTitle">份数：</td>
	            <td class="tableContent"><input type="number" name="copies" value="${copies}"/></td>
	      
	            <td class="columnTitle">复核人：</td>
	            <td class="tableContent"><input type="text" name="reviewer" value="${reviewer}"/></td>
	        </tr>
	         <tr>
	            <td class="columnTitle">扼要说明：</td>
	            <td class="tableContent">
	            		<textarea rows="15" name="marksAndNos" cols="75">${marksAndNos}</textarea>
	            </td>
	            <td class="columnTitle">运输要求：</td>
	            <td class="tableContent">
	            		<textarea rows="15" name="specialCondition" cols="75">${specialCondition}</textarea>
	            </td>
	        </tr>
	         <tr>
	            <td class="columnTitle">运费说明：</td>
	            <td class="tableContent">
	            		<textarea rows="15" name="description" cols="75">${description}</textarea>
	            </td>
	        </tr>											
		</table>
	</div>
</form>
</body>
</html>

