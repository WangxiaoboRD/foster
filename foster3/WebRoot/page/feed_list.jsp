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
	   	                  { text: '新增', icon: 'add', click: function(){
                   				$.ligerDialog.open({ 
                   	   				title:'添加饲料',
                   	   				url: 'feed_add.jsp', 
                   	   				height: 500,
                   	   				width: 450, 
                   	   				buttons: [ 
                   	   					{ text: '确定', onclick: function(item, dialog) {
                   	   						var data = dialog.frame.onSave();
		                   	   		    	if(data!=null){
		                   	   		    	$.ligerui.ligerAjax({
		                   	   						type:"POST",
		                   	   						async: false,
		                   	   						url:"feed!save",	
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
	               	   	    			$.ligerDialog.open({
	               							title:'修改饲料',
	               							width:450,
	               							height:500, 
	               							url: 'feed!loadUpdateById?id='+grid.selected[0]['id'],
	               							buttons: [
	               				                {text:'确定',onclick: function(item, dialog) {
	               				                	var data = dialog.frame.onSave();
		                 				       	    	if(data!=null){
		                 				       				$.ligerui.ligerAjax({
		                 				       					url:"feed!modify",
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
	   	   	            	  if( grid.selected.length > 0 ){
	   	   	      	    		$.ligerDialog.confirm('数据删除后不可恢复,你确认要删除?', function(data) {
	   	   	          				var delIds = "";
	   	   	          				$(grid.selected).each(function(i, item){
	   	   	          					delIds += item['id']+",";
	   	   	          				});
	   	   	          				if(data){
	   	   	          					$.ligerui.ligerAjax({
	   	   	      							type: "POST",
	   	   	      							async:  false,
	   	   	      							url: "feed!delete",
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
	   	   			      }}
	   	   	              
                   		]
             	};

        	var form = {
                 labelWidth: 70,
                 space: 20,
                 fields:[
    	 			 { display: "名称", name: "e.name", type: "text" },
    	 			 { display: "编码", name: "e.code", type: "text" },
    	 			 { display: "养殖公司", name:"e.company.id", width:200,type:"select", 
						   options:{
		                	 url:'company!loadType',
		                	 keySupport:true,
		                	 selectBoxWidth: 219,
			        		 valueField: 'id', 
							 textField: 'name'
					 }}
    	 			 
	    			]
            	}

        	 var gridoption={
                     columns:[
                        { display: '饲料id',name: 'id',hide:true},
                        { display: '饲料编码',name: 'code'},
                        { display: '饲料名称',name: 'name' },
                        { display: '饲料阶段',name: 'feedType.name'},
                        { display: '饲料类型',name: 'feedClass.value'},
	    				{ display: '饲料规格',name: 'spec' },
	    				{ display: '饲料包装',name: 'packForm',render:function(data){
	                        if(data.packForm=='1')
	                        	return '散装';
	                        else
	                        	return '袋装';
                        }},
	    				{ display: '饲料单位',name: 'unit.value' },
	    				{ display: '副单位',name: 'subUnit.value' },
	    				{ display: '换算比例',name: 'ratio' },
	    				{ display: '养殖公司',name: 'company.name'}
	    				
              		],
                    url:'feed!loadByPage'
             }
          
           var glist=$.pap.createFormGridList({ toolbar:toolBarOption,form:form,grid:gridoption});

           grid=glist.getGrid();
        });

        function f_select(){
        	return grid.getSelectedRows();
        }
       
    </script>
</head>
<body>
</body>
</html>
