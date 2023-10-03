package com.huangxin.session;

import com.huangxin.mapping.BoundSql;
import com.huangxin.util.SqlParser;

/**
 * 该对象对应的是mapper.xml中的一条sql标签，如：select|insert|update|delete
 * 一个对象对应一个sql
 *
 * @author huangxin 
 */
public class MappedStatement {

    private final Configuration configuration;
    // statementId = namespace.id
    private String statementId;
    private String sql;
    private Class<?> paramType;
    private Class<?> resultType;
    private String sqlCommandType;

    public MappedStatement(Configuration configuration) {
        this.configuration = configuration;
    }

    public Configuration getConfiguration() {
        return configuration;
    }

    public String getStatementId() {
        return statementId;
    }

    public void setStatementId(String statementId) {
        this.statementId = statementId;
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

    public void setParamType(Class<?> paramType) {
        this.paramType = paramType;
    }

    public Class<?> getResultType() {
        return resultType;
    }

    public void setResultType(Class<?> resultType) {
        this.resultType = resultType;
    }

    public String getSqlCommandType() {
        return sqlCommandType;
    }

    public void setSqlCommandType(String sqlCommandType) {
        this.sqlCommandType = sqlCommandType;
    }

    public BoundSql getBoundSql(Object parameter) {
        BoundSql boundSql = new BoundSql(this.sql, paramType, parameter);
        SqlParser.parseSql(boundSql);
        return boundSql;
    }
}
