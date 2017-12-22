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
    <script src="../ligerUI/ligerUI/js/ligerui.all.js" type="text/javascript"></script>       
    <script src="../ligerUI/ligerUI/js/plugins/XGrid.js" type="text/javascript"></script>    
	<script src="../ligerUI/ligerUI/js/pap.js" type="text/javascript" ></script>
	<script src="../ligerUI/ligerUI/js/template/ligerTemplate.js" type="text/javascript"></script>
	<script src="../ligerUI/ligerUI/js/template/ligerXEFormGridEdit.js" type="text/javascript"></script>
	<script src="../ligerUI/ligerUI/js/core/ligerAjax.js" type="text/javascript" ></script>
    <script src="../ligerUI/ligerUI/js/json2.js" type="text/javascript" ></script>
    <script type="text/javascript">
		var v;
        var form = null;
        var grid = null;
        var delIds = ""; // 删除的明细ids
        var groupicon = "../ligerUI/ligerUI/skins/icons/communication.gif";
        $(function(){
	  		 var formOption = {
	  		 	 url: "eliminate_bill!modifyAll",	// 此处也是必须的
	  			 labelWidth: 85, 
	  			 inputWidth: 170,
           		 space:20,
	  			 fields: [
	  			    { display: "淘汰单号", name: "e.id", type: "text", validate: { required: true }, options: { value: '${e.id}', readonly: true }, group: "淘汰单", groupicon: groupicon },	  		  			 
					{ display: "养殖公司编码", name:"e.company.id", type: "hidden", options:{value: '${e.company.id}'}},
					{ display: "养殖公司", name:"e.company.name",  type: "text", options:{value: '${e.company.name}',readonly: true},group: "单据信息", groupicon: groupicon},
					{ display: "代养户id", name:"e.farmer.id", type: "hidden", options:{value: '${e.farmer.id}'}},
					{ display: "代养户", name:"e.farmer.name", type: "text", options:{value: '${e.farmer.name}',readonly: true}},
					{ display: "批次", name: "e.batch.id",comboboxName: 'batch', type: "select",options:{
					    url:'batch!loadByEntity',
					    valueField: 'id', 
				        textField: 'batchNumber',
				        initValue: '${e.batch.id}',
  					    initText: '${e.batch.batchNumber}',
				        keySupport:true,
						selectBoxHeight: 200,
						triggerToLoad: true,
						autocomplete: true,
						onBeforeOpen: function() {
		                	var g = this;
		                	g.setParm('e.company.id', $("input[id='e.company.id']").val());
		                	g.setParm('e.farmer.id', $("input[id='e.farmer.id']").val());
		                	g.setParm('e.isBalance', 'N');
		                	g.setParm('e.contract.status.dcode', 'BREED');
		                }
					}},
					{ display: "淘汰时间", name: "e.registDate", type: "date", validate: { required: true },newline:true,options: { value: '${e.registDate}', showTime:false }},
					{ display: "总头数", name: "e.totalQuan", type: "text",options:{readonly:true,value: '${e.totalQuan}'}},
					{ display: "备注", name: "e.remark", type: "text",width:450,options: { value: '${e.remark}'}},
					]};
	  		   var oldNum=0;
  	  		   var gridOption={
  	  		  		 title: '淘汰单明细',
	  	  		  	 url:'eliminate_bill_dtl!loadByEntity',
	        		 parms:{ 'e.eliminateBill.id': '${e.id}' },
  	  		  	 	 submitDetailsPrefix: 'e.details',
  	  		  	     enabledEdit: true,
  	  		  	     detailsIsNotNull:true,
                     columns:[
							{ display: "明细id", name: 'id',hide:true},
							{ display: "头数", name: 'quantity',editor: { type: 'text',options: { value:'0',digits: true,initSelect: true, minValue:0}}},
                        	{ display: "原因", name: 'reason',editor: { type: 'text'}},
                		],
                		onBeforeEdit: function(e) { 
		  					var rowdata = e.record;
		  					if (rowdata.quantity) {
		  						oldNum = rowdata.quantity;
			  				}else {
			  					oldNum = 0;
				  			}
		  		 		},
                  		onAfterEdit: function(e) {
							var rowdata = e.record;
							// 起始值
							if (e.column.name == 'quantity') {
			 					if (rowdata.quantity) {
			  						var num = rowdata.quantity*1;
			  						//数量不能为负
			  						if(num<=0){
	 	 		 						grid.updateCell('quantity', oldNum, rowdata);
	 	 	 	 						$.ligerDialog.warn("数值不能为非正整数！") ;
	 	 	 	 						return;
	 	 		 					}
	 	 		 					//更新表头总头数
	 	 		 					var totalNum = $("input[id='e.totalQuan']").val()*1;
	 	 		 					$("input[id='e.totalQuan']").val((totalNum-oldNum+num).toFixed(0));
			 					}
		  					}
						},
                        toolbar: {
                          items: [
        		                { text: '添加', icon: 'add', click: addClick },
        		                { line: true },	                
        		                { text: '删除', icon:'delete', click: deleteClick }
        	                ]
        	            }
               }
  	  		  
  	  		  var template=$.pap.createEFormGridEdit({form:formOption,grid:gridOption});
  		      grid=template.getGrid();
  		      // 记录删除对象id集合
		  	  template.appendOtherData = function(){
		     	  var result= {'e.tempStack.deleteIds': delIds } ;
		          return result;
		      }
          });
          //生成明细
   	      var addClick = function(){
   	    	var company = $("input[id='e.company.id']").val();
   	    	if(!company){
   	    		$.ligerDialog.warn('请选择养殖公司！');
   	    		return;
   	    	}
   	    	var farmer = $("input[id='e.farmer.id']").val();
   	    	if(!farmer){
   	    		$.ligerDialog.warn('请选择代养户！');
   	    		return;
   	    	}
   	    	//生成明细
   	    	grid.addRow({
				
			});
   	 	 }
          //删除
       	 var deleteClick = function(){
       	 	  var selRows = grid.getSelecteds();
              if (selRows.length == 0) {
           		  $.ligerDialog.warn('请选择要删除的记录！');
    			      return;
              }
              var delTotalNum=0;
              $(selRows).each(function(index, data){
                  if(data['id'])
                	  delIds += data['id']+',';
               	  if(data['quantity'])
                     delTotalNum += data['quantity']*1;
              });
              var totalNum = $("input[id='e.totalQuan']").val();
         	  var nowNum=(totalNum*1 - delTotalNum).toFixed(0);
         	  if(nowNum>0)
         		  $('input[id="e.totalQuan"]').val(nowNum);
              else
            	  $('input[id="e.totalQuan"]').val(null);
    		  //删除选中的行
              grid.deleteSelectedRow(); 
        }
    </script>
</head>
<body></body>
</html>

