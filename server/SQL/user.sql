

/* 用户表 */
CREATE TABLE user (
    user_id STRING COMMENT '唯一标识用户身份的ID',
    tags STRING COMMENT '标签-标签值kv串, 具体含义建用户属性表(user_meta.sql)'
) PARTITIONED BY (
    dt STRING COMMENT ' 日期分区 格式:[yyyyMMdd] '
);