package com.dtdream.DtRecommender.common.model.userbehavior;

import java.io.Serializable;

/**
 * Created by handou on 9/7/16.
 */
public enum PositionType implements Serializable {
    LL, /* ll：经纬度格式的位置信息 */
    GH, /* gh：geohash格式的位置信息 */
    POI /* poi：poi格式的位置信息 */
}
