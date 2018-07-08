

CREATE TABLE rec_item_info (
    item_id STRING COMMENT '允许被推荐的物品 ID',
    item_info STRING COMMENT '业务方需要的物品内部信息，对外不可知，原样返回给业务方，数据由业务方自行解析'
)
PARTITIONED BY (
    ds STRING COMMENT ' 日期分区 格式:[yyyyMMdd] '
);