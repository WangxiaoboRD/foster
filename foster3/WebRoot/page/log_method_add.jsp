<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>添加页面</title>
    <link href="../ligerUI/ligerUI/skins/Aqua/css/ligerui-all.css" rel="stylesheet" type="text/css" />
	<link href="../ligerUI/ligerUI/skins/ligerui-icons.css" rel="stylesheet" type="text/css" />	
    <script src="../ligerUI/jquery/jquery-1.6.4.js" type="text/javascript"></script>
	<script src="../ligerUI/jquery-validation/jquery.form.js" type="text/javascript"></script> 
	<script src="../ligerUI/jquery-validation/jquery.validate.js" type="text/javascript"></script> 
    <script src="../ligerUI/jquery-validation/jquery.metadata.js" type="text/javascript"></script>
    <script src="../ligerUI/jquery-validation/messages_cn.js" type="text/javascript"></script>    
    <script src="../ligerUI/ligerUI/js/ligerui.all.js" type="text/javascript"></script>       
    <script type="text/javascript">
	  var form,v;
	  var groupicon = "../ligerUI/ligerUI/skins/icons/communication.gif";
	  $(function(){
	  	form = $("form").ligerForm({
	  		 inputWidth: 160, labelWidth: 70, space: 20,
             fields: [
                { display: "名称", name: "e.name", validate:{ required: true }, newline: true, type: "text" , group: "日志方法", groupicon: groupicon},
            	//{ display: '类名称', name: 'e.className', validate:{ required: true }, newline: true, type: "text" },
				{ display: '方法名称', name: 'e.methodName', validate:{ required: true }, newline: true, type: "text" },
				{ display: '所属类', name: 'e.logClass.id', type:"select", validate: { required: true }, newline: true,
					   options:{
					     selectBoxHeight:180,
	                	 url:'log_class!loadType',
	                	 keySupport:true,
		        		 valueField: 'id', 
						 textField: 'name'
				}},
				 { display: '日志类型', name: 'e.logType.id', type:"select", validate: { required: true }, newline: true,
					   options:{
					     selectBoxHeight:150,
	                	 url:'log_type!loadType',
	                	 keySupport:true,
		        		 valueField: 'id', 
						 textField: 'name'
				}}
                ]
	  		 });
	  		 v= form.validateForm();
	  	});
	  	var onSave = function(){
	  		$("form").submit();
			if( v.valid() ){
				return $('form').formSerialize();
			}else{
				return null;
			}
		}  
    </script>
</head>
	<body style="padding: 10px">
		<form/>
		<div style="display: none"></div>
	</body>
</html>
