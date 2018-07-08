package com.dtdream.DtRecommender.cfgMgr.impl.policy;

import com.dtdream.DtRecommender.cfgMgr.Policy;
import com.dtdream.DtRecommender.cfgMgr.PolicyCfg;
import com.dtdream.DtRecommender.cfgMgr.utils.BytesHelper;
import com.dtdream.DtRecommender.common.model.recommender.RecommenderItems;
import org.apache.zookeeper.*;

import javax.validation.constraints.NotNull;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.HashMap;
import java.util.List;

/**
 * 构造时
 * 读操作：从本地读
 * 写操作：先修改本地，再同步到Zookeeper
 */
public class PolicyCfgImpl implements PolicyCfg {
    private final ZooKeeper zk;
    private HashMap<String, PolicyImpl> policyMap;
    private static final String prePath = "/policy";

    public PolicyCfgImpl(ZooKeeper zk) {
        this.zk = zk;
        policyMap = new HashMap<String, PolicyImpl>();
        pullPolicyList();
    }

    private void pullPolicyList() {
        try {
            List<String> remoteList = zk.getChildren(prePath, new Watcher() {
                public void process(WatchedEvent event) {
                    switch (event.getType()) {
                        case NodeChildrenChanged: {
                            pullPolicyList();//如果策略列表发生变化，则重新执行此函数。
                            break;
                        }
                    }
                }
            });
            //对比Zookeeper列表与policyMap，多则增
            for (String remote : remoteList) {
                if (!policyMap.containsKey(remote))
                    pullPolicy(remote);
            }
            //少则删
            for (String local : policyMap.keySet()) {
                if (!remoteList.contains(local))
                    policyMap.remove(local);
            }
        } catch (KeeperException e) {
            e.printStackTrace();
            //这种异常不应该出现
        } catch (InterruptedException e) {
            e.printStackTrace();
            //TODO 需要一直执行至成功为止??
            pullPolicyList();
        }
    }

    /**
     * 从ZooKeeper上获取Policy数据，并且更新本地的policyMap。
     */
    private void pullPolicy(String name) {
        final String pname = name;
        try {
            byte[] data = zk.getData(getPolicyPath(name), new Watcher() {
                public void process(WatchedEvent event) {
                    switch (event.getType()) {
                        case NodeDataChanged: {
                            pullPolicy(pname);//如果数据发生改变，则重新拉取数据。
                            break;
                        }
                    }
                }
            }, null);
            if (data != null) {
                ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(data));
                PolicyImpl p = (PolicyImpl) ois.readObject();
                policyMap.put(name, p);
            }

        } catch (KeeperException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public Policy createPolicy(String name, String info) {
        PolicyImpl policy = new PolicyImpl(name);
        byte[] data = BytesHelper.toBytes(policy);
        policyMap.put(name, policy);
        try {
            zk.create(getPolicyPath(name), data, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
            return policy;
        } catch (KeeperException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void deletePolicy(String name) {
        policyMap.remove(name);
        try {
            zk.delete(getPolicyPath(name), -1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (KeeperException e) {
            e.printStackTrace();
        }
    }

    public void publishPolicy(Policy policy) {
        PolicyImpl p = (PolicyImpl) policy;
        if (p.isModified) p.save();
        try {
            zk.setData(getPolicyPath(p.getName()), BytesHelper.toBytes(p), -1);
        } catch (KeeperException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public Policy getPolicy(String name) {
        return new PolicyImpl(name);
    }

    public RecommenderItems runPolicy(String policyName, String bizCode) {
        return policyMap.get(policyName).run(bizCode);
    }

    public RecommenderItems runPolicy(String policyName, String bizCode, String objId) {
        return policyMap.get(policyName).run(bizCode, objId);
    }

    private String getPolicyPath(String name) {
        return prePath + "/" + name;
    }
}
