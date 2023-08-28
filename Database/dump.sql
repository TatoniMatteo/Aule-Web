-- MySQL dump 10.13  Distrib 8.0.32, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: auleweb
-- ------------------------------------------------------
-- Server version	8.0.32

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
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
/*!50503 SET character_set_client = utf8mb4 */;
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
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
/*!50032 DROP TRIGGER IF EXISTS increment_administrator_version */;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER `increment_administrator_version` BEFORE UPDATE ON `amministratore` FOR EACH ROW BEGIN
    IF NEW.versione = OLD.versione THEN
        SET NEW.versione = OLD.versione + 1;
    END IF;
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;

--
-- Table structure for table `attrezzatura`
--

DROP TABLE IF EXISTS `attrezzatura`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
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
INSERT INTO `attrezzatura` VALUES (1,'Proiettore','P12345',5,3),(2,'Lavagna','L54321',2,0),(3,'Computer','C67890',3,0),(9,'Proiettore','PR456',2,0),(11,'Microfono','MI111',5,3),(12,'Microfono','MI222',NULL,1),(13,'Microfono','MS333',4,0),(14,'Microfono','MX444',3,0),(15,'Computer','CO555',2,0),(18,'Lavagna','LB888',4,0),(20,'Lavagna','LD000',2,0),(22,'Microfono con Filo','M45678',NULL,0);
/*!40000 ALTER TABLE `attrezzatura` ENABLE KEYS */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
/*!50032 DROP TRIGGER IF EXISTS increment_attrezzatura_version */;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER `increment_attrezzatura_version` BEFORE UPDATE ON `attrezzatura` FOR EACH ROW BEGIN
    IF NEW.versione = OLD.versione THEN
        SET NEW.versione = OLD.versione + 1;
    END IF;
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
ALTER DATABASE `auleweb` CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci ;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
/*!50032 DROP TRIGGER IF EXISTS prevent_delete_attrezzatura */;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER `prevent_delete_attrezzatura` BEFORE DELETE ON `attrezzatura` FOR EACH ROW BEGIN
    IF OLD.id_aula IS NOT NULL THEN
        SIGNAL SQLSTATE '45000'
            SET MESSAGE_TEXT =
                    'Non puoi eliminare un\'attrezzatura associata a un\'aula. Rimuovi l\'attrezzatura dall\'aula e riporva';
    END IF;
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
ALTER DATABASE `auleweb` CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci ;

--
-- Table structure for table `aula`
--

DROP TABLE IF EXISTS `aula`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
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
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `aula`
--

/*!40000 ALTER TABLE `aula` DISABLE KEYS */;
INSERT INTO `aula` VALUES (2,'Aula 201','Via Roma, 1','Edificio A',2,40,15,8,'Generiche note per l\'aula 201',2,0),(3,'Aula 301','Via Milano, 2','Edificio B',3,30,10,6,'Generiche note per l\'aula 301',3,0),(4,'Aula 102','Via Roma, 1','Coppito 2',1,120,60,10,'Aula in manutenzione',1,0),(5,'Aula 101','Via Roma, 1','Coppito 2',1,120,60,10,'Aula Rossa',4,0);
/*!40000 ALTER TABLE `aula` ENABLE KEYS */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
/*!50032 DROP TRIGGER IF EXISTS increment_aula_version */;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER `increment_aula_version` BEFORE UPDATE ON `aula` FOR EACH ROW BEGIN
    IF NEW.versione = OLD.versione THEN
        SET NEW.versione = OLD.versione + 1;
    END IF;
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;

--
-- Table structure for table `aula_gruppo`
--

