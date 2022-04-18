CREATE DATABASE IF NOT EXISTS `user_db`;
USE `user_db`;

CREATE TABLE IF NOT EXISTS `addresses` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `city` varchar(50) DEFAULT NULL,
  `country` varchar(35) DEFAULT NULL,
  `street` varchar(95) DEFAULT NULL,
  PRIMARY KEY (`id`)
);

CREATE TABLE IF NOT EXISTS `users` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `egn` varchar(10) NOT NULL,
  `email` varchar(255) NOT NULL,
  `first_name` varchar(255) NOT NULL,
  `last_name` varchar(255) NOT NULL,
  `status` varchar(10) DEFAULT 'ACTIVE',
  `address_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_jlqd3jclx3u4qv3a40sc4rbee` (`egn`),
  KEY `FKe8vydtk7hf0y16bfm558sywbb` (`address_id`),
  CONSTRAINT `FKe8vydtk7hf0y16bfm558sywbb` FOREIGN KEY (`address_id`) REFERENCES `addresses` (`id`)
);