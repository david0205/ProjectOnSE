-- MySQL dump 10.13  Distrib 8.0.26, for Win64 (x86_64)
--
-- Host: localhost    Database: gearzdb
-- ------------------------------------------------------
-- Server version	8.0.26

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `addresses`
--

DROP TABLE IF EXISTS `addresses`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `addresses` (
  `id` int NOT NULL AUTO_INCREMENT,
  `customer_id` int DEFAULT NULL,
  `full_name` varchar(45) NOT NULL,
  `address_line` varchar(64) NOT NULL,
  `ward_id` int DEFAULT NULL,
  `district_id` int DEFAULT NULL,
  `city_id` int DEFAULT NULL,
  `phone_number` varchar(15) NOT NULL,
  `default_address` bit(1) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK9fkb8qaj71tiyr9htkmn7r8y5` (`city_id`),
  KEY `FKhrpf5e8dwasvdc5cticysrt2k` (`customer_id`),
  KEY `FKdu4j0drj57p0x9avyatpq2v5` (`district_id`),
  KEY `FKgafq5o69m5p2rq5q5egx2sfm` (`ward_id`),
  CONSTRAINT `FK9fkb8qaj71tiyr9htkmn7r8y5` FOREIGN KEY (`city_id`) REFERENCES `cities` (`id`),
  CONSTRAINT `FKdu4j0drj57p0x9avyatpq2v5` FOREIGN KEY (`district_id`) REFERENCES `districts` (`id`),
  CONSTRAINT `FKgafq5o69m5p2rq5q5egx2sfm` FOREIGN KEY (`ward_id`) REFERENCES `wards` (`id`),
  CONSTRAINT `FKhrpf5e8dwasvdc5cticysrt2k` FOREIGN KEY (`customer_id`) REFERENCES `customers` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `addresses`
--

LOCK TABLES `addresses` WRITE;
/*!40000 ALTER TABLE `addresses` DISABLE KEYS */;
INSERT INTO `addresses` VALUES (1,7,'Bryan Kearney','address 2',742,28,12,'0123456789',_binary '\0'),(2,7,'Mark Sixma','address 4',1048,48,4,'0555155450',_binary '\0'),(3,7,'Armin van Buuren','address 3',810,86,6,'0579224568',_binary '\0');
/*!40000 ALTER TABLE `addresses` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `brands`
--

DROP TABLE IF EXISTS `brands`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `brands` (
  `id` int NOT NULL AUTO_INCREMENT,
  `logo` varchar(128) NOT NULL,
  `name` varchar(40) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_oce3937d2f4mpfqrycbr0l93m` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `brands`
--

LOCK TABLES `brands` WRITE;
/*!40000 ALTER TABLE `brands` DISABLE KEYS */;
INSERT INTO `brands` VALUES (1,'intel-logo-vector-1.svg','Intel'),(2,'amd-logo-vector-1.svg','AMD'),(3,'msi-logo-vector.svg','MSI'),(4,'ASUS_Logo.svg','ASUS'),(5,'ASRock-Logo.wine.svg','ASRock'),(6,'Biostar-Logo.wine.svg','Biostar'),(7,'evga.svg','EVGA Corporation'),(8,'gigabyte-technology-logo-2008.svg','Gigabyte Techonology'),(9,'Western_Digital-Logo.wine.svg','WESTERN DIGITAL'),(10,'Seagate_Technology-Logo.wine.svg','Seagate'),(11,'SanDisk-Logo.wine.svg','SanDisk'),(12,'Sony-Logo.wine.svg','Sony'),(13,'Microsoft-Logo.wine.svg','Microsoft'),(14,'Samsung-Logo.wine.svg','Samsung'),(15,'Transcend_Information-Logo.wine.svg','Transcend'),(16,'Hewlett-Packard-Logo.wine.svg','HP'),(17,'ADATA-Logo.wine.svg','ADATA'),(18,'Apple_Inc.-Logo.wine.svg','Apple'),(19,'Dell-Logo.wine.svg','Dell');
/*!40000 ALTER TABLE `brands` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `brands_categories`
--

DROP TABLE IF EXISTS `brands_categories`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `brands_categories` (
  `brand_id` int NOT NULL,
  `category_id` int NOT NULL,
  PRIMARY KEY (`brand_id`,`category_id`),
  KEY `FK6x68tjj3eay19skqlhn7ls6ai` (`category_id`),
  CONSTRAINT `FK58ksmicdguvu4d7b6yglgqvxo` FOREIGN KEY (`brand_id`) REFERENCES `brands` (`id`),
  CONSTRAINT `FK6x68tjj3eay19skqlhn7ls6ai` FOREIGN KEY (`category_id`) REFERENCES `categories` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `brands_categories`
--

LOCK TABLES `brands_categories` WRITE;
/*!40000 ALTER TABLE `brands_categories` DISABLE KEYS */;
INSERT INTO `brands_categories` VALUES (1,4),(2,4),(3,5),(4,5),(3,10),(4,10),(2,12),(3,12);
/*!40000 ALTER TABLE `brands_categories` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cart_item`
--

DROP TABLE IF EXISTS `cart_item`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `cart_item` (
  `id` int NOT NULL AUTO_INCREMENT,
  `quantity` int NOT NULL,
  `customer_id` int DEFAULT NULL,
  `product_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKehe6sev71h6jfmfjyeebcu1c2` (`customer_id`),
  KEY `FKqkqmvkmbtiaqn2nfqf25ymfs2` (`product_id`),
  CONSTRAINT `FKehe6sev71h6jfmfjyeebcu1c2` FOREIGN KEY (`customer_id`) REFERENCES `customers` (`id`),
  CONSTRAINT `FKqkqmvkmbtiaqn2nfqf25ymfs2` FOREIGN KEY (`product_id`) REFERENCES `products` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=156 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cart_item`
--

LOCK TABLES `cart_item` WRITE;
/*!40000 ALTER TABLE `cart_item` DISABLE KEYS */;
INSERT INTO `cart_item` VALUES (142,11,5,1),(143,12,5,3),(144,8,5,4);
/*!40000 ALTER TABLE `cart_item` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `categories`
--

DROP TABLE IF EXISTS `categories`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `categories` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(128) NOT NULL,
  `tag` varchar(64) NOT NULL,
  `image` varchar(128) NOT NULL,
  `enabled` bit(1) NOT NULL,
  `parent_id` int DEFAULT NULL,
  `all_parent_ids` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_t8o6pivur7nn124jehx7cygw5` (`name`),
  UNIQUE KEY `UK_f1ehfphjvwf4hch5era9wxhwx` (`tag`),
  KEY `FKsaok720gsu4u2wrgbk10b5n8d` (`parent_id`),
  CONSTRAINT `FKsaok720gsu4u2wrgbk10b5n8d` FOREIGN KEY (`parent_id`) REFERENCES `categories` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=59 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `categories`
--

LOCK TABLES `categories` WRITE;
/*!40000 ALTER TABLE `categories` DISABLE KEYS */;
INSERT INTO `categories` VALUES (1,'Computer Parts','computer-parts','computer-parts.jpg',_binary '',NULL,NULL),(2,'PC/Computer Systems','computer-systems','pc.jpg',_binary '',NULL,NULL),(4,'CPUs/Processors','cpus-processors','cpu.jpg',_binary '',1,'-1-'),(5,'Laptops','laptops','laptop.jpg',_binary '',NULL,NULL),(6,'Games Consoles','games-consoles','games-consoles.jpg',_binary '',NULL,NULL),(7,'Networking','networking','networking.jpg',_binary '',NULL,NULL),(8,'Office Solutions','office-solutions','office-solutions.jpg',_binary '',NULL,NULL),(9,'Software & Services','software-and-services','software.jpg',_binary '',NULL,NULL),(10,'Motherboards','pc-motherboard','motherboard.jpg',_binary '',1,'-1-'),(11,'Computer Memory','computer-memory','ram.jpg',_binary '',1,'-1-'),(12,'Graphics Cards','graphics-cards','gpus.jpg',_binary '',1,'-1-'),(13,'Solid State Drives','ssds','ssd.jpg',_binary '',1,'-1-'),(14,'Hard Disk Drives','hdds','hdd-closeup.jpg',_binary '',1,'-1-'),(15,'Fans, Heatsinks & Water Cooling','air-water-cooling','coolers.jpg',_binary '',1,'-1-'),(16,'Sound Cards','sound-cards','sound-card.jpg',_binary '',1,'-1-'),(17,'Audio','audio','audio.jpg',_binary '',NULL,NULL),(18,'Computer Peripherals','pc-peripherals','mouse-keyboard-headset.jpg',_binary '',NULL,NULL),(19,'PC Monitors','pc-monitors','monitor.jpg',_binary '',18,'-18-'),(20,'Cables & Connector','cables-and-connectors','cables.jpg',_binary '',NULL,NULL),(21,'Gaming PCs','gaming-desktops','gaming-pc.jpg',_binary '',2,'-2-'),(22,'Xbox Systems','xbox-systems','xbox.jpg',_binary '',6,'-6-'),(23,'Office PCs','office-pc','office-pc.jpg',_binary '',2,'-2-'),(24,'Graphic PCs','graphic-pc','graphic pc.jpg',_binary '',2,'-2-'),(25,'PCs by CPU','pc-by-cpu','pc by cpu.jpg',_binary '',2,'-2-'),(26,'PCs by VGA','pc-by-vga','pc by vga.jpg',_binary '',2,'-2-'),(27,'PCs by STATUS','pc-by-status','pc by status.jpg',_binary '',2,'-2-'),(28,'PC SERVER','pc-server','pc server.jpg',_binary '',2,'-2-'),(29,'Desktop Set','desktop-set','desktop set.jpg',_binary '',2,'-2-'),(30,'Office Laptops','office-laptop','laptop office.jpg',_binary '',5,'-5-'),(31,'Gaming Laptops','gaming-laptop','laptop gaming.jpg',_binary '',5,'-5-'),(32,'Laptops by Size','laptop-by-size','laptop by size.jpg',_binary '',5,'-5-'),(33,'Laptops by Price','laptop-by-price','laptop by price.jpg',_binary '',5,'-5-'),(34,'Laptops by CPU','laptop-by-cpu','laptop by cpu.jpg',_binary '',5,'-5-'),(35,'Laptop Accessories','laptop-accessories','laptop accessories.jpg',_binary '',5,'-5-'),(36,'Playstation','playstation','playstation.jpg',_binary '',6,'-6-'),(37,'Nintendo','nintendo','nintendo.jpg',_binary '',6,'-6-'),(38,'Home Networking','home-networking','home networking.jpg',_binary '',7,'-7-'),(39,'Commercial Networking','commercial-networking','commercial networking.jpg',_binary '',7,'-7-'),(40,'Server & Components','server-components','server&components.jpg',_binary '',7,'-7-'),(41,'Security Devices','security-devices','securitydevices.jpg',_binary '',7,'-7-'),(42,'Display & Printing','display-printing','display-print.jpg',_binary '',8,'-8-'),(43,'Office Technologies','office-technologies','office-technology.jpg',_binary '',8,'-8-'),(45,'Office Supplies','office-supplies','office-supplies.jpg',_binary '',8,'-8-'),(46,'Mailing & Inventory Supplies','inventory-supplies','inventory-supplies.jpg',_binary '',8,'-8-'),(48,'Software','software','software.jpg',_binary '',9,'-9-'),(49,'Business Software','business-software','business softwar.jpg',_binary '',9,'-9-'),(50,'Services','services','services.jpg',_binary '',9,'-9-'),(51,'Media & Entertainment','media-entertainment','media entertainment.jpg',_binary '',9,'-9-'),(52,'Bluetooth Speaker','bluetooth-speaker','bluetooth speaker.jpg',_binary '',17,'-17-'),(53,'PC Speaker','pc-speaker','pc speaker.jpg',_binary '',17,'-17-'),(54,'Sound Card','sound-card','soundcard.jpg',_binary '',17,'-17-'),(55,'Input Devices','input-devices','input devices.jpg',_binary '',18,'-18-'),(56,'Keyboard & Mice','keyboard-mice','keyboard mice.jpg',_binary '',18,'-18-'),(57,'HDMI','hdmi','hdmi.jpg',_binary '',20,'-20-'),(58,'VGA','vga','vga.jpg',_binary '',20,'-20-');
/*!40000 ALTER TABLE `categories` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cities`
--

DROP TABLE IF EXISTS `cities`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `cities` (
  `id` int NOT NULL AUTO_INCREMENT,
  `code` varchar(3) NOT NULL,
  `name` varchar(30) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_qww1g66rmhx352jxut53oqh3y` (`code`),
  UNIQUE KEY `UK_l61tawv0e2a93es77jkyvi7qa` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cities`
--

LOCK TABLES `cities` WRITE;
/*!40000 ALTER TABLE `cities` DISABLE KEYS */;
INSERT INTO `cities` VALUES (1,'HAN','Ha Noi'),(3,'SGN','Ho Chi Minh'),(4,'DAD','Da Nang'),(5,'VDO','Quang Ninh'),(6,'HPH','Hai Phong'),(7,'NGA','Nghe An'),(8,'HUI','Hue'),(12,'VCA','Can Tho'),(13,'PQC','Kien Giang'),(14,'LDD','Lam Dong '),(15,'KHA','Khanh Hoa');
/*!40000 ALTER TABLE `cities` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `customers`
--

DROP TABLE IF EXISTS `customers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `customers` (
  `id` int NOT NULL AUTO_INCREMENT,
  `email` varchar(128) NOT NULL,
  `password` varchar(64) NOT NULL,
  `full_name` varchar(40) DEFAULT NULL,
  `profile_pic` varchar(255) DEFAULT NULL,
  `address_line` varchar(64) DEFAULT NULL,
  `phone_number` varchar(15) DEFAULT NULL,
  `enabled` bit(1) NOT NULL,
  `verification_code` varchar(64) DEFAULT NULL,
  `created_time` datetime(6) NOT NULL,
  `city_id` int DEFAULT NULL,
  `district_id` int DEFAULT NULL,
  `ward_id` int DEFAULT NULL,
  `authentication_type` varchar(10) DEFAULT NULL,
  `reset_password_token` varchar(30) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_rfbvkrffamfql7cjmen8v976v` (`email`),
  KEY `FKcnjv7us3tih8hj2fonxov8gxw` (`city_id`),
  KEY `FKg890eq05q824vgdhv0tiuyc74` (`district_id`),
  KEY `FK8ajroxg0o18sxtmffuseyuya1` (`ward_id`),
  CONSTRAINT `FK8ajroxg0o18sxtmffuseyuya1` FOREIGN KEY (`ward_id`) REFERENCES `wards` (`id`),
  CONSTRAINT `FKcnjv7us3tih8hj2fonxov8gxw` FOREIGN KEY (`city_id`) REFERENCES `cities` (`id`),
  CONSTRAINT `FKg890eq05q824vgdhv0tiuyc74` FOREIGN KEY (`district_id`) REFERENCES `districts` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `customers`
--

LOCK TABLES `customers` WRITE;
/*!40000 ALTER TABLE `customers` DISABLE KEYS */;
INSERT INTO `customers` VALUES (5,'johnnycracker99104746@gmail.com','','Tôn Trí Thiện',NULL,'','',_binary '',NULL,'2021-11-25 16:31:57.296000',NULL,NULL,NULL,'FACEBOOK',NULL),(7,'kashimalawson@gmail.com','$2a$10$xcV8didaLPrLKBw4YOhmMOBmjI2imwbb4Kusfy6JHkbTTn5KTU3gK','Kashima Lawson',NULL,'address 1','0123456789',_binary '',NULL,'2021-11-26 03:17:55.889000',1,54,333,'DATABASE',NULL);
/*!40000 ALTER TABLE `customers` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `districts`
--

DROP TABLE IF EXISTS `districts`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `districts` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(128) NOT NULL,
  `city_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK3g7x8w4lc7qxth7ibrr5j73mn` (`city_id`),
  CONSTRAINT `FK3g7x8w4lc7qxth7ibrr5j73mn` FOREIGN KEY (`city_id`) REFERENCES `cities` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=190 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `districts`
--

LOCK TABLES `districts` WRITE;
/*!40000 ALTER TABLE `districts` DISABLE KEYS */;
INSERT INTO `districts` VALUES (1,'District 1',3),(3,'District 3',3),(4,'District 4',3),(5,'District 5',3),(6,'District 6',3),(7,'District 7',3),(8,'District 8',3),(10,'District 10',3),(11,'District 11',3),(12,'District 12',3),(13,'Thu Duc city ',3),(14,'Binh Tan district',3),(15,'Binh Thanh district',3),(16,'Go Vap district',3),(17,'Phu Nhuan district',3),(18,'Tan Binh district',3),(19,'Tan Phu district',3),(20,'Binh Chanh district',3),(21,'Can Gio district',3),(22,'Cu Chi district',3),(23,'Hoc Mon district',3),(24,'Nha Be district',3),(25,'Binh Thuy district',12),(26,'Cai Rang district',12),(27,'Ninh Kieu district',12),(28,'O Mon district',12),(29,'Thot Not district',12),(30,'Co Do district',12),(31,'Phong Dien district',12),(32,'Thoi Lai district',12),(33,'Vinh Thanh district',12),(46,'Cam Le district',4),(47,'Hai Chau district',4),(48,'Lien Chieu district ',4),(49,'Ngu Hanh Son district',4),(50,'Son Tra district',4),(51,'Thanh Khe district',4),(52,'Hoang Sa district',4),(53,'Hoa Vang district',4),(54,'Ba Dinh district',1),(55,'Bac Tu Liem district',1),(56,'Cau Giay district',1),(57,'Dong Da district',1),(58,'Ha Dong district',1),(59,'Hai Ba Trung district',1),(60,'Hoan Kiem district',1),(61,'Hoang Mai district',1),(62,'Long Bien district',1),(63,'Nam Tu Liem district',1),(64,'Tay Ho district',1),(65,'Thanh Xuan district',1),(66,'Ba Vi district',1),(67,'Chuong My district',1),(68,'Dan Phuong district',1),(69,'Dong Anh district',1),(70,'Gia Lam district',1),(71,'Hoai Duc district',1),(72,'Me Linh district',1),(73,'My Duc district',1),(74,'Phu Xuyen district',1),(75,'Phuc Tho district',1),(76,'Quoc Oai district',1),(77,'Soc Son district',1),(78,'Thach That district',1),(79,'Thanh Oai district',1),(80,'Thanh Tri district',1),(81,'Thuong Tin district',1),(82,'Ung Hoa district',1),(83,'Son Tay town',1),(84,'Do Son district',6),(85,'Duong Kinh district',6),(86,'Hai An district',6),(87,'Hong Bang district',6),(88,'Kien An district',6),(89,'Le Chan district',6),(90,'Ngo Quyen district',6),(91,'An Duong district',6),(92,'An Lao district',6),(93,'Bach Long Vi district',6),(94,'Cat Hai district',6),(95,'Kien Thuy district',6),(96,'Thuy Nguyen district',6),(97,'Tien Lang district',6),(98,'Vinh Bao district',6),(99,'A Luoi district',8),(100,'Nam Dong district',8),(106,'Phu Loc district',8),(113,'Phu Vang district',8),(114,'Quang Dien district',8),(115,'Hue city',8),(116,'Huong Thuy town',8),(117,'Huong Tra town',8),(118,'Cam Lam district',15),(119,'Truong Sa district',15),(120,'Dien Khanh district',15),(121,'Khanh Son district',15),(122,'Khanh Vinh district',15),(123,'Van Ninh district',15),(124,'Cam Ranh city',15),(125,'Nha Trang city',15),(126,'Ninh Hoa town',15),(127,'An Bien district',13),(128,'An Minh district',13),(129,'Chau Thanh district',13),(130,'Giang Thanh district',13),(131,'Giong Rieng district',13),(132,'Go Quao district',13),(133,'Hon Dat district',13),(134,'Kien Hai district',13),(135,'Vinh Thuan district',13),(136,'Ha Tien city',13),(137,'Phu Quoc city',13),(138,'Rach Gia city',13),(139,'Bao Lam district',14),(140,'Cat Tien district',14),(141,'Da Huoai district',14),(142,'Da Teh district',14),(143,'Đam Rong district',14),(145,'Di Linh district',14),(146,'Don Duong district',14),(147,'Duc Trong district',14),(148,'Lac Duong district',14),(149,'Lam Ha district',14),(150,'Bao Loc city',14),(151,'Da Lat city',14),(152,'Anh Son district',7),(153,'Con Cuong district',7),(154,'Dien Chau district',7),(155,'Do Luong district',7),(156,'Hung Nguyen district',7),(157,'Ky Son district',7),(158,'Nam Dan district',7),(159,'Nghi Loc district',7),(160,'Nghia Dan district',7),(161,'Que Phong district',7),(162,'Quy Chau district',7),(163,'Quy Hop district',7),(164,'Quynh Luu district',7),(165,'Tan Ky district',7),(166,'Thanh Chuong district',7),(167,'Tuong Duong district',7),(168,'Yen Thanh district',7),(169,'Vinh city',7),(170,'Cua Lo town',7),(171,'Hoang Mai town',7),(172,'Thai Hoa town',7),(173,'Ba Che district',5),(174,'Binh Lieu district',5),(175,'Co To district',5),(176,'Dam Ha district',5),(177,'Hai Ha district',5),(178,'Tien Yen district',5),(179,'Van Don district',5),(180,'Cam Pha city',5),(181,'Ha Long city',5),(182,'Mong Cai city',5),(183,'Uong Bi city',5),(184,'Dong Trieu town',5),(185,'Quang Yen town',5);
/*!40000 ALTER TABLE `districts` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `order_details`
--

DROP TABLE IF EXISTS `order_details`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `order_details` (
  `id` int NOT NULL AUTO_INCREMENT,
  `quantity` int NOT NULL,
  `shipping_cost` float NOT NULL,
  `subtotal` float NOT NULL,
  `unit_price` float NOT NULL,
  `order_id` int DEFAULT NULL,
  `product_id` int DEFAULT NULL,
  `product_cost` float NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKjyu2qbqt8gnvno9oe9j2s2ldk` (`order_id`),
  KEY `FK4q98utpd73imf4yhttm3w0eax` (`product_id`),
  CONSTRAINT `FK4q98utpd73imf4yhttm3w0eax` FOREIGN KEY (`product_id`) REFERENCES `products` (`id`),
  CONSTRAINT `FKjyu2qbqt8gnvno9oe9j2s2ldk` FOREIGN KEY (`order_id`) REFERENCES `orders` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `order_details`
--

LOCK TABLES `order_details` WRITE;
/*!40000 ALTER TABLE `order_details` DISABLE KEYS */;
INSERT INTO `order_details` VALUES (5,4,26.72,419.972,104.993,3,1,398.973);
/*!40000 ALTER TABLE `order_details` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `orders`
--

DROP TABLE IF EXISTS `orders`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `orders` (
  `id` int NOT NULL AUTO_INCREMENT,
  `address_line` varchar(64) NOT NULL,
  `full_name` varchar(45) NOT NULL,
  `phone_number` varchar(15) NOT NULL,
  `city` varchar(30) NOT NULL,
  `delivery_complete_date` datetime(6) DEFAULT NULL,
  `district` varchar(128) NOT NULL,
  `estimated_delivery_days` int NOT NULL,
  `order_time` datetime(6) DEFAULT NULL,
  `payment_method` varchar(255) DEFAULT NULL,
  `shipping_cost` float NOT NULL,
  `status` varchar(255) DEFAULT NULL,
  `subtotal` float NOT NULL,
  `tax` float NOT NULL,
  `total` float NOT NULL,
  `ward` varchar(30) NOT NULL,
  `customer_id` int DEFAULT NULL,
  `product_cost` float NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKpxtb8awmi0dk6smoh2vp1litg` (`customer_id`),
  CONSTRAINT `FKpxtb8awmi0dk6smoh2vp1litg` FOREIGN KEY (`customer_id`) REFERENCES `customers` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `orders`
--

LOCK TABLES `orders` WRITE;
/*!40000 ALTER TABLE `orders` DISABLE KEYS */;
INSERT INTO `orders` VALUES (3,'address 1','Kashima Lawson','0123456789','Ha Noi','2021-12-08 19:24:08.166000','Ba Dinh district',6,'2021-12-02 19:24:08.166000','PAYPAL',26.72,'PAID',419.972,0,446.692,'Dien Bien ward',7,398.973);
/*!40000 ALTER TABLE `orders` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `product_images`
--

DROP TABLE IF EXISTS `product_images`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `product_images` (
  `id` int NOT NULL AUTO_INCREMENT,
  `image` varchar(255) NOT NULL,
  `product_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKqnq71xsohugpqwf3c9gxmsuy` (`product_id`),
  CONSTRAINT `FKqnq71xsohugpqwf3c9gxmsuy` FOREIGN KEY (`product_id`) REFERENCES `products` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product_images`
--

LOCK TABLES `product_images` WRITE;
/*!40000 ALTER TABLE `product_images` DISABLE KEYS */;
INSERT INTO `product_images` VALUES (1,'11900K-2.jpg',1),(2,'11900K-5.jpg',1),(3,'11900K-4.jpg',1),(4,'11900K-3.jpg',1),(6,'5950x-3.jpg',2),(7,'5950x-4.jpg',2),(8,'5950x-5.jpg',2),(9,'11900-4.jpg',3),(10,'11900-3.jpg',3),(11,'11900-2.jpg',3),(12,'11900-5.jpg',3),(13,'5900x-3.jpg',4),(14,'5900x-4.jpg',4),(15,'5900x-2.jpg',4);
/*!40000 ALTER TABLE `product_images` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `product_specifications`
--

DROP TABLE IF EXISTS `product_specifications`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `product_specifications` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `value` varchar(255) NOT NULL,
  `product_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKbets5sov4bn9d2wy8vqathw6d` (`product_id`),
  CONSTRAINT `FKbets5sov4bn9d2wy8vqathw6d` FOREIGN KEY (`product_id`) REFERENCES `products` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=31 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product_specifications`
--

LOCK TABLES `product_specifications` WRITE;
/*!40000 ALTER TABLE `product_specifications` DISABLE KEYS */;
INSERT INTO `product_specifications` VALUES (1,'Max clock speed','Up to 5.3 GHz',1),(2,'Base clock speed','3.5 GHz',1),(3,'Cores/Threads','8/16',1),(4,'Unlocked and overclockable','Yes',1),(5,'Boost technology','Intel Thermal Velocity Boost Technology',1),(6,'TDP','125W',1),(7,'Intel Smart Cache','16M',1),(8,'Motherboard compatibility','400 and 500 series',1),(9,'Cores/Threads','16/32',2),(10,'Max Boost','Up to 4.9 GHz',2),(11,'Cache (L2+L3)','72MB',2),(12,'TDP','105W',2),(13,'PCIe Version','4.0',2),(14,'Unlocked for Overclocking','Yes + Precision Boost Overdrive',2),(15,'Sockets Supported','AM4 on AMD 500 Series motherboards',2),(16,'Max clock speed','Up to 5.2 GHz',3),(17,'Base clock speed','2.5 GHz',3),(18,'Cores/Threads','8/16',3),(19,'Unlocked and overclockable','No',3),(20,'Boost technology','Intel Thermal Velocity Boost Technology',3),(21,'TDP','65W',3),(22,'Intel Smart Cache','16M',3),(23,'Motherboard compatibility','400 and 500 series',3),(24,'Cores/Threads','12/24',4),(25,'Max Boost','Up to 4.8 GHz',4),(26,'Cache (L2+L3)','70MB',4),(27,'TDP','105W',4),(28,'PCIe Version','4.0',4),(29,'Unlocked for Overclocking','Yes + Precision Boost Overdrive',4),(30,'Sockets Supported','AM4 on AMD 500 Series motherboards',4);
/*!40000 ALTER TABLE `product_specifications` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `products`
--

DROP TABLE IF EXISTS `products`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `products` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(256) NOT NULL,
  `tag` varchar(64) NOT NULL,
  `short_description` varchar(512) NOT NULL,
  `full_description` varchar(4096) NOT NULL,
  `created_time` datetime(6) DEFAULT NULL,
  `updated_time` datetime(6) DEFAULT NULL,
  `enabled` bit(1) NOT NULL,
  `in_stock` bit(1) DEFAULT NULL,
  `price` float NOT NULL,
  `discount_percentage` float DEFAULT NULL,
  `length` float NOT NULL,
  `weight` float NOT NULL,
  `height` float NOT NULL,
  `width` float NOT NULL,
  `main_image` varchar(255) NOT NULL,
  `brand_id` int DEFAULT NULL,
  `category_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_o61fmio5yukmmiqgnxf8pnavn` (`name`),
  UNIQUE KEY `UK_estf88dbc3shq5ru812knyndc` (`tag`),
  KEY `FKa3a4mpsfdf4d2y6r8ra3sc8mv` (`brand_id`),
  KEY `FKog2rp4qthbtt2lfyhfo32lsw9` (`category_id`),
  FULLTEXT KEY `product_FTS` (`name`,`short_description`,`full_description`),
  CONSTRAINT `FKa3a4mpsfdf4d2y6r8ra3sc8mv` FOREIGN KEY (`brand_id`) REFERENCES `brands` (`id`),
  CONSTRAINT `FKog2rp4qthbtt2lfyhfo32lsw9` FOREIGN KEY (`category_id`) REFERENCES `categories` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `products`
--

LOCK TABLES `products` WRITE;
/*!40000 ALTER TABLE `products` DISABLE KEYS */;
INSERT INTO `products` VALUES (1,'Intel Core i9-11900K','Intel-Core-i9-11900K','<div>The 11th generation Intel Core Rocket Lake-S processor takes you to the next level of power with PCI-Express 4.0 support. The Intel Core i9-11900K processor features 8 Cores (16 Threads), 16MB of cache and turbo speeds up to 5.3GHz.<br></div>','<div>The 11th generation Intel Core Rocket Lake-S processor propels you to even more power thanks to its compatibility with the PCI-Express 4. 0 standard and the presence of Hyper Threading on the entire range from Core i3 to Core i9. Its Turbo frequencies go ever higher while maintaining the same TDP as previous generations. Peak performance and smoothness are the order of the day for everyone, whether it\'s for office, creative or gaming use. The Intel Core i9-11900K processor with 8 Cores (16 Threads), 16MB cache and Turbo frequencies of up to 5.3GHz is aimed at advanced gamers and users who need a processor that is at home in any situation.</div><div>From compelling gaming performance to virtual reality without slowing down to intensive multitasking, the 11th generation Intel Core processors are ultra versatile and nothing is too much trouble for them!<br></div>',NULL,'2021-12-02 16:16:14.452000',_binary '',_binary '',149.99,30,6,1,4,3,'11900K-1.jpg',1,4),(2,'AMD Ryzen 9 5950X','AMD-Ryzen-9-5950X','The AMD Ryzen 9 5950X processor is the ultimate video game and streaming processor: 16 Cores, 32 Threads and 72MB Game Cache. Not to mention the native and boosted frequencies, which are at an all-time high, so you can enjoy your favorite games in the best possible conditions.<br>','<div style=\"text-align: center;\"><b style=\"font-size: 1rem;\">THE ULTIMATE PROCESSOR FOR GAMING AND STREAMING</b></div><div style=\"text-align: justify;\">The <b>AMD Ryzen 9 5950X processor</b> is the ultimate gaming and streaming processor: 1<b>6 Cores, 32 Threads</b> and <b>72MB Game Cache</b>. Not to mention the native and boosted frequencies, which reach new heights so you can enjoy your favorite games in the best possible conditions. Combine it with a high-performance graphics card and you can do it all, EASILY.</div><div><div style=\"text-align: center;\"><b><br></b></div><div style=\"text-align: center;\"><b>16 NATIVE CORES AND 32 LOGICAL CORES</b><br></div></div><div style=\"text-align: justify;\">The <b>AMD Ryzen 9 5950X desktop processor</b> offers <b>16 native and 32 logical cores</b> for smooth multitasking. Thanks to its <b>high native frequency</b> and its <b>Turbo Core</b> mode, the next-generation AMD Ryzen CPU delivers exceptional performance in gaming, intensive multitasking, video editing, streaming, 3D modeling and more. The <b>64MB L3 cache</b> also enables ultra-fast processing of large numbers of instructions with low latency.<br></div>',NULL,'2021-11-22 18:59:47.911000',_binary '',_binary '\0',18990000,0,2,1,1,2,'5950x-2.jpg',2,4),(3,'Intel Core i9-11900','Intel-Core-i9-11900','<div><br></div>','<div><br></div>',NULL,'2021-11-22 19:23:14.392000',_binary '',_binary '',11990000,0,1,1,1,1,'11900-1.jpg',1,4),(4,'AMD Ryzen 9 5900X','AMD-Ryzen-9-5900X','<div><br></div>','<div><br></div>',NULL,'2021-11-22 19:50:40.264000',_binary '',_binary '',14990000,0,1,1,1,1,'5900x-1.jpg',2,4);
/*!40000 ALTER TABLE `products` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `roles`
--

DROP TABLE IF EXISTS `roles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `roles` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(40) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_ofx66keruapi6vyqpv6f2or37` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `roles`
--

LOCK TABLES `roles` WRITE;
/*!40000 ALTER TABLE `roles` DISABLE KEYS */;
INSERT INTO `roles` VALUES (1,'Admin'),(3,'Assistant'),(4,'Editor'),(2,'Salesperson'),(5,'Shipper');
/*!40000 ALTER TABLE `roles` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `settings`
--

DROP TABLE IF EXISTS `settings`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `settings` (
  `key` varchar(128) NOT NULL,
  `value` varchar(1024) NOT NULL,
  `category` varchar(45) NOT NULL,
  PRIMARY KEY (`key`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `settings`
--

LOCK TABLES `settings` WRITE;
/*!40000 ALTER TABLE `settings` DISABLE KEYS */;
INSERT INTO `settings` VALUES ('COPYRIGHT','© 2021 GearZ, DLT Ltd.','GENERAL'),('CUSTOMER_VERIFY_CONTENT','<div><span style=\"font-size:16px;\">Hi, [[name]]!<br></span></div><div><span style=\"font-size:16px;\"><br></span></div><div><span style=\"font-size:16px;\">Thank you for sign up to our website!</span></div><div><span style=\"font-size:16px;\"></span></div><span style=\"font-size:16px;\">\r\nClick the link below to verify your account<br>\r\n<br>\r\n<h2><a href=\"[[URL]]\" target=\"_blank\">VERIFY</a></h2></span><span style=\"font-size:16px;\"><br></span><div>We hope you have a good day.<br></div><div><br></div><span style=\"font-size:16px;\">- GearZ Team.</span>','MAIL_TEMPLATE'),('CUSTOMER_VERIFY_SUBJECT','GearZ - Verify your account','MAIL_TEMPLATE'),('MAIL_FROM','gearz.project@gmail.com','MAIL_SERVER'),('MAIL_HOST','smtp.gmail.com','MAIL_SERVER'),('MAIL_PASSWORD','ijtmrsxpamoqzjci','MAIL_SERVER'),('MAIL_PORT','587','MAIL_SERVER'),('MAIL_SENDER_NAME','GearZ Team','MAIL_SERVER'),('MAIL_USERNAME','gearz.project@gmail.com','MAIL_SERVER'),('ORDER_CONFIRMATION_CONTENT','<div><div>Confirmation of order ID #[[orderId]]</div></div><div><br></div><div><div>Hello [[name]],</div><div><br></div><div>We\'re happy to let you know that we’ve received your order.</div><div>Once your package ships, we will send you an email with a tracking number and link so you can see the movement of your package.</div><div><br></div><div>Summary of your order:</div><div>- Order ID: [[orderId]]</div><div>- Order Time: [[orderTime]]</div><div>- Delivery Address: [[shippingAddress]]</div><div>- Total: [[total]]</div><div>- Payment method: [[paymentMethod]]</div><div>Click here to view full details of your order (login required)</div><div><br></div><div>If you have any questions, contact us here or call us on 1800 1234!</div><div><br></div><div>We are here to help!</div></div><div><br></div><div>- GearZ Team.<br></div>','MAIL_TEMPLATE'),('ORDER_CONFIRMATION_SUBJECT','Order confirmation ID #[[orderId]]','MAIL_TEMPLATE'),('PAYPAL_API_BASE_URL','https://api-m.sandbox.paypal.com','PAYMENT'),('PAYPAL_API_CLIENT_ID','AcrO6c2vvJE0EmwA73s0spdq8Sp0Tjn3kAI6tXdwE0Xlyfyhx-xg8blhlXQ6-AfYScvz9EemYuDUxClA','PAYMENT'),('PAYPAL_API_CLIENT_SECRET','EH0zbohjhRvFve8LMgCA7k8X052O1o9ybvvYfk_weQAeGb_5gbHvnPSIiIfaRtyk43Gh_bCzibl2l6iL','PAYMENT'),('SITE_LOGO','/site-logo/gearz-logo.svg','GENERAL'),('SITE_NAME','GearZ','GENERAL'),('SMTP_AUTH','true','MAIL_SERVER'),('SMTP_SECURED','true','MAIL_SERVER');
/*!40000 ALTER TABLE `settings` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `shipping_rates`
--

DROP TABLE IF EXISTS `shipping_rates`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `shipping_rates` (
  `id` int NOT NULL AUTO_INCREMENT,
  `city_id` int DEFAULT NULL,
  `district` varchar(30) NOT NULL,
  `rate` float NOT NULL,
  `days` int NOT NULL,
  `cod_supported` bit(1) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK8r45ghl7ecwuplujqqxa5l2uk` (`city_id`),
  CONSTRAINT `FK8r45ghl7ecwuplujqqxa5l2uk` FOREIGN KEY (`city_id`) REFERENCES `cities` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `shipping_rates`
--

LOCK TABLES `shipping_rates` WRITE;
/*!40000 ALTER TABLE `shipping_rates` DISABLE KEYS */;
INSERT INTO `shipping_rates` VALUES (1,12,'Cai Rang district',1.2,1,_binary ''),(2,12,'Ninh Kieu district',1.3,1,_binary ''),(3,12,'O Mon district',1.4,1,_binary '\0'),(4,12,'Phong Dien district',1.8,1,_binary ''),(5,12,'Thoi Lai district',2.35,2,_binary ''),(6,12,'Thot Not district',1.55,1,_binary ''),(7,4,'Cam Le district',3.58,3,_binary '\0'),(8,4,'Hai Chau district',4.56,4,_binary ''),(9,4,'Hoa Vang district',3.88,3,_binary ''),(10,4,'Hoang Sa district',4.78,4,_binary ''),(11,4,'Lien Chieu district ',4.63,4,_binary '\0'),(12,4,'Son Tra district',3.99,4,_binary ''),(13,4,'Thanh Khe district',3.79,3,_binary '\0'),(14,1,'Ba Dinh district',6.68,6,_binary ''),(15,1,'Ba Vi district',7.23,6,_binary '\0'),(16,1,'Bac Tu Liem district',6.39,6,_binary '\0'),(17,1,'Cau Giay district',6.69,7,_binary '\0'),(18,1,'Chuong My district',7.89,7,_binary '\0'),(19,1,'Dan Phuong district',6.54,6,_binary '\0'),(20,1,'Dong Anh district',7.33,8,_binary ''),(21,1,'Gia Lam district',5.99,6,_binary '\0'),(22,1,'Ha Dong district',7.19,7,_binary ''),(23,1,'Hai Ba Trung district',6.89,6,_binary '\0'),(24,1,'Hoan Kiem district',7.52,7,_binary '\0');
/*!40000 ALTER TABLE `shipping_rates` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users` (
  `id` int NOT NULL AUTO_INCREMENT,
  `email` varchar(128) NOT NULL,
  `enabled` bit(1) NOT NULL,
  `first_name` varchar(20) NOT NULL,
  `last_name` varchar(20) NOT NULL,
  `password` varchar(64) NOT NULL,
  `profile_pic` varchar(64) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_6dotkott2kjsp8vw4d0m25fb7` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=30 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (1,'18110050@student.hcmute.edu.vn',_binary '','Thien','Ton Tri','$2a$10$nMLa8vKAhRnCb/dk6244GOtAr39HSNdYo.dOrLBEIpNyXepZ39vxG','2021-10-03_175547.png'),(2,'18110029@student.hcmute.edu.vn',_binary '','Long','Tran Duc','$2a$10$pp6GLyht1kBH8RR5n4FJfuWxF9qBVLoyLt8ZqcnGG50dW7c6RFYgm','2021-10-03_184745.png'),(3,'18110007@student.hcmute.edu.vn',_binary '','Duy','Huynh Quang','$2a$10$ksPRM3zqhbxaP9FDOmiI2O6dtXgDWwMKvqo74PE8P5j2qzi6Oi4vW','2021-10-04_002546.png'),(6,'jt12@gmail.com',_binary '','Jennifer','Taylor','$2a$10$yVzHbGRG739WEll4nSVXb.Rtb48BVZCOcELIIUkLFM/IhGFoa3Q3u','image.jpg'),(7,'sli@gmail.com',_binary '','Susan','Li','$2a$10$m6mpPSEqk6o0Ye/yycLTWeBCBY8FdCUKGUzz4Sf3cyc6IPa.5zdGW','image2.jpg'),(8,'asd@gmail.com',_binary '','Sarah','Lam','$2a$10$YjkpQBTnwoBPT7nwOAfGE.TcTSnx6FA7DAhn7u5Y2oAQKg.TL.tnS','image3.jpg'),(9,'job@gmail.com',_binary '','Jack','O\'Brien','$2a$10$melWKKWGHM.T0J/s/e3vquKEf6nJ9vqLyuR7hG/WL4tK5Ipn0DAdC','image4.jpg'),(10,'hm@gmail.com',_binary '','Harry','Murphy','$2a$10$8xRYx.6Hho0FfqG.ElHO5.H7Zyx4iWPvP6btBdQ.FqRQ19xgf4SkC','image5.jpg'),(11,'ej@gmail.com',_binary '','Emily','Jones','$2a$10$nPDGzB83PtlHxtsFtcCeUOPUYbGQLy6/BzjgkJzITuz056hEd4oBW','image6.jpg'),(12,'jon@gmail.com',_binary '','Jessica','O\'Neill','$2a$10$CgFJC52Wjk3OucVmS9fYW..gibfAAWWRvRJv8GqgDzFOJ0yLhlM8K','image7.jpg'),(13,'ew@gmail.com',_binary '','Elizabeth','White','$2a$10$UXeeO48RvzalW4vB3WnK.eWoWSM9Nt8Ikr5GokcVOxhkrpolnm29a','image8.jpg'),(14,'ekr@gmail.com',_binary '\0','Emma','Rodriguez','$2a$10$5P5UskM.5RvWOdll.0xwje9Hksu7B1NT.g7tl9yo3BllcEiP74HJS','image9.jpg'),(15,'te@gmail.com',_binary '\0','Thomas','Evans','$2a$10$bzwO.uXSYCeKi4wmqMw/qehTctcAG84JYFRI4Mg/rvUDZdn9Xt5Bm','image10.jpg'),(16,'dharman@gmail.com',_binary '','Darien','Harman','$2a$10$adue1npBeigFoHYOr1EMS.6IxH/gy4RejLqHyFPbshMdjfLOtdnCm','image11.jpg'),(17,'gmanscott@gmail.com',_binary '\0','Scott','Goodman','$2a$10$xAvggYdURa8c8gz31HMpHO2i5LuxKzQI4yDDPsrIhmz/nn1qaFIze','image12.jpg'),(18,'almurphy@gmail.com',_binary '','Alvin','Murphy','$2a$10$UdLxfUaC0Aw24GHp/DEhu.BKV/aPppvNQg8nZ.wU6phcw8JIPNRYm','image13.jpg'),(19,'abeake@gmail.com',_binary '','Alexia','Beake','$2a$10$1167RJg4EnNXRh5iFbj/ieZiARSpVg08RyRqqKZ8EBN2UCOlNSFc2','image14.jpg'),(20,'bb@gmail.com',_binary '\0','Bethanie','Blackburn','$2a$10$rkNJ4viGr7t4.BU3qhWJ4O8j4M6F9TcBCeX73EtpoeeHmial1OAPq','image15.jpg'),(21,'maxbr@gmail.com',_binary '','Max','Burrell','$2a$10$KlboHMh1m2laxgB8RbdwCeN1Jw.6kYQ/qIrXOmCg4jEJJHIkVWWju','image16.jpg'),(22,'edwebster@gmail.com',_binary '','Edwin','Webster','$2a$10$Snopw86F1wr.LzZSoV02T.HC.FtIyrhDDxv9o63rfhaH0Op/WIjnS','image17.jpg'),(23,'bdobbs@gmail.com',_binary '','Brendon','Dobbs','$2a$10$VjulAMeVtaZ9KkAzxmOa4eSQkuoFVbVFb7tHXQMOiPXC/dBRxjYEK','image18.jpg'),(24,'britt.warner@gmail.com',_binary '\0','Brittany','Warner','$2a$10$/vrOUVueCzp/7SgQR9TS5.F19usE0nV53voSSkF4.Df0bDnQ2o1FK','image19.jpg'),(25,'lyda.moses@gmail.com',_binary '\0','Lyda','Moses','$2a$10$icN3oC8dzJU6gWOsrzhbzeHre3PhnxUN4RJfmeVpY6/ocgNIwRhni','image20.jpg'),(26,'lmurga@gmail.com',_binary '','Lawrence','Murgatroyd','$2a$10$SzhvzibYSkLgjFwfIORtL.tGr6LaWwe4NeCs/Fac7jMdotbjWIPPS','image21.jpg'),(27,'sc919@gmail.com',_binary '','Sophia','Cornell','$2a$10$j1mes5GnvoAxIKF4l69y9eYiekX.r/3khrCaZY8rQJ.rNsd313bXm','image22.jpg');
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users_roles`
--

DROP TABLE IF EXISTS `users_roles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users_roles` (
  `user_id` int NOT NULL,
  `role_id` int NOT NULL,
  PRIMARY KEY (`user_id`,`role_id`),
  KEY `FKj6m8fwv7oqv74fcehir1a9ffy` (`role_id`),
  CONSTRAINT `FK2o0jvgh89lemvvo17cbqvdxaa` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`),
  CONSTRAINT `FKj6m8fwv7oqv74fcehir1a9ffy` FOREIGN KEY (`role_id`) REFERENCES `roles` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users_roles`
--

LOCK TABLES `users_roles` WRITE;
/*!40000 ALTER TABLE `users_roles` DISABLE KEYS */;
INSERT INTO `users_roles` VALUES (1,1),(2,1),(3,1),(6,2),(8,2),(10,2),(12,2),(13,2),(14,2),(19,2),(20,2),(21,2),(22,2),(25,2),(26,2),(27,2),(6,3),(7,3),(10,3),(20,3),(24,3),(26,3),(10,4),(11,4),(12,4),(14,4),(16,4),(19,4),(21,4),(24,4),(26,4),(27,4),(6,5),(9,5),(15,5),(17,5),(18,5),(23,5);
/*!40000 ALTER TABLE `users_roles` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `wards`
--

DROP TABLE IF EXISTS `wards`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `wards` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(30) NOT NULL,
  `district_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKfjqt744bo800mb5uax74lav8k` (`district_id`),
  CONSTRAINT `FKfjqt744bo800mb5uax74lav8k` FOREIGN KEY (`district_id`) REFERENCES `districts` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1716 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `wards`
--

LOCK TABLES `wards` WRITE;
/*!40000 ALTER TABLE `wards` DISABLE KEYS */;
INSERT INTO `wards` VALUES (1,'An Khanh ward',13),(2,'An Loi Dong ward',13),(3,'An Phu ward',13),(4,'Binh Chieu ward',13),(5,'Binh Tho ward',13),(6,'Binh Trung Dong ward',13),(7,'Binh Trung Tay ward',13),(8,'Cat Lai ward',13),(9,'Hiep Binh Chanh ward',13),(10,'Hiep Binh Phuoc ward',13),(11,'Hiep Phu ward',13),(12,'Lieu Chieu ward',13),(13,'Linh Dong ward',13),(14,'Linh Tay ward',13),(15,'Linh Trung ward',13),(16,'Linh Xuan ward',13),(17,'Long Binh ward',13),(18,'Long Phuoc ward',13),(19,'Long Thanh My ward',13),(20,'Long Truong ward',13),(21,'Phu Huu ward',13),(22,'Phuong Binh ward',13),(23,'Phuoc Long A ward',13),(24,'Phuoc Long B ward',13),(25,'Tam Binh ward',13),(26,'Tam Phu ward',13),(27,'Tan Phu ward',13),(28,'Tang Nhon Phu A ward',13),(29,'Tang Nhon Phu B ward',13),(30,'Thao Dien ward',13),(31,'Thu Thiem ward',13),(32,'Thanh My Loi ward',13),(33,'Truong Thanh ward',13),(34,'Truong Tho ward',13),(35,'Ben Nghe ward',1),(36,'Ben Thanh ward',1),(37,'Cau Kho ward',1),(38,'Cau Ong Lanh ward',1),(39,'Co Giang ward',1),(40,'Da Kao ward',1),(41,'Nguyen Cu Trinh ward',1),(42,'Nguyen Thai Binh ward',1),(43,'Pham Ngu Lao ward',1),(44,'Tan Dinh ward',1),(45,'Ward 1',3),(46,'Ward 2',3),(47,'Ward 3',3),(48,'Ward 4',3),(49,'Ward 5',3),(50,'Ward 6',3),(51,'Ward 7',3),(52,'Ward 8',3),(53,'Ward 9',3),(54,'Ward 10',3),(55,'Ward 11',3),(56,'Ward 12',3),(57,'Ward 13',3),(58,'Ward 14',3),(59,'Vo Thi Sau ward',3),(60,'Ward 1',10),(61,'Ward 2',10),(62,'Ward 3',10),(63,'Ward 4',10),(64,'Ward 5',10),(65,'Ward 6',10),(66,'Ward 7',10),(67,'Ward 8',10),(68,'Ward 9',10),(69,'Ward 10',10),(70,'Ward 11',10),(71,'Ward 12',10),(72,'Ward 13',10),(73,'Ward 14',10),(74,'Ward 15',10),(75,'Ward 1',11),(76,'Ward 2',11),(77,'Ward 3',11),(78,'Ward 4',11),(79,'Ward 5',11),(80,'Ward 6',11),(81,'Ward 7',11),(82,'Ward 8',11),(83,'Ward 9',11),(84,'Ward 10',11),(85,'Ward 11',11),(86,'Ward 12',11),(87,'Ward 13',11),(88,'Ward 14',11),(89,'Ward 15',11),(90,'Ward 16',11),(91,'An Phu Dong ward',12),(92,'Dong Hung Thuan ward',12),(93,'Hiep Thanh ward',12),(94,'Tan Chanh Hiep ward',12),(95,'Tan Hung Thuan ward',12),(96,'Tan Thoi Hiep ward',12),(97,'Tan Thoi Nhat ward',12),(98,'Thanh Loc ward',12),(99,'Thanh Xuan ward',12),(100,'Thoi An ward',12),(101,'Trung My Tay ward',12),(102,'Ward 1',4),(103,'Ward 2',4),(104,'Ward 3',4),(105,'Ward 4',4),(106,'Ward 5',4),(107,'Ward 6',4),(108,'Ward 7',4),(109,'Ward 8',4),(110,'Ward 9',4),(111,'Ward 10',4),(112,'Ward 11',4),(113,'Ward 12',4),(114,'Ward 13',4),(115,'Ward 14',4),(116,'Ward 15',4),(117,'Ward 16',4),(118,'Ward 17',4),(119,'Ward 18',4),(120,'Ward 1',5),(121,'Ward 2',5),(122,'Ward 3',5),(123,'Ward 4',5),(124,'Ward 5',5),(125,'Ward 6',5),(126,'Ward 7',5),(127,'Ward 8',5),(128,'Ward 9',5),(129,'Ward 10',5),(130,'Ward 10',5),(131,'Ward 11',5),(132,'Ward 12',5),(133,'Ward 13',5),(134,'Ward 14',5),(135,'Ward 15',5),(136,'Ward 1',6),(137,'Ward 2',6),(138,'Ward 3',6),(139,'Ward 4',6),(140,'Ward 5',6),(141,'Ward 6',6),(142,'Ward 7',6),(143,'Ward 8',6),(144,'Ward 9',6),(145,'Ward 10',6),(146,'Ward 11',6),(147,'Ward 12',6),(148,'Ward 13',6),(149,'Ward 14',6),(150,'Binh Thuan ward',7),(151,'Phu My ward',7),(152,'Phu Thuan ward',7),(153,'Tan Hung ward',7),(154,'Tan Kieng ward',7),(155,'Tan Phong ward',7),(156,'Tan Phu ward',7),(157,'Tan Quy ward',7),(158,'Tan Thuan Dong ward',7),(159,'Tan Thuan Tay ward',7),(160,'Ward 1',8),(161,'Ward 2',8),(162,'Ward 3',8),(163,'Ward 4',8),(164,'Ward 5',8),(165,'Ward 6',8),(166,'Ward 7',8),(167,'Ward 8',8),(168,'Ward 9',8),(169,'Ward 10',8),(170,'Ward 11',8),(171,'Ward 12',8),(172,'Ward 13',8),(173,'Ward 14',8),(174,'Ward 15',8),(175,'Ward 16',8),(176,'An Lac ward',14),(177,'An Lac A ward',14),(178,'Binh Hung Hoa ward',14),(179,'Binh Hung Hoa A ward',14),(180,'Binh Hung Hoa B ward',14),(181,'Binh Tri Dong ward',14),(182,'Binh Tri Dong A ward',14),(183,'Binh Tri Dong B ward',14),(184,'Tan Tao ward',14),(185,' Tan Tao A ward',14),(186,'Ward 1',15),(187,'Ward 2',15),(188,'Ward 3',15),(189,'Ward 4',15),(190,'Ward 5',15),(191,'Ward 6',15),(192,'Ward 7',15),(193,'Ward 8',15),(194,'Ward 9',15),(195,'Ward 10',15),(196,'Ward 11',15),(197,'Ward 12',15),(198,'Ward 13',15),(199,'Ward 14',15),(200,'Ward 15',15),(201,'Ward 16',15),(202,'Ward 17',15),(203,'Ward 18',15),(204,'Ward 19',15),(205,'Ward 20',15),(206,'Ward 21',15),(207,'Ward 22',15),(208,'Ward 23',15),(209,'Ward 24',15),(210,'Ward 25',15),(211,'Ward 26',15),(212,'Ward 28',15),(213,'Ward 27',15),(214,'Ward 1',16),(215,'Ward 2',16),(216,'Ward 3',16),(217,'Ward 4',16),(218,'Ward 5',16),(219,'Ward 6',16),(220,'Ward 7',16),(221,'Ward 8',16),(222,'Ward 9',16),(223,'Ward 10 ',16),(224,'Ward 11',16),(225,'Ward 12',16),(226,'Ward 13',16),(227,'Ward 14',16),(228,'Ward 15',16),(229,'Ward 16',16),(230,'Ward 17',16),(231,'Ward 1',17),(232,'Ward 2',17),(233,'Ward 3',17),(234,'Ward 4',17),(235,'Ward 5',17),(236,'Ward 6',17),(237,'Ward 7',17),(238,'Ward 8',17),(239,'Ward 9',17),(240,'Ward 10',17),(241,'Ward 11',17),(242,'Ward 12',17),(243,'Ward 13',17),(244,'Ward 14',17),(245,'Ward 15',17),(246,'Ward 17',17),(247,'Ward 1',18),(248,'Ward 2',18),(249,'Ward 3',18),(250,'Ward 4',18),(251,'Ward 5',18),(252,'Ward 6',18),(253,'Ward 7',18),(254,'Ward 8',18),(255,'Ward 9',18),(256,'Ward 10',18),(257,'Ward 11',18),(258,'Ward 12',18),(259,'Ward 13',18),(260,'Ward 14',18),(261,'Ward 15',18),(262,'Hiep Tan ward',19),(263,'Hoa Thanh ward',19),(264,'Phu Thanh ward',19),(265,'Phu Tho Hoa ward',19),(266,'Phu Trung ward',19),(267,'Son Ky ward',19),(268,'Tan Quy ward',19),(269,'Tan Son Nhi ward',19),(270,'Tan Thanh ward',19),(271,'Tan Thoi Hoa ward',19),(272,'Tay Thanh ward',19),(273,'Tan Tuc town',20),(274,'An Phu Tay ward',20),(275,'Binh Chanh ward',20),(276,'Binh Hung ward',20),(277,'Binh Loi ward',20),(278,'Da Phuoc ward',20),(279,'Hung Long ward',20),(280,'Le Minh Xuan ward',20),(281,'Pham Van Hai ward',20),(282,'Phong Phu ward',20),(283,'Qui Duc ward',20),(284,'Tan Kien ward',20),(285,'Tan Nhut ward',20),(286,'Tan Quy Tay ward',20),(287,'Vinh Loc A ward',20),(288,'Vinh Loc B ward',20),(289,'Can Thanh town',21),(290,'An Thoi Dong ward',21),(291,'Binh Khanh ward',21),(292,'Long Hoa ward',21),(293,'Ly Nhon ward',21),(294,'Tam Thon Hiep ward',21),(295,'Thanh An ward',21),(296,'Cu Chi town',22),(297,'An Nhon Tay ward',22),(298,'An Phu ward',22),(299,'Binh My ward',22),(300,'Hoa Phu ward',22),(301,'Nhuan Duc ward',22),(302,'Pham Van Coi ward',22),(303,'Phu Hoa Dong ward',22),(304,'Phu My Hung ward',22),(305,'Phuoc Hiep ward',22),(306,'Phuoc Vinh An ward',22),(307,'Tan An Hoi ward',22),(308,'Tan Phu Trung ward',22),(309,'Tan Thanh Dong ward',22),(310,'Tan Thanh Tay ward',22),(311,'Tan Thong Hoi ward',22),(312,'Thai My ward',22),(313,'Trung An ward',22),(314,'Trung Lap Ha ward',22),(315,'Trung Lap Thuong ward',22),(316,'Hoc Mon town',23),(317,'Ba Diem ward',23),(318,'Dong Thanh ward',23),(319,'Nhi Binh ward',23),(320,'Tan Hiep ward',23),(321,'Tan Thoi Nhi ward',23),(322,'Tan Xuan ward',23),(323,'Thoi Tam Thon ward',23),(324,'Trung Chanh ward',23),(325,'Nha Be town',24),(326,'Hiep Phuoc ward',24),(327,'Long Thoi ward',24),(328,'Nhon Duc ward',24),(329,'Phu Xuan ward',24),(330,'Phuoc Kien ward',24),(331,'Phuoc Loc ward',24),(332,'Cong Vi ward',54),(333,'Dien Bien ward',54),(334,'Doi Can ward',54),(335,'Giang Vo ward',54),(336,'Kim Ma ward',54),(337,'Lieu Giai ward',54),(338,'Ngoc Ha ward',54),(339,'Ngoc Khanh ward',54),(340,'Phuc Xa ward',54),(341,'Quan Thanh ward',54),(342,'Truc Bach ward',54),(343,'Thanh Cong ward',54),(344,'Co Nhue 1 ward',55),(345,'Co Nhue 2 ward',55),(346,'Dong Ngac ward',55),(347,'Duc Thang ward',55),(348,'Lien Mac ward',55),(349,'Minh Khai ward',55),(350,'Phu Dien ward',55),(351,'Phuc Dien ward',55),(352,'Tay Tuu ward',55),(353,'Thuong Cat ward',55),(354,'Thuy Phuong ward',55),(355,'Xuan Dinh ward',55),(356,'Xuan Tao ward',55),(357,'Dich Vong ward',56),(358,'Dich Vong Hau ward',56),(359,'Mai Dich ward',56),(360,'Nghia Do ward',56),(361,'Nghia Tan ward',56),(362,'Quan Hoa ward',56),(363,'Trung Hoa ward',56),(364,'Yen Hoa ward',56),(365,'Cat Linh ward',57),(366,'Hang Bot ward',57),(367,'Kham Thien ward',57),(368,'Khuong Thuong ward',57),(369,'Kim Lien ward',57),(370,'Lang Ha ward',57),(371,'Nam Dong ward',57),(372,'Nga Tu So ward',57),(373,'O Cho Dua ward',57),(374,'Phuong Mai ward',57),(375,'Quang Trung ward',57),(376,'Quoc Tu Giam ward',57),(377,'Thinh Quang ward',57),(378,'Tho Quan ward',57),(379,'Ha Cau ward',58),(380,'La Khe ward',58),(381,'Mo Lao ward',58),(382,'Nguyen Trai ward',58),(383,'Phu La ward',58),(384,'Yen Nghia ward',58),(385,'Yet Kieu ward',58),(386,'Bien Giang ward',58),(387,'Dong Mai ward',58),(388,'Duong Noi ward',58),(389,'Phuc La ward',58),(390,'Kien Hung ward',58),(391,'Quang Trung ward',58),(392,'Phu Lam ward',58),(393,'Bach Dang ward',59),(394,'Bach Khoa ward',59),(395,'Bach Mai ward',59),(396,'Bui Thi Xuan ward',59),(397,'Cau Den ward',59),(398,'Ngo Thi Nham ward',59),(399,'Nguyen Du ward',59),(400,'Pham Dinh Ho ward',59),(401,'Pho Hue ward',59),(402,'Quynh Loi ward',59),(403,'Dong Mac ward',59),(404,'Thanh Luong ward',59),(405,'Dong Tam ward',59),(406,'Thanh Nhan ward',59),(407,'Le Dai Hanh ward',59),(408,'Chuong Duong ward',60),(409,'Cua Dong ward',60),(410,'Cua Nam ward',60),(411,'Dong Xuan ward',60),(412,'Hang Bac ward',60),(413,'Hang Dao ward',60),(414,'Hang Gai ward',60),(415,'Hang Ma ward',60),(416,'Hang Trong ward',60),(417,'Ly Thai To ward',60),(418,'Hang Bai ward',60),(419,'Phan Chu Trinh ward',60),(420,'Phuc Tan ward',60),(421,'Hang Bai ward',60),(422,'Hang Bo ward',60),(423,'Hang Bong ward',60),(424,'Tran Hung Dao ward',60),(427,'Hoang Van Thu ward',61),(428,'Thinh Liet ward',61),(429,'Dai Kim ward',61),(430,'Dinh Cong ward',61),(431,'Giap Bat ward',61),(432,'Tan Mai ward',61),(433,'Thanh Tri ward',61),(434,'Tran Phu ward',61),(435,'Tuong Mai ward',61),(436,'Hoang Liet town',61),(437,'Linh Nam ward',61),(438,'Mai Dong ward',61),(439,'Vinh Hung ward',61),(440,'Yen So ward',61),(441,'Bo De ward',62),(442,'Cu Khoi ward',62),(443,'Duc Giang ward',62),(444,'Gia Thuy ward',62),(445,'Giang Bien ward',62),(446,'Ngoc Thuy ward',62),(447,'Phuc Dong ward',62),(448,'Phuc Loi ward',62),(449,'Sai Dong ward',62),(450,'Thach Ban ward',62),(451,'Long Bien ward',62),(452,'Ngoc Lam ward',62),(453,'Thuong Thanh ward',62),(454,'Cau Dien ward',63),(455,'Dai Mo ward',63),(456,'Me Tri ward',63),(457,'My Dinh 1 ward',63),(458,'My Dinh 2 ward',63),(459,'Tay Mo ward',63),(460,'Trung Van ward',63),(461,'Xuan Phuong ward',63),(462,'Phuong Canh ward',63),(463,'Phu Do ward',63),(464,'Buoi ward',64),(465,'Nhat Tan ward',64),(466,'Phu Thuong ward',64),(467,'Quang An ward',64),(468,'Thuy Khue ward',64),(469,'Tu Lien ward',64),(470,'Xuan La ward',64),(471,'Yen Phu ward',64),(472,'Ha Dinh ward',65),(473,'Khuong Dinh ward',65),(474,'Khuong Mai ward',65),(475,'Khuong Trung ward',65),(476,'Kim Giang ward',65),(477,'Phuong Liet ward',65),(478,'Thanh Xuan Bac ward',65),(479,'Thanh Xuan Nam ward',65),(480,'Thanh Xuan Trung ward',65),(481,'Tay Dang town',66),(482,'Ba Trai ward',66),(483,'Ba Vi ward',66),(484,'Cam Linh ward',66),(485,'Cam Thuong ward',66),(486,'Phu Dong ward',66),(487,'Phu Phuong ward',66),(488,'Phu Son ward',66),(489,'Son Da ward',66),(490,'Tan Hong ward',66),(491,'Chau Son ward',66),(492,'Tan Linh ward',66),(493,'Chu Minh ward',66),(494,'Thai Hoa ward',66),(495,'Co Do ward',66),(496,'Thuan My ward',66),(497,'Dong Quang ward',66),(498,'Thuy An ward',66),(499,'Chuc Sonn town',67),(500,'Xuan Mai town',67),(501,'Dai Yen ward',67),(502,'Dong Lac ward',67),(503,'Dong Phu ward',67),(504,'Ngoc Hoa ward',67),(505,'Phu Nam An ward',67),(506,'Phu Nghia ward',67),(507,'Phung Chau ward',67),(508,'Quang Bi ward',67),(509,'Dong Phuong Yen ward',67),(510,'Dong Son ward',67),(511,'Hoa Chinh ward',67),(512,'Tan Tien ward',67),(513,'Thanh Binh ward',67),(514,'Thuong Vuc ward',67),(515,'Thuy Huong ward',67),(516,'Thuy Xuan Tien ward',67),(517,'Phung town',68),(518,'Dan Phuong ward',68),(519,'Dong Thap ward',68),(520,'Ha Mo ward',68),(521,'Hong Ha ward',68),(522,'Phuong Dinh ward',68),(523,'Song Phuong ward',68),(524,'Tan Hoi ward',68),(525,'Tan Lap ward',68),(526,'Tho An ward',68),(527,'Lien Ha ward',68),(528,'Lien Hong ward',68),(529,'Lien Trung ward',68),(530,'Tho Xuan ward',68),(531,'Dong Anh town',69),(532,'Bac Hong ward',69),(533,'Co Loa ward',69),(534,'Dai Mach ward',69),(535,'Dong Hoi ward',69),(536,'Tam Xa ward',69),(537,'Tam Xa ward',69),(538,'Thuy Lam ward',69),(539,'Tien Duong ward',69),(540,'Uy No ward',69),(541,'Duc Tu ward',69),(542,'Hai Boi ward',69),(543,'Kim Chung ward',69),(544,'Van Ha ward',69),(545,'Viet Hung ward',69),(546,'Vinh Ngoc ward',69),(547,'Vong La ward',69),(548,'Yen Vien town',70),(549,'Kim Lan ward',70),(550,'Bat Trang ward',70),(551,'Kim Son ward',70),(552,'Co Bi ward',70),(553,'Le Chi ward',70),(554,'Da Ton ward',70),(555,'Ninh Hiep ward',70),(556,'Dang Xa ward',70),(557,'Phu Dong ward',70),(558,'Dinh Xuyen ward',70),(559,'Phu Thi ward',70),(560,'Dong Du ward',70),(561,'Trau Quy ward',70),(562,'Duong Ha ward',70),(563,'Trung  Mau ward',70),(564,'Duong Quang ward',70),(565,'Van Duc ward',70),(566,'Duong Xa ward',70),(567,'Yen Thuong ward',70),(568,'Tram Troi town',71),(569,'An Khanh ward',71),(570,'Kim Chung ward',71),(571,'La Phu ward',71),(572,'An Thuong ward',71),(573,'Lai Yen ward',71),(574,'Cat Que ward',71),(575,'Minh Khai ward',71),(576,'Dac So ward',71),(577,'Son Dong ward',71),(578,'Di Trach ward',71),(579,'Song Phuong ward',71),(580,'Dong La ward',71),(581,'Tien Yen ward',71),(582,'Duc Giang ward',71),(583,'Van Canh ward',71),(584,'Duc Thuong ward',71),(585,'Van Con ward',71),(586,'Duong Lieu ward',71),(587,'Chi Dong town',72),(588,'Thach Da ward',72),(589,'Quang Minh ward',72),(590,'Thanh Lam ward',72),(591,'Chu Phan ward',72),(592,'Tien Phong ward',72),(593,'Dai Thinh ward',72),(594,'Tien Thang ward',72),(595,'Hoang Kim ward',72),(596,'Tien Thinh ward',72),(597,'Kim Hoa ward',72),(598,'Trang Viet ward',72),(599,'Lien Mac ward',72),(600,'Tu Lap ward',72),(601,'Me Linh ward',72),(602,'Van Khe ward',72),(603,'Dai Nghia town',73),(604,'Hung Tien ward',73),(605,'An My ward',73),(606,'Huong Son ward',73),(607,'An Phu ward',73),(608,'Le Thanh ward',73),(609,'An Tien ward',73),(610,'My Thanh ward',73),(611,'Bot Xuyen ward',73),(612,'Phu Luu Te ward',73),(613,'Dai Hung ward',73),(614,'Phuc Lam ward',73),(615,'Doc Tin ward',73),(616,'Phung Xa ward',73),(617,'Phu Minh town',74),(618,'Phu Xuyen town',74),(619,'Nam Trieu ward',74),(620,'Phu Tuc ward',74),(621,'Bach Ha ward',74),(622,'Phu Yen ward',74),(623,'Chau Can ward',74),(624,'Phuc Tien ward',74),(625,'Chuyen My ward',74),(626,'Thuong Duc ward',74),(627,'Phuc Tho town',75),(628,'Cam Dinh ward',75),(629,'Tam Hiep ward',75),(630,'Tam Thuan ward',75),(631,'Hat Mon ward',75),(632,'Thanh Da ward',75),(633,'Hiep Thuan ward',75),(634,'Tho Loc ward',75),(635,'Lien Hiep ward',75),(636,'Thuong Coc ward',75),(637,'Quoc Oai town',76),(638,'Ngoc My ward',76),(639,'Can Huu ward',76),(640,'Phu Cat ward',76),(641,'Cong Hoa ward',76),(642,'Phu Man ward',76),(643,'Dai Thanh ward',76),(644,'Phuong Cach ward',76),(645,'Dong Quang ward',76),(646,'Sai Son ward',76),(647,'Soc Son town',77),(648,'Phu Linh ward',77),(649,'Bac Phu ward',77),(650,'Phu Lo ward',77),(651,'Bac Son ward',77),(652,'Phu Minh ward',77),(653,'Dong Xuan ward',77),(654,'Quang Tien ward',77),(655,'Duc Hoa ward',77),(656,'Tan Dan ward',77),(657,'Lien Quan town',78),(658,'Huu Bang ward',78),(659,'Binh Phu ward',78),(660,'Kim Quan ward',78),(661,'Binh Yen ward',78),(662,'Lai Thuong ward',78),(663,'Cam Yen ward',78),(664,'Phu Kim ward',78),(665,'Can Kiem ward',78),(666,'Phung Xa ward',78),(667,'Kim Bai town',79),(668,'Lien Chau ward',79),(669,'Bich Hoa ward',79),(670,'My Hung ward',79),(671,'Binh  Minh ward',79),(672,'Phuong Trung ward',79),(673,'Cao Duong ward',79),(674,'Tam Hung ward',79),(675,'Cao Vien ward',79),(676,'Tan Uoc ward',79),(677,'Van Dien town',80),(678,'Ta Thanh Oai ward',80),(679,'Dai Ang ward',80),(680,'Tam Hiep ward',80),(681,'Dong My ward',80),(682,'Tan Trieu ward',80),(683,'Duyen Ha ward',80),(684,'Thanh Liet ward',80),(685,'Huu Hoa ward',80),(686,'Tu Hiep ward',80),(687,'Thuong Tin town',81),(688,'Ninh So ward',81),(689,'Chuong Duong ward',81),(690,'Quat Dong ward',81),(691,'Dung Tien ward',81),(692,'Tan Minh ward',81),(693,'Duyen Thai ward',81),(694,'Thang Loi ward',81),(695,'Ha Hoi ward',81),(696,'Thong Nhat ward',81),(697,'Van Dinh town',82),(698,'Lien Bat ward',82),(699,'Cao Thanh ward',82),(700,'Luu Hoang ward',82),(701,'Dai Cuong ward',82),(702,'Minh Duc ward',82),(703,'Dai Hung ward',82),(704,'Phu Luu ward',82),(705,'Doi Binh ward',82),(706,'Phuong Tu ward',82),(707,'Le Loi ward',83),(708,'Xuan Khanh ward',83),(709,'Ngo Quyen ward',83),(710,'Co Dong ward',83),(711,'Phu Thinh ward',83),(712,'Duong Lam ward',83),(713,'Quang Trung ward',83),(714,'Kim Son ward',83),(715,'Son Loc ward',83),(716,'Son Dong ward',83),(717,'An Thoi ward',25),(718,'Binh Thuy ward',25),(719,'Long Tuyen ward',25),(720,'Thoi An Dong ward',25),(721,'Tra An ward',25),(722,'Tra Noc ward',25),(723,'Bui Huu Nghia ward',25),(724,'Long Hoa ward',25),(725,'Ba Lang ward',26),(726,'Phu Thu ward',26),(727,'Hung Phu ward',26),(728,'Tan Phu ward',26),(729,'Hung Thanh ward',26),(730,'Thuong Thanh ward',26),(731,'Le Binh ward',26),(732,'An Binh ward',27),(733,'An Cu ward',27),(734,'Cai Khe ward',27),(735,'Hung Loi ward',27),(736,'An Hoa ward',27),(737,'Tan An ward',27),(738,'An Khanh ward',27),(739,'Thoi Binh ward',27),(740,'An Nghiep ward',27),(741,'Xuan Khanh ward',27),(742,'Chau Van Liem ward',28),(743,'Long Hung ward',28),(744,'Phuoc Thoi ward',28),(745,'Thoi An ward',28),(746,'Thoi Hoa ward',28),(747,'Thoi Long ward',28),(748,'Truong Lac ward',28),(749,'Tan Hung ward',29),(750,'Tan Loc ward',29),(751,'Thuan An ward',29),(752,'Thuan Hung ward',29),(753,'Thanh Hoa ward',29),(754,'Trung Kien ward',29),(755,'Thoi Thuan ward',29),(756,'Trung Nhut ward',29),(757,'Thot Not ward',29),(758,'Co Do town',30),(759,'Dong Hiep ward',30),(760,'Thoi Hung ward',30),(761,'Thoi Xuan ward',30),(762,'Trung An ward',30),(763,'Dong Thang ward',30),(764,'Thanh Phu ward',30),(765,'Trung Hung ward',30),(766,'Thoi Dong ward',30),(767,'Trung Thanh ward',30),(768,'Phong Dien town',31),(769,'Giai Xuan ward',31),(770,'Nhon Nghia ward',31),(771,'Tan Thoi ward',31),(772,'Truong Long ward',31),(773,'My Khanh ward',31),(774,'Nhon Ai ward',31),(775,'Thoi Lai town',32),(776,'Dinh Mon ward',32),(777,'Dong Binh ward',32),(778,'Dong Thuan ward',32),(779,'Tan Thanh ward',32),(780,'Truong Thang ward',32),(781,'Truong Thanh ward',32),(782,'Truong Xuan ward',32),(783,'Truong Xuan A ward',32),(784,'Truong Xuan B ward',32),(785,'Thanh An town',33),(786,'Vinh Thanh town',33),(787,'Thanh An ward',33),(788,'Thanh Quoi ward',33),(789,'Thanh Thang ward',33),(790,'Thanh Tien ward',33),(791,'Thanh Loc ward',33),(792,'Vinh Binh ward',33),(793,'Thanh Loi ward',33),(794,'Vinh Trinh ward',33),(795,'Thanh My ward',33),(796,'Hai Son ward',84),(797,'Ngoc Xuyen ward',84),(798,'Van Huong ward',84),(799,'Hop Duc ward',84),(800,'Minh Duc ward',84),(801,'Van Son ward',84),(802,'Ngoc Hai ward',84),(803,'Bang La ward',84),(804,'Anh Dung ward',85),(805,'Da Phuc ward',85),(806,'Hai Thanh ward',85),(807,'Hoa Nghia ward',85),(808,'Hung Dao ward',85),(809,'Tan Thanh ward',85),(810,'Cat Bi ward',86),(811,'Dong Hai 2 ward',86),(812,'Dang Hai ward',86),(813,'Dang Lam ward',86),(814,'Dong Hai 1 ward',86),(815,'Thanh To ward',86),(816,'Trang Cat ward',86),(817,'Nam Hai ward',86),(818,'Ha Ly ward',87),(819,'Hoang Van Thu ward',87),(820,'Hung Vuong ward',87),(821,'Minh Khai ward',87),(822,'Pham Hong Thai ward',87),(823,'Quan Toan ward',87),(824,'Quang Trung ward',87),(825,'So Dau ward',87),(826,'Thuong Ly ward',87),(827,'Trai Chuoi ward',87),(828,'Bac Son ward',88),(829,'Dong Hoa ward',88),(830,'Lam Ha ward',88),(831,'Nam Son ward',88),(832,'Ngoc Son ward',88),(833,'Phu Lien ward',88),(834,'Quan Tru ward',88),(835,'Tran Thanh Ngo ward',88),(836,'Trang Minh ward',88),(837,'Van Dau ward',88),(838,'An Bien ward',89),(839,'An Duong ward',89),(840,'Cat Dai ward',89),(841,'Dong Hai ward',89),(842,'Du Hang ward',89),(843,'Kenh Duong ward',89),(844,'Lam Son ward',89),(845,'Nghia Xa ward',89),(846,'Niem Nghia ward',89),(847,'Trai Cau ward',89),(848,'Cau Dat ward',90),(849,'Cau Tre ward',90),(850,'Dang Giang ward',90),(851,'Dong Khe ward',90),(852,'Dong Quoc Binh ward',90),(853,'Lach Tray ward',90),(854,'Le Loi ward',90),(855,'May Chai ward',90),(856,'May To ward',90),(857,'Van My ward',90),(858,'An Duong town',91),(859,'An Dong ward',91),(860,'An Hoa ward',91),(861,'An Hong ward',91),(862,'An Hung ward',91),(863,'Dong Thai ward',91),(864,'Hong Thai ward',91),(865,'Le Loi ward',91),(866,'Hong Phong ward',91),(867,'Le Thien ward',91),(868,'An Lao town',92),(869,'Truong Son town',92),(870,'An Thai ward',92),(871,'An Thang ward',92),(872,'An Tho ward',92),(873,'Quang Hung ward',92),(874,'Quang Trung ward',92),(875,'Quoc Tuan ward',92),(876,'Tan Dan ward',92),(877,'Tan Vien ward',92),(878,'Bach Long Vi island',93),(879,'Cat Ba town',94),(880,'Cat Hai town',94),(881,'Dong Bai ward',94),(882,'Gia Luan ward',94),(883,'Hien Hao ward',94),(884,'Nghia Lo ward',94),(885,'Phu Long ward',94),(886,'Tran Chau ward',94),(887,'Van Phong ward',94),(888,'Viet Hai ward',94),(889,'Nui Doi town',95),(890,'Dai Dong ward',95),(891,'Dai Ha ward',95),(892,'Dai Hop ward',95),(893,'Doan Xa ward',95),(894,'Minh Tan ward',95),(895,'Ngu Doan ward',95),(896,'Ngu Phuc ward',95),(897,'Tan Phong ward',95),(898,'Tan Trao ward',95),(899,'Minh Duc town',96),(900,'Nui Deo town',96),(901,'An Lu ward',96),(902,'An Son ward',96),(903,'Cao Nhan ward',96),(904,'Lap Le ward',96),(905,'Lien Khe ward',96),(906,'Luu Kiem ward',96),(907,'Luu Ky ward',96),(908,'Minh Tan ward',96),(909,'Tien Lang town',97),(910,'Bac Hung ward',97),(911,'Bach Dang ward',97),(912,'Cap Tien ward',97),(913,'Dai Thang ward',97),(914,'Quyet Tien ward',97),(915,'Tay Hung ward',97),(916,'Tien Cuong ward',97),(917,'Tien Hung ward',97),(918,'Tien Minh ward',97),(919,'Vinh Bao town',98),(920,'An Hoa ward',98),(921,'Cao Minh ward',98),(922,'Co Am ward',98),(923,'Cong Hien ward',98),(924,'Tam Cuong ward',98),(925,'Tam Da ward',98),(926,'Tan Hung ward',98),(927,'Tan Lien ward',98),(928,'Thang Thuy ward',98),(929,'A Luoi town',99),(930,'A Dot ward',99),(931,'A Ngo ward',99),(932,'A Roang ward',99),(933,'Bac Son ward',99),(934,'Hong Thuong ward',99),(935,'Hong Thuy ward',99),(936,'Hong Trung ward',99),(937,'Hong Van ward',99),(938,'Huong Lam ward',99),(939,'Dong Son ward',99),(940,'Hong Bac ward',99),(941,'Huong Nguyen ward',99),(942,'Phu Vinh ward',99),(943,'Khe Tre town',100),(944,'Huong Giang ward',100),(945,'Huong Huu ward',100),(946,'Huong Loc ward',100),(947,'Huong Phu ward',100),(948,'Huong Xuan ward',100),(949,'Thuong Lo ward',100),(950,'Thuong Long ward',100),(951,'Thuong Nhat ward',100),(952,'Thuong Quang ward',100),(953,'Huong Son ward',100),(954,'Lang Co town',106),(955,'Phu Loc town',106),(956,'Loc An ward',106),(957,'Loc Binh ward',106),(958,'Loc Bon ward',106),(959,'Loc Tien ward',106),(960,'Loc Tri ward',106),(961,'Loc Vinh ward',106),(962,'Vinh Giang ward',106),(963,'Vinh Hai ward',106),(964,'Loc Dien ward',106),(965,'Vinh Hung ward',106),(966,'Xuan Loc ward',106),(967,'Phu Da town',113),(968,'Phu An ward',113),(969,'Phu Dien ward',113),(970,'Phu Hai ward',113),(971,'Phu Ho ward',113),(972,'Phu Xuan ward',113),(973,'Vinh An ward',113),(974,'Vinh Ha ward',113),(975,'Vinh Phu ward',113),(976,'Vinh Thai ward',113),(977,'Phu Luong ward',113),(978,'Phu My ward',113),(979,'Phu Thuan ward',113),(980,'Vinh Xuan ward',113),(981,'Sia town',114),(982,'Quang An ward',114),(983,'Quang Cong ward',114),(984,'Quang Loi ward',114),(985,'Quang Ngan ward',114),(986,'Quang Phuoc ward',114),(987,'Quang Thai ward',114),(988,'Quang Thanh ward',114),(989,'Quang Tho ward',114),(990,'Quang Vinh ward',114),(991,'An Cuu ward',115),(992,'An Dong ward',115),(993,'An Hoa ward',115),(994,'An Tay ward',115),(995,'Dong Ba ward',115),(996,'Tay Loc ward',115),(997,'Thuan An ward',115),(998,'Thuan Hoa ward',115),(999,'Thuan Loc ward',115),(1000,'Thuan Thanh ward',115),(1001,'Gia Hoi ward',115),(1002,'Huong An ward',115),(1003,'Huong Ho ward',115),(1004,'Huong So ward',115),(1005,'Huong Vinh ward',115),(1006,'Thuy Van ward',115),(1007,'Truong An ward',115),(1008,'Vinh Ninh ward',115),(1009,'Vy Da ward',115),(1010,'Xuan Phu ward',115),(1011,'Phu Bai town',116),(1012,'Duong Hoa ward',116),(1013,'Phu Son ward',116),(1014,'Thuy Chau ward',116),(1015,'Thuy Duong ward',116),(1016,'Thuy Luong ward',116),(1017,'Thuy Phu ward',116),(1018,'Thuy Phuong ward',116),(1019,'Thuy Tan ward',116),(1020,'Thuy Thanh ward',116),(1021,'Huong Chu ward',117),(1022,'Huong Van ward',117),(1023,'Huong Vân ward',117),(1024,'Huong Xuan ward',117),(1025,'Tu Ha ward',117),(1026,'Binh Thanh ward',117),(1027,'Binh Tien ward',117),(1028,'Hong Tien ward',117),(1029,'Huong Binh ward',117),(1030,'Huong Toan ward',117),(1031,'Hoa An ward',46),(1032,'Hoa Phat ward',46),(1033,'Hoa Tho Dong ward',46),(1034,'Hoa Tho Tay ward',46),(1035,'Hoa Xuan ward',46),(1036,'Khue Trung ward',46),(1037,'Binh Hien ward',47),(1038,'Binh Thuan ward',47),(1039,'Hai Chau I ward',47),(1040,'Hai Chau II ward',47),(1041,'Hoa Cuong Bac ward',47),(1042,'Hoa Thuan Tay ward',47),(1043,'Nam Duong ward',47),(1044,'Phuoc Ninh ward',47),(1045,'Thach Thang ward',47),(1046,'Thanh Binh ward',47),(1047,'Hoa Hiep Bac ward',48),(1048,'Hoa Hiep Nam ward',48),(1049,'Hoa Khanh Bac ward',48),(1050,'Hoa Khanh Nam ward',48),(1051,'Hoa Minh ward',48),(1052,'Hoa Hai ward',49),(1053,'Hoa Quy ward',49),(1054,'Khue My ward',49),(1055,'My An ward',49),(1059,'An Hai Bac ward',50),(1060,'An Hai Dong ward',50),(1061,'An Hai Tay ward',50),(1062,'Man Thai ward',50),(1063,'Nai Hien Dong ward',50),(1064,'Phuoc My ward',50),(1065,'Tho Quang ward',50),(1066,'An Khe ward',51),(1067,'Chinh Gian ward',51),(1068,'Hoa Khe ward',51),(1069,'Tam Thuan ward',51),(1070,'Tan Chinh ward',51),(1071,'Thac Gian ward',51),(1072,'Thanh Khe Dong ward',51),(1073,'Thanh Khe Tay ward',51),(1074,'Vinh Trung ward',51),(1075,'Xuan Ha ward',51),(1079,'Hoa Bac ward',53),(1080,'Hoa Chau ward',53),(1081,'Hoa Khuong ward',53),(1082,'Hoa Lien ward',53),(1083,'Hoa Nhon ward',53),(1084,'Hoa Phong ward',53),(1085,'Hoa Phu ward',53),(1086,'Hoa Phuoc ward',53),(1087,'Hoa Son ward',53),(1088,'Hoa Tien ward',53),(1089,'Hoa Ninh ward',53),(1090,'Thu Ba town',127),(1091,'Dong Thai ward',127),(1092,'Dong Yen ward',127),(1093,'Hung Yen ward',127),(1094,'Nam Thai ward',127),(1095,'Nam Thai A ward',127),(1096,'Nam Yen ward',127),(1097,'Tay Yen ward',127),(1098,'Tay Yen A ward',127),(1099,'Thu Muoi Mot town',128),(1100,'Dong Hoa ward',128),(1101,'Dong Hung ward',128),(1102,'Dong Hung A ward',128),(1103,'Dong Hung B ward',128),(1104,'Tan Thanh ward',128),(1105,'Thuan Hoa ward',128),(1106,'Van Khanh ward',128),(1107,'Van Khanh Dong ward',128),(1108,'Van Khanh Tay ward',128),(1109,'Dong Thanh ward',128),(1110,'Minh Luong town',129),(1111,'Binh An ward',129),(1112,'Giuc Tuong ward',129),(1113,'Minh Hoa ward',129),(1114,'Mong Tho ward',129),(1115,'Mong Tho A ward',129),(1116,'Mong Tho B ward',129),(1117,'Thanh Loc ward',129),(1118,'Vinh Hoa Hiep ward',129),(1119,'Vinh Hoa Phu ward',129),(1120,'Phu  Loi ward',130),(1121,'Phu My ward',130),(1122,'Tan Khanh Hoa ward',130),(1123,'Vinh Dieu ward',130),(1124,'Vinh Phu ward',130),(1125,'Giong Rieng town',131),(1126,'Ban Thach ward',131),(1127,'Hoa An ward',131),(1128,'Ngoc Hoa ward',131),(1129,'Ngoc Thanh ward',131),(1130,'Ngoc Thuan ward',131),(1131,'Thanh Hoa ward',131),(1132,'Thanh Hung ward',131),(1133,'Hoa Hung ward',131),(1134,'Hoa Loi ward',131),(1135,'Hoa Thuan ward',131),(1136,'Thanh Loc ward',131),(1137,'Thanh Phuoc ward',131),(1138,'Vinh Thanh ward',131),(1139,'Go Quao town',132),(1140,'Dinh An ward',132),(1141,'Dinh Hoa ward',132),(1142,'Thoi Quan ward',132),(1143,'Thuy Lieu ward',132),(1144,'Vinh Hoa Hung Nam ward',132),(1145,'Vinh Phuoc A ward',132),(1146,'Vinh Phuoc B ward',132),(1147,'Vinh Thang ward',132),(1148,'Vinh Tuy ward',132),(1149,'Hon Dat town',133),(1150,'Soc Son town',133),(1151,'Binh Giang ward',133),(1152,'Binh Sonh ward',133),(1153,'Linh Huynh ward',133),(1154,'My Phuoc ward',133),(1155,'My Thai ward',133),(1156,'My Thuan ward',133),(1157,'Nam Thai Son ward',133),(1158,'Son Binh ward',133),(1159,'My Hiep Son ward',133),(1160,'Son Kien ward',133),(1161,'Tho Son ward',133),(1162,'My Lam ward',133),(1163,'An Son ward',134),(1164,'Hon Tre ward',134),(1165,'Lai Son ward',134),(1166,'Nam Du ward',134),(1167,'Vinh Thuan town',135),(1168,'Tan Thuan ward',135),(1169,'Vinh Binh Bac ward',135),(1170,'Vinh Binh Nam ward',135),(1171,'Vinh Phong ward',135),(1172,'Vinh Thuan ward',135),(1173,'Binh San ward',136),(1174,'Dong Ho ward',136),(1175,'Phao Dai ward',136),(1176,'To Chau ward',136),(1177,'My Duc ward',136),(1178,'Thuan Yen ward',136),(1179,'Tien Hai ward',136),(1180,'An Thoi ward',137),(1181,'Duong Dong ward',137),(1182,'Bai Thom ward',137),(1183,'Cua Can ward',137),(1184,'Cua Duong ward',137),(1185,'Duong To ward',137),(1186,'Ganh Dau ward',137),(1187,'Ham Ninh ward',137),(1188,'Hon Thom ward',137),(1189,'Tho Chau ward',137),(1190,'An Binh ward',138),(1191,'An Hoa ward',138),(1192,'Rach Soi ward',138),(1193,'Vinh Bao ward',138),(1194,'Vinh Hiep ward',138),(1195,'Vinh Loi ward',138),(1196,'Vinh Quang ward',138),(1197,'Vinh Thanh ward',138),(1198,'Vinh Thanh Van ward',138),(1199,'Vinh Thong ward',138),(1200,'Vinh Lac ward',138),(1201,'Phi Thong ward',138),(1202,'Anh Son town',152),(1203,'Binh Son ward',152),(1204,'Cam Son ward',152),(1205,'Cao Son ward',152),(1206,'Dinh Son ward',152),(1207,'Linh Son ward',152),(1208,'Long Son ward',152),(1209,'Phuc Son ward',152),(1210,'Tam Son ward',152),(1211,'Tao Son ward',152),(1212,'Duc Son ward',152),(1213,'Hoa Son ward',152),(1214,'Hoi Son ward',152),(1215,'Thach Son ward',152),(1216,'Thanh Son ward',152),(1217,'Tuong Son ward',152),(1218,'Con Cuong town',153),(1219,'Binh Chuan ward',153),(1220,'Bong Khe ward',153),(1221,'Cam Lam ward',153),(1222,'Chau Khe ward',153),(1223,'Lang Khe ward',153),(1224,'Luc Da ward',153),(1225,'Mau Duc ward',153),(1226,'Mon Son ward',153),(1227,'Thach Ngan ward',153),(1228,'Yen Khe ward',153),(1229,'Dien Chau town',154),(1230,'Dien An ward',154),(1231,'Dien Bich ward',154),(1232,'Dien Binh ward',154),(1233,'Dien Cat ward',154),(1234,'Dien My ward',154),(1235,'Dien Ngoc ward',154),(1236,'Dien Nguyen ward',154),(1237,'Dien Phong ward',154),(1238,'Dien Phu ward',154),(1239,'Dien Hai ward',154),(1240,'Dien Hoa ward',154),(1241,'Dien Hoang ward',154),(1242,'Dien Hong ward',154),(1243,'Dien Tan ward',154),(1244,'Dien Thang ward',154),(1245,'Dien Thanh ward',154),(1246,'Dien Thap ward',154),(1247,'Dien Kim ward',154),(1248,'Dien Ky ward',154),(1249,'Diem Lam ward',154),(1250,'Do Luong town',155),(1251,'Bac Son ward',155),(1252,'Bai Son ward',155),(1253,'Boi Son ward',155),(1254,'Da Son ward',155),(1255,'My Son ward',155),(1256,'Nam Son ward',155),(1257,'Ngoc Son ward',155),(1258,'Nhan Son ward',155),(1259,'Nhan Son ward',155),(1260,'Quang Son ward',155),(1261,'Tan Son ward',155),(1262,'Dong Son ward',155),(1263,'Yen Bac ward',156),(1264,'Yen Nam ward',156),(1265,'Hung Nguyen town',156),(1266,'Hung Chau ward',156),(1267,'Hung Dao ward',156),(1268,'Hung Tan ward',156),(1269,'Hung Tay ward',156),(1270,'Hung Thang ward',156),(1271,'Hung Thanh ward',156),(1272,'Hung Thinh ward',156),(1273,'Hung Linh ward',156),(1274,'Muong Xen town',157),(1275,'Bac Ly ward',157),(1276,'Bao Nam ward',157),(1277,'Bao Thang ward',157),(1278,'Chieu Luu ward',157),(1279,'Muong Long ward',157),(1280,'Muong Tip ward',157),(1281,'My Ly ward',157),(1282,'Na Loi ward',157),(1283,'Na Ngoi ward',157),(1284,'Nam Dan town',158),(1285,'Hong Long ward',158),(1286,'Hung Tien ward',158),(1287,'Khanh Son ward',158),(1288,'Kim Lien ward',158),(1289,'Nam Nghia ward',158),(1290,'Nam Phuc ward',158),(1291,'Nam Tan ward',158),(1292,'Nam Thai ward',158),(1293,'Nam Thanh ward',158),(1294,'Quan Hanh town',159),(1295,'Khanh Hop ward',159),(1296,'Cong Bac ward',159),(1297,'Cong Nam ward',159),(1298,'Nghi Dien ward',159),(1299,'Nghi Quang ward',159),(1300,'Nghi Thach ward',159),(1301,'Nghi Thai ward',159),(1302,'Nghi Thiet ward',159),(1303,'Nghi Thinh ward',159),(1304,'Nghia An ward',160),(1305,'Nghia Duc ward',160),(1306,'Nghia Long ward',160),(1307,'Nghia Mai ward',160),(1308,'Nghia Minh ward',160),(1309,'Nghia Phu ward',160),(1310,'Nghia Son ward',160),(1311,'Nghia Hieu ward',160),(1312,'Nghia Hoi ward',160),(1313,'Nghia Thang ward',160),(1314,'Nghia Thanh ward',160),(1315,'Nghia Tho ward',160),(1316,'Nghia Hung ward',160),(1317,'Nghia Khanh ward',160),(1318,'Kim Son town',161),(1319,'Cam Muon ward',161),(1320,'Chau Kim ward',161),(1321,'Chau Thon ward',161),(1322,'Dong Van ward',161),(1323,'Nam Giai ward',161),(1324,'Nam Nhoong ward',161),(1325,'Quang Phong ward',161),(1326,'Que Son ward',161),(1327,'Thong Thu ward',161),(1328,'Tan Lac town',162),(1329,'Chau Binh ward',162),(1330,'Chau Hanh ward',162),(1331,'Chau Binh ward',162),(1332,'Chau Hoan ward',162),(1333,'Chau Nga ward',162),(1334,'Chau Phong ward',162),(1335,'Chau Thang ward',162),(1336,'Chau Thuan ward',162),(1337,'Chau Tien ward',162),(1338,'Chau Hoi ward',162),(1339,'Dien Lam ward',162),(1340,'Quy Hop town',163),(1341,'Bac Son ward',163),(1342,'Chau Cuong ward',163),(1343,'Chau Dinh ward',163),(1344,'Chau Hong ward',163),(1345,'Dong Hop ward',163),(1346,'Ha Son ward',163),(1347,'Lien Hop ward',163),(1348,'Minh Hop ward',163),(1349,'Nam Son ward',163),(1350,'Cau Giat town',164),(1351,'An Hoa ward',164),(1352,'Ngoc Son ward',164),(1353,'Quynh Ba ward',164),(1354,'Quynh Bang ward',164),(1355,'Quynh My ward',164),(1356,'Quynh Nghia ward',164),(1357,'Quynh Ngoc ward',164),(1358,'Quynh Tam ward',164),(1359,'Quynh Tan ward',164),(1360,'Quynh Long ward',164),(1361,'Tien Thuy ward',164),(1362,'Tan Thang ward',164),(1363,'Tan Ky town',165),(1364,'Dong Van ward',165),(1365,'Giai Xuan ward',165),(1366,'Huong Son ward',165),(1367,'Nghia Hop ward',165),(1368,'Nghia Phuc ward',165),(1369,'Nghia Thai ward',165),(1370,'Phu Son ward',165),(1371,'Tan An ward',165),(1372,'Ky Son ward',165),(1373,'Tan Long ward',165),(1374,'Tan Huong ward',165),(1375,'Thanh Chuong town',166),(1376,'Cat Van ward',166),(1377,'Dai Dong ward',166),(1378,'Dong Van ward',166),(1379,'Hanh Lam ward',166),(1380,'Thanh Lam ward',166),(1381,'Thanh Lien ward',166),(1382,'Thanh Linh ward',166),(1383,'Thanh Long ward',166),(1384,'Thanh Luong ward',166),(1385,'Thanh Mai ward',166),(1386,'Hoa Binh town',167),(1387,'Huu Khuong ward',167),(1388,'Luong Minh ward',167),(1389,'Luu Kien ward',167),(1390,'Tam Quang ward',167),(1391,'Tam Thai ward',167),(1392,'Thach Giam ward',167),(1393,'Xa Luong ward',167),(1394,'Xieng My ward',167),(1395,'Yen Hoa ward',167),(1396,'Yen Thang ward',167),(1397,'Yen Thanh town',168),(1398,'Bac Thanh ward',168),(1399,'Bao Thanh ward',168),(1400,'Cong Thanh ward',168),(1401,'Dai Thanh ward',168),(1402,'Minh Thanh ward',168),(1403,'My Thanh ward',168),(1404,'Nam Thanh ward',168),(1405,'Nhan Thanh ward',168),(1406,'Phu Thanh ward',168),(1407,'Ben Thuy ward',169),(1408,'Cua Nam ward',169),(1409,'Doi Cung ward',169),(1410,'Dong Vinh ward',169),(1411,'Ha Huy Tap ward',169),(1412,'Trung Do ward',169),(1413,'Truong Thi ward',169),(1414,'Hung Chinh ward',169),(1415,'Hung Dong ward',169),(1416,'Hung Hoa ward',169),(1417,'Hong Son ward',169),(1418,'Hung Dung ward',169),(1419,'Hung Phuc ward',169),(1420,'Le Loi ward',169),(1421,'Hung Loc ward',169),(1422,'Nghi An ward',169),(1423,'Nghi Duc ward',169),(1424,'Nghi Hai ward',170),(1425,'Nghi Hoa ward',170),(1426,'Nghi Tan ward',170),(1427,'Nghi Thuy ward',170),(1428,'Thu Thuy ward',170),(1429,'Nghi Huong ward',170),(1430,'Nghi Thu ward',170),(1431,'Mai Hung ward',171),(1432,'Quynh Di ward',171),(1433,'Quynh Phuong ward',171),(1434,'Quynh Thien ward',171),(1435,'Quynh Xuan ward',171),(1436,'Quynh Lap ward',171),(1437,'Quynh Lien ward',171),(1438,'Quynh Loc ward',171),(1439,'Quynh Trang ward',171),(1440,'Quynh Vinh ward',171),(1441,'Long Son ward',172),(1442,'Quang Phong ward',172),(1443,'Quang Tien ward',172),(1444,'Hoa Hieu ward',172),(1445,'Nghia Hoa ward',172),(1446,'Nghia My ward',172),(1447,'Nghia Thuan ward',172),(1448,'Nghia Tien ward',172),(1449,'Tay Hieu ward',172),(1450,'Dong Hieu ward',172),(1451,'Ba Che town',173),(1452,'Dap Thanh ward',173),(1453,'Don Dac ward',173),(1454,'Luong Mong ward',173),(1455,'Minh Cam ward',173),(1456,'Nam Son ward',173),(1457,'Thanh Lam ward',173),(1459,'Thanh Son ward',173),(1460,'Binh Lieu town',174),(1461,'Dong Tam ward',174),(1462,'Dong Van ward',174),(1463,'Hoanh Mo ward',174),(1464,'Huc Dong ward',174),(1465,'Luc Hon ward',174),(1466,'Tinh Huc ward',174),(1467,'Vo Ngai ward',174),(1468,'Co To town',175),(1469,'Dong Tien ward',175),(1470,'Thanh Lan ward',175),(1477,'Dam Ha town',176),(1478,'Dai Binh ward',176),(1479,'Duc Yen ward',176),(1480,'Quang An ward',176),(1481,'Quang Lam ward',176),(1482,'Quang Loi ward',176),(1483,'Quang Tan ward',176),(1484,'Tan Binh ward',176),(1485,'Tan Lap ward',176),(1486,'Quang Ha town',177),(1487,'Cai Chien ward',177),(1488,'Duong Hoa ward',177),(1489,'Phu Hai ward',177),(1490,'Quang Chinh ward',177),(1491,'Quang Minh ward',177),(1492,'Quang Minh ward',177),(1493,'Quang Phong ward',177),(1494,'Quang Son ward',177),(1495,'Quang Thang ward',177),(1496,'Quang Thanh ward',177),(1497,'Quang Dien ward',177),(1498,'Tien Yen town',178),(1499,'Dai Duc ward',178),(1500,'Dai Thanh ward',178),(1501,'Dien Xa ward',178),(1502,'Dong Hai ward',178),(1503,'Dong Rui ward',178),(1504,'Ha Lau ward',178),(1505,'Hai Lang ward',178),(1506,'Phong Du ward',178),(1507,'Tien Lang ward',178),(1508,'Dong Ngu ward',178),(1509,'Yen Than ward',178),(1510,'Cai Rong ward',179),(1511,'Ban San ward',179),(1512,'Dai Xuyen ward',179),(1513,'Doan Ket ward',179),(1514,'Ha Long ward',179),(1515,'Minh Chau ward',179),(1516,'Ngoc Vung ward',179),(1517,'Quan Lan ward',179),(1518,'Thang Loi ward',179),(1519,'Cam Binh ward',180),(1520,'Cam Dong ward',180),(1521,'Cam Phu ward',180),(1522,'Cam Son ward',180),(1523,'Cam Tay ward',180),(1524,'Cam Thuy ward',180),(1525,'Cam Trung ward',180),(1526,'Cua Ong ward',180),(1527,'Mong Duong ward',180),(1528,'Quanh Hanh ward',180),(1529,'Bach Dang ward',181),(1530,'Bai Chay ward',181),(1531,'Cao Thang ward',181),(1532,'Cao Xanh ward',181),(1533,'Dai Yen ward',181),(1534,'Tran Hung Dao ward',181),(1535,'Tuan Chau ward',181),(1536,'Viet Hung ward',181),(1537,'Yet Kieu ward',181),(1538,'Bang Ca ward',181),(1539,'Hai Hoa ward',182),(1540,'Hai Yen ward',182),(1541,'Hoa Lac ward',182),(1542,'Ka Long ward',182),(1543,'Ninh Duong ward',182),(1544,'Hai Dong ward',182),(1545,'Hai Son ward',182),(1546,'Hai Tien ward',182),(1547,'Hai Xuan ward',182),(1548,'Quang Nghia ward',182),(1549,'Vinh Trung ward',182),(1550,'Bac Son ward',183),(1551,'Nam Khe ward',183),(1552,'Phuong Dong ward',183),(1553,'Quang Trung ward',183),(1554,'Trung Vuong ward',183),(1555,'Vanh Danh ward',183),(1556,'Yen Thanh ward',183),(1557,'Dien Cong ward',183),(1558,'Thuong Yen Cong ward',183),(1559,'Thanh Son ward',183),(1560,'Dong Trieu town',184),(1561,'Mao Khe ward',184),(1562,'An Sinh ward',184),(1563,'Binh Duong ward',184),(1564,'Binh Khe ward',184),(1565,'Kim Son ward',184),(1566,'Nguyen Hue ward',184),(1567,'Tan Viet ward',184),(1568,'Thuy An ward',184),(1569,'Trang An ward',184),(1570,'Duc Chinh ward',184),(1571,'Hoang Que ward',184),(1572,'Trang Luong ward',184),(1573,'Cong Hoa ward',185),(1574,'Dong Mai ward',185),(1575,'Ha An ward',185),(1576,'Minh Thanh ward',185),(1577,'Nam Hoa ward',185),(1578,'Yen Hai ward',185),(1579,'Song Khoai ward',185),(1580,'Cam La ward',185),(1581,'Hiep Hoa ward',185),(1582,'Hoang Tan ward',185),(1583,'Phong Hai ward',185),(1584,'Quang Yen ward',185),(1585,'Song Khoai ward',185),(1586,'Loc Thang town',139),(1587,'B\' La ward',139),(1588,'Loc An ward',139),(1589,'Loc Bac ward',139),(1590,'Loc Bao ward',139),(1591,'Loc Nam ward',139),(1592,'Loc Nghai ward',139),(1593,'Loc Phu ward',139),(1594,'Loc Quang ward',139),(1595,'Loc Tan ward',139),(1596,'Loc Thanh ward',139),(1597,'Cat Tien town',140),(1598,'Phuoc Cat ward',140),(1599,'Dong Nai Thuong ward',140),(1600,'Duc Pho ward',140),(1601,'Gia Vien ward',140),(1602,'Nam Ninh ward',140),(1603,'Phu My ward',140),(1604,'Phuoc Cat 2 ward',140),(1605,'Quang Ngai ward',140),(1606,'Tien Hoang ward',140),(1607,'My Lam ward',140),(1608,'Tu Nghia ward',140),(1609,'Da M-ri town',141),(1610,'Ma Da Guoi town',141),(1611,'Da Oai ward',141),(1612,'Da Ploa ward',141),(1613,'Da Ton ward',141),(1614,'Doan Ket ward',141),(1615,'Ha Lam ward',141),(1616,'Phuoc Loc ward',141),(1617,'Da Teh town',142),(1618,'An Nhon ward',142),(1619,'Da Kho ward',142),(1620,'Da Pal ward',142),(1621,'Huong Lam ward',142),(1622,'My Duc ward',142),(1623,'Quang Tri ward',142),(1624,'Quoc Oai ward',142),(1625,'Trieu Hai ward',142),(1626,'Ha Dong ward',142),(1627,'Da K Nang ward',143),(1628,'Da Long ward',143),(1629,'Da M Rong ward',143),(1630,'Da R Sal ward',143),(1631,'Da Tong ward',143),(1632,'Lieng Sronh ward',143),(1633,'Phi Lieng ward',143),(1634,'Ro Men ward',143),(1635,'Di Linh town',145),(1636,'Bao Thuan ward',145),(1637,'Dinh Lac ward',145),(1638,'Dinh Trang Hoa ward',145),(1639,'Dinh Trang Thuong ward',145),(1640,'Hoa Ninh ward',145),(1641,'Hoa Trung ward',145),(1642,'Lien Dam ward',145),(1643,'Son Dien ward',145),(1644,'Tam Bo ward',145),(1645,'Gia Bac ward',145),(1646,'Gung Re ward',145),(1647,'Hoa Bac ward',145),(1648,'Tan Lam ward',145),(1649,'Tan Chau ward',145),(1650,'Tan Thuong ward',145),(1651,'D Ran town',146),(1652,'Thanh My town',146),(1653,'Da Ron ward',146),(1654,'Ka Don ward',146),(1655,'Lac Lam ward',146),(1656,'Lac Xuan ward',146),(1657,'Xa Pro ward',146),(1658,'Quang Lap ward',146),(1659,'Tu Tra ward',146),(1660,'Lien Nghia town',147),(1661,'Binh Thanh ward',147),(1662,'Da Loan ward',147),(1663,'Da Quyn ward',147),(1664,'Hiep An ward',147),(1665,'Ninh Gia ward',147),(1666,'Ninh Loan ward',147),(1667,'Ninh Loan ward',147),(1668,'Phu Hoi ward',147),(1669,'Ta Hine ward',147),(1670,'Ta Nang ward',147),(1671,'Lac Duong town',148),(1672,'Da Chais ward',148),(1673,'Da Nhim ward',148),(1674,'Da Sar ward',148),(1675,'Dung Kno ward',148),(1676,'Lat ward',148),(1677,'Dinh Van town',149),(1678,'Nam Ban town',149),(1679,'Da Don ward',149),(1680,'Dong Thanh ward',149),(1681,'Me Linh ward',149),(1682,'Nam Ha ward',149),(1683,'Phi To ward',149),(1684,'Phu Son ward',149),(1685,'Phuc Tho ward',149),(1686,'Gia Lam ward',149),(1687,'Tan Ha ward',149),(1688,'Tan Thanh ward',149),(1689,'Ward 1',150),(1690,'Ward 2',150),(1691,'B Lao ward',150),(1692,'Loc Phat ward',150),(1693,'Loc Son ward',150),(1694,'Dai Lao ward',150),(1695,'Dam Bri ward',150),(1696,'Loc Chau ward',150),(1697,'Loc Nga ward',150),(1698,'Loc Thanh ward',150),(1699,'Loc Tien ward',150),(1700,'Ward 1',151),(1701,'Ward 2',151),(1702,'Ward 3',151),(1703,'Ward 4',151),(1704,'Ward 5',151),(1705,'Ward 6',151),(1706,'Ward 7',151),(1707,'Ward 8',151),(1708,'Ward 9',151),(1709,'Ward 10',151),(1710,'Ward 11',151),(1711,'Ward 12',151),(1712,'Ta Nung ward ',151),(1713,'Tram Hanh ward ',151),(1714,'Xuan Tho ward',151),(1715,'Xuan Truong ward',151);
/*!40000 ALTER TABLE `wards` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2021-12-02 19:30:11
