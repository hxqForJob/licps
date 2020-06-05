<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="../../baselist.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title></title>
	<script type="text/javascript">
	//更新
	function Update(url) {		
	var ids=document.getElementsByName("id");
	for (var i = 0; i < ids.length; i++) {
		if(ids[i].checked)
			{
				if(ids[i].getAttribute("state")!="0")
					{
						alert("当前装箱单不能修改!请重新选择!");
						return
					}
			}
	}
	toUpdate(url);
	};
	//取消
	function cancel(a,url) {		
		var ids=document.getElementsByName("id");
		for (var i = 0; i < ids.length; i++) {
			if(ids[i].checked)
				{
				if(ids[i].getAttribute("state")=="0")
				{
					alert("当前装箱单还未提交!");
					return
				}
					if(ids[i].getAttribute("state")!="1")
						{
							alert("当前装箱单已委托!不能取消!");
							return
						}
				}
		}
		cancelDept(a,url);
		};
	//提交
	function submit(a,url){
		var ids=document.getElementsByName("id");
		for (var i = 0; i < ids.length; i++) {
			if(ids[i].checked)
				{
				
					if(ids[i].getAttribute("state")!="0")
						{
							alert("当前装箱单不能提交!");
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
							alert("当前装箱单不能删除!");
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
<li id="view"><a href="#" onclick="toUpdate('packageAction_toview');this.blur();">查看</a></li>
<li id="new"><a href="#" onclick="formSubmit('packingListAction_tocreate','_self');this.blur();">新增</a></li>
<li id="update"><a href="#" onclick="Update('packingListAction_toupdate');">修改</a></li>
<li id="new"><a href="#" onclick="submit(this,'packingListAction_tosubmit');">提交</a></li>
<li id="print"><a href="#" onclick="toUpdate('packageAction_print');">打印</a></li>
<li id="delete"><a href="#" onclick="cancel(this,'packageAction_cancel');">取消</a></li>
<li id="delete"><a href="#" onclick="deleteC(this,'packingListAction_delete');this.blur();">删除</a></li>
</ul>
  </div>
</div>
</div>
</div>
   
  <div class="textbox-title">
	<img src="${ctx }/skin/default/images/icon/currency_yen.png"/>
    装箱单列表
  </div> 
  
<div>


<div class="eXtremeTable" >
<table id="ec_table" class="tableRegion" width="98%" >
	<thead>
	<tr>
		<td class="tableHeader"><input type="checkbox" name="selid" onclick="checkAll('id',this)"></td>
		<td class="tableHeader">序号</td>
		<td class="tableHeader">卖方</td>
		<td class="tableHeader">买方</td>
		<td class="tableHeader">发票号</td>
		<td class="tableHeader">发票日期</td>
		<td class="tableHeader">状态</td>
	</tr>
	</thead>
	<tbody class="tableBody" >
${links}
	
	<c:forEach items="${results}" var="o" varStatus="status">
	<tr class="odd" onmouseover="this.className='highlight'" onmouseout="this.className='odd'" >
		<td><input type="checkbox" name="id" value="${o.id}" state="${o.state}" /></td>
		<td>${status.count}</td>
	
		<td>${o.seller}</td>
		<td>${o.buyer}</td>
		<td>${o.invoiceNo}</td>
		<td>
		     <fmt:formatDate value="${o.invoiceDate}" pattern="yyyy-MM-dd"/>
		</td>
		
		<td>
		 <c:if test="${o.state==0}">草稿</c:if>
		   <c:if test="${o.state==1}"><font color="green">已上报</font></c:if>
		  <c:if test="${o.state==2}"><font color="green">已委托</font></c:if>
		  <c:if test="${o.state==3}"><font color="green">已开发票</font></c:if>
		  <c:if test="${o.state==4}"><font color="green">已上报财务</font></c:if>
		    <c:if test="${o.state==5}"><font color="green">已结算</font></c:if>
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

