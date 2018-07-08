package com.dtdream.DtRecommender.bridge.odps;

import com.aliyun.odps.data.Record;
import com.aliyun.odps.data.RecordWriter;
import com.aliyun.odps.tunnel.TableTunnel;
import com.aliyun.odps.tunnel.TunnelException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.HashMap;


public class Writer {
    private TableTunnel.UploadSession uploadSession;
    private long lastUsed = System.currentTimeMillis();
    private RecordWriter recordWriter;
    private Record record;
    private long recordNum = 0;
    private final long maxRecordNum;
    private final long maxLiveTime;
    private final static Logger logger = LoggerFactory.getLogger(Writer.class);

    public Writer(TableTunnel.UploadSession session, long maxRecordNum, long maxLivetime) {
        this.uploadSession = session;
        try {
            this.recordWriter = session.openRecordWriter(0);
            this.record = session.newRecord();
        } catch (TunnelException e) {
            logger.error("", e);
        } catch (IOException e) {
            logger.error("", e);
        }
        this.maxRecordNum = maxRecordNum;
        this.maxLiveTime = maxLivetime;
    }

    /**
     * @param map the hashmap which stores data
     */
    public void write(HashMap<String, Object> map) throws Exception {
        for (String key : map.keySet()) {
            record.set(key, map.get(key));
        }

        try {
            recordWriter.write(record);
            recordNum++;
            lastUsed = System.currentTimeMillis();
        } catch (RuntimeException e) {
            logger.error("RecordWriter.write", e);
            throw new Exception();
        } catch (IOException e) {
            logger.error("RecordWriter.write", e);
            throw new Exception();
        }
    }

    /**
     *
     * @return true if the session should be committed
     */
    public boolean commitCheck() {

        // 写入的数据达到最大值，需要提交
        if (recordNum >= maxRecordNum) {
            return true;
        }

        // 存在时间超过配置值，需要提交
        if (System.currentTimeMillis() - lastUsed > maxLiveTime) {
            return true;
        }

        return false;
    }

    /**
     *
     */
    public void commit() {
        try {
            recordWriter.close();
        } catch (IOException e) {
            logger.error("Failed to commit record writer {}", e);
        } finally {
            try {
                logger.info("Successfully to commit message {}", recordNum);
                uploadSession.commit();
            } catch (TunnelException e) {
                logger.error("Exception of ODPS tunnel {}", e);
            } catch (IOException e) {
                logger.error("Exception of IO {}", e);
            }

        }
    }

    //测试用代码
    long getRecordNum() {
        return recordNum;
    }
}
