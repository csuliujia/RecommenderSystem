package com.dtdream.geely.tuijian;

import com.aliyun.odps.graph.Edge;
import com.aliyun.odps.graph.LoadingVertexResolver;
import com.aliyun.odps.graph.Vertex;
import com.aliyun.odps.graph.VertexChanges;
import com.aliyun.odps.io.BooleanWritable;
import com.aliyun.odps.io.Text;

import java.io.IOException;


//每条记录会产生两个顶点，本类用于合并相同顶点
public class CFResolver extends
        LoadingVertexResolver<Text, BooleanWritable, CFEdgeValue, CFMessage> {

    @Override
    public Vertex<Text, BooleanWritable, CFEdgeValue, CFMessage> resolve(Text text, VertexChanges<Text, BooleanWritable, CFEdgeValue, CFMessage> vertexChanges) throws IOException {
        //统一节点
        CFVertex vertex;
        CFVertex temp = (CFVertex) vertexChanges.getAddedVertexList().get(0);
        vertex = new CFVertex();
        vertex.setId(temp.getId());
        vertex.setValue(temp.getValue());
        //合并出边
        for (Vertex<Text, BooleanWritable, CFEdgeValue, CFMessage> v : vertexChanges.getAddedVertexList()) {
            for (Edge<Text, CFEdgeValue> e : v.getEdges()) {
                vertex.addEdge(e.getDestVertexId(), e.getValue());
            }
        }
        return vertex;
    }
}