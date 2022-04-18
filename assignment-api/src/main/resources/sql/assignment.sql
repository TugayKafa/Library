CREATE DATABASE IF NOT EXISTS `assignment_db`;
USE `assignment_db`;


CREATE TABLE IF NOT EXISTS `assignments` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `assignment_date` date NOT NULL,
  `book_id` bigint(20) NOT NULL,
  `period` varchar(12) DEFAULT 'ONE_WEEK',
  `quantity` int(11) DEFAULT NULL,
  `return_date` date NOT NULL,
  `user_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`)
);

CREATE TABLE IF NOT EXISTS `assignment_history` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `h_assigned_date` date NOT NULL,
  `h_book_id` bigint(20) NOT NULL,
  `h_returned_date` date NOT NULL,
  `h_period` varchar(12) DEFAULT 'ONE_WEEK',
  `h_quantity` int(11) DEFAULT NULL,
  `h_user_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`)
);