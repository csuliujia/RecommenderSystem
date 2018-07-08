package com.dtdream.DtRecommender.server.service.impl;

import com.dtdream.DtRecommender.common.model.userbehavior.UserBehaviorList;
import com.dtdream.DtRecommender.common.restful.StatusCode;
import com.dtdream.DtRecommender.server.dao.MessageQueueDao;
import com.dtdream.DtRecommender.server.service.UserBehaviorService;
import com.dtdream.DtRecommender.server.utils.ThreadPoolUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service("ActionService")
public class UserBehaviorImpl implements UserBehaviorService {
    @Resource(name = "MessageQueueDao")
    private MessageQueueDao messageQueueDao;

    @Override
    public String postAction(UserBehaviorList actionlist, final String biz_code, final String token) {

        // 在线程池汇总异步执行
        /* 默认上传用户行为数据 为成功，发送失败的情况，直接打印log信息 */
        ThreadPoolUtils.getInit().execute(() -> {

            messageQueueDao.handleMessage(biz_code, token, actionlist);
        });

        return StatusCode.SUCCESS;
    }
}
