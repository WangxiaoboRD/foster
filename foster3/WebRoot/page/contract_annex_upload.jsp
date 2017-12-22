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
	<script src="../ligerUI/ligerUI/js/pap.js" type="text/javascript" ></script>
	<script src="../ligerUI/ligerUI/js/template/ligerTemplate.js" type="text/javascript"></script>
	<script src="../ligerUI/ligerUI/js/template/ligerXSFormCreate.js" type="text/javascript"></script>
    <script src="../ligerUI/ligerUI/js/plugins/ajaxfileupload.js" type="text/javascript"></script> 
    <script type="text/javascript">
		var v;
        var form = null;
        $(function(){
	  		 var formOptions = {
	  		 	 url: "contract!upload",
	  			 labelWidth: 80, 
           		 space:20,
           		 fileUpload: true,
	  			 fields: [
	  			      { display: "所属合同", name: "id", type: "hidden" }, 
	  			      { display: "合同名称", name: "name", type: "text",options: {readonly: true}},
	  			      { display: "合同编号",newline: true, name: "code", type: "text",options: {readonly: true}}, 
	  			      { display: "代养农户", name: "farmerid", type: "hidden" },
	  			      { display: "代养农户",newline: true, name: "farmer", type: "text",options: {readonly: true}},
	  	              { display: "选择附件", name: "doc", validate: { required: true }, newline: true, type: "file", width: 260 },
	               ]
	          };
               
              var template =$.pap.createSFormCreate({form: formOptions});
	  		  form = template.getForm();
	  		  v = form.validateForm() ;
        });
        // 上传
     	function upload(dialog, grid){
     		 
     		 $.ajaxFileUpload({
                url:'contract!upload',//用于文件上传的服务器端请求地址
                secureuri:false,//一般设置为false
                fileElementId:'doc',//文件上传空间的id属性  <input type="file" id="file" name="file" />
                data:{"id": $("input[id='id']").val()},
                dataType: 'json',//返回值类型 一般设置为json
                success: function (data, status){  //服务器成功响应处理函数
                   if (data != '') {
                       if(data == '1'){
	                	   tip = $.ligerDialog.tip({ title: '提示信息', content: '附件上传成功！' });
							window.setTimeout(function(){ 
								tip.close()
							    dialog.close();
							    grid.loadData(true);
							} ,2000);
                       }else if(data == '0'){
                    	   $.ligerDialog.error('文件上传失败');
                       }
                   }
                }
	          });
	          return true;
		}  
    </script>
</head>
<body>
</body>
</html>

