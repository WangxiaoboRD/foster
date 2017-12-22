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
	<style type="text/css">
			.toolbar-pap {
			    height: 0px;
			}
			.l-form ul {
			     clear: both; 
			     margin-top: 0px; 
			     margin-bottom: 0px; 
			     overflow: hidden; 
			}
		</style>
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
    	 			 { display: "药品类型", name:"e.drug.drugType", type:"select" ,comboboxName: 'drugType', options:{
							data: [{ text: '疫苗', id: '1' },
        			               { text: '药品', id: '0' }],
					 }},
    	 			 { display: "名称", name: "e.drug.name", type: "text" },
    	 			 { display: "编码", name: "e.drug.code", type: "text" },
	    			]
            	}
        	 var gridoption={
                     columns:[
                        { display: '药品id',name: 'drug.id',hide:true},
                        { display: '药品类型',name: 'drug.drugType',width:90,hide:true,render:function(data){
	                        if(data['drug.drugType']==1)
	                        	return '疫苗';
	                        else
	                        	return '药品';
                        }},
                        { display: '系统编码',name: 'drug.sysCode',width:90,hide:true},
                        { display: '药品编码',name: 'drug.code',width:90},
                        { display: '药品名称',name: 'drug.name',width:90},
	    				{ display: '药品规格',name: 'drug.spec',width:90},
	    				{ display: '药品厂家',name: 'drug.supplier',width:180,align:"left"},
	    				{ display: '数量',name: 'quantity',width:90},
	    				{ display: '单位',name: 'drug.unit.value',width:90},
	    				{ display: '副单位数量',name: 'subQuantity',width:90,hide:true},
	    				{ display: '副单位',name: 'drug.subUnit.value',width:90,hide:true},
	    				{ display: '换算比值',name: 'drug.ratio',hide:true},
              		],
              		usePager: false,
                    url:'drug_warehouse_far!loadByCodes'
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
