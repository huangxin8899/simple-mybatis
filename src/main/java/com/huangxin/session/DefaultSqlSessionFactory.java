package com.huangxin.session;

import com.huangxin.executor.Executor;

/**
 * DefaultSqlSessionFactory
 *
 * @author huangxin 
 */
public class DefaultSqlSessionFactory implements SqlSessionFactory {

    private final Configuration configuration;

    public DefaultSqlSessionFactory(Configuration configuration) {
        this.configuration = configuration;
    }

    @Override
    public SqlSession openSession() {
        Executor executor = this.configuration.getDefaultExecutor();
        return new DefaultSqlSession(this.configuration, executor);
    }
}
