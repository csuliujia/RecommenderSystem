package com.dtdream.DtRecommender.cfgMgr.impl.policy;

import com.dtdream.DtRecommender.cfgMgr.Policy;
import com.dtdream.DtRecommender.cfgMgr.impl.policy.node.*;
import com.dtdream.DtRecommender.common.model.recommender.ItemModel;
import com.dtdream.DtRecommender.common.model.recommender.RecommenderItems;

import java.io.Serializable;
import java.security.InvalidParameterException;
import java.util.*;


class PolicyImpl implements Policy, Serializable {
    private transient final int startNodeId = 0;
    private transient final int endNodeId = 1;
    transient boolean isModified = false;//指示topoghList与nodeMap是否一致（包括节点，拓扑顺序）
    private transient int availNodeId = 2;
    private transient HashMap<Integer, AbstractNode> nodeMap;//只由控制台服务器使用，API Server不需要
    private transient String name;  //策略名，与znode名一致。全局唯一。

    private LinkedList<AbstractNode> topologyList;//按照拓扑顺序排列的节点列表。
    private HashMap<Integer, LinkedList<Integer>> outList;//出边表
    private HashMap<Integer, LinkedList<Integer>> inList;//入边表

    PolicyImpl(String name) {
        this.name = name;
        topologyList = new LinkedList<AbstractNode>();
        outList = new HashMap<Integer, LinkedList<Integer>>();
        inList = new HashMap<Integer, LinkedList<Integer>>();
    }

    public int getStartNodeId() {
        return startNodeId;
    }

    public int getEndNodeId() {
        return endNodeId;
    }

    public int createNode(NodeType type, Map<String, String> params) {
        isModified = false;
        if (type == NodeType.RecStart) return startNodeId;
        if (type == NodeType.RecEnd) return endNodeId;
        AbstractNode node = null;
        int newId = availNodeId++;
        switch (type) {
            case Distinct:
                node = new Distinct(newId, params);
                break;
            case GetItemDefRecList:
                node = new GetItemDefRecList(newId, params);
                break;
            case GetUserDefRecList:
                node = new GetUserDefRecList(newId, params);
                break;
            case GetItemRecList:
                node = new GetItemRecList(newId, params);
                break;
            case GetUserRecList:
                node = new GetUserRecList(newId, params);
                break;
            case Merge:
                node = new Merge(newId, params);
                break;
            case Sort:
                node = new Sort(newId, params);
                break;
            case TopN:
                node = new TopN(newId, params);
                break;
        }
        nodeMap.put(newId, node);
        return newId;
    }

    /**
     * 添加一条由id1指向id2的边
     * @param id1 边起点
     * @param id2 边终点
     * @throws InvalidParameterException 如果id1或id2不存在
     */
    public void link(int id1, int id2) throws InvalidParameterException {
        isModified = true;
        if (!nodeMap.containsKey(id1) || !nodeMap.containsKey(id2))
            throw new InvalidParameterException();

        LinkedList<Integer> outEdges = outList.get(id1);
        if (outEdges == null) {
            outEdges = new LinkedList<Integer>();
            outList.put(id1, outEdges);
        }
        outEdges.add(id2);

        LinkedList<Integer> inEdges = inList.get(id2);
        if (inEdges == null) {
            inEdges = new LinkedList<Integer>();
            inList.put(id2, inEdges);
        }
        inEdges.add(id2);
    }

    public void reset() {
        isModified = true;
        nodeMap = new HashMap<Integer, AbstractNode>();
        outList = new LinkedHashMap<Integer, LinkedList<Integer>>();
        inList = new LinkedHashMap<Integer, LinkedList<Integer>>();
        //topologistList在保存或运行时再计算。
    }

    public RecommenderItems run(String bizCode) {
        HashMap<Integer, List<ItemModel>> rs = new HashMap<Integer, List<ItemModel>>(availNodeId);
        //运行每一个节点
        for (AbstractNode n : topologyList) {
            //构造当前节点的输入数据
            ArrayList<List<ItemModel>> inputRecList = null;
            LinkedList<Integer> inputNumbers = inList.get(n.getId());
            if (inputNumbers != null) {
                inputRecList = new ArrayList<List<ItemModel>>(inputNumbers.size());
                for (Integer id : inputNumbers) {
                    inputRecList.add(rs.get(id));//把需要的输入推荐列表按照inList中的顺序放到input中。
                }
            }
            List<ItemModel> midRS = n.run(bizCode, null, inputRecList);
            rs.put(n.getId(), midRS);//把当前节点的运行结果放到rs中，供后面的节点使用。
        }
        return new RecommenderItems(getName(), rs.get(endNodeId));
    }

    public RecommenderItems run(String bizCode, String objId) {
        HashMap<Integer, List<ItemModel>> rs = new HashMap<Integer, List<ItemModel>>(availNodeId);
        //运行每一个节点
        for (AbstractNode n : topologyList) {
            //构造当前节点的输入数据
            ArrayList<List<ItemModel>> inputRecList = null;
            LinkedList<Integer> inputNumbers = inList.get(n.getId());
            if (inputNumbers != null) {
                inputRecList = new ArrayList<List<ItemModel>>(inputNumbers.size());
                for (Integer id : inputNumbers) {
                    inputRecList.add(rs.get(id));//把需要的输入推荐列表按照inList中的顺序放到input中。
                }
            }
            List<ItemModel> midRS = n.run(bizCode, objId, inputRecList);
            rs.put(n.getId(), midRS);//把当前节点的运行结果放到rs中，供后面的节点使用。
        }
        return new RecommenderItems(getName(), rs.get(endNodeId));
    }


    /**
     * 计算拓扑顺序并更新topologyList
     */
    void save() {
        topologyList = new LinkedList<AbstractNode>();
        int[] indegreeList = new int[availNodeId];//入度表
        for (int i = 0; i < availNodeId; i++)
            indegreeList[i] = inList.get(i).size();
        LinkedList<Integer> queue = new LinkedList<Integer>();
        queue.add(0);
        while (true) {
            int node = queue.poll();
            topologyList.addLast(nodeMap.get(node));
            if (outList.get(node) == null) break;
            for (int endNodeId : outList.get(node)) {
                indegreeList[endNodeId]--;
                if (indegreeList[endNodeId] == 0) topologyList.addLast(nodeMap.get(endNodeId));
            }
        }
        isModified = false;
    }

    public String getName() {
        return name;
    }
}
