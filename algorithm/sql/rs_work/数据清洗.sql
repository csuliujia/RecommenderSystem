​-----------------------------------------------------------------------------------------------
-- 数据清洗
-----------------------------------------------------------------------------------------------
-- 需要考虑的几个问题
-- 1. 数据清洗的方式，去重、数据一致性检查、非法值检查、数据标准化
-- 2. 清洗后数据的存储方式，如果按分区存储，分区间出现重复的数据如何处理？

truncate table ods_action_clean;

insert overwrite table ods_action_clean
select /*+ mapjoin(b) */
	 a.user_id,
    a.item_id,
    a.bhv_type,
    (a.bhv_amt - b.min_bhv_amt)/(b.max_bhv_amt - b.min_bhv_amt) as bhv_amt,
    a.bhv_cnt,
    a.bhv_datetime,
    a.content,
    a.media_type,
    a.pos_type,
    a.position,
    a.env,
    a.trace_id,
	 a.pt
from
	TMP_ODS_ACTION a
left outer join
	(
		select
			min(bhv_amt) as min_bhv_amt,
			max(bhv_amt) as max_bhv_amt
	  	from
	  		TMP_ODS_ACTION
	)b
on 1 = 1;