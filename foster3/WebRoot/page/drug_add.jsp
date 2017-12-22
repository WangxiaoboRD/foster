<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title></title>
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
				{ display: "养殖公司", group: "基础信息", groupicon: groupicon,name:"e.company.id", type:"select" ,validate:{ required:true }, newline:true, options:{
				     url:'company!load',
				     valueField: 'id', //关键项
				 	 textField: 'name',
					 keySupport:true,
					 alwayShowInDown: true
				     
				}},
				
                { display: "药品名称", name: "e.name",validate: { required: true }, newline: true, type: "text"},
                { display: "药品编码", name: "e.code",validate: { required: true }, newline: true, type: "text"},
                { display: "系统编码", name: "e.sysCode",newline: true, type: "text"},
                { display: "药品类型", name: "e.drugType",type: "radiogroup",newline: true, options:{               
			       	data: [{ text: '药品', id: '0' },
		                  { text: '疫苗', id: '1' }],          
			       	name: "e.drugType",
			       	value: '0', 
                }},
                { display: "药品规格", name: "e.spec",validate: { required: true }, newline: true, type: "text"},
                { display: "药品单位", name: "e.unit.dcode", type:"select" ,validate:{ required:true }, newline:true, options:{
                	 url:'bussiness_ele_detail!loadByEntity',
	        		 parms:{'e.bussinessEle.ecode': 'UNIT'},
				     valueField: 'dcode', //关键项
				 	 textField: 'value',
					 keySupport:true
				}},
				{ display: "副单位", name: "e.subUnit.dcode", type:"select" ,validate:{ required:true }, newline:true, options:{
               	 url:'bussiness_ele_detail!loadByEntity',
	        		 parms:{'e.bussinessEle.ecode': 'UNIT'},
				     valueField: 'dcode', //关键项
				 	 textField: 'value',
					 keySupport:true
				}},
				{ display: "换算比例", name: "e.ratio", newline: true,validate:{ required:true }, type: "text"},
				{ display: "供应商", name: "e.supplier", newline: true,validate:{ required:true }, type: "text"}
                
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
