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
	<script language="javascript" src="../ligerUI/original/Lodop6/LodopFuncs.js"></script>
	<script language="javascript" src="../ligerUI/original/Lodop6/PrintBuyOrderTemplet.js"></script>

    <script type="text/javascript">
		// 表格
        var grid = null;
        $(function () {
            var toolbaroption={
                    items:[
                           ]}
        	var formoption = {
                 labelWidth: 70,
                 fields:[
						{ display: "养殖公司", name:"e.company.id", type:"select", options:{
						    url:'company!load',
						    valueField: 'id', 
					        textField: 'name',
					        keySupport:true,
					        selectBoxHeight: 200,
							selectBoxWidth: 180,
							triggerToLoad: true,
							onSelected:function(nv){
								var _material=liger.get("material");
								if(_material)
									_material.setValue("");
						    }
						}},
						{ display: "名称", name:"e.material.id", type:"select" ,comboboxName: 'material', options:{
						    url:'material!loadByEntity',
						    valueField: 'id', //关键项
						   	textField: 'name',
							keySupport:true,
							alwayShowInDown: true,
						    selectBoxHeight: 200,
						    triggerToLoad: true,
						    autocomplete: true,
						    onBeforeOpen: function() {
			                	var g = this;
			                	g.setParm('e.company.id', $("input[id='e.company.id']").val());
			                }
						}}
	    			]
            	}
        	 var gridoption={
                     columns:[
                        { display: '其他物料库存id',name: 'id', width: 100,hide:true },
                        { display: '其他物料编码',name: 'material.code', width: 100},
	    				{ display: '其他物料名称',name: 'material.name', width: 100},
	    				{ display: '数量',name: 'quantity'},
	    				{ display: "单位", name: 'material.unit.value'},
	    				{ display: '副单位数量',name: 'subQuantity'},
	    				{ display: "副单位", name: 'material.subUnit.value'},
	    				{ display: '养殖公司',name: 'company.name', width: 150 },
	    				{ display: '创建人',name: 'createUser' , width: 100},
	    				{ display: '创建时间', name: 'createDate', width: 100}
              		],
                    url:'material_warehouse!loadByPage'
                    
                }
           var glist=$.pap.createFormGridList({toolbar:toolbaroption,form:formoption,grid:gridoption});
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
