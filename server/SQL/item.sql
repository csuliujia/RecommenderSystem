


/* 物品表 */
CREATE TABLE item (
    item_id STRING COMMONT '物品唯一标识',
    category STRING COMMENT '物品所属类目',
    keywords STRING COMMENT '关键词串',
    description STRING COMMENT '物品描述',
    properties STRING '物品属性-属性值kv串',
    bizinfo STRING COMMENT '物品的业务信息，KV格式'
)
PARTITIONED BY (
    dt STRING COMMENT ' 日期分区 格式:[yyyyMMdd] '
);