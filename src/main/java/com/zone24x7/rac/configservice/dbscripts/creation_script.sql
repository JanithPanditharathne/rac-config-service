
-- use database --
use ibraccs;

-- create algorithm table --
CREATE TABLE `algorithm` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `description` longtext DEFAULT NULL,
  `default_display_text` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
);