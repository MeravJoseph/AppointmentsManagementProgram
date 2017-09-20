-- MySQL Administrator dump 1.4
--
-- ------------------------------------------------------
-- Server version	5.0.67-communipatientspatientsty-nt


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;


--
-- Create schema test
--

CREATE DATABASE IF NOT EXISTS test;
USE test;

--
-- Definition of table `patients`
--

DROP TABLE IF EXISTS `patients`;
CREATE TABLE `patients` (
  `num` int(10) unsigned NOT NULL auto_increment,
  `firstName` varchar(45) NOT NULL,
  `lastName` varchar(45) NOT NULL,
  `id` int(9) unsigned NOT NULL,
  `email` varchar(45) NOT NULL,
  `userName` varchar(45) NOT NULL,
  `password` varchar(45) NOT NULL,
  `permissions` int(1) NOT NULL,
  PRIMARY KEY  (`num`)
) ENGINE=InnoDB AUTO_INCREMENT=7790 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `patients`
--

/*!40000 ALTER TABLE `patients` DISABLE KEYS */;
INSERT INTO `patients` (`num`,`firstName`,`lastName`,`id`, `email`, `userName`, `password`, `permissions`) VALUES 
 (1,'Isadora',	'West',			578245979,	'wisadora@gmail.com',	'wisadora',		'good1234',	0),
 (2,'Warren',	'Sydney',		830664709,	'swarren@gmail.com',	'swarren',		'good1234',	0),
 (3,'Ingram',	'Kron',			342231740,	'kingram@gmail.com',	'kingram',		'good1234',	0),
 (4,'Wolfram',	'Austin',		465662225,	'awolfram@gmail.com',	'awolfram',		'good1234',	0),
 (5,'Pamella',	'Mutton',		391162125,	'mpamella@gmail.com',	'mpamella',		'good1234',	0),
 (6,'Richard',	'Feynman',		479337748,	'frichard@gmail.com',	'frichard',		'good1234',	0),
 (7,'Zackery',	'Mathews',		412462066,	'mzackery@gmail.com',	'mzackery',		'good1234',	0),
 (8,'Gustav',	'Gottfried',	945880068,	'ggustav@gmail.com',	'ggustav',		'good1234',	0),
 (9,'Heinrich',	'Holzknecht',	215672403,	'hheinrich@gmail.com',	'hheinrich',	'good1234',	0),
 (10,'Elvira',	'Strand',		190883978,	'selvira@gmail.com',	'selvira',		'good1234',	0),
 (11,'Herbert',	'Dallas',		158022401,	'dherbert@gmail.com',	'dherbert',		'good1234',	0),
 (12,'Renee',	'Boston',		465359018,	'brenee@gmail.com',	    'brenee',		'good1234',	0),
 (13,'Oskar', 	'Rimmer',		332869022,	'roskar@gmail.com',	    'roskar',		'good1234',	0);
/*!40000 ALTER TABLE `patients` ENABLE KEYS */;




/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
