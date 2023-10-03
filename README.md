# Simple-MyBatis
这是一个我手写的简易版本的 MyBatis，通过阅读 MyBatis 源码而实现的。

## 设计思路
这个项目的核心思路是对 JDBC 进行封装。下面是项目的主要设计思路：

1. **核心配置类**
   - `Configuration`：全局配置类，对应存储在 `config.xml` 配置文件中的内容。
   - `MappedStatement`：映射配置类，对应存储在 `mapper.xml` 配置文件中的内容。

2. **加载配置文件**
   - 创建了一个 `Resources` 工具类，用于加载配置文件，将文件加载成字节流并加载到内存中。

3. **解析配置文件，创建并填充核心配置类对象**
   - 定义了 `SqlSessionFactoryBuilder` 类和 `build()` 方法，用于解析 XML，创建并封装 `Configuration` 和 `MappedStatement`。
   - 创建 `SqlSessionFactory`。

4. **定义`SQLSessionFactory`和`SQLSession`接口**
   - 创建 `SqlSessionFactory` 的实现类 `DefaultSqlSessionFactory`，负责创建 `SqlSession` 对象。
   - 创建 `SqlSession` 的实现类 `DefaultSqlSession`，实现 CRUD 方法和 `getMapper()` 方法，它们委托给 `Executor` 来执行 SQL。

5. **`Executor` 接口和实现类 `SimpleExecutor`**
   - `SimpleExecutor` 是实际执行 SQL 的类，它调用 JDBC API 来执行查询和修改操作。
