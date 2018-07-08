package com.dtdream.DtRecommender.mover;

import com.dtdream.DtRecommender.mover.utils.KVRecord;

/**
 * Created by handou on 8/24/16.
 */
public interface Reader {
    void close();

    boolean next();
    void read(KVRecord record);
}
