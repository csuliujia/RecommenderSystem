-----------------------------------------------------------------------------------------------
-- 基于物品的协同过滤
-----------------------------------------------------------------------------------------------

-----------------------------------------------------------------------------------------------
-- 1、计算物品推荐列表
-----------------------------------------------------------------------------------------------
drop table if exists TMP_ITEM_SCORE;
create table TMP_ITEM_SCORE as
--计算每个物品的排名
select
	user_id,
  	item_id,
  	score,
	row_number() over(partition by user_id order by score desc) as rank
from
(
	--找出与用户关注的物品相关的所有物品
	select
		t.user_id as user_id,
		p.item2 as item_id,
		sum(t.value*p.sim) as score
	from
		INTEREST_RATE t
	join
		ITEM_SIMILARITY_N p
	on
	  	t.item_id=p.item1
	group by
	  	t.user_id, p.item2
)t1
;

-----------------------------------------------------------------------------------------------
-- 2、产出结果表
-----------------------------------------------------------------------------------------------
truncate table ITEM_CF;
insert overwrite table ITEM_CF
select
  user_id as key,
  wm_concat(';',concat(item_id,':',round(score,2) ) ) as value
from
(
  select
    user_id,
    item_id,
    score
  from
    TMP_ITEM_SCORE
  where
    rank<=5
)t
group by user_id
;
