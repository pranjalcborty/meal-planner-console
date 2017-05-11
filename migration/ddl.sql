CREATE DATABASE meal_planner;
USE meal_planner;

CREATE TABLE items (
  item_id int(11) NOT NULL AUTO_INCREMENT,
  item_name varchar(20) DEFAULT NULL,
  PRIMARY KEY (item_id),
  UNIQUE KEY uk_item_name (item_name)
);

CREATE TABLE meal_item (
  meal_id int(11) NOT NULL,
  item_id int(11) NOT NULL,
  UNIQUE KEY uk_item_meal_id (meal_id, item_id)
);

CREATE TABLE meals (
  meal_id int(11) NOT NULL AUTO_INCREMENT,
  meal_type enum('BREAKFAST','LUNCH') DEFAULT NULL,
  day enum('SUN','MON','TUE','WED','THU') DEFAULT NULL,
  PRIMARY KEY (meal_id),
  UNIQUE KEY uk_day (meal_type, day),
  UNIQUE KEY uk_meal_day (meal_type, day)
);