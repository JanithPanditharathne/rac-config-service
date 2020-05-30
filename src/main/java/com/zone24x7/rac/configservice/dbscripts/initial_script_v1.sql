-- database --
USE ibraccs;



-- create rec_engine table --
CREATE TABLE `rec_engine` (
`id` int(11) NOT NULL AUTO_INCREMENT,
`config_type` varchar(45) DEFAULT NULL,
`config_json` longtext,
PRIMARY KEY(`id`)
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
`combine_enabled` tinyint(1) DEFAULT 0,
`combine_display_text` varchar(255) DEFAULT NULL,
PRIMARY KEY (`id`)
);


-- create bundle_algorithm association table --
CREATE TABLE `bundle_algorithm` (
`id` int(11) NOT NULL AUTO_INCREMENT,
`bundle_id` int(11) NOT NULL,
`algorithm_id` int(11) NOT NULL,
`custom_display_text` varchar(255) DEFAULT NULL,
`rank` int(11) DEFAULT NULL,
PRIMARY KEY (`id`),
KEY `fk_bundle_id_idx` (`bundle_id`),
KEY `fk_algorithm_id_idx` (`algorithm_id`),
CONSTRAINT `fk_algorithm_id` FOREIGN KEY (`algorithm_id`) REFERENCES `algorithm` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
CONSTRAINT `fk_bundle_id` FOREIGN KEY (`bundle_id`) REFERENCES `bundle` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
);


-- create rec table --
CREATE TABLE `rec` (
`id` int(11) NOT NULL AUTO_INCREMENT,
`name` varchar(255) DEFAULT NULL,
`bundle_id` int(11) NOT NULL,
PRIMARY KEY(`id`),
KEY `fk_bundle_idx` (`bundle_id`),
CONSTRAINT `fk_bundle_id1` FOREIGN KEY (`bundle_id`) REFERENCES `bundle` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
);




-- create channel table --
CREATE TABLE `channel` (
`id` int(11) NOT NULL AUTO_INCREMENT,
`name` varchar(255) DEFAULT NULL,
PRIMARY KEY(`id`)
);

-- create page table --
CREATE TABLE `page` (
`id` int(11) NOT NULL AUTO_INCREMENT,
`name` varchar(255) DEFAULT NULL,
PRIMARY KEY(`id`)
);

-- create placeholder table --
CREATE TABLE `placeholder` (
`id` int(11) NOT NULL AUTO_INCREMENT,
`name` varchar(255) DEFAULT NULL,
PRIMARY KEY(`id`)
);





-- create rec_slot table --
CREATE TABLE `rec_slot` (
`id` int(11) NOT NULL AUTO_INCREMENT,
`channel_id` int(11) NOT NULL,
`page_id` int(11) NOT NULL,
`placeholder_id` int(11) NOT NULL,
`rec_id` int(11) NOT NULL,
PRIMARY KEY(`id`),
KEY `fk_channel_idx` (`channel_id`),
KEY `fk_page_idx` (`page_id`),
KEY `fk_placeholder_idx` (`placeholder_id`),
KEY `fk_rec_idx` (`rec_id`),
CONSTRAINT `fk_channel_id` FOREIGN KEY (`channel_id`) REFERENCES `channel` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
CONSTRAINT `fk_page_id` FOREIGN KEY (`page_id`) REFERENCES `page` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
CONSTRAINT `fk_placeholder_id` FOREIGN KEY (`placeholder_id`) REFERENCES `placeholder` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
CONSTRAINT `fk_rec_id` FOREIGN KEY (`rec_id`) REFERENCES `rec` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
);



-- create rule table --
CREATE TABLE `rule` (
`id` int(11) NOT NULL AUTO_INCREMENT,
`name` varchar(255) DEFAULT NULL,
`type` varchar(255) DEFAULT NULL,
`is_global` tinyint(1) DEFAULT 0,
`matching_condition_json` longtext DEFAULT NULL,
`action_condition_json` longtext DEFAULT NULL,
`matching_condition` longtext DEFAULT NULL,
`action_condition` longtext DEFAULT NULL,
PRIMARY KEY(`id`)
);




-- create rec_slot_rule association table --
CREATE TABLE `rec_slot_rule` (
`id` int(11) NOT NULL AUTO_INCREMENT,
`rec_slot_id` int(11) NOT NULL,
`rule_id` int(11) NOT NULL,
PRIMARY KEY(`id`),
KEY `fk_rule_idx` (`rule_id`),
KEY `fk_rec_slot_idx` (`rec_slot_id`),
CONSTRAINT `fk_rule_id` FOREIGN KEY (`rule_id`) REFERENCES `rule` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
CONSTRAINT `fk_rec_slot_id` FOREIGN KEY (`rec_slot_id`) REFERENCES `rec_slot` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
);




-- create action_trace table --
CREATE TABLE `action_trace` (
`id` int(11) NOT NULL AUTO_INCREMENT,
`user_id` varchar(255) DEFAULT NULL,
`action` varchar(255) DEFAULT NULL,
`value` longtext DEFAULT NULL,
`timestamp` datetime DEFAULT NULL,
PRIMARY KEY(`id`)
);




-- create metadata table --
CREATE TABLE `metadata` (
`id` int(11) NOT NULL AUTO_INCREMENT,
`type` varchar(255) DEFAULT NULL,
`name` varchar(255) DEFAULT NULL,
PRIMARY KEY(`id`)
);










-- insert initial data in to rec_engine table --
INSERT INTO `rec_engine`(`id`, `config_type`)
VALUES (1, 'bundles'), (2, 'rules'), (3, 'recs'), (4, 'recSlots');








