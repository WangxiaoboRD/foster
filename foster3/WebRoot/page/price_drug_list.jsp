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
        	 form = {
                 space: 20,
                 fields:[
					 { display: "养殖公司编码", name: "e.company.id", type: "hidden"},
    	 			 { display: "名称", name: "e.name",labelWidth: 40, type: "text" },
    	 			 { display: "编码", name: "e.code",labelWidth: 40, type: "text" },
    	 			 { display: "药品类型",labelWidth: 70,name: "e.drugType", type: "select", options:{
						  keySupport:true,
  	                	  data: [
  	                       	{ text: '药品', id: '0' },
  	               	  		{ text: '疫苗', id: '1' }
  		                 ]
              		}},
	    			]
            	}

        	 var gridoption={
                     columns:[
                        { display: '药品编码',name: 'id',hide:true},
                        { display: '药品名称',name: 'name'},
                        { display: '药品编码',name: 'code'},
                        { display: '系统编码',name: 'sysCode'},
                        
                        { display: "药品类型", name: 'drugType',render: function(data){
                			 if(data['drugType']=='0')
	                        	return '药品';
	                        else if(data['drugType']=='1')
	                        	return '疫苗';
	    				}},
	    				{ display: '药品规格',name: 'spec'},
	    				{ display: '药品单位',name: 'unit.value'},
	    				{ display: '原始定价(元)',name: 'lastPrice'}
              		],
                    url:'drug!loadByPage'
             }
          
           var glist=$.pap.createFormGridList({form:form,grid:gridoption});
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
