# step:

> 来自：bilibili尚硅谷-2023-10-25日发布的javaWeb微头条项目

## 1、前后端接口

- 项目中总共用到三种读取方式
  - JSON
    - new ObjectMapper().readValue()
  - 请求头
    - request.getParameter()
  - 请求体
    - request.getHeader()

- 登录注册功能

  > 需求描述

  + 用户在客户端输入用户名密码并向后端提交,后端根据用户名和密码判断登录是否成功,用户有误或者密码有误响应不同的提示信息

  > uri:

  ``` http
  user/login
  ```

  > 请求方式:

  ``` http
  POST
  ```

  > 请求参数,采用读取JSON串转为NewsUser对象进行处理

  ``` json
  {
      "username":"zhangsan", //用户名
      "userPwd":"123456"     //明文密码
  }
  ```

  > 响应示例

  + 登录成功

  ``` json
  {
      "code":"200",         // 成功状态码 
   	"message":"success"   // 成功状态描述
   	"data":{
  		"token":"... ..." // 用户id的token
  	}
  }
  ```

  + 用户名有误

  ``` json
  {
      "code":"501",
   	"message":"用户名有误"
   	"data":{}
  }
  ```

  + 密码有误

  ``` json
  {
      "code":"503",
   	"message":"密码有误"
   	"data":{}
  }
  ```

- 根据token获取完整用户信息

  > 需求描述

  + 客户端发送请求,提交token请求头,后端根据token请求头获取登录用户的详细信息并响应给客户端进行存储

  > uri

  ``` http
  user/getUserInfo
  ```

  > 请求方式

  ``` http
  GET
  ```

  > 请求头
  >
  > 可采用request.getHeader(String s) 进行读取请求头中信息

  ``` json
  token: ... ...
  ```

  > 响应示例

  + 成功获取

  ``` JSON
  {
      "code": 200,
      "message": "success",
      "data": {
          "loginUser": { 
              "uid": 1,
              "username": "zhangsan",
              "userPwd": "",
              "nickName": "张三"
          }
      }
  }
  ```

  + 获取失败

  {
      "code": 504,
      "message": "notLogin",
      "data": null
  }

- 注册时用户名占用校验

  > 需求说明

  + 用户在注册时输入用户名时,立刻将用户名发送给后端,后端根据用户名查询用户名是否可用并做出响应

  > uri:

  ``` http
  user/checkUserName
  ```

  > 请求方式:

  ``` http
  POST
  ```

  > 请求参数

  ``` json
  username=zhangsan
  ```

  > 响应示例

  + 用户名校验通过

  ``` json
  {
      "code":"200",
   	"message":"success"
   	"data":{}
  }
  ```

  + 用户名占用

  ``` json
  {
      "code":"505",
   	"message":"用户名占用"
   	"data":{}
  }
  ```


- 注册表单的提交

  > 需求说明

  + 客户端将新用户信息发送给服务端,服务端将新用户存入数据库,存入之前做用户名是否被占用校验,校验通过响应成功提示,否则响应失败提示


  > uri:

  ``` http
  user/regist
  ```

  > 请求方式:

  ``` http
  POST
  ```

  > 请求参数

  ``` json
  {
      "username":"zhangsan",
      "userPwd":"123456",
      "nickName":"张三"
  }
  ```

  > 响应示例

  + 注册成功

  ``` json
  {
      "code":"200",
   	"message":"success"
   	"data":{}
  }
  ```

  + 用户名占用

  ``` json
  {
      "code":"505",
   	"message":"用户名占用"
   	"data":{}
  }
  ```


- 查询所有头条分类

  > 需求说明

  + 进入新闻首页,查询所有分类并动态展示新闻类别栏位

  > uri:

  ``` http
  portal/findAllTypes
  ```

  > 请求方式

  ``` http
  GET
  ```

  > 请求参数

  ``` JSON
  无
  ```

  > 响应示例

  ``` JSON
  {
      "code":"200",
   	"message":"OK"
   	"data":
              [
                  {
                      "tid":"1",
                      "tname":"新闻"
                  },
                  {
                      "tid":"2",
                      "tname":"体育"
                  },
                  {
                      "tid":"3",
                      "tname":"娱乐"
                  },
                  {
                      "tid":"4",
                      "tname":"科技"
                  },
                  {
                      "tid":"5",
                      "tname":"其他"
                  }
              ]
      
  }
  
  ```

