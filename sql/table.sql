CREATE TABLE `user` (
                        `id` int NOT NULL AUTO_INCREMENT COMMENT 'ID',
                        `user_name` varchar(50) NOT NULL COMMENT '姓名',
                        `password` varchar(50) NOT NULL COMMENT '密码',
                        `salt` varchar(18) NOT NULL COMMENT '盐',
                        `date_time` datetime DEFAULT NULL COMMENT '预约时间',
                        `create_time` datetime DEFAULT NULL COMMENT '创建时间',
                        `update_time` datetime DEFAULT NULL COMMENT '修改时间',
                        `update_name` varchar(50) DEFAULT NULL COMMENT '修改人',
                        PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci


CREATE TABLE `admin` (
                         `id` int NOT NULL AUTO_INCREMENT COMMENT 'ID',
                         `admin_name` varchar(50) NOT NULL COMMENT '姓名',
                         `password` varchar(50) NOT NULL COMMENT '密码',
                         `create_time` datetime DEFAULT NULL COMMENT '创建时间',
                         `update_time` datetime DEFAULT NULL COMMENT '修改时间',
                         `update_name` varchar(50) DEFAULT NULL COMMENT '修改人',
                         PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='访客管理员'


CREATE TABLE `top_admin` (
                             `id` int NOT NULL AUTO_INCREMENT COMMENT 'ID',
                             `admin_name` varchar(50) NOT NULL COMMENT '姓名',
                             `password` varchar(50) NOT NULL COMMENT '密码',
                             `create_time` datetime DEFAULT NULL COMMENT '创建时间',
                             `update_time` datetime DEFAULT NULL COMMENT '修改时间',
                             `update_name` varchar(50) DEFAULT NULL COMMENT '修改人',
                             PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='系统管理员'

