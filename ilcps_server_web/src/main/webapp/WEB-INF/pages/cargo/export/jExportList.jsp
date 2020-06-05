<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="../../baselist.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title></title>
	<script type="text/javascript">
	//更新
	function Update() {		
	var ids=document.getElementsByName("id");
	for (var i = 0; i < ids.length; i++) {
		if(ids[i].checked)
			{
				if(ids[i].getAttribute("state")!="0")
					{
						alert("当前报运单不能修改!请重新选择!");
						return
					}
			}
	}
	toUpdate('exportAction_toupdate');
	};
	//取消
	function cancel(a) {		
		var ids=document.getElementsByName("id");
		for (var i = 0; i < ids.length; i++) {
			if(ids[i].checked)
				{
				if(ids[i].getAttribute("state")=="0")
				{
					alert("当前报运单还未提交!");
					return
				}
					if(ids[i].getAttribute("state")!="1")
						{
							alert("当前报运单已装箱!不能取消!");
							return
						}
				}
		}
		cancelDept(a,'exportAction_cancel');
		};
	//提交
	function submit(a){
		var ids=document.getElementsByName("id");
		for (var i = 0; i < ids.length; i++) {
			if(ids[i].checked)
				{
					if(ids[i].getAttribute("state")!="0")
						{
							alert("当前报运单不能提交!");
							return
						}
				}
		}
		submitDept(a,'exportAction_submit');
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
							alert("当前报运单不能删除!");
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
<li id="view"><a href="#" onclick="toView('exportAction_toview');">查看</a></li>
<li id="update"><a href="#" onclick="Update()">修改</a></li>
<li id="delete"><a href="#" onclick="deleteC(this,'exportAction_delete');">删除</a></li>
<li id="print"><a href="#" onclick="toUpdate('exportAction_printExport')">打印</a></li>
<li id="new"><a href="#" onclick="submit('exportAction_submit')">提交</a></li>
<li id="delete"><a href="#" onclick="cancel(this)">取消</a></li>
<li id="work_assign"><a href="#" onclick="toUpdate('exportAction_exportE')">电子报运</a></li>
</ul>
  </div>
</div>
</div>
</div>
   
  <div class="textbox-title">
	<img src="${ctx }/skin/default/images/icon/currency_yen.png"/>
    出口报运列表
  </div> 
  
<div>


<div class="eXtremeTable" >
<table id="ec_table" class="tableRegion" width="98%" >
	<thead>
	<tr>
		<td class="tableHeader"><input type="checkbox" name="selid" onclick="checkAll('id',this)"></td>
		<td class="tableHeader">序号</td>
		<td class="tableHeader">报运号</td>
		<td class="tableHeader">货物数/附件数</td>
		<td class="tableHeader">信用证号</td>
		<td class="tableHeader">收货人及地址</td>
		<td class="tableHeader">装运港</td>
		<td class="tableHeader">目的港</td>
		<td class="tableHeader">运输方式</td>
		<td class="tableHeader">价格条件</td>
		<td class="tableHeader">制单日期</td>
		<td class="tableHeader">状态</td>
	</tr>
	</thead>
	<tbody class="tableBody" >
${links}
	
	<c:forEach items="${results}" var="o" varStatus="status">
	<tr class="odd" onmouseover="this.className='highlight'" onmouseout="this.className='odd'" >
		<td><input type="checkbox" name="id" value="${o.id}" state="${o.state }"/></td>
		<td>${status.index+1}</td>
		<td>${o.id}</td>
		<td align="center">
			${fn:length(o.exportProducts)}
			/
			<c:set var="extNumber" value="0"></c:set><!-- 设置一个变量，用来累加，初始值0 -->
			<c:forEach items="${o.exportProducts}" var="ep">
			   <c:if test="${fn:length(ep.extEproducts)!=0 }">
					<c:set var="extNumber" value="${extNumber +fn:length(ep.extEproducts)}"/>
				</c:if>
			</c:forEach>
			${extNumber}
		</td>		
		<td>${o.lcno}</td>
		<td>${o.consignee}</td>
		<td>${o.shipmentPort}</td>
		<td>${o.destinationPort}</td>
		<td>${o.transportMode}</td>
		<td>${o.priceCondition}</td>
		<td><fmt:formatDate value="${o.inputDate }" pattern="yyyy-MM-dd"/></td>
		<td>
		   <c:if test="${o.state==0}">草稿</c:if>
		   <c:if test="${o.state==1}"><font color="green">已上报</font></c:if>
		   <c:if test="${o.state==2}"><font color="green">已装箱</font></c:if>
		   <c:if test="${o.state==3}"><font color="green">已委托</font></c:if>
		   <c:if test="${o.state==4}"><font color="green">已开发票</font></c:if>
		   <c:if test="${o.state==5}"><font color="green">已上报财务</font></c:if>
		   <c:if test="${o.state==6}"><font color="green">已结算</font></c:if>
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

