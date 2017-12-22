<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head><title>
</title>
    <link href="../ligerUI/ligerUI/skins/Aqua/css/ligerui-all.css" rel="stylesheet" type="text/css" />
	<link href="../ligerUI/ligerUI/skins/ligerui-icons.css" rel="stylesheet" type="text/css" />	
    <script src="../ligerUI/jquery/jquery-1.6.4.js" type="text/javascript"></script>
	<script src="../ligerUI/jquery-validation/jquery.form.js" type="text/javascript"></script> 
	<script src="../ligerUI/jquery-validation/jquery.validate.js" type="text/javascript"></script> 
    <script src="../ligerUI/jquery-validation/jquery.metadata.js" type="text/javascript"></script>
    <script src="../ligerUI/jquery-validation/messages_cn.js" type="text/javascript"></script> 
	<script src="../ligerUI/ligerUI/js/ligerui.all.js" type="text/javascript"></script>       
	<script src="../ligerUI/ligerUI/js/pap.js" type="text/javascript" ></script>
	<script src="../ligerUI/ligerUI/js/template/ligerTemplate.js" type="text/javascript"></script>
	<script src="../ligerUI/ligerUI/js/template/ligerXEFormGridSave.js" type="text/javascript"></script>
    <script type="text/javascript">
	    var v;
	    var form = null;
	    var grid = null;
	    $(function(){
	    	var groupicon = "../ligerUI/ligerUI/skins/icons/communication.gif";
	  		 var formOption = {
	  		 	 url: "role!saveAndDetail",	// 此处也是必须的
	  			 labelWidth: 80, 
	       		 space:20,
	  			 fields: [
			        { display: "角色编码", name: "e.roleCode", group: "设置角色", groupicon: groupicon,validate: { required: true }, newline: true, type: "text" }, 
	                { display: "角色名称", name: "e.roleName", newline: true, type: "text", validate:{ required: true }},
	                { display: "养殖公司", name:"e.company.id",  newline: true,validate: { required: true },type:"select", options:{
					    url:'company!load',
					    valueField: 'id', 
				        textField: 'name',
				        keySupport:true,
				        selectBoxHeight: 130,
						onSelected: function (nv){
							var _technician = liger.get("technician");
							if(_technician)
								_technician.setValue("");
						} 
					}},
	                { display: "角色状态", name: "e.roleStatus", newline: true,type: "select", options:{               
				       	data: [{ text: '启用', id: '1' },
			                  { text: '停用', id: '2' }],          
				       	name: "e.roleStatus",
				       	value: '1',
				       	selectBoxHeight: 60     
	                }}
	           ]};
	
	  		 var gridOption={
	  				submitDetailsPrefix: 'e.authObjSet',
	                 columns:[
	                    { display: '权限对象编码',name: 'code' },
	    				{ display: '权限对象名称',name: 'name' },
	    				{ display: '权限对象类型', name: 'type', render: function(data){
		    				var text = data.type;
	    					switch(data.type){
	    						case 'FUNCTION': text = "功能权限"; break;
	    						case 'DATA': text = "数据权限"; break;
	    						case 'BUSINESS': text = "业务权限"; break;
	    					}
	    					return text;
	    				}}
	          		],
	                toolbar: {
	                   items: [
	  		                { text: '添加', icon: 'add', click: addClick },
	  		                { line: true },	                
	  		                { text: '删除', icon:'delete', click: deleteClick }
	  	                ]
	  	            }
	         }
	         
	          var template =$.pap.createEFormGridSave({form: formOption, grid: gridOption});
	  		  form = template.getForm() ;
	  		  v = form.validateForm() ;
	  		  grid = template.getGrid();

	  		// 添加按钮窗口
	          function addClick(item) {
	              // 提交form后valid才会有效
	         	$("form").submit();
	         	if (! v.valid()) {
	         		$.ligerDialog.error('请先录入角色信息！');
	 	    		return;
	             }
	  		    
	         	 $.ligerDialog.open({ 
	       			title:'搜索',
	       			url: 'auth_obj_list.jsp', 
	       			height: 500,
	       			width: 800, 
	       			onLoaded:function(param){
	      		       var documentF = param.contentDocument || param.document ;//兼容IE 和 FF
	      		           $('div.toolbar-pap',documentF).hide();
	      		       },
	       			buttons: [ 
	       				{ text: '选择', onclick:  function (item, dialog) { 
	       					var selRows = dialog.frame.f_select();
	       					if (selRows.length < 1) {
	       						$.ligerDialog.warn('请至少选择一条记录进行操作');
	 	           			}else {
 								  $(selRows).each(function(index, data){
	 									var isRepeat = false;
	 									$(grid.rows).each(function(index, data1){
	 										if (data1.code == data.code) {
	 											isRepeat = true;
	 											return false;
	 										}
	 									});
	 									if (! isRepeat) {
	 		           						grid.addRow({
	 		           			                'code': data.code,
	 		           			                'name': data.name,
	 		           			                'type': data.type
	 		           			            });
	 									}
		 	           					dialog.close();
 								  });
	        					}
	       					}}, 
	  						{ text: '取消', onclick: function (item, dialog) { dialog.close(); }}
	   					] 
	   				 });
	          }

	          // 删除按钮
	          function deleteClick(item) {
	              var selRows = grid.getSelecteds();
	              if (selRows.length == 0) {
	             	 $.ligerDialog.error('请选择要删除的记录！');
	 				 return;
	              }
	              //	删除选中的行
	              grid.deleteSelectedRow(); 
	          }
	    });
    </script>
</head>
	<body>
	</body>
</html>
