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
				{ display: "编码", name: "e.id",options: { value: '${e.id}' }, type: "hidden",newline: true},
				{ display: "养殖公司编码", name:"e.company.id", type: "hidden", options:{value: '${e.company.id}'}},
				{ display: "养殖公司", name:"s_name", type: "text",group: "类型属性", groupicon: groupicon, options:{value: '${e.company.name}',readonly: true}},
				{ display: "饲料阶段编码", name:"e.feedType.id", type: "hidden", options:{value: '${e.feedType.id}'}},
				{ display: "饲料阶段", name:"f_name",  type: "text", options:{value: '${e.feedType.name}',readonly: true}},
				{ display: "饲料类型", name: "e.feedClass.dcode", type:"select" ,validate:{ required:true }, newline:true, options:{
               	 	url:'bussiness_ele_detail!loadByEntity',
	        		 parms:{'e.bussinessEle.ecode': 'FEEDTYPE'},
				     valueField: 'dcode', //关键项
				 	 textField: 'value',
				 	 keySupport:true,
					 value: '${e.feedClass.dcode}',
					 alwayShowInDown: true,
				     selectBoxHeight: 120
				}},
                { display: "饲料名称", name: "e.name", group: "基础信息", groupicon: groupicon,options: { value: '${e.name}', readonly: true}, type: "text"},
                { display: "饲料编码", name: "e.code",options: { value: '${e.code}' },validate: { required: true }, type: "text"},
                { display: "饲料规格", name: "e.spec",options: { value: '${e.spec}' },validate: { required: true }, type: "text"},
                { display: "饲料包装", name: "e.packForm",type: "radiogroup", options:{               
			       	data: [{ text: '散装', id: '1' },
		                  { text: '袋装', id: '2' }],          
			       	name: "e.packForm",
			       	value: '${e.packForm}', 
                }},
                { display: "饲料单位", name: "e.unit.dcode", type:"select" ,validate:{ required:true }, options:{
                	 url:'bussiness_ele_detail!loadByEntity',
	        		 parms:{'e.bussinessEle.ecode': 'UNIT'},
				     valueField: 'dcode', //关键项
				 	 textField: 'value',
					 keySupport:true,
					 value: '${e.unit.dcode}',
					 alwayShowInDown: true,
				     selectBoxHeight: 120
				}},
				{ display: "副单位", name: "e.subUnit.dcode", type:"select" , validate:{ required:true }, options:{
                	 url:'bussiness_ele_detail!loadByEntity',
	        		 parms:{'e.bussinessEle.ecode': 'UNIT'},
				     valueField: 'dcode', //关键项
				 	 textField: 'value',
					 keySupport:true,
					 value: '${e.subUnit.dcode}',
					 alwayShowInDown: true,
				     selectBoxHeight: 120
				}},
				{ display: "换算比例", name: "e.ratio",options: { value: '${e.ratio}' },validate:{ required:true }, type: "text"}
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
