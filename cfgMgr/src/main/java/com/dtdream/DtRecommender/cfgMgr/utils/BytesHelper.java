package com.dtdream.DtRecommender.cfgMgr.utils;

import com.google.gson.internal.Primitives;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Created by handou on 10/10/16.
 */
public class BytesHelper {
    /* 对象转化成字节流 */
    public static byte[] toBytes(Object obj) {
        byte[] bytes = null;

        try {
            ByteArrayOutputStream bo = new ByteArrayOutputStream();
            ObjectOutputStream oo = new ObjectOutputStream(bo);
            oo.writeObject(obj);

            bytes = bo.toByteArray();

            bo.close();
            oo.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return bytes;
    }

    public static <T> T fromBytes(byte[] bytes, Class<T> mapClazz) {
        Object obj = null;
        try {
            ByteArrayInputStream bi = new ByteArrayInputStream(bytes);
            ObjectInputStream oi = new ObjectInputStream(bi);

            obj = oi.readObject();
            bi.close();
            oi.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return Primitives.wrap(mapClazz).cast(obj);
    }
}
