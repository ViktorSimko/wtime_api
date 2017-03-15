CREATE DATABASE IF NOT EXISTS `wtime`;
USE `wtime`;

DROP TABLE IF EXISTS `project`;
DROP TABLE IF EXISTS `task`;
DROP TABLE IF EXISTS `work_interval`;

CREATE TABLE `project` (
	`id` int(5) NOT NULL AUTO_INCREMENT,
    `title` varchar(50),
    PRIMARY KEY (`id`)
)