#### 项目概览



##### 前端页面

- [ ] 注册界面

- [ ] 登录界面

- [ ] 返回**用户界面**或**管理员界面**

  - [^只有管理员可以发起/暂停/删除投票]: 

- [ ] 频道展示界面

- [ ] 进入频道后的投票展示界面

- [ ] 投票界面

##### 后端界面

###### 	接口

- ​	注册

  ```
  普通用户注册
  /register  Method：POSt、
  参数:
  username 
  password
  重复密码：用来确认正确注册
  
  管理员注册
  参数:
  /admin/register Method：POSt
  username 
  password
  重复密码：用来确认正确注册
  
  返回：
  code：信息代码(200/404)
  message：返回信息
  
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
  /usrVote：Method：POSt
  参数：token
  	 ChannelId（频道id）
  	 VoteId(投票id)	
  返回：code(200/404)
  	 message
  	 
  /usrVote_delete：Method：POSt
  参数：token
  	 ChannelId（频道id）
  	 VoteId(投票id)	
  返回：code
  	 message
  ```



- 管理员发起投票

  ```
  /amdin/createVote：Method：POSt
  参数：token
  	 ChannelId（频道id）
  	 VoteName
  	 VoteId(投票id)	
  	  EndTime（截至时间）例如：2022-09-01 12:01:56
  返回：code(200/404)
  	 message
  ```

- 管理员暂停投票

  ```
  /amdin/suspendVote：Method：POSt
  参数：token
  	 ChannelId（频道id）
  	 VoteId(投票id)	
  	
  返回：code(200/404)
  	 message
  
  ```

- 管理员暂停投票

  ```
  /amdin/deleteVote：Method：POSt
  参数：token
  	 ChannelId（频道id）
  	 VoteId(投票id)
       
  返回：code(200/404)
  	 message
  ```

  

######  	数据库表结构

- ```
  Channel-投票频道：	id name
  VoteIterm-投票实体（每一个单个投票）：id name 所属频道（id） 发布时间 截至时间 是否暂停发布者id
  Options单个投票中选项：id name 得票数 投票实体
  用户投票纪录：id 用户id 投票实体id
  用户：		  id 用户名 密码 是否为管理员
   
  ```

