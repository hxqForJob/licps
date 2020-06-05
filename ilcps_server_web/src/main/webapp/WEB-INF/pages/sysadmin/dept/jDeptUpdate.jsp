<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="../../base.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title></title>
</head>

<script type="text/javascript">
/* $(function(){
	var checkbox=document.getElementById("parentDep");
	for(i=0;i<checkbox.length;i++)
		{
			var isCommon=checkbox.options[i].value=='${parent.id}';
			checkbox[i].selected=isCommon;
		} 
 	}) */
function updateDept(a) {
	var flag=window.confirm("是否确认更新？");
	if(flag)
		{
			formSubmit('deptAction_update','_self');
			a.blur();
		}
	
}
</script>
<body>
<form name="icform" method="post">
      <input type="hidden" name="id" value="${id}"/>
       <input type="hidden" name="state" value="${state}"/>
      
<div id="menubar">
<div id="middleMenubar">
<div id="innerMenubar">
  <div id="navMenubar">
<ul>
<li id="save"><a href="#" onclick="updateDept(this)">保存</a></li>
<li id="back"><a href="#" onclick="history.go(-1);">返回</a></li>
</ul>
  </div>
</div>
</div>
</div>
   
  <div class="textbox-title">
	<img src="${ctx }/skin/default/images/icon/currency_yen.png"/>
   修改部门
  </div> 
  
<%-- <s:debug></s:debug> --%>
    <div>
		<table class="commonTable" cellspacing="1">
	        <tr>
	            <td class="columnTitle">上级部门：</td>
	            <td class="tableContent">
	            	<s:select id="parentDep" name="parent.id" list="deptList"
	            		listKey="id" listValue="deptName"
	            		headerKey="" headerValue="--请选择--"
	            	></s:select>
	            	
	            	<%-- <select>
	            	    <c:forEach   var="dept">
	            	        <option  <c:if test="${dept.deptName=deptName}">selected</c:if>></option>
	            	    </c:forEach>
	            	</select> --%>
	            </td>
	        </tr>		
	        <tr>
	            <td class="columnTitle">部门名称：</td>
	            <td class="tableContent"><input type="text" name="deptName" value="${deptName }"/>
	            </td>
	        </tr>		
		</table>
	</div>
 </form>
</body>
</html>