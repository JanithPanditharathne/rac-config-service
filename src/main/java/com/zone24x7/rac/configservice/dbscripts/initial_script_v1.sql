
-- use database --
use ibraccs;

-- create rec engine table --
CREATE TABLE `rec_engine` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `config_type` varchar(45) DEFAULT NULL,
  `config_json` longtext DEFAULT NULL,
  PRIMARY KEY (`id`)
);


-- create algorithm table --
CREATE TABLE `algorithm` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `description` longtext DEFAULT NULL,
  `default_display_text` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
);