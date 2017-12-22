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
	   	   	            	   tabid: 'contractAdd',
   	   	      			  	   text: '合同新增',
   	   	      			 	   url: 'contract_add.jsp'
   	   	       			    });
	   	   	              }},
	   	   	        	  { text: '复制新增', icon: 'copy', expression:'!=1', disabled:true, click: function() {
	   	   	            	  $.pap.addTabItem({ 
	   	   	      			  	 text: '复制新增',
	   	   	      		 		 url: 'contract!loadCopyById?id='+grid.selected[0]['id'],
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
			   	   	         if (selData['status.dcode'] == 'LOST') {
			   	   	       		$.ligerDialog.warn('所选单据已终止，不允许修改！');
  	   	           				return;
				   	   	     }
							  $.pap.addTabItem({ 
		   	    	        	 tabid: 'contractModify'+grid.selected[0]['id'],
			   	    	         text: '合同修改', 
			   	    	         url: 'contract!loadUpdateById?id='+grid.selected[0]['id']
			   	    	       });
	   	   			      }},
	   	   	              { line:true },
	   	   	              { text: '删除', icon: 'delete', expression:'==0', disabled:true, click: function(){
		   	   	              var selectedRow = grid.selected; 
	  	   	           		  if (selectedRow.length == 0) {
	  	   	           			 $.ligerDialog.warn('请选择要操作的记录！');
	  	   	           			 return;
			   	   	          }
			   	   	         var status = false;
  	   	          			 var delIds = "";
  	   	          			 $(grid.selected).each(function(i, item){
  	   	          				delIds += item['id']+",";
	  	   	          			if (item['checkStatus'] == '1') {
	  	   	          				status = true;
		         				}
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
   	   	      							url: "contract!delete",
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
	   		      							url: "contract!check",
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
			   	   	            $.ligerDialog.confirm('确认要撤销吗?', function(data) {
			   	   	          		var id = grid.selected[0]['id'];
	 	   	          				if(data){
		 	   	          				$.ligerui.ligerAjax({
	   		      							type: "POST",
	   		      							async:  false,
	   		      							url: "contract!cancelCheck",
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
			   	    				tabid: 'contractOrder'+grid.selected[0]['id'],
		   	   	    				text: '合同【'+grid.selected[0]['id']+'】',
		       	   					params:{ 'ids':ids,'id':grid.selected[0]['id']},
		   	   	    				url: 'contract!loadDetailById?id='+grid.selected[0]['id']
		   	   	    			});
		   	   			   }},
	 	   			       { line:true },
		   	               { text: '附件上传', icon: 'up', expression:'!=1', disabled:true, click: function(item){
		 	   			    	if(grid.selected[0]['isAnnex']=='Y'){
			 	   			    	$.ligerDialog.confirm('附件已经上传需要替换吗?', function(data) {
				 	   			    	if(data){
					   	   					uploadDialog = $.ligerDialog.open({ 
						       	   				title:'合同附件上传',
						       	   				url: 'contract_annex_upload.jsp', 
						       	   				height: 260,
						       	   				width: 430, 
						       	   				onLoaded: function(param){
						   	    		       	   var documentF = param.contentDocument || param.document ;//兼容IE 和 FF
						   	    		           $('div.toolbar-pap',documentF).hide();
						   	    		           $('input[id="id"]',documentF).attr('value', grid.selected[0]['id']); 
						   	    		           $('input[id="code"]',documentF).attr('value', grid.selected[0]['code']); 
						   	    		           $('input[id="name"]',documentF).attr('value', grid.selected[0]['name']);
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
					       	   				title:'合同附件上传',
					       	   				url: 'contract_annex_upload.jsp', 
					       	   				height: 260,
					       	   				width: 430, 
					       	   				onLoaded: function(param){
					   	    		       	   var documentF = param.contentDocument || param.document ;//兼容IE 和 FF
					   	    		           $('div.toolbar-pap',documentF).hide();
					   	    		           $('input[id="id"]',documentF).attr('value', grid.selected[0]['id']); 
					   	    		           $('input[id="code"]',documentF).attr('value', grid.selected[0]['code']); 
					   	    		           $('input[id="name"]',documentF).attr('value', grid.selected[0]['name']);
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
			   			  	 { text: '附件下载', icon: 'down', expression:'!=1', disabled:true, click: function(item){

			   			    	if(grid.selected[0]['isAnnex']=='Y'){
				   			    	$("form").append("<input name='id' id='id' type='hidden' value='" + grid.selected[0]['id'] + "'>");
						   			$("form")[0].action = "contract!download";
					   				$("form")[0].submit();
					   				$("#id").remove();
			   			    	}else{
			   			    		$.ligerDialog.warn('合同附件未上传');
			   			    		return;
					   			}
			   			     }},
			   			     { line:true },
		   	   				 { text: '合同中止', icon: 'busy', expression:'!=1', disabled: true, click: function(item){
				   				 var selectedRow = item.selectGrid.selected; 
			   	           		 if (selectedRow.length == 0) {
			   	           			$.ligerDialog.warn('请选择要操作的记录！');
				   	           			return;
				   	   	         }
				   	   	         if (selectedRow[0]['checkStatus'] !='1') {
			   	   	           		$.ligerDialog.warn('合同未生效！');
			   	   	           		return;
						   	   	 }
					   	   	     if (selectedRow[0]['status.dcode'] !='EFFECT') {
			   	   	           		$.ligerDialog.warn('合同不是生效状态！');
			   	   	           		return;
					   	   	     }
			   	   	             $.ligerDialog.confirm('确认要中止合同吗?', function(data) {
		   	          				var id = grid.selected[0]['id'];
		   	          				if(data){
		   	          					$.ligerui.ligerAjax({
		   	      							type: "POST",
		   	      							async:  false,
		   	      							url: "contract!unStopEffect",
		   	      							data: { id: id },
		   	      							dataType: "text",
		   	      							success: function(result, status){
		   	      								if(result == "OK"){
		   	      									tip = $.ligerDialog.tip({ title: '提示信息', content: '合同已失效！' });
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
		   	   				 { text: '合同终止', icon: 'group', expression:'!=1', disabled: true, click: function(item){
				   				 var selectedRow = item.selectGrid.selected; 
			   	           		 if (selectedRow.length == 0) {
			   	           			$.ligerDialog.warn('请选择要操作的记录！');
				   	           			return;
				   	   	         }
				   	   	         if (grid.selected[0]['checkStatus'] !='1') {
			   	   	           		$.ligerDialog.warn('合同未生效！');
			   	   	           		return;
					   	   	     }
			   	   	             $.ligerDialog.confirm('确认要终止合同吗?', function(data) {
		   	          				var id = grid.selected[0]['id'];
		   	          				if(data){
		   	          					$.ligerui.ligerAjax({
		   	      							type: "POST",
		   	      							async:  false,
		   	      							url: "contract!unEffect",
		   	      							data: { id: id },
		   	      							dataType: "text",
		   	      							success: function(result, status){
		   	      								if(result == "OK"){
		   	      									tip = $.ligerDialog.tip({ title: '提示信息', content: '合同已失效！' });
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
	   	   				 { text: '合同恢复', icon: 'cancel', expression:'!=1', disabled: true, click: function(item){
			   				 var selectedRow = item.selectGrid.selected; 
		   	           		 if (selectedRow.length == 0) {
		   	           			$.ligerDialog.warn('请选择要操作的记录！');
			   	           			return;
			   	   	         }
			   	   	         if (grid.selected[0]['checkStatus'] !='1') {
		   	   	           		$.ligerDialog.warn('合同未生效！');
		   	   	           		return;
				   	   	     }
		   	   	             $.ligerDialog.confirm('确认要恢复合同吗?', function(data) {
	   	          				var id = grid.selected[0]['id'];
	   	          				if(data){
	   	          					$.ligerui.ligerAjax({
	   	      							type: "POST",
	   	      							async:  false,
	   	      							url: "contract!onEffect",
	   	      							data: { id: id },
	   	      							dataType: "text",
	   	      							success: function(result, status){
	   	      								if(result == "OK"){
	   	      									tip = $.ligerDialog.tip({ title: '提示信息', content: '合同已恢复！' });
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
	 	   			   }}
                   		]
             	};

        	 form = {
                 labelWidth: 70,
                 fields:[
						{ display: "合同编码", name: "e.code", type: "text" },
                    	{ display: "养殖公司", name:"e.company.id",type:"select",options:{
						    url:'company!loadByEntity',
						    valueField: 'id', //关键项
							textField: 'name',
							keySupport:true,
							selectBoxHeight: 150,
							triggerToLoad: true,
							onSelected: function (nv){
								var _farmer = liger.get("farmer");
								if(_farmer)
									_farmer.setValue("");
                    		}
						}},
						{ display: "代养农户", name:"e.farmer.id",comboboxName: 'farmer', type:"select",options:{
							url: 'farmer!loadByPinYin',
			                valueField: 'id',
			                textField: 'name', 
			                selectBoxHeight: 180,
			                autocomplete: true,
			                keySupport:true,
			                onBeforeOpen: function() {
			                	var g = this;
			                	g.setParm('e.company.id', $("input[id='e.company.id']").val());
			                },
						}},
						{ display: "合同状态", name:"e.status.dcode",newline:true, type:"select" , options:{
						    url:'bussiness_ele_detail!loadByEntity',
						    parms:{'e.bussinessEle.ecode': 'CONTRACTSTATUS'},
						    valueField: 'dcode', //关键项
						   	textField: 'value',
							keySupport:true,
							alwayShowInDown: true,
						    selectBoxHeight: 90
						}},
	        	 		{ display: "签订日期", name: "e.tempStack.startTime", type: "date", space: 10,options: {format : "yyyy-MM-dd"}},
		                { display: "至", type: "label", space: 10 },
		                { hideLabel: true, name: "e.tempStack.endTime", type: "date",options: {format : "yyyy-MM-dd"}}
	    			]
            	}

        	 var gridoption={
                     columns:[
						{ display: '合同流水',name: 'id',width: 90},
						{ display: '合同编码',name: 'code',width: 90},
						{ display: "养殖公司编码", name:"company.id",hide:true},
						{ display: "养殖公司", name:"company.name",width: 100},
						{ display: '代养编码',name: 'farmer.id',hide:true},
						{ display: '代养户',name: 'farmer.name',width: 100},
						{ display: "批次编号", name:"batchNumber",width: 100},
						{ display: "合同状态", name:"status.value",width: 70}, 
						{ display: "签订日期", name:"registDate",width: 85},
						{ display: "饲料厂", name:"feedFac.name",width: 120}, 
						{ display: "开发人员", name:"developMan.name",width: 80},
						{ display: "技术人员", name:"technician.name",width: 80},
						{ display: "养殖品种", name:"variety.name",width: 80},
						{ display: "养殖数量", name:"pigletQuan",width: 70},
						{ display: "猪苗单价", name:"pigletPrice",width: 70},
						{ display: "猪苗标重", name:"standPigletWeight",width: 70},
						{ display: "出栏标重", name:"standSaleWeight",width: 70},
						{ display: "销售市价", name:"marketPrice",width: 70},
						
						{ display: "允许料肉比偏差", name:"allowDiff",width: 95},
						{ display: "超出允许值扣费", name:"firstPrice",width: 95},
						{ display: "一级料肉比偏差", name:"firstDiff",width: 95},
						{ display: "超出一级值扣费", name:"overFirstPrice",width: 95},
						
						{ display: "合同状态", name:"status.dcode",hide:true}, 
						{ display: "终止日期", name:"endDate",width: 85}, 
						{ display: '审核状态',name: 'checkStatus',width: 70,render: function(data){
							var text = data.checkStatus;
							if(text=='1')
								return "已审核";
							else
								return "未审核";
						}},
						{ display: "审核人", name:"checkUser",width: 90},
						{ display: "审核日期", name:"checkDate",width: 85},
						{ display: "附件信息", name:"isAnnex",width:50,render: function(data){
							var text = "";
							if(data.isAnnex == "Y"){
								text = "已上传附件";
							}else
								text = "未上传附件";
							return text;
						}}
              		],
                    url:'contract!loadByPage',
                    exportBtn: true,
			        exportUrl: 'contract!exportFile'
             }
          
           var glist=$.pap.createFormGridList({ toolbar:toolBarOption,form:form,grid:gridoption});
           grid=glist.getGrid();
        });

        // 获得选中数据行
        function f_select(){
        	return grid.getSelectedRows();
        }

    </script>
</head>
<body>
</body>
</html>
