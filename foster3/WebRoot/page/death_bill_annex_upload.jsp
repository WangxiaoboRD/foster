<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<html>
<head>
	<meta http-equiv="content-type" content="text/html; charset=utf-8" />
	<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
	<meta content='width=330, height=400, initial-scale=1' name='viewport' />
	<link rel="icon" href="favicon.ico" type="../image/x-icon" />
	<title>${initParam.title}</title>
	<style type="text/css">
		
		#upBox{
			text-align: center;
			width:530px;
			margin: auto;
			margin-top: 0px;
			margin-bottom: 0px;
		}
		#inputBox{
			width: 100%;
			height: 25px;
			border: 1px solid #DADEE4;
			color: rgba(21, 90, 214, 0.58);
    		border-radius: 8px;
			position: relative;
			text-align: center;
			line-height: 20px;
			overflow: hidden;
			font-size: 13px;
		}
		#inputBox input{
			width: 114%;
			height: 40px;
			opacity: 0;
			cursor: pointer;
			position: absolute;
			top: 0;
			left: -14%;
			
		}
		#imgBox{
			text-align: left;
		}
		.imgContainer{
			display: inline-block;
			width: 24%;
			height: 115px;
			margin-left: 1%;
			position: relative;
			margin-top: 4px;
		}
		.imgContainer img{
			width: 100%;
			height: 115px;
			cursor: pointer;
		}
		.imgContainer p{
			position: absolute;
			bottom: -1px;
			left: 0;
			width: 100%;
			height: 30px;
			background: black;
			text-align: center;
			line-height: 30px;
			color: white;
			font-size: 16px;
			font-weight: bold;
			cursor: pointer;
			display: none;
		}
		.imgContainer:hover p{
			display: block;
		}
		#btn{
			outline: none;
			width: 100px;
			height: 30px;
			background: cornflowerblue;
			border: 1px solid cornflowerblue;
			color: white;
			cursor: pointer;
			margin-top: 30px;
			border-radius: 5px;
		}
	</style>
	<link href="../ligerUI/ligerUI/skins/Aqua/css/ligerui-all.css" rel="stylesheet" type="text/css" />
	<link href="../ligerUI/ligerUI/skins/ligerui-icons.css" rel="stylesheet" type="text/css" />
    <script src="../original/upImg/jquery-1.7.2.min.js" type="text/javascript" charset="utf-8"></script>  
    <script src="../ligerUI/jquery-validation/jquery.form.js" type="text/javascript"></script> 
	<script src="../ligerUI/jquery-validation/jquery.validate.js" type="text/javascript"></script> 
    <script src="../ligerUI/jquery-validation/jquery.metadata.js" type="text/javascript"></script>
    <script src="../ligerUI/jquery-validation/messages_cn.js" type="text/javascript"></script>  
    <script src="../ligerUI/ligerUI/js/ligerui.all.js" type="text/javascript"></script>       
	<script src="../original/upImg/uploadImg.js" type="text/javascript" charset="utf-8"></script>
	  <script type="text/javascript">
		var v;
        var form = null;
        $(function(){
        	 var groupicon = "../ligerUI/ligerUI/skins/icons/communication.gif";
        	form=$("form").ligerForm({
	  			 labelWidth: 65, 
           		 space:40,
           		 fileUpload: true,
	  			 fields: [
	  			      { display: "死亡单号", name: "id", type: "hidden"},
	  			      { display: "批次id",name: "batch.id", type: "hidden"}, 
	  			      { display: "代养农户",width:150, name: "farmer", type: "text",group: "单据信息", groupicon: groupicon,options: {readonly: true}},
	  			      { display: "批&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;次",width:150,name: "batch.batchNumber", type: "text",options: {readonly: true}} 
	  			      
	               ]
	          });
        });
       
    </script>
</head>
<body>
<form></form>
<div style="width: 98%;position: relative;">
	<div id="upBox">
	 	<div id="inputBox"><input type="file" title="请选择图片" id="doc" multiple accept="image/png,image/jpg,image/gif,image/JPEG"/>点击选择图片</div>
     	<div id="imgBox"> </div>
	</div>
</div>
   <script type="text/javascript">
    var formData = imgUpload({
		inputId:'doc', //input框id
		imgBox:'imgBox', //图片容器id
		buttonId:'btn', //提交按钮id
		//upUrl:'death_bill!upload',  //提交地址
		data:'files', //参数名
		fileNum:10, //图片数量
		fileSize:2000 //图片大小(K = 1024*1024)
		//imgType:'jpeg,png,jpg,gif,bmp'
		//isRepeat:true //是否允许重复
	 });
    // 上传
 	function upload(dialog, grid){
 		var _id = $("input[id='id']").val();
 		formData.append("id", _id);
		$.ajax({
			type: "post",
			url: 'death_bill!upload',
			async: true,
			data: formData,
			dataType:'text',
			//traditional: true,
			//关闭序列化-
			processData: false,
		    contentType: false,
			success: function(data) {
				if (data != '') {
	                if(data == '1'){
	             	   tip = $.ligerDialog.tip({ title: '提示信息', content: '图片上传成功！' });
							window.setTimeout(function(){ 
								tip.close()
							    dialog.close();
							    grid.loadData(true);
							} ,2000);
	                }else if(data == '0'){
	             	   $.ligerDialog.error('图片上传失败');
	                }
	            }
			}
		});
	}  
	 
    </script>  
    
</body>
</html>

