<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>添加死亡原因</title>
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
					{display: "销售日期", name: "e.registDate", type: "date",validate: {required:true},group: "公司销售单", groupicon: groupicon,options: {value:new Date(),showTime:true,format:"yyyy-MM-dd"}},
                    {display: "代养农户", name:"e.farmer.id",newline: true,comboboxName: 'farmer',validate: { required: true },type:"select",options:{
					    url:'farmer!loadByEntity',
					    valueField: 'id', 
				        textField: 'name',
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
		                },
					}},
					{ display: "代养销售单", name:"e.farmerSale", type:"select" ,comboboxName: 'farmerSale',  newline:true, options:{
					    url:'farmer_sale!loadByEntity',
					    valueField: 'id', //关键项
					   	textField: 'id',
						keySupport:true,
						alwayShowInDown: true,
					    selectBoxHeight: 150,
					    //isMultiSelect: true,
					    //isShowCheckBox: true,
					    //split: ",",
					    triggerToLoad: true,
					    onBeforeOpen: function() {
		                	var g = this;
		                	g.setParm('e.batch.id', $("input[id='e.batch.id']").val());
		                	g.setParm('e.farmer.id', $("input[id='e.farmer.id']").val());
		                	g.setParm('e.checkStatus', '1');
		                },
		                onSelected : function(value, oldValue) {
							//alert("111");
			                if(value){
				                //获取批次对应的头数
				                $.ligerui.ligerAjax({
									type: "POST",
									async:  false,
									url: "farmer_sale!loadByIds",
									data: {id:value},
									dataType: "json",
									success: function(result, status){
										if(result != "") {
											$("input[id='e.quantity']").val(result.e.totalQuan);
											$("input[id='e.weight']").val(result.e.totalWeight);
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
					{display: "销售商", name:"e.buyer.id",type:"select",comboboxName: 'customer',newline: true,options:{
					    url:'cust_vender!loadByCustomer',
					    valueField: 'id', 
				        textField: 'name',
				        keySupport:true,
						selectBoxHeight: 100,
						triggerToLoad: true,
						onBeforeOpen: function() {
		                	var g = this;
		                	g.setParm('e.custVenderType.dcode', 'PIGDEALER');
		                	g.setParm('e.company.id', $("input[id='e.farmer.id']").val());
		                }
					}},
					{display: "销售头数(头)", name: "e.quantity",group: "销售信息", groupicon: groupicon, type: "digits",validate: { required: true },newline: true,options:{initSelect: true,digits:true,value:"0"}},
					{display: "销售重量(KG)", name: "e.weight", type: "number",validate: { required: true },newline: true,options:{initSelect: true,number:true,value:"0.00"}},
					{display: "销售金额(元)", name: "e.amount", type: "number",validate: { required: true },newline: true,options:{initSelect: true,number:true,value:"0.00"}},
					{display: "运输费用(元)", name: "e.tcost", type: "number",validate: { required: true },newline: true,options:{initSelect: true,number:true,value:"0.00"}}
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
