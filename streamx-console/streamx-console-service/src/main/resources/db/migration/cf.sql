SET
FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for t_group
-- ----------------------------
DROP TABLE IF EXISTS `streamx`.`t_group`;
CREATE TABLE `streamx`.`t_group`
(
    `GROUP_ID`          bigint       NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `GROUP_NAME`  varchar(255) NOT NULL COMMENT '组名',
    `CREATE_TIME` datetime     NOT NULL COMMENT '创建时间',
    PRIMARY KEY (`GROUP_ID`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
insert into streamx.t_group values (1,'大数据组','2022-02-21 18:00:00');

DROP TABLE IF EXISTS `streamx`.`t_group_user`;
CREATE TABLE `streamx`.`t_group_user`
(
    `GROUP_ID`    bigint   NOT NULL COMMENT 'groupId',
    `USER_ID`     bigint   NOT NULL COMMENT 'userId',
    `CREATE_TIME` datetime NOT NULL COMMENT '创建时间',
    UNIQUE KEY `GROUP_USER` (`GROUP_ID`,`USER_ID`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

insert into streamx.t_group_user values (1,1,'2022-02-21 18:00:00');


ALTER TABLE `streamx`.`t_flink_app`
    ADD COLUMN `group_id` bigint not null default 0 comment '任务所属组';

ALTER TABLE `streamx`.`t_flink_project`
    ADD COLUMN `group_id` bigint not null default 0 comment '项目所属组';


update streamx.t_flink_app set t_flink_app.group_id=1 where group_id=0;

update streamx.t_flink_project set t_flink_project.group_id=1 where group_id=0;
