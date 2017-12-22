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

    <script type="text/javascript">
    	// 表格
        var grid = null;
        $(function () {
        	   var groupicon = "../ligerUI/ligerUI/skins/icons/search2.gif";
        	   var toolBarOption = { 
   	                   items: [
	   	                    { text: '新增', icon: 'add', click: function(){
	   	                    	$.pap.addTabItem({
		   	   	      			  	 text:'新增生长标准',
	               	   				 url: 'grow_stand_add.jsp'
	   	   	       				 });
                           	}},
                            { line: true },
      		                { text: '复制新增', icon: 'copy' ,expression:'!=1', disabled:true, click: function(item) {
                 		    	 if(! jQuery.isEmptyObject(grid.selected) ){
	 		         	   	    		if(grid.selected.length > 1){
	 		         	   	    			$.ligerDialog.warn('请选择一条要复制的记录！');
	 		         	   	    		}
	 		         	   	    		else if(grid.selected.length ==1){
	 		         	   	    			$.pap.addTabItem({
	 				   	   	      			  	 text:'复制新增生长标准',
	 			               	   				 url: 'grow_stand!loadCopyById?id='+grid.selected[0]['id']
	 			   	   	       				 });
	 		         	   	    			
	 		         	   	    		}
	 		         	   	    }else{
	 		         	   	    	$.ligerDialog.warn('至少选择一条要复制的记录！');
	 		         	   	    }
          		            }},
                 		    { line: true },
      		                { text: '修改', icon: 'modify' ,expression:'!=1', disabled:true, click: function(item) {
                 		    	 if(! jQuery.isEmptyObject(grid.selected) ){
	 		         	   	    		if(grid.selected.length > 1){
	 		         	   	    			$.ligerDialog.warn('请选择一条要修改的记录！');
	 		         	   	    		}
	 		         	   	    		else if(grid.selected.length ==1){
		 		         	   	    		
			         	   	    			$.pap.addTabItem({
					   	   	      			  	text:'编辑生长标准',
				               	   				url: 'grow_stand!loadUpdateById?id='+grid.selected[0]['id']
				   	   	       				});
 		         	   	    			
	 		         	   	    		}
	 		         	   	    }else{
	 		         	   	    	$.ligerDialog.warn('至少选择一条要修改的记录！');
	 		         	   	    }
          		            }},
      		                { line: true },
      		                { text: '删除', icon: 'delete', expression:'==0', disabled:true, click: function(item) {
      		                	 if( grid.selected.length > 0 ){
      		                		var delIds = "";
      	   	          				var status = false;
       	   	          				$(grid.selected).each(function(i, item){
       	   	          					delIds += item['id']+",";
       	   	          				});
        	   	   	      	    	$.ligerDialog.confirm('数据删除后不可恢复,你确认要删除?', function(data) {
      	   	   	          				if(data){
      	   	   	          					$.ligerui.ligerAjax({
      	   	   	      							type: "POST",
      	   	   	      							async:  false,
      	   	   	      							url: "grow_stand!delete",
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
		   	   	           			$.ligerDialog.warn('请选择要查看的记录！');
		   	   	           			return;
				   	   	         }
		   	    				$.pap.addTabItem({ 
		   	   	    				text: '生长标准查看', 
		   	   	    				url: 'grow_stand!loadDetailById?id='+grid.selected[0]['id']
		   	   	    				//param: selectedRow 
		   	   	    			});
	   	   			       }}
        		            
   	   	              ]
             	};

        	 form = {
                	 labelWidth: 70,
                     fields:[
                         { display: "养殖公司", name: "e.company.id", width:180,type: "select",
			                  	options:{
			                  	    url:'company!load',
			                  		selectBoxWidth: 180, 
			                  		selectBoxHeight: 150,
			                  		keySupport:true, 
			  					    valueField: 'id', 
			  					    textField: 'name'
			             }}
					    ]
                	}

        	 var gridoption={
                     columns:[
                        { display: '养殖公司', name: 'company.name' },
	    				{ display: '登记时间', name: 'registDate' },
    					{ display: '备    注', name: 'remark'}
              		],
                    url:'grow_stand!loadByPage',
                    exportBtn: true,
			        exportUrl: 'grow_stand!export'
             }
          
           var glist = $.pap.createFormGridList({ toolbar:toolBarOption,form:form,grid:gridoption});
           grid = glist.getGrid();
        });
        
    </script>
</head>
<body>
</body>
</html>
