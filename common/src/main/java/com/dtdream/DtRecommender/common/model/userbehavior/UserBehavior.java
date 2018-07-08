package com.dtdream.DtRecommender.common.model.userbehavior;


import java.io.Serializable;
import java.util.Date;

public class UserBehavior implements Serializable {
    private String user_id;
    private String item_id;
    private Bhv_Type bhv_type;
    private double bhv_amt;
    private double bhv_cnt;
    private Date bhv_datetime;
    private String content;
    private Media_Type media_type;
    private PositionType pos_type;
    private String position;
    private String env;
    private String trace_id;

    public UserBehavior() {
    }

    /* 这四个参数是必须携带的 */
    public UserBehavior(String user_id, String item_id, Bhv_Type bhv_type, Date bhv_datetime) {
        this.user_id = user_id;
        this.item_id = item_id;
        this.bhv_type = bhv_type;
        this.bhv_datetime = bhv_datetime;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getItem_id() {
        return item_id;
    }

    public void setItem_id(String item_id) {
        this.item_id = item_id;
    }

    public Bhv_Type getBhv_type() {
        return bhv_type;
    }

    public void setBhv_type(Bhv_Type bhv_type) {
        this.bhv_type = bhv_type;
    }

    public Date getBhv_datetime() {
        return bhv_datetime;
    }

    public void setBhv_datetime(Date bhv_datetime) {
        this.bhv_datetime = bhv_datetime;
    }

    public double getBhv_amt() {
        return bhv_amt;
    }

    public void setBhv_amt(double bhv_amt) {
        this.bhv_amt = bhv_amt;
    }

    public double getBhv_cnt() {
        return bhv_cnt;
    }

    public void setBhv_cnt(double bhv_cnt) {
        this.bhv_cnt = bhv_cnt;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Media_Type getMedia_type() {
        return media_type;
    }

    public void setMedia_type(Media_Type media_type) {
        this.media_type = media_type;
    }

    public PositionType getPos_type() {
        return pos_type;
    }

    public void setPos_type(PositionType pos_type) {
        this.pos_type = pos_type;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getEnv() {
        return env;
    }

    public void setEnv(String env) {
        this.env = env;
    }

    public String getTrace_id() {
        return trace_id;
    }

    public void setTrace_id(String trace_id) {
        this.trace_id = trace_id;
    }
}



