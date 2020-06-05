<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ include file="../base.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title></title>
	<script type="text/javascript" src="${ctx }/js/datepicker/WdatePicker.js""></script>
</head>
<script type="text/javascript">
function formSubmit2 (url,sTarget){
	var newPwd=document.getElementById("newPwd").value;
	var surePwd=document.getElementById("surePwd").value;
	if(newPwd!=surePwd)
		{
			document.getElementById("msg").innerHTML="两次密码输入不正确,请重新输入";
			document.getElementById("newPwd").value="";
			document.getElementById("surePwd").value="";
			return;
		}
    document.forms[0].target = sTarget
    document.forms[0].action = url;
    document.forms[0].submit();
    return true;
}
</script>
<body>
<form name="icform" method="post">
     <input type="hidden" name="id" value="${id }">
<div id="menubar">
<div id="middleMenubar">
<div id="innerMenubar">
  <div id="navMenubar">
<ul>
<li id="save"><a href="#" onclick="formSubmit2('homeAction_updateUserPwd','_self');this.blur();">确认</a></li>
<li id="back"><a href="${ctx}/homeAction_toUpdateUserInfo?moduleName=home">取消</a></li>
</ul>
  </div>
</div>
</div>
</div>
   
<div class="textbox" id="centerTextbox">
  <div class="textbox-header">
  <div class="textbox-inner-header">
  <div class="textbox-title">
   更新用户密码
  </div> 
  </div>
  </div>
  

 
    <div>
		<table class="commonTable" cellspacing="1">
	        <tr>
	            <td class="columnTitle">旧密码：</td>
	            <td class="tableContent"><input type="password"  name="oldPwd" value="${oldPwd}"/></td>
	        </tr>
	      	<tr>
	            <td class="columnTitle">新密码：</td>
	            <td class="tableContent"><input type="password" id="newPwd" name="newPwd" value="${newPwd}"/></td>
	        </tr> 
	        <tr>
	            <td class="columnTitle">确认新密码：</td>
	            <td class="tableContent"><input type="password" id="surePwd" name="customName"/></td>
	        </tr> 
		</table>
		<span id="msg" style="color: red;">${errorMsg}</span>
	</div>
</form>
</body>
</html>