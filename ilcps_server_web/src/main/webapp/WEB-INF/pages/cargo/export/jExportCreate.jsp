<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="../../base.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title></title>
</head>

<body>
<form name="icform" method="post">
   <!-- 保存的是购销合同的id,用逗号空格进行分隔 -->
   <input type="hidden" name="contractIds" value="${id }" />
<div id="menubar">
<div id="middleMenubar">
<div id="innerMenubar">
  <div id="navMenubar">
<ul>
<li id="save"><a href="#" onclick="formSubmit('exportAction_insert','_self');this.blur();">保存</a></li>
<li id="back"><a href="#" onclick="history.go(-1);">返回</a></li>
</ul>
  </div>
</div>
</div>
</div>
   
  <div class="textbox-title">
	<img src="${ctx }/skin/default/images/icon/currency_yen.png"/>
   新增出口报运
  </div> 
  
    <div>
		<table class="commonTable" cellspacing="1">
	        <tr>
	            <td class="columnTitle">信用证号</td>
	            <td class="tableContentAuto">
	            <input type="radio" name="lcno" value="L/C" checked="checked" />L/C
	            <input type="radio" value="T/T" name="lcno" />T/T
	            </td>
	       
	            <td class="columnTitle">收货人及地址</td>
	            <td class="tableContent"><input type="text" name="consignee"/></td>
	        </tr>	
	        <tr>
	            <td class="columnTitle">唛头</td>
	            <td class="tableContent"><input type="text" name="marks"/></td>
	       
	            <td class="columnTitle">装运港</td>
	            <td class="tableContent"><input type="text" name="shipmentPort"/></td>
	        </tr>	
	        <tr>
	            <td class="columnTitle">目的港</td>
	            <td class="tableContent"><input type="text" name="destinationPort"/></td>
	       
	            <td class="columnTitle">运输方式</td>
	            <td class="tableContentAuto">
	            <input type="radio" name="transportMode" value="SEA" checked="checked" />SEA
	            <input type="radio" value="AIR" name="transportMode"  />AIR
	            </td>
	        </tr>
	        <tr>
	        	<td class="columnTitle">价格条件</td>
	            <td class="tableContentAuto">
	            	<input type="radio" name="priceCondition" value="FBO" checked="checked"/>FBO
	             	<input type="radio" value="CIF" name="priceCondition"/>CIF
	            </td>
	              <td class="columnTitle">备注</td>
	            <td class="tableContent"><input type="text" name="remark" /></td>
	        </tr>	
		</table>
	</div>
 
 
</form>
</body>
</html>

