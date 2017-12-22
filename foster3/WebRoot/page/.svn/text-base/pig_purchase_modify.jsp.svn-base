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
    <script type="text/javascript" src="../ligerUI/ligerUI/js/pap.js"></script>    
    <script type="text/javascript">
    
	  	var form,v;
	  $(function(){
		 var groupicon = "../ligerUI/ligerUI/skins/icons/communication.gif";
		 //生成ligerUI form
		  var check = ${e.checkStatus};
		  var _fields;
		  if(check=='1'){
		  _fields=[
					{ display: "id", name: "e.id",type: "hidden",options: { value: '${e.id}' }},
					{ display: "登记时间", name: "e.registDate", type: "date",validate: { required: true },group: "猪苗登记单", groupicon: groupicon,options: { value: '${e.registDate}', showTime: false,readonly: true }},
					{ display: "养殖公司id", name:"e.company.id", type: "hidden", options:{value: '${e.company.id}'}},
					/**{ display: "养殖公司", name:"e.company.name",  type: "text", newline: true,options:{value: '${e.company.name}',readonly: true}},*/
					{ display: "代养户id", name:"e.farmer.id", type: "hidden", options:{value: '${e.farmer.id}'}},
					{ display: "代&nbsp;养&nbsp;户", name:"e.farmer.name",  type: "text",  options:{value: '${e.farmer.name}',readonly: true}},
					{ display: "批次id", name:"e.batch.id", type: "hidden", options:{value: '${e.batch.id}'}},
					{ display: "批&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;次", name:"e.batch.batchNumber",  type: "text",  options:{value: '${e.batch.batchNumber}',readonly: true}},
					{ display: "运输公司", name:"e.transportCo.id",comboboxName: 'transportCo',type:"select",options:{
					    url:'transport_co!loadByEntity',
					    valueField: 'id', 
				        textField: 'name',
				        initValue: '${e.transportCo.id}',
  					    initText: '${e.transportCo.name}',
				        keySupport:true,
						selectBoxHeight: 200,
						triggerToLoad: true,
						autocomplete: true,
						alwayShowInDown: true,
						onBeforeOpen: function() {
		                	var g = this;
		                	g.setParm('e.company.id', $("input[id='e.company.id']").val());
		                },
		                onSelected: function (nv){
		                	var _driver = liger.get("driver");
		                	if(_driver)
								_driver.setValue("");
		                }
					}},
					{ display: "司&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;机",newline: true, name:"e.driver.id",comboboxName: 'driver',type:"select",options:{
					    url:'driver!loadByEntity',
					    valueField: 'id', 
				        textField: 'name',
				        initValue: '${e.driver.id}',
  					    initText: '${e.driver.name}',
				        keySupport:true,
						selectBoxHeight: 200,
						triggerToLoad: true,
						autocomplete: true,
						alwayShowInDown: true,
						onBeforeOpen: function() {
		                	var g = this;
		                	g.setParm('e.company.id', $("input[id='e.company.id']").val());
		                	g.setParm('e.transportCo.id', $("input[id='e.transportCo.id']").val());
		                }
					}},
					{ display: "车&nbsp;牌&nbsp;号", name: "e.carNum", type: "text",options: { value: '${e.carNum}'} },
					
					{ display: "供&nbsp;应&nbsp;商", name:"e.pigletSupplier.id",comboboxName: 'pigletSupplier',type:"select",group: "猪苗采购信息", groupicon: groupicon,options:{
					    url:'cust_vender!loadByEntity',
					    valueField: 'id', 
				        textField: 'name',
				        initValue: '${e.pigletSupplier.id}',
  					    initText: '${e.pigletSupplier.name}',
				        keySupport:true,
						selectBoxHeight: 150,
						triggerToLoad: true,
						autocomplete: true,
						alwayShowInDown: true,
						onBeforeOpen: function() {
		                	var g = this;
		                	g.setParm('e.company.id', $("input[id='e.company.id']").val());
		                	g.setParm('e.custVenderType.dcode', 'PIGLETSUPPLIER');
		                }
					}},
					{ display: "头&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;数", name: "e.quantity", type: "int",validate: { required: true },options: {value: '${e.quantity}',readonly: true }},
					{ display: "重量(kg)", name: "e.weight", newline: true,type: "float",validate: { required: true },options: {initSelect: true,number:true, value: '${e.weight}' }},
					{ display: "运费(元)", name: "e.freight", type: "number",validate: { required: true },options: {initSelect: true,number:true, value: '${e.freight}' }},
					{ display: "成本单价", name: "e.price", type: "float",newline: true,width:170,options:{initSelect: true,number:true,value: '${e.price}'}},
					{ display: "成本(元)", name: "e.cost", type: "float",validate: { required: true },options: {initSelect: true,number:true, value: '${e.cost}' }},
					{ display: "日&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;龄",newline: true, name: "e.days", type: "int",validate: { required: true },options: { value: '${e.days}' }},
					{ display: "赠送头数", name: "e.giveQuantity", type: "text",options: { value: '${e.giveQuantity}',readonly: true },width:170},
					{ display: "备&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;注", name: "e.remark", type: "text",options: { value: '${e.remark}' }},
                ]
		  }else{
			  _fields=[
						{ display: "id", name: "e.id",type: "hidden",options: { value: '${e.id}' }},
						{ display: "登记时间", name: "e.registDate", type: "date",validate: { required: true },group: "猪苗登记单", groupicon: groupicon,options: { value: '${e.registDate}', showTime: false }},
						{ display: "养殖公司id", name:"e.company.id", type: "hidden", options:{value: '${e.company.id}'}},
						/**{ display: "养殖公司", name:"e.company.name",  type: "text", newline: true,options:{value: '${e.company.name}',readonly: true}},*/
						{ display: "代养户id", name:"e.farmer.id", type: "hidden", options:{value: '${e.farmer.id}'}},
						{ display: "代&nbsp;养&nbsp;户", name:"e.farmer.name",  type: "text",  options:{value: '${e.farmer.name}',readonly: true}},
						{ display: "批&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;次", name: "e.batch.id",comboboxName: 'batch', type: "select",options:{
						    url:'batch!loadByEntity',
						    valueField: 'id', 
					        textField: 'batchNumber',
					        initValue: '${e.batch.id}',
	  					    initText: '${e.batch.batchNumber}',
					        keySupport:true,
							selectBoxHeight: 200,
							triggerToLoad: true,
							autocomplete: true,
							onBeforeOpen: function() {
			                	var g = this;
			                	g.setParm('e.company.id', $("input[id='e.company.id']").val());
			                	g.setParm('e.farmer.id', $("input[id='e.farmer.id']").val());
			                	g.setParm('e.isBalance', 'N');
			                	g.setParm('e.contract.status.dcode', 'BREED,EFFECT');
			                }
						}},
						
						{ display: "运输公司", name:"e.transportCo.id",comboboxName: 'transportCo',type:"select",options:{
						    url:'transport_co!loadByEntity',
						    valueField: 'id', 
					        textField: 'name',
					        initValue: '${e.transportCo.id}',
	  					    initText: '${e.transportCo.name}',
					        keySupport:true,
							selectBoxHeight: 200,
							triggerToLoad: true,
							autocomplete: true,
							alwayShowInDown: true,
							onBeforeOpen: function() {
			                	var g = this;
			                	g.setParm('e.company.id', $("input[id='e.company.id']").val());
			                },
			                onSelected: function (nv){
			                	var _driver = liger.get("driver");
			                	if(_driver)
									_driver.setValue("");
			                }
						}},
						{ display: "司&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;机",newline: true, name:"e.driver.id",comboboxName: 'driver',validate: { required: true },type:"select",options:{
						    url:'driver!loadByEntity',
						    valueField: 'id', 
					        textField: 'name',
					        initValue: '${e.driver.id}',
	  					    initText: '${e.driver.name}',
					        keySupport:true,
							selectBoxHeight: 200,
							triggerToLoad: true,
							autocomplete: true,
							alwayShowInDown: true,
							onBeforeOpen: function() {
			                	var g = this;
			                	g.setParm('e.company.id', $("input[id='e.company.id']").val());
			                	g.setParm('e.transportCo.id', $("input[id='e.transportCo.id']").val());
			                }
						}},
						{ display: "车&nbsp;牌&nbsp;号", name: "e.carNum", type: "text",options: { value: '${e.carNum}'} },
						
						{ display: "供&nbsp;应&nbsp;商", name:"e.pigletSupplier.id",comboboxName: 'pigletSupplier',type:"select",group: "猪苗采购信息", groupicon: groupicon,options:{
						    url:'cust_vender!loadByEntity',
						    valueField: 'id', 
					        textField: 'name',
					        initValue: '${e.pigletSupplier.id}',
	  					    initText: '${e.pigletSupplier.name}',
					        keySupport:true,
							selectBoxHeight: 150,
							triggerToLoad: true,
							autocomplete: true,
							alwayShowInDown: true,
							onBeforeOpen: function() {
			                	var g = this;
			                	g.setParm('e.company.id', $("input[id='e.company.id']").val());
			                	g.setParm('e.custVenderType.dcode', 'PIGLETSUPPLIER');
			                }
						}},
						{ display: "头&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;数", name: "e.quantity", type: "int",validate: { required: true },options: {initSelect: true,digits:true, value: '${e.quantity}' }},
						{ display: "重量(kg)", name: "e.weight", newline: true,type: "float",validate: { required: true },options: {initSelect: true,number:true, value: '${e.weight}' }},
						{ display: "运费(元)", name: "e.freight", type: "number",validate: { required: true },options: {initSelect: true,number:true, value: '${e.freight}' }},
						{ display: "成本单价", name: "e.price", type: "float",newline: true,width:170,options:{initSelect: true,number:true,value: '${e.price}'}},
						{ display: "成本(元)", name: "e.cost", type: "float",validate: { required: true },options: {initSelect: true,number:true, value: '${e.cost}' }},
						{ display: "日&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;龄",newline: true, name: "e.days", type: "int",validate: { required: true },options: { value: '${e.days}' }},
						{ display: "赠送头数", name: "e.giveQuantity", type: "text",options: {initSelect: true,digits:true, value: '${e.giveQuantity}' },width:170},
						{ display: "备&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;注", name: "e.remark", type: "text",options: { value: '${e.remark}' }},
	                ]
		  }
	  	 
	  	 form = $("form").ligerForm({
	  		       inputWidth: 170, labelWidth: 80, space: 20,
	  		       fields:_fields
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
	<body style="padding: 8px">
		<form/>
		<div style="display: none"></div>
	</body>
</html>
