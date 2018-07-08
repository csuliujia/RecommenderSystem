package com.dtdream.geely.tuijian;

import com.aliyun.odps.graph.GraphLoader;
import com.aliyun.odps.graph.MutationContext;
import com.aliyun.odps.io.BooleanWritable;
import com.aliyun.odps.io.LongWritable;
import com.aliyun.odps.io.Text;
import com.aliyun.odps.io.WritableRecord;

import java.io.IOException;

public class CFLoader extends
        GraphLoader<Text, BooleanWritable, CFEdgeValue, CFMessage> {

    @Override
    public void load(LongWritable longWritable, WritableRecord writableRecord, MutationContext<Text, BooleanWritable, CFEdgeValue, CFMessage> mutationContext) throws IOException {
        /*
         * 表结构：
         * user_id, user_length,item_id, item_length,interest_rate
         */
        //解析记录
        String user_id = writableRecord.get("user_id").toString();
        String item_id = writableRecord.get("item_id").toString();
        double user_length = Double.parseDouble(writableRecord.get("user_length").toString());
        double item_length = Double.parseDouble(writableRecord.get("item_length").toString());
        double interest_rate = Double.parseDouble(writableRecord.get("interest_rate").toString());
        //构建人节点
        CFVertex vertex = CFVertex.createPersonVertex();
        vertex.setId(new Text(user_id));
        vertex.addEdge(new Text(item_id), new CFEdgeValue(interest_rate, item_length));
        mutationContext.addVertexRequest(vertex);
        //构建物品节点
        CFVertex movieVertex = CFVertex.createMovieVertex();
        movieVertex.setId(new Text(item_id));
        movieVertex.addEdge(new Text(user_id), new CFEdgeValue(interest_rate, user_length));//同上
        mutationContext.addVertexRequest(movieVertex);
    }
}
