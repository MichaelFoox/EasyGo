SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema EasyGoDB
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema EasyGoDB
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `EasyGoDB` DEFAULT CHARACTER SET utf8 ;
USE `EasyGoDB` ;


-- -----------------------------------------------------
-- Table `EasyGoDB`.`users`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `EasyGoDB`.`users` (
  `login` VARCHAR(45) NOT NULL,
  `password` VARCHAR(45) NOT NULL,
  `name` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`login`))
ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table `EasyGoDB`.`users`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `EasyGoDB`.`users` (
  `login` VARCHAR(45) NOT NULL,
  `password` VARCHAR(45) NOT NULL,
  `name` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`login`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `EasyGoDB`.`gomaps`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `EasyGoDB`.`gomaps` (
  `map_id` int not null primary key auto_increment,
  `owner_login` VARCHAR(45) NOT NULL,
  `name` VARCHAR(45) NOT NULL,
  `tags` VARCHAR(45) NOT NULL,
  `map_attributes` VARCHAR(45) NOT NULL,
  `is_private` TINYINT(1) NULL,
  CONSTRAINT `owner_login`
    FOREIGN KEY (`owner_login`)
    REFERENCES `EasyGoDB`.`users` (`login`)
    ON DELETE NO ACTION
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `EasyGoDB`.`points`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `EasyGoDB`.`points` (
  `point_id` int not null primary key auto_increment,
  `x` FLOAT NOT NULL,
  `y` FLOAT NOT NULL,
  `name` VARCHAR(60) NOT NULL,
  `map_id` int NOT NULL,
  `attribute_values` VARCHAR(255) NULL,
  CONSTRAINT `id`
    FOREIGN KEY (`map_id`)
    REFERENCES `EasyGoDB`.`gomaps` (`map_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;

-- -----------------------------------------------------
-- Data for table `EasyGoDB`.`users`
-- -----------------------------------------------------
START TRANSACTION;
USE `EasyGoDB`;
INSERT INTO `EasyGoDB`.`users` (`login`, `password`, `name`) VALUES ('olhaR', '0506909637', 'olha');
INSERT INTO `EasyGoDB`.`users` (`login`, `password`, `name`) VALUES ('olehG', '1234', 'oleg');
INSERT INTO `EasyGoDB`.`users` (`login`, `password`, `name`) VALUES ('aleksS', '1234', 'alesks');
INSERT INTO `EasyGoDB`.`users` (`login`, `password`, `name`) VALUES ('AnnaN', '1234', 'anna');
INSERT INTO `EasyGoDB`.`users` (`login`, `password`, `name`) VALUES ('IraP', '1234', 'ira');
INSERT INTO `EasyGoDB`.`users` (`login`, `password`, `name`) VALUES ('AnastasiaF', '1234', 'anastasia');
INSERT INTO `EasyGoDB`.`users` (`login`, `password`, `name`) VALUES ('IhorK', '1234', 'ihor');

COMMIT;

-- -----------------------------------------------------
-- Data for table `EasyGoDB`.`gomaps`
-- -----------------------------------------------------
START TRANSACTION;
USE `EasyGoDB`;
INSERT INTO `EasyGoDB`.`gomaps` (`map_id`, `owner_login`, `name`, `tags`, `map_attributes`, `is_private`) VALUES ('0', '0', 'ATMs', 'ATMs', 'look for this ATMs', true);
INSERT INTO `EasyGoDB`.`gomaps` (`owner_login`, `name`, `tags`, `map_attributes`, `is_private`) VALUES ('0', 'Coffee', 'Cup', 'look for this coffee', true);
INSERT INTO `EasyGoDB`.`gomaps` (`owner_login`, `name`, `tags`, `map_attributes`, `is_private`) VALUES ('2', 'WaterAutomats', 'Water', 'look for this water', false);
INSERT INTO `EasyGoDB`.`gomaps` (`map_id`, `owner_login`, `name`, `tags`, `map_attributes`, `is_private`) VALUES ('0', 'olhaR', 'ATMs', 'ATMs', 'look for this ATMs', true);
INSERT INTO `EasyGoDB`.`gomaps` ( `owner_login`, `name`, `tags`, `map_attributes`, `is_private`) VALUES ( 'olhaR', 'Coffee', 'Cup', 'look for this coffee', true);
INSERT INTO `EasyGoDB`.`gomaps` ( `owner_login`, `name`, `tags`, `map_attributes`, `is_private`) VALUES ( 'olehG', 'WaterAutomats', 'Water', 'look for this water', false);

COMMIT;


-- -----------------------------------------------------
-- Data for table `EasyGoDB`.`points`
-- -----------------------------------------------------
START TRANSACTION;
USE `EasyGoDB`;
INSERT INTO `EasyGoDB`.`points` (`point_id`, `x`, `y`, `name`, `map_id`, `attribute_values`) VALUES ('0', 21331.3, 12321.2, 'Privat', '0', 'ATM');
INSERT INTO `EasyGoDB`.`points` (`x`, `y`, `name`, `map_id`, `attribute_values`) VALUES ( 12321, 2342, 'GasBank', '0', 'ATMGasBank');
INSERT INTO `EasyGoDB`.`points` ( `x`, `y`, `name`, `map_id`, `attribute_values`) VALUES ( 1233, 3453, 'UniCredit', '0', 'ATMUniCredit');
INSERT INTO `EasyGoDB`.`points` ( `x`, `y`, `name`, `map_id`, `attribute_values`) VALUES (1231, 3245, 'UniCredit', '0', 'ATMUniCredit2');
INSERT INTO `EasyGoDB`.`points` ( `x`, `y`, `name`, `map_id`, `attribute_values`) VALUES ( 74565, 3456, 'GasBank', '0', 'ATMGasBank');
INSERT INTO `EasyGoDB`.`points` (`x`, `y`, `name`, `map_id`, `attribute_values`) VALUES (2342, 2354, 'Privat', '0', 'ATMPrivat');
INSERT INTO `EasyGoDB`.`points` ( `x`, `y`, `name`, `map_id`, `attribute_values`) VALUES ( 2524, 2342, 'Privat', '0', 'ATMPrivat');
INSERT INTO `EasyGoDB`.`points` ( `x`, `y`, `name`, `map_id`, `attribute_values`) VALUES ( 6546, 2355, 'Privat', '0', 'ATMPrivat');
INSERT INTO `EasyGoDB`.`points` ( `x`, `y`, `name`, `map_id`, `attribute_values`) VALUES ( 342, 23424, 'Caffka', '1', 'coff');
INSERT INTO `EasyGoDB`.`points` ( `x`, `y`, `name`, `map_id`, `attribute_values`) VALUES ( 324, 1231, 'CupCoff', '1', 'cococogood');
INSERT INTO `EasyGoDB`.`points` ( `x`, `y`, `name`, `map_id`, `attribute_values`) VALUES ( 12312, 1231, 'CoffeeMat', '1', 'good capuchino');
INSERT INTO `EasyGoDB`.`points` (`x`, `y`, `name`, `map_id`, `attribute_values`) VALUES ( 23123, 1233, 'CoffeeGo', '1', 'just try');
INSERT INTO `EasyGoDB`.`points` ( `x`, `y`, `name`, `map_id`, `attribute_values`) VALUES ( 1231, 12312, 'Tea&Coffee', '1', NULL);
INSERT INTO `EasyGoDB`.`points` ( `x`, `y`, `name`, `map_id`, `attribute_values`) VALUES ( 4324, 23424, 'Caffka', '1', 'caaffka2');
INSERT INTO `EasyGoDB`.`points` (`x`, `y`, `name`, `map_id`, `attribute_values`) VALUES ( 1231, 12312, 'Caffka', '1', 'caaffka3');
INSERT INTO `EasyGoDB`.`points` ( `x`, `y`, `name`, `map_id`, `attribute_values`) VALUES ( 1231, 12313, 'StarBucks', '1', 'For your instagrsm');
INSERT INTO `EasyGoDB`.`points` ( `x`, `y`, `name`, `map_id`, `attribute_values`) VALUES ( 4563, 3453, 'StarBucks', '1', 'more photos');
INSERT INTO `EasyGoDB`.`points`( `x`, `y`, `name`, `map_id`, `attribute_values`) VALUES ( 2341, 3242, 'CoffeeMat', '1', 'coffemat2 ');
INSERT INTO `EasyGoDB`.`points` (`x`, `y`, `name`, `map_id`, `attribute_values`) VALUES ( 1231, 4432, 'filin', '1', 'fill your morning');
INSERT INTO `EasyGoDB`.`points` (`x`, `y`, `name`, `map_id`, `attribute_values`) VALUES ( 321, 23423, 'coff3', '1', 'IT caffe');
INSERT INTO `EasyGoDB`.`points` ( `x`, `y`, `name`, `map_id`, `attribute_values`) VALUES (4342, 2342, 'water', '2', NULL);
INSERT INTO `EasyGoDB`.`points` ( `x`, `y`, `name`, `map_id`, `attribute_values`) VALUES ( 123123, 12312, 'water', '2', '73 k/l');
INSERT INTO `EasyGoDB`.`points` (`x`, `y`, `name`, `map_id`, `attribute_values`) VALUES ( 34232, 23424, 'water1', '2', 'allways water');
INSERT INTO `EasyGoDB`.`points` ( `x`, `y`, `name`, `map_id`, `attribute_values`) VALUES ( 23234, 2342, '2', '2', NULL);
INSERT INTO `EasyGoDB`.`points` (`x`, `y`, `name`, `map_id`, `attribute_values`) VALUES ( 2342, 32423, 'water3', '2', '77 k/l');
INSERT INTO `EasyGoDB`.`points` (`x`, `y`, `name`, `map_id`, `attribute_values`) VALUES ( 3242, 23423, 'water4', '2', '71 k/l');
INSERT INTO `EasyGoDB`.`points` ( `x`, `y`, `name`, `map_id`, `attribute_values`) VALUES ( 23423, 234325, 'water5', '2', '77 k/l');
INSERT INTO `EasyGoDB`.`points` (`x`, `y`, `name`, `map_id`, `attribute_values`) VALUES ( 234234, 23423, 'water6', '2', '70 k/l');
INSERT INTO `EasyGoDB`.`points` ( `x`, `y`, `name`, `map_id`, `attribute_values`) VALUES (23434, 23423, 'water7', '2', NULL);
INSERT INTO `EasyGoDB`.`points` (`x`, `y`, `name`, `map_id`, `attribute_values`) VALUES ( 2342, 234323, 'water8', '2', '72 k/l');

COMMIT;
