<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="../../baselist.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
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
							alert("当前购销合同不能修改!请重新选择!");
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
						alert("当前购销合同还未提交!");
						return
					}
						if(ids[i].getAttribute("state")!="1")
							{
								alert("当前购销合同已报运!不能取消!");
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
								alert("当前购销合同不能提交!");
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
								alert("当前购销合同不能删除!");
								return
							}
					}
			}
			deleteDept(a, url);
		}
		
		//货物
		function toGoods(url,state){
			if(state!="0")
				{
				  alert("当前购销合同已经上报,不能添加货物了!");
				  return;
				}
			window.location.href=url;
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

<li id="view"><a href="#" onclick="javascript:toView('contractAction_toview')">查看</a></li>
<li id="new"><a href="#" onclick="formSubmit('contractAction_tocreate','_self');this.blur();">新增</a></li>
<li id="update"><a href="#" onclick="javascript:Update('contractAction_toupdate')">修改</a></li>
<li id="delete"><a href="#" onclick="deleteC(this,'contractAction_delete')">删除</a></li>
<li id="new"><a href="#" onclick="submit(this,'contractAction_submit')">提交</a></li>
<li id="new"><a href="#" onclick="cancel(this,'contractAction_cancel')">取消</a></li>
<li id="print"><a href="#" onclick="javascript:toUpdate('contractAction_print')">打印</a></li>

</ul>
  </div>
</div>
</div>
</div>
   
<div class="textbox" id="centerTextbox">
  <div class="textbox-header">
  <div class="textbox-inner-header">
  <div class="textbox-title">
  <img src="${ctx }/skin/default/images/icon/currency_yen.png"/>
    购销合同列表
  </div> 
  </div>
  </div>
  
<div>


<div class="eXtremeTable" >
<table id="ec_table" class="tableRegion" width="98%" >
	<thead>
	<tr>
		<td class="tableHeader"><input type="checkbox" name="selid" onclick="checkAll('id',this)"></td>
		<td class="tableHeader">序号</td>
		<td class="tableHeader">客户名称</td>
		<td class="tableHeader">合同号</td>
	    <td class="tableHeader">货物数/附件数</td>
		<td class="tableHeader">制单人</td>
		<td class="tableHeader">审单人</td>
		<td class="tableHeader">验货员</td>
		<td class="tableHeader">签单日期</td>
		<td class="tableHeader">交货期限</td>
		<td class="tableHeader">船期</td>
		<td class="tableHeader">贸易条款</td>
		<td class="tableHeader">总金额</td>
		<td class="tableHeader">状态</td>
		<td class="tableHeader">操作</td>
	</tr>
	</thead>
	<tbody class="tableBody" >
	${links }
	<c:forEach items="${results}" var="o" varStatus="status">
	<tr class="odd" onmouseover="this.className='highlight'" onmouseout="this.className='odd'" >
		<td><input type="checkbox" name="id" value="${o.id}" state="${o.state }"/></td>
		<td>${status.index+1}</td>
		<td>${o.customName}</td>
		<td><a href="contractAction_toview?id=${o.id}">${o.contractNo}</a></td>
		 <td>
		    ${fn:length(o.contractProducts) }
		    <%-- ${o.contractProducts.size() } --%>
		    /
		    <c:set var="extNo" value="0"></c:set>
		    <c:forEach items="${o.contractProducts }"  var="cp" >
		        <c:if test="${fn:length(cp.extCproducts)!=0 }">
		            <c:set var="extNo" value="${extNo+cp.extCproducts.size() }"></c:set>
		        </c:if>
		    	
		    </c:forEach>
		    ${extNo }
		</td>
		<td>${o.inputBy}</td>
		<td>${o.checkBy}</td>
		<td>${o.inspector}</td>
		<td><fmt:formatDate value="${o.signingDate}" pattern="yyyy-MM-dd"/></td>
		<td><fmt:formatDate value="${o.deliveryPeriod}" pattern="yyyy-MM-dd"/></td>
		<td><fmt:formatDate value="${o.shipTime}" pattern="yyyy-MM-dd"/></td>
		<td>${o.tradeTerms}</td>
		<td>${o.totalAmount}</td>
		<td><c:if test="${o.state==0}">草稿</c:if>
		<c:if test="${o.state==1}"><font color="green">已上报</font></c:if>
		<c:if test="${o.state==2}"><font color="green">已报运</font></c:if>
		<c:if test="${o.state==3}"><font color="green">已装箱</font></c:if>
		<c:if test="${o.state==4}"><font color="green">已委托</font></c:if>
		<c:if test="${o.state==5}"><font color="green">已开发票</font></c:if>
		<c:if test="${o.state==6}"><font color="green">已上报财务</font></c:if>
		<c:if test="${o.state==7}"><font color="green">已结算</font></c:if>
		</td>
		<td><a onclick="toGoods('contractProductAction_tocreate?contract.id=${o.id}','${o.state }')" href="#">[货物]</a></td>
	</tr>
	</c:forEach>
	
	</tbody>
</table>
</div>
 
</div>
 
 
</form>
</body>
</html>

