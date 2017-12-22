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
    <script src="../ligerUI/ligerUI/js/plugins/ligerUtil.js" type="text/javascript"></script>  
	<script src="../ligerUI/ligerUI/js/template/ligerTemplate.js" type="text/javascript"></script>
	<script src="../ligerUI/ligerUI/js/template/ligerXFormGridList.js" type="text/javascript"></script>

    <script type="text/javascript">
    	// 表格
        var grid = null;
        $(function () {
        	   var toolBarOption = { 
   	                   items: [
	   	                 { text: '新增', icon: 'add', click: function() {
               	   			 $.pap.addTabItem({
	   	   	      			  	 text:'系统参数/新增',
               	   				 url: 'sys_config_add.jsp'
   	   	       				 });
	   	   	              }},
	   	   	              { line:true },
	   	   	              { text: '修改', icon: 'modify', expression:'!=1', disabled:true, click: function(item){
			   	   	           if(! jQuery.isEmptyObject(grid.selected) ){
		         	   	    		if(grid.selected.length > 1){
		         	   	    			$.ligerDialog.warn('请选择一条要修改的记录！');
		         	   	    		}
		         	   	    		else if(grid.selected.length ==1){
		         	   	    			$.pap.addTabItem({
			               	   			 	 id:'business_paramUpdate', 
				   	   	      			  	 text:'系统参数/修改',
			               	   				 url: 'sys_config!loadUpdateById?id='+grid.selected[0]['id']
			   	   	       				 });
		         	   	    			
		         	   	    		}
		         	   	    }else{
		         	   	    	$.ligerDialog.warn('至少选择一条要修改的记录！');
		         	   	    }
	   	   			      }},
	   	   			      /*{ line:true },
	   	   	              { text: '查看', icon: 'view', expression:'!=1', disabled:true, click: function(item){
	   	   	           		 	var selectedRow = item.selectGrid.selected; 
			   	   	           	if (selectedRow.length == 0) {
		   	   	           			$.ligerDialog.warn('请选择要查看的记录！');
		   	   	           			return;
				   	   	         }
		   	    				$.pap.addTabItem({ 
		   	   	    				text: '业务参数/查看', 
		   	   	    				url: 'parameter_set!loadDetailById?id='+grid.selected[0]['id'],
		   	   	    			});
	   	   			      }},*/
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
	   	   	      							url: "sys_config!delete",
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
			   	    				tabid: 'sysConfigOrder'+grid.selected[0]['id'],
		   	   	    				text: '系统参数【'+grid.selected[0]['id']+'】',
		       	   					params:{ 'ids':ids,'id':grid.selected[0]['id']},
		   	   	    				url: 'sys_config!loadDetailById?id='+grid.selected[0]['id']
		   	   	    			});
		   	   			   }}
                   		]
             	};

        	 form = {
                	 labelWidth: 70,
                     fields:[
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
                        { display: '编号', name: 'id',hide:true},
                        { display: '养殖公司', name: 'company.name',width:100 },
                        { display: '药品价格系数', name: 'coefficient',width:100},
                        { display: '创建时间', name: 'createDate',width:100}
              		],
                    url:'sys_config!loadByPage'
             }
           var glist = $.pap.createFormGridList({ toolbar:toolBarOption,form:form,grid:gridoption});
           grid = glist.getGrid();
        });
    </script>
</head>
<body><br />
</body>
</html>
