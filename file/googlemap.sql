
use googlemap;

#����google��ͼ���ز�����

#���򻮷ֱ�
drop table if exists nation_division;
create table nation_division(	
	province varchar(10) default null,	#ʡ��
	start_p_longitude double default 0,	#ʡ����ʼ����
	start_p_latitude double default 0,  	#��ʼγ��
	end_p_longitude double default 0, 	#��ֹ����
	end_p_latitude double default 0,    	#��ֹγ��
	city varchar(15) default null,		#����
	start_ci_longitude double default 0, 	#���п�ʼ����
	start_ci_latitude double default 0,  	#��ʼγ��
	end_ci_longitude double default 0,   	#��ֹ����
	end_ci_latitude double default 0,    	#��ֹγ��	
	county varchar(15) default null,  	#����
	start_co_longitude double default 0,	#���ؿ�ʼ����
	start_co_latitude double default 0,  	#��ʼγ��
	end_co_longitude double default 0,   	#��ֹ����
	end_co_latitude double default 0,    	#��ֹγ��
	status int default 0			#�Ƿ��Ѿ���ѡ��
);

#�Ͽ���

drop table if exists break_place;
create table break_place(
	break_position varchar(100) default null, #�Ͽ�λ�ã��ļ���·����
	time varchar(100) default null,		#�Ͽ�ʱ�� 
	break_col int default 0,		#�Ͽ���		
	break_row int default 0,		#�Ͽ���	
	break_longitude double default 0,	#�Ͽ�����
	break_latitude double default 0,	#�Ͽ�γ��
	end_longitude double default 0,		#��ֹ����
	end_latitude double default 0,		#��ֹγ��
	break_zoom int default 0,		#�Ͽ�����
	status int default 0			#�Ƿ��ѱ�ȡ

);			
	
#������ʶ����������
drop table if exists area_recognition;
create table area_recognition( 
	areaname varchar(50) default null,	#������
	latitude double default 0,		#γ��
	longitude double default 0		#����
);
	

#�������������ͳ�Ʊ�
drop table if exists quantity;
create table quantity(
	province varchar(10) default null,	#ʡ����
	city varchar(10) default null,		#������
	areaname varchar(10) default null,	#������
	townname varchar(20) default null,	#������
	vilagename varchar(20) default null,	#������
	sum int default 0			#ʶ������
);

#��������ļ���λ��
drop table if exists vilage_path;
create table vilage_path(
	vilagename varchar(20) default null,	#������
	path varchar(100) default null		#λ��
);


#����ͼ��ʶ���
drop database if exists mapparameter;
create database mapparameter;

use mapparameter;

#��������Ӳ����洢��

drop table if exists houseParameter;
create table houseParameter(	
	vilageName varchar(100) default null,	#�������������
	type varchar(10) default null,		#�������
	damageDegree int default 0,		#��������
	number int default 0,			#�������
	styleStartTime varchar(10) default null,#��ӷ����ʼ���
	styleEndTime varchar(10) default null,	#��ӷ����ֹ���
	radius int default 0,			#�������Ӱ��뾶
	relation varchar(10) default null,	#��Ӵ��ʹ�ϵ
	humanDegree int default 0		#������ķ��
	
)ENGINE=INNODB DEFAULT CHARSET=utf8;

#�����Ŵ�����Ӵ洢��

drop table if exists vilageParameter;
create table vilageParameter(	
	vilageName varchar(100) default null,	#������
	vilageArea int default 0,		#�������
	vilageDensity int default 0,		#�����ܶ�
	vilageNeighbour int default 0,		#�����ܶ�
	vilageDegree int default 0,		#ӵ����
	cultureRating int default 0,		#�����̶�
	vilageRadius int default 0,		#Ӱ��뾶
	vilageSum int default 0,		#�������
	vilageStartTime varchar(10) default null,#�����ݱ���ʼʱ��
	vilageEndTime varchar(10) default null,	#�����ݱ���ֹʱ��
	vilageSize varchar(10) default null,	#�����ģ
	vilageShape varchar(10) default null	#������״
	
)ENGINE=INNODB DEFAULT CHARSET=utf8;

#���ʶ������

drop table if exists results;
create table results(	
	vilageName varchar(100) default null,		#������ڴ�����
	accuracyRating varchar(5) default null,		#���ʶ��׼ȷ��
	greenPercentage varchar(5) default null,	#ֲ���ٷֱ�
	fieldPercentage varchar(5) default null,	#��Ұ�ٷֱ�
	roadPercentage varchar(5) default null		#��·�ٷֱ�
	
);

#�����ʷ��¼��

drop table if exists historyRecord;
create table historyRecord(
	vilageName varchar(100) default null,		#������
	tangSum int default 0,				
	songSum int default 0,				#���������������		
	yuanSum int default 0,						
	mingSum int default 0,						
	qingSum int default 0,								
	minguoSum int default 0,						
	nowSum int default 0				
);



	
	
	
	