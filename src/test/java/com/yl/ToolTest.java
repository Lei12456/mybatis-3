package com.yl;


import com.yl.mapper.AccountMapper;
import ognl.Ognl;
import ognl.OgnlException;
import org.apache.ibatis.builder.CustomObjectWrapperFactory;
import org.apache.ibatis.builder.StaticSqlSource;
import org.apache.ibatis.builder.xml.XMLConfigBuilder;
import org.apache.ibatis.domain.jpetstore.Account;
import org.apache.ibatis.logging.slf4j.Slf4jImpl;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ResultMap;
import org.apache.ibatis.mapping.ResultMapping;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.parsing.XNode;
import org.apache.ibatis.parsing.XPathParser;
import org.apache.ibatis.reflection.DefaultReflectorFactory;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.factory.DefaultObjectFactory;
import org.apache.ibatis.scripting.xmltags.XMLLanguageDriver;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.submitted.resolution.cachereffromxml.UserMapper;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class ToolTest {

  @Test
  public void testExecutor() throws FileNotFoundException {
    SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(
      new FileInputStream("D:\\learning\\source-code\\mybatis-3\\src\\test\\resources\\com.yl\\mybatis-config.xml"));
    SqlSession sqlSession = sqlSessionFactory.openSession();
  }


  @Test
  public void testProxy() throws FileNotFoundException {
    SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(
      new FileInputStream("D:\\learning\\source-code\\mybatis-3\\src\\test\\resources\\com.yl\\mybatis-config.xml"));
    SqlSession sqlSession = sqlSessionFactory.openSession();
    AccountMapper mapper = sqlSession.getMapper(AccountMapper.class);

  }
  @Test
  public void startup() throws FileNotFoundException {
    SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(
      new FileInputStream("D:\\learning\\source-code\\mybatis-3\\src\\test\\resources\\com.yl\\mybatis-config.xml"));
    SqlSession sqlSession = sqlSessionFactory.openSession();
  }

  @Test
  public void testConfiguration() {
    Configuration configuration = new Configuration();
    Properties properties = new Properties();
    //数据源信息
    properties.setProperty("url", "jdbc:mysql://127.0.0.1:3306/test");
    properties.setProperty("username", "root");
    properties.setProperty("password", "123456");
    properties.setProperty("driver", "org.mysql.Driver");

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
  public void testXMLConfigBuilder() {
    XMLConfigBuilder xmlConfigBuilder = new XMLConfigBuilder(new BufferedReader(new InputStreamReader(System.in)));
    Configuration configuration = xmlConfigBuilder.parse();
  }

  @Test
  public void testOGNL() throws OgnlException {
    Object value = Ognl.getValue("userName", "");

  }

  @Test
  public void testMetaObject() throws OgnlException {
    Account account = new Account();
    MetaObject metaObject = MetaObject.forObject(account,
      new DefaultObjectFactory(), new CustomObjectWrapperFactory(), new DefaultReflectorFactory());
    metaObject.setValue("", "");
  }

  @Test
  public void testMappedStatement() throws OgnlException {
    //构建一个配置类
    Configuration configuration = new Configuration();
    Properties properties = new Properties();
    //数据源信息
    properties.setProperty("url", "jdbc:mysql://127.0.0.1:3306/test");
    properties.setProperty("username", "root");
    properties.setProperty("password", "123456");
    properties.setProperty("driver", "org.mysql.Driver");

    configuration.setVariables(properties);
    //配置日志解析
    configuration.setLogImpl(Slf4jImpl.class);

    StaticSqlSource staticSqlSource = new StaticSqlSource(configuration, "select user_name from tb_account");
    MappedStatement.Builder builder = new MappedStatement.Builder(configuration, "select", staticSqlSource, SqlCommandType.SELECT);

    List<ResultMapping> resultMappings = new ArrayList<ResultMapping>();
    ResultMapping.Builder resultMappingBuilder = new ResultMapping.Builder(configuration, "username", "user_name", String.class);
    resultMappings.add(resultMappingBuilder.build());

    ResultMap.Builder resultMapBuilder = new ResultMap.Builder(configuration, "myResultMap", Account.class, resultMappings);
    ResultMap resultMap = resultMapBuilder.build();
    List<ResultMap> resultMapList = new ArrayList<>();
    resultMapList.add(resultMap);

    //封装结果集
    builder.resultMaps(resultMapList);

    MappedStatement mappedStatement = builder.build();

    System.out.println(mappedStatement);
    BoundSql boundSql = mappedStatement.getBoundSql(new Account());
  }

  @Test
  public void testXmlLanguageDriver() throws OgnlException, FileNotFoundException {
    //构建一个配置类
    Configuration configuration = new Configuration();
    Properties properties = new Properties();
    //数据源信息
    properties.setProperty("url", "jdbc:mysql://127.0.0.1:3306/test");
    properties.setProperty("username", "root");
    properties.setProperty("password", "123456");
    properties.setProperty("driver", "org.mysql.Driver");

    configuration.setVariables(properties);
    //配置日志解析
    configuration.setLogImpl(Slf4jImpl.class);

    XMLLanguageDriver xmlLanguageDriver = new XMLLanguageDriver();
    XPathParser xPathParser = new XPathParser(new FileInputStream(""));
    XNode xNode = xPathParser.evalNode("/select");
    SqlSource sqlSource = xmlLanguageDriver.createSqlSource(configuration, xNode, Account.class);
  }
}
