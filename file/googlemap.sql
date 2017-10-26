
use googlemap;

#创建google地图下载参数表

#区域划分表
drop table if exists nation_division;
create table nation_division(	
	province varchar(10) default null,	#省市
	start_p_longitude double default 0,	#省级开始经度
	start_p_latitude double default 0,  	#开始纬度
	end_p_longitude double default 0, 	#终止经度
	end_p_latitude double default 0,    	#终止纬度
	city varchar(15) default null,		#地市
	start_ci_longitude double default 0, 	#地市开始经度
	start_ci_latitude double default 0,  	#开始纬度
	end_ci_longitude double default 0,   	#终止经度
	end_ci_latitude double default 0,    	#终止纬度	
	county varchar(15) default null,  	#区县
	start_co_longitude double default 0,	#区县开始经度
	start_co_latitude double default 0,  	#开始纬度
	end_co_longitude double default 0,   	#终止经度
	end_co_latitude double default 0,    	#终止纬度
	status int default 0			#是否已经被选中
);

#断开处

drop table if exists break_place;
create table break_place(
	break_position varchar(100) default null, #断开位置（文件夹路径）
	time varchar(100) default null,		#断开时间 
	break_col int default 0,		#断开列		
	break_row int default 0,		#断开行	
	break_longitude double default 0,	#断开经度
	break_latitude double default 0,	#断开纬度
	end_longitude double default 0,		#终止经度
	end_latitude double default 0,		#终止纬度
	break_zoom int default 0,		#断开级数
	status int default 0			#是否已被取

);			
	
#界面点击识别的区域入库
drop table if exists area_recognition;
create table area_recognition( 
	areaname varchar(50) default null,	#区域名
	latitude double default 0,		#纬度
	longitude double default 0		#经度
);
	

#各地区民居数量统计表
drop table if exists quantity;
create table quantity(
	province varchar(10) default null,	#省市名
	city varchar(10) default null,		#地市名
	areaname varchar(10) default null,	#区域名
	townname varchar(20) default null,	#乡镇名
	vilagename varchar(20) default null,	#村落名
	sum int default 0			#识别总数
);

#民居所在文件夹位置
drop table if exists vilage_path;
create table vilage_path(
	vilagename varchar(20) default null,	#村落名
	path varchar(100) default null		#位置
);


#创建图像识别库
drop database if exists mapparameter;
create database mapparameter;

use mapparameter;

#创建古民居参数存储表

drop table if exists houseParameter;
create table houseParameter(	
	vilageName varchar(100) default null,	#民居所属村落名
	type varchar(10) default null,		#民居类型
	damageDegree int default 0,		#民居灾损度
	number int default 0,			#民居数量
	styleStartTime varchar(10) default null,#民居风格起始年代
	styleEndTime varchar(10) default null,	#民居风格终止年代
	radius int default 0,			#民居人文影响半径
	relation varchar(10) default null,	#民居代际关系
	humanDegree int default 0		#民居人文丰度
	
)ENGINE=INNODB DEFAULT CHARSET=utf8;

#创建古村落民居存储表

drop table if exists vilageParameter;
create table vilageParameter(	
	vilageName varchar(100) default null,	#村落名
	vilageArea int default 0,		#村落面积
	vilageDensity int default 0,		#村落密度
	vilageNeighbour int default 0,		#邻里密度
	vilageDegree int default 0,		#拥挤度
	cultureRating int default 0,		#文明程度
	vilageRadius int default 0,		#影响半径
	vilageSum int default 0,		#民居数量
	vilageStartTime varchar(10) default null,#村落演变起始时间
	vilageEndTime varchar(10) default null,	#村落演变终止时间
	vilageSize varchar(10) default null,	#村落规模
	vilageShape varchar(10) default null	#村落形状
	
)ENGINE=INNODB DEFAULT CHARSET=utf8;

#民居识别结果表

drop table if exists results;
create table results(	
	vilageName varchar(100) default null,		#民居所在村落名
	accuracyRating varchar(5) default null,		#民居识别准确率
	greenPercentage varchar(5) default null,	#植被百分比
	fieldPercentage varchar(5) default null,	#田野百分比
	roadPercentage varchar(5) default null		#道路百分比
	
);

#民居历史记录表

drop table if exists historyRecord;
create table historyRecord(
	vilageName varchar(100) default null,		#村落名
	tangSum int default 0,				
	songSum int default 0,				#各朝代民居数量表		
	yuanSum int default 0,						
	mingSum int default 0,						
	qingSum int default 0,								
	minguoSum int default 0,						
	nowSum int default 0				
);



	
	
	
	