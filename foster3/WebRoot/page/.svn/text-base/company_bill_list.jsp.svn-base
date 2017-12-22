<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<html>
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
	<script type="text/javascript" src="../ligerUI/ligerUI/js/plugins/ligerUtil.js"></script>

    <script type="text/javascript">
    	// 表格
        var grid = null;
        $(function () {
        	   var groupicon = "../ligerUI/ligerUI/skins/icons/search2.gif";
        	   var toolBarOption = { 
   	                   items: [
					  		{ text: '查看', icon: 'view', expression:'!=1', disabled:true, click: function(item){
					 		 		var selectedRow = item.selectGrid.selected; 
					         		if (selectedRow.length == 0) {
						     			$.ligerDialog.warn('请选择要查看的记录！');
						     			return;
						        	}
							        var ids = new Array()
							        var datas=grid.rows;
							        for(var i=0;i<datas.length;i++){
										ids[i]=datas[i]['id'];
									}
								    $.pap.addTabItem({ 
									    tabid: 'viewCompanyBill'+grid.selected[0]['id'],
										text: '公司账单【'+grid.selected[0]['id']+'】',
										params:{ 'ids':ids,'id':grid.selected[0]['id']},
										url: 'company_bill!loadDetailById?id='+grid.selected[0]['id']
								    });
					         }}
   	   	                 ]
             	};
        	 form = {
                	 labelWidth: 70,
                     fields:[
                         { display: "养殖公司", name: "e.company.id", type: "select",
			                  	options:{
			                  	    url:'company!load',
			                  		selectBoxHeight: 150,
			                  		keySupport:true, 
			  					    valueField: 'id', 
			  					    textField: 'name',
			  					  	onSelected:function(nv){
     									var _farmer=liger.get("farmer");
			     						if(_farmer)
			     							_farmer.setValue("");
     					 			}
			             }},
			             { display: "代养户", name:"e.farmer.id",comboboxName: 'farmer', type:"select", options:{
							    url:'farmer!loadByPinYin',
							    valueField: 'id', 
						        textField: 'name',
						        keySupport:true,
						        autocomplete: true,
								triggerToLoad: true,
								onBeforeOpen: function() {
				                	var g = this;
				                	g.setParm('e.company.id', $("input[id='e.company.id']").val());
		            			},
		            			onSelected:function(nv){
 									var _batch=liger.get("batch");
		     						if(_batch)
		     							_batch.setValue("");
 					 			}
							}},
							{ display: "批次", name:"e.batch.id",comboboxName: 'batch', type:"select", options:{
							    url:'batch!loadByEntity',
							    valueField: 'id', 
						        textField: 'batchNumber',
						        keySupport:true,
						        autocomplete: true,
								triggerToLoad: true,
								onBeforeOpen: function() {
				                	var g = this;
				                	g.setParm('e.farmer.id', $("input[id='e.farmer.id']").val());
		            			}
							}},
							{ display: "账单日期", name: "e.registDate", type: "date"}
			             
					    ]
                	}

        	 var gridoption={
                     columns:[
						{ display: '账单编码', name: 'id',width:80},
                        { display: '养殖公司', name: 'company.name',width:130 },
                        { display: '代养农户', name: 'farmer.name',width:120 },
                        { display: '所属批次', name: 'batch.batchNumber',width:80 },
	    				{ display: '账单日期', name: 'registDate',width:90 },
	    				{ display: '销售收入(元)', name: 'saleIncome',width:90,align:'right',render: function(data){
		                  	   if(data.saleIncome){
								   value = (data.saleIncome*1);
								   return value.toFixed(2);
							   }
		                }},
	    				{ display: '饲料费用(元)', name: 'feedCost',width:90,align:'right',render: function(data){
		                  	   if(data.feedCost){
								   value = (data.feedCost*1);
								   return value.toFixed(2);
							   }
		                }},
	    				{ display: '药品费用(元)', name: 'drugsCost',width:90,align:'right',render: function(data){
		                  	   if(data.drugsCost){
								   value = (data.drugsCost*1);
								   return value.toFixed(2);
							   }
		                }},
	    				{ display: '物料费用(元)', name: 'otherCost',width:90,align:'right',render: function(data){
		                  	   if(data.otherCost){
								   value = (data.otherCost*1);
								   return value.toFixed(2);
							   }
		                }},
	    				{ display: '猪苗费用(元)', name: 'pigletCost',width:90,align:'right',render: function(data){
		                  	   if(data.pigletCost){
								   value = (data.pigletCost*1);
								   return value.toFixed(2);
							   }
		                }},
	    				{ display: '采购运费(元)', name: 'freight',width:90,align:'right',render: function(data){
		                  	   if(data.freight){
								   value = (data.freight*1);
								   return value.toFixed(2);
							   }
		                }},
	    				{ display: '销售运费(元)', name: 'salefreight',width:90,align:'right',render: function(data){
		                  	   if(data.salefreight){
								   value = (data.salefreight*1);
								   return value.toFixed(2);
							   }
		                }},
	    				{ display: '代养费用(元)', name: 'farmerCost',width:90,align:'right',render: function(data){
		                  	   if(data.farmerCost){
								   value = (data.farmerCost*1);
								   return value.toFixed(2);
							   }
		                }},
	    				{ display: '总成本(元)', name: 'allCost',width:90,align:'right',render: function(data){
		                  	   if(data.allCost){
								   value = (data.allCost*1);
								   return value.toFixed(2);
							   }
		                }},
	    				{ display: '头均成本(元)', name: 'avgCost',width:90,align:'right',render: function(data){
		                  	   if(data.avgCost){
								   value = (data.avgCost*1);
								   return value.toFixed(2);
							   }
		                }},
	    				{ display: '营收毛利(元)', name: 'profit',width:90,align:'right',render: function(data){
		                  	   if(data.profit){
								   value = (data.profit*1);
								   return value.toFixed(2);
							   }
		                }},
	    				{ display: '头均毛利(元)', name: 'avgProfit',width:90,align:'right',render: function(data){
		                  	   if(data.avgProfit){
								   value = (data.avgProfit*1);
								   return value.toFixed(2);
							   }
		                }}
              		],
                    url:'company_bill!loadByPage',
                    exportBtn: true,
			        exportUrl: 'company_bill!export'
             }
          
           var glist = $.pap.createFormGridList({ toolbar:toolBarOption,form:form,grid:gridoption});
           grid = glist.getGrid();
        });
        
    </script>
</head>
<body>
</body>
</html>
