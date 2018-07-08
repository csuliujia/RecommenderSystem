/* 注意：redis key区分大写*/

/* 整体流程：建表-创建分区-插入数据、导入数据 */

/* 创建三张表 */
/* 用户喜好的物品列表 */
CREATE TABLE user_cfr (
  key STRING,   --user_id
  value  STRING --ItemList
) PARTITIONED BY (
    dt STRING
);
/* 相关物品列表 */
CREATE TABLE item_cfr (
  key STRING,   --item_id
  value  STRING --ItemList
) PARTITIONED BY (
    dt STRING
);
/* 统计表（热门物品+最新上架等） */
CREATE TABLE statistic (
  key STRING,   --_hot、_new
  value  STRING --ItemList
) PARTITIONED BY (
    dt STRING
);

/* 为每个表创建或删除分区，仅以user_cfr为例，其余两张表这一操作皆相同 */
alter table user_cfr add if not exists partition (dt='20160930');
alter table user_cfr drop if exists partition (dt='20160930');

/* 在user_cfr表中插入数据 */
insert into table user_cfr partition (dt='20160930')
        select "x0404", "BMW_001:0.7;Geely_001:0.8;AUdi_007:0.7;Lexus_001:0.7" from (select count(*) from user_cfr) test;

/* 在item_cfr表中插入数据 */
insert into table item_cfr partition (dt='20160930')
        select "apple_01", "apple_03:0.9;apple_08:0.6;apple_05:0.7" from (select count(*) from item_cfr) test;

/* 在statistic表中插入数据（热门物品+最新上架等) */
insert into table statistic partition (dt='20160930')
         select "jd_hot", "tv_02:0.9;fridge_08:0.5" from (select count(*) from item_cfr) test;
insert into table statistic partition (dt='20160930')
         select "jd_new", "tv_07:0.7;video_04:0.5;microwave_01:0.3" from (select count(*) from item_cfr) test;

/* 往user_cfr表导入测试数据，测试数据存在test.txt文档中 */
tunnel upload test.txt user_cfr/dt="20160930";
-- test.txt文件内容格式如下：
-- b302,NIKE_001:0.3;Adidas_001:097

