CREATE DATABASE IF NOT EXISTS `book_db`;
USE `book_db`;

CREATE TABLE IF NOT EXISTS `authors` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `first_name` varchar(255) NOT NULL,
  `last_name` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
);

CREATE TABLE IF NOT EXISTS `publishers` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_an1ucpx8sw2qm194mlok8e5us` (`name`)
);

CREATE TABLE IF NOT EXISTS `books` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `cover_img` varchar(255) NOT NULL,
  `date_added` date NOT NULL,
  `isbn` varchar(13) NOT NULL,
  `language` varchar(255) DEFAULT NULL,
  `quantity` bigint(20) NOT NULL,
  `title` varchar(255) NOT NULL,
  `year_of_publication` smallint(6) NOT NULL,
  `publisher_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_kmucx27q9508j7ym5qvf1hsbk` (`cover_img`),
  UNIQUE KEY `UK_kibbepcitr0a3cpk3rfr7nihn` (`isbn`),
  KEY `FKayy5edfrqnegqj3882nce6qo8` (`publisher_id`),
  CONSTRAINT `FKayy5edfrqnegqj3882nce6qo8` FOREIGN KEY (`publisher_id`) REFERENCES `publishers` (`id`)
);

CREATE TABLE IF NOT EXISTS `book_author` (
  `book_id` bigint(20) NOT NULL,
  `author_id` bigint(20) NOT NULL,
  KEY `FKro54jqpth9cqm1899dnuu9lqg` (`author_id`),
  KEY `FK91ierknt446aaqnjl4uxjyls3` (`book_id`),
  CONSTRAINT `FK91ierknt446aaqnjl4uxjyls3` FOREIGN KEY (`book_id`) REFERENCES `books` (`id`),
  CONSTRAINT `FKro54jqpth9cqm1899dnuu9lqg` FOREIGN KEY (`author_id`) REFERENCES `authors` (`id`)
);

CREATE TABLE IF NOT EXISTS `genres` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_pe1a9woik1k97l87cieguyhh4` (`name`)
);

CREATE TABLE IF NOT EXISTS `tags` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_t48xdq560gs3gap9g7jg36kgc` (`name`)
);

CREATE TABLE IF NOT EXISTS `book_genre` (
  `book_id` bigint(20) NOT NULL,
  `genre_id` bigint(20) NOT NULL,
  PRIMARY KEY (`book_id`,`genre_id`),
  KEY `FKnkh6m50njl8771p0ll3lylqp2` (`genre_id`),
  CONSTRAINT `FKnkh6m50njl8771p0ll3lylqp2` FOREIGN KEY (`genre_id`) REFERENCES `genres` (`id`),
  CONSTRAINT `FKq0f88ptislu8lv230mvgonssl` FOREIGN KEY (`book_id`) REFERENCES `books` (`id`)
);

CREATE TABLE IF NOT EXISTS `book_tag` (
  `book_id` bigint(20) NOT NULL,
  `tag_id` bigint(20) NOT NULL,
  PRIMARY KEY (`book_id`,`tag_id`),
  KEY `FK6h6a61im6dbd5759issu0jfd1` (`tag_id`),
  CONSTRAINT `FK6h6a61im6dbd5759issu0jfd1` FOREIGN KEY (`tag_id`) REFERENCES `tags` (`id`),
  CONSTRAINT `FKbacbwwmfdh1bwsdgs9pycw9rr` FOREIGN KEY (`book_id`) REFERENCES `books` (`id`)
);