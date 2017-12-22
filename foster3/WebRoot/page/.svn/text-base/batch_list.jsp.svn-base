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
			   	    				tabid: 'batch'+grid.selected[0]['id'],
		   	   	    				text: '批次【'+grid.selected[0]['batchNumber']+'】',
		       	   					params:{ 'ids':ids,'id':grid.selected[0]['id']},
		   	   	    				url: 'batch!loadDetailById?id='+grid.selected[0]['id']
		   	   	    			});
	   	   			      	}},
		   	   			 	{ line: true },
			                { text: '修改', icon: 'modify' ,expression:'!=1', disabled:true, click: function(item) {
	          		    		if(! jQuery.isEmptyObject(grid.selected) ){
		          	   	    		if(grid.selected.length > 1){
		          	   	    			$.ligerDialog.warn('请选择一条要修改的记录！');
		          	   	    		}else if(grid.selected.length ==1){
		          	   	    			$.ligerDialog.open({
		          							title:'修改技术员',
		          							width:380,
		          							height:320, 
		          							url: 'batch!loadUpdateById?id='+grid.selected[0]['id'],
		          							buttons: [
		          				                {text:'确定',onclick: function(item, dialog) {
		          				                	var data = dialog.frame.onSave();
		              				       	    	if(data!=null){
		              				       				$.ligerui.ligerAjax({
		              				       					url:"batch!modify",
		              				       					dataType:"text",
		              				       					data:data,
		              				       					success:function(_data,textStatus){
		              				       						if(_data == 'MODIFYOK'){
		              				       							tip = $.ligerDialog.tip({ title: '提示信息', content: '成功更新一条记录！' });
		              				       							window.setTimeout(function(){ tip.close()} ,2000); 	
		              				       						}
		              				       					},
		              				       					error: function(XMLHttpRequest,textStatus){
		              				       					$.ligerDialog.error('操作出现异常');
		              				       					},
		              				       					complete: function(){ grid.loadData(true);}
		              				       				});
		              				       				
		              				       				dialog.close();
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
	   		            	}}
                   		]
             	};

        	 form = {
                 labelWidth: 70,
                 fields:[
						{ display: "批次号", name:"e.batchNumber", type:"text" },
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
						}}
	    			]
            	}

        	 var gridoption={
                     columns:[
                        { display: "批次id", name:"id",hide:true },
	    				{ display: '养殖公司',name: 'company.name',width:110,frozen: true},
	    				{ display: '饲料厂',name: 'feedFac.name',width:110,frozen: true},
	    				{ display: '代养户',name: 'farmer.name',width:110,frozen: true},
	    				{ display: "批次号", name:"batchNumber",frozen: true},
	    				{ display: '合同号',name: 'contract.code',width:100},
	    				{ display: "开发人员", name:"developMan.name",width: 80},
						{ display: "技术人员", name:"technician.name",width: 80},
	    				{ display: '合同头数', name: 'contQuan',width:90},
	    				{ display: '进猪时间',name: 'inDate',width:80},
	    				{ display: '预计出栏时间',name: 'outDate',width:100},
	    				{ display: '进猪头数',name: 'pigletQuan',width:80},
	    				{ display: '当前头数',name: 'quantity',width:100},
	    				{ display: '死亡头数',name: 'deathQuan',width:100},
	    				{ display: '免责死亡',name: 'otherDeathQuan',width:100},
	    				{ display: '淘汰头数',name: 'eliminateQuan',width:80},
	    				{ display: '出栏头数', name: 'saleQuan',width:90},
	    				{ display: '是否已结算', name: 'isBalance',width:90,render:function(data){
		    				if(data.isBalance=='N')
		    					return '未结算';
		    				else
		    					return'已结算';
	    				}},
	    				{ display: '料肉比', name: 'fcr',width:90}
              		],
                    url:'batch!loadByPage',
                    exportBtn: true,
			        exportUrl: 'batch!exportFile'
             }
            var glist=$.pap.createFormGridList({ toolbar:toolBarOption,form:form,grid:gridoption});
            grid=glist.getGrid();
        });
    </script>
</head>
<body>
</body>
</html>
