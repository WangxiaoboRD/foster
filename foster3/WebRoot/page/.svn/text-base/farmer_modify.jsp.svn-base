<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>修改代养户</title>
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
				{ display: "养殖公司编码", name:"e.company.id", type: "hidden", options:{value: '${e.company.id}'}},
				{ display: "养殖公司", name:"e.company.name", type: "text",group: "基础信息", groupicon: groupicon, options:{value: '${e.company.name}',readonly: true}},

				{ display: "id", name: "e.id", validate: { required: true }, options: { value: '${e.id}' }, type: "hidden"},
                { display: "编码", name: "e.code",options: { value: '${e.code}' }, validate: { required: true }, type: "text" }, 
                { display: "姓名", name: "e.name", validate: { required: true }, newline: true,options: { value: '${e.name}',readonly:true },type: "text"},

                { display: "身份证号", name: "e.idCard", options: { value: '${e.idCard}' },type: "text" },
                { display: "电话", name: "e.phone",newline: true,options: { value: '${e.phone}' }, type: "text" },

				{ display: "阶段", name: "e.stage.dcode", type: "select",options:{
	 			     url:'bussiness_ele_detail!loadByEntity',
				     parms:{'e.bussinessEle.ecode': 'FARMERSTAGE'},
				     valueField: 'dcode', //关键项
				     textField: 'value',
				     initValue: '${e.stage.dcode}',
 					 initText: '${e.stage.value}',
				 	 keySupport:true,
				 	 autocomplete: true
				}},
				{ display: "是否自养", name: "e.isOwnBreed",type: "radiogroup",newline: true, options:{               
			       	data: [{ text: '是', id: 'Y' },
		                  { text: '否', id: 'N' }],          
			       	name: "e.isOwnBreed",
			       	value: '${e.isOwnBreed}'     
             	}},
				
                { display: "家庭地址", name: "e.homeAddress",group: "园区信息", groupicon: groupicon,newline: true,width:440,options: { value: '${e.homeAddress}' }, type: "text" },

                { display: "园区面积", name: "e.farmArea",newline: true, options: { value: '${e.farmArea}' },type: "text" },
	 			{ display: "猪舍栋数", name: "e.piggeryQuan",options: { value: '${e.piggeryQuan}' }, type: "text" },

	 			{ display: "园区地址", name: "e.farmAddress",newline: true,width:440,options: { value: '${e.farmAddress}' }, type: "text" },

	 			{ display: "可代养头数", name: "e.breedQuan", newline: true,options: { value: '${e.breedQuan}' },type: "text" },
	 			{ display: "已养批次", name: "e.batchNum",options: { value: '${e.batchNum}' }, type: "text" }
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
