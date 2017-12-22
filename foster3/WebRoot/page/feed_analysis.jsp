<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ taglib prefix="s" uri="/struts-tags" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="content-type" content="text/html; charset=utf-8" />
	<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
	<meta content='width=330, height=400, initial-scale=1' name='viewport' />
	<link rel="icon" href="favicon.ico" type="image/x-icon" />
	<title>${initParam.title}</title>
	<link href="../ligerUI/ligerUI/skins/Aqua/css/ligerui-all.css" rel="stylesheet" type="text/css" />
	<link href="../ligerUI/ligerUI/skins/ligerui-icons.css" rel="stylesheet" type="text/css" />	
    <script src="../ligerUI/jquery/jquery-1.6.4.js" type="text/javascript"></script>
	<script src="../ligerUI/jquery-validation/jquery.form.js" type="text/javascript"></script> 
	<script src="../ligerUI/jquery-validation/jquery.validate.js" type="text/javascript"></script> 
    <script src="../ligerUI/jquery-validation/jquery.metadata.js" type="text/javascript"></script>
    <script src="../ligerUI/jquery-validation/messages_cn.js" type="text/javascript"></script>    
    <script src="../ligerUI/ligerUI/js/ligerui.all.js" type="text/javascript"></script>       
	<script type="text/javascript" src="../ligerUI/ligerUI/js/plugins/XDialog.js"></script>
	<script type="text/javascript" src="../ligerUI/ligerUI/js/plugins/ligerUtil.js"></script>
	<script src="../ligerUI/ligerUI/js/template/ligerTemplate.js" type="text/javascript"></script>
	<script src="../ligerUI/ligerUI/js/template/ligerXFormGridList.js" type="text/javascript"></script>
	<script type="text/javascript" src="../ligerUI/ligerUI/js/pap.js"></script>
	<script language="javascript" src="../original/Lodop6/LodopFuncs.js"></script>
	<script language="javascript" src="../original/Lodop6/PigStockTemplet.js"></script>
	<script src="../original/echarts-2.2.7/build/dist/echarts.js"></script>
	
    <script type="text/javascript">
        var grid = null;
        var form = null;
           $(function () {
                var toolBarOption = { 
   	                   items: [
                   			{ text: '查询条件', icon: 'search', click: function(){
                   				var display = $("form").css("display");
                   				if (display == 'none') {
	                   				$("form").css({ display: 'block' });
                   				}else {
                   					$("form").css({ display: 'none' });
                   				}
                   				grid.setHeight(grid.options.height);
                            }},
                            { line:true },
                            { text: '导出', icon: 'export', click: function() {
                            	$.exportFile($('form'), grid, 'feed_bill!exportFeedAnalysis');
  					        }},
                  		]
             	};
                var formOption={
                    labelWidth: 80, 
                    inputWidth: 150,
	                fields:[
						{ display: "养殖公司", name:"e.company.id", comboboxName: 'e.company.name', newline: true, validate: { required: true },type:"select", options:{
						    url:'company!load',
						    valueField: 'id', 
						    textField: 'name',
							selectBoxHeight: 200,
							selectBoxWidth: 200,
							onSelected:function(nv){
								var _farm=liger.get("farmer");
								if(_farm)
									_farm.setValue("");
								var _batch=liger.get("batch");
						        if(_batch)
							        _batch.setValue("");
					        }
						}},
						{ display: "代养户", name:"e.farmer.id", comboboxName: 'farmer', newline: true,validate: { required: true }, type:"select", options:{
						    url:'farmer!loadByEntity',
						    valueField: 'id', 
						    textField: 'name',
							selectBoxHeight: 200,
							selectBoxWidth: 200,
							triggerToLoad: true,
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
						{ display: "批次", name:"e.batch.id", comboboxName: 'batch', newline: true,validate: { required: true },type:"select", options:{
						    url:'batch!loadByEntity',
						    valueField: 'id', 
						    textField: 'batchNumber',
							selectBoxHeight: 200,
							selectBoxWidth: 200,
							triggerToLoad: true,
							onBeforeOpen: function() {
			                	var g = this;
			                	g.setParm('e.company.id', $("input[id='e.company.id']").val());
			                	g.setParm('e.farmer.id', $("input[id='e.farmer.id']").val());
			                }
						}},
				    ],
					 buttons:[
	                    { text:'查询', icon: '../ligerUI/ligerUI/skins/icons/search2.gif', width: 70, newline: true, click:function(item){
	                    	var arra = $('form').formToArray();
	                    	if(grid){
	                    		grid.setParms(arra);
	                    		grid.options.newPage=1;
			           		    grid.loadData(true);
			           		 	$("form").css( { display: 'none' });
			           		 	grid.setHeight(grid.options.height);
	                    	}
	                    }}
	                 ]
               	}
           		var columns=[
                       { display: '养殖公司',name:'companyName'},	
                       { display: '代养户',name:'farmerName'},
                       { display: '批次',name:'batchNumber'},
                       { display: '日期',name:'feedDate'},
                       { display: '饲养天数',name:'feedDays',align:"right"},
                       { display: '日龄',name:'dayAge',align:"right"},
                       { display: '标准喂料类别',name:'sdFeedType',width:120},
                       { display: '标准喂料量',name:'sdFeedQty',align:"right"},
                       /**{ display: '日龄标准累计喂料量',name:'sdTolFeedQty',align:"right"},*/
                       { display: '实际喂料类别',name:'acFeedType'},
                       { display: '实际喂料量',name:'acFeedQty',align:"right"},
                       { display: '实际累计喂料量',name:'acTolFeedQty',align:"right"},
           	         ];
             	var gridoption={
                    title: '喂料分析',
             		url:'feed_bill!loadFeedAnalysisByPage',
                    columns: columns,
                    checkbox: false,
                    usePager: true,
                    allowHideColumn: false // 允许隐藏列
                }
              var glist=$.pap.createFormGridList({ toolbar:toolBarOption,form:formOption,grid:gridoption});
              grid=glist.getGrid();
              form = glist.getForm();
        });
    	
    </script>
 
</head>
<body>
</body>
</html>
