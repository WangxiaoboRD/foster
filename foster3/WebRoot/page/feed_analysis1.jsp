<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <title>本部项目公司</title>
    <link rel="shortcut icon" href="../image/logo.ico" type="image/x-icon"/> 
    <link href="../ligerUI/ligerUI/skins/Aqua/css/ligerui-all.css" rel="stylesheet" type="text/css" />
	<link href="../ligerUI/ligerUI/skins/ligerui-icons.css" rel="stylesheet" type="text/css" />	
    <link href="../ligerUI/original/echarts-2.2.7/css/font-awesome.min.css" rel="stylesheet" type="text/css"/>
    <script src="../ligerUI/jquery/jquery-1.6.4.js" type="text/javascript"></script>
    <script src="../ligerUI/jquery-validation/jquery.form.js" type="text/javascript"></script> 
    <script src="../ligerUI/ligerUI/js/ligerui.all.js" type="text/javascript"></script>
    <script src="../original/echarts-2.2.7/build/dist/echarts.js"></script>
</head>
<body>
	<div style=" position:absolute; left:0; top:0px; width:100%; height:100%;">
        <div style="height:99%">
         	<div style="height:40%;border:1px solid #999999;color:#fff;">
         		<div id='area1' style="display:inline;float:left;width:99%;height:100%;border:1px solid #999999;text-align:center;"></div>
         	</div>
         	<div style="height:45%;text-align:center;border:1px solid #999999;">
         		<div id='area2' style="display:inline;float:left;width:99%;height:100%;border:1px solid #999999;text-align:center;"></div>
         	</div>
         	<div style="height:45%;text-align:center;border:1px solid #999999;">
         		<div id='area3' style="display:inline;float:left;width:99%;height:100%;border:1px solid #999999;text-align:center;"></div>
         	</div>
         	<div style="height:45%;text-align:center;border:1px solid #999999;">
        		<form id = 'form1'></form>
				<div id='grid' ></div>
         	</div>
        </div>
    </div>
    <script src="../ligerUI/original/echarts-2.2.7/build/dist/echarts.js"></script>
    <script type="text/javascript">
       $(function () {
       		//初始化数据
       		var dataMap;
       		//定义变量
			var grids;
       		var name = [];
       		//完成率图
       		var slaughter = [];
       		var salesvalue = [];
       		var margin = [];
       		//柱形图
       		var slaughterNum = [];
       		var salesvalueNum = [];
       		var marginNum = [];
       		//折线图
       		var slaughterAn = [];
       		var slaughterMom = [];
       		var salesvalueAn = [];
       		var salesvalueMom = [];
       		var marginAn = [];
       		var marginMom = [];
			
			//解析
			function resolves(value){
				//完成率图
				slaughter.length=0;
				salesvalue.length=0;
				margin.length=0;
				//柱形图
				slaughterNum.length=0;
				salesvalueNum.length=0;
				marginNum.length=0;
				//折线图
				slaughterAn.length=0;
       			slaughterMom.length=0;
       			salesvalueAn.length=0;
       			salesvalueMom.length=0;
       			marginAn.length=0;
       			marginMom.length=0;
				//名称
				name.length=0;
				//表格
				grids = {};
			 	if(value){
			 		if(value.TZL){
			 			grids = { 'Rows': value.TZL };
				 		var item = value.TZL;
			       		for(var i in item){
			       		   //名称
			       		   name.push(item[i]['cwDate'].substring(8));
			       		   //屠宰 销售 毛利
			       		   slaughterNum.push(item[i]['slaughterNum']);
			       		   salesvalueNum.push(item[i]['salesNum']);
			       		   marginNum.push(item[i]['margin']);
			       		   //曲线图
			       		   
			       		   var shAn = item[i]['slaughterAn'];
			       		   var shMom = item[i]['slaughterMom'];
			       		   var slAn = item[i]['salesvalueAn'];
			       		   var slMom = item[i]['salesvalueMom'];
			       		   var mmAn = item[i]['marginAn'];
			       		   var mmMom = item[i]['marginMom'];
			       		   
			       		   if(shAn){
			       		   		slaughterAn.push(shAn.replace("%","")*1);
			       		   }else{
			       		   		slaughterAn.push(0);
			       		   }
			       		   if(shMom){
			       		   	    slaughterMom.push(shMom.replace("%","")*1);
			       		   }else{
			       		   		slaughterMom.push(0);
			       		   }
			       		   if(slAn){
			       		   		salesvalueAn.push(slAn.replace("%","")*1);
			       		   }else{
			       		   		salesvalueAn.push(0);
			       		   }
			       		   
			       		   if(slMom){
			       		   		salesvalueMom.push(slAn.replace("%","")*1);
			       		   }else{
			       		   		salesvalueMom.push(0);
			       		   }
			       		   
			       		   if(mmAn){
			       		   		marginAn.push(mmAn.replace("%","")*1);
			       		   }else{
			       		   		marginAn.push(0);
			       		   }
			       		   
			       		   if(mmMom){
			       		   		marginMom.push(mmMom.replace("%","")*1);
			       		   }else{
			       		   		marginMom.push(0);
			       		   }
			        	}
			 		}else{
			 			   //名称
			       		   name.push("01");
			       		   name.push("02");
			       		   name.push("03");
			       		   name.push("04");
			       		   name.push("05");
			       		   name.push("06");
			       		   name.push("07");
			       		   name.push("08");
			       		   name.push("09");
			       		   name.push("10");
			       		   for(var i=0;i<10;i++){
				       		   //屠宰 销售 毛利
				       		   slaughterNum.push(0);
				       		   salesvalueNum.push(0);
				       		   marginNum.push(0);
				       		   //曲线图
				       		   slaughterAn.push(0);
				       		   slaughterMom.push(0);
				       		   salesvalueAn.push(0);
				       		   salesvalueMom.push(0);
				       		   marginAn.push(0);
				       	       marginMom.push(0);
			       	       }
			 		}
			 		if(value.WCL){
			 			var cw = value.WCL;
			 			var _valueTZ = cw['slaughterRate'];
			 			_valueTZ = _valueTZ.replace("%","")*1;
			 			slaughter.push({value: _valueTZ, name: '屠宰进度'});
			 			var _valueXS = cw['salesRate'];
			 			_valueXS = _valueXS.replace("%","")*1;
			 			salesvalue.push({value: _valueXS, name: '销售进度'});
			 			var _valueML = cw['marginRate'];
			 			_valueML = _valueML.replace("%","")*1;
			 			margin.push({value: _valueML, name: '毛利进度'});
			 			
			 		}else{
			 			slaughter.push({value: 0, name: '屠宰进度'});
			 			salesvalue.push({value: 0, name: '销售进度'});
			 			margin.push({value: 0, name: '毛利进度'});
			 		}
	           	}
			}
       		
       		require.config({
		        paths: {
		    	 	echarts: '../ligerUI/original/echarts-2.2.7/build/dist'
		        }
			});
			
			//配置图表
		    var THBDiv;
			require(
		        [
		            'echarts',
		            'echarts/chart/pie',
		            'echarts/chart/gauge',
		            'echarts/chart/line',
		            'echarts/chart/funnel',
		            'echarts/chart/bar'
		        ],
		        function (ec){
		        	THBDiv = ec.init(document.getElementById('area3'),'shine');
		        	//渲染
		        	THBDiv.setOption(THBOP,true);
		   		}
		    );
		    //form表格
	        var form = $("#form1").ligerForm({
		  		 inputWidth: 150, labelWidth:80, space:20,
	             fields: [
	             	{ display: "记账日期", name:"e.cwDate",type:"date",options:{format: "yyyy-MM-dd",value:new Date((new Date().getTime()) - 24*60*60*1000)}}
	             ],
	             buttons:[
	                 {text:'查询',click:function(item){
						var arra = $('form').formSerialize();
						arra += "&e.factory.id=3000"
						$.ajax({    
						    url:'cw_source!getSingleNum',// 跳转到 action    
						    type:'post',
						    data:arra,    
						    cache:false,
						    async:false,    
						    dataType:'json',    
						    success:function(data) { 
						     	dataMap = data.Rows;
			      				console.dir(dataMap);   
						      	resolves(dataMap);
						    } 
						});
						grid.loadData(grids);
						//折线图
               			THBOP.series[0].data = slaughterAn;
               			THBOP.series[1].data = salesvalueAn;
               			THBOP.series[2].data = marginAn;
               			THBOP.series[3].data = slaughterMom;
               			THBOP.series[4].data = salesvalueMom;
			        	THBOP.series[5].data = marginMom;
			        	
			        	THBDiv.setOption(THBOP,true);
					}}
			     ]
		    });
		    //表格
	        var grid = $("#grid").ligerGrid({
	           width: '99%', 
	           height: '99%',
	           rownumbers:true,
	           isScroll: false,                        
	           columns: [
	                   { display: '养殖公司',name:'companyName'},	
                       { display: '代养户',name:'farmerName'},
                       { display: '批次',name:'batchNumber'},
                       { display: '日期',name:'feedDate'},
                       { display: '饲养天数',name:'feedDays',align:"right"},
                       { display: '日龄',name:'dayAge',align:"right"},
                       { display: '标准喂料类别',name:'sdFeedType',width:120},
                       { display: '标准喂料量',name:'sdFeedQty',align:"right"},
                       { display: '实际喂料类别',name:'acFeedType'},
                       { display: '实际喂料量',name:'acFeedQty',align:"right"},
                       { display: '实际累计喂料量',name:'acTolFeedQty',align:"right"},
	           ],
	           usePager: false,
	           data: grids
	       });
		    
		    
			//同比环比
		    var THBOP = {
			    tooltip : {
			        trigger: 'axis'
			    },
			    toolbox: {
			        show : true,
			        y: 'bottom',
			        feature : {
			            magicType : {show: true, type: ['line', 'bar']},
			            restore : {show: true},
			            saveAsImage : {show: true}
			        }
			    },
			    calculable : true,
			    legend: {
			        data:['屠宰同比','销售同比','毛利同比','屠宰环比','销售环比','毛利环比']
			    },
			    grid:{
			    	x:40
			    },
			    xAxis : [
			        {
			            show:true,
			            type : 'category',
			            axisLine:{
			            	lineStyle:{
				            	color: "#30e0e0",
							    width: 1,
							    type: 'solid'
				            }
			            },
			            splitLine : {show : false},
			            //data : ['长春','长葛','驻马店','洛阳','安阳','永城','泰州','天津','KA','调理']
			            data:name
			        }
			    ],
			    yAxis : [
			        {
				    	type : 'value',
				    	//scale: true,
				    	name : '(值)%'
			        }
			    ],
			    series : [
			        {
			           name:'屠宰同比',
			           type:'line',
			           data:slaughterAn
			        },
			        {
			           name:'销售同比',
			           type:'line',
			           data:salesvalueAn
			        },
			        {
			           name:'毛利同比',
			           type:'line',
			           data:marginAn
			        },
			         {
			           name:'屠宰环比',
			           type:'line',
			           data:slaughterMom
			        },
			        {
			           name:'销售环比',
			           type:'line',
			           data:salesvalueMom
			        },
			        {
			           name:'毛利环比',
			           type:'line',
			           data:marginMom
			        }
			    ]
			};
       });
    </script> 
</body>
</html>
