-----------------------------------------------------------------------------------------------
-- 基于物品相似度的推荐
-----------------------------------------------------------------------------------------------

-----------------------------------------------------------------------------------------------
-- 计算物品相似度
-----------------------------------------------------------------------------------------------

drop table if exists TMP_ITEM_SIM;
create table TMP_ITEM_SIM as
select
	id_1 as user1,
	id_2 as user2,
	interest_rate_product/(length_1*length_2) as sim
from
  GRAPH_OUTPUT
where flag=false
;

-----------------------------------------------------------------------------------------------
-- 保留最相关的5个物品
-----------------------------------------------------------------------------------------------
truncate table ITEM_SIM_N;
insert overwrite table ITEM_SIM_N
select
  item1,
  item2,
  sim
from
(
  select
    item1,
    item2,
    sim,
    row_number() over(partition by item1 order by sim desc) as rank
  from
    TMP_ITEM_SIM
) t
where
  rank<=5
;

-----------------------------------------------------------------------------------------------
-- 计算相关物品推荐列表
-----------------------------------------------------------------------------------------------
truncate table SIMILAR_ITEM;
insert overwrite table SIMILAR_ITEM
select
  item1 as key,
  wm_concat(';',concat(item2,':',round(sim,2) ) ) as value
from
  ITEM_SIM_N
group by
  item1
;


-----------------------------------------------------------------------------------------------
-- 删除临时数据，避免不必要的空间占用（调试阶段可以先不删除，中间结果对问题定位有帮助）
-----------------------------------------------------------------------------------------------
-- drop table if exists TMP_ITEM_STATISTICS;
-- drop table if exists TMP_ITEM_SIMILARITY;
