-- MySQL dump 10.13  Distrib 8.0.19, for Win64 (x86_64)
--
-- Host: localhost    Database: demo_db
-- ------------------------------------------------------
-- Server version	5.7.29-log

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `demo_tb_admin`
--

DROP TABLE IF EXISTS `demo_tb_admin`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `demo_tb_admin`
(
    `admin_id`         char(32) NOT NULL,
    `nickname`         char(20) NOT NULL,
    `admin_account`    char(15) NOT NULL,
    `admin_phone`      char(15) NOT NULL DEFAULT '',
    `admin_password`   varchar(255)      DEFAULT NULL,
    `mini_open_id`     char(50)          DEFAULT NULL COMMENT '小程序openId',
    `subscribed`       tinyint(4) DEFAULT '0' COMMENT '是否关注公众号',
    `official_open_id` char(50)          DEFAULT NULL COMMENT '公众号OpenId',
    `wechat_union_id`  char(50)          DEFAULT NULL COMMENT 'unionID',
    `token_version`    int(11) NOT NULL DEFAULT '0',
    `role`             char(20) NOT NULL DEFAULT 'ROLE_USER',
    `locked`           tinyint(4) NOT NULL DEFAULT '0' COMMENT '0：正常 1：冻结',
    `is_delete`        tinyint(4) NOT NULL DEFAULT '0' COMMENT '0：正常 1：删除',
    `create_time`      datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `update_time`      datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (`admin_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `demo_tb_admin`
--

LOCK
TABLES `demo_tb_admin` WRITE;
/*!40000 ALTER TABLE `demo_tb_admin` DISABLE KEYS */;
/*!40000 ALTER TABLE `demo_tb_admin` ENABLE KEYS */;
UNLOCK
TABLES;

--
-- Table structure for table `demo_tb_article`
--

DROP TABLE IF EXISTS `demo_tb_article`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `demo_tb_article`
(
    `article_id`  char(32) NOT NULL COMMENT 'ID',
    `is_delete`   tinyint(4) NOT NULL DEFAULT '0' COMMENT '0正常 1删除',
    `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (`article_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `demo_tb_article`
--

LOCK
TABLES `demo_tb_article` WRITE;
/*!40000 ALTER TABLE `demo_tb_article` DISABLE KEYS */;
INSERT INTO `demo_tb_article`
VALUES ('1410533976459345921', 0, '2021-07-01 17:41:21', '2021-07-01 17:41:21');
/*!40000 ALTER TABLE `demo_tb_article` ENABLE KEYS */;
UNLOCK
TABLES;

--
-- Table structure for table `demo_tb_login_log`
--

DROP TABLE IF EXISTS `demo_tb_login_log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `demo_tb_login_log`
(
    `login_log_id` char(32)     NOT NULL COMMENT '登录日志id',
    `login_path`   varchar(128) NOT NULL DEFAULT '' COMMENT '登录入口',
    `login_result` varchar(10)  NOT NULL DEFAULT '' COMMENT '登录结果',
    `log_point`    varchar(64)  NOT NULL DEFAULT '' COMMENT '记录点',
    `ip`           varchar(64)  NOT NULL DEFAULT '' COMMENT 'ip地址',
    `user_id`      char(32)     NOT NULL DEFAULT '' COMMENT '用户id',
    `username`     varchar(64)  NOT NULL DEFAULT '' COMMENT '用户昵称',
    `param`        text COMMENT '请求json参数',
    `exception`    text COMMENT '异常信息',
    `is_delete`    tinyint(4) NOT NULL DEFAULT '0' COMMENT '是否已经删除,0为否，1为是',
    `create_time`  datetime     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '插入时间',
    `update_time`  datetime     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`login_log_id`),
    KEY            `log_time` (`create_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `demo_tb_login_log`
--

LOCK
TABLES `demo_tb_login_log` WRITE;
/*!40000 ALTER TABLE `demo_tb_login_log` DISABLE KEYS */;
INSERT INTO `demo_tb_login_log`
VALUES ('1410440544588058626', 'user/login/account', '登录成功', 'successHandler', '0:0:0:0:0:0:0:1', '1376087783730413570',
        'user1', '{}', NULL, 0, '2021-07-01 11:30:05', '2021-07-01 11:30:05');
/*!40000 ALTER TABLE `demo_tb_login_log` ENABLE KEYS */;
UNLOCK
TABLES;

--
-- Table structure for table `demo_tb_news`
--

DROP TABLE IF EXISTS `demo_tb_news`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `demo_tb_news`
(
    `news_id`     char(32) NOT NULL COMMENT 'ID',
    `is_delete`   tinyint(4) NOT NULL DEFAULT '0' COMMENT '0正常 1删除',
    `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (`news_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `demo_tb_news`
--

LOCK
TABLES `demo_tb_news` WRITE;
/*!40000 ALTER TABLE `demo_tb_news` DISABLE KEYS */;
/*!40000 ALTER TABLE `demo_tb_news` ENABLE KEYS */;
UNLOCK
TABLES;

--
-- Table structure for table `demo_tb_system_log`
--

DROP TABLE IF EXISTS `demo_tb_system_log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `demo_tb_system_log`
(
    `system_log_id` char(32)     NOT NULL COMMENT '系统日志id',
    `path`          varchar(100) NOT NULL DEFAULT '' COMMENT '请求路径',
    `method`        varchar(10)  NOT NULL DEFAULT '' COMMENT '请求方法',
    `type`          varchar(10)  NOT NULL DEFAULT '' COMMENT '操作类型',
    `name`          varchar(25)  NOT NULL DEFAULT '' COMMENT '操作名称',
    `platform`      varchar(25)  NOT NULL DEFAULT '' COMMENT '操作平台',
    `ip`            varchar(20)  NOT NULL DEFAULT '' COMMENT 'ip地址',
    `user_id`       char(32)     NOT NULL DEFAULT '' COMMENT '用户id',
    `username`      varchar(20)  NOT NULL DEFAULT '' COMMENT '用户昵称',
    `header`        text COMMENT '头部信息',
    `param`         text COMMENT '请求json参数',
    `result`        text COMMENT '响应结果',
    `exception`     text COMMENT '异常信息',
    `is_delete`     tinyint(4) NOT NULL DEFAULT '0' COMMENT '是否已经删除,0为否，1为是',
    `create_time`   datetime     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '插入时间',
    `update_time`   datetime     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`system_log_id`),
    KEY             `log_time` (`create_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `demo_tb_system_log`
--

LOCK
TABLES `demo_tb_system_log` WRITE;
/*!40000 ALTER TABLE `demo_tb_system_log` DISABLE KEYS */;
INSERT INTO `demo_tb_system_log`
VALUES ('1410438886168645633', '/article/user/article/articles', 'POST', '成功', '文章列表', '用户端', '0:0:0:0:0:0:0:1', '', '',
        '{\"content-type\":\"application/json\",\"token\":null}', '{}',
        '{\"statusCode\":200,\"statusMsg\":\"success\",\"data\":[]}', NULL, 0, '2021-07-01 11:23:29',
        '2021-07-01 11:23:29'),
       ('1410533978564923393', '/article/user/article/saveArticle', 'POST', '成功', '保存文章', '用户端', '0:0:0:0:0:0:0:1',
        '1376087783730413570', 'user1',
        '{\"content-type\":\"application/json\",\"token\":\"Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIxMzc2MDg3NzgzNzMwNDEzNTcwIiwidG9rZW5WZXJzaW9uIjozLCJleHAiOjE2MjU3MTUwMDUsImlhdCI6MTYyNTExMDIwNX0.0lzUzELWL2YfvCjvKZvvpnlFOj8BStUhStG23plBnf9J3h3iZpMyE3PUiM4VWCbJvCSmeCesjgUYvSYG2gU71g\"}',
        '{}', '{\"statusCode\":200,\"statusMsg\":\"success\",\"data\":null}', NULL, 0, '2021-07-01 17:41:21',
        '2021-07-01 17:41:21'),
       ('1410537422663127042', '/article/user/article/articles', 'POST', '成功', '文章列表', '用户端', '0:0:0:0:0:0:0:1',
        '1376087783730413570', 'user1',
        '{\"content-type\":\"application/json\",\"token\":\"Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIxMzc2MDg3NzgzNzMwNDEzNTcwIiwidG9rZW5WZXJzaW9uIjozLCJleHAiOjE2MjU3MTUwMDUsImlhdCI6MTYyNTExMDIwNX0.0lzUzELWL2YfvCjvKZvvpnlFOj8BStUhStG23plBnf9J3h3iZpMyE3PUiM4VWCbJvCSmeCesjgUYvSYG2gU71g\"}',
        '{}',
        '{\"statusCode\":200,\"statusMsg\":\"success\",\"data\":[{\"articleId\":\"1410533976459345921\",\"createTime\":\"2021-07-01 17:41:21\"}]}',
        NULL, 0, '2021-07-01 17:55:02', '2021-07-01 17:55:02');
/*!40000 ALTER TABLE `demo_tb_system_log` ENABLE KEYS */;
UNLOCK
TABLES;

--
-- Table structure for table `demo_tb_user`
--

DROP TABLE IF EXISTS `demo_tb_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `demo_tb_user`
(
    `user_id`          char(32) NOT NULL,
    `nickname`         char(20) NOT NULL,
    `user_account`     char(15) NOT NULL,
    `user_phone`       char(15) NOT NULL DEFAULT '',
    `user_password`    varchar(255)      DEFAULT NULL,
    `mini_open_id`     char(50)          DEFAULT NULL COMMENT '小程序openId',
    `subscribed`       tinyint(4) DEFAULT '0' COMMENT '是否关注公众号',
    `official_open_id` char(50)          DEFAULT NULL COMMENT '公众号OpenId',
    `wechat_union_id`  char(50)          DEFAULT NULL COMMENT 'unionID',
    `token_version`    int(11) NOT NULL DEFAULT '0',
    `role`             char(20) NOT NULL DEFAULT 'ROLE_USER',
    `locked`           tinyint(4) NOT NULL DEFAULT '0' COMMENT '0：正常 1：冻结',
    `is_delete`        tinyint(4) NOT NULL DEFAULT '0' COMMENT '0：正常 1：删除',
    `create_time`      datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `update_time`      datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `demo_tb_user`
--

LOCK
TABLES `demo_tb_user` WRITE;
/*!40000 ALTER TABLE `demo_tb_user` DISABLE KEYS */;
INSERT INTO `demo_tb_user`
VALUES ('1376087783730413570', 'user1', 'user', '10086', '$2a$10$/7xE1Q9jFhxNe4J3TvS4iewwF2WeXaMmdsgePddF72Ob.SxxpP9g.',
        NULL, 0, NULL, NULL, 3, 'ROLE_USER', 0, 0, '2021-07-01 10:48:46', '2021-07-01 10:48:46');
/*!40000 ALTER TABLE `demo_tb_user` ENABLE KEYS */;
UNLOCK
TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2021-07-01 18:08:43
