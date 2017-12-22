<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta content='width=330, height=400, initial-scale=1' name='viewport' />
<link rel="shortcut icon" href="image/logo.ico" type="image/x-icon"/> 
<title>${initParam.title}</title>	
<link href="ligerUI/ligerUI/skins/Aqua/css/ligerui-dialog.css" rel="stylesheet" type="text/css" />
<link href="ligerUI/ligerUI/skins/ligerui-icons.css" rel="stylesheet" type="text/css" />	
<script src="ligerUI/jquery/jquery-1.6.4.js" type="text/javascript"></script>
<script src="ligerUI/jquery-validation/jquery.form.js" type="text/javascript"></script> 
<script src="ligerUI/jquery-validation/jquery.validate.js" type="text/javascript"></script> 
<script src="ligerUI/jquery-validation/jquery.metadata.js" type="text/javascript"></script>
<script src="ligerUI/jquery-validation/messages_cn.js" type="text/javascript"></script> 
<script type="text/javascript" src="ligerUI/ligerUI/js/ligerui.all.js"></script> 
<style type="text/css">
body {
	margin:0;
	padding:0;
	background:url(image/bg.jpg) no-repeat center top #EBFFFF;
	font-size:12px;
	font:12px/1.5 Tahoma,Arial,"宋体",SimSun,sans-serif;
}
.content{
	position:absolute;
	left:40px;
	top:296px;
	font-family:microsoft yahei;
}
.content th{ font-weight:normal;}
.content td {
	height:20px;
	line-height:20px;
	padding:3px 0;
}
.content input {
	border: 1px solid #079EDD;
	box-shadow: 1px 1px 3px #ededed inset;
	width: 150px;
}
.content .btn {
	background:url(image/bt.jpg) no-repeat 0 0 ;
	border:none;
	box-shadow:none;
	width: 62px;
	height: 22px;
	cursor:pointer;
	display:block;
	margin-top:3px;
	
}
.content .btn:hover{ background-position:-62px 0;}
</style>

<script type="text/javascript">

	if(top.window !== window){
	    top.location = window.location;
	}
	$(function(){
		$("#submitB").bind("click",function(){
			if (!login()) {
				return false;
			}
			$.ajax({
				type:"POST",
				async: false,
				url:"login!login",
				data:$("#form1").formSerialize(),
				dataType:"text",
				beforeSend:function(){
				   $.ligerDialog.waitting('正在登录,请稍候...');
				},
				success:function(result, textStatus){
					if(result == 'LOGINOK' )
			        	window.location.href= "page/index.jsp";
			        	//window.location.href= "page/feed_bill_list1.jsp";
			        else if(result=='CAPTCHAERROR')
			        	$.ligerDialog.error('验证码输入错误');
			        else if(result=='LOGINSTOP')
			        	$.ligerDialog.error('用户已经停用');
			        else	
			          $.ligerDialog.error('账号或密码出现错误');
				},
				error: function(XMLHttpRequest,textStatus){
					 $.ligerDialog.error('操作出现异常');
				},
				complete:function(){
				   setTimeout(function() {
						$.ligerDialog.closeWaitting();
				   }, 1000);
					//获取当前的时间作为参数，无具体意义
	    			var timenow = new Date().getTime();
	    			$("#image").attr("src","patchca.png?d="+timenow);
	    		   
	    		}
			});
		});
		$("#patchca").keydown( function(event){
			  if(event.keyCode == 13) {
			       $("#submitB").trigger("click");
			       return false; //阻止浏览器的默认行为
			  }
	
	    });
	});
	// 登录方法
	function login() {
		var flag = true;
		var account = document.getElementById("userCode").value.trim();
		var pwd = document.getElementById("userPassword").value.trim();
		var patchca = document.getElementById("patchca").value.trim();
		with (document.getElementById("RequiredFieldValidator3").style) {
			if (account == "") {
				visibility = "visible";
				flag = false;
			} else 
				visibility = "hidden";
		}
	
		with (document.getElementById("RequiredFieldValidator4").style) {
			if (pwd == "") {
				visibility = "visible";
				flag = false;
			} else 
				visibility = "hidden";
		}

		with (document.getElementById("RequiredFieldValidator5").style) {
			if (patchca == "") {
				visibility = "visible";
				flag = false;
			} else 
				visibility = "hidden";
		}
	
		return flag;
	}
	
</script>
</head>
<body>
<div  style="position:relative; width:760px; height:600px; margin:auto;">
  <div class="content">
    <form id="form1" name="form1" method="post">
      <table  cellspacing="0" cellpadding="0" border="0">
        <tbody>
          <tr>
	        <th width="55" align="right">用户名：</th>
	        <td><input id="userCode" name="e.userCode" style="width:150px;height:17px"  value="admin" type="text" /><span id="RequiredFieldValidator3" style="VISIBILITY: hidden; COLOR: red">请输入登录名</span></td>
	      </tr>
          <tr>
	        <th width="55" align="right">密码：</th>
	        <td><input id="userPassword" style="width:150px;height:17px" type=password name="e.userPassword" value="cp"/><span id="RequiredFieldValidator4" style="VISIBILITY: hidden; COLOR: red">请输入密码</span></td>
	      </tr>
         
          <tr>
	        <th width="55" align="right">验证码：</th>
	        <td><input id="patchca" type="text"  style=" width:88px;height:17px" name="patchca"/> <img id="image" src="patchca.png" width="55" height="20" align="absbottom" style="margin-left:2px;" alt="验证码" onclick="this.src=this.src+'?'+Math.random();"/><span id="RequiredFieldValidator5" style="VISIBILITY: hidden; COLOR: red">请输入验证码</span></td>
	      </tr>
          <tr>
            <th width="55" align="right">&nbsp;</th>
            <td><button id="submitB" type="button" class="btn" ></button></td>
          </tr>
        </tbody>
      </table>
    </form>
  </div>
</div>
</body>
</html>