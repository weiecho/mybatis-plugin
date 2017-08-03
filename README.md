# lemon-mybatis-plus
根据真实使用场景对mybatis的进行扩展，实现分页、json存储、数组存储、blob存储等支持。

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
<result property="imagesUrl" column="imagesUrl" typeHandler="cn.lemon.mybatis.plugin.ArrayTypeHandler"/>
```

##### 3、BlobTypeHandler 支持Blob数据的存储和读取
bean定义格式
```java
/** 文章内容 **/
private String content;
```

mapper定义格式
```xml
<result property="content" column="content" typeHandler="cn.lemon.provider.config.BlobTypeHandler"/>
```

### 扩展Mybatis自动执行分页SQL处理


