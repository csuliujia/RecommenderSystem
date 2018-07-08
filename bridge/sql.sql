/*可能需要的sql语句*/

select * from a;
insert overwrite table a partition (ds="20160830") select "placeholder" from ( select count(*) from a) test;

CREATE TABLE action2 (
    user_id STRING,
    item_id STRING,
    bhv_type STRING,
    bhv_amt DOUBLE,
    bhv_cnt DOUBLE,
    bhv_datetime DATETIME,
    content STRING,
    media_type STRING,
    pos_type STRING,
    position STRING,
    env STRING,
    trace_id STRING
)
PARTITIONED BY (
    ds STRING
);

INSERT overwrite TABLE action2 PARTITION (ds=20160903)
SELECT ' ' AS user_id
    , '' AS item_id
    , '' AS bhv_type
    , 0 AS bhv_amt
    , 0 AS bhv_cnt
    , NULL AS bhv_datetime
    , ' ' AS content
    , ' ' AS media_type
    , ' ' AS pos_type
    , ' ' AS position
    , ' ' AS env
    , ' ' AS trace_id
FROM (SELECT count(*) from  action)test;

alter table action2 drop if exists partition (ds=20160901);

/*--------------------------------------------------------------*/

select  s.common,  t.alldata,  s.common/t.alldata  as  sim  from
    (select  1  as  id,  count(*)  as  common  from
        (select  *  from  user_behavior  where  user_id  =  '127233')  a  join
            (select  *  from  user_behavior  where  user_id  =  '127234')  b
              on  a.item_id  =  b.item_id) s
join  (select  1  as  id,  count(*)  as  alldata  from
(select  distinct(item_id)  from  user_behavior  where  user_id  =    '127233'  or  user_id=  '127234') p )t
on  s.id  =  t.id

select  *  from
        (select  a.user_id  as  aid,  b.user_id  as  bid  from
                (select  distinct(user_id)  as  user_id,  1  as  tag
                      from  user_behavior)a
                join
                (select  distinct(user_id)  as  user_id,  1  as  tag
                      from  user_behavior  )b
                on  a.tag  =  b.tag) t
where  aid  <  bid



/*基于同一场电影的过滤掉评分3星以下的用户做笛卡尔积，10:04开始*/
CREATE TABLE user_pairs AS
  SELECT Split_part(concatstr, ',', 1) AS aid,
         Split_part(concatstr, ',', 2) AS bid
  FROM   (SELECT DISTINCT( Concat(aid, ',', bid) ) AS concatstr
          FROM   (SELECT a.user_id AS aid,
                         b.user_id AS bid,
                         a.item_id AS item_id,
                         a.bhv_amt AS apingfen,
                         b.bhv_amt AS bpingfen
                  FROM   (SELECT *
                          FROM   user_behavior
                          WHERE  bhv_amt > 3.0)a
                         JOIN (SELECT *
                               FROM   user_behavior
                               WHERE  bhv_amt > 3.0)b
                           ON a.item_id = b.item_id)t
          WHERE  aid < bid)s;

CREATE TABLE user_pairs AS
  SELECT aid,
         bid,
         Count(*) AS common
  FROM   (SELECT a.user_id AS aid,
                 b.user_id AS bid,
                 a.item_id AS item_id,
                 a.bhv_amt AS apingfen,
                 b.bhv_amt AS bpingfen
          FROM   (SELECT *
                  FROM   user_behavior
                  WHERE  bhv_amt > 3.0)a
                 JOIN (SELECT *
                       FROM   user_behavior
                       WHERE  bhv_amt > 3.0)b
                   ON a.item_id = b.item_id)t
  WHERE  aid < bid
  GROUP  BY aid,
            bid;
