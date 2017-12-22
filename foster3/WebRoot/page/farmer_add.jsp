<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>添加公司</title>
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
				{ display: "养殖公司", name: "e.company.id",comboboxName: 'company',group: "基础信息", groupicon: groupicon, validate: { required: true }, newline: true,type: "select",options:{
				    url:'company!loadByEntity',
				    valueField: 'id', //关键项
				  	 textField: 'name',
					 keySupport:true,
					 alwayShowInDown: true,
				    selectBoxHeight: 200,
				    selectBoxWidth: 180,
				    onSelected:function(nv){
						var _technician=liger.get("technician");
						if(_technician)
							_technician.setValue("");
					}
				}},
				
                { display: "编码", name: "e.code",validate: { required: true }, type: "text" }, 
                { display: "姓名", name: "e.name",newline: true, validate: { required: true },type: "text"},
                
                { display: "身份证号", name: "e.idCard",type: "text" },
                { display: "电话", name: "e.phone",  newline: true,type: "text" },
				{ display: "阶段", name: "e.stage.dcode", validate: { required: true },type: "select",options:{
	 			     url:'bussiness_ele_detail!loadByEntity',
				     parms:{'e.bussinessEle.ecode': 'FARMERSTAGE'},
				     valueField: 'dcode', //关键项
				     textField: 'value',
				 	 keySupport:true,
				 	 autocomplete: true,
				}},
				{ display: "是否自养", name: "e.isOwnBreed",type: "radiogroup",newline: true, options:{               
			       	data: [{ text: '是', id: 'Y' },
		                  { text: '否', id: 'N' }],          
			       	name: "e.isOwnBreed",
			       	value: 'N'     
             	}},
				
	 			{ display: "家庭地址", name: "e.homeAddress",group: "园区信息", groupicon: groupicon,width:440,newline: true, type: "text" },

	 			{ display: "面积(㎡)", name: "e.farmArea",newline: true, type: "text" },
	 			{ display: "猪舍栋数", name: "e.piggeryQuan",type: "text" },
	 			
	 			{ display: "园区地址", name: "e.farmAddress",width:440,newline: true, type: "text" },
	 			
	 			{ display: "养殖头数", name: "e.breedQuan", newline: true,type: "text" },
	 			{ display: "已养批次", name: "e.batchNum", type: "text",value:"0" }
	 			
	 			
                ]
	  		 });
	  		 v= form.validateForm();
	  	});
	  	var onSave = function(){
	  		$("form").submit();
			if( v.valid() ){
				return  $('form').formSerialize();
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
