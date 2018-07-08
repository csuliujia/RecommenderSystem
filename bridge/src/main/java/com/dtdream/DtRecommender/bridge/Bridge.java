package com.dtdream.DtRecommender.bridge;

import com.dtdream.DtRecommender.bridge.mqconsumer.MQConsumer;
import com.dtdream.DtRecommender.bridge.odps.Table;
import com.dtdream.DtRecommender.bridge.utils.configuration.ConfigParser;
import com.dtdream.DtRecommender.bridge.utils.configuration.bean.Project;
import com.dtdream.DtRecommender.bridge.utils.configuration.bean.Task;
import com.dtdream.DtRecommender.common.util.LogHelper;

import java.util.ArrayList;

public class Bridge {
    public static void main(String[] args) {

        /* 日志路径配置 */
        LogHelper.configLogPath("bridge");

        ArrayList<Table> tables = new ArrayList<Table>();
        for (Project p : ConfigParser.getInstance().getProjects()) {
            for (Task t : p.getTasks()) {
                Table newTable = new Table(p.getProjectName(),
                        t.getTableName(), t.getRecordNum(), t.getCommitCycle());
                tables.add(newTable);
            }
        }

        MQConsumer MQConsumer = new MQConsumer(tables);
        MQConsumer.subscribe();

    }
}