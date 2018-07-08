-----------------------------------------------------------------------------------------------
-- 基于用户的协同过滤
-----------------------------------------------------------------------------------------------

-- 用户兴趣度是一个[0,1]的值
-- 用户兴趣度去值为1是代表用户已经拥有了这件物品（购买），如果兴趣度为0，代表用户没有对物品产生过行为。

-----------------------------------------------------------------------------------------------
--计算用户相似度
-----------------------------------------------------------------------------------------------
drop table if exists TMP_USER_SIM;
create table TMP_USER_SIM as
select
	id_1 as user1,
	id_2 as user2,
	interest_rate_product/(length_1*length_2) as sim
from
  GRAPH_OUTPUT
where flag=true
;

-----------------------------------------------------------------------------------------------
-- 计算物品推荐列表
-----------------------------------------------------------------------------------------------
drop table if exists TMP_USER_ITEM;
create table TMP_USER_ITEM as
select
	user1 as user_id , item_id, sum(sim * score)  as score
from
(
	select
		t1.user1,t1.user2,t1.sim, t2.item_id, t2.score
	from
	(
		-- 找出与当前用户兴趣最接近的前5个用户
		select
			user1,user2,sim
		from
		(
			select
				user1,
				user2,
				sim,
				row_number() over(partition BY user1 ORDER BY sim DESC) AS sim_rank
			from
				TMP_USER_SIM
		) t
		where
			sim_rank <= 5
	) t1
	join
	(
		-- 找到每个用户最近感兴趣的前5个物品
		select
			user_id, item_id, score
		from
		(
			select
				t.user_id, t.item_id, t.value as score,
				row_number() over(partition BY t.user_id ORDER BY t.value DESC) AS interest_rate_rank
			from
				interest_rate t
		) t
		where
			interest_rate_rank <= 5
	) t2
	on
		t1.user2 = t2.user_id
) t
group by
	user1, item_id
;

-----------------------------------------------------------------------------------------------
-- 结果整理，产出最终结果
-----------------------------------------------------------------------------------------------
-- 是否要排除在interest_rate中兴趣度为1的项，还需再考虑？？？？？
insert overwrite table USER_CF
select
	user_id as key,
	wm_concat(';',concat(item_id,':',round(score,2) ) ) as value
from
(
	select
		user_id,
		item_id,
		score,
		row_number() over(partition BY user_id ORDER BY score DESC) AS rank
	from
		TMP_USER_ITEM
) t
where
	rank <= 5
group by
	user_id
;

-----------------------------------------------------------------------------------------------
-- 删除临时数据，避免不必要的空间占用（调试阶段可以先不删除，中间结果对问题定位有帮助）
-----------------------------------------------------------------------------------------------
-- drop table if exists TMP_USER_STATISTICS;
-- drop table if exists TMP_USER_SIM;
-- drop table if exists TMP_USER_ITEM;
