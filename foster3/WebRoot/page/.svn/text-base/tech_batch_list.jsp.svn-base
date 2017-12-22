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
	<script src="../ligerUI/ligerUI/js/template/ligerTemplate.js" type="text/javascript"></script>
	<script src="../ligerUI/ligerUI/js/template/ligerXFormGridList.js" type="text/javascript"></script>
	<style type="text/css">	
		.toolbar-pap {
    		height: 0px;
		}
	</style>
    <script type="text/javascript">
    	// 表格
        var grid = null;
        $(function () {
        	 form = {
                 labelWidth: 60,
                 space: 20,
                 fields:[
						{ display: "养殖公司", name:"e.company.id", type:"select", options:{
						    url:'company!load',
						    valueField: 'id', 
						    textField: 'name',
						    keySupport:true,
						    selectBoxHeight: 200,
							selectBoxWidth: 180,
							onSelected: function (nv){
								var _farmer = liger.get("farmer");
								if(_farmer)
									_farmer.setValue("");
								var _batch = liger.get("batch");
								if(_batch)
									_batch.setValue("");
								var _technician = liger.get("technician");
								if(_technician)
									_technician.setValue("");
							} 
						}},
						{display: "代养农户", name:"e.farmer.id",comboboxName: 'farmer',type:"select",options:{
						    url:'farmer!loadByPinYin',
						    valueField: 'id', 
						    textField: 'name',
						    keySupport:true,
						    alwayShowInDown:true,
							selectBoxHeight: 120,
							triggerToLoad: true,
							autocomplete: true,
							onSelected: function (nv){
								var _batch = liger.get("batch");
								if(_batch)
									_batch.setValue("");
							}
						}},
						{display: "养殖批次", name:"e.batch.id",comboboxName: 'batch',type:"select",options:{
						    url:'batch!loadByEntity',
						    valueField: 'id', 
						    textField: 'batchNumber',
						    keySupport:true,
							selectBoxHeight: 120,
							alwayShowInDown:true,
							triggerToLoad: true,
							onBeforeOpen: function() {
						    	var g = this;
						    	g.setParm('e.farmer.id', $("input[id='e.farmer.id']").val());
						    }
						}},
						{ display: "技术员", name: "e.technician.id",comboboxName: 'technician', type: "select",options:{
						     url:'technician!loadByEntity',
						     valueField: 'id', //关键项
						   	 textField: 'name',
							 keySupport:true,
							 selectBoxHeight: 130,
							 triggerToLoad: true,
						     onBeforeOpen: function() {
			                	var g = this;
			                	g.setParm('e.company.id', $("input[id='e.farmer.id']").val());
					         }
						}}
	    			]
            	}

        	 var gridoption={
                     columns:[
                        { display: '编      号',name: 'id'},
                        { display: '所属公司',name: 'company.name'},
                        { display: '代养户',name: 'farmer.name'},
                        { display: '批     次',name: 'batch.batchNumber'},
                        { display: '技术员',name: 'technician.name'},
                        { display: '开始日期',name: 'startDate'},
                        { display: '结束日期',name: 'endDate'}
              		],
                    url:'tech_batch!loadByPage'
             }
          
           var glist=$.pap.createFormGridList({form:form,grid:gridoption});
           grid=glist.getGrid();
        });

       
       
    </script>
</head>
<body>
</body>
</html>
