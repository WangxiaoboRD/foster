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
								var _farmer=liger.get("farmer");
								if(_farmer)
									_farmer.setValue("");
								var _batch=liger.get("batch");
								if(_batch)
									_batch.setValue("");
								var _drug=liger.get("drug");
								if(_drug)
									_drug.setValue("");
						    }
						}},
						{ display: "代养户", name:"e.farmer.id", type:"select" ,comboboxName: 'farmer', options:{
						    url:'farmer!loadByPinYin',
						    valueField: 'id', //关键项
						   	textField: 'name',
							keySupport:true,
							alwayShowInDown: true,
							triggerToLoad: true,
							autocomplete: true,
						    selectBoxHeight: 200,
						    onBeforeOpen: function() {
			                	var g = this;
			                	g.setParm('e.company.id', $("input[id='e.company.id']").val());
			                },
			                onSelected:function(nv){
								var _batch=liger.get("batch");
								if(_batch)
									_batch.setValue("");
						    }
			                
						}},
						{ display: "批次", name:"e.batch.id", type:"select" ,comboboxName: 'batch', options:{
						    url:'batch!loadByEntity',
						    valueField: 'id', //关键项
						   	textField: 'batchNumber',
							keySupport:true,
							alwayShowInDown: true,
						    selectBoxHeight: 200,
						    triggerToLoad: true,
						    autocomplete: true,
						    onBeforeOpen: function() {
			                	var g = this;
			                	g.setParm('e.company.id', $("input[id='e.company.id']").val());
			                	g.setParm('e.farmer.id', $("input[id='e.farmer.id']").val());
			                }
						    
						}},
						{ display: "药品类型", name:"e.drug.drugType", type:"select" ,comboboxName: 'drugType', newline:true,options:{
							data: [{ text: '疫苗', id: '1' },
        			               { text: '药品', id: '0' }],
						}},
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
						}},
						{ display:"药品编码",name:"e.drug.code",type:"text"}
	    			]
            	}
        	 var gridoption={
                     columns:[
                        { display: '代养户',name: 'farmer.name', width: 100 },
                        { display: '批次',name: 'batch.batchNumber', width: 100 },
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
	    				{ display: '创建人',name: 'createUser' , width: 100},
	    				{ display: '创建时间', name: 'createDate', width: 100}
              		],
                    url:'drug_warehouse_far!loadByPage'
                    
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
