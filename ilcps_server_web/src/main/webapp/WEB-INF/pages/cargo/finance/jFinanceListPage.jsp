<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="../../baselist.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title></title>
	<script type="text/javascript">
	//结算
	function submit(a,url){
		var ids=document.getElementsByName("id");
		for (var i = 0; i < ids.length; i++) {
			if(ids[i].checked)
				{
				
					if(ids[i].getAttribute("state")!="0")
						{
							alert("当前财务单已结算!");
							return
						}
				}
		}
		submitDept(a,url);
	}
	//删除
	function deleteC(a,url)
	{
		var ids=document.getElementsByName("id");
		for (var i = 0; i < ids.length; i++) {
			if(ids[i].checked)
				{
				
					if(ids[i].getAttribute("state")!="0")
						{
							alert("当前财务单已结算,不能删除!");
							return
						}
				}
		}
		deleteDept(a, url);
	}
	</script>
</head>
<body>
<form name="icform" method="post">

<div id="menubar">
<div id="middleMenubar">
<div id="innerMenubar">
  <div id="navMenubar">
<ul>
<li id="view"><a href="#" onclick="toUpdate('financeAction_toview');this.blur();">查看</a></li>
<li id="new"><a href="#" onclick="formSubmit('financeListAction_tocreate','_self');this.blur();">新增</a></li>
<li id="new"><a href="#" onclick="submit(this,'financeListAction_tosubmit');">完成结算</a></li>
<li id="print"><a href="#" onclick="toUpdate('financeAction_print');">打印</a></li>
<li id="delete"><a href="#" onclick="deleteC(this,'financeListAction_delete');this.blur();">删除</a></li>
</ul>
  </div>
</div>
</div>
</div>
   
  <div class="textbox-title">
	<img src="${ctx }/skin/default/images/icon/currency_yen.png"/>
   财务单列表
  </div> 
  
<div>


<div class="eXtremeTable" >
<table id="ec_table" class="tableRegion" width="98%" >
	<thead>
	<tr>
		<td class="tableHeader"><input type="checkbox" name="selid" onclick="checkAll('id',this)"></td>
		<td class="tableHeader">序号</td>
		<td class="tableHeader">制单人</td>
		<td class="tableHeader">制单日期</td>
		<td class="tableHeader">状态</td>
	</tr>
	</thead>
	<tbody class="tableBody" >
	${links}
	<c:forEach items="${results}" var="o" varStatus="status">
	<tr class="odd" onmouseover="this.className='highlight'" onmouseout="this.className='odd'" >
		<td><input type="checkbox" name="id" value="${o.id}" state="${o.state}" /></td>
		<td>${status.count}</td>
		<td>${o.maker}</td>
		<td>${o.makeDate}</td>
		<td>
		 <c:if test="${o.state==0}">草稿</c:if>
		   <c:if test="${o.state==1}"><font color="green">已结算</font></c:if>
		</td>
	</tr>
	</c:forEach>
	</tbody>
</table>
</div>
</div>
</form>
</body>
</html>

