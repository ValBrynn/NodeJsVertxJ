CREATE DATABASE IF NOT EXISTS KthServer;

USE KthServer;

CREATE TABLE IF NOT EXISTS `Test` (
                                      `id` int(11) NOT NULL AUTO_INCREMENT,
                                      `value` varchar(255) DEFAULT NULL,
                                      PRIMARY KEY (`id`)
);