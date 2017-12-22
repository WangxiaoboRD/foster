<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>账单调整</title>
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
	  		 inputWidth: 170, labelWidth: 75, space: 20,
             fields: [
					{display: "账单编号", name: "e.id", type: "text",options:{value:'${e.id}',readonly:true},group: "账单信息", groupicon: groupicon},
	                {display: "代养农户", name:"e.farmer.name",type:"text",options:{value:'${e.farmer.name}',readonly:true}},
					{display: "养殖批次", name:"e.batch.batchNumber",newline: true,type:"text",options:{value:'${e.batch.batchNumber}',readonly:true}},
					{display: "销售收入", name:"e.saleIncome",type:"text",options:{value:'${e.saleIncome}',readonly:true}},
					{display: "总养殖费", name:"e.allBreedCost",newline: true,type:"text",options:{value:'${e.allBreedCost}',readonly:true}},
					{display: "代养费用", name:"e.farmerCost",type:"text",options:{value:'${e.farmerCost}',readonly:true}},
					
					{display: "调整费用", name: "e.adjustCost", type: "number",validate: { required: true },options:{initSelect: true,number:true,value:'${e.adjustCost}'},group: "调整信息", groupicon: groupicon},
					{display: "调整人", name: "e.adjustMen", type: "text",validate: { required: true },options:{value:'${e.adjustMen}'}},
					{display: "调整原因", name: "e.adjustReason",width:435, type: "text",validate: { required: true },newline: true,options:{value:'${e.adjustReason}'}}
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
