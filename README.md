# mybatis-plugin
根据真实使用场景对mybatis进行的扩展，实现分页、json存储、数组存储、blob存储等支持。

[![GitHub release](https://img.shields.io/github/v/release/weiecho/mybatis-plugin)](https://github.com/weiecho/mybatis-plugin/releases)
[![Maven Central Repo](https://img.shields.io/maven-central/v/cn.weiecho/mybatis-plugin)](https://mvnrepository.com/artifact/cn.weiecho/mybatis-plugin)
[![openjdk](https://img.shields.io/badge/jdk-v1.8%2B-red)](http://openjdk.java.net)
[![License](https://img.shields.io/github/license/weiecho/sentinel-client)](https://opensource.org/licenses/Apache-2.0)


### 扩展Mybatis数据类型

##### 1、ArrayTypeHandler 支持数组数据的存储和读取
bean定义格式
```java
private String[] tags;
```

mapper定义格式
```xml
<result property="tags" column="tags" typeHandler="ArrayTypeHandler"/>
```


##### 2、JsonTypeHandler 支持Json对象数据的存储和读取
bean定义格式
```java
/** 图片 如：[{imageUrl:"/1.jpg"}] **/
private Object imagesUrl;
```

mapper定义格式
```xml
<result property="imagesUrl" column="imagesUrl" typeHandler="JsonTypeHandler"/>
```

##### 3、BlobTypeHandler 支持Blob数据的存储和读取
bean定义格式
```java
/** 文章内容 **/
private String content;
```

mapper定义格式
```xml
<result property="content" column="content" typeHandler="BlobTypeHandler"/>
```

##### 4、ListTypeHandler 支持List<String>数据的存储和读取(可以视为Array的升级版)
bean定义格式
```java
private List<String> tags;
```

mapper定义格式
```xml
<result property="tags" column="tags" typeHandler="ListTypeHandler"/>
```



### 扩展Mybatis自动执行分页SQL处理
##### 1、MysqlPageInterceptor支持mysql数据的分页
```java
PageInterceptor pageInterceptor = new MysqlPageInterceptor();
sessionFactory.setPlugins(new Interceptor[]{pageInterceptor});
```

##### 2、OraclePageInterceptor支持oracle数据的分页
```java
PageInterceptor pageInterceptor = new OraclePageInterceptor();
sessionFactory.setPlugins(new Interceptor[]{pageInterceptor});
```

##### 3、PostgresqlPageInterceptor支持postgresql数据的分页
```java
PageInterceptor pageInterceptor = new PostgresqlPageInterceptor();
sessionFactory.setPlugins(new Interceptor[]{pageInterceptor});
```

ps:如果分页对应的查询语句中不存在` from `会throw异常分页查询SQL没有找到[from]关键字，from必须为小写且前后加空格。
