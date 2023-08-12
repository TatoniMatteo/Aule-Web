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
INSERT INTO `amministratore` VALUES (1,'Matteo','f8360d6f939bb6125a853f54ca17309eab1d892a4ec8ec4b90e7ab871443d289bb01a5ca3207d964f5c3be1c8df417dc','matteo.tatoni@student.univaq.it',0),(2,'Davide','f8360d6f939bb6125a853f54ca17309eab1d892a4ec8ec4b90e7ab871443d289bb01a5ca3207d964f5c3be1c8df417dc','davide.dirocco@student.univaq.it',0);
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
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `attrezzatura`
--

/*!40000 ALTER TABLE `attrezzatura` DISABLE KEYS */;
INSERT INTO `attrezzatura` VALUES (1,'Proiettore','P12345',1,0),(2,'Lavagna','L54321',2,0),(3,'Computer','C67890',3,0),(4,'Microfono','M45678',1,0);
/*!40000 ALTER TABLE `attrezzatura` ENABLE KEYS */;

--
-- Table structure for table `aula`
--

DROP TABLE IF EXISTS `aula`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `aula` (
  `id` int NOT NULL AUTO_INCREMENT,
  `nome` varchar(255) NOT NULL,
  `luogo` varchar(255) NOT NULL,
  `edificio` varchar(255) NOT NULL,
  `piano` tinyint NOT NULL,
  `capienza` int NOT NULL,
  `prese_elettriche` int DEFAULT NULL,
  `prese_rete` int DEFAULT NULL,
  `note` text,
  `id_responsabile` int DEFAULT NULL,
  `versione` int DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `id_responsabile` (`id_responsabile`),
  CONSTRAINT `aula_ibfk_1` FOREIGN KEY (`id_responsabile`) REFERENCES `responsabile` (`id`) ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `aula`
--

/*!40000 ALTER TABLE `aula` DISABLE KEYS */;
INSERT INTO `aula` VALUES (1,'Aula 101','Via Roma, 1','Edificio A',1,50,20,10,'Generiche note per l\'aula 101',1,0),(2,'Aula 201','Via Roma, 1','Edificio A',2,40,15,8,'Generiche note per l\'aula 201',2,0),(3,'Aula 301','Via Milano, 2','Edificio B',3,30,10,6,'Generiche note per l\'aula 301',3,0);
/*!40000 ALTER TABLE `aula` ENABLE KEYS */;

--
-- Table structure for table `aula_gruppo`
--

DROP TABLE IF EXISTS `aula_gruppo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `aula_gruppo` (
  `id_aula` int NOT NULL,
  `id_gruppo` int NOT NULL,
  `versione` int DEFAULT '0',
  PRIMARY KEY (`id_aula`,`id_gruppo`),
  KEY `id_gruppo` (`id_gruppo`),
  CONSTRAINT `aula_gruppo_ibfk_1` FOREIGN KEY (`id_aula`) REFERENCES `aula` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `aula_gruppo_ibfk_2` FOREIGN KEY (`id_gruppo`) REFERENCES `gruppo` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `aula_gruppo`
--

/*!40000 ALTER TABLE `aula_gruppo` DISABLE KEYS */;
INSERT INTO `aula_gruppo` VALUES (1,1,0),(1,4,0),(2,2,0),(2,5,0),(3,3,0),(3,6,0);
/*!40000 ALTER TABLE `aula_gruppo` ENABLE KEYS */;

--
-- Table structure for table `categoria`
--

DROP TABLE IF EXISTS `categoria`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `categoria` (
  `id` int NOT NULL AUTO_INCREMENT,
  `nome` varchar(255) NOT NULL,
  `versione` int DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `categoria`
--

/*!40000 ALTER TABLE `categoria` DISABLE KEYS */;
INSERT INTO `categoria` VALUES (1,'dipartimenti',0),(2,'Poli',0);
/*!40000 ALTER TABLE `categoria` ENABLE KEYS */;

--
-- Table structure for table `corso`
--

DROP TABLE IF EXISTS `corso`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `corso` (
  `id` int NOT NULL AUTO_INCREMENT,
  `nome` varchar(255) NOT NULL,
  `descrizione` text,
  `corso_laurea` varchar(255) DEFAULT NULL,
  `versione` int DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `corso`
--

/*!40000 ALTER TABLE `corso` DISABLE KEYS */;
INSERT INTO `corso` VALUES (1,'Base di Dati','Corso di Base di Dati','Informatica',0),(2,'Ingegneria del Web','Corso di Ingegneria del Web','Informatica',0),(3,'Anatomia 1','Corso di Anatomia 1','Medicina',0),(4,'Farmacologia','Corso di Farmacologia','Medicina',0);
/*!40000 ALTER TABLE `corso` ENABLE KEYS */;

--
-- Table structure for table `evento`
--

DROP TABLE IF EXISTS `evento`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `evento` (
  `id` int NOT NULL AUTO_INCREMENT,
  `id_ricorrenza` int DEFAULT NULL,
  `nome` varchar(255) NOT NULL,
  `descrizione` text,
  `data` date NOT NULL,
  `ora_inizio` time NOT NULL,
  `ora_fine` time NOT NULL,
  `id_corso` int DEFAULT NULL,
  `id_responsabile` int DEFAULT NULL,
  `id_aula` int DEFAULT NULL,
  `versione` int DEFAULT '0',
  `tipo_evento` varchar(30) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `id_corso` (`id_corso`),
  KEY `id_responsabile` (`id_responsabile`),
  KEY `id_aula` (`id_aula`),
  CONSTRAINT `evento_ibfk_1` FOREIGN KEY (`id_corso`) REFERENCES `corso` (`id`) ON DELETE RESTRICT ON UPDATE CASCADE,
  CONSTRAINT `evento_ibfk_2` FOREIGN KEY (`id_responsabile`) REFERENCES `responsabile` (`id`) ON DELETE RESTRICT ON UPDATE CASCADE,
  CONSTRAINT `evento_ibfk_3` FOREIGN KEY (`id_aula`) REFERENCES `aula` (`id`) ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `evento`
--

/*!40000 ALTER TABLE `evento` DISABLE KEYS */;
INSERT INTO `evento` VALUES (1,NULL,'Lezione 1','Prima lezione di Informatica','2023-08-11','09:00:00','11:00:00',1,1,1,0,'LEZIONE'),(2,NULL,'Seminario Finanza','Seminario sulla finanza aziendale','2023-08-11','14:00:00','16:00:00',NULL,2,2,0,'SEMINARIO'),(3,NULL,'Lab Elettronica','Laboratorio di elettronica','2023-08-11','10:00:00','12:00:00',3,3,3,0,'LEZIONE'),(4,NULL,'Conferenza Informatica','Conferenza su nuove tecnologie','2023-08-11','15:00:00','18:00:00',NULL,4,1,0,'SEMINARIO');
/*!40000 ALTER TABLE `evento` ENABLE KEYS */;

--
-- Table structure for table `gruppo`
--

DROP TABLE IF EXISTS `gruppo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `gruppo` (
  `id` int NOT NULL AUTO_INCREMENT,
  `nome` varchar(255) NOT NULL,
  `descrizione` text,
  `id_categoria` int NOT NULL,
  `versione` int DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `id_categoria` (`id_categoria`),
  CONSTRAINT `gruppo_ibfk_1` FOREIGN KEY (`id_categoria`) REFERENCES `categoria` (`id`) ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `gruppo`
--

/*!40000 ALTER TABLE `gruppo` DISABLE KEYS */;
INSERT INTO `gruppo` VALUES (1,'Informatica','Dipartimento di Informatica',1,0),(2,'Economia','Dipartimento di Economia',1,0),(3,'Ingegneria','Dipartimento di Ingegneria',1,0),(4,'Scienze Umanistiche','Dipartimento di Scienze Umanistiche',1,0),(5,'Polo Tecnologico','Polo di ricerca tecnologica',2,0),(6,'Polo Culturale','Polo di ricerca culturale',2,0);
/*!40000 ALTER TABLE `gruppo` ENABLE KEYS */;

--
-- Table structure for table `responsabile`
--

DROP TABLE IF EXISTS `responsabile`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `responsabile` (
  `id` int NOT NULL AUTO_INCREMENT,
  `nome` varchar(255) NOT NULL,
  `cognome` varchar(255) NOT NULL,
  `email` varchar(255) DEFAULT NULL,
  `versione` int DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `responsabile`
--

/*!40000 ALTER TABLE `responsabile` DISABLE KEYS */;
INSERT INTO `responsabile` VALUES (1,'Mario','Rossi','mario.rossi@example.com',0),(2,'Laura','Bianchi','laura.bianchi@example.com',0),(3,'Luca','Verdi','luca.verdi@example.com',0),(4,'Anna','Gialli','anna.gialli@example.com',0);
/*!40000 ALTER TABLE `responsabile` ENABLE KEYS */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-08-12 10:13:05