- 分页带条件查询所有头条

  > 需求说明

  + 客户端向服务端发送查询关键字,新闻类别,页码数,页大小
  + 服务端根据条件搜索分页信息,返回含页码数,页大小,总页数,总记录数,当前页数据等信息,并根据时间降序,浏览量降序排序

  > uri:

  ``` http
  portal/findNewsPage
  ```

  > 请求方式:

  ``` http
  POST
  ```

  > 请求参数:

  ``` json
  {
      "keyWords":"马斯克", // 搜索标题关键字
      "type":0,           // 新闻类型
      "pageNum":1,        // 页码数
      "pageSize":"10"     // 页大小
  }
  ```

  > 响应示例:

  ``` json
  {
      "code":"200",
   	"message":"success"
   	"data":{
      	"pageInfo":{
      		"pageData":[                           // 本页的数据
      			{
      				"hid":"1",                     // 新闻id 
      				"title":"尚硅谷宣布 ... ...",   // 新闻标题
      				"type":"1",                    // 新闻所属类别编号
      				"pageViews":"40",              // 新闻浏览量
      				"pastHours":"3" ,              // 发布时间已过小时数
      				"publisher":"1"                // 发布用户ID
  				},
  				{
      				"hid":"1",                     // 新闻id 
      				"title":"尚硅谷宣布 ... ...",   // 新闻标题
      				"type":"1",                    // 新闻所属类别编号
      				"pageViews":"40",              // 新闻浏览量
      				"pastHours":"3",              // 发布时间已过小时数
      				"publisher":"1"                // 发布用户ID
  				},
  				{
      				"hid":"1",                     // 新闻id 
      				"title":"尚硅谷宣布 ... ...",   // 新闻标题
      				"type":"1",                    // 新闻所属类别编号
      				"pageViews":"40",              // 新闻浏览量
      				"pastHours":"3",               // 发布时间已过小时数
      				"publisher":"1"                // 发布用户ID
  				}
      		],
  			"pageNum":1,    //页码数
  			"pageSize":10,  // 页大小
  			"totalPage":20, // 总页数
  			"totalSize":200 // 总记录数
  		}
  	}
  }
  ```

-  查看头条详情

  > 需求说明

  + 用户点击"查看全文"时,向服务端发送新闻id
  + 后端根据新闻id查询完整新闻文章信息并返回
  + 后端要同时让新闻的浏览量+1

  > uri

  ``` http
  portal/showHeadlineDetail
  ```

  > 请求方式

  ``` http
  POST
  ```

  > 请求参数

  ``` json
  hid=1
  ```

  > 响应示例

  ``` json
  {
      "code":"200",
      "message":"success",
      "data":{
          "headline":{
              "hid":"1",                     // 新闻id 
              "title":"马斯克宣布 ... ...",   // 新闻标题
              "article":"... ..."            // 新闻正文
              "type":"1",                    // 新闻所属类别编号
              "typeName":"科技",             // 新闻所属类别
              "pageViews":"40",              // 新闻浏览量
              "pastHours":"3" ,              // 发布时间已过小时数
              "publisher":"1" ,               // 发布用户ID
              "author":"张三"                 // 新闻作者
          }
      }
  }
  ```

- 头条发布修改和删除前的登录校验

  > 需求说明

  + 客户端在进入发布页前、发布新闻前、进入修改页前、修改前、删除新闻前先向服务端发送请求携带token请求头
  + 后端接收token请求头后,校验用户登录是否过期并做响应
  + 前端根据响应信息提示用户进入登录页还是进入正常业务页面

  > uri

  ``` http
  user/checkLogin
  ```

  > 请求方式

  ``` http
  GET
  ```

  > 请求参数

  ``` json
  无
  ```

  > 请求头

  ``` JSON
  token: ... ...
  ```

  > 响应示例

  + 登录未过期

  ``` json
  {
      "code":"200",
      "message":"success",
      "data":{}
  }
  ```

  + 登录已过期

  ``` json
  {
      "code":"504",
      "message":"loginExpired",
      "data":{}
  }
  ```

  

