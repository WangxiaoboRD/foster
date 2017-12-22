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
	<script type="text/javascript" src="../ligerUI/ligerUI/js/plugins/ligerUtil.js"></script>
	<script language="javascript" src="../original/Lodop6/LodopFuncs.js"></script>
	<script language="javascript" src="../original/Lodop6/FarmerBillTemplet.js"></script>

    <script type="text/javascript">
    	// 表格
        var grid = null;
        $(function () {
        	   var groupicon = "../ligerUI/ligerUI/skins/icons/search2.gif";
        	   var toolBarOption = { 
   	                   items: [
      		                { text: '费用调整', icon: 'adjust' ,expression:'!=1', disabled:true, click: function(item) {
      		                	var selectedRow = item.selectGrid.selected;
            	   	    		if (selectedRow.length == 0) {
				   	           		$.ligerDialog.warn('请选择要操作的记录！');
				   	           		return;
			   	   	          	}
           	   	    			$.ligerDialog.open({
           							title:'费用调整',
           							height: 380,
                   	   				width: 580,  
           							url: 'farmer_bill!loadJustById?id='+grid.selected[0]['id'],
           							buttons: [
           				                {text:'确定',onclick: function(item, dialog) {
           				                	var data = dialog.frame.onSave();
              				       	    	if(data!=null){
              				       				$.ligerui.ligerAjax({
              				       					url:"farmer_bill!modify",
              				       					dataType:"text",
              				       					data:data,
              				       					success:function(_data,textStatus){
              				       						if(_data == 'MODIFYOK'){
              				       							tip = $.ligerDialog.tip({ title: '提示信息', content: '成功更新一条记录！' });
              				       							window.setTimeout(function(){ tip.close()} ,2000); 
              				       							dialog.close();	
              				       						}
              				       					},
              				       					error: function(XMLHttpRequest,textStatus){
              				       					$.ligerDialog.error('操作出现异常');
              				       					},
              				       					complete: function(){ grid.loadData(true);}
              				       				});
              				       				
              				       				
              				           		}
               				            }},
           				                {text:'取消',onclick: function(item, dialog) {
               				            	dialog.close();
               				            }}
           		        			]
           		        		});
          		            }},
          		            { line:true },
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
    		   	    				tabid: 'viewFarmerBill'+grid.selected[0]['id'],
    	   	   	    				text: '账单查看【'+grid.selected[0]['id']+'】',
    	       	   					params:{ 'ids':ids,'id':grid.selected[0]['id']},
    	   	   	    				url: 'farmer_bill!loadDetailById?id='+grid.selected[0]['id']
    	   	   	    			});
       	   			         }},
       	   			         { line:true },
       	   			         { text: '打印预览', icon: 'preview1', expression:'==0', disabled:true, click: function(item){
	       	   			        var selectedRow = item.selectGrid.selected; 
		   	           		  	if (selectedRow.length == 0) {
		   	           				$.ligerDialog.warn('请选择要打印的单据！');
		   	           			 	return;
			   	   	          	}
                                var delIds = "";
 		                		var flag=false;
 	   	          				$(grid.selected).each(function(i, item){
 	   	          					delIds += item['id']+",";
 	   	          				});
	 	   	          			$.ligerui.ligerAjax({
		  							type: "POST",
		  							async:  false,
		  							url: "farmer_bill!print",
		  							data: { ids: delIds},
		  							dataType: "json",
		  							success: function(result, status){
		  								if(result != ""){
		  									var objArray = result.Rows;
		  									var iCurLine = 0;
		  									for(var i in objArray){
		  										var obj = objArray[i];
		  										if(obj != null){
		  											var funcName = "FarmerBIll";
		  											window[funcName](obj,0);
				  									LODOP.PRINT_DESIGN();
		  										}
		  									}
		  								}	
		  							},
		  							error: function(XMLHttpRequest,status){
		  								$.ligerDialog.error('操作出现异常');
		  							}
		  					  	});    
 					         }},
					         { line:true },
					         { text: '打印', icon: 'printer',expression:'!=1', click: function(item){
						        	var arra = $('form').formToArray();
						        	var selectedRow = item.selectGrid.selected; 
		 	   	           		  	if (selectedRow.length == 0) {
		 	   	           				$.ligerDialog.warn('请选择要打印的单据！');
		 	   	           			 	return;
				   	   	          	}
							        var selData = selectedRow[0];
						        	LODOP = getLodop();
				      	   	       	$.ligerui.ligerAjax({
			 							type: "POST",
			 							async:  false,
			 							url: "drug_bill!print",
			 							data: { id: selData['id'] },
			 							dataType: "json",
			 							success: function(result, status){
			 								if(result != ""){
			 									var obj = result.Rows;
			 									var iCurLine = 0;
			 									var funcName = "DRUG_BILL_TEMPLET";
				  								window[funcName](obj,iCurLine);
				  								LODOP.PRINT();
			 								}	
			 							},
			 							error: function(XMLHttpRequest,status){
			 								$.ligerDialog.error('操作出现异常');
			 							}
			 					   });  			  	 			
						    }} 
   	   	              ]
             	};

        	 form = {
                	 labelWidth: 70,
                     fields:[
                         { display: "养殖公司", name: "e.company.id",type: "select",
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
						{ display: '代养账单', name: 'id',width:80 },
                        { display: '养殖公司', name: 'company.name',width:130 },
                        { display: '代养农户', name: 'farmer.name',width:100 },
                        { display: '所属批次', name: 'batch.batchNumber',width:90},
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
	    				{ display: '奖罚费用(元)', name: 'rewardCost',width:90,align:'right',render: function(data){
		                  	   if(data.rewardCost){
								   value = (data.rewardCost*1);
								   return value.toFixed(2);
							   }
		                }},
	    				{ display: '调整费用(元)', name: 'adjustCost',width:90,align:'right',render: function(data){
		                  	   if(data.adjustCost){
								   value = (data.adjustCost*1);
								   return value.toFixed(2);
							   }
		                }},
	    				{ display: '调整人', name: 'adjustMen' },
	    				{ display: '调整原因', name: 'adjustReason',width:180 },
	    				{ display: '总投入费(元)', name: 'allBreedCost',width:90,align:'right',render: function(data){
		                  	   if(data.allBreedCost){
								   value = (data.allBreedCost*1);
								   return value.toFixed(2);
							   }
		                }},
	    				{ display: '料肉比', name: 'fcr',width:50 },
	    				{ display: '代养费(元)', name: 'farmerCost',width:90,align:'right',render: function(data){
		                  	   if(data.farmerCost){
								   value = (data.farmerCost*1);
								   return value.toFixed(2);
							   }
		                }}
              		],
                    url:'farmer_bill!loadByPage',
                    exportBtn: true,
			        exportUrl: 'farmer_bill!export'
             }
          
           var glist = $.pap.createFormGridList({ toolbar:toolBarOption,form:form,grid:gridoption});
           grid = glist.getGrid();
        });
        
    </script>
</head>
<body>
</body>
</html>
