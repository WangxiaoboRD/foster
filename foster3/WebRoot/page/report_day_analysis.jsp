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
                            	$.exportFile($('form'), grid, 'batch!exportDayAnalysis');
  					        }}
                  		]
             	};

                var fields = [{ text: '饲料耗用', id: 'feed' },{ text: '饲料入库', id: 'InWare' },
                              { text: '饲料库存', id: 'Warehouse' },{ text: '饲料未拉', id: 'noFeed' } ];
                
                formOption={
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
						{ display: "代养户", name:"e.farmer.id", comboboxName: 'farmer', type:"select", options:{
						    url:'farmer!loadByPinYin',
						    valueField: 'id', 
						    textField: 'name',
							selectBoxHeight: 200,
							selectBoxWidth: 200,
							triggerToLoad: true,
							 autocomplete: true,
							 keySupport:true,
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
						{ display: "批次", name:"e.id", comboboxName: 'batch',newline: true,type:"select", options:{
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
						{ display: "时间", name: "e.tempStack.startTime", type: "date",options: { value: new Date(), showTime: false } },
	    	                
						{ display: '显示字段', name: "e.showColumns", type: "checkboxgroup", newline: true, width: 500, options:{       
	                		rowSize: 4,        
	    			       	data: fields,
	    			       	name: 'e.showColumns',
	    			       	split : ','
	                    }}
				    ],
					 buttons:[
	                    { text:'查询', icon: '../ligerUI/ligerUI/skins/icons/search2.gif', width: 70, newline: true, click:function(item){
	                    	var company = $("input[id='e.company.id']").val();
	      	      		    if(!company){
	      	      				$.ligerDialog.warn('请选择养殖公司');
	      	      				return ;
	      	      		    }

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

           		var columns1 = [
							 { display: '区      域',name:'feedFac',width:90,frozen: true},
           		           	 { display: '养殖公司',name:'company',width:90,hide:true},
           		             { display: '代养户',name:'farmer',width:70,frozen: true},
                             { display: '批次',name:'batch',width:70,frozen: true},
                             { display: '查询日期', name:'sdate',width:90,frozen: true}
           		           	];
        	    var columns2 = [
					   { display: '进猪日期',name:'inDate',width:90},
                       { display: '用户饲养天数',name:'breedDays',width:70},
                       { display: '周龄', name:'weeks',width:70}
               		];
        	   

           		var columns = columns1.concat(columns2);
             	var gridoption={
                    title: '日报',
             		url:'batch!loadDayAnalysisByPage',
                    columns: columns,
                    checkbox: false,
                    usePager: true,
                    allowHideColumn: false // 允许隐藏列
                }

              var glist=$.pap.createFormGridList({ toolbar:toolBarOption,form:formOption,grid:gridoption});
              grid=glist.getGrid();
              form = glist.getForm();

              $("input[name='e.showColumns']").change(function() {
            	  var company = $("input[id='e.company.id']").val();
	      		  if(!company){
	      				$.ligerDialog.warn('请选择养殖公司');
	      				liger.get("e.showColumns").clear();
	      		  }else{
		      		  var names = null;
	      			$.ligerui.ligerAjax({
						type: "POST",
						async:  false,
						url: "feed_type!loadByCor",
						data: {company:company},
						dataType: "json",
						success: function(result, status){
							if(result != "") {
								names = result.Rows;
								//console.dir(names);
							}
						},
						error: function(XMLHttpRequest,status){
							$.ligerDialog.error('操作出现异常');
						},
						complete:function(){}
				   	  })
				   	  //获取showcolumns的值
 					  var value = liger.get("e.showColumns").getValue();
					  //勾选库存时
	      			  if (value.indexOf('Warehouse') != -1) {
					  	//也勾选未拉时
						if (value.indexOf('noFeed') != -1) {
							value = 'feed,InWare,Warehouse,noFeed';
						}else{
							value = 'feed,InWare,Warehouse';
						}
	      			  }
	      			  //勾选未拉时
	      			  if (value.indexOf('noFeed') != -1) {
	      					value = 'feed,InWare,Warehouse,noFeed';
	      			  }
	      			  liger.get("e.showColumns").setValue(value);
				   	  var selCols = [];
		              selCols = selCols.concat(columns1);
	                 
	                  if (value.indexOf('feed') != -1) {
		                  var feed = [];
	                	  for(var i=0;i<names.length;i++){
							  var name = names[i];
							  feed.push({ display:name , name: name,width:70,align:'right' });
						  }
						  feed.push({ display:'合计' , name: 'all',width:70,align:'right'});
		                  
	                	  selCols.push({ display: '饲料耗用(kg)',columns:feed});
	                  }
	                  if (value.indexOf('InWare') != -1) {
	                	  var feed_in = [];
	                	  for(var i=0;i<names.length;i++){
							  var name = names[i];
							  feed_in.push({ display:name , name: name+'_in',width:70,align:'right' });
						  }
	                	  feed_in.push({ display:'合计' , name: 'all_in',width:70,align:'right'});

	                	  selCols.push({ display: '饲料入库(kg)',columns:feed_in});
	                  }
	                  if (value.indexOf('Warehouse') != -1) {
	                	  var feed_ware = [];
	                	  for(var i=0;i<names.length;i++){
							  var name = names[i];
							  feed_ware.push({ display:name , name: name+'_ware',width:70,align:'right' });
						  }
	                	  feed_ware.push({ display:'合计' , name: 'all_ware',width:70,align:'right'});

	                	  selCols.push({ display: '饲料库存(kg)',columns:feed_ware});
	                  }
	                  selCols = selCols.concat(columns2);
	                  if (value.indexOf('noFeed') != -1) {
	                	  var feed_no = [];
	                	  for(var i=0;i<names.length;i++){
							  var name = names[i];
							  feed_no.push({ display:name , name: name+'_no',width:70,align:'right' });
						  }
	                	  feed_no.push({ display:'合计' , name: 'all_no',width:70,align:'right'});

	                	  selCols.push({ display: '饲料未拉(kg)',columns:feed_no});
	                  }
	                  grid.set('columns', selCols); 
	                  grid.currentData = {};
	                  grid.reRender();  
	      		  }
	                 
              });
        });
    	
    </script>
 
</head>
<body>
</body>
</html>
