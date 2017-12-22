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
	<script src="../ligerUI/ligerUI/js/plugins/ligerUtil.js" type="text/javascript" ></script> 
	<script type="text/javascript" src="../ligerUI/ligerUI/js/pap.js"></script>
	<script src="../ligerUI/ligerUI/js/template/ligerTemplate.js" type="text/javascript"></script>
	<script src="../ligerUI/ligerUI/js/template/ligerXFormGridList.js" type="text/javascript"></script>
    <script type="text/javascript">

    	var groupicon = "../ligerUI/ligerUI/skins/icons/communication.gif";
    	var toolbar = null;
    	var gridfeed,griddrug,gridother,gridpig,gridreward,gridsale;
    	 // 获得当前页面对象
        $(function () {
        	var currentPage = $.pap.getOpenPage(window);
     	  	var data = currentPage.getParam("params");
     	  	var ids=data.ids;	
     	  	var id = data.id;	
            toolbar = $("#toolbar").ligerToolBar({
            	items: [
                	 { text: '关闭', icon: 'delete', click: function(){
                		 $.pap.removeTabItem();
                	 }},
                	 { text: '上一单', icon: 'up', click: function(){
 	                 var infoId="";
 	                 for(var i=0;i<ids.length;i++){
 	                    if(ids[i]==id){
 	                      if(i==0){
	 	                     $.ligerDialog.warn('到顶啦！');
	 	                      return;
 	                      }else{
 	                        infoId=ids[i-1];
 	                      }
 	                      continue;
 	                    }
 	                 }
 	           		
		  			$.pap.addTabItem({ 
		  			 		tabid:'viewFarmerBill'+infoId,
  	   	    				text: '代养账单【'+infoId+'】',
      	   					params:{ 'ids':ids,'id':infoId},
  	   	    				url: 'farmer_bill!loadDetailById?id='+infoId
  	   	    		});
  	   	    		$.pap.removeTabItem();
 			      }},
                  { line:true },
 	              { text: '下一单', icon: 'down', click: function(){
 	                 var infoId="";
 	                 for(var i=0;i<ids.length;i++){
 	                    if(ids[i]==id){
 	                      if(i==(ids.length-1)){
	 	                     $.ligerDialog.warn('到底啦！');
	 	                      return;
 	                      }else{
 	                        infoId=ids[i+1];
 	                      }
 	                      continue;
 	                    }
 	                 }
 	           		
 	                $.pap.addTabItem({ 
	  			 		tabid:'viewFarmerBill'+infoId,
	   	    				text: '代养账单【'+infoId+'】',
  	   					params:{ 'ids':ids,'id':infoId},
	   	    				url: 'farmer_bill!loadDetailById?id='+infoId
	   	    		});
  	   	    		$.pap.removeTabItem();
 			      }}
                 ]
             });
            $("#form0").ligerForm({
                fields: [
				    { display: "账单编号", name:"e.id",  type: "text", options:{value: '${e.id}',readonly: true}},
					{ display: "养殖公司", name:"e.company.name",  type: "text", options:{value: '${e.company.name}',readonly: true},group: "账单信息", groupicon: groupicon},
					{ display: "代养户", name:"e.farmer.name",  type: "text", options:{value: '${e.farmer.name}',readonly: true}},
					{ display: "批次", name:"e.batch.batchNumber", type: "text", options:{value: '${e.batch.batchNumber}',readonly: true}},
					{ display: "账单时间", name: "e.registDate",type: "date",options:{format : "yyyy-MM-dd",showTime: true,value:"${e.registDate}",readonly:true}},

					{ display: "料肉比", name:"e.fcr",  type: "text",group: "生产效率", groupicon: groupicon,options:{value: '${e.fcr}',readonly: true,render: function(data){
                 	   if(data){
						   value = (data*1);
						   return value.toFixed(4);
					   }
                	}}},
					{ display: "进栏头数", name:"e.inPigNum",  type: "text",options:{value: '${e.inPigNum}',readonly: true}},
					{ display: "进栏均重", name:"e.inPigAvgWeight",  type: "text",options:{value: '${e.inPigAvgWeight}',readonly: true,render: function(data){
                 	   if(data){
						   value = (data*1);
						   return value.toFixed(2);
					   }
                	}}},
					{ display: "进栏日期", name:"e.inPigDate",  type: "text",options:{value: '${e.inPigDate}',readonly: true}},
					{ display: "出栏头数", name:"e.outPigNum", newline: true, type: "text",options:{value: '${e.outPigNum}',readonly: true}},
					{ display: "出栏均重", name:"e.outPigAvgWeight",  type: "text",options:{value: '${e.outPigAvgWeight}',readonly: true,render: function(data){
                 	   if(data){
						   value = (data*1);
						   return value.toFixed(2);
					   }
                	}}},
					{ display: "出栏日期", name:"e.outPigDate",  type: "text",options:{value: '${e.outPigDate}',readonly: true}},
					{ display: "毛猪净重", name:"e.outPigAllWeight",  type: "text",options:{value: '${e.outPigAllWeight}',readonly: true,render: function(data){
                 	   if(data){
						   value = (data*1);
						   return value.toFixed(2);
					   }
                	}}},
					{ display: "毛猪净增重", name:"e.netGin", newline: true, type: "text",options:{value: '${e.netGin}',readonly: true,render: function(data){
                 	   if(data){
						   value = (data*1);
						   return value.toFixed(2);
					   }
                	}}},
					{ display: "净增均重", name:"e.avgNetGin",  type: "text",options:{value: '${e.avgNetGin}',readonly: true,render: function(data){
                 	   if(data){
						   value = (data*1);
						   return value.toFixed(2);
					   }
                	}}},
					{ display: "成活率", name:"e.survivalRate",  type: "text",options:{value: '${e.survivalRate}',readonly: true}},
					{ display: "平均代养费", name:"e.avgFarmerCost",  type: "text",options:{value: '${e.avgFarmerCost}',readonly: true,render: function(data){
                 	   if(data){
						   value = (data*1);
						   return value.toFixed(2);
					   }
                	}}},
					{ display: "饲料费用(元)", name:"e.feedCost", group: "费用信息", groupicon: groupicon, type: "text",options:{value: '${e.feedCost}',readonly: true,render: function(data){
                 	   if(data){
						   value = (data*1);
						   return value.toFixed(2);
					   }
                	}}},
					{ display: "药品费用(元)", name:"e.drugsCost",  type: "text",options:{value: '${e.drugsCost}',readonly: true,render: function(data){
                 	   if(data){
						   value = (data*1);
						   return value.toFixed(2);
					   }
                	}}},
					{ display: "物料费用(元)", name:"e.otherCost",  type: "text",options:{value: '${e.otherCost}',readonly: true,render: function(data){
                 	   if(data){
						   value = (data*1);
						   return value.toFixed(2);
					   }
                	}}},
					{ display: "猪苗费用(元)", name:"e.pigletCost",  type: "text",options:{value: '${e.pigletCost}',readonly: true,render: function(data){
                 	   if(data){
						   value = (data*1);
						   return value.toFixed(2);
					   }
                	}}},
					{ display: "奖罚费用(元)", name:"e.rewardCost", newline: true, type: "text",options:{value: '${e.rewardCost}',readonly: true,render: function(data){
                 	   if(data){
						   value = (data*1);
						   return value.toFixed(2);
					   }
                	}}},
					{ display: "销售收入(元)", name:"e.saleIncome", type: "text", options:{value: '${e.saleIncome}',readonly: true,render: function(data){
                 	   if(data){
						   value = (data*1);
						   return value.toFixed(2);
					   }
                	}}},
					{ display: "总养殖费(元)", name:"e.allBreedCost",type: "text",options:{value: '${e.allBreedCost}',readonly: true,render: function(data){
                 	   if(data){
						   value = (data*1);
						   return value.toFixed(2);
					   }
                	}}},
					{ display: "代养费用(元)", name:"e.farmerCost",  type: "text",options:{value: '${e.farmerCost}',readonly: true,render: function(data){
                 	   if(data){
						   value = (data*1);
						   return value.toFixed(2);
					   }
                	}}},
					{ display: "调整费用(元)", name:"e.adjustCost",group: "费用调整", groupicon: groupicon,  type: "text",options:{value: '${e.adjustCost}',readonly: true,render: function(data){
                 	   if(data){
						   value = (data*1);
						   return value.toFixed(2);
					   }
                	}}},
					{ display: "调整人", name:"e.adjustMen", type: "text",options:{value: '${e.adjustMen}',readonly: true}},
					{ display: "调整原因", name:"e.adjustReason",  type: "text",options:{value: '${e.adjustReason}',readonly: true}}
					
                 ]
            });
            //销售
            gridsale = $("#form1").ligerGrid({
            	url:"farmer_sale_income!loadByFarmerSaleIncome",
   			    parms:{"e.batch.id":"${e.batch.id}"},
                columns:[
					{ display: '销售级别', name: 'pigLevel.value',totalSummary:{
                        render: function (suminf, column, cell){
	                        return '<div>合计:</div>';
	                    },align: 'left'
	                }},
					{ display: '头数', name: 'quantity',align:'right',totalSummary:{
                        render: function (suminf, column, cell){
                            return '<div>'+(suminf.sum).toFixed(0) + '</div>';
                        },
                        align: 'left'
                    }},
					{ display: '重量(KG)', name: 'weight',align:'right',render: function(data){
                 	   if(data.weight!=null){
						   value = (data.weight*1);
						   return value.toFixed(2);
					   }},
                		totalSummary:{
	                    render: function (suminf, column, cell){
	                        return '<div>'+(suminf.sum).toFixed(2) + '</div>';
	                    },
	                    align: 'left'
	                }},
					{ display: '单价(元)', name: 'price',align:'right',render: function(data){
	                 	   if(data.price!=null){
							   value = (data.price*1);
							   return value.toFixed(2);
						   }
	                }},
					{ display:'金额(元)',name:'money',align:'right',render: function(data){
                 	   if(data.money!=null){
						   value = (data.money*1);
						   return value.toFixed(2);
					   }
                	},totalSummary:{
                        render: function (suminf, column, cell){
	                        return '<div>'+(suminf.sum).toFixed(2) + '</div>';
	                    }
	                    
	                }}
					
        	     ],
       			 usePager: false,
       			 checkbox:false,
       			 height:'99%',
                 rownumbers:true
            });
            gridfeed = $("#form2").ligerGrid({
  				 detailsIsNotNull:true,
    			 url:"farmer_feed_cost!loadByEntity",
    			 parms:{"e.batch.id":"${e.batch.id}"},
                 columns:[
 	       				{ display:'饲料',name:'feed.name'},
 	       				{ display:'规格',name:'feed.spec'},
 	       			    { display:'包装',name:'feed.packForm',render:function(data){
	                        if(data['feed.packForm']=='1')
	                        	return '散装';
	                        else
	                        	return '袋装';
                        }},
 	       			    { display:'单位',name:'feed.unit.value',totalSummary:{
	                        render: function (suminf, column, cell){
		                        return '<div>合计:</div>';
		                    },align: 'left'
		                }},
 		       			{ display:'重量(kg)',name:'quantity',align:'right',render: function(data){
		                 	   if(data.quantity!=null){
								   value = (data.quantity*1);
								   return value.toFixed(2);
							   }
		                	},totalSummary:{
		                    render: function (suminf, column, cell){
		                        return '<div>'+(suminf.sum).toFixed(2) + '</div>';
		                    }
		                }},
	       				{ display:'单价(元/kg)',name:'price',align:'right',render: function(data){
		                 	   if(data.price!=null){
								   value = (data.price*1);
								   return value.toFixed(2);
							   }
		                }},
	       		 		{ display:'金额(元)',name:'money',align:'right',width:150,render: function(data){
		                 	   if(data.money!=null){
								   value = (data.money*1);
								   return value.toFixed(2);
							   }
		                	},totalSummary:{
		                    render: function (suminf, column, cell){
		                        return '<div>'+(suminf.sum).toFixed(2) + '</div>';
		                    }
		                }}
 	       				
         			 ],
         			 usePager: false, 
         			 checkbox:false,
         			 height:'99%',
                     rownumbers:true
            });
            griddrug = $("#form3").ligerGrid({
            	 detailsIsNotNull:true,
   			     url:"farmer_drugs_cost!loadByEntity",
   			     parms:{"e.batch.id":"${e.batch.id}"},
                 columns:[
						{ display: '单据类型', name: 'billType', render: function(data,rowindex){
							var value = data.billType;
							if(value=='1')
								return '药品领用单';
							else if(value=='2')
								return '药品转接单';
							return value;
						}},
						{ display:'单据号',name:'drugBill'},
						{ display:'单据日期',name:'registDate'},
 	       				{ display:'药品',name:'drug.name'},
 	       			    { display:'规格',name:'drug.spec'},
 	       				{ display:'单位',name:'drug.unit.value',totalSummary:{
		                    render: function (suminf, column, cell){
		                        return '<div>合计:</div>';
		                    },
		                    align: 'left'
		                }},
 	       				{ display:'数量',name:'quantity',align:'right',render: function(data){
		                 	   if(data.quantity!=null){
								   value = (data.quantity*1);
								   return value.toFixed(2);
							   }
		                	},totalSummary:{
		                    render: function (suminf, column, cell){
		                        return '<div>'+(suminf.sum).toFixed(2) + '</div>';
		                    }
		                }},
		                { display:'定价单',name:'drugPrice'},
       		 			{ display:'单价(元)',name:'price',hide:true},
       		 		
       		 			{ display:'销售单价(元)',name:'salePrice',align:'right',render: function(data){
		                 	   if(data.salePrice!=null){
								   value = (data.salePrice*1);
								   return value.toFixed(2);
							   }
		                }},
       		 			{ display:'金额(元)',name:'money',align:'right',render: function(data){
		                 	   if(data.money!=null){
								   value = (data.money*1);
								   return value.toFixed(2);
							   }
		                	},totalSummary:{
			                render: function (suminf, column, cell){
		                        return '<div>'+(suminf.sum).toFixed(2) + '</div>';
		                    }
		                }}
         			 ],
         			 usePager: false,
         			 checkbox:false,
         			 height:'99%',
                     rownumbers:true
            });
            //其他物料
            gridother = $("#form4").ligerGrid({
            	url:"farmer_other_cost!loadByEntity",
   			    parms:{"e.batch.id":"${e.batch.id}"},
                columns:[
					{ display: '物料', name: 'material.name'},
					{ display:'单位',name:'material.unit.value',totalSummary:{
	                    render: function (suminf, column, cell){
	                        return '<div>合计:</div>';
	                    },
	                    align: 'left'
	                }},
					{ display: '数量', name: 'quantity',align:'right',render: function(data){
	                 	   if(data.quantity!=null){
							   value = (data.quantity*1);
							   return value.toFixed(2);
						   }
	                	},totalSummary:{
		                render: function (suminf, column, cell){
	                        return '<div>'+(suminf.sum).toFixed(2) + '</div>';
	                    },
	                    align: 'left'
	                }},
	                { display:'定价单',name:'materialPrice'},
					{ display: '单价(元)', name: 'price',align:'right',render: function(data){
	                 	   if(data.price!=null){
							   value = (data.price*1);
							   return value.toFixed(2);
						   }
	                }},
					{ display:'金额(元)',name:'money',align:'right',render: function(data){
	                 	   if(data.money!=null){
							   value = (data.money*1);
							   return value.toFixed(2);
						   }
	                	},totalSummary:{
		                render: function (suminf, column, cell){
	                        return '<div>'+(suminf.sum).toFixed(2) + '</div>';
	                    },
	                    align: 'left'
	                }}
        	     ],
       			 usePager: false,
       			 checkbox:false,
       			 height:'99%',
                 rownumbers:true
            });
          	//猪苗
            gridpig = $("#form5").ligerGrid({
            	url:"farmer_piglet_cost!loadByEntity",
   			    parms:{"e.batch.id":"${e.batch.id}"},
                columns:[
					{ display: '采购单', name: 'pigPurchase.id',totalSummary:{
	                    render: function (suminf, column, cell){
	                        return '<div>合计:</div>';
	                    },
	                    align: 'left'
	                }},
					{ display: '运输数量', name: 'quantity',align:'right',totalSummary:{
		                render: function (suminf, column, cell){
	                        return '<div>'+(suminf.sum).toFixed(0) + '</div>';
	                    },
	                    align: 'left'
	                }},
	                { display: '死亡头数', name: 'deadHead',align:'right',totalSummary:{
		                render: function (suminf, column, cell){
	                        return '<div>'+(suminf.sum).toFixed(0) + '</div>';
	                    },
	                    align: 'left'
	                }},
	                { display: '结算头数', name: 'balanceHead',align:'right',totalSummary:{
		                render: function (suminf, column, cell){
	                        return '<div>'+(suminf.sum).toFixed(0) + '</div>';
	                    },
	                    align: 'left'
	                }},
					{ display: '单价(元)', name: 'price',align:'right',render: function(data){
	                 	   if(data.price!=null){
							   value = (data.price*1);
							   return value.toFixed(2);
						   }
	                }},
					{ display:'金额(元)',name:'money',align:'right',render: function(data){
	                 	   if(data.money!=null){
							   value = (data.money*1);
							   return value.toFixed(2);
						   }
	                	},totalSummary:{
		                render: function (suminf, column, cell){
	                        return '<div>'+(suminf.sum).toFixed(2) + '</div>';
	                    },
	                    align: 'left'
	                }}
					
        	     ],
       			 usePager: false,
       			 checkbox:false,
       			 height:'99%',
                 rownumbers:true
            });
          	//奖罚
            gridreward = $("#form6").ligerGrid({
            	url:"farmer_reward_cost!loadByEntity",
   			    parms:{"e.batch.id":"${e.batch.id}"},
                columns:[
					{ display: '费用增减类型', name: 'reward.value'},
					{ display:'标准值',name:'standWeight',align:'right',render: function(data){
	                 	   if(data.standWeight){
							   value = (data.standWeight*1);
							   return value.toFixed(2);
						   }
	                }},
                	{ display: '实际值', name: 'avgWeight',align:'right',render: function(data){
	                 	   if(data.avgWeight){
							   value = (data.avgWeight*1);
							   return value.toFixed(4);
						   }
	                }},
	                { display: '偏移量', name: 'standFcr',align:'right',render: function(data){
	                 	   if(data.standFcr){
							   value = (data.standFcr*1);
							   return value.toFixed(4);
						   }
	                }},
					{ display: '净增值', name: 'netgain',align:'right',render: function(data){
	                 	if(data.netgain){
							   value = (data.netgain*1);
							   return value.toFixed(4);
						   }
	                	},totalSummary:{
		                render: function (suminf, column, cell){
	                        return '<div>合计:</div>';
	                    },
	                    align: 'left'
	                }},
					{ display: '数量', name: 'quantity',align:'right'},
					{ display: '单价(元)', name: 'price',align:'right',render: function(data){
	                 	   if(data.price!=null){
							   value = (data.price*1);
							   return value.toFixed(2);
						   }
	                }},
					{ display:'金额(元)',name:'money',align:'right',render: function(data){
	                 	   if(data.money!=null){
							   value = (data.money*1);
							   return value.toFixed(2);
						   }
	                	},totalSummary:{
		                render: function (suminf, column, cell){
	                        return '<div>'+(suminf.sum).toFixed(2) + '</div>';
	                    },
	                    align: 'left'
	                }}
					
        	     ],
       			 usePager: false,
       			 checkbox:false,
       			 height:'99%',
                 rownumbers:true
            });
           
        	$("#tab").ligerTab();
			//分组 收缩/展开  
		    $(".togglebtn").live('click', function (){
		    	gridsale.setHeight(gridsale.options.height);
		    	gridfeed.setHeight(gridfeed.options.height);
		    	griddrug.setHeight(griddrug.options.height);
		    	gridother.setHeight(gridother.options.height);
		    	gridpig.setHeight(gridpig.options.height);
		    	gridreward.setHeight(gridreward.options.height);
		    });
        });
    </script>
</head>
<body>
	<div id="toolbar"></div>
	<div id="form">
		<form id="form0"></form>
	</div>
	<div id="tab">
		<div id="tab1" title="销售明细" >
			<form id="form1"></form>
		</div>
		<div id="tab2" title="饲料明细">
			<form id="form2"></form>
		</div>
		<div id="tab3" title="药品明细" >
			<form id="form3"></form>
		</div>
		<div id="tab4" title="物料明细" >
			<form id="form4"></form>
		</div>
		<div id="tab5" title="猪苗明细" >
			<form id="form5"></form>
		</div>
		<div id="tab6" title="费用增减" >
			<form id="form6"></form>
		</div>
	</div>
	<div id="loading" class="l-sumbit-loading" style="display: none;"></div>
</body>
</html>
