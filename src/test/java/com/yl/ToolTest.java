package com.yl;


import ognl.Ognl;
import ognl.OgnlException;
import org.apache.ibatis.builder.CustomObjectWrapperFactory;
import org.apache.ibatis.builder.xml.XMLConfigBuilder;
import org.apache.ibatis.demo.entity.User;
import org.apache.ibatis.logging.slf4j.Slf4jImpl;
import org.apache.ibatis.reflection.DefaultReflectorFactory;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.factory.DefaultObjectFactory;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.submitted.resolution.cachereffromxml.UserMapper;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Properties;

public class ToolTest {


  @Test
  public void testConfiguration(){
    Configuration configuration = new Configuration();
    Properties properties = new Properties();
    //数据源信息
    properties.setProperty("url","jdbc:mysql://127.0.0.1:3306/test");
    properties.setProperty("username","root");
    properties.setProperty("password","123456");
    properties.setProperty("driver","org.mysql.Driver");

    configuration.setVariables(properties);
    //配置日志解析
    configuration.setLogImpl(Slf4jImpl.class);

    //设置Environment
    //TransactionFactory transactionFactory = new TransactionFactory(properties);
    //Environment environment = new Environment(configuration, );
    //configuration.setEnvironment(environment);

    //设置Mapper
    configuration.addMapper(UserMapper.class);
  }

  @Test
  public void testXMLConfigBuilder(){
    XMLConfigBuilder xmlConfigBuilder = new XMLConfigBuilder(new BufferedReader(new InputStreamReader(System.in)));
    Configuration configuration = xmlConfigBuilder.parse();
  }

  @Test
  public void testOGNL() throws OgnlException {
    Object value = Ognl.getValue("userName", "");

  }

  @Test
  public void testMetaObject() throws OgnlException {
    User user = new User();
    MetaObject metaObject =  MetaObject.forObject(user,
      new DefaultObjectFactory(),new CustomObjectWrapperFactory(), new DefaultReflectorFactory());
    metaObject.setValue("","");
  }
}
