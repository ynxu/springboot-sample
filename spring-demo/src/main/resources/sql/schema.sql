-- 这里是定义数据结构的SQL，每次运行都会执行，此文件不能为空，必须至少写一句Sql语句。
DROP DATABASE sample;
CREATE DATABASE sample;
USE sample;
-- app版本信息
CREATE TABLE IF NOT EXISTS sample_user
(
    id       INT PRIMARY KEY AUTO_INCREMENT,
    user_name VARCHAR(20) NOT NULL,
    user_pwd VARCHAR(20) NOT NULL
)
    AUTO_INCREMENT = 100001
    DEFAULT CHARSET = utf8;