SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for t_flink_env
-- ----------------------------
DROP TABLE IF EXISTS `t_flink_env`;
CREATE TABLE `t_flink_env` (
`ID` bigint NOT NULL AUTO_INCREMENT COMMENT 'ID',
`FLINK_NAME` varchar(255) NOT NULL COMMENT 'Flink实例名称',
`FLINK_HOME` varchar(255) NOT NULL COMMENT 'Flink Home路径',
`VERSION` varchar(50) NOT NULL COMMENT 'Flink对应的版本号',
`SCALA_VERSION` varchar(50) NOT NULL COMMENT 'Flink对应的scala版本号',
`FLINK_CONF` text NOT NULL COMMENT 'flink-conf配置内容',
`IS_DEFAULT` tinyint NOT NULL DEFAULT '0' COMMENT '是否为默认版本',
`DESCRIPTION` varchar(255) DEFAULT NULL COMMENT '描述信息',
`CREATE_TIME` datetime NOT NULL COMMENT '创建时间',
PRIMARY KEY (`ID`) USING BTREE,
UNIQUE KEY `UN_NAME` (`FLINK_NAME`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;


INSERT INTO `t_setting` VALUES (11, 'docker.register.address', null, 'Docker Register Address', 'Docker容器服务地址', 1);
INSERT INTO `t_setting` VALUES (12, 'docker.register.user', null, 'Docker Register User', 'Docker容器服务认证用户名', 1);
INSERT INTO `t_setting` VALUES (13, 'docker.register.password', null, 'Docker Register Password', 'Docker容器服务认证密码', 1);

ALTER TABLE `streamx`.`t_flink_app` ADD COLUMN `VERSION_ID` bigint NULL AFTER `JOB_ID`;
ALTER TABLE `streamx`.`t_flink_app` ADD COLUMN `CLUSTER_ID` varchar(255) NULL AFTER `VERSION_ID`;
ALTER TABLE `streamx`.`t_flink_app` ADD COLUMN `K8S_NAMESPACE` varchar(255) NULL AFTER `CLUSTER_ID`;
ALTER TABLE `streamx`.`t_flink_app` ADD COLUMN `FLINK_IMAGE` varchar(255) NULL AFTER `K8S_NAMESPACE`;
ALTER TABLE `streamx`.`t_flink_app` ADD COLUMN `K8S_REST_EXPOSED_TYPE` tinyint NULL AFTER `RESOLVE_ORDER`;
ALTER TABLE `streamx`.`t_flink_app` ADD COLUMN `K8S_POD_TEMPLATE` text NULL AFTER `ALERT_EMAIL`;
ALTER TABLE `streamx`.`t_flink_app` ADD COLUMN `K8S_JM_POD_TEMPLATE` text NULL AFTER `K8S_POD_TEMPLATE`;
ALTER TABLE `streamx`.`t_flink_app` ADD COLUMN `K8S_TM_POD_TEMPLATE` text NULL AFTER `K8S_JM_POD_TEMPLATE`;

delete from `t_setting` where `NUM` = 1;
delete from `t_setting` where `NUM` = 4;

update `t_setting` set `NUM`= case when `NUM` > 4 then `NUM` - 2 else `NUM` - 1 end;

update `t_flink_project` set `url`='https://gitee.com/streamxhub/streamx-quickstart.git' where `NAME`='streamx-quickstart';


-- ----------------------------
-- Table structure for t_group
-- ----------------------------
DROP TABLE IF EXISTS `streamx`.`t_team`;
CREATE TABLE `streamx`.`t_team`
(
    `TEAM_ID`     bigint       NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `TEAM_CODE`   varchar(255) NOT NULL COMMENT '团队标识 后续可以用于队列 资源隔离相关',
    `TEAM_NAME`   varchar(255) NOT NULL COMMENT '团队名',
    `CREATE_TIME` datetime     NOT NULL COMMENT '创建时间',
    PRIMARY KEY (`TEAM_ID`) USING BTREE,
    UNIQUE KEY `TEAM_CODE` (TEAM_CODE) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;


DROP TABLE IF EXISTS `streamx`.`t_team_user`;
CREATE TABLE `streamx`.`t_team_user`
(
    `TEAM_ID`    bigint   NOT NULL COMMENT 'teamId',
    `USER_ID`     bigint   NOT NULL COMMENT 'userId',
    `CREATE_TIME` datetime NOT NULL COMMENT '创建时间',
    UNIQUE KEY `GROUP_USER` (`TEAM_ID`,`USER_ID`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;


insert into streamx.t_team values (1,'bigdata','大数据组','2022-02-21 18:00:00');
insert into streamx.t_team_user values (1, 1,'2022-02-21 18:00:00');


ALTER TABLE `streamx`.`t_flink_app` ADD COLUMN `TEAM_ID` bigint not null default 0 comment '任务所属组';

ALTER TABLE `streamx`.`t_flink_project`
    ADD COLUMN `TEAM_ID` bigint not null default 0 comment '项目所属组';

INSERT INTO `t_menu` VALUES (37, 1, 'Team Management', '/system/team', 'system/team/Team', 'team:view', 'team', '0', '1', 1, NOW(), NULL);
INSERT INTO `t_menu` VALUES (38, 37, 'add', NULL, NULL, 'team:add', NULL, '1', '1', NULL, NOW(), NULL);
INSERT INTO `t_menu` VALUES (39, 37, 'update', NULL, NULL, 'team:update', NULL, '1', '1', NULL, NOW(), NULL);
INSERT INTO `t_menu` VALUES (40, 37, 'delete', NULL, NULL, 'team:delete', NULL, '1', '1', NULL, NOW(), NULL);


INSERT INTO `t_role_menu` VALUES (1, 37);
INSERT INTO `t_role_menu` VALUES (1, 38);
INSERT INTO `t_role_menu` VALUES (1, 39);
INSERT INTO `t_role_menu` VALUES (1, 40);


SET FOREIGN_KEY_CHECKS = 1;

