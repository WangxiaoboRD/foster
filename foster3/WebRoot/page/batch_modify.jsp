<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head><title>死亡原因类型</title>

    <link href="../ligerUI/ligerUI/skins/Aqua/css/ligerui-all.css" rel="stylesheet" type="text/css" />
	<link href="../ligerUI/ligerUI/skins/ligerui-icons.css" rel="stylesheet" type="text/css" />	
    <script src="../ligerUI/jquery/jquery-1.6.4.js" type="text/javascript"></script>
	<script src="../ligerUI/jquery-validation/jquery.form.js" type="text/javascript"></script> 
	<script src="../ligerUI/jquery-validation/jquery.validate.js" type="text/javascript"></script> 
    <script src="../ligerUI/jquery-validation/jquery.metadata.js" type="text/javascript"></script>
    <script src="../ligerUI/jquery-validation/messages_cn.js" type="text/javascript"></script>    
    <script src="../ligerUI/ligerUI/js/ligerui.all.js" type="text/javascript"></script>       
    <script type="text/javascript" src="../ligerUI/ligerUI/js/plugins/XGrid.js"></script>    
	<script src="../ligerUI/ligerUI/js/template/ligerTemplate.js" type="text/javascript"></script>
	<script type="text/javascript" src="../ligerUI/ligerUI/js/pap.js"></script>
	<script type="text/javascript" src="../ligerUI/ligerUI/js/core/ligerAjax.js"></script>
    <script type="text/javascript" src="../ligerUI/ligerUI/js/json2.js"></script>
    <script type="text/javascript">
	  	
	  var form,v;
	  $(function(){
		  var groupicon = "../ligerUI/ligerUI/skins/icons/communication.gif";
		  //生成ligerUI form
	  	 form = $("form").ligerForm({
	  		 inputWidth: 170, labelWidth: 80, 
             fields: [
				{ display: "批次id", name: "e.id", type: "hidden", options:{value: '${e.id}'}}, 
                { display: "批次编码", name: "e.batchNumber", group: "批次编号", groupicon: groupicon,type: "text", options:{readonly:true,value: '${e.batchNumber}'}}, 
                { display: "技术员", name: "e.technician.id",newline: true,comboboxName: 'technician',validate: { required: true }, type: "select",options:{
				     url:'technician!loadByBatch',
				     valueField: 'id', //关键项
				   	 textField: 'name',
				   	 initValue: '${e.technician.id}',
 					 initText: '${e.technician.name}',
 					 keySupport:true, 
		             selectBoxHeight: 130,
		             autocomplete: true,
					 triggerToLoad: true,
				     onBeforeOpen: function() {
	                	var g = this;
	                	g.setParm('e.company.id', '${e.id}');
			         }
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
	  		/*console.log($('#form1').formSerialize());
			return $('#form1').formSerialize();*/
		}  	 
    </script>
    <style type="text/css">
         body{ font-size:12px;}
        .l-table-edit {}
        .l-table-edit-td{ padding:4px;}
    </style>
</head>
	<body style="padding: 10px">
		<form />
	</body>
</html>
