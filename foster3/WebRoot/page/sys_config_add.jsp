<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="content-type" content="text/html; charset=utf-8" />
	<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
	<meta content='width=330, height=400, initial-scale=1' name='viewport' />
	<link rel="icon" href="favicon.ico" type="../image/x-icon" />
	<title>${initParam.title}</title>
    <link href="../ligerUI/ligerUI/skins/Aqua/css/ligerui-all.css" rel="stylesheet" type="text/css" />
	<link href="../ligerUI/ligerUI/skins/ligerui-icons.css" rel="stylesheet" type="text/css" />	
    <script src="../ligerUI/jquery/jquery-1.6.4.js" type="text/javascript"></script>
	<script src="../ligerUI/jquery-validation/jquery.form.js" type="text/javascript"></script> 
	<script src="../ligerUI/jquery-validation/jquery.validate.js" type="text/javascript"></script> 
    <script src="../ligerUI/jquery-validation/jquery.metadata.js" type="text/javascript"></script>
    <script src="../ligerUI/jquery-validation/messages_cn.js" type="text/javascript"></script>    
    <script src="../ligerUI/ligerUI/js/ligerui.all.js" type="text/javascript"></script>       
	<script src="../ligerUI/ligerUI/js/template/ligerTemplate.js" type="text/javascript"></script>
	<script src="../ligerUI/ligerUI/js/template/ligerXSFormCreate.js" type="text/javascript"></script>
	<script src="../ligerUI/ligerUI/js/pap.js" type="text/javascript" ></script>
    <script type="text/javascript">
		var v;
        var form = null;
        $(function(){
        	 var groupicon = "../ligerUI/ligerUI/skins/icons/communication.gif";
	  		 var formOptions = {
	  		 	 url: "sys_config!save",	// 此处也是必须的
	  			 labelWidth: 75, 
           		 space:20,
	  			 fields: [
					{ display: "养殖公司",labelWidth: 110, name:"e.company.id",type:"select", group: '参数设置', groupicon: groupicon ,comboboxName: 'e.corporation.name',comboboxNameSubmit: true, newline: true, validate: { required: true },
					    options:{
					    	 url:'company!loadType',
					    	 keySupport:true,
							 valueField: 'id', 
							 textField: 'name'
					}},
	                { display: "药品销售系数",labelWidth: 110,group: '药品价格销售系数', groupicon: groupicon , name: "e.coefficient", type: "text",options:{value:'0',initSelect:true}}, 
	               ]};
               
              var template =$.pap.createSFormCreate({form: formOptions});
	  		  form = template.getForm() ;
	  		  v = form.validateForm() ;
        });
	  	
    </script>
</head>
<body></body>
</html>

