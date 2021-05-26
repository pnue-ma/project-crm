# project-crm
first project based on servlet+jsp+mybatis.Java Web

  version 1.0
  
  1）servlet：doGet(),doPost()方法
（2）web.xml:用户请求与servlet的映射路径
（3）jsp中的<base>标签，加入该标签后，该jsp页面中所有的相对路径失效，实际项目开发中应全是绝对路径，不加“/”，默认根路径是该项目的web目录。


crm1:项目开发环境搭建
（1）tomcat
（2）maven
（3）mysql
（4）idea
项目开发文件框架部署：
（1）maven文件结构
（2）pom.xml引入依赖
（3）加入基础配置文件模板web.xml 	mybatis-config.xml 		jdbc.properties 		log4j.properties
（4）设计包结构，以及一些包下的工具或模板文件
（5）设计数据库表结构，及表之间的关系。表之间的关系由外键作为桥梁，外键应建立在“多”的一方

crm2：用户登录
用户输入用户名，密码
dao层执行sql语句：User user=select * from tbl_user where id=#{id} and userpass=#{userpass} 
如果user==null，则用户名或密码错误。提示。登录失败。
如果user!=null,则用户名密码正确，继续验证
user.getExpireTime()与用户登录时的当前系统时间比较是否过期。过期则提示。登录失败。
user.getLockState()与冻结状态比较。冻结则提示。登录失败
user.getAllowIps()与用户登录使用的ip地址作比较。不在允许的ip数组中则提示。登录失败。
以上四种登录失败放在一个异常类中提示。
验证成功。登录成功。跳转系统主页面。

String类的compareTo()方法与String的contains()方法
request.getServletPath()与request.getContextPath()
前端有一个缺陷:当错误信息显示后，如果用户重新输入，则错误信息应适时地消失
servlet的域对象，jsp的域对象，jsp的根本原型，域对象的生命周期
使用servlet域对象取出该域中的存放的信息，使用jsp域对象（el表达式）取出该域中存放的信息
拦截器（过滤器），监听器
controller层捕捉service层抛出的异常时，由于忽略动态代理对象而产生的大bug
过滤器进行登录拦截时由于拦截所有jsp和controller（包括login.jsp和login.do）而产生的死循环大bug
过滤器统一设置字符编码（request字符编码与response字符编码）
分层思想，数据的流动，以及数据流动过程中存取数据的不同容器
每一层的工作内容：从上一层接受输入数据，调用下一层服务（核心），返回服务对数据的处理结果给上一层。
一个疑问：怎么没有看到从哪里读mybatis-config.xml文件啊
上一层存参，上一层传参，下一层取参，下一层处理参，下一层向上一层返回处理结果。由于（最顶层的浏览器-tomcat）与（最底层的mybatis-dao）不是
我自己开发的，很多对象由他们进行维护，所以在向这两层传递参数，或者从这两层取出数据时需要遵守他们的规范，他们允许传入什么格式的参数，以及
他们将处理结果以什么样的形式进行存储。
如何传递信息？
异常，受检异常与未受检异常，异常处理机制，子类从父类继承的方法不能比父类的该方法抛出更多的异常
练习写工具类以及使用工具类
登录模块利用异常处理机制的逻辑实现
service层怎么向controller层传递结果：返回值，返回值采用什么类型的。service中逻辑的bug（仔细分析后并不是）
filter启动失败，interface中的方法带有default关键字
测试jsssionid
一条请求的处理链
request.getContextPath()	request.getServletPath()	request.getRequestURI()
构建if...else...逻辑框架	将try...catch..块写成if...else...的本质形式
controller向前端返回json形式的字符串的方式:1.手动拼接2.利用json提供的java类
jsp中获取域对象:1.通过java代码方式2.通过el表达式方式

