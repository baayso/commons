# commons
Common library.

[![Jdk Version](https://img.shields.io/badge/JDK-1.8+-green.svg)](https://www.oracle.com/technetwork/java/javase/downloads/index.html)
[![Build Status](https://travis-ci.org/baayso/commons.svg?branch=master)](https://travis-ci.org/baayso/commons)
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.baayso/commons/badge.svg)](http://maven-badges.herokuapp.com/maven-central/com.baayso/commons)

****
<br/>

## [exception:](https://github.com/baayso/commons/tree/master/src/main/java/com/baayso/commons/exception)

[ApiException](https://github.com/baayso/commons/blob/master/src/main/java/com/baayso/commons/exception/ApiException.java) 专用于 API 的异常

****
<br/>

## [ftp:](https://github.com/baayso/commons/tree/master/src/main/java/com/baayso/commons/ftp)

[DownloadStatus](https://github.com/baayso/commons/blob/master/src/main/java/com/baayso/commons/ftp/DownloadStatus.java)

[FtpConfig](https://github.com/baayso/commons/blob/master/src/main/java/com/baayso/commons/ftp/FtpConfig.java)

[FtpUtils](https://github.com/baayso/commons/blob/master/src/main/java/com/baayso/commons/ftp/FtpUtils.java)

[UploadStatus](https://github.com/baayso/commons/blob/master/src/main/java/com/baayso/commons/ftp/UploadStatus.java)

****
<br/>

## [http:](https://github.com/baayso/commons/tree/master/src/main/java/com/baayso/commons/http)

[HttpClientUtils](https://github.com/baayso/commons/blob/master/src/main/java/com/baayso/commons/http/HttpClientUtils.java)

[OkHttpClientUtils](https://github.com/baayso/commons/blob/master/src/main/java/com/baayso/commons/http/OkHttpClientUtils.java)

****
<br/>

## [interceptor:](https://github.com/baayso/commons/tree/master/src/main/java/com/baayso/commons/interceptor)

[DataDigestInterceptorAdapter](https://github.com/baayso/commons/blob/master/src/main/java/com/baayso/commons/interceptor/DataDigestInterceptorAdapter.java) 验证数据摘要拦截器

[PerformanceInterceptor](https://github.com/baayso/commons/blob/master/src/main/java/com/baayso/commons/interceptor/PerformanceInterceptor.java) 性能监控拦截器

****
<br/>

## [log:](https://github.com/baayso/commons/tree/master/src/main/java/com/baayso/commons/log)

[Log](https://github.com/baayso/commons/blob/master/src/main/java/com/baayso/commons/log/Log.java) 对 slf4j Logger 的简单封装

****
<br/>

## [mybatis:](https://github.com/baayso/commons/tree/master/src/main/java/com/baayso/commons/mybatis)

~~[BaseMapper](https://github.com/baayso/commons/blob/master/src/main/java/com/baayso/commons/mybatis/mapper/BaseMapper.java)~~ 已删除（与 Mybatis-Plus 3.x API 冲突，建议放至具体业务项目中），基于[Mybatis-Plus 2.x](https://github.com/baomidou/mybatis-plus/tree/2.x)的**自定义通用Mapper**

[CommonMapper](https://github.com/baayso/commons/blob/master/src/main/java/com/baayso/commons/mybatis/mapper/CommonMapper.java) 基于[MyBatis通用Mapper3](https://github.com/abel533/Mapper/tree/3.5.x)的**自定义通用Mapper**

[EnumValueTypeHandler](https://github.com/baayso/commons/blob/master/src/main/java/com/baayso/commons/mybatis/type/EnumValueTypeHandler.java) 自定义Mybatis处理枚举转换类

[ValueEnum](https://github.com/baayso/commons/blob/master/src/main/java/com/baayso/commons/mybatis/type/ValueEnum.java)

****
<br/>

## [qiniu:](https://github.com/baayso/commons/tree/master/src/main/java/com/baayso/commons/qiniu)

[QiniuConfigurable](https://github.com/baayso/commons/blob/master/src/main/java/com/baayso/commons/qiniu/QiniuConfigurable.java) 七牛云存储配置

[QiniuConfigurationAdapter](https://github.com/baayso/commons/blob/master/src/main/java/com/baayso/commons/qiniu/QiniuConfigurationAdapter.java) Abstract adapter class for the QiniuConfigurable interface

[QiniuProvider](https://github.com/baayso/commons/blob/master/src/main/java/com/baayso/commons/qiniu/QiniuProvider.java) 七牛云存储操作封装

****
<br/>

## [security:](https://github.com/baayso/commons/tree/master/src/main/java/com/baayso/commons/security)

[BCryptPasswordEncoder](https://github.com/baayso/commons/blob/master/src/main/java/com/baayso/commons/security/password/BCryptPasswordEncoder.java) BCrypt strong hashing function

[PasswordEncoder](https://github.com/baayso/commons/blob/master/src/main/java/com/baayso/commons/security/password/PasswordEncoder.java)

[AESCoder](https://github.com/baayso/commons/blob/master/src/main/java/com/baayso/commons/security/AESCoder.java)

[EncryptPropertyPlaceholderConfigurer](https://github.com/baayso/commons/blob/master/src/main/java/com/baayso/commons/security/EncryptPropertyPlaceholderConfigurer.java)

[RSACoder](https://github.com/baayso/commons/blob/master/src/main/java/com/baayso/commons/security/RSACoder.java)

****
<br/>

## [sequence:](https://github.com/baayso/commons/tree/master/src/main/java/com/baayso/commons/sequence)

[ObjectId](https://github.com/baayso/commons/blob/master/src/main/java/com/baayso/commons/sequence/ObjectId.java) MongoDB ObjectId

[Sequence](https://github.com/baayso/commons/blob/master/src/main/java/com/baayso/commons/sequence/Sequence.java) 基于Twitter的Snowflake算法实现分布式高效有序ID

[SystemClock](https://github.com/baayso/commons/blob/master/src/main/java/com/baayso/commons/sequence/SystemClock.java) 高并发场景下System.currentTimeMillis()的性能问题的优化

****
<br/>

## [serialize:](https://github.com/baayso/commons/tree/master/src/main/java/com/baayso/commons/serialize)

[CustomSerializationRedisSerializer](https://github.com/baayso/commons/blob/master/src/main/java/com/baayso/commons/serialize/redis/CustomSerializationRedisSerializer.java) 自定义Redis序列化器

[FSTSerializer](https://github.com/baayso/commons/blob/master/src/main/java/com/baayso/commons/serialize/FSTSerializer.java) 使用FST实现的序列化

[JavaSerializer](https://github.com/baayso/commons/blob/master/src/main/java/com/baayso/commons/serialize/JavaSerializer.java) 使用标准Java API实现的序列化

[SerializationUtils](https://github.com/baayso/commons/blob/master/src/main/java/com/baayso/commons/serialize/SerializationUtils.java) 序列化工具类

[Serializer](https://github.com/baayso/commons/blob/master/src/main/java/com/baayso/commons/serialize/Serializer.java) 对象序列化接口

****
<br/>

## [service:](https://github.com/baayso/commons/tree/master/src/main/java/com/baayso/commons/service)

~~[AbstractBaseService](https://github.com/baayso/commons/blob/master/src/main/java/com/baayso/commons/service/AbstractBaseService.java)~~ 已删除（与 Mybatis-Plus 3.x API 冲突，建议放至具体业务项目中），基于[Mybatis-Plus 2.x](https://github.com/baomidou/mybatis-plus/tree/2.x)的**通用Service，封装了对数据库的单表操作**

[AbstractCommonService](https://github.com/baayso/commons/blob/master/src/main/java/com/baayso/commons/service/AbstractCommonService.java) 基于[MyBatis通用Mapper3](https://github.com/abel533/Mapper/tree/3.5.x)的**通用Service，封装了对数据库的单表操作**

****
<br/>

## [spring:](https://github.com/baayso/commons/tree/master/src/main/java/com/baayso/commons/spring)

[ClassScanner](https://github.com/baayso/commons/blob/master/src/main/java/com/baayso/commons/spring/ClassScanner.java) 基于spring的扫描指定package和指定类型的class扫描工具

[SpringUtils](https://github.com/baayso/commons/blob/master/src/main/java/com/baayso/commons/spring/SpringUtils.java)

****
<br/>

## [tool:](https://github.com/baayso/commons/tree/master/src/main/java/com/baayso/commons/tool)

[CommonResponseStatus](https://github.com/baayso/commons/blob/master/src/main/java/com/baayso/commons/tool/CommonResponseStatus.java)

[ResponseStatus](https://github.com/baayso/commons/blob/master/src/main/java/com/baayso/commons/tool/ResponseStatus.java)

****
<br/>

## [utils:](https://github.com/baayso/commons/tree/master/src/main/java/com/baayso/commons/utils)

[BigDecimalUtils](https://github.com/baayso/commons/blob/master/src/main/java/com/baayso/commons/utils/BigDecimalUtils.java) BigDecimal 工具类

[CloneUtils](https://github.com/baayso/commons/blob/master/src/main/java/com/baayso/commons/utils/CloneUtils.java) 使用序列化实现对象的（深）拷贝

[Distance](https://github.com/baayso/commons/blob/master/src/main/java/com/baayso/commons/utils/Distance.java)

[FileUtils](https://github.com/baayso/commons/blob/master/src/main/java/com/baayso/commons/utils/FileUtils.java)

[JsonUtils](https://github.com/baayso/commons/blob/master/src/main/java/com/baayso/commons/utils/JsonUtils.java) 基于 Jackson JSON 的 JSON工具类

[Validator](https://github.com/baayso/commons/blob/master/src/main/java/com/baayso/commons/utils/Validator.java) 参数检验器

****
<br/>

## [web:](https://github.com/baayso/commons/tree/master/src/main/java/com/baayso/commons/web)

[BodyReaderHttpServletRequestWrapper](https://github.com/baayso/commons/blob/master/src/main/java/com/baayso/commons/web/BodyReaderHttpServletRequestWrapper.java) 包装HttpServletRequest，防止流读取一次后就没有了

[WebUtils](https://github.com/baayso/commons/blob/master/src/main/java/com/baayso/commons/web/WebUtils.java)

****
<br/>

