package com.huangxin.session;

import com.huangxin.executor.Executor;

import java.io.IOException;
import java.lang.reflect.Proxy;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * DefaultSqlSession
 *
 * @author huangxin
 */
public class DefaultSqlSession implements SqlSession {

    private final Configuration configuration;
    private final Executor executor;

    public DefaultSqlSession(Configuration configuration, Executor executor) {
        this.configuration = configuration;
        this.executor = executor;
    }

    @Override
    public <T> List<T> selectList(String statement, Object parameter) {
        Map<String, MappedStatement> mappedStatementMap = this.configuration.getMappedStatementMap();
        MappedStatement mappedStatement = mappedStatementMap.get(statement);
        return this.executor.query(mappedStatement, parameter);
    }

    @Override
    public <T> T selectOne(String statement, Object parameter) {
        List<T> list = this.selectList(statement, parameter);
        if (list.size() == 1) {
            return list.get(0);
        } else if (list.size() > 1) {
            throw new RuntimeException("Expected one result (or null) to be returned by selectOne(), but found: " + list.size());
        } else {
            return null;
        }
    }

    @Override
    public int insert(String statement, Object parameter) {
        return update(statement, parameter);
    }

    @Override
    public int update(String statement, Object parameter) {
        Map<String, MappedStatement> mappedStatementMap = this.configuration.getMappedStatementMap();
        MappedStatement mappedStatement = mappedStatementMap.get(statement);
        return this.executor.update(mappedStatement, parameter);
    }

    @Override
    public int delete(String statement, Object parameter) {
        return update(statement, parameter);
    }

    @Override
    public <T> T getMapper(Class<T> cls) {
        Map<Class<?>, Object> proxyMapper = this.configuration.getProxyMapper();
        Object proxyInstance = proxyMapper.get(cls);
        if (cls.isInterface()) {
            if (proxyInstance == null) {
                proxyInstance =
                        Proxy.newProxyInstance(DefaultSqlSession.class.getClassLoader(), new Class[]{cls}, (proxy, method, args) -> {
                            String clsName = cls.getName();
                            String methodName = method.getName();
                            Class<?> returnType = method.getReturnType();
                            String statementId = clsName + "." + methodName;
                            MappedStatement mappedStatement = configuration.getMappedStatementMap().get(statementId);
                            String sqlCommandType = mappedStatement.getSqlCommandType();
                            switch (sqlCommandType) {
                                case "select":
                                    if (returnType.isArray() || Collection.class.isAssignableFrom(returnType)) {
                                        if (args != null) {
                                            return selectList(statementId, args[0]);
                                        }
                                        return selectList(statementId, null);
                                    }
                                    return selectOne(statementId, args[0]);
                                case "update":
                                    return update(statementId, args[0]);
                                case "insert":
                                    return insert(statementId, args[0]);
                                case "delete":
                                    return delete(statementId, args[0]);
                                default:
                                    return null;
                            }
                        });
                proxyMapper.put(cls, proxyInstance);
            }
        } else {
            throw new IllegalArgumentException("类型必须是接口类");
        }
        return (T) proxyInstance;
    }

    @Override
    public void close() throws IOException {
        executor.close();
    }
}
