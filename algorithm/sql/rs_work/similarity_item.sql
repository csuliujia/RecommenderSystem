-----------------------------------------------------------------------------------------------
-- 基于物品相似度的推荐
-----------------------------------------------------------------------------------------------

-----------------------------------------------------------------------------------------------
-- 1、统计物品的关注用户总数
-----------------------------------------------------------------------------------------------
drop table if exists TMP_ITEM_STATISTICS;
create table TMP_ITEM_STATISTICS as
select
  t.item_id,
  t.user_id,
  t.value,
  sum(t.value*t.value) over(partition by t.item_id) as sum_square
from
  interest_rate t
;

-----------------------------------------------------------------------------------------------
-- 2、计算物品相似度
-----------------------------------------------------------------------------------------------
drop table if exists TMP_ITEM_SIMILARITY;
create table TMP_ITEM_SIMILARITY as
select
  item1,
  item2,
  inner_product/sqrt(sum_square1 * sum_square2) as sim
from
(
  --计算两个物品共同的关注用户总数
  select
    item1,
    sum_square1,
    item2,
    sum_square2,
    sum(value1*value2) as inner_product
  from
  (
    --计算两个物品共同的关注用户
    select
      t1.item_id as item1,
      t1.value as value1,
      t1.sum_square as sum_square1,
      t2.item_id as item2,
      t2.value as value2,
      t2.sum_square as sum_square2
    from
      TMP_ITEM_STATISTICS t1
    join
      TMP_ITEM_STATISTICS t2
    on
      t1.user_id = t2.user_id
    where
      t1.item_id != t2.item_id
  )t
  group by
    item1, sum_square1, item2, sum_square2
)  t
;

-----------------------------------------------------------------------------------------------
-- 3、保留最相关的5个物品
-----------------------------------------------------------------------------------------------
truncate table ITEM_SIMILARITY_N;
insert overwrite table ITEM_SIMILARITY_N
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
    TMP_ITEM_SIMILARITY
) t
where
  rank<=5
;

-----------------------------------------------------------------------------------------------
-- 4、计算相关物品推荐列表
-----------------------------------------------------------------------------------------------
truncate table SIMILAR_ITEM;
insert overwrite table SIMILAR_ITEM
select
  item1 as key,
  wm_concat(';',concat(item2,':',round(sim,2) ) ) as value
from
  ITEM_SIMILARITY_N
group by
  item1
;


-----------------------------------------------------------------------------------------------
-- 5、删除临时数据，避免不必要的空间占用（调试阶段可以先不删除，中间结果对问题定位有帮助）
-----------------------------------------------------------------------------------------------
-- drop table if exists TMP_ITEM_STATISTICS;
-- drop table if exists TMP_ITEM_SIMILARITY;
