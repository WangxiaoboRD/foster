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
                labelWidth: 60,
                inputWidth:150,
                space:20,
                fields:[
					{ display: "代养户", name:"e.company.id", type:"hidden"},
					{ display: "饲料类型", name:"e.feedType.id",group: "查询条件", groupicon: groupicon,type:"select",options:{
					    url:'feed_type!loadByEntity',
					    valueField: 'id', 
				        textField: 'name',
				        keySupport:true,
						selectBoxHeight: 200,
						triggerToLoad: true
					}}
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
						{ display: "饲料编码", name: 'id',hide:true},
						{ display: "饲料名称", name: 'name'},
						{ display: "饲料规格", name: 'spec'},
						{ display: "饲料包装", name: 'packForm',render: function(data){
		    				var text = data.packForm;
	    					switch(data.packForm){
	    						case 1: text = "散装"; break;
	    						case 2: text = "袋装"; break;
	    					}
	    					return text;
	    				}},
						{ display: "饲料单位", name: 'unit.value'},
						{ display: "饲料类型", name: 'feedType.name'},
						{ display: "养殖公司", name: 'company.name'}
              		],
              		url:'feed!loadFeed',
                    checkbox: true,
                    usePager: false,
               	    delayLoad:true,
                    rownumbers:true,
                    alternatingRow:false,
                    width: '99.5%',
                    height: '99.5%'
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
