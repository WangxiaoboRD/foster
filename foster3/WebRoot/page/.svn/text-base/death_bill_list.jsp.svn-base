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

    <script type="text/javascript">
        var grid = null;
        $(function () {
			   // 工具条
        	   var toolBarOption = { 
   	                   items: [
	   	   	              { text: '新增', icon: 'add', click: function(){
							 $.pap.addTabItem({ 
	   	   	            	   	tabid: 'deathBillAdd',
   	   	      			  	   	text: '死亡单/新增',
   	   	      			 	   	url: 'death_bill_add.jsp'
   	   	       			     });
	   	   	              }},
	   	   	              { line:true },
	   	   	              { text: '修改', icon: 'modify', expression:'!=1', disabled:true, click: function(item){
	   	   	           		 var selectedRow = item.selectGrid.selected; 
	   	   	           		 if (selectedRow.length == 0) {
	   	   	           			$.ligerDialog.warn('请选择要修改的记录！');
	   	   	           			return;
			   	   	         }
			   	   	         var selData = selectedRow[0];
			   	   	         if (selData['checkStatus'] == '1') {
			   	   	       		$.ligerDialog.warn('所选单据已审核，不允许修改！');
  	   	           				return;
				   	   	     }
							 $.pap.addTabItem({ 
		   	    	        	tabid: 'deathBillModify'+grid.selected[0]['id'],
			   	    	        text: '死亡单/修改', 
			   	    	        url: 'death_bill!loadUpdateById?id='+grid.selected[0]['id']
			   	    	     });
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
   	   	      							url: "death_bill!delete",
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
			   	    				tabid: 'deathBill'+grid.selected[0]['id'],
		   	   	    				text: '死亡单【'+grid.selected[0]['id']+'】',
		       	   					params:{ 'ids':ids,'id':grid.selected[0]['id']},
		   	   	    				url: 'death_bill!loadDetailById?id='+grid.selected[0]['id']
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
	   		      							url: "death_bill!check",
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
	   		      							url: "death_bill!cancelCheck",
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
	 	   			       { text: '图片上传', icon: 'up', expression:'!=1', disabled:true, click: function(item){
		 	   			    	if(grid.selected[0]['isAnnex']=='Y'){
			 	   			    	$.ligerDialog.confirm('图片已经上传需要替换吗?', function(data) {
				 	   			    	if(data){
					   	   					uploadDialog = $.ligerDialog.open({ 
						       	   				title:'死亡单图片上传',
						       	   				url: 'death_bill_annex_upload.jsp', 
						       	   				height: 480,
						       	   				width: 580, 
						       	   				onLoaded: function(param){
						   	    		       	   var documentF = param.contentDocument || param.document ;//兼容IE 和 FF
						   	    		           $('div.toolbar-pap',documentF).hide();
						   	    		           $('input[id="id"]',documentF).attr('value', grid.selected[0]['id']); 
						   	    		           $('input[id="batch.id"]',documentF).attr('value', grid.selected[0]['batch.id']); 
						   	    		           $('input[id="batch.batchNumber"]',documentF).attr('value', grid.selected[0]['batch.batchNumber']); 
						   	    		           $('input[id="farmerid"]',documentF).attr('value', grid.selected[0]['farmer.id']); 
						   	    		           $('input[id="farmer"]',documentF).attr('value', grid.selected[0]['farmer.name']); 
						   	    		        },
						       	   				buttons: [ 
						       	   					{ text: '确定', onclick: function(item, dialog) {
						       	   						dialog.frame.upload(dialog, grid);
						               	   			}}, 
						       	   					{ text: '取消', onclick: function(item, dialog) {
						               	   				dialog.close();
						           	   				}}
						           	   			] 
						       	   			});
				 	   			    	}});
		   	    		  		   }else{
		   	    		  				uploadDialog = $.ligerDialog.open({ 
					       	   				title:'死亡单图片上传',
					       	   				url: 'death_bill_annex_upload.jsp', 
					       	   				height:480,
					       	   				width: 580, 
					       	   				onLoaded: function(param){
					   	    		       	   var documentF = param.contentDocument || param.document ;//兼容IE 和 FF
					   	    		           $('div.toolbar-pap',documentF).hide();
					   	    		           $('input[id="id"]',documentF).attr('value', grid.selected[0]['id']); 
					   	    		           $('input[id="batch.id"]',documentF).attr('value', grid.selected[0]['batch.id']); 
					   	    		           $('input[id="batch.batchNumber"]',documentF).attr('value', grid.selected[0]['batch.batchNumber']); 
					   	    		           $('input[id="farmerid"]',documentF).attr('value', grid.selected[0]['farmer.id']); 
					   	    		           $('input[id="farmer"]',documentF).attr('value', grid.selected[0]['farmer.name']); 
					   	    		        },
					       	   				buttons: [ 
					       	   					{ text: '确定', onclick: function(item, dialog) {
					       	   						dialog.frame.upload(dialog, grid);
					               	   			}}, 
					       	   					{ text: '取消', onclick: function(item, dialog) {
					               	   				dialog.close();
					           	   				}}
					           	   			] 
					       	   			});
				   	    		  }
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
				   	   	        var flag3 = false;
			     			    var ids = "";
		     				    $(grid.selected).each(function(i, item){
			     					ids += item['id']+",";
			     					if (item['isReceipt'] == 'Y') {
			     						flag1 = true;
			         				}
			         				if (item['checkStatus']!= '1') {
			     						flag2 = true;
			         				}
			         				if (item['isAnnex']!= 'Y') {
			     						flag3 = true;
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
				 	     	    /*
				 	     	    if (flag3) {
					       			$.ligerDialog.warn('所选单据未上传照片，不允许回执！');
									return;
				 	     	    }
				 	     	    */
		   	    				  $.ligerDialog.confirm('确认要回执该单据吗?', function(data) {
	 	   	          				if(data){
		 	   	          				$.ligerui.ligerAjax({
	   		      							type: "POST",
	   		      							async:  false,
	   		      							url: "death_bill!receipt",
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
	   		      							url: "death_bill!cancelReceipt",
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
						{ display: "死亡单号", name:"e.id", type:"text" },
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
						{ display: "死亡时间", name: "e.tempStack.startTime", type: "date", space: 10 },
		                { display: "至", type: "label", space: 10 },
		                { hideLabel: true, name: "e.tempStack.endTime", type: "date"}
	    			]
            	}

        	 var gridoption={
                     columns:[
                        { display: '死亡单号',name: 'id',width:90},
	    				{ display: '养殖公司',name: 'company.name',width:110},
	    				{ display: '代养户',name: 'farmer.name',width:110 },
	    				{ display: '死亡时间',name: 'registDate',width:100},
	    				{ display: '批次',name: 'batch.batchNumber',width:100},
	    				{ display: '总头数',name: 'totalQuan',width:100},
	    				{ display: '总重量(kg)',name: 'totalWeight',width:100},
	    				{ display: '技术员',name: 'technician.name',width:100},
	    				{ display: '是否已结算',name: 'isBalance',render: function(data){
	    					if(data.isBalance=='Y')
	    						return '已结算'; 
	    					else
	    						return '未结算';	
	    				}},
	    				{ display: '照片是否上传',name: 'isAnnex',render: function(data){
	    					if(data.isAnnex=='Y')
	    						return '已上传'; 
	    					else
	    						return '未上传';	
	    				}},
	    				{ display: '死亡归属',name: 'isCorDeath',render: function(data){
	    					if(data.isCorDeath=='Y')
	    						return '养殖公司'; 
	    					else
	    						return '代养户';	
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
	    				{ display: '创建时间', name: 'createDate',width:90}
              		],
                    url:'death_bill!loadByPage',
                    exportBtn: true,
			        exportUrl: 'death_bill!exportFile'
             }
            var glist=$.pap.createFormGridList({ toolbar:toolBarOption,form:form,grid:gridoption});
            grid=glist.getGrid();
        });
    </script>
</head>
<body>
</body>
</html>
