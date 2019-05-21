-- 这里是定义数据结构的SQL，每次运行都会执行，此文件不能为空，必须至少写一句Sql语句。
DROP DATABASE sample;
CREATE DATABASE sample;
USE sample;
-- 安全用户信息表
CREATE TABLE IF NOT EXISTS user
(
    username           VARCHAR(50) primary key NULL,
    password           VARCHAR(50)             NOT NULL,
    email              varchar(50) comment '用户邮箱',
    activated          tinyint(1),
    activation_key     varchar(50),
    reset_password_key varchar(50)
)
    AUTO_INCREMENT = 100001
    DEFAULT CHARSET = utf8;

-- 角色安全信息表
create table if not exists authority
(
    name varchar(50) not null comment '安全角色信息'
)
    DEFAULT CHARSET = utf8;;
-- 用户角色关联表
create table if not exists user_authority
(
    username  varchar(50) not null,
    authority varchar(50) not null
)
    DEFAULT CHARSET = utf8;

-- access token 信息表
create table if not exists access_token
(
    token_id          varchar(256) not null,
    token             blob,
    authentication_id varchar(256),
    user_name         varchar(20),
    client_id         varchar(256),
    authentication    blob,
    refresh_token     varchar(256)

)
    DEFAULT CHARSET = utf8;;
-- refresh token 信息表
create table if not exists refresh_token
(
    token_id       varchar(256) not null,
    token          blob,
    authentication bool
)
    DEFAULT CHARSET = utf8;



