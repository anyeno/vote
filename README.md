#### 项目概览

##### 项目技术介绍

###### 前端

###### 后端

- 使用用**Springboot**框架,**mysql**数据库，使用**MybatisPlus**在框架内搭建了Mapper层，实现了Pojo类对表的映射，使用**Lombok**自动为Pojo类自动生成getter，setter和构造方法
- 使用**SpringSecurity**对用户的请求进行拦截判断。使用jwt生成token登陆后交给客户端，在访问时对token进行判断实现了用户的登录认证和鉴权。并自定义未登录和权限不足的返回。
- 使用**redis**实现了对用户信息的缓存，登录后利用token和redis信息核对即可实现用户的登录认证和鉴权。



##### 前端页面

1. 首页
    1. 未登录状态会显示登录注册按钮，登录之后会显示用户名和退出按钮
    2. 普通用户看不到创建投票按钮， 管理员可以看到
2. 注册界面
3. 登录界面
4. 投票频道界面
    1. 可以看到所有的投票频道  投票频道对投票进行了分类
    2. 管理员可以删除频道、创建频道
    3. 普通用户只能进入频道
5. 投票实体界面
    1. 从投票频道界面进入对应的投票实体界面
    2. 记录了这个频道内的所有的发起的投票
    3. 管理员可以发起投票、删除投票、暂停投票
    4. 普通用户只能进入投票、查看投票
    5. 投票是有时限的，过期的投票背景会显示为灰色
    6. 被暂停的投票会显示为米色、此时用户不能进行投票，普通投票背景为白色
6. 投票界面
    1. 从投票实体界面进入，展示了这个投票的所有选项和得票数
    2. 用户可以投一次票
    3. 多次投票、在暂停时间内投票、为已截止的投票投票都是不允许的
    4. 投票成功之后会进入投票成功界面
7. 投票成功界面
8. 管理员新增投票频道界面
9. 管理员新增投票界面

##### 后端部分

###### 控制器接口分配

- FreeController `/free/**`

  允许非登录用户使用，包含

  - 登录
  - 注册**一般**用户
  - 查看各种资源列表

- UserAdminController `/user/**`

  允许登录用户使用，包含

  - 进行投票
  - 撤回自己的投票
  - 登出

- AdminController `/admin/**`

  仅允许登陆的管理员用户使用，包含

  - 注册管理员用户

  - 发起投票
  - 暂停投票 
  - 取消投票

  

###### 	接口信息

- ​	注册

  ```
  普通用户注册
  /free/register  Method：POSt、
  参数:
  username 
  password
  repeat：用来确认正确注册
  
  管理员注册
  参数:
  /admin/register Method：POSt
  username 
  password
  repeat：用来确认正确注册
  
  返回：
  code：信息代码(200/404)
  message：user实体
  
  ```

  

- 登录

  ```
  /login     Method：POSt
  参数：
  username 
  password
  
  返回：
  code：(200/404)
  message：若成功则为token：jwt token
  
  ```



- 请求全部频道

  ```
  /getAllChannel Method：GET
  参数：无
  返回：ChannelList （Channel类型数组）
  
  ```



- 请求频道内所有投票

  ```
  /getAllVote Method：GET
  参数：id（频道id）
  返回：VoteList （VoteIterm类型数组）
  ```

  

- 请求单个投票信息

  ```(
  /getAllOptions：Method：POSt
  参数：token
  	 ChannelId（频道id）
  	 VoteId(投票id)	
  返回：OptionsList(Options类型数组)
  ```



- 用户投票/撤回

  ```
  /user/vote Method:GET
  参数：
  	Header	  :"token":token 
      Body	  :"id":频道实体id
  	 			
  返回：
  	code(200/404)
  
  /user/vote_back Method:GET
  参数：
  	Header	  :"token":token 
  	 Body	  :"id":频道实体id
  返回：
  	成功：
  	code(200/404)
  ```



- 管理员发起/删除投票频道

  ```
  /admin/createChannel Method：GET
  参数：
  	Header	  :"token":token 
      Body	  :"name" :频道名称
  	 			
  返回：
  	code(200/404)
  	 message
  
  /admin/deleteChannel Method：GET
  参数：
  	Header	  :"token":token 
      Body	  :"name" :频道名称
  	 			
  返回：
  	code(200/404)
  	 message
  ```

  

- 管理员发起/删除投票实体

  ```
  /admin/creatVote Method:POST
  参数：
  	Header	  :"token":token 
      Body	  :"votename" :投票实体
      			"channelName":频道名称
      			"during":投票持续时间（小时计数）
  	 			
  返回：
  	code(200/404)
  	 message：VoteItem实体
  
  /admin/deleteVote Method:GET
  参数：
  	Header	  :"token":token 
      Body	  :"id" :投票实体id
  	 			
  返回：
  	成功：
  	code(200/404)
  ```
  
- 管理员暂停投票

  ```
  /admin/deleteVote Method:GET
  参数：
  	Header	  :"token":token 
      Body	  :"id" :投票实体id
  	
  返回：code(200/404)
  	 message
  
  ```
  
  
  
- 管理员新增/删除选项

  ```
  /admin/creaOption Method:POST
  参数：
  	Header	  :"token":token 
      Body	  :"votename" :投票实体
      			"channelName":频道名称
  				"optionName":投票选项名称	 			
  
  返回：
  	code(200/404)
  	 message：Option实体
  
  /admin/deleteOption Method:POST
  参数：
  	Header	  :"token":token 
      Body	  :"votename" :投票实体
      			"channelName":频道名称
      			"optionName":投票选项名称	 			
  
  返回：
  	成功：
  	code(200/404)
  ```

  

###### SpringSecurity

`SecurityConfig`——springsecurity的核心配置类。

##### 相关依赖和方法说明

- configure(HttpSecurity httpSecurity)：用于配置需要拦截的url路径、jwt过滤器及出异常后的处理器；

- configure(AuthenticationManagerBuilder auth)：用于配置UserDetailsService及PasswordEncoder；

- RestfulAccessDeniedHandler：当用户没有访问权限时的处理器，用于返回JSON格式的处理结果；

- RestAuthenticationEntryPoint：当未登录或token失效时，返回JSON格式的结果；

- UserDetailsService:SpringSecurity定义的核心接口，用于根据用户名获取用户信息，需要自行实现；

- UserDetails：SpringSecurity定义用于封装用户信息的类（主要是用户信息和权限），需要自行实现；

- PasswordEncoder：SpringSecurity定义的用于对密码进行编码及比对的接口，目前使用的是BCryptPasswordEncoder；

- JwtAuthenticationTokenFilter：在用户名和密码校验前添加的过滤器，如果有jwt的token，会自行根据token信息进行登录。



######  	数据库表结构

- ```
  Channel-投票频道：	id name
  VoteIterm-投票实体（每一个单个投票）：id name 所属频道（id） 发布时间 截至时间 是否暂停发布者id
  Options单个投票中选项：id name 得票数 投票实体
  用户投票纪录：id 用户id 投票实体id
  用户：		  id 用户名 密码 是否为管理员
   
  ```

