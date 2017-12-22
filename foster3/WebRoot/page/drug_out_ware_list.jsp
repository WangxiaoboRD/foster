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
        var grid = null;
        $(function () {
			   // 工具条
        	   var toolBarOption = { 
   	                   items: [
	   	   	              { text: '查看', icon: 'view', expression:'!=1', disabled:true, click: function(item){
	   	   	           		 	var selectedRow = item.selectGrid.selected; 
			   	   	           	if (selectedRow.length == 0) {
		   	   	           			$.ligerDialog.warn('请选择要查看的记录！');
		   	   	           			return;
				   	   	         }
				   	   	         var ids = new Array()
				   	   	         var datas=grid.rows;
				   	   	         for(var i=0;i<datas.length;i++){
	  	   	          				ids[i]=datas[i]['id'];
	  	   	          			 }
		   	    				$.pap.addTabItem({ 
			   	    				tabid: 'drugOutWare'+grid.selected[0]['id'],
		   	   	    				text: '药品出库单【'+grid.selected[0]['id']+'】',
		       	   					params:{ 'ids':ids,'id':grid.selected[0]['id']},
		   	   	    				url: 'drug_out_ware!loadDetailById?id='+grid.selected[0]['id']
		   	   	    			});
	   	   			      }},
                   		]
             	};

        	 form = {
                 labelWidth: 70,
                 fields:[
						{ display: "出库单号", name:"e.id", type:"text" },
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
								var _farmer=liger.get("farmer");
								if(_farmer)
									_farmer.setValue("");
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
						{ display: "代养户", name:"e.farmer.id",comboboxName: 'farmer', type:"select", options:{
							url: 'farmer!loadByPinYin',
			                valueField: 'id',
			                textField: 'name', 
			                selectBoxHeight: 180,
			                keySupport:true,
			                triggerToLoad: true,
			                autocomplete: true,
			                onBeforeOpen: function() {
			                	var g = this;
			                	g.setParm('e.company.id', $("input[id='e.company.id']").val());
			                }
						}},
						{ display: "审核状态",name: "e.checkStatus",newline: true,type: "select", options:{
							  keySupport:true,
	   	                	  data: [
	   	                       	{ text: '未审核', id: '0' },
	   	               	  		{ text: '已审核', id: '1' }
	   		                 ],
	   		              	value:0
	               		}},
						{ display: "出库时间", name: "e.tempStack.startTime", type: "date", space: 10 },
		                { display: "至", type: "label", space: 10 },
		                { hideLabel: true, name: "e.tempStack.endTime", type: "date"}
	    			]
            	}

        	 var gridoption={
                     columns:[
                        { display: '出库单号',name: 'id',width:90},
                        { display: '养殖公司',name: 'company.name',width:110},
	    				{ display: '饲料厂',name: 'feedFac.name',width:110},
	    				{ display: '代养户',name: 'farmer.name',width:110 },
	    				{ display: '批次',name: 'batch.batchNumber',width:110 },
	    				{ display: '出库时间',name: 'registDate',width:100},
	    				{ display: '审核状态',name: 'checkStatus',render: function(data){
		    				var text = data.checkStatus;
	    					switch(data.checkStatus){
	    						case '1': text = "已审核"; break;
	    						case '0': text = "未审核"; break;
	    					}
	    					return text;
	    				}},
	    				{ display: '审核人',name: 'checkUser',width:80},
	    				{ display: '审核日期', name: 'checkDate',width:90},
	    				{ display: '创建人',name: 'createUser',width:80},
	    				{ display: '创建时间', name: 'createDate',width:90}
              		],
                    url:'drug_out_ware!loadByPage',
                    exportBtn: true,
			        exportUrl: 'drug_out_ware!exportFile'
             }
            var glist=$.pap.createFormGridList({ toolbar:toolBarOption,form:form,grid:gridoption});
            grid=glist.getGrid();
        });
    </script>
</head>
<body>
</body>
</html>