DROP TABLE IF EXISTS `aula_gruppo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
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
INSERT INTO `aula_gruppo` VALUES (2,2,0),(2,5,0),(3,3,0),(3,6,0),(4,1,0),(4,5,0),(5,1,0),(5,5,0);
/*!40000 ALTER TABLE `aula_gruppo` ENABLE KEYS */;

--
-- Table structure for table `categoria`
--

DROP TABLE IF EXISTS `categoria`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `categoria` (
  `id` int NOT NULL AUTO_INCREMENT,
  `nome` varchar(255) NOT NULL,
  `versione` int DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `categoria`
--

/*!40000 ALTER TABLE `categoria` DISABLE KEYS */;
INSERT INTO `categoria` VALUES (1,'Dipartimenti',0),(2,'Poli',0),(3,'Test',0),(4,'Test1',0);
/*!40000 ALTER TABLE `categoria` ENABLE KEYS */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
/*!50032 DROP TRIGGER IF EXISTS increment_categoria_version */;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER `increment_categoria_version` BEFORE UPDATE ON `categoria` FOR EACH ROW BEGIN
    IF NEW.versione = OLD.versione THEN
        SET NEW.versione = OLD.versione + 1;
    END IF;
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;

--
-- Table structure for table `corso`
--

DROP TABLE IF EXISTS `corso`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `corso` (
  `id` int NOT NULL AUTO_INCREMENT,
  `nome` varchar(255) NOT NULL,
  `descrizione` text,
  `corso_laurea` varchar(255) DEFAULT NULL,
  `versione` int DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `corso`
--

/*!40000 ALTER TABLE `corso` DISABLE KEYS */;
INSERT INTO `corso` VALUES (1,'Base di Dati','Acquisire le conoscenze e le metodologie necessarie per l\'interazione con le basi di dati, approfondendo i concetti di base del funzionamento delle basi di dati e gli strumenti per la progettazione e realizzazione delle stesse. Sviluppare basi di dati tradizionali di tipo relazionale SQL-like e basi di dati evolute di tipo NoSQL capaci di poter gestire Big Data\r\nSviluppare la capacità di comunicare correttamente il risultato delle attività svolte mediante la redazione di progetti di basi di dati individuali e/o di gruppo.\r\nSviluppare l\'attitudine all\'ampliamento delle proprie conoscenze mediante la continua sollecitazione alla ricerca delle metodologie più adatte ad affrontare l\'opportuna progettazione e realizzazione di una base di dati.','Informatica',1),(2,'Ingegneria del Web','Corso di Ingegneria del Web','Informatica',0),(3,'Anatomia 1','Questo corso introduttivo di Anatomia 1 offre agli studenti della facoltà di medicina una panoramica dettagliata dell\'anatomia umana. Gli studenti esploreranno la struttura e la funzione del corpo umano attraverso una combinazione di lezioni teoriche, disegni anatomici, esami di laboratorio e progetti di ricerca. Il corso copre una vasta gamma di argomenti, tra cui l\'anatomia degli organi interni, del sistema scheletrico e muscolare, e del sistema nervoso. Gli studenti avranno l\'opportunità di acquisire una conoscenza approfondita dell\'anatomia attraverso l\'analisi di preparati anatomici, modelli tridimensionali e tecnologie avanzate come le scansioni MRI. Al termine del corso, gli studenti saranno in grado di applicare le loro conoscenze anatomiche alla pratica medica e alla diagnosi.','Medicina',2),(4,'Farmacologia','Corso di Farmacologia','Medicina',0),(5,'Web Engeneering','Il corso si propone di fornire una preparazione completa sulle tecnologie necessarie allo sviluppo di applicazioni web moderne, conformi agli standard e sicure, con particolare riguardo anche per le problematiche di accessibilità e usabilità','Informatica',0);
/*!40000 ALTER TABLE `corso` ENABLE KEYS */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
/*!50032 DROP TRIGGER IF EXISTS increment_corso_version */;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER `increment_corso_version` BEFORE UPDATE ON `corso` FOR EACH ROW BEGIN
    IF NEW.versione = OLD.versione THEN
        SET NEW.versione = OLD.versione + 1;
    END IF;
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;

--
-- Table structure for table `evento`
--

DROP TABLE IF EXISTS `evento`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
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
  CONSTRAINT `evento_ibfk_3` FOREIGN KEY (`id_aula`) REFERENCES `aula` (`id`) ON DELETE RESTRICT ON UPDATE CASCADE,
  CONSTRAINT `check_start_end_time` CHECK ((`ora_inizio` < `ora_fine`))
) ENGINE=InnoDB AUTO_INCREMENT=110 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `evento`
--

/*!40000 ALTER TABLE `evento` DISABLE KEYS */;
INSERT INTO `evento` VALUES (2,NULL,'Seminario Finanza','Seminario sulla finanza aziendale','2023-09-07','14:00:00','16:00:00',NULL,2,2,1,'Seminario'),(3,NULL,'Lab Elettronica','Laboratorio di elettronica','2023-09-08','10:00:00','13:00:00',3,3,3,1,'Lezione'),(16,NULL,'Seminario sul sangue','Seminario sul sangue','2023-09-09','09:00:00','12:00:00',NULL,3,2,1,'Seminario'),(83,1,'Test','test','2023-09-07','10:00:00','12:00:00',NULL,1,4,0,'Altro'),(84,1,'Test','test','2023-09-08','10:00:00','12:00:00',NULL,1,4,0,'Altro'),(85,1,'Test','test','2023-09-09','10:00:00','12:00:00',NULL,1,4,0,'Altro'),(86,1,'Test','test','2023-09-10','10:00:00','12:00:00',NULL,1,4,0,'Altro'),(87,1,'Test','test','2023-09-11','10:00:00','12:00:00',NULL,1,4,0,'Altro'),(88,1,'Test','test','2023-09-12','10:00:00','12:00:00',NULL,1,4,0,'Altro'),(89,1,'Test','test','2023-09-13','10:00:00','12:00:00',NULL,1,4,0,'Altro'),(90,1,'Test','test','2023-09-14','10:00:00','12:00:00',NULL,1,4,0,'Altro'),(91,1,'Test','test','2023-09-15','10:00:00','12:00:00',NULL,1,4,0,'Altro'),(92,1,'Test','test','2023-09-16','10:00:00','12:00:00',NULL,1,4,0,'Altro'),(93,1,'Test','test','2023-09-17','10:00:00','12:00:00',NULL,1,4,0,'Altro'),(94,2,'Lezione','Lezioni di Anatomia per pincipianti','2023-09-01','16:00:00','18:00:00',3,2,3,0,'Lezione'),(95,2,'Lezione','Lezioni di Anatomia per pincipianti','2023-09-08','16:00:00','18:00:00',3,2,3,0,'Lezione'),(96,2,'Lezione','Lezioni di Anatomia per pincipianti','2023-09-15','16:00:00','18:00:00',3,2,3,0,'Lezione'),(97,2,'Lezione','Lezioni di Anatomia per pincipianti','2023-09-22','16:00:00','18:00:00',3,2,3,0,'Lezione'),(98,2,'Lezione','Lezioni di Anatomia per pincipianti','2023-09-29','16:00:00','18:00:00',3,2,3,0,'Lezione'),(99,2,'Lezione','Lezioni di Anatomia per pincipianti','2023-10-06','16:00:00','18:00:00',3,2,3,0,'Lezione'),(100,2,'Lezione','Lezioni di Anatomia per pincipianti','2023-10-13','16:00:00','18:00:00',3,2,3,0,'Lezione'),(101,2,'Lezione','Lezioni di Anatomia per pincipianti','2023-10-20','16:00:00','18:00:00',3,2,3,0,'Lezione'),(102,2,'Lezione','Lezioni di Anatomia per pincipianti','2023-10-27','16:00:00','18:00:00',3,2,3,0,'Lezione'),(103,2,'Lezione','Lezioni di Anatomia per pincipianti','2023-11-03','16:00:00','18:00:00',3,2,3,0,'Lezione'),(104,2,'Lezione','Lezioni di Anatomia per pincipianti','2023-11-10','16:00:00','18:00:00',3,2,3,0,'Lezione'),(105,2,'Lezione','Lezioni di Anatomia per pincipianti','2023-11-17','16:00:00','18:00:00',3,2,3,0,'Lezione'),(106,2,'Lezione','Lezioni di Anatomia per pincipianti','2023-11-24','16:00:00','18:00:00',3,2,3,0,'Lezione'),(107,2,'Lezione','Lezioni di Anatomia per pincipianti','2023-12-01','16:00:00','18:00:00',3,2,3,0,'Lezione'),(108,2,'Lezione','Lezioni di Anatomia per pincipianti','2023-12-08','16:00:00','18:00:00',3,2,3,0,'Lezione'),(109,2,'Lezione','Lezioni di Anatomia per pincipianti','2023-12-15','16:00:00','18:00:00',3,2,3,0,'Lezione');
/*!40000 ALTER TABLE `evento` ENABLE KEYS */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
/*!50032 DROP TRIGGER IF EXISTS check_event_constraints */;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER `check_event_constraints` BEFORE INSERT ON `evento` FOR EACH ROW BEGIN
    DECLARE is_overlap BOOLEAN;
    DECLARE overlap_event_info TEXT;

    SET is_overlap = check_event_overlap(NEW.id, NEW.ora_inizio, NEW.ora_fine, NEW.data, NEW.id_aula);

    IF is_overlap THEN
        SIGNAL SQLSTATE '45000'
            SET MESSAGE_TEXT = 'Errore: L\'aula è già impegnata in questa fascia oraria da un altro evento.';
    END IF;

    IF TIME_FORMAT(NEW.ora_inizio, '%i:%s') NOT IN ('00:00', '15:00', '30:00', '45:00') THEN
        SIGNAL SQLSTATE '45000'
            SET MESSAGE_TEXT = 'L\'orario di inizio deve essere arrotondato al quarto d\'ora.';
    END IF;

    IF TIME_FORMAT(NEW.ora_fine, '%i:%s') NOT IN ('00:00', '15:00', '30:00', '45:00') THEN
        SIGNAL SQLSTATE '45000'
            SET MESSAGE_TEXT = 'L\'orario di fine deve essere arrotondato al quarto d\'ora.';
    END IF;

    IF NOT (NEW.ora_inizio <= NEW.ora_fine) THEN
        SIGNAL SQLSTATE '45000'
            SET MESSAGE_TEXT = 'L\'ora d\'inizio deve essere antecedente all\'ora di fine.';
    END IF;
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
/*!50032 DROP TRIGGER IF EXISTS increment_event_version */;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER `increment_event_version` BEFORE UPDATE ON `evento` FOR EACH ROW BEGIN
    IF NEW.versione = OLD.versione THEN
        SET NEW.versione = OLD.versione + 1;
    END IF;
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
/*!50032 DROP TRIGGER IF EXISTS check_event_constraints_update */;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER `check_event_constraints_update` BEFORE UPDATE ON `evento` FOR EACH ROW BEGIN
    DECLARE is_overlap BOOLEAN;

    SET is_overlap = check_event_overlap(OLD.id, NEW.ora_inizio, NEW.ora_fine, NEW.data, NEW.id_aula);

    IF is_overlap THEN
        SIGNAL SQLSTATE '45000'
            SET MESSAGE_TEXT = 'Errore: L\'aula è già impegnata in questa fascia oraria da un altro evento.';
    END IF;

    IF TIME_FORMAT(NEW.ora_inizio, '%i:%s') NOT IN ('00:00', '15:00', '30:00', '45:00') THEN
        SIGNAL SQLSTATE '45000'
            SET MESSAGE_TEXT = 'L\'orario di inizio deve essere arrotondato al quarto d\'ora.';
    END IF;

    IF TIME_FORMAT(NEW.ora_fine, '%i:%s') NOT IN ('00:00', '15:00', '30:00', '45:00') THEN
        SIGNAL SQLSTATE '45000'
            SET MESSAGE_TEXT = 'L\'orario di fine deve essere arrotondato al quarto d\'ora.';
    END IF;

    IF NOT (NEW.ora_inizio <= NEW.ora_fine) THEN
        SIGNAL SQLSTATE '45000'
            SET MESSAGE_TEXT = 'L\'ora d\'inizio deve essere antecedente all\'ora di fine';
    END IF;
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;

--
-- Table structure for table `gruppo`
--

DROP TABLE IF EXISTS `gruppo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `gruppo` (
  `id` int NOT NULL AUTO_INCREMENT,
  `nome` varchar(255) NOT NULL,
  `descrizione` text,
  `id_categoria` int NOT NULL,
  `versione` int DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `id_categoria` (`id_categoria`),
  CONSTRAINT `gruppo_ibfk_1` FOREIGN KEY (`id_categoria`) REFERENCES `categoria` (`id`) ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `gruppo`
--

/*!40000 ALTER TABLE `gruppo` DISABLE KEYS */;
INSERT INTO `gruppo` VALUES (1,'Informatica','Dipartimento di Informatica',1,0),(2,'Economia','Dipartimento di Economia',1,1),(3,'Ingegneria','Dipartimento di Ingegneria',1,0),(4,'Scienze Umanistiche','Dipartimento di Scienze Umanistiche',1,0),(5,'Polo Tecnologico','Polo di ricerca tecnologica',2,0),(6,'Polo Culturale','Polo di ricerca culturale',2,0);
/*!40000 ALTER TABLE `gruppo` ENABLE KEYS */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
/*!50032 DROP TRIGGER IF EXISTS increment_gruppo_version */;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER `increment_gruppo_version` BEFORE UPDATE ON `gruppo` FOR EACH ROW BEGIN
    IF NEW.versione = OLD.versione THEN
        SET NEW.versione = OLD.versione + 1;
    END IF;
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;

--
-- Table structure for table `responsabile`
--

DROP TABLE IF EXISTS `responsabile`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `responsabile` (
  `id` int NOT NULL AUTO_INCREMENT,
  `nome` varchar(255) NOT NULL,
  `cognome` varchar(255) NOT NULL,
  `email` varchar(255) DEFAULT NULL,
  `versione` int DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `responsabile`
--

/*!40000 ALTER TABLE `responsabile` DISABLE KEYS */;
INSERT INTO `responsabile` VALUES (1,'Mario','Rossi','mario.rossi@example.com',0),(2,'Laura','Bianchi','laura.bianchi@example.com',0),(3,'Luca','Verdi','luca.verdi@example.com',0),(4,'Anna','Gialli','anna.gialli@example.com',1),(5,'Piero','Pelu','pieropelu@example.com',0);
/*!40000 ALTER TABLE `responsabile` ENABLE KEYS */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
/*!50032 DROP TRIGGER IF EXISTS increment_responsabile_version */;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER `increment_responsabile_version` BEFORE UPDATE ON `responsabile` FOR EACH ROW BEGIN
    IF NEW.versione = OLD.versione THEN
        SET NEW.versione = OLD.versione + 1;
    END IF;
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-08-28 17:59:48
