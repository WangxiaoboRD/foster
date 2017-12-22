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
                 labelWidth: 60,
                 space: 20,
                 fields:[
					 { display: "代养户id", name: "e.farmer.id", type: "hidden"},
					 { display: "批次id", name: "e.batch.id", type: "hidden"},
    	 			 { display: "饲料类型", name:"e.feed.feedType.id", type:"select",options:{
 	 				    url:'feed_type!loadByEntity',
 	 				    keySupport:true,
   		        		valueField: 'id', 
   						textField: 'name',
   						alwayShowInDown: true,
 			      		selectBoxHeight: 100,
 			      		onBeforeOpen: function() {
		                	var g = this;
		                	g.setParm('e.materialType.dcode', 'feed');
		                }
	  		         }},
    	 			 { display: "名称", name: "e.feed.name", type: "text" },
    	 			 { display: "编码", name: "e.feed.code", type: "text" },
	    			]
            	}
        	 var gridoption={
                     columns:[
                        { display: '饲料id',name: 'feed.id',hide:true},
                        { display: '饲料名称',name: 'feed.name',width:90},
                        { display: '饲料编码',name: 'feed.code',width:90},
                        { display: '饲料类型',name: 'feed.feedType.name',width:90},
                        { display: '包装方式',name: 'feed.packForm',width:90,render:function(data){
                            if(data.feed.packForm==1)
                                return '散装';
                            else
                                return '袋装';
                        }},
	    				{ display: '饲料规格',name: 'feed.spec',width:90},
	    				{ display: '数量',name: 'quantity',width:90},
	    				{ display: '单位',name: 'feed.unit.value',width:90},
	    				{ display: '副单位数量',name: 'subQuantity',width:90},
	    				{ display: '副单位',name: 'feed.subUnit.value',width:90},
	    				{ display: '换算比值',name: 'feed.ratio',hide:true},
              		],
                    url:'feed_warehouse!loadByPage'
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
