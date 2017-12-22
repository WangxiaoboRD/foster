<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ taglib prefix="s" uri="/struts-tags" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="content-type" content="text/html; charset=utf-8" />
	<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
	<meta content='width=330, height=400, initial-scale=1' name='viewport' />
	<link rel="icon" href="favicon.ico" type="image/x-icon" />
	<title>${initParam.title}</title>
	<link href="../ligerUI/ligerUI/skins/Aqua/css/ligerui-all.css" rel="stylesheet" type="text/css" />
	<link href="../ligerUI/ligerUI/skins/ligerui-icons.css" rel="stylesheet" type="text/css" />	
    <script src="../ligerUI/jquery/jquery-1.6.4.js" type="text/javascript"></script>
	<script src="../ligerUI/jquery-validation/jquery.form.js" type="text/javascript"></script> 
	<script src="../ligerUI/jquery-validation/jquery.validate.js" type="text/javascript"></script> 
    <script src="../ligerUI/jquery-validation/jquery.metadata.js" type="text/javascript"></script>
    <script src="../ligerUI/jquery-validation/messages_cn.js" type="text/javascript"></script>    
    <script src="../ligerUI/ligerUI/js/ligerui.all.js" type="text/javascript"></script>       
	<script type="text/javascript" src="../ligerUI/ligerUI/js/plugins/XDialog.js"></script>
	<script type="text/javascript" src="../ligerUI/ligerUI/js/plugins/ligerUtil.js"></script>
	<script src="../ligerUI/ligerUI/js/template/ligerTemplate.js" type="text/javascript"></script>
	<script src="../ligerUI/ligerUI/js/template/ligerXFormGridList.js" type="text/javascript"></script>
	<script type="text/javascript" src="../ligerUI/ligerUI/js/pap.js"></script>
	<script language="javascript" src="../original/Lodop6/LodopFuncs.js"></script>
	<script language="javascript" src="../original/Lodop6/PigStockTemplet.js"></script>
	<script src="../original/echarts-2.2.7/build/dist/echarts.js"></script>
	
    <script type="text/javascript">
        var grid = null;
        var form = null;
           $(function () {
        	   var groupicon = "../ligerUI/ligerUI/skins/icons/communication.gif";
                var toolBarOption = { 
   	                   items: [
                   			{ text: '查询条件', icon: 'search', click: function(){
                   				var display = $("form").css("display");
                   				if (display == 'none') {
	                   				$("form").css({ display: 'block' });
                   				}else {
                   					$("form").css({ display: 'none' });
                   				}
                   				grid.setHeight(grid.options.height);
                            }},
                            { line:true },
                            { text: '导出', icon: 'export', click: function() {
                            	$.exportFile($('form'), grid, 'death_bill_dtl!exportDeathAnalysis');
  					        }},
                  		]
             	};
                var formOption={
                    labelWidth: 80, 
                    inputWidth: 150,
	                fields:[
						{ display: "养殖公司", name:"e.deathBill.company.id", group: "养殖死亡报表", groupicon: groupicon,comboboxName: 'e.deathBill.company.name', newline: true, validate: { required: true },type:"select", options:{
						    url:'company!load',
						    valueField: 'id', 
						    textField: 'name',
							selectBoxHeight: 200,
							selectBoxWidth: 200,
							onSelected:function(nv){
								var _farm=liger.get("farmer");
								if(_farm)
									_farm.setValue("");
								var _batch=liger.get("batch");
						        if(_batch)
							        _batch.setValue("");
						        var _technician=liger.get("technician");
						        if(_technician)
							        _technician.setValue("");
					        }
						}},
						{ display: "代养户", name:"e.deathBill.farmer.id", comboboxName: 'farmer', type:"select", options:{
						    url:'farmer!loadByPinYin',
						    valueField: 'id', 
						    textField: 'name',
							selectBoxHeight: 200,
							selectBoxWidth: 200,
							 keySupport:true,
			                 triggerToLoad: true,
			                 autocomplete: true,
							onBeforeOpen: function() {
			                	var g = this;
			                	g.setParm('e.company.id', $("input[id='e.deathBill.company.id']").val());
			                },
							onSelected:function(nv){
						        var _batch=liger.get("batch");
						        if(_batch)
							        _batch.setValue("");
					        }
						}},
						{ display: "批次", name:"e.deathBill.batch.id", comboboxName: 'batch',newline: true,type:"select", options:{
						    url:'batch!loadByEntity',
						    valueField: 'id', 
						    textField: 'batchNumber',
							selectBoxHeight: 200,
							selectBoxWidth: 200,
							triggerToLoad: true,
							onBeforeOpen: function() {
			                	var g = this;
			                	g.setParm('e.company.id', $("input[id='e.deathBill.company.id']").val());
			                	g.setParm('e.farmer.id', $("input[id='e.deathBill.farmer.id']").val());
			                }
						}},
						{ display: "技术员", name:"e.deathBill.technician.id", comboboxName: 'technician',type:"select", options:{
						    url:'technician!loadByEntity',
						    valueField: 'id', 
						    textField: 'name',
							selectBoxHeight: 200,
							selectBoxWidth: 200,
							triggerToLoad: true,
							onBeforeOpen: function() {
			                	var g = this;
			                	g.setParm('e.company.id', $("input[id='e.deathBill.company.id']").val());
			                }
						}},
						{ display: "回执状态",name: "e.deathBill.isReceipt",newline: true,type: "select", options:{
							  keySupport:true,
	   	                	  data: [
	   	                       	{ text: '未回执', id: 'N' },
	   	               	  		{ text: '已回执', id: 'Y' }
	   		                 ]
	               		}},
	               		{ display: "死亡时间", newline: true,name: "e.tempStack.startTime", type: "date", space: 10 },
		                { display: "至", type: "label", space: 10 },
		                { hideLabel: true, name: "e.tempStack.endTime", type: "date"}
						
				    ],
					 buttons:[
	                    { text:'查询', icon: '../ligerUI/ligerUI/skins/icons/search2.gif', width: 70, newline: true, click:function(item){
	                    	var arra = $('form').formToArray();
	                    	if(grid){
	                    		grid.setParms(arra);
	                    		grid.options.newPage=1;
			           		    grid.loadData(true);
			           		 	$("form").css( { display: 'none' });
			           		 	grid.setHeight(grid.options.height);
	                    	}
	                    }}
	                 ]
               	}
           		var columns=[
                       { display: '养殖公司',name:'company'},	
                       { display: '区域',name:'feedFac'},
                       { display: '日期',name:'deathDate'},
                       { display: '代养户',name:'farmer'},
                       { display: '批次',name:'batch'},
                       { display: '数量',name:'quantity',align:"right"},
                       { display: '重量',name:'weight',align:"right",render: function(data){
	                  	   if(data.weight){
							   value = (data.weight*1);
							   return value.toFixed(2);
						   }
	                   }},
                       { display: '死亡归属',name:'deathAffiliation'},
                       { display: '死亡原因',name:'reason'},
                       { display: '死因类别',name:'reasonType'},
                       { display: '急慢性',name:'acuteChronic'},
                       { display: '技术员',name:'technician'},
                       { display: '回执状态',name:'receipt'}
                       
           	         ];
             	var gridoption={
                    title: '死亡报表',
             		url:'death_bill_dtl!loadDeathAnalysisByPage',
                    columns: columns,
                    checkbox: false,
                    usePager: true,
                    allowHideColumn: false // 允许隐藏列
                }
              var glist=$.pap.createFormGridList({ toolbar:toolBarOption,form:formOption,grid:gridoption});
              grid=glist.getGrid();
              form = glist.getForm();
        });
    	
    </script>
 
</head>
<body>
</body>
</html>
