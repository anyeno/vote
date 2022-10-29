# 工程简介

# 

# 修改配置文件

修改`src\main\resources\application.properties`为自己的数据库配置



# 建表语句

```
create table channel
(
    id   int auto_increment
        primary key,
    name varchar(50) null,
    constraint channel_id_uindex
        unique (id)
);
```

```
create table options
(
    id           int auto_increment
        primary key,
    name         varchar(100)  null,
    vote_item_id int           not null,
    vote_count   int default 0 null,
    constraint options_id_uindex
        unique (id)
);
```

```
create table record
(
    id      int not null,
    user_id int not null,
    vote_id int not null
);
```

```
create table user
(
    id       int auto_increment
        primary key,
    name     varchar(100)         not null,
    password varchar(300)         not null,
    is_admin tinyint(1) default 0 null,
    constraint user_id_uindex
        unique (id)
);
```

```
create table vote_item
(
    id          int auto_increment
        primary key,
    name        varchar(100) null,
    channel_id  int          not null,
    create_time datetime     null,
    end_time    datetime     null,
    is_paused    tinyint(1)   null,
    user_id     int          not null,
    constraint vote_item_id_uindex
        unique (id)
);
```

