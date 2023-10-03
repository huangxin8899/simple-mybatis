package com.huangxin.session;

/**
 * SqlSessionFactory
 *
 * @author huangxin
 */
public interface SqlSessionFactory {

    /**
     * 创建SqlSession对象
     */
    SqlSession openSession();
}
