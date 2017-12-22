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
	<script type="text/javascript" src="../ligerUI/ligerUI/js/plugins/XDialog.js"></script>
	<script type="text/javascript" src="../ligerUI/ligerUI/js/plugins/ligerUtil.js"></script>

    <script type="text/javascript">
		// 表格
        var grid = null;
        $(function () {
            var toolbaroption={
                    items:[
								{ text: '导出', icon: 'export', click: function() {
									$.exportFile($('form'), grid, 'drug_warehouse!exportWarehouse');
								}}
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
								var _feedFac=liger.get("feedFac");
								if(_feedFac)
									_feedFac.setValue("");
								var _drug=liger.get("drug");
								if(_drug)
									_drug.setValue("");
						    }
						}},
						{display: "饲料厂", name:"e.feedFac.id",comboboxName:'feedFac',type:"select", options:{
							url: 'feed_fac!loadByEntity',
			                valueField: 'id',
			                textField: 'name', 
			                selectBoxHeight: 150,
			                keySupport:true,
			                triggerToLoad: true,
							onBeforeOpen: function() {
			                	var g = this;
			                	g.setParm('e.company.id', $("input[id='e.company.id']").val());
			                }
						}},
						{ display: "药品类型", name:"e.drug.drugType", type:"select" ,comboboxName: 'drugType', options:{
							data: [{ text: '疫苗', id: '1' },
        			               { text: '药品', id: '0' }],
						}},
						{ display: "药品编码", name:"e.drug.code", type:"text" },
						{ display: "药品名称", name:"e.drug.id", type:"select" ,comboboxName: 'drug', options:{
						    url:'drug!loadByEntity',
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
			                	g.setParm('e.drug.drugType', $("input[id='e.drug.drugType']").val());
			                }
						}}
	    			]
            	}
        	 var gridoption={
                     columns:[
                        { display: '药品库存id',name: 'id', width: 100,hide:true },
                        { display: '药品类型',name: 'drug.drugType', width: 100,render:function(data){
                        	if(data['drug.drugType']==0)
                        		return'药品';
                        	else
                        		return'疫苗';
                        }},
                        { display: '药品编码',name: 'drug.code', width: 100},
	    				{ display: '药品名称',name: 'drug.name', width: 100},
	    				{ display: '数量',name: 'quantity'},
	    				{ display: "单位", name: 'drug.unit.value'},
	    				{ display: '副单位数量',name: 'subQuantity'},
	    				{ display: "副单位", name: 'drug.subUnit.value'},
	    				{ display: '养殖公司',name: 'company.name', width: 150 },
	    				{ display: '所属饲料厂',name: 'feedFac.name', width: 150 },
	    				{ display: '创建人',name: 'createUser' , width: 100},
	    				{ display: '创建时间', name: 'createDate', width: 100}
              		],
                    url:'drug_warehouse!loadByPage'
                    
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
