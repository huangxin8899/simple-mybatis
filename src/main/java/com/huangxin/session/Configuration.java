package com.huangxin.session;

import com.huangxin.executor.Executor;
import com.huangxin.executor.SimpleExecutor;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * 全局配置类，对应config.xml
 *
 * @author huangxin
 */
public class Configuration {

    private DataSource dataSource;

    private final Executor defaultExecutor = new SimpleExecutor(this);

    // K:V -> statementId:MappedStatement
    private final Map<String, MappedStatement> mappedStatementMap = new HashMap<>();

    private final Map<Class<?>, Object> proxyMapper = new HashMap<>();

    public DataSource getDataSource() {
        return dataSource;
    }

    public Executor getDefaultExecutor() {
        return defaultExecutor;
    }

    public Map<String, MappedStatement> getMappedStatementMap() {
        return mappedStatementMap;
    }

    public Map<Class<?>, Object> getProxyMapper() {
        return proxyMapper;
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }
}
