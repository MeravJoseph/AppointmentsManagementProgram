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
-- Definition of table `doctors`
--

DROP TABLE IF EXISTS `doctors`;
CREATE TABLE `doctors` (
  `num` int(10) unsigned NOT NULL auto_increment,
  `firstName` varchar(45) NOT NULL,
  `lastName` varchar(45) NOT NULL,
  `id` int(9) unsigned NOT NULL,
  `type` int(10) UNSIGNED NOT NULL,
  `clinic` int(9) UNSIGNED NOT NULL,
  `email` varchar(45) NOT NULL,
  `userName` varchar(45) NOT NULL,
  `password` varchar(45) NOT NULL,
  `permissions` int(1) NOT NULL,
  PRIMARY KEY  (`num`)
) ENGINE=InnoDB AUTO_INCREMENT=7790 DEFAULT CHARSET=latin1;


-- Dumping data for table `doctors`
--

/*!40000 ALTER TABLE `doctors` DISABLE KEYS */;
INSERT INTO `doctors` (`num`,`firstName`,`lastName`,`id`, `type`, `clinic`, `email`, `userName`, `password`, `permissions`) VALUES 
 (1,'Donna',		'Dresdner',	145929472, 0, 0, 'ddonna@good-health.com',		'ddonna',		'gooddoc1234',	1),
 (2,'Rudolf',		'Holland',	264901881, 1, 0, 'hrudolf@good-health.com',		'hrudolf',		'gooddoc1234',	1),
 (3,'Filipp',		'Gott',		181740907, 2, 0, 'gfilipp@good-health.com',		'gfilipp',		'gooddoc1234',	1),
 (4,'Heather',		'Hayward',	139641877, 3, 0, 'hheather@good-health.com',	'hheather',		'gooddoc1234',	1),
 (5,'Darwin', 		'Biermann',	500508900, 4, 0, 'bdarwin@good-health.com',		'bdarwin',		'gooddoc1234',	1),
 (6,'Asher', 		'Horn',		505204324, 5, 0, 'hasher@good-health.com',		'hasher',		'gooddoc1234',	1),
 (7,'Mariya', 		'Ayton',	903117327, 6, 0, 'amariya@good-health.com',		'amariya',		'gooddoc1234',	1),
 (8,'Hugh',			'Wang',		567892585, 7, 1, 'whugh@good-health.com',		'whugh',		'gooddoc1234',	1),
 (9,'James', 		'Mayes',	606456660, 0, 1, 'mjames@good-health.com',		'mjames',		'gooddoc1234',	1),
 (10,'Glenda', 		'Reiher',	875389081, 1, 1, 'rglenda@good-health.com',		'rglenda',		'gooddoc1234',	1),
 (11,'Gary',		'Yap',		867929472, 2, 1, 'ygary@good-health.com',		'ygary',		'gooddoc1234',	1),
 (12,'Boleslav',	'Jakeman',	799883026, 3, 1, 'jboleslav@good-health.com',	'jboleslav',	'gooddoc1234',	1),
 (13,'Bruce', 		'Ackerman',	562509822, 4, 1, 'abruce@good-health.com',		'abruce',		'gooddoc1234',	1),
 (14,'Terrell', 	'Mills',	751131438, 5, 1, 'mterrell@good-health.com',	'mterrell',		'gooddoc1234',	1),
 (15,'Kathleen', 	'Franke',	145920596, 6, 1, 'fkathleen@good-health.com',	'fkathleen',	'gooddoc1234',	1),
 (16,'Tyron', 		'Hewitt',	859086301, 7, 1, 'htyron@good-health.com',		'htyron',		'gooddoc1234',	1),
 (17,'Todd',		'Garnett',	840596529, 0, 2, 'gtodd@good-health.com',		'gtodd',		'gooddoc1234',	1),
 (18,'Tatiana', 	'Wembley',	950825679, 1, 2, 'wtatiana@good-health.com',	'wtatiana',		'gooddoc1234',	1),
 (19,'Nicole', 		'Acker', 	877823283, 2, 2, 'anicole@good-health.com',		'anicole',		'gooddoc1234',	1),
 (20,'Arvel',		'Fiedler',	393236078, 3, 2, 'farvel@good-health.com',		'farvel',		'gooddoc1234',	1),
 (21,'Kir',			'Radoslav',	994797540, 4, 2, 'rkir@good-health.com',		'rkir',			'gooddoc1234',	1),
 (22,'Adam',		'Mohller',	971565157, 5, 2, 'madam@good-health.com',		'madam',		'gooddoc1234',	1),
 (23,'Sebastian', 	'Baines',	813001169, 6, 2, 'bsebastian@good-health.com',	'bsebastian',	'gooddoc1234',	1),
 (24,'Gabriela', 	'Braun',	604251040, 7, 2, 'bgabriala@good-health.com',	'bgabriala',	'gooddoc1234',	1);
/*!40000 ALTER TABLE `doctors` ENABLE KEYS */;




/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