- 提交发布头条

  > 需求说明

  + 用户在客户端输入发布的新闻信息完毕后
  + 发布前先请求后端的登录校验接口验证登录
  + 登录通过则提交新闻信息
  + 后端将新闻信息存入数据库

  > uri

  ``` http
  headline/publish
  ```

  > 请求方式

  ``` http
  POST
  ```

  > 请求头

  ``` json
  token: ... ...
  ```

  > 请求参数

  ``` json
  {
      "title":"尚硅谷宣布 ... ...",   // 文章标题
      "article":"... ...",          // 文章内容
      "type":"1"                    // 文章类别
  }
  ```

  > 响应示例

  + 发布成功

  ``` json
  {
      "code":"200",
      "message":"success",
      "data":{}
  }
  ```

  + 失去登录状态发布失败

  ```  json
  {
      "code":"504",
      "message":"loginExpired",
      "data":{}
  }
  ```

- 修改头条回显

  > 需求说明

  + 前端先调用登录校验接口,校验登录是否过期
  + 登录校验通过后 ,则根据新闻id查询新闻的完整信息并响应给前端

  > uri

  ``` http
  headline/findHeadlineByHid
  ```

  > 请求方式

  ``` http
  POST
  ```

  > 请求参数

  ``` json
  hid=1
  ```

  > 响应示例

  + 查询成功

  ``` json
  {
      "code":"200",
      "message":"success",
      "data":{
          "headline":{
              "hid":"1",
              "title":"马斯克宣布",
              "article":"... ... ",
              "type":"2"
          }
      }
  }
  ```

  

- 保存修改

  > 需求描述

  + 客户端将新闻信息修改后,提交前先请求登录校验接口校验登录状态
  + 登录校验通过则提交修改后的新闻信息,后端接收并更新进入数据库

  > uri

  ``` http
  headline/update
  ```

  > 请求方式 

  ``` http
  POST
  ```

  > 请求参数

  ``` JSON
  {
      "hid":"1",
      "title":"尚硅谷宣布 ... ...",
      "article":"... ...",
      "type":"2"
  }
  ```

  > 响应示例

  + 修改成功

  ``` json
  {
      "code":"200",
      "message":"success",
      "data":{}
  }
  ```

  + 修改失败

  ```  json
  {
      "code":"504",
      "message":"loginExpired",
      "data":{}
  }
  ```

- 删除头条

  > 需求说明

  + 将要删除的新闻id发送给服务端
  + 服务端校验登录是否过期,未过期则直接删除,过期则响应登录过期信息

  > uri

  ``` http
  headline/removeByHid
  ```

  > 请求方式

  ``` http
  POST
  ```

  > 请求参数

  ```  json
  hid=1
  ```

  > 响应示例

  + 删除成功

  ``` json
  {
      "code":"200",
      "message":"success",
      "data":{}
  }
  ```

  + 删除失败

  ``` json
  {
      "code":"504",
      "message":"loginExpired",
      "data":{}
      
  }
  ```




## 2、数据库

- Mysql-8.0.32

- news_headline 新闻详细信息表
  - hid int -新闻id -主键
  - title varchar -标题
  - article varchar-内容
  - publisher int -作者
  - page_views int -浏览量
  - type int -新闻类型id
  - create_time datetime -发布时间
  - update_time datetime-更新时间
  - is_deleted int -是否被删除
- news_type - 新闻类型表
  - tid int -新闻类型id -主键
  - tname varchar -新闻类型名称 
- news_user -用户表
  - uid int -用户id -主键
  - username varchar -用户名
  - user_pwd varchar 密码
  - nick_name varchar 昵称 

## 3、后端

