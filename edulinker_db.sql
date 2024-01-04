 -- phpMyAdmin SQL Dump
-- version 5.1.1
-- https://www.phpmyadmin.net/
--
-- Hôte : 127.0.0.1:3306
-- Généré le : mer. 03 jan. 2024 à 23:03
-- Version du serveur : 5.7.36
-- Version de PHP : 8.1.0

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de données : `edulinker_db`
--

-- --------------------------------------------------------

--
-- Structure de la table `accounts`
--

DROP TABLE IF EXISTS `accounts`;
CREATE TABLE IF NOT EXISTS `accounts` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `email` varchar(255) NOT NULL,
  `password` varchar(255) DEFAULT NULL,
  `role` varchar(10) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `email` (`email`)
) ENGINE=MyISAM AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

--
-- Déchargement des données de la table `accounts`
--

INSERT INTO `accounts` (`id`, `email`, `password`, `role`) VALUES
(1, 'kamalmarouane@ensa.ma', '$2a$12$vgCuB8gHREZ06DRavL5/C.vYMJF3zR3b9TWansSc4exUU63bswvDC', 'PROF'),
(2, 'admin@ensa.ma', '$2a$12$ABsRJUv1vErV5x8hu3vqLe0ERK0SFKrCHqTw.E9YIK5XeGkLhE6AS', 'ADMIN');

-- --------------------------------------------------------

--
-- Structure de la table `administrators`
--

DROP TABLE IF EXISTS `administrators`;
CREATE TABLE IF NOT EXISTS `administrators` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `last_name` varchar(25) DEFAULT NULL,
  `first_name` varchar(25) DEFAULT NULL,
  `email` varchar(100) DEFAULT NULL,
  `phone_number` varchar(10) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `email` (`email`)
) ENGINE=MyISAM AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;

--
-- Déchargement des données de la table `administrators`
--

INSERT INTO `administrators` (`id`, `last_name`, `first_name`, `email`, `phone_number`) VALUES
(1, 'Kassimi', 'Abdelkarim', 'admin@ensa.ma', '0584744633');

-- --------------------------------------------------------

--
-- Structure de la table `evaluation_procedures`
--

DROP TABLE IF EXISTS `evaluation_procedures`;
CREATE TABLE IF NOT EXISTS `evaluation_procedures` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `type` varchar(20) DEFAULT NULL,
  `coefficient` float DEFAULT NULL,
  `element_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `element_id` (`element_id`)
) ENGINE=MyISAM AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

--
-- Déchargement des données de la table `evaluation_procedures`
--

INSERT INTO `evaluation_procedures` (`id`, `type`, `coefficient`, `element_id`) VALUES
(1, 'TP', 0.3, 1),
(2, 'Projet', 0.4, 1);

-- --------------------------------------------------------

--
-- Structure de la table `grades`
--

DROP TABLE IF EXISTS `grades`;
CREATE TABLE IF NOT EXISTS `grades` (
  `id` bigint(20) NOT NULL,
  `grade` float DEFAULT NULL,
  `evaluation_procedure_id` bigint(20) DEFAULT NULL,
  `status` tinyint(1) DEFAULT NULL,
  `student_id` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `procedure_id` (`evaluation_procedure_id`),
  KEY `student_id` (`student_id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Déchargement des données de la table `grades`
--

INSERT INTO `grades` (`id`, `grade`, `evaluation_procedure_id`, `status`, `student_id`) VALUES
(1, 15, 1, 1, 'R3211111'),
(2, 17.5, 2, 1, 'R3211111');

-- --------------------------------------------------------

--
-- Structure de la table `modules`
--

DROP TABLE IF EXISTS `modules`;
CREATE TABLE IF NOT EXISTS `modules` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `sector_id` bigint(20) DEFAULT NULL,
  `semester` varchar(2) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `sector_id` (`sector_id`)
) ENGINE=MyISAM AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;

--
-- Déchargement des données de la table `modules`
--

INSERT INTO `modules` (`id`, `name`, `sector_id`, `semester`) VALUES
(1, 'Analyse pour l\'ingénieur', 1, 'S1');

-- --------------------------------------------------------

--
-- Structure de la table `module_elements`
--

DROP TABLE IF EXISTS `module_elements`;
CREATE TABLE IF NOT EXISTS `module_elements` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `coefficient` float DEFAULT NULL,
  `module_id` bigint(20) DEFAULT NULL,
  `professor_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `module_id` (`module_id`)
) ENGINE=MyISAM AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;

--
-- Déchargement des données de la table `module_elements`
--

INSERT INTO `module_elements` (`id`, `name`, `coefficient`, `module_id`, `professor_id`) VALUES
(1, 'Topologie', 0.6, 1, 1);

-- --------------------------------------------------------

--
-- Structure de la table `professors`
--

DROP TABLE IF EXISTS `professors`;
CREATE TABLE IF NOT EXISTS `professors` (
  `code` bigint(20) NOT NULL AUTO_INCREMENT,
  `last_name` varchar(255) DEFAULT NULL,
  `first_name` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `phone_number` varchar(20) DEFAULT NULL,
  `speciality` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`code`),
  UNIQUE KEY `email` (`email`)
) ENGINE=MyISAM AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;

--
-- Déchargement des données de la table `professors`
--

INSERT INTO `professors` (`code`, `last_name`, `first_name`, `email`, `phone_number`, `speciality`) VALUES
(1, 'Marouane', 'Kamal', 'kamalmarouane@ensa.ma', '0674635359', 'IT');

-- --------------------------------------------------------

--
-- Structure de la table `sectors`
--

DROP TABLE IF EXISTS `sectors`;
CREATE TABLE IF NOT EXISTS `sectors` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `name_abbreviation` varchar(10) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;

--
-- Déchargement des données de la table `sectors`
--

INSERT INTO `sectors` (`id`, `name`, `name_abbreviation`) VALUES
(1, 'Informatique et Ingénierie des Données', 'IID');

-- --------------------------------------------------------

--
-- Structure de la table `students`
--

DROP TABLE IF EXISTS `students`;
CREATE TABLE IF NOT EXISTS `students` (
  `last_name` varchar(255) DEFAULT NULL,
  `first_name` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `phone_number` varchar(20) DEFAULT NULL,
  `cne` varchar(20) NOT NULL,
  `appoge` varchar(20) DEFAULT NULL,
  `birth_day` date DEFAULT NULL,
  `sector_id` bigint(20) NOT NULL,
  `semester` varchar(2) NOT NULL,
  PRIMARY KEY (`cne`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Déchargement des données de la table `students`
--

INSERT INTO `students` (`last_name`, `first_name`, `email`, `phone_number`, `cne`, `appoge`, `birth_day`, `sector_id`, `semester`) VALUES
('Aissy', 'Achraf', 'achrafaissy@ensa.ma', '0645332155', 'R3211111', '00001', '2001-08-10', 1, 'S1');
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
