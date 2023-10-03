package com.huangxin.builder;

import com.alibaba.druid.pool.DruidDataSource;
import com.huangxin.io.Resources;
import com.huangxin.session.Configuration;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.InputStream;
import java.util.List;
import java.util.Properties;

/**
 * config.xml的解析器
 * 也是configuration的创建者
 *
 * @author huangxin 
 */
public class XMLConfigBuilder {
    private final Configuration configuration;

    public XMLConfigBuilder() {
        this.configuration = new Configuration();
    }

    /**
     * 解析config.xml拼装configuration
     * @param inputStream config.xml的字节流
     */
    public Configuration parse(InputStream inputStream) {
        try {
            Document document = new SAXReader().read(inputStream);
            Element rootElement = document.getRootElement();
            Element dataSource = rootElement.element("dataSource");
            List<Element> proElement = dataSource.elements("property");
            Properties properties = new Properties();
            for (Element element : proElement) {
                String name = element.attributeValue("name");
                String value = element.attributeValue("value");
                properties.setProperty(name, value);
            }
            // 使用Druid连接池
            DruidDataSource druidDataSource = new DruidDataSource();
            druidDataSource.setDriverClassName(properties.getProperty("driver"));
            druidDataSource.setUrl(properties.getProperty("url"));
            druidDataSource.setUsername(properties.getProperty("username"));
            druidDataSource.setPassword(properties.getProperty("password"));
            this.configuration.setDataSource(druidDataSource);

            // 解析mapper.xml
            Element mappers = rootElement.element("mappers");
            List<Element> list = mappers.elements("mapper");
            for (Element element : list) {
                String resource = element.attributeValue("resource");
                InputStream resourceAsStream = Resources.getResourceAsStream(resource);
                XMLMapperBuilder mapperBuilder = new XMLMapperBuilder(this.configuration);
                mapperBuilder.parse(resourceAsStream);
            }
            return this.configuration;
        } catch (DocumentException e) {
            throw new RuntimeException(e);
        }
    }
}
