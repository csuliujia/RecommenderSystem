package com.dtdream.DtRecommender.sdk;

import com.dtdream.DtRecommender.sdk.mock.can_rec_item.DeleteRecItem;
import com.dtdream.DtRecommender.sdk.mock.can_rec_item.GetRecItem;
import com.dtdream.DtRecommender.sdk.mock.can_rec_item.PostRecItem;
import com.dtdream.DtRecommender.sdk.mock.item.*;
import com.dtdream.DtRecommender.sdk.mock.user.*;

import java.text.ParseException;


/**
 * Created by handou on 8/20/16.
 * <p>
 * 冒烟测试
 */
public class EntryMain {
    public static void main(String[] args) throws ParseException {

/*        testPost();

//        testPostFile();

        testGet();*/

        /* 1 post 接口*/
        PostUser.shoot();

        /* 2 get 接口 */
        //GetUser.shoot();


        /* 3 delete 接口 */
        //DeleteUser.shoot();


        //PostUserMeta.shoot();

        //GetUserMeta.shoot();

//          PostItem.shoot();

//          DeleteItem.shoot();

//        GetItem.shoot();

//        PostItemMeta.shoot();

//        GetItemMeta.shoot();

//          PostRecItem.shoot();

        DeleteRecItem.shoot();

//          GetRecItem.shoot();

    }

}
