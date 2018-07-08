package com.dtdream.DtRecommender.server.controller;

import com.dtdream.DtRecommender.common.model.user.User;
import com.dtdream.DtRecommender.common.model.user.UserIdList;
import com.dtdream.DtRecommender.common.model.user.UserList;
import com.dtdream.DtRecommender.common.restful.OperationMethod;
import com.dtdream.DtRecommender.common.restful.ResponseMsg;
import com.dtdream.DtRecommender.common.restful.StatusCode;
import com.dtdream.DtRecommender.common.util.JsonHelper;
import com.dtdream.DtRecommender.server.entity.UserEntity;
import com.dtdream.DtRecommender.server.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.LinkedList;
import java.util.List;

import static com.dtdream.DtRecommender.server.controller.check.CheckParameter.checkBiz_code;

/**
 * Created by Administrator on 2016/8/18.
 */
@RestController
@RequestMapping("/api/v1/data/")
public class UserController {
    private final static Logger log = LoggerFactory.getLogger(UserController.class);

    @Resource(name = "UserService")
    private UserService userService;

    @RequestMapping(method = RequestMethod.POST, path = "user")
    @ResponseBody
    public String updateUser(@RequestBody String data,
                             @RequestParam OperationMethod method,
                             @RequestParam String biz_code,
                             @RequestParam String token) {
        String code = StatusCode.SUCCESS;
        ResponseMsg rm = new ResponseMsg();


        if(checkBiz_code(biz_code)) {

            switch (method) {
                case NEW:
                    code = userService.postUser(data);
                    break;
                case DELETE:
                    userService.deleteUsers(data);
                    break;
                default:
                    log.error("Unexpected method: {}", method.getLabel());
                    break;
            }

            rm.setCode(code);
        } else {

            rm.setCode(StatusCode.FAILURE);
            rm.setErrMsg("Invalid parameters");
        }

        return JsonHelper.toJsonStr(rm);
    }

    @RequestMapping(method = RequestMethod.PUT, path = "user")
    @ResponseBody
    public String getUser(@RequestBody UserIdList users,
                          @RequestParam String biz_code,
                          @RequestParam String token) {
        ResponseMsg rm = new ResponseMsg();

        if(checkBiz_code(biz_code)){

            List<UserEntity> el = userService.getUsers(users);
            List<User> ul = new LinkedList();

            for (UserEntity e: el) {
                ul.add(new User(e.getUser_id(), e.getTags()));
            }

            rm.setData(JsonHelper.toJsonStr(new UserList(ul)));

            rm.setCode(StatusCode.SUCCESS);

            System.out.print("========= Get result: " + JsonHelper.toJsonStr(rm));
        } else {

            rm.setCode(StatusCode.FAILURE);
            rm.setErrMsg("Invalid parameters");
        }

        return JsonHelper.toJsonStr(rm);
    }



}
