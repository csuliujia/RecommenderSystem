package com.dtdream.DtRecommender.server.controller;

import com.dtdream.DtRecommender.common.model.meta.EntityAttributeType;
import com.dtdream.DtRecommender.common.model.user.UserMeta;
import com.dtdream.DtRecommender.common.model.user.UserMetaList;
import com.dtdream.DtRecommender.common.restful.ResponseMsg;
import com.dtdream.DtRecommender.common.restful.StatusCode;
import com.dtdream.DtRecommender.common.util.JsonHelper;
import com.dtdream.DtRecommender.server.entity.UserMetaEntity;
import com.dtdream.DtRecommender.server.service.UserMetaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.LinkedList;
import java.util.List;

import static com.dtdream.DtRecommender.server.controller.check.CheckParameter.checkBiz_code;

/**
 * Created by handou on 9/26/16.
 */
@RestController
@RequestMapping("/api/v1/data/")
public class UserMetaController {
    private final static Logger log = LoggerFactory.getLogger(UserMetaController.class);

    @Resource(name = "UserMetaSrv")
    private UserMetaService service;

    @RequestMapping(method = RequestMethod.POST, path = "userConfig")
    @ResponseBody
    public String postUserConfig(@RequestBody UserMetaList users,
                                 @RequestParam String biz_code,
                                 @RequestParam String token) {
        String code;
        ResponseMsg rm = new ResponseMsg();

 
        if(checkBiz_code(biz_code)) {
            code = service.postUserConfig(users, biz_code, token);

            rm.setCode(code);
        } else {

            rm.setCode(StatusCode.FAILURE);
            rm.setErrMsg("Invalid parameters");
        }

        return JsonHelper.toJsonStr(rm);
    }

    @RequestMapping(method = RequestMethod.GET, path = "userConfig")
    @ResponseBody
    public String getUserConfig(@RequestParam String biz_code, @RequestParam String token) {
        String code = StatusCode.SUCCESS;
        ResponseMsg rm = new ResponseMsg();

        
        if(checkBiz_code(biz_code)) {

            List<UserMetaEntity> ume =  service.getUserConfig(biz_code, token);

            List<UserMeta> lue  = new LinkedList<>();
            for (UserMetaEntity e : ume) {
                lue.add(new UserMeta(e.getName(), EntityAttributeType.valueOf(e.getType())));
            }

            rm.setCode(code);

            rm.setData(JsonHelper.toJsonStr(new UserMetaList(lue)));
        } else {

            rm.setCode(StatusCode.FAILURE);
            rm.setErrMsg("Invalid parameters");
        }

        return JsonHelper.toJsonStr(rm);
    }
}
