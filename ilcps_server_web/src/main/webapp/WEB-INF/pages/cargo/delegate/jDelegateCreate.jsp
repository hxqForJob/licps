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
		function saveDelegate(url){
			var ids=document.getElementsByName("packageId");
			var count=0;
			for (var i = 0; i < ids.length; i++) {
				if(ids[i].checked)
					{
						count++;
					}
			}
			if(count==0)
			{
				alert("请选择要委托的装箱单！");
				return;
			}
			if(count>1)
				{
				alert("一个委托单只能选择一个装箱单！请重新选择");
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
<li id="save"><a href="#" onclick="saveDelegate('delegateListAction_insert');this.blur();">保存</a></li>
<li id="back"><a href="#" onclick="history.go(-1);">返回</a></li>
</ul>
  </div>
</div>
</div>
</div>
  <div class="textbox-title">
	<img src="${ctx }/skin/default/images/icon/currency_yen.png"/>
   新增委托单
  </div> 
    <div>
		<table class="commonTable" cellspacing="1">
	       
	        <tr>
	            <td class="columnTitle">货主：</td>
	            <td class="tableContent"><input type="text" name="shipper" value=""/></td>
	     
	            <td class="columnTitle">提单抬头：</td>
	            <td class="tableContent"><input type="text" name="consihnee" value=""/></td>
	        </tr>	
	        <tr>
	            <td class="columnTitle">正本通知人：</td>
	            <td class="tableContent"><input type="text" name="originalNotifyParty" value=""/></td>
	      
	            <td class="columnTitle">信用证：</td>
	            <td class="tableContentAuto">
	            	<input type="radio" name="lcNo" value="L/C"/>L/C
	            	<input type="radio" name="lcNo" value="T/T"/>T/T
	            </td>
	        </tr>
	         <tr>
	            <td class="columnTitle">装运港：</td>
	            <td class="tableContent"><input type="text" name="portLoading" value=""/></td>
	      
	            <td class="columnTitle">转船港：</td>
	            <td class="tableContent"><input type="text" name="portTrans" value=""/></td>
	        </tr>	
	      	<tr>
	            <td class="columnTitle">卸货港：</td>
	            <td class="tableContent"><input type="text" name="portDischange" value=""/></td>
	      
	            <td class="columnTitle">运输方式：</td>
	            <td class="tableContentAuto">
	            	<input type="radio" name="transportMode" value="SEA"/>SEA
	            	<input type="radio" name="transportMode" value="AIR"/>AIR
	            </td>
	        </tr>
	        <tr>
	            <td class="columnTitle">装期：</td>
	            <td class="tableContent">
	            	<input type="text" style="width:90px;" name="installPeriod"
	            	 value=""
	             	onclick="WdatePicker({el:this,isShowOthers:true,dateFmt:'yyyy-MM-dd'});"/>
	            </td>
	      
	            <td class="columnTitle">效期：</td>
	            <td class="tableContent">
	            	<input type="text" style="width:90px;" name="effectPeriod"
	            	 value=""
	             	onclick="WdatePicker({el:this,isShowOthers:true,dateFmt:'yyyy-MM-dd'});"/>
	            </td>
	        </tr>
	         <tr>
	            <td class="columnTitle">是否分批：</td>
	            <td class="tableContentAuto">
	            	<input type="radio" name="isBatches" value="1"/>是
	            	<input type="radio" name="isBatches" value="0"/>否
	            </td>
	      
	            <td class="columnTitle">是否转船：</td>
	            <td class="tableContentAuto">
	            	<input type="radio" name="isTransshipment" value="1"/>是
	            	<input type="radio" name="isTransshipment" value="0"/>否
	            </td>
	        </tr>
	         <tr>
	            <td class="columnTitle">份数：</td>
	            <td class="tableContent"><input type="number" name="copies" value=""/></td>
	      
	            <td class="columnTitle">复核人：</td>
	            <td class="tableContent"><input type="text" name="reviewer" value=""/></td>
	        </tr>
	         <tr>
	            <td class="columnTitle">扼要说明：</td>
	            <td class="tableContent">
	            		<textarea rows="15" name="marksAndNos" cols="75"></textarea>
	            </td>
	            <td class="columnTitle">运输要求：</td>
	            <td class="tableContent">
	            		<textarea rows="15" name="specialCondition" cols="75"></textarea>
	            </td>
	        </tr>
	         <tr>
	            <td class="columnTitle">运费说明：</td>
	            <td class="tableContent">
	            		<textarea rows="15" name="description" cols="75"></textarea>
	            </td>
	        </tr>											
		</table>
	</div>
  <div class="textbox-header">
	 <div class="textbox-inner-header">
 <div class="textbox-title">
    装箱单列表
  </div> 
  </div>
  </div>
<div>
<div class="eXtremeTable" >
<table id="ec_table" class="tableRegion" width="98%" >
	<thead>
	<tr>
		<td class="tableHeader"><input type="checkbox" name="selid" onclick="checkAll('packageId',this)"></td>
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
		<td><input type="checkbox" name=packageId value="${o.id}" state="${o.state }"/></td>
		<td>${status.index+1}</td>
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

