CREATE DATABASE user_db CHARACTER SET 'utf8' COLLATE 'utf8_general_ci';

use user_db;

CREATE TABLE t_user (
	id BIGINT(20) NOT NULL COMMENT '用户id',
	username VARCHAR(64) NOT NULL,
	`password` VARCHAR(64) NOT NULL,
	`fullname` VARCHAR(255) NOT NULL COMMENT '用户姓名',
	`mobile` VARCHAR(11) DEFAULT NULL COMMENT '手机号',
	PRIMARY KEY(id) USING BTREE
) ENGINE=INNODB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;
INSERT INTO `user_db`.`t_user`(`id`, `username`, `password`, `fullname`, `mobile`) VALUES (1, 'lisi', '$2a$10$Y6oMzwqbNyF8pYGaXbMPVeNCD1.LrwS4ZxQ0jl9BzFrp9q6NG.s42', '李四', '13866668888');

CREATE TABLE t_role (
	id VARCHAR(32) NOT NULL,
	role_name VARCHAR(255) DEFAULT NULL,
	description VARCHAR(255) DEFAULT NULL,
	create_time datetime  DEFAULT NULL,
	update_time datetime  DEFAULT NULL,
	`status` char(1) NOT NULL,
	PRIMARY KEY (id),
	UNIQUE KEY `unique_role_name` (role_name)
) ENGINE = INNODB DEFAULT CHARSET=utf8;
INSERT INTO `user_db`.`t_role`(`id`, `role_name`, `description`, `create_time`, `update_time`, `status`) VALUES ('1', '管理员', NULL, NULL, NULL, '1');

CREATE TABLE t_user_role (
	user_id VARCHAR(32) NOT NULL,
	role_id VARCHAR(32) NOT NULL,
	create_time datetime  DEFAULT NULL,
	creator varchar(64) NOT NULL,
	PRIMARY KEY (user_id, role_id)
) ENGINE = INNODB DEFAULT CHARSET=utf8;
INSERT INTO `user_db`.`t_user_role`(`user_id`, `role_id`, `create_time`, `creator`) VALUES ('1', '1', NULL, 'lisi');

CREATE TABLE t_permission (
	id VARCHAR(32) NOT NULL,
	`code` VARCHAR(32) NOT NULL COMMENT '权限标识符',
	description VARCHAR(64) NOT NULL COMMENT '描述',
	`url` VARCHAR(128) NOT NULL COMMENT '请求地址',
	PRIMARY KEY (id)
) ENGINE = INNODB DEFAULT CHARSET=utf8;
INSERT INTO `user_db`.`t_permission`(`id`, `code`, `description`, `url`) VALUES ('1', 'p1', '测试资源1', '/r/r1');
INSERT INTO `user_db`.`t_permission`(`id`, `code`, `description`, `url`) VALUES ('2', 'p2', '测试资源2', '/r/r2');

CREATE TABLE t_role_permission (
	role_id VARCHAR(32) NOT NULL,
	permission_id VARCHAR(32) NOT NULL,
	PRIMARY KEY (role_id, permission_id)
) ENGINE = INNODB DEFAULT CHARSET=utf8;
INSERT INTO `user_db`.`t_role_permission`(`role_id`, `permission_id`) VALUES ('1', '1');
INSERT INTO `user_db`.`t_role_permission`(`role_id`, `permission_id`) VALUES ('1', '2');

-- 查询用户权限
SELECT * FROM t_permission WHERE id IN (
SELECT permission_id FROM t_role_permission WHERE role_id IN (
SELECT role_id FROM t_user_role WHERE user_id='1')
)

-- 客户端信息和授权码存入数据库中需要的表
CREATE TABLE `oauth_client_details` (
  `client_id` varchar(255) NOT NULL COMMENT '客户端标识',
  `resource_ids` varchar(255) DEFAULT NULL COMMENT '接入资源列表',
  `client_secret` varchar(255) DEFAULT NULL COMMENT '客户端秘钥',
  `scope` varchar(255) DEFAULT NULL,
	`authorized_grant_types` varchar(255) DEFAULT NULL,
	`web_server_redirect_uri` varchar(255) DEFAULT NULL,
	`authorities` varchar(255) DEFAULT NULL,
	`access_token_validity` int(255) DEFAULT NULL,
	`refresh_token_validity` int(255) DEFAULT NULL,
	`additional_information` longtext DEFAULT NULL,
	`create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
	`archived` tinyint(4) DEFAULT NULL,
	`trusted` tinyint(4) DEFAULT NULL,
	`autoapprove` VARCHAR(255) DEFAULT NULL,
  PRIMARY KEY (`client_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='接入客户端信息';

CREATE TABLE `oauth_code` (
	`create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
	`code` VARCHAR(255) DEFAULT NULL,
	`authentication` blob NULL ,
	 INDEX `code_index`(`code`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='授权码信息';

-- 测试数据
INSERT INTO `oauth_client_details`(`client_id`, `resource_ids`, `client_secret`, `scope`, `authorized_grant_types`, `web_server_redirect_uri`, `authorities`, `access_token_validity`, `refresh_token_validity`, `additional_information`, `create_time`, `archived`, `trusted`, `autoapprove`) VALUES ('c1', 'res1', '$2a$10$cWycKkqF4YUHj852cdaK0OMqiblb4vwGfgiKgwU8IciA6qix9L7Ay', 'ROLE_ADMIN,ROLE_USER,ROLE_API', 'authorization_code,password,client_credentials,implicit,refresh_token', 'http://www.baidu.com', NULL, NULL, NULL, NULL, '2019-12-08 08:28:45', NULL, NULL, NULL);
