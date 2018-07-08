package com.dtdream.DtRecommender.server.controller;

import com.dtdream.DtRecommender.common.util.JsonHelper;
import com.dtdream.DtRecommender.common.model.userbehavior.UserBehaviorList;
import com.dtdream.DtRecommender.common.restful.ResponseMsg;
import com.dtdream.DtRecommender.common.restful.StatusCode;
import com.dtdream.DtRecommender.server.service.UserBehaviorService;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;

import static com.dtdream.DtRecommender.server.controller.check.CheckParameter.checkBiz_code;


/**
 * Created by Administrator on 2016/8/18.
 */
@RestController
@RequestMapping("/api/v1/action")
public class UserBehaviorController {
    @Resource(name = "ActionService")
    private UserBehaviorService actionService;

    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public String postAction(@RequestBody UserBehaviorList actionlist,
                             @RequestParam(value = "biz_code", required = true) String biz_code,
                             @RequestParam(value = "token", required = false) String token) {

        String code;
        ResponseMsg rm = new ResponseMsg();

 
        if (checkBiz_code(biz_code)) {
            code = actionService.postAction(actionlist, biz_code, token);

            rm.setCode(code);

        } else {

            code = StatusCode.FAILURE;

            rm.setCode(code);
            rm.setErrMsg("Invalid parameters");
        }

        return JsonHelper.toJsonStr(rm);
    }
}