- 测试工具-pastman
  - https://www.getpostman.com 
- WEB服务器-tomcat-10.1.18
  - http://tomcat.apache.org/

- 配置tomcat

- 搭建web项目

- 导入相关依赖-web.WEB-INF.lib

  - jackson

    类库(用于处理JSON数据）

    - jackson-annotations-2.13.2.jar
    - jackson-core-2.13.2.jar
    - jackson-databind-2.13.2.jar

  - lombok

    使用lombok处理getter setter equals hashcode 构造器 

    idea下载lombok插件

    设置-Build,Exception,Deployment-Annotation Processors-【勾上】Enable annocation processing

    - lombok-1.18.24.jar	

  - mysql类库

    - druid-1.1.21.jar
    - mysql-connector-java-8.0.25.jar

  - jjwt

    - jaxb-api-2.3.0.jar
    - jjwt-0.9.1.jar

  - junit4

    - junit-4.13.1.jar

- 准备通用类

  - 异步响应规范格式类 -Result类

  - 枚举类，返回结果状态信息 -ResultCodeEnum 

- 项目结构

  headline

  - resources
    - jdbc.properties 数据库连接相关配置信息

      ```properties
      driverClassName=com.mysql.cj.jdbc.Driver
      url=jdbc:mysql://localhost:3306/top_news
      username=
      password=
      initialSize=5
      maxActive=10
      maxWait=1000
      ```
    
      
    
  - src
    - common 公共包
      - Result 异步响应格式类
      - ResultCodeEnum 枚举类，枚举后端响应状态
      
    - controller 控制层代码，主要由Servlet组成
      - BaseControl 使用不同方法控制不同请求
      - NewsHeadlineControl 管理新闻
      - NewsUserControler 管理用户
      - NewsTypeControler 管理新闻类型
      - ProptalController 门户控制器，未登录状态下访问信息
      
    - dao 数据访问层，主要用于定义对各个表格的curd方法
      - impl 存放实现类
        - NewsUserDaoImpl
        - NewsTypeDaoImpl
        -  NewsHeadlineDaoImpl
      - BaseDao 操作数据库
      - interface NewsUserDao
      - interface NewsTypeDao
      - interface NewsHeadlineDao
      
    - filters 过滤器包， 存放过滤器代码
      - CrosFilter 跨域过滤器，解决跨域问题
      - LoginFilter 检验登陆状态
      
    - pojo 实体类层 ，存放实体类
      - package-vo
        - HeadlineQueryVo 带条件查询,关键字封装
        - HeadlinePageVo 封装响应给客户端的数据
        - HeadlineDetail 显示新闻详情
      - NewsUser
      - NewsHeadline
      - NewsType
      
    - service 服务层， 主要用于处理业务逻辑
      - Impl 实现类
        -  NewsUserServiceImpl
        - NewsHeadlineServiceImpl
        - NewsTypeServiceImpl
      - interface NewsUserService
      - interface NewsHeadlineService
      - interface NewsTypeService
      
    - test 测试代码
    
    - util 工具类包，主要存放一些工具类
      - MD5Util 加密工具类
      
      - JDBCUtil连接池工具类
      
      - JwtHelper 处理token相关信息
      
      - WebUtil 处理json数据
      
        

## 4、遇到过的问题

- tomcat输出日志乱码问题

  > 查看idea或cmd编码格式,到tomcat目录下，找到conf目录下的logging.properties,修改对应编码格式即可

- 请求参数读取出错

  > json数据错误使用getParameter()读取 

  - 应使用ObjectMapper方法读取

  > 请求头数据错误使用getParameter()读取 

  - 应使用getHeader()方法读取



> 注解错误

- 访问路径出错

  > @WebServlet("/headline")，导致headline路径下方法不能被获得请求

  - 应修改为@WebServlet("/headline/*")

- 数据库操作失误

  > 数据库采用下划线分割命名,而java中采用驼峰式命名

  - 访问时需要注意起别名,变量应转换为与java一致

  > 操作数据库时注意sql语句空格、变量顺序、数据类型等问题

