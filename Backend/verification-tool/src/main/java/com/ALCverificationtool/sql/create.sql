DROP TABLE IF EXISTS `english_current`;

CREATE TABLE `english_current` (
  `approved` tinyint(4) NOT NULL,
  `file_name` varchar(255) DEFAULT NULL,
  `file_notes` varchar(255) DEFAULT NULL,
  `folder_path` varchar(255) DEFAULT NULL,
  `key_name` varchar(255) NOT NULL,
  `key_new` tinyint(4) NOT NULL,
  `key_note` varchar(255) DEFAULT NULL,
  `key_variant` varchar(2000) DEFAULT NULL,
  `section_id` varchar(255) DEFAULT NULL,
  `section_note` varchar(255) DEFAULT NULL,
  `key_id` bigint(20) NOT NULL,
  PRIMARY KEY (`key_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;