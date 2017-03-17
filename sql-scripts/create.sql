CREATE DATABASE IF NOT EXISTS `wtime`;
USE `wtime`;

DROP TABLE IF EXISTS `users`;
DROP TABLE IF EXISTS `authorities`;

CREATE TABLE `users` (
    `username` varchar(50),
    `password` varchar(50),
    `enabled` bool,
    PRIMARY KEY (`username`)
);

CREATE TABLE `authorities`(
	`username` varchar(50),
    `authority` varchar(50)
);

DROP TABLE IF EXISTS `project`;
DROP TABLE IF EXISTS `task`;
DROP TABLE IF EXISTS `work_interval`;

CREATE TABLE `project` (
	`id` int(5) NOT NULL AUTO_INCREMENT,
    `title` varchar(50),
    `user` varchar(50),
    PRIMARY KEY (`id`)
);