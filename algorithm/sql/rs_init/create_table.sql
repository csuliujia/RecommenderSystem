
-- 存储原始用户行为的数据表
DROP TABLE IF EXISTS ods_action;
CREATE TABLE ods_action (
    user_id STRING COMMENT '用户标示',
    item_id STRING COMMENT '物品标示',
    bhv_type STRING COMMENT '行为类型',
    bhv_amt DOUBLE COMMENT '用户对物品的评分、消费、观看时长等',
    bhv_cnt DOUBLE COMMENT '行为次数，默认为1，消费可以埋购买件数',
    bhv_datetime DATETIME COMMENT '行为发生的时间，UTC格式。',
    content STRING COMMENT '用户对物品的评价文本',
    media_type STRING COMMENT '如果bhv_type=share，该字段记录分享到目标媒体。短信：sms，邮件：email，微博：sina_wb，微信好友：wechat_friend，微信朋友圈：wechat_circle，QQ空间：qq_zone，来往好友：laiwang_friend，来往动态：laiwang_circle',
    pos_type STRING COMMENT '行为发生的位置类型，和下面position字段联合使用，有三种取值：ll：经纬度格式的位置信息 gh：geohash格式的位置信息 poi：poi格式的位置信息',
    position STRING COMMENT '行为发生的位置，根据pos_type有不同的取值格式：如果pos_type=ll，position格式[longitude:latitude]  如果pos_type=gh，position格式[geohashcode] 如果pos_type=poi，position格式[poi_string]',
    env STRING COMMENT '行为发生时的环境信息，如：IP地址、终端类型等',
    trace_id STRING  COMMENT '返回的推荐列表用于跟踪效果。如果对item_id 的行为不是来自推荐引导，则为NUL'
)
PARTITIONED BY (
    pt STRING
);

-- 存储原始用户信息的数据表
DROP TABLE IF EXISTS ods_user;
CREATE TABLE ods_user(
	user_id  STRING COMMENT '用户标示',
	tags  STRING COMMENT '标签-标签值kv串'
)
PARTITIONED BY (
	pt STRING
);

-- 存储原始物品信息的数据表
DROP TABLE IF EXISTS ods_item;
CREATE TABLE ods_item(
	item_id  STRING COMMENT '物品ID，唯一标识',
	category  STRING COMMENT '物品所属类目',
	keywords  STRING COMMENT '关键词串',
	description  STRING COMMENT '用于描述这个物品的一段文本',
	properties  STRING COMMENT '属性-属性值kv串',
	bizinfo  STRING COMMENT '物品的业务信息，KV格式'
)
PARTITIONED BY (
	pt STRING
);


-- 存储清洗后用户行为的数据表
DROP TABLE IF EXISTS ods_action_clean;
CREATE TABLE ods_action_clean (
    user_id STRING COMMENT '用户标示',
    item_id STRING COMMENT '物品标示',
    bhv_type STRING COMMENT '行为类型',
    bhv_amt DOUBLE COMMENT '用户对物品的评分、消费、观看时长等,这里是归一化之后的结果',
    bhv_cnt DOUBLE COMMENT '行为次数，默认为1，消费可以埋购买件数',
    bhv_datetime DATETIME COMMENT '行为发生的时间，UTC格式。',
    content STRING COMMENT '用户对物品的评价文本',
    media_type STRING COMMENT '如果bhv_type=share，该字段记录分享到目标媒体。短信：sms，邮件：email，微博：sina_wb，微信好友：wechat_friend，微信朋友圈：wechat_circle，QQ空间：qq_zone，来往好友：laiwang_friend，来往动态：laiwang_circle',
    pos_type STRING COMMENT '行为发生的位置类型，和下面position字段联合使用，有三种取值：ll：经纬度格式的位置信息 gh：geohash格式的位置信息 poi：poi格式的位置信息',
    position STRING COMMENT '行为发生的位置，根据pos_type有不同的取值格式：如果pos_type=ll，position格式[longitude:latitude]  如果pos_type=gh，position格式[geohashcode] 如果pos_type=poi，position格式[poi_string]',
    env STRING COMMENT '行为发生时的环境信息，如：IP地址、终端类型等',
    trace_id STRING  COMMENT '返回的推荐列表用于跟踪效果。如果对item_id 的行为不是来自推荐引导，则为NUL',
	 pt STRING
);


-- 存储存储用户兴趣度的数据表
DROP TABLE IF EXISTS interest_rate;
CREATE TABLE interest_rate (
    user_id STRING COMMENT '用户标示',
    item_id STRING COMMENT '物品标示',
    value DOUBLE COMMENT '用户兴趣度',
	  pt STRING  COMMENT '时间'
);


-- itemcf算法的结果表
DROP TABLE IF EXISTS item_cf;
CREATE TABLE item_cf (
  key STRING COMMENT '用户标示',
  value  STRING COMMENT '推荐列表'
)
;


-- usercf算法的结果表
DROP TABLE IF EXISTS user_cf;
CREATE TABLE user_cf (
  key STRING COMMENT '用户标示',
  value  STRING COMMENT '推荐列表'
)
;

--物品相似度的中间表，仅保留最相关的n个物品
DROP TABLE IF EXISTS item_similarity_n;
CREATE TABLE item_similarity_n(
  item1 STRING COMMENT '物品1标示',
  item2 STRING COMMENT '物品2标示',
  sim DOUBLE COMMENT '相似度'
)
;

-- 相关物品推荐结果表
DROP TABLE IF EXISTS similar_item;
CREATE TABLE similar_item (
  key STRING COMMENT '物品标示',
  value  STRING COMMENT '推荐列表'
)
;

-- 统计推荐算法的结果表
DROP TABLE IF EXISTS statistics_ret;
CREATE TABLE statistics_ret (
  key STRING COMMENT '类目标示',
  value  STRING COMMENT '推荐列表'
)
;
