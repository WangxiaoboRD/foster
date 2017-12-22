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
							 $.pap.addTabItem({ 
	   	   	            	   	tabid: 'feedTransferAdd',
   	   	      			  	   	text: '药品转接单/新增',
   	   	      			 	   	url: 'drug_transfer_add.jsp'
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
		   	    	        	tabid: 'drugTransferModify'+grid.selected[0]['id'],
			   	    	        text: '药品转接单/修改', 
			   	    	        url: 'drug_transfer!loadUpdateById?id='+grid.selected[0]['id']
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
   	   	      							url: "drug_transfer!delete",
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
			   	    				tabid: 'drugTransfer'+grid.selected[0]['id'],
		   	   	    				text: '药品转接单【'+grid.selected[0]['id']+'】',
		       	   					params:{ 'ids':ids,'id':grid.selected[0]['id']},
		   	   	    				url: 'drug_transfer!loadDetailById?id='+grid.selected[0]['id']
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
	   		      							url: "drug_transfer!check",
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
			   	   	            $.ligerDialog.confirm('确认要撤销该单据吗?', function(data) {
	 	   	          				if(data){
		 	   	          				$.ligerui.ligerAjax({
	   		      							type: "POST",
	   		      							async:  false,
	   		      							url: "drug_transfer!cancelCheck",
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
	 	   			       }} 
                   		]
             	};

        	 form = {
                 labelWidth: 80,
                 fields:[
						{ display: "转接单号", name:"e.id", type:"text" },
						{ display: "养殖公司", name:"e.company.id", type:"select", options:{
						    url:'company!load',
						    valueField: 'id', 
					        textField: 'name',
					        keySupport:true,
					        selectBoxHeight: 200,
							selectBoxWidth: 180,
							onSelected: function (nv){
								var _farmer = liger.get("outFarmer");
								if(_farmer)
									_farmer.setValue("");
							} 
						}},
						{ display: "转出代养户", name:"e.outFarmer.id",comboboxName: 'outFarmer', type:"select", options:{
							url: 'farmer!loadByEntity',
			                valueField: 'id',
			                textField: 'name', 
			                selectBoxHeight: 180,
			                keySupport:true,
			                triggerToLoad: true,
			                onBeforeOpen: function() {
			                	var g = this;
			                	g.setParm('e.company.id', $("input[id='e.company.id']").val());
			                }
						}},
						{ display: "转入代养户", name:"e.inFarmer.id",comboboxName: 'inFarmer', type:"select", options:{
							url: 'farmer!loadByEntity',
			                valueField: 'id',
			                textField: 'name', 
			                selectBoxHeight: 180,
			                keySupport:true,
			                triggerToLoad: true,
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
						{ display: "转接时间", name: "e.tempStack.startTime", type: "date", space: 10 },
		                { display: "至", type: "label", space: 10 },
		                { hideLabel: true, name: "e.tempStack.endTime", type: "date"}
	    			]
            	}

        	 var gridoption={
                     columns:[
                        { display: '转接单号',name: 'id',width:90},
	    				{ display: '养殖公司',name: 'company.name',width:110},
	    				{ display: '转出代养户',name: 'outFarmer.name',width:110 },
	    				{ display: '转入代养户',name: 'inFarmer.name',width:110 },
	    				{ display: '转接时间',name: 'registDate',width:100},
	    				{ display: '转出批次',name: 'outBatch.batchNumber',width:100},
	    				{ display: '转入批次',name: 'inBatch.batchNumber',width:100},
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
	    				{ display: '审核人',name: 'checkUser',width:80},
	    				{ display: '审核日期', name: 'checkDate',width:90},
	    				{ display: '创建人',name: 'createUser',width:80},
	    				{ display: '创建时间', name: 'createDate',width:90}
              		],
                    url:'drug_transfer!loadByPage',
                    exportBtn: true,
			        exportUrl: 'drug_transfer!exportFile'
             }
            var glist=$.pap.createFormGridList({ toolbar:toolBarOption,form:form,grid:gridoption});
            grid=glist.getGrid();
        });
    </script>
</head>
<body>
</body>
</html>
