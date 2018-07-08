package com.dtdream.DtRecommender.common.model.recommender;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Administrator on 2016/8/24.
 */
public class ItemModel {
    private String item_id;
    private double score;

    public ItemModel(String item_id, double score) {

        this.item_id = item_id;
        this.score = score;
    }

    public String getItem_id() {
        return item_id;
    }

    public void setItem_id(String item_id) {
        this.item_id = item_id;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public boolean equals(Object destination)
    {
        boolean retVal = false;
        if(destination != null && destination.getClass().equals(this.getClass()))
        {
            ItemModel bean = (ItemModel)destination;
            if(bean.getItem_id()==null && this.getItem_id()==null)
            {
                retVal = true;
            }
            else
            {
                if(bean.getItem_id()!=null && bean.getItem_id().equals(this.getItem_id()))
                {
                    retVal = true;
                }
            }
        }

        return retVal;
    }

    /* 将redis返回的List<String> 转换成 List<Item> */
    public static List<ItemModel> getItemList(List<String> value){
        List<ItemModel> l = new LinkedList<>();

        for (String e : value) {
            for (String i : e.split(";")) {

                String[] itemKV = i.split(":");
                ItemModel r = new ItemModel(itemKV[0], Double.parseDouble(itemKV[1]));

                l.add(r);
            }
        }

        return l;
    }
}

