package com.dtdream.DtRecommender.server.controller;

import com.dtdream.DtRecommender.common.model.item.ItemIdList;
import com.dtdream.DtRecommender.common.model.item.ItemList;
import com.dtdream.DtRecommender.common.restful.OperationMethod;
import com.dtdream.DtRecommender.common.restful.ResponseMsg;
import com.dtdream.DtRecommender.common.restful.StatusCode;
import com.dtdream.DtRecommender.common.util.JsonHelper;
import com.dtdream.DtRecommender.server.entity.ItemEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dtdream.DtRecommender.common.model.item.Item;
import com.dtdream.DtRecommender.server.service.ItemService;
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
public class ItemController {
    private final static Logger log = LoggerFactory.getLogger(ItemController.class);

    @Resource(name = "ItemService")
    private ItemService itemService;


    @RequestMapping(method = RequestMethod.POST, path = "item")
    public String updateItem(@RequestBody String data,
                             @RequestParam OperationMethod method,
                             @RequestParam String biz_code,
                             @RequestParam String token) {
        String code = StatusCode.SUCCESS;
        ResponseMsg rm = new ResponseMsg();

        if((checkBiz_code(biz_code))) {

            switch (method) {
                case NEW:
                    code = itemService.postItem(data);
                    break;
                case DELETE:
                    itemService.deleteItems(data);
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

    @RequestMapping(method = RequestMethod.PUT, path = "item")
    @ResponseBody
    public String getItem(@RequestBody ItemIdList ids,
                          @RequestParam String biz_code,
                          @RequestParam String token) {
        ResponseMsg rm = new ResponseMsg();

        if(checkBiz_code(biz_code)){

            List<ItemEntity> el = itemService.getItems(ids);
            List<Item> il = new LinkedList();

            for (ItemEntity e: el) {
                il.add(new Item(e.getItemId(), e.getCategory(),e.getKeywords(),
                    e.getDescription(),e.getBizinfo(),e.getDescription()));
            }

            rm.setData(JsonHelper.toJsonStr(new ItemList(il)));
            rm.setCode(StatusCode.SUCCESS);
        } else {

            rm.setCode(StatusCode.FAILURE);
            rm.setErrMsg("Invalid parameters");
        }

        return JsonHelper.toJsonStr(rm);

    }

}
