<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="../../base.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title></title>
	<script type="text/javascript" src="${ctx }/js/datepicker/WdatePicker.js"></script>
</head>

<body>
<form name="icform" method="post">
<input type="hidden" name="id" value="${id}">
<div id="menubar">
<div id="middleMenubar">
<div id="innerMenubar">
  <div id="navMenubar">
<ul>
<li id="save"><a href="#" onclick="formSubmit('factoryAction_insert','_self');this.blur();">保存</a></li>
<li id="back"><a href="#" onclick="history.go(-1);">返回</a></li>
</ul>
  </div>
</div>
</div>
</div>
   
  <div class="textbox-title">
	<img src="${ctx }/skin/default/images/icon/currency_yen.png"/>
   修改工厂
  </div> 
  
<%--  <s:debug></s:debug>  --%>
 
    <div>
		<table class="commonTable" cellspacing="1">
	       <tr>
	            <td class="columnTitle">工厂名</td>
	            <td class="tableContent"><input type="text" value="${fullName}" name="fullName"></td>
	        </tr>
	         <tr>
	            <td class="columnTitle">工厂简称</td>
	            <td class="tableContent"><input type="text" value="${factoryName}" name="factoryName"></td>
	        </tr>
	         <tr>
	            <td class="columnTitle">联系人</td>
	            <td class="tableContent"><input type="text" value="${contacts}" name="contacts"></td>
	        </tr>	
	         <tr>
	            <td class="columnTitle">电话</td>
	            <td class="tableContent"><input type="text" value="${phone}" name="phone"></td>
	        </tr>	
	         <tr>
	            <td class="columnTitle">手机</td>
	            <td class="tableContent"><input type="text" value="${mobile}" name="mobile"></td>
	        </tr>	
	         <tr>
	            <td class="columnTitle">传真</td>
	            <td class="tableContent"><input type="text" value="${fax}" name="fax"></td>
	        </tr>
	         <tr>
	            <td class="columnTitle">验货员</td>
	            <td class="tableContent"><input type="text" value="${inspector}" name="inspector"></td>
	        </tr>	
	        <tr>
	            <td class="columnTitle">类型</td>
	            <td class="tableContentAuto">
	            <input type="radio" class="input" name="ctype" value="货物" ${ctype=='货物'?'checked':'' }>货物
	            <input type="radio" class="input" name="ctype" value="附件"${ctype=='附件'?'checked':'' }>附件
	            </td>
	        </tr>
	         <tr>
	            <td class="columnTitle">说明</td>
	            <td class="tableContent"><textarea name="remark" rows="10" cols="150">${remark }</textarea></td>
	        </tr>	
	         <tr>
	            <td class="columnTitle">地址</td>
	            <td class="tableContent"><textarea name="address" rows="10" cols="150">${address }</textarea></td>
	        </tr>					
		</table>
	</div>
 </form>
</body>
</html>