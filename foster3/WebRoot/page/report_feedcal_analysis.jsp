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
                            	$.exportFile($('form'), grid, 'feed_bill_dtl!exportFeedCalAnalysis');
  					        }},
                  		]
             	};
                var formOption={
                    labelWidth: 80, 
                    inputWidth: 150,
	                fields:[
	                    { display: "查询时间", name: "e.feedBill.registDate", type: "date", group: "饲料耗用分析报表", groupicon: groupicon,validate: { required: true },newline:true,options: { value: new Date(), showTime: false }},
						{ display: "养殖公司", name:"e.feedBill.company.id",comboboxName: 'e.feedBill.company.name',  validate: { required: true },type:"select", options:{
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
						{ display: "代养户", name:"e.feedBill.farmer.id", comboboxName: 'farmer', type:"select",newline: true, options:{
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
			                	g.setParm('e.company.id', $("input[id='e.feedBill.company.id']").val());
			                },
							onSelected:function(nv){
						        var _batch=liger.get("batch");
						        if(_batch)
							        _batch.setValue("");
							    var _technician=liger.get("technician");
						        if(this.selectedText!='' && _technician.selectedText!='')
							        _technician.setValue("");
					        }
						}},
						{ display: "批次", name:"e.feedBill.batch.id", comboboxName: 'batch',type:"select", options:{
						    url:'batch!loadByEntity',
						    valueField: 'id', 
						    textField: 'batchNumber',
							selectBoxHeight: 200,
							selectBoxWidth: 200,
							triggerToLoad: true,
							onBeforeOpen: function() {
			                	var g = this;
			                	g.setParm('e.company.id', $("input[id='e.feedBill.company.id']").val());
			                	g.setParm('e.farmer.id', $("input[id='e.feedBill.farmer.id']").val());
			                }
						}},
						{ display: "技术员", name:"e.feedBill.technician.id", comboboxName: 'technician',type:"select",newline: true, options:{
						    url:'technician!loadByName',
						    valueField: 'id', 
						    textField: 'name',
							selectBoxHeight: 200,
							selectBoxWidth: 200,
							keySupport:true,
							triggerToLoad: true,
							autocomplete: true,
							onBeforeOpen: function() {
			                	var g = this;
			                	g.setParm('e.company.id', $("input[id='e.feedBill.company.id']").val());
			                },
			                onSelected:function(nv){
			                	var _farm=liger.get("farmer");
								if(this.selectedText!='' && _farm.selectedText!='')
									_farm.setValue("");
					        }
						}}
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
                       /*{ display: '养殖公司',name:'company'},*/
                       { display: '饲料厂',name:'feedFac'},
                       { display: '代养户',name:'farmer'},
                       { display: '批次',name:'batch'},
                       { display: '技术员',name:'technician'},
                       { display: '当日存栏(头)',name:'pigNum',align:"right",render: function(data){
	                  	   if(data.pigNum){
							   value = (data.pigNum*1);
							   return value.toFixed(0);
						   }
	                   }},
                       { display: '当日喂料(kg)',name:'dQty',align:"right",render: function(data){
	                  	   if(data.dQty){
							   value = (data.dQty*1);
							   return value.toFixed(2);
						   }
	                   }},
                       { display: '当日标准(kg)',name:'dayStd',align:"right",render: function(data){
	                  	   if(data.dayStd){
							   value = (data.dayStd*1);
							   return value.toFixed(2);
						   }
	                   }},
                       { display: '当日差值(kg)',name:'dayDif',align:"right",render: function(data){
	                  	   if(data.dayDif){
							   value = (data.dayDif*1);
							   return value.toFixed(2);
						   }
	                   }},
                       { display: '本日达成',name:'dayDifPro',align:"right",render:function(data){
	                       if(data.dayDifPro){
							   return data.dayDifPro+"%";
	                       }
                       }},
                       { display: '累积喂料(吨)',name:'tolQty',align:"right",render: function(data){
	                  	   if(data.tolQty){
							   value = (data.tolQty*1);
							   return value.toFixed(2);
						   }
	                   }},
                       { display: '累积标准(吨)',name:'stdtq',align:"right",render: function(data){
	                  	   if(data.stdtq){
							   value = (data.stdtq*1);
							   return value.toFixed(2);
						   }
	                   }},
                       { display: '累积差值(吨)',name:'tolDif',align:"right",render: function(data){
	                  	   if(data.tolDif){
							   value = (data.tolDif*1);
							   return value.toFixed(2);
						   }
	                   }},
                       { display: '累积达成',name:'difPro',align:"right",render:function(data){
	                       if(data.difPro){
							   return data.difPro+"%";
	                       }
                       }},
                       { display: '当日库存(吨)',name:'stock',align:"right",render: function(data){
	                  	   if(data.stock){
							   value = (data.stock*1);
							   return value.toFixed(2);
						   }
	                   }},
	                   { display: '合同头数',name:'contQuan',align:"right"},
	                   { display: '进猪日期',name:'piginDate',width:120},
           	         ];
             	var gridoption={
                    title: '喂料分析报表',
             		url:'feed_bill_dtl!loadFeedCalAnalysisByPage',
                    columns: columns,
                    checkbox: false,
                    usePager: false,
                    enabledSort:true,
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
