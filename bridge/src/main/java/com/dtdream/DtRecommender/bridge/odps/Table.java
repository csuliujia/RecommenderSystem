package com.dtdream.DtRecommender.bridge.odps;

import com.aliyun.odps.Odps;
import com.aliyun.odps.OdpsException;
import com.aliyun.odps.PartitionSpec;
import com.aliyun.odps.account.Account;
import com.aliyun.odps.account.AliyunAccount;
import com.aliyun.odps.tunnel.TableTunnel;
import com.aliyun.odps.tunnel.TunnelException;
import com.dtdream.DtRecommender.common.util.ConfParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

public class Table {
    private final static Logger logger = LoggerFactory.getLogger(Table.class);

    private final String projectName;
    private final String tableName;
    private final long maxRecordNum;//max record numbers each session.
    private final long commitCycle;
    private TableTunnel tableTunnel;
    private com.aliyun.odps.Table aliyun_Table;
    /**
     * Free Writer
     */
    private LinkedList<Writer> writers;

    private CommitTask commitTask;

    private long totalReceiveNum = 0;
    private long totalCommitNum = 0;

    public Table(String projectName, String tableName, long maxRecordNum, long commitCycle) {
        this.projectName = projectName;
        this.tableName=tableName;
        this.maxRecordNum = maxRecordNum;
        this.commitCycle = commitCycle;

        ConfParser paser = new ConfParser("bridge", "odps.properties");
        Account account = new AliyunAccount(paser.getString("accessId"), paser.getString("accessKey"));
        Odps odps = new Odps(account);
        odps.setEndpoint(paser.getString("endPoint"));
        odps.setDefaultProject(paser.getString("project"));

        this.tableTunnel = new TableTunnel(odps);
        aliyun_Table = odps.tables().get(projectName, tableName);

        this.writers = new LinkedList<Writer>();
        // 启动提交线程
        commitTask = new CommitTask();
        commitTask.start();
    }


    private PartitionSpec getPartitonSpec(){
        //以日期作为分区
        Date date=new Date();
        SimpleDateFormat ft=new SimpleDateFormat("yyyyMMdd");
        return new PartitionSpec("ds='"+ft.format(date));
    }

    private Writer createTableWriter() {
        TableTunnel.UploadSession uploadSession = null;
        try {
            aliyun_Table.createPartition(getPartitonSpec(), true);
            uploadSession = this.tableTunnel.createUploadSession(projectName, tableName, getPartitonSpec());
        } catch (TunnelException e) {
            logger.error("createTableWriterError", e);
        } catch (OdpsException e) {
            logger.error("createPartitionError", e);
        }

        return new Writer(uploadSession, maxRecordNum, commitCycle);
    }

    synchronized public Writer getFreeWriter() {
        //测试用代码--------------
        totalReceiveNum++;
        logger.debug("received message {}", totalReceiveNum);
        //-----------------------

        if (writers.size() != 0) {
            /* 取队列第一个writer，然后从队列摘除 */
            return writers.removeFirst();
        } else {
            return createTableWriter();
        }
    }

    /**
     * @param writer writer to recycle
     * recycle Writer
     */
    synchronized public void recycleWriter(Writer writer) {
        if (writer.commitCheck()) {
            commitTask.commit(writer);
        } else {
            writers.addFirst(writer);
        }
    }

    synchronized private void commitOld() {
        Iterator<Writer> iterator = writers.iterator();
        while (iterator.hasNext()) {
            Writer tw = iterator.next();
            if (tw.commitCheck()) {
                iterator.remove();
                commitTask.commit(tw);
            }
        }
    }

    private class CommitTask extends Thread {
        private Table table=Table.this;
        private LinkedBlockingQueue<Writer> queue=new LinkedBlockingQueue<Writer>();

        public void commit(Writer writer) {
            queue.offer(writer);
        }

        @Override
        public void run() {
            while (true) {
                try {
                    Writer writer = queue.poll(table.commitCycle, TimeUnit.MILLISECONDS);
                    if (null != writer) {
                        writer.commit();
                        //测试用
                        totalCommitNum += writer.getRecordNum();
                        logger.debug("commit num: " + totalCommitNum);
                    } else {
                        // 等待超时进入这个分支
                        table.commitOld();
                    }
                } catch (InterruptedException e) {
                    logger.error("CommitTask Run", e);
                }
            }
        }
    }

}
