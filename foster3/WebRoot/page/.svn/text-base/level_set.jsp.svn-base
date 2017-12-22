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
    <script type="text/javascript">
        var grid = null;
        var gridvalue = null;
        $(function () {
        	grid=$("#grid").ligerGrid({
                 columns:[
					{ display: "销售级别编码", name: 'dcode',width:160},
					{ display: "销售级别名称", name: 'value',width:185}
           		],
           	 	data:gridvalue,
                checkbox: true,
                usePager: false,
            	delayLoad:false,
                rownumbers:true,
                alternatingRow:false,
                width: '99.5%',
                height: '99.5%'
            });
        	$.ajax({    
			    url:'bussiness_ele_detail!loadByEntity',// 跳转到 action    
			    data:{'e.bussinessEle.ecode': 'SALELEVEL'},    
			    type:'post',    
			    cache:false,
			    async:false,    
			    dataType:'json',    
			    success:function(data) {  
			       if(data != null && data !=''){
			    	    gridvalue = data;
						grid.loadData(gridvalue);
			       }
			    } 
			});
        });
        // 获得选中数据行
        function f_select(){
        	return grid.getSelectedRows();
        }
    </script>
</head>
<body>
<div id="grid"></div>
</body>
</html>
