<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
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
	
    <script type="text/javascript">
    	var toolbar;
    	var form;
    	var griddetail;
    	var gridall;
        
           $(function () {
        	   var groupicon = "../ligerUI/ligerUI/skins/icons/communication.gif";
        	   toolbar =$("#toolbar").ligerToolBar({ 
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
                            	$.exportFile($('form'), null, 'batch!exportCurrentBatch');
  					        }}
                  		]
             	});
        	   form=$("form").ligerForm({
                    labelWidth: 80, 
                    inputWidth: 150,
	                fields:[
						{ display: "查询日期", name: "e.registDate",group: "代养进度追踪报表", groupicon: groupicon, type: "date",validate: { required: true },options: { value: new Date(), showTime: false }},    
						{ display: "选择公司", name:"e.company.id",newline: true, comboboxName: 'e.company.name',validate: { required: true },  type:"select", options:{
						    url:'company!load',
						    valueField: 'id', 
						    textField: 'name',
							selectBoxHeight: 150,
							onSelected: function (nv){
								var _farmer = liger.get("farmer");
								if(_farmer)
									_farmer.setValue("");
							} 
						 }},
					     { display: "代养农户", name:"e.farmer.id",comboboxName: 'farmer',newline: true, type:"select", options:{
							 url: 'farmer!loadByPinYin',
			                 valueField: 'id',
			                 textField: 'name', 
			                 selectBoxHeight: 180,
			                 keySupport:true,
			                 triggerToLoad: true,
			                 autocomplete: true,
			                 onBeforeOpen: function() {
			                	var g = this;
			                	g.setParm('e.company.id', $("input[id='e.company.id']").val());
			                 }
						}}
				    ],
					buttons:[
	                    { text:'查询', icon: '../ligerUI/ligerUI/skins/icons/search2.gif', width: 70, newline: true, click:function(item){
	                    	var arra = $('form').formToArray();
	                    	if(griddetail){
	                    		griddetail.setParms(arra);
	                    		griddetail.options.newPage=1;
	                    		griddetail.loadData(true);
	                    	}
	                    	if(gridall){
	                    		gridall.setParms(arra);
	                    		gridall.options.newPage=1;
	                    		gridall.loadData(true);
	                    	}
	                    	$("form").css( { display: 'none' });
	                    }}
	                 ]
               	});

				var columns=[
		             { display: '区域', name: 'area',frozen: true },
		             { display: '代养户', name: 'farmerName',frozen: true },
		             { display: '批次号', name: 'batchNumber',frozen: true },
		             { display: '进猪时间', name: 'inDate',width:100},
		             { display: '预计出栏日期', name: 'outDate',width:100},
		             { display: '进猪数', name: 'pigletQuan',align:'right',render: function(data){
	                  	   if(data.pigletQuan){
							   value = (data.pigletQuan*1);
							   return value.toFixed(0);
						   }
	                 }},
		             { display: '当日死亡', name: 'currentDeadNum',align:'right',render: function(data){
	                  	   if(data.currentDeadNum){
							   value = (data.currentDeadNum*1);
							   return value.toFixed(0);
						   }
	                 }},
	                 { display: '免责死亡', name: 'otherDeathQuan',align:'right',render: function(data){
	                  	   if(data.otherDeathQuan){
							   value = (data.otherDeathQuan*1);
							   return value.toFixed(0);
						   }
	                 }},
		             { display: '累计死亡', name: 'deathQuan',align:'right',render: function(data){
	                  	   if(data.deathQuan){
							   value = (data.deathQuan*1);
							   return value.toFixed(0);
						   }
	                 }},
                     { display: '出栏总数',name: 'saleQuan',align:'right',render: function(data){
	                  	   if(data.saleQuan){
							   value = (data.saleQuan*1);
							   return value.toFixed(0);
						   }
	                 }},
                     { display: '淘汰总数', name: 'eliminateQuan',align:'right',render: function(data){
	                  	   if(data.eliminateQuan!=null){
							   value = (data.eliminateQuan*1);
							   return value.toFixed(0);
						   }
	                 }},
                     { display: '库存总数', name: 'quantity',align:'right',render: function(data){
	                  	   if(data.quantity!=null){
							   value = (data.quantity*1);
							   return value.toFixed(0);
						   }
	                 }},
	                 { display: '死淘率', name: 'deadRate'},
                     { display: '备注', name: 'deadReason'},
                     { display: '当月耗料量（吨）',width:120, name: 'currentFeed',align:'right',render: function(data){
	                  	   if(data.currentFeed!=null){
							   value = ((data.currentFeed*1)/1000);
							   return value.toFixed(3);
						   }
	                 }},
	                 { display: '当月领料量（吨）',width:120, name: 'currentInFeed',align:'right',render: function(data){
	                  	   if(data.currentInFeed!=null){
							   value = ((data.currentInFeed*1)/1000);
							   return value.toFixed(3);
						   }
	                 }},
                     { display: '开发员', name: 'developMan.name'},
                     { display: '技术员', name: 'technician.name'},
                     { display: '猪苗来源', name: 'pigSource',width:100}
				];

		      var columns2=[
		             { display: '区域', name: 'area'},
		             { display: '代养户数', name: 'farmerName',sort:false,frozen: true, totalSummary:{
		                        render: function (suminf, column, cell){
		                        return '<div>合计:</div>';
		                    }
					 }},
		             { display: '进猪数', name: 'pigletQuan',align:'right',render: function(data){
		                   if(data.pigletQuan){
							   value = (data.pigletQuan*1);
							   return value.toFixed(0);
						   }},
						   totalSummary:{
	                        render: function (suminf, column, cell){
		                        return '<div>'+(suminf.sum).toFixed(0) + '</div>';
		                    }
					 }},
		             { display: '库存总数', name: 'quantity',align:'right',render: function(data){
		                   if(data.quantity!=null){
							   value = (data.quantity*1);
							   return value.toFixed(0);
						   }},
						   totalSummary:{
		                        render: function (suminf, column, cell){
		                        return '<div>'+(suminf.sum).toFixed(0) + '</div>';
		                   }}
					 },
		             { display: '出栏总数',name: 'saleQuan',align:'right',render: function(data){
	                 	   if(data.saleQuan){
						       value = (data.saleQuan*1);
						       return value.toFixed(0);
					       }},
					       totalSummary:{
		                        render: function (suminf, column, cell){
		                        return '<div>'+(suminf.sum).toFixed(0) + '</div>';
		                   }}
		             },
	                 { display: '淘汰总数', name: 'eliminateQuan',align:'right',render: function(data){
	                 	   if(data.eliminateQuan!=null){
						       value = (data.eliminateQuan*1);
						       return value.toFixed(0);
					       }},
					       totalSummary:{
		                        render: function (suminf, column, cell){
		                        return '<div>'+(suminf.sum).toFixed(0) + '</div>';
		                   }}
				     },
	                 { display: '成活率', name: 'deadRate'}
	                  
				];
		      griddetail=$("#griddetail").ligerGrid({
                    title: '代养进度追踪报表',
             		url:'batch!loadCurrentBatchByPage',
                    columns: columns,
                    checkbox: false,
                    usePager: true,
                    //enabledSort:true,
                    allowHideColumn: false, // 允许隐藏列
	         		delayLoad:true,
	                rownumbers:true,
	                pageSize: 15,                           
	       			pageSizeOptions: [15, 35, 50],
	       		    pageParmName:'pageBean.pageNo', //当前页数
					pagesizeParmName:'pageBean.pageSize',//每页条数
	                width: '99.8%'
                });
		      gridall=$("#gridall").ligerGrid({
                 title: '汇总信息',
           		 url:'batch!loadAllByFarmer',
                 columns: columns2,
                 allowHideColumn: false, // 允许隐藏列
                 rownumbers:true,
                 delayLoad:true,
                 usePager: false,
                 checkbox: false,
                 width: '99.8%'
             });

             
        });
    	
    </script>
 
</head>
<body>
<div id="toolbar" style='height:23px;' ></div>
<form></form>
<div id="griddetail" style="display:block"></div>
<div id="gridall" style="display:block"></div>
</body>
</html>
