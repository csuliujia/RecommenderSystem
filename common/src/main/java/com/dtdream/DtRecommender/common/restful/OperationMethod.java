package com.dtdream.DtRecommender.common.restful;

/**
 * Created by handou on 9/23/16.
 *
 * 资源对象操作类型
 *
 */
public enum OperationMethod {
    UPDATE("UPDATE"),  /* 更新一个已有资源 */
    NEW("NEW"),     /* 新增一个资源 */
    DELETE("DELETE");  /* 删除一个资源 */

    private String label;

    OperationMethod(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }
}
