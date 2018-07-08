package com.dtdream.geely.tuijian;


import com.aliyun.odps.graph.ComputeContext;
import com.aliyun.odps.graph.Edge;
import com.aliyun.odps.graph.Vertex;
import com.aliyun.odps.graph.WorkerContext;
import com.aliyun.odps.io.BooleanWritable;
import com.aliyun.odps.io.DoubleWritable;
import com.aliyun.odps.io.Text;

import java.io.IOException;

//节点，包括人和电影
public class CFVertex extends
        Vertex<Text, BooleanWritable, CFEdgeValue, CFMessage> {
    public CFVertex() {
    }

    @Override
    public void compute(ComputeContext<Text, BooleanWritable, CFEdgeValue, CFMessage> computeContext, Iterable<CFMessage> iterable) throws IOException {
        BooleanWritable bool = new BooleanWritable(!getValue().get());//如果是物品节点，输出的是人与人的join,因此要取反；人节点亦然。
        DoubleWritable product = new DoubleWritable();
        for (Edge<Text, CFEdgeValue> e : this.getEdges())
            for (Edge<Text, CFEdgeValue> f : this.getEdges()) {
                if (!e.getDestVertexId().equals(f.getDestVertexId()))//排除相同的顶点
                {
                    //输出表schema:flag, id_1, length_1, id_2, length_2, interest_rate_product
                    product.set(e.getValue().getInterest_rate().get() * f.getValue().getInterest_rate().get());//计算兴趣度乘积
                    computeContext.write(bool,
                            e.getDestVertexId(),
                            e.getValue().getLength(),
                            f.getDestVertexId(),
                            f.getValue().getLength(),
                            product);
                }
            }
        voteToHalt();//不需要处理消息，直接停机
    }


    public CFVertex(boolean isPerson) {
        this.setValue(new BooleanWritable(isPerson));
    }

    public static CFVertex createPersonVertex() {
        return new CFVertex(true);
    }

    public static CFVertex createMovieVertex() {
        return new CFVertex(false);
    }

    @Override
    public void cleanup(WorkerContext<Text, BooleanWritable, CFEdgeValue, CFMessage> context) throws IOException {

    }
}
