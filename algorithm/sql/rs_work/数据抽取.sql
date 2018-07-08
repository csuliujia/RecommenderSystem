-----------------------------------------------------------------------------------------------
-- 数据抽取
-----------------------------------------------------------------------------------------------
--1、将分区表抽取成为普通表
--2、每人抽取限制为100条
--3、后续可以考虑限制时间为1年内

drop table if exists TMP_ODS_ACTION;

create table TMP_ODS_ACTION as
select * from 
	(select 
		*, 
		row_number() over (partition by user_id order by pt desc) as row_num 
	  from ods_action) t 
where row_num <= 100;
