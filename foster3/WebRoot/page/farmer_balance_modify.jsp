<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>修改公司销售单</title>
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
	  $(function(){
		 var groupicon = "../ligerUI/ligerUI/skins/icons/communication.gif";
	  	 form = $("form").ligerForm({
	  		 inputWidth: 170, labelWidth: 100, space: 20,
             fields: [
					{display: "id", name: "e.id", type: "hidden",options:{value:'${e.id}'}},
					{display: "销售日期", name: "e.registDate", type: "date",validate: {required:true},group: "公司销售单", groupicon: groupicon,options: {value:'${e.registDate}',format:"yyyy-MM-dd",alwayShowInDown: true}},
	                {display: "代养农户", name:"e.farmer.id",newline: true,comboboxName: 'farmer',validate: { required: true },type:"select",options:{
					    url:'farmer!loadByPinYin',
					    valueField: 'id', 
				        textField: 'name',
				        keySupport:true,
						selectBoxHeight: 120,
						triggerToLoad: true,
						autocomplete: true,
						alwayShowInDown:true,
						initValue: '${e.farmer.id}',
  					    initText: '${e.farmer.name}', 
						onSelected: function (nv){
							var _batch = liger.get("batch");
							if(_batch)
								_batch.setValue("");
	            		}
					}},
					{display: "养殖批次", name:"e.batch.id",comboboxName: 'batch',newline: true,validate: { required: true },type:"select",options:{
					    url:'batch!loadByEntity',
					    valueField: 'id', 
				        textField: 'batchNumber',
				        keySupport:true,
						selectBoxHeight: 110,
						autocomplete: true,
						triggerToLoad: true,
						alwayShowInDown:true,
						initValue: '${e.batch.id}',
  					    initText: '${e.batch.batchNumber}', 
						onBeforeOpen: function() {
		                	var g = this;
		                	g.setParm('e.farmer.id', $("input[id='e.farmer.id']").val());
		                },
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
	<body style="padding: 5px">
		<form/>
		<div style="display: none"></div>
	</body>
</html>
