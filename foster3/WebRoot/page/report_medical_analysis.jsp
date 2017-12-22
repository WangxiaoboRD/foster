<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
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
        	   var groupicon = "../ligerUI/ligerUI/skins/icons/communication.gif";
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
                            	$.exportFile($('form'), grid, 'drug_bill!exportMedicalReport');
  					        }}
                  		]
             	};

                var fields = [
							  { text: '领药单号', id: 'bill'},
                              { text: '领药日期', id: 'medicalDate'},
                              { text: '单价(元)', id: 'price'},
                              { text: '金额合计', id: 'amount'}, 
                              { text: '备      注', id: 'mark'}
                             ];
                
                formOption={
                    labelWidth: 80, 
                    inputWidth: 150,
	                fields:[
						{ display: "选择日期", name: "e.tempStack.startDate",group: "药品领用报表", groupicon: groupicon, type: "date", options: { format : "yyyy-MM-dd", showTime: true }, space: 5, newline: true},
						{ display: "至", type: "label", space: 5 },
						{ hideLabel: true, name: "e.tempStack.endDate", type: "date", options: { format : "yyyy-MM-dd", showTime: true }},
						{ display: "养殖公司",newline: true, name:"e.company.id",validate: { required: true },type:"select",options:{
						    url:'company!loadByEntity',
						    valueField: 'id', //关键项
							textField: 'name',
							keySupport:true,
							selectBoxHeight: 150,
							onSelected: function (nv){
								var _farmer = liger.get("farmer");
								if(_farmer)
									_farmer.setValue("");
								var _batch = liger.get("batch");
								if(_batch)
									_batch.setValue("");
                    		}
						}},
						{ display: "代养农户",newline: true, name:"e.farmer.id",comboboxName: 'farmer', type:"select",options:{
							url: 'farmer!loadByPinYin',
			                valueField: 'id',
			                textField: 'name', 
			                selectBoxHeight: 180,
			                triggerToLoad: true,
			                keySupport:true,
			                autocomplete: true,
			                onBeforeOpen: function() {
			                	var g = this;
			                	g.setParm('e.company.id', $("input[id='e.company.id']").val());
			                },
			                onSelected: function (nv){
								var _batch = liger.get("batch");
								if(_batch)
									_batch.setValue("");
                    		}
						}},
						{ display: "所属批次",newline: true, name:"e.batch.id",comboboxName: 'batch', type:"select",options:{
							url: 'batch!loadByEntity',
			                valueField: 'id',
			                textField: 'batchNumber', 
			                selectBoxHeight: 180,
			                triggerToLoad: true,
			                onBeforeOpen: function() {
			                	var g = this;
			                	g.setParm('e.farmer.id', $("input[id='e.farmer.id']").val());
			                }
						}},
						{ display: "药品类型", name: "e.isBalance",type: "select",newline: true, options:{               
					       	data: [{ text: '药品', id: '0' },
				                  { text: '疫苗', id: '1' }],          
					       	name: "e.isBalance",
					       	value: '', 
		                }},
						{ display: "查询状态", name: "e.checkStatus",type: "radiogroup",newline: true, options:{               
					       	data: [{ text: '领药明细', id: '1' },
				                  { text: '领药合计', id: '2' }],          
					       	name: "e.checkStatus",
					       	value: '1'     
		                }},
						{ display: '显示字段', name: "showColumns",group: "显示字段", groupicon: groupicon, type: "checkboxgroup", newline: true, width: 500, options:{       
	                		rowSize: 4,        
	    			       	data: fields,
	    			       	name: 'showColumns',
	    			       	split : ','
	                    }},
	                    { display: '显示选择', name: "showSelect", type: "radiogroup", newline: true, options:{               
						   	data: [
								{ text: '全选', id: 'all' },
							   	{ text: '全不选', id: 'none' }
					         ],
					         name: "showSelect",
					         value: 'none'
						}}
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
        	    var columns1 = [
					   { display: '区      域',name:'feedFac',width:90},
					   { display: '代 养 户',name:'farmer',width:90},
                       { display: '批 次 号',name:'batch',width:50},
                       { display: '领用药品', name:'drug',width:90},
                       { display: '药品编号', name:'drugCode',width:90},
                       { display: '药品类型', name:'drugType',width:60,render: function(data){
	                  	   if(data.drugType){
							  if(data.drugType=='0')
								  return "药品";
							  if(data.drugType=='1')
								  return "疫苗";
						   }
	                   }},
	                   { display: '药品单位', name:'unit',width:90},
                       { display: '领用数量', name:'quantity',width:90,align:'right',render: function(data){
	                  	   if(data.quantity){
							   value = (data.quantity*1);
							   return value.toFixed(2);
						   }
	                   }},
                 ];
           		var columns = columns1;
             	var gridoption={
                    title: '药品耗用报表',
             		url:'drug_bill!loadMedicalByPage',
                    columns: columns,
                    checkbox: false,
                    usePager: true,
                    allowHideColumn: false // 允许隐藏列
                }

              var glist=$.pap.createFormGridList({ toolbar:toolBarOption,form:formOption,grid:gridoption});
              grid=glist.getGrid();
              form = glist.getForm();

              //表格动态列设置
              function gridColSet(grid, selCols) {
             	 grid.set('columns', selCols); 
                 grid.currentData = {};
                 grid.reRender();    
              }

              // 显示选择设置
              var allColumn = "";
              $.each(fields, function(i, field) {
			   	 allColumn += field['id'] + ",";
			  });
              $("input[name='showSelect']").change(function() {
                  var value = liger.get("showSelect").getValue();
                  var showColumns = liger.get("showColumns");
                  var selCols = [];
  		    	  if (value == 'all') {//全选
  		    		 showColumns.clear();
 		    		 showColumns.setValue(allColumn);
 		    		 var value = showColumns.getValue();
 		    		 if (value.indexOf('bill') != -1) {
 	                	  selCols.push({ display: '领药单号', name: 'bill' ,width:90});
 	                 }
 	                 if (value.indexOf('medicalDate') != -1) {
 	                	  selCols.push({ display: '领药日期', name: 'medicalDate',width:90 });
 	                 }
 		             selCols = selCols.concat(columns1);
 	                  
 	                 if (value.indexOf('price') != -1) {
 	                	  selCols.push({ display: '单价(元)', name: 'price' ,width:60,align:'right',render: function(data){
			 		                  	   if(data.price){
			 								   value = (data.price*1);
			 								   return value.toFixed(2);
			 							   }
			 		                   }});
 	                 }
 	                 if (value.indexOf('amount') != -1) {
 	                	  selCols.push({ display: '金额合计', name: 'amount' ,width:90,align:'right',render: function(data){
					                  	   if(data.amount){
			 								   value = (data.amount*1);
			 								   return value.toFixed(2);
			 							   }
			 		                   }});
 	                 }
 	                 if (value.indexOf('mark') != -1) {
 	                	  selCols.push({ display: '备       注', name: 'mark' ,width:200});
 	                 }
	  			  }else {// 全不选
	  			     showColumns.clear();
	  			     selCols = selCols.concat(columns1);
	  		      }
  		    	  gridColSet(grid,selCols);
              });
              //查询状态
              $("input[name='e.checkStatus']").change(function() {
            	  var selCols = []; 
            	  var checklistObject = liger.get("e.checkStatus");
            	  var value = checklistObject.getValue();
            	  var cls = liger.get("showColumns");
            	  var show = liger.get("showSelect");
            	  if(value=="2"){
            		  cls.clear();
            		  cls.setDisabled();
            		  show.setDisabled();
            		  selCols = selCols.concat(columns1);
            		  gridColSet(grid,selCols);
                  }
				  if(value=="1"){
					  show.setEnabled();
					  cls.setEnabled();
				  }
              });
              //显示字段
              $("input[name='showColumns']").change(function() {
	              var selCols = [];
	              var value = liger.get("showColumns").getValue();
	              if (value.indexOf('bill') != -1) {
	                	  selCols.push({ display: '领药单号', name: 'bill' ,width:90});
                  }
                  if (value.indexOf('medicalDate') != -1) {
                	  selCols.push({ display: '领药日期', name: 'medicalDate',width:90 });
                  }
	              selCols = selCols.concat(columns1);
                  
                  if (value.indexOf('price') != -1) {
                	  selCols.push({ display: '单价(元)', name: 'price' ,width:60,align:'right',render: function(data){
	                  	   if(data.price){
							   value = (data.price*1);
							   return value.toFixed(2);
						   }
	                   }});
                  }
                  if (value.indexOf('amount') != -1) {
                	  selCols.push({ display: '金额合计', name: 'amount' ,width:90,align:'right',render: function(data){
	                  	   if(data.amount){
								   value = (data.amount*1);
								   return value.toFixed(2);
							   }
		                   }});
                  }
                  if (value.indexOf('mark') != -1) {
                	  selCols.push({ display: '备       注', name: 'mark' ,width:200});
                  }
                  gridColSet(grid,selCols);
              });
        });
    	
    </script>
 
</head>
<body>
</body>
</html>
