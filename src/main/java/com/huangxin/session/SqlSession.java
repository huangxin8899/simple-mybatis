package com.huangxin.session;

import java.io.Closeable;
import java.util.List;

/**
 * SqlSession
 *
 * @author huangxin
 */
public interface SqlSession extends Closeable {

    <T> List<T> selectList(String statement, Object parameter);

    <T> T selectOne(String statement, Object parameter);

    int insert(String statement, Object parameter);

    int update(String statement, Object parameter);

    int delete(String statement, Object parameter);

    /**
     * 获取（创建）mapper接口的代理对象
     * @param cls   mapper接口的类对象
     * @return      代理对象
     * @param <T>   接口类型
     */
    <T> T getMapper(Class<T> cls);

}
