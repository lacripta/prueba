-- MySQL dump 10.13  Distrib 5.7.12, for Win64 (x86_64)
--
-- Host: localhost    Database: elibom
-- ------------------------------------------------------
-- Server version	5.7.9

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `account`
--

DROP TABLE IF EXISTS `account`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `account` (
  `id_account` bigint(20) NOT NULL AUTO_INCREMENT,
  `creation_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `modification_time` datetime DEFAULT NULL,
  `credits_account` decimal(19,2) DEFAULT NULL,
  `name_account` varchar(70) NOT NULL,
  `taxid_account` varchar(20) DEFAULT NULL,
  `type_account` tinyint(4) NOT NULL,
  PRIMARY KEY (`id_account`),
  KEY `fk_account_taxes_idx` (`taxid_account`),
  KEY `fk_account_type_idx` (`type_account`),
  CONSTRAINT `fk_account_taxes` FOREIGN KEY (`taxid_account`) REFERENCES `taxes` (`id_taxes`) ON UPDATE CASCADE,
  CONSTRAINT `fk_account_type` FOREIGN KEY (`type_account`) REFERENCES `account_type` (`id_accounttype`) ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `account`
--

LOCK TABLES `account` WRITE;
/*!40000 ALTER TABLE `account` DISABLE KEYS */;
INSERT INTO `account` VALUES (1,'2016-09-09 15:25:55',NULL,100.00,'test account','COP_IVA',1);
/*!40000 ALTER TABLE `account` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `account_type`
--

DROP TABLE IF EXISTS `account_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `account_type` (
  `id_accounttype` tinyint(4) NOT NULL,
  `description_accounttype` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`id_accounttype`),
  UNIQUE KEY `description_accounttype` (`description_accounttype`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `account_type`
--

LOCK TABLES `account_type` WRITE;
/*!40000 ALTER TABLE `account_type` DISABLE KEYS */;
INSERT INTO `account_type` VALUES (2,'POSTPAID'),(1,'PREPAID');
/*!40000 ALTER TABLE `account_type` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `credits_movement`
--

DROP TABLE IF EXISTS `credits_movement`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `credits_movement` (
  `id_creditsmovement` bigint(20) NOT NULL AUTO_INCREMENT,
  `creation_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `credits_count` double DEFAULT NULL,
  `description_creditsmovement` varchar(255) DEFAULT NULL,
  `account_creditsmovement` bigint(20) DEFAULT NULL,
  `id_products` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id_creditsmovement`),
  KEY `fk_cr_movement_account_idx` (`account_creditsmovement`),
  KEY `fk_products_idx` (`id_products`),
  CONSTRAINT `fk_cr_movement_account` FOREIGN KEY (`account_creditsmovement`) REFERENCES `account` (`id_account`) ON UPDATE CASCADE,
  CONSTRAINT `fk_products` FOREIGN KEY (`id_products`) REFERENCES `products_rate` (`id_products`) ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `credits_movement`
--

LOCK TABLES `credits_movement` WRITE;
/*!40000 ALTER TABLE `credits_movement` DISABLE KEYS */;
INSERT INTO `credits_movement` VALUES (5,'2016-09-09 17:06:30',123,'qweqwe',1,1);
/*!40000 ALTER TABLE `credits_movement` ENABLE KEYS */;
UNLOCK TABLES;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_ALL_TABLES,NO_AUTO_CREATE_USER' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER `elibom`.`credits_movement_AFTER_INSERT` AFTER INSERT ON `credits_movement` FOR EACH ROW
BEGIN
	insert into invoice (due_date,issue_date,total_invoice,taxes_invoice,account_invoice,taxid_invoice) 
	select due_date,issue_date,(rate * credits_count) as total_invoice,
		  ((rate * credits_count) * (taxes / 100)) as taxes_invoice,
		  id_account, taxid_account 
	from (
	SELECT a.credits_count,b.taxid_account, b.id_account,b.name_account,b.type_account,current_date() as issue_date,
	   (select date_add(current_date(), interval duedays_rate day) from products_rate where id_productsrate = a.id_products) due_date,
	   (select value_taxes from taxes where id_taxes = b.taxid_account) taxes,
	   (select value_rate from products_rate where id_productsrate = a.id_products) rate
	FROM elibom.credits_movement a
	left join account b on (a.account_creditsmovement = b.id_account)
    where a.id_creditsmovement = new.id_creditsmovement) factura;
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;

--
-- Table structure for table `invoice`
--

DROP TABLE IF EXISTS `invoice`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `invoice` (
  `id_invoice` bigint(20) NOT NULL AUTO_INCREMENT,
  `due_date` date NOT NULL,
  `issue_date` date NOT NULL,
  `total_invoice` double NOT NULL,
  `taxes_invoice` double NOT NULL DEFAULT '0',
  `user_invoice` bigint(20) DEFAULT NULL,
  `account_invoice` bigint(20) DEFAULT NULL,
  `taxid_invoice` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`id_invoice`),
  KEY `fk_invoice_account_idx` (`account_invoice`),
  KEY `fk_invoice_usser_idx` (`user_invoice`),
  KEY `fk_invoice_taxes_idx` (`taxid_invoice`),
  CONSTRAINT `fk_invoice_account` FOREIGN KEY (`account_invoice`) REFERENCES `account` (`id_account`) ON UPDATE CASCADE,
  CONSTRAINT `fk_invoice_taxes` FOREIGN KEY (`taxid_invoice`) REFERENCES `taxes` (`id_taxes`) ON UPDATE CASCADE,
  CONSTRAINT `fk_invoice_usser` FOREIGN KEY (`user_invoice`) REFERENCES `usser` (`id_user`) ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `invoice`
--

LOCK TABLES `invoice` WRITE;
/*!40000 ALTER TABLE `invoice` DISABLE KEYS */;
INSERT INTO `invoice` VALUES (9,'2016-10-09','2016-09-09',6150,984,NULL,1,'COP_IVA');
/*!40000 ALTER TABLE `invoice` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `message_statistics`
--

DROP TABLE IF EXISTS `message_statistics`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `message_statistics` (
  `id_statistics` bigint(20) NOT NULL AUTO_INCREMENT,
  `creation_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `date_statistics` date DEFAULT NULL,
  `messages_count` bigint(20) DEFAULT NULL,
  `account_statistics` bigint(20) DEFAULT NULL,
  `user_statistics` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id_statistics`),
  KEY `fk_msg_statistics_account_idx` (`account_statistics`),
  KEY `fk_msg_statistics_user_idx` (`user_statistics`),
  KEY `date_statistics` (`date_statistics`),
  CONSTRAINT `fk_msg_statistics_account` FOREIGN KEY (`account_statistics`) REFERENCES `account` (`id_account`) ON UPDATE CASCADE,
  CONSTRAINT `fk_msg_statistics_user` FOREIGN KEY (`user_statistics`) REFERENCES `usser` (`id_user`) ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `message_statistics`
--

LOCK TABLES `message_statistics` WRITE;
/*!40000 ALTER TABLE `message_statistics` DISABLE KEYS */;
INSERT INTO `message_statistics` VALUES (1,'2016-09-09 15:40:57','2016-09-09',10,1,1),(2,'2016-09-09 15:42:42','2016-09-08',25,1,2),(3,'2016-09-09 15:42:42','2016-09-07',10,1,1),(4,'2016-09-09 15:42:42','2016-09-06',20,1,2),(5,'2016-09-09 15:42:42','2016-09-05',50,1,1),(6,'2016-09-09 15:42:42','2016-09-04',10,1,2),(7,'2016-09-09 15:42:42','2016-09-03',16,1,1),(8,'2016-09-09 15:42:42','2016-09-09',8,1,2),(9,'2016-09-09 15:42:42','2016-09-08',7,1,2),(10,'2016-09-09 15:44:01','2016-09-08',10,1,1);
/*!40000 ALTER TABLE `message_statistics` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `products`
--

DROP TABLE IF EXISTS `products`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `products` (
  `id_products` bigint(20) NOT NULL AUTO_INCREMENT,
  `description_products` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id_products`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `products`
--

LOCK TABLES `products` WRITE;
/*!40000 ALTER TABLE `products` DISABLE KEYS */;
INSERT INTO `products` VALUES (1,'credits');
/*!40000 ALTER TABLE `products` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `products_rate`
--

DROP TABLE IF EXISTS `products_rate`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `products_rate` (
  `id_productsrate` bigint(20) NOT NULL AUTO_INCREMENT,
  `id_products` bigint(20) DEFAULT NULL,
  `start_date` date NOT NULL,
  `end_date` date NOT NULL,
  `value_rate` double NOT NULL DEFAULT '0',
  `estate_rate` tinyint(4) NOT NULL,
  `duedays_rate` int(9) DEFAULT NULL,
  PRIMARY KEY (`id_productsrate`),
  KEY `fk_pro_rate_products_idx` (`id_products`),
  CONSTRAINT `fk_pro_rate_products` FOREIGN KEY (`id_products`) REFERENCES `products` (`id_products`) ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `products_rate`
--

LOCK TABLES `products_rate` WRITE;
/*!40000 ALTER TABLE `products_rate` DISABLE KEYS */;
INSERT INTO `products_rate` VALUES (1,1,'2016-01-01','2016-12-31',50,1,30);
/*!40000 ALTER TABLE `products_rate` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `server`
--

DROP TABLE IF EXISTS `server`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `server` (
  `name` varchar(100) DEFAULT NULL,
  `state` varchar(10) DEFAULT 'idle',
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=7 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `server`
--

LOCK TABLES `server` WRITE;
/*!40000 ALTER TABLE `server` DISABLE KEYS */;
INSERT INTO `server` VALUES ('ASDASDASDASD','running',6);
/*!40000 ALTER TABLE `server` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `taxes`
--

DROP TABLE IF EXISTS `taxes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `taxes` (
  `value_taxes` double NOT NULL DEFAULT '0',
  `country_taxes` varchar(3) NOT NULL,
  `description_taxes` varchar(100) NOT NULL,
  `creation_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `id_taxes` varchar(20) NOT NULL,
  PRIMARY KEY (`id_taxes`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `taxes`
--

LOCK TABLES `taxes` WRITE;
/*!40000 ALTER TABLE `taxes` DISABLE KEYS */;
INSERT INTO `taxes` VALUES (16,'COP','IVA','2016-09-09 15:54:30','COP_IVA');
/*!40000 ALTER TABLE `taxes` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `usser`
--

DROP TABLE IF EXISTS `usser`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `usser` (
  `id_user` bigint(20) NOT NULL AUTO_INCREMENT,
  `creation_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `email_user` varchar(100) NOT NULL,
  `firstname_user` varchar(100) NOT NULL,
  `lastaccess_user` datetime DEFAULT NULL,
  `lastname_user` varchar(100) NOT NULL,
  `mobile_user` varchar(50) DEFAULT NULL,
  `password_user` varchar(100) NOT NULL,
  `roles_user` varchar(50) DEFAULT NULL,
  `state_user` tinyint(4) NOT NULL,
  `account_user` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id_user`),
  UNIQUE KEY `email_user` (`email_user`),
  KEY `fk_account_user_idx` (`account_user`),
  KEY `fk_usser_type_idx` (`roles_user`),
  CONSTRAINT `fk_account_user` FOREIGN KEY (`account_user`) REFERENCES `account` (`id_account`) ON UPDATE CASCADE,
  CONSTRAINT `fk_usser_type` FOREIGN KEY (`roles_user`) REFERENCES `usser_type` (`description_usertype`) ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usser`
--

LOCK TABLES `usser` WRITE;
/*!40000 ALTER TABLE `usser` DISABLE KEYS */;
INSERT INTO `usser` VALUES (1,'2016-09-09 15:27:52','mail@mail.com','jose','2016-09-09 00:00:00','bocanegra','33333333','clave','user',1,1),(2,'2016-09-09 15:28:45','asdasd','asdasd',NULL,'asdasd','213123','asdasd','manager',1,1);
/*!40000 ALTER TABLE `usser` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `usser_type`
--

DROP TABLE IF EXISTS `usser_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `usser_type` (
  `description_usertype` varchar(50) NOT NULL,
  `creation_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`description_usertype`),
  UNIQUE KEY `description_usertype` (`description_usertype`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usser_type`
--

LOCK TABLES `usser_type` WRITE;
/*!40000 ALTER TABLE `usser_type` DISABLE KEYS */;
INSERT INTO `usser_type` VALUES ('manager','2016-09-09 15:36:40'),('user','2016-09-09 15:36:40');
/*!40000 ALTER TABLE `usser_type` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping routines for database 'elibom'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2016-09-09 21:05:32
