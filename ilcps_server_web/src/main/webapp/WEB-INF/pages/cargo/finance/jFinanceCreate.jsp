<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="../../baselist.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title></title>
	<script type="text/javascript" src="${ctx }/js/datepicker/WdatePicker.js""></script>
	<script type="text/javascript">
		//保存报运单
		function saveFinance(url){
			var ids=document.getElementsByName("invoiceId");
			var count=0;
			for (var i = 0; i < ids.length; i++) {
				if(ids[i].checked)
					{
						count++;
					}
			}
			if(count==0)
			{
				alert("请选择要上报财务的发票单！");
				return;
			}
			if(count>1)
				{
				alert("一个财务单只能选择一个发票单！请重新选择");
				return;
				} 	
			formSubmit(url,'_self')
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
<li id="save"><a href="#" onclick="saveFinance('financeListAction_insert');this.blur();">保存</a></li>
<li id="back"><a href="#" onclick="history.go(-1);">返回</a></li>
</ul>
  </div>
</div>
</div>
</div>
  <div class="textbox-title">
	<img src="${ctx }/skin/default/images/icon/currency_yen.png"/>
   新增财务单
  </div> 
  <div class="textbox-header">
	 <div class="textbox-inner-header">
 <div class="textbox-title">
 请选择要上报财务的发票单
  </div> 
  </div>
  </div>
<div>
<div class="eXtremeTable" >
<table id="ec_table" class="tableRegion" width="98%" >
	<thead>
	<tr>
		<td class="tableHeader"><input type="checkbox" name="selid" onclick="checkAll('invoiceId',this)"></td>
		<td class="tableHeader">序号</td>
		<td class="tableHeader">发票号</td>
		<td class="tableHeader">贸易条款</td>
		<td class="tableHeader">开票时间</td>
			<td class="tableHeader">开票人</td>
		<td class="tableHeader">状态</td>
	</tr>
	</thead>
	<tbody class="tableBody" >
	${links}
	<c:forEach items="${results}" var="o" varStatus="status">
	<tr class="odd" onmouseover="this.className='highlight'" onmouseout="this.className='odd'" >
		<td><input type="checkbox" name="invoiceId" value="${o.id}" state="${o.state}" /></td>
		<td>${status.count}</td>
		<td>${o.blNo}</td>
		<td>${o.priceCondition}</td>
		<td>${o.updateTime}</td>
		<td>${o.reallName}</td>
		<td>
		 <c:if test="${o.state==0}">草稿</c:if>
		   <c:if test="${o.state==1}"><font color="green">已上报</font></c:if>
		  <c:if test="${o.state==2}"><font color="green">已上报财务</font></c:if>
		    <c:if test="${o.state==3}"><font color="green">已结算</font></c:if>
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

