-- MariaDB dump 10.19  Distrib 10.4.28-MariaDB, for Win64 (AMD64)
--
-- Host: 127.0.0.1    Database: auleweb
-- ------------------------------------------------------
-- Server version	8.0.32

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `amministratore`
--

DROP TABLE IF EXISTS `amministratore`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `amministratore` (
  `id` int NOT NULL AUTO_INCREMENT,
  `username` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `email` varchar(255) NOT NULL,
  `versione` int DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `amministratore`
--

/*!40000 ALTER TABLE `amministratore` DISABLE KEYS */;
INSERT INTO `amministratore` (`id`, `username`, `password`, `email`, `versione`) VALUES (1,'Matteo','f8360d6f939bb6125a853f54ca17309eab1d892a4ec8ec4b90e7ab871443d289bb01a5ca3207d964f5c3be1c8df417dc','matteo.tatoni@student.univaq.it',0),(2,'Davide','f8360d6f939bb6125a853f54ca17309eab1d892a4ec8ec4b90e7ab871443d289bb01a5ca3207d964f5c3be1c8df417dc','davide.dirocco@student.univaq.it',0);
/*!40000 ALTER TABLE `amministratore` ENABLE KEYS */;

--
-- Table structure for table `attrezzatura`
--

DROP TABLE IF EXISTS `attrezzatura`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `attrezzatura` (
  `id` int NOT NULL AUTO_INCREMENT,
  `nome` varchar(255) NOT NULL,
  `numero_serie` varchar(255) NOT NULL,
  `id_aula` int DEFAULT NULL,
  `versione` int DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `id_aula` (`id_aula`),
  CONSTRAINT `attrezzatura_ibfk_1` FOREIGN KEY (`id_aula`) REFERENCES `aula` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `attrezzatura`
--

/*!40000 ALTER TABLE `attrezzatura` DISABLE KEYS */;
INSERT INTO `attrezzatura` (`id`, `nome`, `numero_serie`, `id_aula`, `versione`) VALUES (1,'Proiettore','P12345',4,2),(2,'Lavagna','L54321',2,0),(3,'Computer','C67890',3,0),(9,'Proiettore','PR456',2,0),(11,'Microfono','MI111',4,2),(12,'Microfono','MI222',NULL,1),(13,'Microfono','MS333',NULL,0),(14,'Microfono','MX444',3,0),(15,'Computer','CO555',2,0),(18,'Lavagna','LB888',NULL,0),(20,'Lavagna','LD000',2,0),(22,'Microfono con Filo','M45678',NULL,0);
/*!40000 ALTER TABLE `attrezzatura` ENABLE KEYS */;
