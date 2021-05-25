<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
	String basePath=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath()+"/";
%>
<!DOCTYPE html>
<html>
<head>
	<base href="<%=basePath%>">
	<meta charset="UTF-8">
	<link href="jquery/bootstrap_3.3.0/css/bootstrap.min.css" type="text/css" rel="stylesheet" />
	<script type="text/javascript" src="jquery/jquery-1.11.1-min.js"></script>
	<script type="text/javascript" src="jquery/bootstrap_3.3.0/js/bootstrap.min.js"></script>
	<script>
		$(function () {
			// 进入页面用户名输入框自动获得焦点
			$("#uname").focus();
			// 进入页面将用户名输入框的缓存清除
			$("#uname").val("");
			//给登录按钮绑定事件
			$("#loginBtn").click(function () {
				//执行登录代码结构
				// alert("登录");
				login();
			})
			//给窗口绑定键盘敲击事件捕捉enter键的敲击
			$(window).keydown(function (event) {
				// alert(event.keyCode);
				if (13==event.keyCode){
					//执行登录代码结构
					login();
				}
			})

		})

		function login() {
			// 首先通过前端验证用户名密码是否为空
			var uname=$.trim($("#uname").val());			//trim()方法用于剪去用户输入的前后空格
			var upass=$.trim($("#upass").val());
			if (uname=="" || upass==""){
				$("#failMsg").html("用户名和密码均不能为空！");
				return false;		//只要这句执行，则程序返回，下面就都不会继续了
			}
			//程序能够执行到这说明用户名与密码均不为空，可通过后端验证用户名与密码是否匹配及其他登录限制
			//发起ajax请求
			$.ajax({
				url:"settings/user/login.do",
				data:{"uname":uname,"upass":upass},
				type:"post",
				dataType:"json",
				async:true,
				success:function (data) {
					// data:{"ifSuccess":"value1","failMsg":"value2"}
					if (data.ifSuccess=="0"){
						//登录失败,显示登录失败的提示信息
						$("#failMsg").html(data.failMsg);
					}else{
						//登录成功，跳转系统主页面
						// alert("登录成功");
						window.location.href="workbench/index.jsp";
					}
				}
			})
		}

	</script>
</head>
<body>
	<div style="position: absolute; top: 0px; left: 0px; width: 60%;">
		<img src="image/IMG_7114.JPG" style="width: 100%; height: 90%; position: relative; top: 50px;">
	</div>
	<div id="top" style="height: 50px; background-color: #3C3C3C; width: 100%;">
		<div style="position: absolute; top: 5px; left: 0px; font-size: 30px; font-weight: 400; color: white; font-family: 'times new roman'">CRM &nbsp;<span style="font-size: 12px;">&copy;2017&nbsp;动力节点</span></div>
	</div>
	
	<div style="position: absolute; top: 120px; right: 100px;width:450px;height:400px;border:1px solid #D5D5D5">
		<div style="position: absolute; top: 0px; right: 60px;">
			<div class="page-header">
				<h1>登录</h1>
			</div>
			<form action="workbench/index.jsp" class="form-horizontal" role="form">
				<div class="form-group form-group-lg">
					<div style="width: 350px;">
						<input class="form-control" type="text" placeholder="用户名" id="uname">
					</div>
					<div style="width: 350px; position: relative;top: 20px;">
						<input class="form-control" type="password" placeholder="密码" id="upass">
					</div>
					<div class="checkbox"  style="position: relative;top: 30px; left: 10px;">
						
							<span style="color: red" id="failMsg"></span>
						
					</div>
					<button type="button" class="btn btn-primary btn-lg btn-block"  style="width: 350px; position: relative;top: 45px;" id="loginBtn">登录</button>
				</div>
			</form>
		</div>
	</div>
</body>
</html>