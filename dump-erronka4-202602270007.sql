-- MySQL dump 10.13  Distrib 8.0.19, for Win64 (x86_64)
--
-- Host: localhost    Database: erronka4
-- ------------------------------------------------------
-- Server version	8.0.44

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
-- Table structure for table `erabiltzaileak`
--

DROP TABLE IF EXISTS `erabiltzaileak`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `erabiltzaileak` (
  `erab_id` int NOT NULL,
  `erab_izena` varchar(100) DEFAULT NULL,
  `erab_abizenak` varchar(100) DEFAULT NULL,
  `erab_email` varchar(150) DEFAULT NULL,
  `erab_helbidea` varchar(150) DEFAULT NULL,
  PRIMARY KEY (`erab_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `erabiltzaileak`
--

LOCK TABLES `erabiltzaileak` WRITE;
/*!40000 ALTER TABLE `erabiltzaileak` DISABLE KEYS */;
/*!40000 ALTER TABLE `erabiltzaileak` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `eskariak`
--

DROP TABLE IF EXISTS `eskariak`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `eskariak` (
  `eskari_kod` int NOT NULL,
  `prezio_totala` double DEFAULT NULL,
  `eskari_data` date DEFAULT NULL,
  `egoera` varchar(100) DEFAULT NULL,
  `erabiltzaile_id` int DEFAULT NULL,
  PRIMARY KEY (`eskari_kod`),
  KEY `erabiltzaile_id` (`erabiltzaile_id`),
  CONSTRAINT `eskariak_ibfk_1` FOREIGN KEY (`erabiltzaile_id`) REFERENCES `erabiltzaileak` (`erab_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `eskariak`
--

LOCK TABLES `eskariak` WRITE;
/*!40000 ALTER TABLE `eskariak` DISABLE KEYS */;
/*!40000 ALTER TABLE `eskariak` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `kategoriak`
--

DROP TABLE IF EXISTS `kategoriak`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `kategoriak` (
  `kat_kod` int NOT NULL,
  `deskribapena` varchar(150) DEFAULT NULL,
  `egoera` varchar(100) DEFAULT NULL,
  `kat_izena` enum('Sudaderak','Alkandorak','Kamisetak','Galtzak','Zapatillak') DEFAULT NULL,
  PRIMARY KEY (`kat_kod`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `kategoriak`
--

LOCK TABLES `kategoriak` WRITE;
/*!40000 ALTER TABLE `kategoriak` DISABLE KEYS */;
INSERT INTO `kategoriak` VALUES (1111,'Izerditzeko jertseak',NULL,'Sudaderak'),(2222,'Janzteko alkandorak',NULL,'Alkandorak'),(3333,'Manga motzeko kamisetak',NULL,'Kamisetak'),(4444,'Galtza luzeak',NULL,'Galtzak'),(5555,'Mota askotako zapatillak',NULL,'Zapatillak');
/*!40000 ALTER TABLE `kategoriak` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `produktu_eskari`
--

DROP TABLE IF EXISTS `produktu_eskari`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `produktu_eskari` (
  `prod_kod` int NOT NULL,
  `eskari_kod` int NOT NULL,
  `kopurua` int DEFAULT NULL,
  `subtotala` double DEFAULT NULL,
  PRIMARY KEY (`prod_kod`,`eskari_kod`),
  KEY `eskari_kod` (`eskari_kod`),
  CONSTRAINT `produktu_eskari_ibfk_1` FOREIGN KEY (`prod_kod`) REFERENCES `produktuak` (`ID`),
  CONSTRAINT `produktu_eskari_ibfk_2` FOREIGN KEY (`eskari_kod`) REFERENCES `eskariak` (`eskari_kod`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `produktu_eskari`
--

LOCK TABLES `produktu_eskari` WRITE;
/*!40000 ALTER TABLE `produktu_eskari` DISABLE KEYS */;
/*!40000 ALTER TABLE `produktu_eskari` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `produktuak`
--

DROP TABLE IF EXISTS `produktuak`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `produktuak` (
  `ID` int NOT NULL AUTO_INCREMENT,
  `izena` varchar(50) DEFAULT NULL,
  `deskribapena` varchar(150) DEFAULT NULL,
  `prezioa` double DEFAULT NULL,
  `stock` int DEFAULT NULL,
  `sorkuntza_data` date DEFAULT NULL,
  `kat_kod` int DEFAULT NULL,
  `irudia` varchar(300) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `kat_kod` (`kat_kod`),
  CONSTRAINT `produktuak_ibfk_1` FOREIGN KEY (`kat_kod`) REFERENCES `kategoriak` (`kat_kod`)
) ENGINE=InnoDB AUTO_INCREMENT=5015 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `produktuak`
--

LOCK TABLES `produktuak` WRITE;
/*!40000 ALTER TABLE `produktuak` DISABLE KEYS */;
INSERT INTO `produktuak` VALUES (1011,'Lacoste Sudadera','Sudadera urdin iluna',90,110,'2024-05-23',1111,'https://encrypted-tbn0.gstatic.com/shopping?q=tbn:ANd9GcTt5v7dV06ER-KILfr1Vn-tgBbFRYjuw_Xo3tvPCXXQCs2cVbbuPenPTGVeHcLc-AsbaPY3VphULOKVW6q56clotHPMVmck'),(1012,'Adidas Jertsea','Jertse txuria',50,100,'2017-05-29',1111,'https://encrypted-tbn2.gstatic.com/shopping?q=tbn:ANd9GcR9Gbyo4xoeAkhEjT2CISN4axsrgJ6CujlMGMvKknzcFY08TANx6xMxpb4tggDfRfyZetk_xrG0m5S7gkqu16fMW69tk3SNEDsbcn5lI129XZbp-ChFdp7VgWs'),(1013,'Sudadera Blue Banana','Sudadera txuria',55,80,'2020-02-17',1111,'https://encrypted-tbn1.gstatic.com/shopping?q=tbn:ANd9GcRP3ufNdrOsb0DFHfltm5-J3AAohHs1oL2luwoTL-S6odDHRyCZivQL78JKyalQdzYZpPEpLinDKjAop5-rwW9--iC6gpI9K10kQpA9vn_uhnWaMDnkixLQn2s'),(1014,'Sudadera Nude Project','Sudadera beltza',90,120,'2022-05-02',1111,'https://encrypted-tbn1.gstatic.com/shopping?q=tbn:ANd9GcRtZGdage_psoSk072M-D3V1rZBn7QEBbcIdpXQR4WiRa6tx1CyV-Au_YbYzIVXUb0EXhsgGG3nPYyMDrHr3TL-11NT69qaXBb9Wdz0mTLdD8ziz-34_BZm'),(2011,'Alkandora Urdina','Alkandora urdina',8,50,'2015-09-08',2222,'https://encrypted-tbn1.gstatic.com/shopping?q=tbn:ANd9GcQdpDNfSjmbZ3vbfXpFKXoTJ77k5zBzFBRIaigiB1ghgtXv06Fs9c-fT9iO1yxyZKp7p7QD34XdbL270KYIuL68K3zwOb5k-glAP6NWQtGd1tYTTsBY9bMuX1iJ7IRf0rCCDk5iVC81fFc'),(2012,'Laukidun Alkandora','Alkandora beltza eta berdea',25,60,'2025-09-09',2222,'https://encrypted-tbn2.gstatic.com/shopping?q=tbn:ANd9GcT_wvvRpHjiplKcCvN9j2VUQ5DH087DhAcWPDXRSpYRyINFDo8mm1OWMTBV4pSGF6E80X6yiFyvBp_BkUqUM0uKmVyrpjWhXFf3y1ykzD-RL4Q9hnwhB7_CtQ'),(2013,'Alkandora Arrosa','Alkandora Arrosa',25,55,'2024-04-04',2222,'https://encrypted-tbn3.gstatic.com/shopping?q=tbn:ANd9GcR34mLoa8i5_yI2da-Ea87MTyjImECBSuCNEtSc-MO8J2VNWH9S8QwUHP5h0vw3ZiJmr3lf6rzQEd-aZJATCz5hHtfQ6TdMtGGRVo-FCCCpVcCodzk0HdXOML4'),(2014,'Alkandora Beltza','Alkandora Beltza',25,70,'2019-12-03',2222,'https://encrypted-tbn2.gstatic.com/shopping?q=tbn:ANd9GcR8apn-r0y7lXk_mPfYZuN2zl7ob9PW-_PsFJffmeVzkgXBdcZ_hHYJ1Vvt9bc8V6kT0DJq4xKQWNhzFBaxs3ot4r7JIQdCaK2OLnOYRJZRMm2AJLUwMO5z0ks'),(3011,'Adidas Kamiseta','Kamiseta urdina',15,300,'2023-11-20',3333,'https://encrypted-tbn2.gstatic.com/shopping?q=tbn:ANd9GcT3LteIVTpTIy20E4jBJz7ww71qiVTyi-9ceT-qASCW6-RxEEZgtndTDQAbK-nZT3IvM2O73lFG2RTkWnlY26jEbq_4R7k_cpAIPdlp7GR_4gF6oBEc-9Uh2NY'),(3012,'Adidas Kamiseta','Kamiseta urdin argia',15,350,'2023-11-29',3333,'https://encrypted-tbn3.gstatic.com/shopping?q=tbn:ANd9GcQa1W_n6uUnXFbatUWp615yYhmPltsh7TGeIXIMj_x5z1xo96xhvO89RbGaInomVtfenvDrmSuEYL7fvbnj0lq9ZAnfXgBy'),(3013,'Adidas Kamiseta','Kamiseta beltza',15,400,'2023-11-29',3333,'https://encrypted-tbn1.gstatic.com/shopping?q=tbn:ANd9GcQP-ll4rTQzbZ6t-XswE2RGmfZ6e1YH_1GQjKVcv6zTMGqHLIjbF9cw4KZLww_DAgZ3R_5moKNfFhh70H_UQObLawHhPUjhqjeFeO2F8hcHxVjTX31n4trl4w'),(3014,'Adidas Kamiseta','Kamiseta gorria',15,300,'2023-11-29',3333,'https://encrypted-tbn2.gstatic.com/shopping?q=tbn:ANd9GcRyEUT1CzjPxPQUWQ1YYa0qRf219VYdfWWLwH6dD-lROLWcuV1BgGHvdLrB2IHJhqVX6eVNXEYHNv36ZQHwPaP52OGhitufaB2J8arGMuTwsr8I-sqe4uGqKg'),(4011,'Galtza Kargoak','Kargo grisak',50,110,'2025-12-13',4444,'https://encrypted-tbn2.gstatic.com/shopping?q=tbn:ANd9GcRY5ALzFvFX3Qln1D3dVs_krm4IDvXSxeNxzElHLhvo1vqCloDvGH6RxOMarx64dbJyrxG06gPgxjrk8wVP3D-VqG1oBcogp1Y3pxN574eex-hG-o4IeJ0NBfg78ypW7biEnEv2Tg'),(4012,'Chandal Osoa','Chandal grisa',60,120,'2025-11-12',4444,'https://encrypted-tbn0.gstatic.com/shopping?q=tbn:ANd9GcSeL7ISKwUjKtiIgELtRKUwHASZc5E1t1SRpwTulkgQ3RcN6_qZJtaemfExGWODav7BZzjJBeYWE8LcQAeWdKXrGVGip575wQ'),(4013,'Baquero Urdinak','Baquero urdinak',20,200,'2025-04-25',4444,'https://encrypted-tbn2.gstatic.com/shopping?q=tbn:ANd9GcR7X_M1PBVNWb3BEarnCWf7tp0shZ2JT3woMhi1ioCHgcaZt05jrdiC7PSfu72suhDiBw8o1dmXGAxELZ724uC7HFl2Ta99NQBPdvPT86LdNY3YN0d1p_UxHw'),(4014,'Baquero Beltzak','Baquero Beltzak',30,30,'2025-03-10',4444,'https://encrypted-tbn3.gstatic.com/shopping?q=tbn:ANd9GcTl_sV7yOiZclALHWx4TpHtQ_8TUAxQELLwZbVoxRqzDawcIAwSSZ1csMWZ7qHGEiZRPP_-zH_nxKVzqAgoGEIPRm_K1XBwKVsGiEXOq4US5PtsJCNX0UCkNQ'),(5011,'Nike Air Max Plus','Air Max Beltzak',190,150,'2026-02-25',5555,'https://encrypted-tbn1.gstatic.com/shopping?q=tbn:ANd9GcR78liEtGOxFkC6fUGb6oyITk0yN7peThyxwBSbagq1ZONJADFQZZbynOyI3ImL9AqNpBKgrC0p9EW1yZ4M7fFxO1jV8enn8MsI3Yw8ccfTI6CkQX9bCRnVxIk'),(5012,'Nike Shox TL','Nike Shox Txuriak',140,200,'2018-09-10',5555,'https://encrypted-tbn3.gstatic.com/shopping?q=tbn:ANd9GcTiEtSW0bhGMCmN_3QwV6o8_8zxieDtRJ_IcwrKowjJuWgyiIe6gZbYzu-PctMqsIWvKU_WQf_F4vmX0k4xabYt4EPrVnB94anT3AHFrJctU4Rcgsv1CM1pcg'),(5013,'Nike Air Max 90','Air Max Beltzak',150,250,'2015-01-09',5555,'https://encrypted-tbn2.gstatic.com/shopping?q=tbn:ANd9GcT8cXTCW5JTQwEeifQnLYN7T5ieWgzxyPIAZhoY2GnERWqkdnNeS0d0OOUe6RpxJPR33tEwHJT6LUPvcDLRE6th0SE3il6Ed_0mPYYv1TkzoW9gqaVEYhZGaA'),(5014,'Nike Dunk Low','Dunk Low Txuriak eta Beltzak',95,200,'2020-05-15',5555,'https://encrypted-tbn0.gstatic.com/shopping?q=tbn:ANd9GcQH8rYcROH9BbXbsKoWBsqBOauS12hhKl2GKkVU4VPDY4XbM-zC0K9NHf2wtlaR3XLuo4Yf-ghkVTV-MsML3BBVwMEMl-Jqp-vvXZC-IWgbvGxevWksw0gfAxY');
/*!40000 ALTER TABLE `produktuak` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping routines for database 'erronka4'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2026-02-27  0:07:51
