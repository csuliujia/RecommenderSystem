package com.dtdream.DtRecommender.bridge.utils;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.HashMap;

public class Deserializer {
    private static final Logger logger = LoggerFactory.getLogger(Deserializer.class);
    public static HashMap<String, Object> toObject(byte[] bytes) {
        ByteArrayInputStream in = new ByteArrayInputStream(bytes);
        HashMap<String, Object> rowMap = null;
        try {
            ObjectInputStream oin = new ObjectInputStream(in);
            rowMap = (HashMap<String, Object>) oin.readObject();
            in.close();
            oin.close();
        } catch (IOException e) {
            logger.error("", e);
        } catch (ClassNotFoundException e) {
            logger.error("", e);
        }
        return rowMap;
    }
}
