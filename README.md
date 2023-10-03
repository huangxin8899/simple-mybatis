# simple-mybatis
了解Mybatis源码后手写的一个简易版本的Mybatis

## 设计思路
本质就是对JDBC进行封装
1. 定义两个核心配置类（JavaBean）
  `Configuration`：全局配置类：对应存储`config.xml`配置文件的内容
  `MappedStatement`：映射配置类：对应存储`mapper.xml`配置文件的内容
2. 加载配置文件
  创建Resources工具类：用来加载配置文件，即将文件加载成字节流到内存中
3. 解析配置文件，创建并填充核心配置类对象
  定义`SQLSessionFactoryBuilder`类和`build()`方法：
  a. 用于解析xml,创建并封装配置类`Configuration`和`MappedStatement`
  b. 创建`SQLSessionFactory`
4. 定义`SQLSessionFactory`和`SQLSession`接口
  创建`SQLSessionFactory`的实现类`DefaultSQLSessionFactory`实现`openSession()`方法：负责创建`SQLSession`对象；
  创建`SQLSession`的实现类`DefaultSQLSession`实现其CRUD方法和`getMapper()`方法：委托`Executor`执行sql
5. 定义`Executor`接口类和其实现类`SimpleExecutor`
   真正解析并执行sql的类，实际上就是调用JDBC的API
   查询操作都走`query()`方法，增删改都走`update()`方法
