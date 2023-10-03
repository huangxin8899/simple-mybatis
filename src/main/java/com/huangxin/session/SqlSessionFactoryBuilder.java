package com.huangxin.session;

import com.huangxin.builder.XMLConfigBuilder;

import java.io.InputStream;

/**
 * SqlSessionFactoryBuilder
 *
 * @author huangxin 
 */
public class SqlSessionFactoryBuilder {

    public static SqlSessionFactory build(InputStream inputStream) {
        XMLConfigBuilder configBuilder = new XMLConfigBuilder();
        Configuration configuration = configBuilder.parse(inputStream);
        return new DefaultSqlSessionFactory(configuration);
    }
}
