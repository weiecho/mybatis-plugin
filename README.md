# lemon-mybatis-plus
根据真实使用场景对mybatis进行的扩展，实现分页、json存储、数组存储、blob存储等支持。

### 扩展Mybatis数据类型

##### 1、ArrayTypeHandler 支持数组数据的存储和读取
bean定义格式
```java
private String[] tags;
```

mapper定义格式
```xml
<result property="tags" column="tags" typeHandler="cn.lemon.mybatis.plugin.ArrayTypeHandler"/>
```


##### 2、JsonTypeHandler 支持Json对象数据的存储和读取
bean定义格式
```java
/** 图片 如：[{imageUrl:"/1.jpg"}] **/
private Object imagesUrl;
```

mapper定义格式
```xml
<result property="imagesUrl" column="imagesUrl" typeHandler="cn.lemon.mybatis.plugin.JsonTypeHandler"/>
```

##### 3、BlobTypeHandler 支持Blob数据的存储和读取
bean定义格式
```java
/** 文章内容 **/
private String content;
```

mapper定义格式
```xml
<result property="content" column="content" typeHandler="cn.lemon.mybatis.plugin.BlobTypeHandler"/>
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
