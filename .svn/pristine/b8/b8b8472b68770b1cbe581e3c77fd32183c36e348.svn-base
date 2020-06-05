<%@ page language="java" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title></title>
		<meta charset="utf-8" />
		<title>后台首页</title>
		<link rel="icon" href="${pageContext.request.contextPath }/img/favicon.ico">
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/css/public.css">
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/css/manage-login.css">
		<script type="text/javascript">
		   if(self.location!=top.location){
			   top.location = self.location;
		   }
		</script>
	</head>
	<body>
		<div class="wrap">
				
			<div class="subwrap">
				<div class="login-box">
					<h4>国际物流云商系统</h4>
					<div style="color: red;font-size:16px;font-weight: bold;text-align: center ">
					<c:if test="${!empty errorInfo}">
						${errorInfo}
					</c:if>
					</div>
					<form id="login_main" method="post" target="_top">
						<div class="form-item">
						   <div class="input-icon">
						   	<img src="${pageContext.request.contextPath }/img/icon/line.png" width="18"/>
						   </div>
						   <input type="text" value="${userName}" name="username" id="userName" placeholder="请输入用户名" onfocus="this.placeholder=''" onblur="this.placeholder='请输入用户名'">
						</div>
						<div class="form-item">
							<div  class="input-icon">
								<img src="${pageContext.request.contextPath }/img/icon/lock.png"  width="18"/>
							</div>
							<input type="password" value="${password}" name="password" id="password" placeholder="请输入密码"  onfocus="this.placeholder=''" onblur="this.placeholder='请输入密码'" 
								onKeyDown="javascript:if(event.keyCode==13){ $('#login_main').submit() }" >
						</div>
						<div class="btns">
							<button id="loginBtn" class="btn btn-login">登&nbsp;&nbsp;录</button>
							<button id="resetBtn" class="btn btn-reset">重&nbsp;&nbsp;置</button>
						</div>
					</form>
				</div>
				
			</div>
		</div>
		
		<script type="text/javascript" src="${pageContext.request.contextPath }/plugins/jquery.min.js" ></script>
		<script>
			$(window).resize(function(){
				$(".login-box").css({
					position:"absolute",
					left:($(window).width() - $(".login-box").outerWidth())/2,
					top:($(window).height() - $(".login-box").outerHeight())/3
				});
			})
			$(function(){
				$(window).resize();
				$("#loginBtn").click(function(){
				   $("#login_main").attr("action","loginAction_login");
                   $("#login_main").submit(function(){
 						return true;
                   });
				});
				$("#resetBtn").bind("click",function(){
					$("#userName").val("");
					$("#password").val("");
					return false;
				});
			})
		</script>
	</body>
</html>
