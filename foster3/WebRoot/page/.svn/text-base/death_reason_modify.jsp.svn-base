<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>修改死亡原因</title>
    <link href="../ligerUI/ligerUI/skins/Aqua/css/ligerui-all.css" rel="stylesheet" type="text/css" />
	<link href="../ligerUI/ligerUI/skins/ligerui-icons.css" rel="stylesheet" type="text/css" />	
    <script src="../ligerUI/jquery/jquery-1.6.4.js" type="text/javascript"></script>
	<script src="../ligerUI/jquery-validation/jquery.form.js" type="text/javascript"></script> 
	<script src="../ligerUI/jquery-validation/jquery.validate.js" type="text/javascript"></script> 
    <script src="../ligerUI/jquery-validation/jquery.metadata.js" type="text/javascript"></script>
    <script src="../ligerUI/jquery-validation/messages_cn.js" type="text/javascript"></script>    
    <script src="../ligerUI/ligerUI/js/ligerui.all.js" type="text/javascript"></script>       
    <script type="text/javascript">
       /*  1.使用 html编写
	  	$(function(){
	  		 $("form").ligerForm();
	  	});*/
	  	// 2.不适用 html 使用js 自动生成效果
	  	var form,v;
	  $(function(){
		 var groupicon = "../ligerUI/ligerUI/skins/icons/communication.gif";
		 //生成ligerUI form
	  	 form = $("form").ligerForm({
	  		 inputWidth: 170, labelWidth: 80, space: 20,
             fields: [
				{ display: "id", name: "e.id", validate: { required: true }, options: { value: '${e.id}' }, type: "hidden",newline: true},
				{ display: "编码", name: "e.code",group: "基础信息", groupicon: groupicon, validate: { required: true }, options: { value: '${e.code}' },newline: true},
                { display: "死亡原因", name: "e.name", options: {value: '${e.name}' }, newline: true, type: "text" },
                { display: "所属类型", name:"e.deathReasonType.id",newline: true,type:"select",validate: { required: true }, options:{
					url: 'death_reason_type!loadByEntity',
	                valueField: 'id',
	                textField: 'name', 
	                initValue: '${e.deathReasonType.id}',
 					initText: '${e.deathReasonType.name}',
	                selectBoxHeight: 150,
	                autocomplete: true,
	                alwayShowInDown:true,
	                keySupport:true
				}},
				{ display: "急慢性", name: "e.acuteChronic.dcode",newline: true, validate: { required: true },type: "select",options:{
	 			     url:'bussiness_ele_detail!loadByEntity',
				     parms:{'e.bussinessEle.ecode': 'ACUTECHRONIC'},
				     valueField: 'dcode', //关键项
				     textField: 'value',
				     initValue: '${e.acuteChronic.dcode}',
  					 initText: '${e.acuteChronic.value}',
				 	 keySupport:true,
				 	 alwayShowInDown:true,
				 	 autocomplete: true
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
    <style type="text/css">
         body{ font-size:12px;}
        .l-table-edit {}
        .l-table-edit-td{ padding:4px;}
    </style>

</head>
	<body style="padding: 10px">
		<form/>
		<div style="display: none"></div>
	</body>
</html>
