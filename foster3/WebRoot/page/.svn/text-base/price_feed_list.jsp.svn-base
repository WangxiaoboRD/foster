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
                 labelWidth: 70,
                 space: 20,
                 fields:[
					 { display: "养殖公司编码", name: "e.company.id", type: "hidden"},
    	 			 { display: "名称", labelWidth: 40, name: "e.name", type: "text" },
    	 			 { display: "编码", labelWidth: 40, name: "e.code", type: "text" },
    	 			 { display: "饲料阶段", name: "e.feedType.id", type:"select",comboboxName: 'feedType',validate: { required: true }, options:{
              			url:'feed_type!loadByEntity',
		        		keySupport:true,
		        		valueField: 'id', 
						textField: 'name',
						alwayShowInDown: true,
						autocomplete: true,
						triggerToLoad: true,
						onBeforeOpen: function() {
		                	var g = this;
		                	g.setParm('e.company.id', $("input[id='e.company.id']").val());
            			}
                	  }}
	    			]
            	}

        	 var gridoption={
                     columns:[
                        { display: '饲料编码',name: 'id',hide:true},
                        { display: '饲料名称',name: 'name'},
                        { display: '饲料编码',name: 'code'},
                        { display: '饲料阶段',name: 'feedType.name'},
                        { display: "饲料包装", name: 'packForm',render: function(data){
                			 if(data['packForm']=='1')
	                        	return '散装';
	                        else if(data['packForm']=='2')
	                        	return '袋装';
	    				}},
	    				{ display: '饲料规格',name: 'spec'},
	    				{ display: '饲料单位',name: 'unit.value'},
	    				{ display: '原始定价(元)',name: 'lastPrice'}
              		],
                    url:'feed!loadByPageOrderPrice'
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
