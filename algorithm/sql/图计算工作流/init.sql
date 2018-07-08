--本sql用于创建graph compute的输入输出表

--在行为表里，统计用户总数和物品总数
drop table if exists GRAPH_INPUT;

create table GRAPH_INPUT(
    user_id string comment '用户id',
    user_length double comment '该用户对所有物品的评分向量的模，即所有评分的平方和开根',
    item_id string comment '物品id',
    item_length double comment '该物品的所有用户评分的模',
    interest_rate double comment '用户对物品的兴趣度'
);

insert overwrite table GRAPH_INPUT
select user_id,sqrt(sum1),item_id, sqrt(sum2),value
from 
(
  select
      user_id ,
      sum(value*value) over (partition by user_id) as sum1,
      item_id ,
      sum(value*value) overfi (partition by item_id) as sum2,
      value
  from
    INTEREST_RATE
)r
;


--将flag字段设置为分区字段，未运行过。
drop table if exists GRAPH_OUTPUT;
create table GRAPH_OUTPUT(
  flag boolean comment '指示是人还是物品',
  id_1 string comment '人1或物1的id',
  length_1 double comment '人1或物1向量的模长',
  id_2 string,
  length_2 double,
  interest_rate_product double comment '兴趣度之积'
)
;

drop table if exists GRAPH_OUTPUT_1;
create table GRAPH_OUTPUT_1(
  flag boolean comment '指示是人还是物品',
  id_1 string comment '人1或物1的id',
  length_1 double comment '人1或物1向量的模长',
  id_2 string,
  length_2 double,
  interest_rate_product double comment '兴趣度之积'
)
;


