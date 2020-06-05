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
<li id="save"><a href="#" onclick="formSubmit('deptAction_update','_self');this.blur();">保存</a></li>
<li id="back"><a href="#" onclick="history.go(-1);">返回</a></li>
</ul>
  </div>
</div>
</div>
</div>
   
  <div class="textbox-title">
	<img src="${ctx }/skin/default/images/icon/currency_yen.png"/>
   查看用户
  </div> 
  
<!--<s:debug></s:debug>-->
 
    <div>
		<table class="commonTable" cellspacing="1">
	        <tr>
	            <td class="columnTitle">所在部门：</td>
	            <td class="tableContent">
	            	${dept.deptName }
	            </td>
	        </tr>		
	        <tr>
	            <td class="columnTitle">用户名：</td>
	            <td class="tableContent">${userName }</td>
	        </tr>	
	         <tr>
	            <td class="columnTitle">职位级别：</td>
	            <td class="tableContent">
					<c:if test="${userinfo.degree==0}">总裁</c:if>
					<c:if test="${userinfo.degree==1}">副总</c:if>
					<c:if test="${userinfo.degree==2}">部门总经理</c:if>
					<c:if test="${userinfo.degree==3}">部门经理</c:if>
					<c:if test="${userinfo.degree==4}">普通员工</c:if>
				</td>
	        </tr>	
	         <tr>
	            <td class="columnTitle">状态：</td>
	            <td class="tableContent">${state==0?'停用':'启用' }</td>
	        </tr>		
		</table>
	</div>
 </form>
</body>
</html>