<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="../../base.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title></title>
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
   查看部门
  </div> 
<%--   <s:debug></s:debug> --%>		
    <div>
		<table class="commonTable" cellspacing="1">
	        <tr>
	            <td class="columnTitle">工厂名</td>
	            <td class="tableContent">${fullName}</td>
	        </tr>
	         <tr>
	            <td class="columnTitle">工厂简称</td>
	            <td class="tableContent">${factoryName}</td>
	        </tr>
	         <tr>
	            <td class="columnTitle">联系人</td>
	            <td class="tableContent">${contacts}</td>
	        </tr>	
	         <tr>
	            <td class="columnTitle">电话</td>
	            <td class="tableContent">${phone}</td>
	        </tr>	
	         <tr>
	            <td class="columnTitle">手机</td>
	            <td class="tableContent">${mobile}</td>
	        </tr>	
	         <tr>
	            <td class="columnTitle">传真</td>
	            <td class="tableContent">${fax }</td>
	        </tr>
	         <tr>
	            <td class="columnTitle">验货员</td>
	            <td class="tableContent">${inspector }</td>
	        </tr>	
	        <tr>
	            <td class="columnTitle">说明</td>
	            <td class="tableContent">${remark }</td>
	        </tr>	
	        <tr>
	            <td class="columnTitle">类型</td>
	            <td class="tableContent">${ctype}</td>
	        </tr>
	        <tr>
	            <td class="columnTitle">状态</td>
	            <td class="tableContent">${state==0?"禁用":"启用" }</td>
	        </tr>	
	         <tr>
	            <td class="columnTitle">地址</td>
	            <td class="tableContent">${address}</td>
	        </tr>						
		</table>
	</div>
 </form>
</body>
</html>