package com.huangxin.executor;

import com.huangxin.session.MappedStatement;

import java.io.Closeable;
import java.sql.SQLException;
import java.util.List;

/**
 * 真正执行sql的类
 *
 * @author huangxin
 */
public interface Executor extends Closeable {

    /**
     * 解析sql，执行sql，其实就是对jdbc的封装
     */
    <E> List<E> query(MappedStatement ms, Object parameter);

    /**
     * insert|update|delete 走的都是该方法
     */
    int update(MappedStatement ms, Object parameter);
}
