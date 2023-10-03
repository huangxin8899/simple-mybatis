package com.huangxin.mapping;

import java.util.ArrayList;
import java.util.List;

/**
 * BoundSql
 *
 * @author huangxin 
 */
public class BoundSql {

    private String sql;
    private final Class<?> paramType;
    private final Object parameter;
    private final List<Object> paramList = new ArrayList<>();

    public BoundSql(String sql, Class<?> paramType, Object parameter) {
        this.sql = sql;
        this.paramType = paramType;
        this.parameter = parameter;
    }

    public String getSql() {
        return sql;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }

    public Class<?> getParamType() {
        return paramType;
    }

    public Object getParameter() {
        return parameter;
    }

    public List<Object> getParamList() {
        return paramList;
    }
}
