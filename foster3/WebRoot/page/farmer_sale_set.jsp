<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
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
	<script src="../ligerUI/ligerUI/js/core/base.js" type="text/javascript"></script>
	<script type="text/javascript" src="../ligerUI/ligerUI/js/ligerui.all.js"></script> 
	<script type="text/javascript" src="../ligerUI/ligerUI/js/pap.js"></script>
    <script type="text/javascript">
        var grid = null;
        var form = null;
        var value = null;
        var groupicon = "../ligerUI/ligerUI/skins/icons/communication.gif";
        $(function () {
        	form = $("#form1").ligerForm({
                labelWidth: 70,
                inputWidth:180,
                space:20,
                fields:[
					{ display: "养殖批次", name:"e.contract.code", type:"text",options:{readonly:true}},
					{ display: "合同编码", name:"e.contract.id", type:"hidden",options:{readonly:true}}
    			],
    			buttons:[
                   { text:'查询', icon: '../ligerUI/ligerUI/skins/icons/search2.gif', width: 70, click:function(item){
	                	var arra = $('form').formToArray();
	                   	if(grid){
	                   		grid.setParms(arra);
	                   		grid.loadData(true);
	                   	}
                  	}}
        		 ]
           	 });
        	 grid=$("#grid").ligerGrid({
                     columns:[
						{ display: "销售级别编码", name: 'pigLevel.dcode',hide:true},
						{ display: "销售级别名称", name: 'pigLevel.value',width:120},
						{ display: "销售级别定价(元)", name: 'price',width:120}
              		],
              		url:'contract_pig_priced!loadByBatch',
                    checkbox: true,
                    usePager: false,
               	    delayLoad:true,
                    rownumbers:true,
                    alternatingRow:false,
                    width: '99.5%'
             });
        });
        // 获得选中数据行
        function f_select(){
        	return grid.getSelectedRows();
        }
    </script>
</head>
<body>
<form id="form1"></form>
<div id="grid"></div>
</body>
</html>
