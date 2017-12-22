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

    <script type="text/javascript">
    	// 表格
        var grid = null;
        $(function () {
			   // 工具条
        	   var toolBarOption = { 
   	                   items: [
	   	                  { text: '新增', icon: 'add', click: function() {
	   	   	            	  $.pap.addTabItem({ 
	   	   	      			  	 text: '代养销售新增',
	   	   	      			 	 url: 'farmer_sale_add.jsp'
	   	   	       			  });
	   	   	              }},
	   	   	              { line:true },
	   	   	              { text: '修改', icon: 'modify', expression:'!=1', disabled:true, click: function(item){
		   	   	            	var selectedRow = item.selectGrid.selected; 
	  	   	           		 	if (selectedRow.length == 0) {
		  	   	           			$.ligerDialog.warn('请选择要操作的记录！');
		  	   	           			return;
				   	   	        }
				   	   	        var selData = selectedRow[0];
				   	   	        if (selData['checkStatus'] == '1') {
				   	   	       		$.ligerDialog.warn('所选单据已审核，不允许修改！');
			   	           			return;
					   	   	    }
							  	$.pap.addTabItem({ 
			   	    	        	tabid: 'farmerSaleModify'+grid.selected[0]['id'],
				   	    	        text: '销售单修改', 
				   	    	        url: 'farmer_sale!loadUpdateById?id='+grid.selected[0]['id']
				   	    	    });
	   	   			      }},
	   	   	              { line:true },
	   	   	              { text: '删除', icon: 'delete', expression:'==0', disabled:true, click: function(){
	   	   	            	  if( grid.selected.length > 0 ){
   	   	          				var delIds = "";
  	   	          				var status = false;
   	   	          				$(grid.selected).each(function(i, item){
   	   	          					delIds += item['id']+",";
		   	   	          			$(item['checkStates']).each(function(i, data){
										  if (data['checkState']) {
											  status = true;
											  return false;
										  }
									  });
									 if (status) {
										 return false;
									 }
   	   	          				});
   	   	          				if (status) {
		   	   	          			$.ligerDialog.warn('所选记录包含有已审核记录，不允许删除！');
									return;
   	   	   	          			}
	   	   	      	    		$.ligerDialog.confirm('数据删除后不可恢复，确认要删除吗？', function(data) {
	   	   	          				if(data){
	   	   	          					$.ligerui.ligerAjax({
	   	   	      							type: "POST",
	   	   	      							async:  false,
	   	   	      							url: "farmer_sale!delete",
	   	   	      							data: { ids: delIds },
	   	   	      							dataType: "text",
	   	   	      							success: function(result, status){
	   	   	      								if(result != ""){
	   	   	      									tip = $.ligerDialog.tip({ title: '提示信息', content: result + '条记录被删除！' });
	   	   	      									window.setTimeout(function(){ tip.close()} ,2000); 
	   	   	      								}	
	   	   	      							},
	   	   	      							error: function(XMLHttpRequest,status){
	   	   	      								$.ligerDialog.error('操作出现异常');
	   	   	      							},
	   	   	      							complete:function(){
	   	   	      								grid.loadData(true);
	   	   	      							}
	   	   	      					   	});    			
	   	   	      	    			}
	   	   	          			});    			
	   	   	      	    	}else {
	   	   	      	    		$.ligerDialog.warn('请选择要删除的记录！')
	   	   	      	    	}
	   	   			      }},
		   	   			  { line:true },
	 	   				  { text: '审核', icon: 'memeber', expression:'==0', disabled: true, click: function(item){
		   	           		  var selectedRow = item.selectGrid.selected; 
		   	           		  if (selectedRow.length == 0) {
		   	           			 $.ligerDialog.warn('请选择要操作的记录！');
		   	           			 return;
			   	   	          }
		   	           		  var flag = false;
			     			  var ids = "";
		     				  $(grid.selected).each(function(i, item){
		     						ids += item['id']+",";
		     						if (item['checkStatus'] == '1') {
		     							flag = true;
		         					}
		     				  });
							  if (flag) {
								$.ligerDialog.warn('所选单据包含有【已审核】的记录，不允许审核！');
								return;
							  }
			   	   	          $.ligerDialog.confirm('审核后将不允许进行修改或删除，确认要审核吗？', function(data) {
		   	          				if(data){
		 	   	          				$.ligerui.ligerAjax({
	 		      							type: "POST",
	 		      							async:  false,
	 		      							url: "farmer_sale!check",
	 		      							data: { ids: ids },
	 		      							dataType: "text",
	 		      							success: function(result, status){
	 		      								if(result != ""){
	 		      									tip = $.ligerDialog.tip({ title: '提示信息', content: result + '条记录被成功审核！' });
	 		      									window.setTimeout(function(){ tip.close()} ,2000); 
	 		      								}	
	 		      							},
	 		      							error: function(XMLHttpRequest,status){
	 		      								$.ligerDialog.error('操作出现异常');
	 		      							},
	 		      							complete:function(){
	 		      								grid.loadData(true);
	 		      							}
	 		      					   	});    			
		   	      	    			}
		   	   	          	  });   
		   			      }},
		   				  { line:true },
	 	   			      { text: '撤销', icon: 'back', expression:'!=1', disabled: true, click: function(item){
	 	   			  	 		var selectedRow = item.selectGrid.selected; 
	   	   	           		  	if (selectedRow.length == 0) {
	   	   	           				$.ligerDialog.warn('请选择要操作的记录！');
	   	   	           			 	return;
			   	   	          	}
	  	   	   	          		if(grid.selected[0]['checkStatus']=='0'){
    	   	    			 		$.ligerDialog.warn('该单据还未审核！');
    	   	    			 		return;
    	   	    		  		}
			  	   	   	        if(grid.selected[0]['isBalance']=='Y'){
		    			 			$.ligerDialog.warn('该单据已经结算不允许撤销！');
		    			 			return;
		    		  			}
			   	   	            $.ligerDialog.confirm('确认要撤销吗?', function(data) {
			   	   	          		var id = grid.selected[0]['id'];
	 	   	          				if(data){
		 	   	          				$.ligerui.ligerAjax({
	   		      							type: "POST",
	   		      							async:  false,
	   		      							url: "farmer_sale!cancelCheck",
	   		      							data: { id: id },
	   		      							dataType: "text",
	   		      							success: function(result, status){
	   		      								if(result != ""){
	   		      									tip = $.ligerDialog.tip({ title: '提示信息', content: result + '条记录被成功撤销！' });
	   		      									window.setTimeout(function(){ tip.close()} ,2000); 
	   		      								}	
	   		      							},
	   		      							error: function(XMLHttpRequest,status){
	   		      								$.ligerDialog.error('操作出现异常');
	   		      							},
	   		      							complete:function(){
	   		      								grid.loadData(true);
	   		      							}
	   		      					   	});     			
	 	   	      	    			}
		   	   	          	  });   
	 	   			      }},
	   	   	              { line:true },
	   	   	              { text: '查看', icon: 'view', expression:'!=1', disabled:true, click: function(item){
	   	   	           		 	var selectedRow = item.selectGrid.selected ; 
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
			   	    				tabid: 'FarmerSaleOrder'+grid.selected[0]['id'],
		   	   	    				text: '代养销售【'+grid.selected[0]['id']+'】',
		       	   					params:{ 'ids':ids,'id':grid.selected[0]['id']},
		   	   	    				url: 'farmer_sale!loadDetailById?id='+grid.selected[0]['id']
		   	   	    			});
	   	   			      }},
                   	  ]
             	};

        	 form = {
                 labelWidth: 60,
                 space: 20,
                 fields:[
						{ display: "销售编码", name: "e.id", type: "text" },
						{ display: "养殖公司", name:"e.company.id",type:"select",options:{
						    url:'company!loadByEntity',
						    valueField: 'id', //关键项
							textField: 'name',
							keySupport:true,
							selectBoxHeight: 150,
							onSelected: function (nv){
								var _farmer = liger.get("farmer");
								if(_farmer)
									_farmer.setValue("");
								var _batch = liger.get("batch");
								if(_batch)
									_batch.setValue("");
                    		}
						}},
						{ display: "代养农户", name:"e.farmer.id",comboboxName: 'farmer', type:"select",options:{
							url: 'farmer!loadByPinYin',
			                valueField: 'id',
			                textField: 'name', 
			                autocomplete: true,
			                keySupport:true,
			                selectBoxHeight: 180,
			                triggerToLoad: true,
			                onBeforeOpen: function() {
			                	var g = this;
			                	g.setParm('e.company.id', $("input[id='e.company.id']").val());
			                },
			                onSelected: function (nv){
								var _batch = liger.get("batch");
								if(_batch)
									_batch.setValue("");
                    		}
						}},
						{ display: "所属批次", name:"e.batch.id",comboboxName: 'batch', type:"select",options:{
							url: 'batch!loadByEntity',
			                valueField: 'id',
			                textField: 'batchNumber', 
			                selectBoxHeight: 180,
			                triggerToLoad: true,
			                onBeforeOpen: function() {
			                	var g = this;
			                	g.setParm('e.farmer.id', $("input[id='e.farmer.id']").val());
			                }
						}},
						{ display: "审核状态",newline:true,name: "e.checkStatus", type: "select", options:{
							  keySupport:true,
	   	                	  data: [
	   	                       	{ text: '未审核', id: '0' },
	   	               	  		{ text: '已审核', id: '1' }
	   		                 ],
	   		              	value:0
	   		                 //empx: true
	               		}},
	        	 	 	{ display: "签订日期", name: "e.tempStack.startTime", type: "date", space: 10,options: {format : "yyyy-MM-dd"}},
		                { display: "至", type: "label", space: 10 },
		                { hideLabel: true, name: "e.tempStack.endTime", type: "date",options: {format : "yyyy-MM-dd"}}
	    			]
            	}
        	    var gridoption={
                     columns:[
                        { display: '销售编号',name: 'id',width:90},
	    				{ display: '养殖公司',name: 'company.name', width:130},
	    				{ display: '代养农户', name: 'farmer.name', width:90},
	    				{ display: '所属批次', name: 'batch.batchNumber',width:90 },
	    				{ display: '销售日期', name: 'registDate',width:90},
	    				{ display: '出栏单', name: 'outPigsty.id',width:90},
	    				{ display: '出栏类型', name: 'outPigsty.saleType',width:100,render: function(data,rowindex){
		    				if(data.outPigsty){
		    					var value = data.outPigsty.saleType;
			    				if(value=='Q')
		          					return '正常出栏';
			    				else if(value=='E')
			    					return '淘汰出栏';
		    				}else
    							return "";
	    				}},
	    				{ display: '销售总数（头）', name: 'totalQuan',width:95,align:'right',render: function(data){
		                  	   if(data.totalQuan){
								   value = (data.totalQuan*1);
								   return value.toFixed(0);
							   }
		                }},
	    				{ display: '销售总重量（公斤）', name: 'totalWeight',width:115,align:'right',render: function(data){
		                  	   if(data.totalWeight){
								   value = (data.totalWeight*1);
								   return value.toFixed(2);
							   }
		                }},
	    				{ display: '销售总金额（元）', name: 'totalMoney',width:110,align:'right',render: function(data){
		                  	   if(data.totalMoney){
								   value = (data.totalMoney*1);
								   return value.toFixed(2);
							   }
		                }},
	    				{ display: '审核人', name: 'checkUser' },
	    				{ display: '审核时间', name: 'checkDate' },
	    				{ display: '审核状态', name: 'checkStatus', render: function(data,rowindex){
		    				var value = data.checkStatus;
		    				if(value=='1')
	          					return '已审核';
		    				else
		    					return '未审核';
    						return value;
		    				
	    				}},
	    				{ display: '结算状态', name: 'isBalance', render: function(data,rowindex){
		    				var value = data.isBalance;
		    				if(value=='Y')
	          					return '已结算';
		    				else
		    					return '未结算';
    						return value;
		    				
	    				}}
              		],
                    url:'farmer_sale!loadByPage',
                    exportBtn: true,
			        exportUrl: 'farmer_sale!exportFile'
             }
             var glist=$.pap.createFormGridList({ toolbar:toolBarOption,form:form,grid:gridoption});
             grid=glist.getGrid();
        });
    </script>
</head>
<body>
</body>
</html>
