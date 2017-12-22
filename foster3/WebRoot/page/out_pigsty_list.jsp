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
   	                        {text: '新增', icon: 'add', click: function(){
                   				$.ligerDialog.open({ 
                   	   				title:'创建出栏单',
                   	   				url: 'out_pigsty_add.jsp', 
                   	   				height: 420,
                   	   				width: 380, 
                   	   				buttons: [ 
                   	   					{ text: '确定', onclick: function(item, dialog) {
                   	   						var data = dialog.frame.onSave();
		                   	   		    	if(data!=null){
			                   	   		    	$.ligerui.ligerAjax({
		                   	   						type:"POST",
		                   	   						async: false,
		                   	   						url:"out_pigsty!save",	
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
	   	            	  		var selectedRow = item.selectGrid.selected;
            	   	    		if (selectedRow.length == 0) {
				   	           		$.ligerDialog.warn('请选择要操作的记录！');
				   	           		return;
			   	   	          	}
		   	   	          		if(selectedRow[0]['checkStatus']=='1'){
		  	    			 		$.ligerDialog.warn('该单据已经审核，不允许修改！');
		  	    			 		return;
		  	    		  		}
           	   	    			$.ligerDialog.open({
           							title:'修改出栏单',
           							height: 420,
                   	   				width: 380,  
           							url: 'out_pigsty!loadUpdateById?id='+grid.selected[0]['id'],
           							buttons: [
           				                {text:'确定',onclick: function(item, dialog) {
           				                	var data = dialog.frame.onSave();
              				       	    	if(data!=null){
              				       				$.ligerui.ligerAjax({
              				       					url:"out_pigsty!modify",
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
	   	   	                { text: '删除', icon: 'delete', expression:'==0', disabled:true, click: function(item){
	   	   	              		var selectedRow = item.selectGrid.selected;
		   	   	                if (selectedRow.length == 0) {
				   	           		$.ligerDialog.warn('请选择要操作的记录！');
				   	           		return;
			   	   	          	}
   	   	          				var delIds = "";
   	   	          				var flag=true;
   	   	          				$(selectedRow).each(function(i, item){
   	   	   	          				if(item['checkStatus']=='1')
   	   	   	   	          				flag = false;
   	   	   	          				else
   	   	          						delIds += item['id']+",";
   	   	          				});
   	   	          				if(flag == false){
   	   	          					$.ligerDialog.warn('选择的记录包含已经审核的单据！');
		   	           				return;
   	   	   	   	          		}
   	   	          		        $.ligerDialog.confirm('数据删除后不可恢复,你确认要删除?', function(data) {
	   	   	          				if(data){
	   	   	          					$.ligerui.ligerAjax({
	   	   	      							type: "POST",
	   	   	      							async:  false,
	   	   	      							url: "out_pigsty!delete",
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
		   	           			 $.ligerDialog.warn('请选择要操作的记录！');
		   	           			 return;
			   	   	          }
		   	           		  var flag = false;
			     			  var ids = "";
		     				  $(selectedRow).each(function(i, item){
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
	  		      							url: "out_pigsty!check",
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
	 	   	   	          		if(selectedRow[0]['checkStatus']=='0'){
		   	    			 		$.ligerDialog.warn('该单据还未审核！');
		   	    			 		return;
		   	    		  		}
	 	   	   	         		if(selectedRow[0]['isBalance']=='Y'){
	   	    			 			$.ligerDialog.warn('该单据已经结算不允许撤销！');
	   	    			 			return;
	   	    		  			}
			   	   	            $.ligerDialog.confirm('确认要撤销吗?', function(data) {
			   	   	          		var id = selectedRow[0]['id'];
		   	          				if(data){
		 	   	          				$.ligerui.ligerAjax({
	  		      							type: "POST",
	  		      							async:  false,
	  		      							url: "out_pigsty!cancelCheck",
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
			   	    				tabid: 'outPigstyOrder'+grid.selected[0]['id'],
		   	   	    				text: '出栏单【'+grid.selected[0]['id']+'】',
		       	   					params:{ 'ids':ids,'id':grid.selected[0]['id']},
		   	   	    				url: 'out_pigsty!loadDetailById?id='+grid.selected[0]['id']
		   	   	    			});
		   	   			   }}
                    ]
             	};

        	 form = {
                 labelWidth:65,
                 space: 20,
                 fields:[
						 { display: "出栏编码", name: "e.id", type: "text" },
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
	        	 	 	{ display: "出栏日期", name: "e.tempStack.startTime", type: "date", space: 10,options: {format : "yyyy-MM-dd"}},
		                { display: "至", type: "label", space: 10 },
		                { hideLabel: true, name: "e.tempStack.endTime", type: "date",options: {format : "yyyy-MM-dd"}}
	    			]
            	}

        	 var gridoption={
                     columns:[
                        { display: '出栏编号',name: 'id',width:90},
	    				{ display: '养殖公司',name: 'company.name', width:130},
	    				{ display: '代养农户', name: 'farmer.name', width:90},
	    				{ display: '所属批次', name: 'batch.batchNumber',width:90 },
	    				{ display: '出栏日期', name: 'registDate',width:90},
	    				{ display: '出栏头数（头）', name: 'quantity',width:65,align:'right'},
	    				{ display: '出栏重量（公斤）', name: 'weight',width:100,align:'right',render: function(data){
		                  	   if(data.weight){
								   value = (data.weight*1);
								   return value.toFixed(2);
							   }
		                }},
	    				{ display: '出栏类型', name: 'saleType', render: function(data,rowindex){
		    				var value = data.saleType;
		    				if(value=='Q')
	          					return '正常出栏';
		    				else
		    					return '淘汰出栏';
    						return value;
		    				
	    				}},
	    				{ display: '销售单编号', name: 'farmerSale',width:80},
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
              		url:'out_pigsty!loadByPage',
                    exportBtn: true,
			        exportUrl: 'out_pigsty!exportFile'
             }
            var glist=$.pap.createFormGridList({ toolbar:toolBarOption,form:form,grid:gridoption});
            grid=glist.getGrid();
        });
  </script>
</head>
<body>
</body>
</html>
