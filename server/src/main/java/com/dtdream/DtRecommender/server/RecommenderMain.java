package com.dtdream.DtRecommender.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * Created by Administrator on 2016/8/18.
 */
@SpringBootApplication
public class RecommenderMain {
    public static void main(String[] args) {

        SpringApplication.run(RecommenderMain.class, args);
    }

    @PostConstruct
    public void init() {
        System.out.println("\r\n   伐木累   \r\n");

        //========初始化开始============

    }

    @PreDestroy
    public void  dostory(){
        /* zk.close */

    }


}
