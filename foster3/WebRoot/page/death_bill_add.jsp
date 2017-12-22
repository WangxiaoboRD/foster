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
	<script src="../ligerUI/ligerUI/js/pap.js" type="text/javascript" ></script>
	<script src="../ligerUI/ligerUI/js/template/ligerTemplate.js" type="text/javascript"></script>
	<script src="../ligerUI/ligerUI/js/template/ligerXEFormGridSave.js" type="text/javascript"></script>
    <script type="text/javascript">
		var v;
        var form = null;
        var grid = null;
        var groupicon = "../ligerUI/ligerUI/skins/icons/communication.gif";
        $(function(){
	  		 var formOption = {
	  		 	 url: "death_bill!save",	// 此处也是必须的
	  			 labelWidth: 85, 
	  			 inputWidth: 170,
           		 space:20,
           		 excludeClearFields:['e.remark','e.registDate','farmer','batch','technician','isCorDeath'],
	  			 fields: [
					{ display: "代&nbsp;养&nbsp;户", name:"e.farmer.id",comboboxName: 'farmer',type:"select",group: "死亡单", groupicon: groupicon,validate: { required: true },options:{
					    url:'farmer!loadByPinYin',
					    valueField: 'id', 
				        textField: 'name',
				        keySupport:true,
						selectBoxHeight: 200,
						triggerToLoad: true,
						autocomplete: true,
						onBeforeOpen: function() {
		                	var g = this;
		                	g.setParm('e.company.id', $("input[id='e.company.id']").val());
		                },
		                onSelected: function (nv){
					        var _batch = liger.get("batch");
							if(_batch)
								_batch.setValue("");
							var _technician = liger.get("technician");
							if(_technician)
								_technician.setValue("");
					    }
					}},
					{ display: "批&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;次", name: "e.batch.id",comboboxName: 'batch', type: "select",validate: { required: true },options:{
					    url:'batch!loadByEntity',
					    valueField: 'id', 
				        textField: 'batchNumber',
				        keySupport:true,
						selectBoxHeight: 200,
						triggerToLoad: true,
						onBeforeOpen: function() {
		                	var g = this;
		                	g.setParm('e.farmer.id', $("input[id='e.farmer.id']").val());
		                	g.setParm('e.isBalance', 'N');
		                	g.setParm('e.contract.status.dcode', 'BREED');
		                },
		                onSelected: function (nv){
		                  var batchId=$("input[id='e.batch.id']").val();
		                  if(batchId){
					        $.ligerui.ligerAjax({
					       		url: "batch!loadById",
								type: "POST",
								async:  false,			
								data: {id:$("input[id='e.batch.id']").val()},
								dataType: "json",
								success:function(result,textStatus){
									if(result != null && result !=""){
									    var stockNum=result.e.quantity;
									    $("input[id='e.stockNum']").val(stockNum);   
									}
								},
						});
						}
					    }
					}},
					{ display: "死亡时间", name: "e.registDate", type: "date",validate: { required: true },newline:true,options: { value: new Date(), showTime: false }},
					/**{ display: "总头数", name: "e.totalQuan", type: "text",options:{readonly:true}},*/
					{ display: "总重量(kg)", name: "e.totalWeight", type: "text",options:{readonly:true}},
					{ display: "技&nbsp;术&nbsp;员", name:"e.technician.id",comboboxName: 'technician',type:"select",validate: { required: true },newline:true,options:{
					    url:'technician!loadByName',
					    valueField: 'id', 
				        textField: 'name',
				        keySupport:true,
						selectBoxHeight: 200,
						triggerToLoad: true,
						autocomplete: true,
						onBeforeOpen: function() {
		                	var g = this;
		                	g.setParm('e.company.id', $("input[id='e.company.id']").val());
		                }
					}},
					{ display: "死亡归属", name:"e.isCorDeath",comboboxName: 'isCorDeath',type:"select",validate: { required: true },options:{
					    data:[
							  	{ text: '养殖公司', id: 'Y' },
							  	{ text: '代养户', id: 'N' }
									],
					    initValue: 'N',
  					    initText: '代养户死亡',
				        keySupport:true,
						selectBoxHeight: 200,
					}},
					{ display: "备&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;注", name: "e.remark", type: "text",newline:true,width:450},
					{ display: "存栏头数", name: "e.stockNum", type: "text",options:{readonly:true}},
					
               ]};
             var oldNum=0;
             var oldWeight=0;
	  		 var gridOption={
	  		  		 title: '死亡单明细',
	  		  	 	 submitDetailsPrefix: 'e.details',
	  		  	     enabledEdit: true,
	  		  	     detailsIsNotNull:true,
                     columns:[
                        /**{ display: "头数", name: 'quantity',editor: { type: 'text',options: { value:'0',digits: true,initSelect: true, minValue:0}}},*/
                        { display: "重量(kg)", name: 'weight',editor: { type: 'text',options: { number: true,initSelect: true, minValue:0}}},
                        { display: '原因', name: 'reason.id', textField: 'reason.name',width: 180, editor: { type: 'select',
	    					options: {
	       					url: 'death_reason!loadByPinYin',
	       					valueField: 'id',
			                textField: 'name', 
			                selectBoxHeight: 180,
			                //alwayShowInDown: true,
			                autocomplete: true,
			                keySupport:true,
   						}
             		    }}

              		],
              		onBeforeEdit: function(e) { 
	  					var rowdata = e.record;
	  					if (rowdata.quantity) {
	  						oldNum = rowdata.quantity;
		  				}else {
		  					oldNum = 0;
			  			}
			  			if (rowdata.weight) {
	  						oldWeight = rowdata.weight;
		  				}else {
		  					oldWeight = 0;
			  			}
	  		 		},
              		onAfterEdit: function(e) {
						var rowdata = e.record;
						// 起始值
						/**
						if (e.column.name == 'quantity') {
		 					if (rowdata.quantity) {
		  						var num = rowdata.quantity*1;
		  						//数量不能为非正整数
		  						if(num<=0){
 	 		 						grid.updateCell('quantity', oldNum, rowdata);
 	 	 	 						$.ligerDialog.warn("数值不能为非正整数！") ;
 	 	 	 						return;
 	 		 					}
 	 		 					//更新表头总头数
 	 		 					var totalNum = $("input[id='e.totalQuan']").val()*1;
 	 		 					$("input[id='e.totalQuan']").val(totalNum-oldNum+num);
		 					}
	  					}
	  					*/
	  					if (e.column.name == 'weight') {
		 					if (rowdata.weight) {
		  						var weight = rowdata.weight*1;
		  						//数量不能为非正整数
		  						if(weight<=0){
 	 		 						grid.updateCell('weight', oldWeight, rowdata);
 	 	 	 						$.ligerDialog.warn("数值不能为非正整数！") ;
 	 	 	 						return;
 	 		 					}
 	 		 					//更新表头总头数
 	 		 					var totalWeight = $("input[id='e.totalWeight']").val()*1;
 	 		 					$("input[id='e.totalWeight']").val((totalWeight-oldWeight+weight).toFixed(2));
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
	  		var glist=$.pap.createEFormGridSave({form:formOption,grid:gridOption});
		    grid=glist.getGrid();
        });
        //生成明细
   	    var addClick = function(){
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
             var delTotalWeight=0;
             $(selRows).each(function(index, data){
               	  if(data['quantity'])
                     delTotalNum += data['quantity']*1;
                  if(data['weight'])
                     delTotalWeight += data['weight']*1;
             });
             var totalNum = $("input[id='e.totalQuan']").val();
         	 var nowNum=(totalNum*1 - delTotalNum).toFixed(0);
         	 if(nowNum>0)
         		 $('input[id="e.totalQuan"]').val(nowNum);
             else
            	 $('input[id="e.totalQuan"]').val(null);
             //	 
           	 var totalWeight = $("input[id='e.totalWeight']").val();
             var nowWeight=(totalWeight*1-delTotalWeight).toFixed(2);
           	 if(nowWeight>0)
         		 $('input[id="e.totalWeight"]').val(nowWeight);
             else
            	 $('input[id="e.totalWeight"]').val(null);
   		     //删除选中的行
             grid.deleteSelectedRow(); 
      	 }
	  	
    </script>
</head>
<body></body>
</html>

