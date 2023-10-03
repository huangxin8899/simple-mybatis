package com.huangxin.builder;

import com.huangxin.session.Configuration;
import com.huangxin.session.MappedStatement;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.InputStream;
import java.util.List;

/**
 * mapper.xml的解析者
 * 也是MappedStatement的建造者
 *
 * @author huangxin 
 */
public class XMLMapperBuilder {

    private final Configuration configuration;

    public XMLMapperBuilder(Configuration configuration) {
        this.configuration = configuration;
    }

    /**
     * 解析mapper.xml拼装MappedStatement
     * @param inputStream mapper.xml的字节流
     */
    public void parse(InputStream inputStream) {
        try {
            Document document = new SAXReader().read(inputStream);
            Element rootElement = document.getRootElement();
            String namespace = rootElement.attributeValue("namespace");
            List<Element> list = rootElement.selectNodes("select|update|insert|delete");
            for (Element element : list) {
                String id = element.attributeValue("id");
                String resultType = element.attributeValue("resultType");
                String paramType = element.attributeValue("paramType");
                String sqlCommandType = element.getName();
                String sql = element.getTextTrim();

                MappedStatement mappedStatement = new MappedStatement(this.configuration);
                String statementId = namespace + "." + id;
                mappedStatement.setStatementId(statementId);
                mappedStatement.setSql(sql);
                mappedStatement.setSqlCommandType(sqlCommandType);
                if (paramType != null) {
                    Class<?> paramClass = Class.forName(paramType);
                    mappedStatement.setParamType(paramClass);
                }
                if (resultType != null) {
                    Class<?> resultClass = Class.forName(resultType);
                    mappedStatement.setResultType(resultClass);
                }
                this.configuration.getMappedStatementMap().put(statementId, mappedStatement);
            }
        } catch (DocumentException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
