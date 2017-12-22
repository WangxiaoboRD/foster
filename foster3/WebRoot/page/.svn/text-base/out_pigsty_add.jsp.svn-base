<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>出栏单</title>
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
					{display: "出栏日期", name: "e.registDate", type: "date",validate: {required:true},group: "出栏单", groupicon: groupicon,options: {value:new Date(),showTime:true,format:"yyyy-MM-dd"}},
					{display: "出栏类型", name: "e.saleType",type: "radiogroup",newline: true,validate: { required: true }, options:{               
				       	data: [{ text: '正常出栏', id: 'Q' },
			                  { text: '淘汰出栏', id: 'E' }],          
				       	name: "e.saleType",
				       	value: 'Q'     
	                }},
                    {display: "代养农户", name:"e.farmer.id",newline: true,comboboxName: 'farmer',validate: { required: true },type:"select",options:{
					    url:'farmer!loadByPinYin',
					    valueField: 'id', 
				        textField: 'name',
				        autocomplete: true,
		                keySupport:true,
						selectBoxHeight: 200,
						selectBoxWidth: 200,
						triggerToLoad: true,
						onSelected: function (nv){
							var _batch = liger.get("batch");
							if(_batch)
								_batch.setValue("");
							var _customer = liger.get("customer");
							if(_customer)
								_customer.setValue("");
							var _cs = liger.get("farmerSale");
							if(_cs)
								_cs.setValue("");
	            		}
					}},
					{display: "养殖批次", name:"e.batch.id",comboboxName: 'batch',newline: true,validate: { required: true },type:"select",options:{
					    url:'batch!loadByEntity',
					    valueField: 'id', 
				        textField: 'batchNumber',
				        keySupport:true,
						selectBoxHeight: 80,
						triggerToLoad: true,
						onBeforeOpen: function() {
		                	var g = this;
		                	g.setParm('e.farmer.id', $("input[id='e.farmer.id']").val());
		                	g.setParm('e.contract.status.dcode', 'BREED');
		                	g.setParm('e.isBalance', 'N');
		                },
		                onSelected : function(value, oldValue) {
			                if(value){
				                //获取批次对应的头数
				                $.ligerui.ligerAjax({
									type: "POST",
									async:  false,
									url: "batch!loadById",
									data: {id:value},
									dataType: "json",
									success: function(result, status){
										if(result != "") {
											$("input[id='e.tempStack.quantity']").val(result.e.quantity);
										}
									},
									error: function(XMLHttpRequest,status){
										$.ligerDialog.error('操作出现异常');
									},
									complete:function(){}
							   	})
			                }
		                }
					}},
					{display: "当前头数(头)", name: "e.tempStack.quantity",newline: true, type: "text",options: {readonly: true}},
					{display: "销售头数(头)", name: "e.quantity",group: "出栏信息", groupicon: groupicon, type: "digits",validate: { required: true },newline: true,options:{initSelect: true,digits:true,value:"0"}},
					{display: "销售重量(KG)", name: "e.weight", type: "number",newline: true,options:{initSelect: true,number:true,value:"0.00"}},
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
