<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head><title>修改用户</title>

    <link href="../ligerUI/ligerUI/skins/Aqua/css/ligerui-all.css" rel="stylesheet" type="text/css" />
	<link href="../ligerUI/ligerUI/skins/ligerui-icons.css" rel="stylesheet" type="text/css" />	
    <script src="../ligerUI/jquery/jquery-1.6.4.js" type="text/javascript"></script>
	<script src="../ligerUI/jquery-validation/jquery.form.js" type="text/javascript"></script> 
	<script src="../ligerUI/jquery-validation/jquery.validate.js" type="text/javascript"></script> 
    <script src="../ligerUI/jquery-validation/jquery.metadata.js" type="text/javascript"></script>
    <script src="../ligerUI/jquery-validation/messages_cn.js" type="text/javascript"></script>    
    <script src="../ligerUI/ligerUI/js/ligerui.all.js" type="text/javascript"></script>       
	<script src="../ligerUI/ligerUI/js/template/ligerTemplate.js" type="text/javascript"></script>
	<script type="text/javascript" src="../ligerUI/ligerUI/js/pap.js"></script>
    <script type="text/javascript">
	  var form,v;
	  $(function(){
		 var groupicon = "../ligerUI/ligerUI/skins/icons/communication.gif";
		  //生成ligerUI form
	  	 form = $("form").ligerForm({
	  		 inputWidth: 170, labelWidth: 80, space: 80,
             fields: [
                { display: "用户账号", name: "e.userCode",validate:{required:true}, type: "text",newline: true,group: "基础信息", groupicon: groupicon, options:{value:'${e.userCode}',readonly:true}},  
                { display: "用户名称 ", name: "e.userRealName", newline: true, validate: { required: true },options:{value:'${e.userRealName}'}, type: "text"},
                { display: "所属岗位 ", name: "e.post", newline: true, options:{value:'${e.post}'}, type: "text"},
                { display: "养殖公司 ", name: "e.company.id", newline: true,options:{value:'${e.company.id}'}, type: "hidden"},
                { display: "养殖公司 ", name: "company", newline: true,options:{readonly:true,value:'${e.company.name}'}, type: "text"},
				{ display: "技术员", name: "e.technician.id",comboboxName: 'technician', type: "select",options:{
				     url:'technician!loadByEntity',
				     valueField: 'id', //关键项
				   	 textField: 'name',
				   	 initValue: '${e.technician.id}',
  					 initText: '${e.technician.name}',
  					 keySupport:true, 
		             selectBoxHeight: 150,
		             autocomplete: true,
					 triggerToLoad: true,
				     onBeforeOpen: function() {
	                	var g = this;
	                	g.setParm('e.company.id', $("input[id='e.farmer.id']").val());
			         }
				}},
                { display: "状态", name: "e.userStatus",type: "radiogroup",group: "设置状态", groupicon: groupicon,newline: true, options:{               
			       	data: [{ text: '启用', id: '1' },
		                  { text: '停用', id: '2' }],          
			       	name: "e.userStatus",
			       	value: '${e.userStatus}'     
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
<body style="padding:10px">
<form/>
<div style="display: none"></div>
</body>
</html>
