--兴趣度大于0
--购买 1 收藏 0.8 分享 0.6 搜索 0.4 浏览 0.2
--计算兴趣度
insert overwrite table INTEREST_RATE
    select user_id ,
           item_id ,
           max(value) as value,--选择最高的分值
           max(bhv_datetime) as pt --选择最近发生行为的时间
    from
    (
      select user_id,
             item_id,
             decode(bhv_type,
                    "VIEW",0.2,
                    "SEARCH_CLICK",0.4,
                    "SHARE",0.6,
                    "COLLECT",0.8,
                    "CONSUME",1.0) as value,
             bhv_datetime
      from ods_action_clean
    )t
    group by user_id,item_id
;
