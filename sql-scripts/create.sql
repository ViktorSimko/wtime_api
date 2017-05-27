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
    `authority` varchar(50),
    FOREIGN KEY (`username`) REFERENCES `users`(`username`)
);