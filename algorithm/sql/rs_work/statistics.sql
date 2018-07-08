​-----------------------------------------------------------------------------------------------
-- 基于统计的物品相似度
-----------------------------------------------------------------------------------------------
--1、最大购买
--2、最大收藏
--3、最大访问量
--4、本年龄段最大访问/收藏/购买
--5、本性别最大访问/收藏/购买

--最大访问量
select count(*) cnt, item_id from ods_action group by item_id order by cnt desc;
--最大收藏
select count(*) cnt, item_id from ods_action where bhv_type = 'collect' group by item_id order by cnt desc;
--最大购买
select count(*) cnt, item_id from ods_action where bhv_type = 'consume' group by item_id order by cnt desc;


