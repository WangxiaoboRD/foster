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
        var grid = null;
        $(function () {
			   // 工具条
        	   var toolBarOption = { 
   	                   items: [
	   	   	              { text: '新增', icon: 'add', click: function(){
                   				$.ligerDialog.open({ 
                   	   				title:'新增猪苗登记单',
                   	   				url: 'pig_purchase_add.jsp', 
                   	   				height: 480,
                   	   				width: 600, 
                   	   				buttons: [ 
                   	   					{ text: '确定', onclick: function(item, dialog) {
                   	   						var data = dialog.frame.onSave();
		                   	   		    	if(data!=null){
		                   	   		    	$.ligerui.ligerAjax({
		                   	   						type:"POST",
		                   	   						async: false,
		                   	   						url:"pig_purchase!save",	
		                   	   						data:data,
		                   	   						dataType:"text",
		                   	   						beforeSend:function(){},
		                   	   						success:function(result, textStatus){
		                   	   							if(result != null && result !=""){
		                   	   							 tip = $.ligerDialog.tip({ title: '提示信息', content: '成功添加一条记录！' });
         				       							 window.setTimeout(function(){ tip.close()} ,2000); 
         				       							dialog.close();	
		                   	   							}
		                   	   						},
		                   	   						error: function(XMLHttpRequest,textStatus){
		                   	   								$.ligerDialog.error('操作出现异常');
		                   	   						},
		                   	   						complete:function(){grid.loadData(true);}
		                   	   					});    			
		                   	   	    			
		                   	   	    		}	
                           	   			}}, 
                   	   					{ text: '取消', onclick: function(item, dialog) {
                           	   				dialog.close();
                       	   				}}
                       	   			] 
                   	   			});
                           }},
	   	   	              { line:true },
	   	   	              { text: '修改', icon: 'modify' ,expression:'!=1', disabled:true, click: function(item) {
	   	   	            	
               		    	if(! jQuery.isEmptyObject(grid.selected) ){
               	   	    		if(grid.selected.length > 1){
               	   	    			$.ligerDialog.warn('请选择一条要修改的记录！');
               	   	    		}else if(grid.selected.length ==1){
               	   	    		    var selectedRow = item.selectGrid.selected;
               	   	    		    var selData = selectedRow[0];
					   	   	        if (selData['isBalance'] == 'Y') {
					   	   	       		$.ligerDialog.warn('所选单据已结算，不允许修改！');
		  	   	           				return;
						   	   	    }
               	   	    			$.ligerDialog.open({
               							title:'修改猪苗登记单',
               							width:600,
               							height:480, 
               							url: 'pig_purchase!loadUpdateById?id='+grid.selected[0]['id'],
               							buttons: [
               				                {text:'确定',onclick: function(item, dialog) {
               				                	var data = dialog.frame.onSave();
	                 				       	    	if(data!=null){
	                 				       				$.ligerui.ligerAjax({
	                 				       					url:"pig_purchase!modifyAll",
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
               	   	    		}
               	   	    	}else{
               	   	    		$.ligerDialog.warn('至少选择一条要修改的记录！');
               	   	    	}
        		          }},
	   	   	              { line:true },
	   	   	              { text: '删除', icon: 'delete', expression:'==0', disabled:true, click: function(){
		   	   	             var selectedRow = grid.selected; 
	  	   	           		 if (selectedRow.length == 0) {
	  	   	           			 $.ligerDialog.warn('请选择要删除的记录！');
	  	   	           			 return;
			   	   	         }
			   	   	         var status = false;
  	   	          			 var delIds = "";
  	   	          			 $(grid.selected).each(function(i, item){
  	   	          			 	 delIds += item['id']+",";
	  	   	          			 if (item['checkStatus'] == '1')
	  	   	          				status = true;
  	   	          			 });
	  	   	          		 if (status) {
								 $.ligerDialog.warn('所选单据包含有【已审核】的记录，不允许删除！');
								 return;
							 }
   	   	      	    		 $.ligerDialog.confirm('数据删除后不可恢复,你确认要删除?', function(data) {
   	   	          				if(data){
   	   	          					$.ligerui.ligerAjax({
   	   	      							type: "POST",
   	   	      							async:  false,
   	   	      							url: "pig_purchase!delete",
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
	   	   			      }},
	   	   	              { line:true },
	   	   				  { text: '审核', icon: 'memeber', expression:'==0', disabled: true, click: function(item){
	 	   	           		  var selectedRow = item.selectGrid.selected; 
	 	   	           		  if (selectedRow.length == 0) {
	 	   	           			 $.ligerDialog.warn('请选择要审核的记录！');
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
	   		      							url: "pig_purchase!check",
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
	   	   	           				$.ligerDialog.warn('请选择要撤销的单据！');
	   	   	           			 	return;
			   	   	          	}
						        var selData = selectedRow[0];
					            if (selData['checkStatus'] != '1') {
					       			$.ligerDialog.warn('所选单据未审核，不允许撤销！');
									return;
				 	     	    }
				 	     	    if (selData['isReceipt'] == 'Y') {
					       			$.ligerDialog.warn('所选单据已回执！');
									return;
				 	     	    }
			   	   	            $.ligerDialog.confirm('确认要撤销该单据吗?', function(data) {
	 	   	          				if(data){
		 	   	          				$.ligerui.ligerAjax({
	   		      							type: "POST",
	   		      							async:  false,
	   		      							url: "pig_purchase!cancelCheck",
	   		      							data: { id: selData['id'] },
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
	  	   	           		 	var selectedRow = item.selectGrid.selected; 
			   	   	           	if (selectedRow.length == 0) {
		   	   	           			$.ligerDialog.warn('请选择要操作的记录！');
		   	   	           			return;
				   	   	        }
			   	   	            var ids = new Array()
			   	   	            var datas=grid.rows;
			   	   	         	for(var i=0;i<datas.length;i++){
		   	          				ids[i]=datas[i]['id'];
		   	          			}
		   	    				$.pap.addTabItem({ 
			   	    				tabid: 'pigPurchaseOrder'+grid.selected[0]['id'],
		   	   	    				text: '公司销售【'+grid.selected[0]['id']+'】',
		       	   					params:{ 'ids':ids,'id':grid.selected[0]['id']},
		   	   	    				url: 'pig_purchase!loadDetailById?id='+grid.selected[0]['id']
		   	   	    			});
		   	   			   }},
	 	   			       { line:true },
		   	   	           { text: '回执', icon: 'user', expression:'==0', disabled:true, click: function(item){
	   	   	           		 	var selectedRow = item.selectGrid.selected; 
			   	   	           	if (selectedRow.length == 0) {
		   	   	           			$.ligerDialog.warn('请选择要确认回执的记录！');
		   	   	           			return;
				   	   	         }
				   	   	        var flag1 = false;
				   	   	        var flag2 = false;
			     			    var ids = "";
 								$(grid.selected).each(function(i, item){
			     					ids += item['id']+",";
			     					if (item['isReceipt'] == 'Y') {
			     						flag1 = true;
			         				}
			         				if (item['checkStatus']!= '1') {
			     						flag2 = true;
			         				}
		     				    });		
		     				    if (flag1) {
					       			$.ligerDialog.warn('所选单据已回执！');
									return;
				 	     	    }
				 	     	    if (flag2) {
					       			$.ligerDialog.warn('所选单据未审核，不允许回执！');
									return;
				 	     	    }
		   	    				$.ligerDialog.confirm('确认要回执该单据吗?', function(data) {
	 	   	          				if(data){
		 	   	          				$.ligerui.ligerAjax({
	   		      							type: "POST",
	   		      							async:  false,
	   		      							url: "pig_purchase!receipt",
	   		      							data: { ids: ids },
	   		      							dataType: "text",
	   		      							success: function(result, status){
	   		      								if(result != ""){
	   		      									tip = $.ligerDialog.tip({ title: '提示信息', content: result + '条记录被成功回执！' });
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
		   	   	            { text: '撤销回执', icon: 'user_go', expression:'!=1', disabled:true, click: function(item){
	   	   	           		 	var selectedRow = item.selectGrid.selected; 
			   	   	           	if (selectedRow.length == 0) {
		   	   	           			$.ligerDialog.warn('请选择要确认回执的记录！');
		   	   	           			return;
				   	   	         }
				   	   	        var selData = selectedRow[0];
					            if (selData['isReceipt'] == 'N') {
					       			$.ligerDialog.warn('所选单据未回执！');
									return;
				 	     	    }
		   	    				$.ligerDialog.confirm('确认要回执该单据吗?', function(data) {
	 	   	          				if(data){
		 	   	          				$.ligerui.ligerAjax({
	   		      							type: "POST",
	   		      							async:  false,
	   		      							url: "pig_purchase!cancelReceipt",
	   		      							data: { id: selData['id'] },
	   		      							dataType: "text",
	   		      							success: function(result, status){
	   		      								if(result != ""){
	   		      									tip = $.ligerDialog.tip({ title: '提示信息', content: result + '条记录被成功回执！' });
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
                   		]
             	};

        	 form = {
                 labelWidth: 70,
                 fields:[
						{ display: "登记单号", name:"e.id", type:"text" },
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
							} 
						}},
						{ display: "代养户", name:"e.farmer.id",comboboxName: 'farmer', type:"select", options:{
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
						}},
                   		{ display: "审核状态",name: "e.checkStatus",newline: true,type: "select", options:{
							  keySupport:true,
	   	                	  data: [
	   	                       	{ text: '未审核', id: '0' },
	   	               	  		{ text: '已审核', id: '1' }
	   		                 ],
	   		              	value:0
	               		}},
	               		{ display: "回执状态",name: "e.isReceipt",type: "select", options:{
							  keySupport:true,
	   	                	  data: [
	   	                       	{ text: '未回执', id: 'N' },
	   	               	  		{ text: '已回执', id: 'Y' }
	   		                 ],
	   		              	value:'N'
	               		}},
						{ display: "登记时间", name: "e.tempStack.startTime", type: "date", space: 10 },
		                { display: "至", type: "label", space: 10 },
		                { hideLabel: true, name: "e.tempStack.endTime", type: "date"}
	    			]
            	}

        	 var gridoption={
                     columns:[
                        { display: '登记单号',name: 'id',width:90},
	    				{ display: '养殖公司',name: 'company.name',width:110},
	    				{ display: '代养户',name: 'farmer.name',width:110 },
	    				{ display: '登记时间',name: 'registDate',width:100},
	    				{ display: '运输公司',name: 'transportCo.name',width:100},
	    				{ display: '司机',name: 'driver.name',width:100},
	    				{ display: '车牌号',name: 'carNum',width:100},
	    				{ display: '批次',name: 'batch.batchNumber',width:100},
	    				{ display: '供应商',name: 'pigletSupplier.name',width:100},
	    				{ display: '头数',name: 'quantity',width:100},
	    				{ display: '重量(kg)',name: 'weight',width:100},
	    				{ display: '运费(元)',name: 'freight',width:100},
	    				{ display: '成本单价',name: 'price',width:100},
	    				{ display: '成本(元)',name: 'cost',width:100},
	    				{ display: '日龄',name: 'days',width:100},
	    				{ display: '赠送头数',name: 'giveQuantity',width:100},
	    				{ display: '是否已结算',name: 'isBalance',render: function(data){
	    					if(data.isBalance=='Y')
	    						return '已结算'; 
	    					else
	    						return '未结算';	
	    				}},
	    				{ display: '审核状态',name: 'checkStatus',render: function(data){
		    				var text = data.checkStatus;
	    					switch(data.checkStatus){
	    						case '1': text = "已审核"; break;
	    						case '0': text = "未审核"; break;
	    					}
	    					return text;
	    				}},
	    				{ display: '是否已回执',name: 'isReceipt',render: function(data){
	    					if(data.isReceipt=='Y')
	    						return '已回执'; 
	    					else
	    						return '未回执';	
	    				}},
	    				{ display: '审核人',name: 'checkUser',width:80},
	    				{ display: '审核日期', name: 'checkDate',width:90},
	    				{ display: '创建人',name: 'createUser',width:80},
	    				{ display: '创建时间', name: 'createDate',width:90},
	    				{ display: '备注', name: 'remark',width:150},
              		],
                    url:'pig_purchase!loadByPage',
                    exportBtn: true,
			        exportUrl: 'pig_purchase!exportFile'
             }
            var glist=$.pap.createFormGridList({ toolbar:toolBarOption,form:form,grid:gridoption});
            grid=glist.getGrid();
        });
    </script>
</head>
<body>
</body>
</html>
