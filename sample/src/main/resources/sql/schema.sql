-- 这里是定义数据结构的SQL，每次运行都会执行，此文件不能为空，必须至少写一句Sql语句。
DROP DATABASE sample;
CREATE DATABASE sample;
USE sample;
-- 安全用户信息表
CREATE TABLE IF NOT EXISTS user
(
    id                 integer  primary key not null ,
    username           VARCHAR(50)            not null ,
    password           VARCHAR(50)            NOT NULL,
    email              varchar(50) comment '用户邮箱',
    activated          tinyint(1),
    activation_key     varchar(50),
    reset_password_key varchar(50)
)
    AUTO_INCREMENT = 100001
    DEFAULT CHARSET = utf8;



create table if not exists t_test_1
(
    `id` integer(32) primary key auto_increment comment '推送消息id',
    `target_user` varchar(32) not null comment '推送用户id' ,
    `entry_id` integer(32) not null comment '对应同送内容实体信息id',
    `entry_type` tinyint(4) not null comment '推送消息类型:1.评论|2.点赞|3.分享|4.邀请',
    `create_time` timestamp not null default current_timestamp comment '消息创建时间',
    `update_time` timestamp not null default current_timestamp on update current_timestamp comment '消息查阅时间',
    `status` tinyint not null default 0 comment '消息查阅状态:0.未查阅|1.已查阅|2.已过期',
    key `IX_USER_ID` (`target_user`)
) auto_increment=10000000  engine = InnoDB default charset = utf8 comment '测试表';