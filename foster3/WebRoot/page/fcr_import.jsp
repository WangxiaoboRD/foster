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
    <script src="../ligerUI/ligerUI/js/ligerui.all.js" type="text/javascript"></script>       
    <script src="../ligerUI/ligerUI/js/plugins/ligerUtil.js" type="text/javascript"></script>    
	<script src="../ligerUI/ligerUI/js/pap.js" type="text/javascript" ></script>
	<script src="../ligerUI/ligerUI/js/template/ligerTemplate.js" type="text/javascript"></script>
	<script src="../ligerUI/ligerUI/js/template/ligerXEFormGridSave.js" type="text/javascript"></script>
	<script src="../ligerUI/ligerUI/js/plugins/ajaxfileupload.js" type="text/javascript"></script>
    <script type="text/javascript">
		var v;
        var form = null;
        var grid = null;
        $(function(){
        	var groupicon = "../ligerUI/ligerUI/skins/icons/communication.gif";
        	 // 工具条
    		var toolBarOption = { 
   	    		items: [
				  { text: '模板下载', icon: 'down', click: function() {
					  $.download($('form'), 'fcr!downloadTemplate');
				  }}
			  ]
        	 };
	  		 var formOption = {
	  		 	 url: "fcr!importFile",	// 此处也是必须的
	  			 labelWidth: 100, 
	  			 fileUpload: true, 
           		 space:20,
	  			 fields: [
  			        { display: "选择文件", name: "doc", type: "file",group:"选择文件",groupicon: groupicon, validate: { required: true },newline: true,width: 200}
               	 ],
              	 buttons:[
 					 { text: '上传', icon: '../ligerUI/ligerUI/skins/icons/up.gif', click: function(item){
 						$("form").submit();
 						if(!v.valid()){
 							$.ligerDialog.warn('请选择要上传的文件！');
 						    return;
 	 					}
 						var file = $("#doc").val();
 						if (!/\.(xls)$/i.test(file) && !/\.(xlsx)$/i.test(file)) {
 							$.ligerDialog.warn('请选择Excel文件进行上传！');
 							return;
 						}

 						$("#loading").ajaxStart(function () {
 					        $(this).show();
 					        if ($.ligerDialog) {
 								$.ligerDialog.waitting('系统正在处理，请稍候......');
 							}
 					    }).ajaxComplete(function () {
 					    	if( $.ligerDialog ){
 								$.ligerDialog.closeWaitting();
 							}
 					        $(this).hide();
 					    });
 						$.ajaxFileUpload({
				    		 url: 'fcr!importFile',
				    		 secureuri: false,
				    		 fileElementId: 'doc',
				    		 dataType: "json",
							 //data:{"e.company.id":$("input[id='e.company.id']").val()},
							 success: function(result, textStatus){
	 							if (result != '') {
		 							grid.loadData(result);
	 								tip = $.ligerDialog.tip({ title: '提示信息', content: '导入成功！' });
	 								window.setTimeout(function(){ 
	 									tip.close();
	 								} ,2000);
		 						}else {
		 							grid.loadData({ Rows:[], Total: 0 });
			 					}
							 }
			    		});
 					 }}
	  		     ]
             };

	  		 var gridOption={
	  		  		 title: '数据表格',
	  		  		 enabledEdit: false,
	  		  		 checkbox: false,
                     columns:[
						 { display: '猪苗重', name: 'pigletWei'},
						 { display: '出猪重', name: 'pigWei'},
	                     { display: '料肉比', name: 'fcr'}
              		]
             }
              var template =$.pap.createEFormGridSave({toolbar: toolBarOption, form: formOption, grid: gridOption});
	  		  form = template.getForm() ;
	  		  v = form.validateForm() ;
	  		  grid = template.getGrid();
        });
    </script>
</head>
<body>
	<div id="loading" class="l-sumbit-loading" style="display: none;"></div>
</body>
</html>

