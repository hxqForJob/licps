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
		function saveInvoice(url){
			var ids=document.getElementsByName("delegateId");
			var count=0;
			for (var i = 0; i < ids.length; i++) {
				if(ids[i].checked)
					{
						count++;
					}
			}
			if(count==0)
			{
				alert("请选择要开发票的委托单！");
				return;
			}
			if(count>1)
				{
				alert("一个发票单只能选择一个委托单！请重新选择");
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
<li id="save"><a href="#" onclick="saveInvoice('invoiceListAction_insert');this.blur();">保存</a></li>
<li id="back"><a href="#" onclick="history.go(-1);">返回</a></li>
</ul>
  </div>
</div>
</div>
</div>
  <div class="textbox-title">
	<img src="${ctx }/skin/default/images/icon/currency_yen.png"/>
   新增发票单
  </div> 
    <div>
		<table class="commonTable" cellspacing="1">
	       
	        <tr>
	            <td class="columnTitle">发票号：</td>
	            <td class="tableContent"><input type="text" name="blNo" value=""/></td>
	     
	            <td class="columnTitle">提货单号：</td>
	            <td class="tableContent"><input type="text" name="scNo" value=""/></td>
	        </tr>	
	        <tr>
	      
	            <td class="columnTitle">贸易条款：</td>
	            <td class="tableContentAuto">
	            	<input type="radio" name="priceCondition" value="FAS"/>FAS
	            	<input type="radio" name="priceCondition" value="FOB"/>FOB
	            </td>
	        </tr>
		</table>
	</div>
  <div class="textbox-header">
	 <div class="textbox-inner-header">
 <div class="textbox-title">
   委托单列表
  </div> 
  </div>
  </div>
<div>
<div class="eXtremeTable" >
<table id="ec_table" class="tableRegion" width="98%" >
	<thead>
	<tr>
		<td class="tableHeader"><input type="checkbox" name="selid" onclick="checkAll('delegateId',this)"></td>
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
		<td><input type="checkbox" name="delegateId" value="${o.id}" state="${o.state}" /></td>
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

