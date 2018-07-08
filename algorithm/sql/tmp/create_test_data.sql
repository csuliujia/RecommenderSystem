//action_cleaned是10万的数据集。
create table if not exists interest_rate_1 (user_id string ,
										  item_id string,
										  value double);

--计算兴趣度
insert overwrite table interest_rate_1
	select user_id as user_id,
		   item_id as item_id,
		   bhv_amt as value
    from action_cleaned;

 ALTER TABLE  interest_rate_1 change column interest_rate RENAME TO value;

//测试计算结果与sql算出来的是否一致


--1M数据
--创建兴趣度表
create table interest_rate_1M(
  user_id string,
  item_id string,
  value double,
  date_time  string
)
;

create table interest_rate_20M(
  user_id string,
  item_id string,
  value double,
  date_time  string
)
;

from graph_output
insert overwrite table TMP_USER_SIM
  select
	  id_1 as user1,
	  id_2 as user2,
	  interest_rate_product/(length_1*length_2) as sim
  where flag=true
insert overwrite table TMP_ITEM_SIM
  select
	  id_1 as user1,
	  id_2 as user2,
	  interest_rate_product/(length_1*length_2) as sim
  where flag=FALSE
;