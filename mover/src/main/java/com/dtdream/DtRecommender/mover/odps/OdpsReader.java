package com.dtdream.DtRecommender.mover.odps;

import com.aliyun.odps.Odps;
import com.aliyun.odps.PartitionSpec;
import com.aliyun.odps.account.Account;
import com.aliyun.odps.account.AliyunAccount;
import com.aliyun.odps.data.Record;
import com.aliyun.odps.data.RecordReader;
import com.aliyun.odps.tunnel.TableTunnel;
import com.aliyun.odps.tunnel.TunnelException;
import com.dtdream.DtRecommender.common.util.ConfParser;
import com.dtdream.DtRecommender.mover.Reader;
import com.dtdream.DtRecommender.mover.utils.KVRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by handou on 8/24/16.
 */
public class OdpsReader implements Reader {
    private long count;
    private long curr;

    private String tableName;       /* trace_id  表名，也是推荐算法名称  */
    private RecordReader odpsReader;
    private final static Logger log = LoggerFactory.getLogger(OdpsReader.class);

    public OdpsReader(String project, String table, String p_value) {
        ConfParser paser = new ConfParser("mover", "odps.conf");

        Account account = new AliyunAccount(paser.getString("accessId"), paser.getString("accessKey"));
        Odps odps = new Odps(account);

        odps.setEndpoint(paser.getString("odpsServerURI"));         /* 设置ODPS服务的地址 */
        odps.setDefaultProject(project);

        TableTunnel tunnel = new TableTunnel(odps);
        tunnel.setEndpoint(paser.getString("tunnelServerURI"));    /* 设置TunnelServer地址 */

        try {
            tableName = table;

            /* 分区列名称固定为“dt”，分区值形式为“20160829” */
            PartitionSpec partitionSpec = new PartitionSpec("dt" + "=" + p_value);
            TableTunnel.DownloadSession downloadSession = tunnel.createDownloadSession(project, tableName, partitionSpec);

            log.debug("Session Status is : {}", downloadSession.getStatus().toString());

            curr = 0;
            count = downloadSession.getRecordCount();

            log.debug("read count: " + count);
            odpsReader = downloadSession.openRecordReader(0, count);
        } catch (TunnelException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void close() {
        try {
            odpsReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean next() {
        return curr < count;
    }

    public void read(KVRecord record) {
        curr++;

        try {
            Record aliRecord = odpsReader.read();
            convert(aliRecord, record);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*  单条记录的转换 */
    private void convert(Record r, KVRecord kv) {
        Map<String, String> hashvalue = new HashMap<>();

        /* key为odps的"key"列数据，存放user_id；value为"value"列的数据，格式也一致,存放item list */
        hashvalue.put(r.getString("key"), r.getString("value"));

        /* 表名即为redis中外层的key */
        kv.setKey(this.tableName);
        kv.setValue(hashvalue);
    }
}