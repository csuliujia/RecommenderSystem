package com.dtdream.DtRecommender.server.controller;

import com.dtdream.DtRecommender.common.util.JsonHelper;
import com.dtdream.DtRecommender.common.model.recommender.RecommenderItems;
import com.dtdream.DtRecommender.common.restful.ResponseMsg;
import com.dtdream.DtRecommender.common.restful.StatusCode;
import com.dtdream.DtRecommender.server.service.RecommenderService;
import org.springframework.web.bind.annotation.*;

import static com.dtdream.DtRecommender.server.controller.check.CheckParameter.checkBiz_code;
import static com.dtdream.DtRecommender.server.controller.check.CheckParameter.checkScn_code;

import javax.annotation.Resource;


/**
 * Created by Administrator on 2016/8/18.
 */
@RestController
@RequestMapping("/api/v1/recommender")
public class RecommenderController {
    @Resource(name = "RecommenderService")
    private RecommenderService recommenderService;

    /* 获取推荐物品列表 */
    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public String getRecommend(@RequestParam(value = "biz_code", required = true) String biz_code,
                               @RequestParam(value = "scn_code", required = true) String scn_code,
                               @RequestParam(value = "id", required = false) String id,
                               @RequestParam(value = "token", required = false) String token) {

        ResponseMsg rs = new ResponseMsg();

        /* 检查URL参数  */
        if (checkBiz_code(biz_code, scn_code) && checkScn_code(scn_code, id)) {

            RecommenderItems rc = recommenderService.getRecommend(biz_code, scn_code, id);
            rs.setCode(StatusCode.SUCCESS);
            rs.setData(JsonHelper.toJsonStr(rc));

        } else {

            rs.setCode(StatusCode.FAILURE);
            rs.setErrMsg("Invalid parameters");

        }

        return JsonHelper.toJsonStr(rs);
    }
}