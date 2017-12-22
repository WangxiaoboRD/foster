<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>添加猪苗登记单</title>
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
                    { display: "登记时间", name: "e.registDate", type: "date",validate: { required: true },group: "猪苗登记单", groupicon: groupicon,options: { value: new Date(), showTime: false }},
					{ display: "代&nbsp;养&nbsp;户", name:"e.farmer.id",comboboxName: 'farmer',type:"select",validate: { required: true },options:{
					    url:'farmer!loadByPinYin',
					    valueField: 'id', 
				        textField: 'name',
				        keySupport:true,
						selectBoxHeight: 200,
						triggerToLoad: true,
						autocomplete: true,
		                onSelected: function (nv){
					        var _batch = liger.get("batch");
							if(_batch)
								_batch.setValue("");
					    }
					}},
					{ display: "批&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;次", newline: true,name: "e.batch.id",comboboxName: 'batch', type: "select",newline: true,validate: { required: true },options:{
					    url:'batch!loadByEntity',
					    valueField: 'id', 
				        textField: 'batchNumber',
				        keySupport:true,
						selectBoxHeight: 200,
						triggerToLoad: true,
						onBeforeOpen: function() {
		                	var g = this;
		                	g.setParm('e.farmer.id', $("input[id='e.farmer.id']").val());
		                	g.setParm('e.isBalance', 'N');
		                	g.setParm('e.contract.status.dcode', 'BREED,EFFECT');
		                }
					}},
					
					{ display: "运输公司", name:"e.transportCo.id",comboboxName: 'transportCo',type:"select",options:{
					    url:'transport_co!loadByFarmer',
					    valueField: 'id', 
				        textField: 'name',
				        keySupport:true,
						selectBoxHeight: 150,
						triggerToLoad: true,
						alwayShowInDown: true,
						onBeforeOpen: function() {
		                	var g = this;
		                	g.setParm('e.code', $("input[id='e.farmer.id']").val());
		                },
		                onSelected: function (nv){
		                	var _driver = liger.get("driver");
		                	if(_driver)
								_driver.setValue("");
		                }
					}},
					{ display: "司&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;机",newline: true, name:"e.driver.id",comboboxName: 'driver',type:"select",options:{
					    url:'driver!loadByFarmer',
					    valueField: 'id', 
				        textField: 'name',
				        keySupport:true,
						selectBoxHeight: 150,
						triggerToLoad: true,
						alwayShowInDown: true,
						onBeforeOpen: function() {
		                	var g = this;
		                	g.setParm('e.code', $("input[id='e.farmer.id']").val());
		                	g.setParm('e.transportCo.id', $("input[id='e.transportCo.id']").val());
		                }
					}},
					{ display: "车&nbsp;牌&nbsp;号", name: "e.carNum", type: "text"},
					{ display: "供&nbsp;应&nbsp;商", name:"e.pigletSupplier.id",comboboxName: 'pigletSupplier',type:"select",group: "猪苗采购信息", groupicon: groupicon,options:{
					    url:'cust_vender!loadByFarmer',
					    valueField: 'id', 
				        textField: 'name',
				        keySupport:true,
						selectBoxHeight: 150,
						triggerToLoad: true,
						alwayShowInDown: true,
						onBeforeOpen: function() {
		                	var g = this;
		                	g.setParm('e.code', $("input[id='e.farmer.id']").val());
		                	g.setParm('e.custVenderType.dcode', 'PIGLETSUPPLIER');
		                }
					}},
					{ display: "头&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;数", name: "e.quantity", type: "int",validate: { required: true },width:170,options:{initSelect: true,digits:true,value:"0"}},
					{ display: "重量(kg)", name: "e.weight", type: "float",validate: { required: true },newline: true,width:170,options:{initSelect: true,number:true,value:"0.00"}},
					{ display: "运费(元)", name: "e.freight", type: "float",validate: { required: true },width:170,options:{initSelect: true,number:true,value:"0.00"}},
					{ display: "成本单价", name: "e.price", type: "float",newline: true,width:170,options:{initSelect: true,number:true,value:"0.00"}},
					{ display: "成本(元)", name: "e.cost", type: "float",validate: { required: true },width:170,options:{initSelect: true,number:true,value:"0.00"}},
					{ display: "日&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;龄", name: "e.days",newline: true, type: "int",validate: { required: true },width:170,options:{initSelect: true,digits:true,value:"0"}},
					{ display: "赠送头数", name: "e.giveQuantity", type: "text",width:170},
					{ display: "备&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;注", newline: true,name: "e.remark", type: "text",width:170}
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
