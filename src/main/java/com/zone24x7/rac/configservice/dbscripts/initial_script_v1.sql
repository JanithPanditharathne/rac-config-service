
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


-- create bundle table --
CREATE TABLE `bundle` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `default_limit` int(11) DEFAULT NULL,
  `combine_enabled` TINYINT(1) DEFAULT 0,
  `combine_display_text` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
);


-- create bundle_algorithm table --
CREATE TABLE `bundle_algorithm` (
    `id` int(11) NOT NULL AUTO_INCREMENT,
    `bundle_id` int(11) NOT NULL,
    `algorithm_id` int(11) NOT NULL,
    `custom_display_text` varchar(255) DEFAULT NULL,
    `rank` decimal(10,0) DEFAULT NULL,
    PRIMARY KEY (`id`),
    KEY `fk_bundle_id_idx` (`bundle_id`),
    KEY `fk_algorithm_id_idx` (`algorithm_id`),
    CONSTRAINT `fk_algorithm_id` FOREIGN KEY (`algorithm_id`) REFERENCES `algorithm` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
    CONSTRAINT `fk_bundle_id` FOREIGN KEY (`bundle_id`) REFERENCES `bundle` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
);


-- create rec table --
CREATE TABLE `rec` (
   `id` int(11) NOT NULL AUTO_INCREMENT,
   `name` varchar(255) NOT NULL,
   `bundle_id` int(11) NOT NULL,
   PRIMARY KEY (`id`),
   KEY `fk_bundle_idx` (`bundle_id`),
   CONSTRAINT `fk_bundle_id1` FOREIGN KEY (`bundle_id`) REFERENCES `bundle` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
);