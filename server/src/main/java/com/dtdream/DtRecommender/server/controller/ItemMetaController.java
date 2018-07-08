package com.dtdream.DtRecommender.server.controller;

import com.dtdream.DtRecommender.common.model.item.ItemMeta;
import com.dtdream.DtRecommender.common.model.item.ItemMetaList;
import com.dtdream.DtRecommender.common.restful.ResponseMsg;
import com.dtdream.DtRecommender.common.restful.StatusCode;
import com.dtdream.DtRecommender.common.util.JsonHelper;
import com.dtdream.DtRecommender.server.service.ItemMetaService;
import org.slf4j.Logger;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

import java.util.List;

import static com.dtdream.DtRecommender.server.controller.check.CheckParameter.checkBiz_code;
/**
 * Created by Administrator on 2016/9/27.
 */
@RestController
@RequestMapping("/api/v1/data/")
public class ItemMetaController {
    private final static Logger log = org.slf4j.LoggerFactory.getLogger(ItemMetaController.class);

    @Resource(name = "ItemMetaService")
    private ItemMetaService itemMetaService;

    @RequestMapping(method = RequestMethod.POST,path = "itemConfig")
    public String PostItemConf(@RequestBody ItemMetaList itemMetaList,
                               @RequestParam String biz_code,
                               @RequestParam String token) {
        String code;
        ResponseMsg rm = new ResponseMsg();

        if(checkBiz_code(biz_code)) {

           code = itemMetaService.postItemConf(itemMetaList, biz_code,token);
           rm.setCode(code);

        } else {

            rm.setCode(StatusCode.FAILURE);
            rm.setErrMsg("Invalid parameters");
        }

        return JsonHelper.toJsonStr(rm);
    }

    @RequestMapping(method = RequestMethod.GET,path = "itemConfig")
    @ResponseBody
    public String GetItemConf(@RequestParam String biz_code,
                               @RequestParam String token) {
        String code = StatusCode.SUCCESS;
        ResponseMsg rm = new ResponseMsg();

        if(checkBiz_code(biz_code)){

            List<ItemMeta> list = itemMetaService.getItemConf(biz_code, token);
            rm.setCode(code);
            rm.setData(JsonHelper.toJsonStr(new ItemMetaList(list)));

        } else {

            rm.setCode(StatusCode.FAILURE);
            rm.setErrMsg("Invalid parameters");
        }

        return JsonHelper.toJsonStr(rm);
    }


}
