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
						alert("当前委托单不能修改!请重新选择!");
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
					alert("当前委托单还未提交!");
					return
				}
					if(ids[i].getAttribute("state")!="1")
						{
							alert("当前委托单已开发票!不能取消!");
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
							alert("当前委托单已提交!");
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
							alert("当前委托单不能删除!");
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
<li id="view"><a href="#" onclick="toUpdate('delegateAction_toview');this.blur();">查看</a></li>
<li id="new"><a href="#" onclick="formSubmit('delegateListAction_tocreate','_self');this.blur();">新增</a></li>
<li id="update"><a href="#" onclick="Update('delegateListAction_toupdate');">修改</a></li>
<li id="new"><a href="#" onclick="submit(this,'delegateListAction_tosubmit');">提交</a></li>
<li id="print"><a href="#" onclick="toUpdate('delegateAction_print');">打印</a></li>
<li id="delete"><a href="#" onclick="cancel(this,'delegateAction_cancel');">取消</a></li>
<li id="delete"><a href="#" onclick="deleteC(this,'delegateListAction_delete');this.blur();">删除</a></li>
</ul>
  </div>
</div>
</div>
</div>
   
  <div class="textbox-title">
	<img src="${ctx }/skin/default/images/icon/currency_yen.png"/>
   委托单列表
  </div> 
  
<div>


<div class="eXtremeTable" >
<table id="ec_table" class="tableRegion" width="98%" >
	<thead>
	<tr>
		<td class="tableHeader"><input type="checkbox" name="selid" onclick="checkAll('id',this)"></td>
		<td class="tableHeader">序号</td>
		<td class="tableHeader">货主</td>
		<td class="tableHeader">抬头人</td>
		<td class="tableHeader">正本通知人</td>
		<td class="tableHeader">信用证</td>
		<td class="tableHeader">运输方式</td>
		<td class="tableHeader">装运港</td>
		<td class="tableHeader">转船港</td>
		<td class="tableHeader">卸货港</td>
		<td class="tableHeader">装期</td>
		<td class="tableHeader">效期</td>
		<td class="tableHeader">是否分批</td>
		<td class="tableHeader">是否转船</td>
		<td class="tableHeader">份数</td>
		<td class="tableHeader">复核人</td>
		<td class="tableHeader">状态</td>
	</tr>
	</thead>
	<tbody class="tableBody" >
${links}
	
	<c:forEach items="${results}" var="o" varStatus="status">
	<tr class="odd" onmouseover="this.className='highlight'" onmouseout="this.className='odd'" >
		<td><input type="checkbox" name="id" value="${o.id}" state="${o.state}" /></td>
		<td>${status.count}</td>
		<td>${o.shipper}</td>
		<td>${o.consihnee}</td>
		<td>${o.originalNotifyParty}</td>
		<td>${o.lcNo}</td>
		<td>${o.transportMode}</td>
		<td>${o.portLoading}</td>
		<td>${o.portTrans}</td>
		<td>${o.portDischange}</td>
		<td>
		     <fmt:formatDate value="${o.installPeriod}" pattern="yyyy-MM-dd"/>
		</td>
		<td>
		     <fmt:formatDate value="${o.effectPeriod}" pattern="yyyy-MM-dd"/>
		</td>
		<td>${o.isBatches==1?'是':'否'}</td>
		<td>${o.isTransshipment==1?'是':'否'}</td>
		<td>${o.copies}</td>
		<td>${o.reviewer}</td>
		<td>
		 <c:if test="${o.state==0}">草稿</c:if>
		   <c:if test="${o.state==1}"><font color="green">已上报</font></c:if>
		  <c:if test="${o.state==2}"><font color="green">已开发票</font></c:if>
		  <c:if test="${o.state==3}"><font color="green">已上报财务</font></c:if>
		    <c:if test="${o.state==4}"><font color="green">已结算</font></c:if>
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

