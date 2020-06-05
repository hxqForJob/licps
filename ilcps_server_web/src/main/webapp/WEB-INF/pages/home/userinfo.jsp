<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="../base.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title></title>
	<script type="text/javascript" src="${ctx }/js/datepicker/WdatePicker.js""></script>
</head>

<body>
<form name="icform" method="post">
     <input type="hidden" name="id" value="${id }">
<div id="menubar">
<div id="middleMenubar">
<div id="innerMenubar">
  <div id="navMenubar">
<ul>
<li id="save"><a href="#" onclick="formSubmit('homeAction_UpdateUserInfo','_self');this.blur();">保存</a></li>
<li style="width: 75px;" id="save"><a href="#" onclick="formSubmit('homeAction_toUpdateUserPwd','_self');this.blur();">修改密码</a></li>
<li id="back"><a href="#" onclick="history.go(-1);">返回</a></li>
</ul>
  </div>
</div>
</div>
</div>
   
<div class="textbox" id="centerTextbox">
  <div class="textbox-header">
  <div class="textbox-inner-header">
  <div class="textbox-title">
   更新用户信息
  </div> 
  </div>
  </div>
  
<!--<s:debug></s:debug>-->
 
    <div>
		<table class="commonTable" cellspacing="1">
	        <tr>
	            <td class="columnTitle">用户名：</td>
	            <td class="tableContent"><input type="text" readonly="readonly" name="customName" value="${userName}"/></td>
	        </tr>
	        <tr>
	        	<td class="columnTitle">性别：</td>
	            <td class="tableContentAuto">
	            	<input type="radio" name="userinfo.gender" value="1" ${userinfo.gender=='1'?"checked":"" }  class="input">男
	            	<input type="radio" name="userinfo.gender" value="0" ${userinfo.gender=='0'?"checked":"" } class="input">女
	            </td>
	        </tr>		
	        <tr>
	            <td class="columnTitle">真实姓名：</td>
	            <td class="tableContent"><input type="text" name="userinfo.name" value="${userinfo.name }"/></td>
	        </tr>		
	        <tr>
	            <td class="columnTitle">电话号码：</td>
	            <td class="tableContent"><input type="text" name="userinfo.telephone" value="${userinfo.telephone }"/></td>
	        </tr>
	        <tr>
	            <td class="columnTitle">邮箱：</td>
	            <td class="tableContent"><input type="text" name="userinfo.email" value="${userinfo.email }"/></td>
	        </tr>		
	        <tr>
	            <td class="columnTitle">出生日期：</td>
	            <td class="tableContent">
					<input type="text" style="width:90px;" name="userinfo.birthday"
	            	 value="<fmt:formatDate value='${userinfo.birthday }' pattern='yyyy-MM-dd' />"
	             	onclick="WdatePicker({el:this,isShowOthers:true,dateFmt:'yyyy-MM-dd'});"/>
				</td>
	        </tr>		
		</table>
	</div>
 
 
</form>
</body>
</html>
