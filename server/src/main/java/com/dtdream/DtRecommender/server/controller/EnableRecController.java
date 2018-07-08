package com.dtdream.DtRecommender.server.controller;

import com.dtdream.DtRecommender.common.model.can_rec_item.RecItem;
import com.dtdream.DtRecommender.common.model.can_rec_item.RecItemList;
import com.dtdream.DtRecommender.common.model.item.ItemIdList;
import com.dtdream.DtRecommender.common.restful.OperationMethod;
import com.dtdream.DtRecommender.common.restful.ResponseMsg;
import com.dtdream.DtRecommender.common.restful.StatusCode;
import com.dtdream.DtRecommender.common.util.JsonHelper;
import com.dtdream.DtRecommender.server.entity.RecItemEntity;
import com.dtdream.DtRecommender.server.service.EnableRecService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

import java.util.LinkedList;
import java.util.List;

import static com.dtdream.DtRecommender.server.controller.check.CheckParameter.checkBiz_code;

/**
 * Created by handou on 9/29/16.
 */
@RestController
@RequestMapping("/api/v1/data/")
public class EnableRecController {
    private final static Logger log = LoggerFactory.getLogger(EnableRecController.class);

    @Resource(name = "EnableRecService")
    private EnableRecService rec_itemService;

    @RequestMapping(method = RequestMethod.POST, path = "rec_item")
    public String updateRecItem(@RequestBody String data,
                                @RequestParam OperationMethod method,
                                @RequestParam String biz_code,
                                @RequestParam String token) {
        String code = StatusCode.SUCCESS;
        ResponseMsg rm = new ResponseMsg();

        if ((checkBiz_code(biz_code))) {

            switch (method) {
                case NEW:
                    code = rec_itemService.postRecItems(data);
                    break;
                case DELETE:
                    rec_itemService.deleteRecItems(data);
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

    @RequestMapping(method = RequestMethod.PUT, path = "rec_item")
    @ResponseBody
    public String getRecItem(@RequestBody ItemIdList ids,
                             @RequestParam String biz_code,
                             @RequestParam String token) {
        ResponseMsg rm = new ResponseMsg();

        if (checkBiz_code(biz_code)) {

            List<RecItemEntity> el = rec_itemService.getRecItems(ids);
            List<RecItem> rl = new LinkedList();

            for (RecItemEntity e : el) {
                rl.add(new RecItem(e.getItemId(), e.getItemInfo()));
            }

            rm.setData(JsonHelper.toJsonStr(new RecItemList(rl)));
            rm.setCode(StatusCode.SUCCESS);
        } else {

            rm.setCode(StatusCode.FAILURE);
            rm.setErrMsg("Invalid parameters");
        }

        return JsonHelper.toJsonStr(rm);
    }

}
