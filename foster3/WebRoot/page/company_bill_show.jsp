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
    	var gridfeed,griddrug,gridother,gridpig,gridfreight,gridsale;
    	 // 获得当前页面对象
		 //var currentPage = $.pap.getOpenPage(window);
		 //var currentParam = currentPage.getParam("param");
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
				  			 		tabid:'viewCompanyBill'+infoId,
		  	   	    				text: '公司账单【'+infoId+'】',
		      	   					params:{ 'ids':ids,'id':infoId},
		  	   	    				url: 'company_bill!loadDetailById?id='+infoId
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
		  			 		tabid:'viewCompanyBill'+infoId,
  	   	    				text: '公司账单【'+infoId+'】',
      	   					params:{ 'ids':ids,'id':infoId},
  	   	    				url: 'company_bill!loadDetailById?id='+infoId
  	   	    			});
	  	   	    		$.pap.removeTabItem();
 			      }}
                 ]
             });
            $("#form0").ligerForm({
                fields: [
					{ display: "账单编号", name:"e.id",  type: "text", options:{value: '${e.id}',readonly: true},group: "公司账单", groupicon: groupicon},
					{ display: "养殖公司", name:"e.company.name",  type: "text", options:{value: '${e.company.name}',readonly: true}},
					{ display: "账单时间", name: "e.registDate",type: "date",options:{format : "yyyy-MM-dd",showTime: true,value:"${e.registDate}",readonly:true}},
					{ display: "代养农户", name:"e.farmer.name",newline: true, type: "text", options:{value: '${e.farmer.name}',readonly: true}},
					{ display: "所属批次", name:"e.batch.batchNumber", type: "text", options:{value: '${e.batch.batchNumber}',readonly: true}},
					
					{ display: "饲料费用(元)", name:"e.feedCost",  type: "text",options:{value: '${e.feedCost}',readonly: true,render: function(data){
	                 	   if(data){
							   value = (data*1);
							   return value.toFixed(2);
						   }
	                	}},
						group: "费用信息", groupicon: groupicon},
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
					{ display: "采购运费(元)", name:"e.freight",  type: "text",newline: true,options:{value: '${e.freight}',readonly: true,render: function(data){
					     if(data!=null){
							value = (data*1);
							return value.toFixed(2);
						}
					}}},
					{ display: "销售运费(元)", name:"e.salefreight",  type: "text",options:{value: '${e.salefreight}',readonly: true,render: function(data){
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
					{ display: "销售收入(元)", name:"e.saleIncome",  type: "text",options:{value: '${e.saleIncome}',readonly: true,render: function(data){
						if(data){
							value = (data*1);
							return value.toFixed(2);
						}
					}}},
					{ display: "营收毛利(元)", name:"e.profit",newline: true,  type: "text",options:{value: '${e.profit}',readonly: true,render: function(data){
						if(data){
							value = (data*1);
							return value.toFixed(2);
						}
					}}},
					{ display: "成本合计(元)", name:"e.allCost",  type: "text",options:{value: '${e.allCost}',readonly: true,render: function(data){
						if(data){
							value = (data*1);
							return value.toFixed(2);
						}
					}}},
					{ display: "头均成本(元)", name:"e.avgCost",  type: "text",options:{value: '${e.avgCost}',readonly: true,render: function(data){
						if(data){
							value = (data*1);
							return value.toFixed(2);
						}
					}}},
					{ display: "头均利润(元)", name:"e.avgProfit",  type: "text",options:{value: '${e.avgProfit}',readonly: true,render: function(data){
							if(data){
								value = (data*1);
								return value.toFixed(2);
							}
					}}}
					
                 ]
            });
           	//销售
            gridsale = $("#form1").ligerGrid({
            	url:"company_sale_income!loadByEntity",
   			    parms:{"e.batch.id":"${e.batch.id}"},
                columns:[
					{ display:'销售单号', name: 'companySale.id'},
					{ display:'销售日期', name: 'companySale.registDate'},
					{ display:'销售商', name: 'companySale.buyer.name',totalSummary:{
                        render: function (suminf, column, cell){
	                        return '<div>合计:</div>';
	                    },align: 'left'
	                }},
					{ display:'头数',name:'companySale.quantity',align:'right',totalSummary:{
                        render: function (suminf, column, cell){
	                        return '<div>'+(suminf.sum).toFixed(0) + '</div>';
	                    }
	                }},
					{ display:'重量(kg)',name:'companySale.weight',align:'right',render: function(data){
	                 	   if(data.companySale.weight!=null){
							   value = (data.companySale.weight*1);
							   return value.toFixed(2);
						   }},totalSummary:{
                        render: function (suminf, column, cell){
	                        return '<div>'+(suminf.sum).toFixed(2) + '</div>';
	                    }
	                }},
					{ display:'单价(元)', name: 'price',align:'right'},
					{ display:'金额(元)',name:'companySale.amount',align:'right',render: function(data){
	                 	   if(data.companySale.amount!=null){
							   value = (data.companySale.amount*1);
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

            //饲料投入
            gridfeed = $("#form2").ligerGrid({
  				 detailsIsNotNull:true,
    			 url:"company_feed_cost!loadByEntity",
    			 parms:{"e.batch.id":"${e.batch.id}"},
                 columns:[
                      	{ display: '单据类型', name: 'billType', render: function(data,rowindex){
							var value = data.billType;
							if(value=='1')
								return '饲料领用单';
							else if(value=='2')
								return '饲料转接单';
							return value;
						}},
						{ display:'单据号',name:'feedInWare'},
						{ display:'单据日期',name:'registDate'},
 	       				{ display:'饲料',name:'feed.name',totalSummary:{
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
		                    },
		                    align: 'left'
		                }},
 		       			{ display:'定价单',name:'feedPrice.id'},
	       				{ display:'单价(元/kg)',name:'price',align:'right'},
	       		 		{ display:'金额(元)',name:'money',width:100,align:'right',render: function(data){
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
            //药品费用
            griddrug = $("#form3").ligerGrid({
            	 detailsIsNotNull:true,
   			     url:"company_drug_cost!loadByEntity",
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
						{ display:'单据号',name:'billNum'},
						{ display:'单据日期',name:'registDate'},
 	       				{ display:'药品',name:'drug.name'},
 	       				{ display:'单位',name:'drug.unit.value',totalSummary:{
	                        render: function (suminf, column, cell){
		                        return '<div>合计:</div>';
		                    },align: 'left'
		                }},
 	       				{ display:'数量',name:'quantity',align:'right',render: function(data){
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
       		 			{ display:'单价',name:'price',align:'right'},
       		 			{ display:'定价单',name:'drugPrice.id'},
       		 			{ display:'金额',name:'money',align:'right',render: function(data){
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
            //其他物料
            gridother = $("#form4").ligerGrid({
            	url:"company_other_cost!loadByEntity",
   			    parms:{"e.batch.id":"${e.batch.id}"},
                columns:[
					{ display: '物料', name: 'material.name'},
					{ display: '单位',name:'material.unit.value',totalSummary:{
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
					{ display:'定价单',name:'materialPrice.id'},
					{ display: '单价(元)', name: 'price',align:'right'},
					{ display: '金额(元)',name:'money',align:'right',render: function(data){
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
            	url:"company_piglet_cost!loadByEntity",
   			    parms:{"e.batch.id":"${e.batch.id}"},
                columns:[
					{ display: '采购单', name: 'pigPurchase.id'},
					{ display: '采购日期', name: 'pigPurchase.registDate',totalSummary:{
                        render: function (suminf, column, cell){
	                        return '<div>合计:</div>';
	                    },
	                    align: 'left'
	                }},
					{ display: '数量(头)', name: 'quantity',align:'right',totalSummary:{
                        render: function (suminf, column, cell){
	                        return '<div>'+(suminf.sum).toFixed(0) + '</div>';
	                    },
	                    align: 'left'
	                }},
					{ display: '单价', name: 'quantity',align:'right'},
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
          	//运费
            gridreward = $("#form6").ligerGrid({
            	url:"company_freight!loadByEntity",
   			    parms:{"e.batch.id":"${e.batch.id}"},
                columns:[
					{ display: '运费类型', name: 'freightType', render: function(data,rowindex){
	    				var value = data.freightType;
	    				if(value=='1')
          					return '猪苗运费';
	    				else if(value=='2')
	    					return '饲料运费';
	    				else if(value=='3')
	    					return '销售运费';
						return value;
    				}},
					{ display: '单据日期', name: 'registDate'},
					{ display: '单据号',name:'billId',totalSummary:{
                        render: function (suminf, column, cell){
	                        return '<div>合计:</div>';
	                    },
	                    align: 'left'
	                }},
					{ display: '饲料重量(KG)', name: 'feedWeight',align:'right',render: function(data){
	                 	   if(data.feedWeight!=null){
							   value = (data.feedWeight*1);
							   return value.toFixed(2);
						   }
	                	},totalSummary:{
                        render: function (suminf, column, cell){
	                        return '<div>'+(suminf.sum).toFixed(2) + '</div>';
	                    },
	                    align: 'left'
	                }},
					{ display: '饲料包装', name: 'feedPack',render: function(data,rowindex){
	    				var value = data.feedPack;
	    				if(value=='1')
          					return '散装';
	    				else if(value=='2')
	    					return '袋装';
						return value;
    				}},
					{ display: '单价(元/吨)', name: 'price',align:'right'},
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
		    	gridfreight.setHeight(gridfreight.options.height);
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
		<div id="tab6" title="运费明细" >
			<form id="form6"></form>
		</div>
		
	</div>
	<div id="loading" class="l-sumbit-loading" style="display: none;"></div>
</body>
</html>
