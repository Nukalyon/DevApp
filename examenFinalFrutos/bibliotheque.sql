-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1:3306
-- Generation Time: Dec 17, 2024 at 02:49 AM
-- Server version: 9.1.0
-- PHP Version: 8.4.0

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `bibliotheque`
--

-- --------------------------------------------------------

--
-- Table structure for table `abonne`
--

DROP TABLE IF EXISTS `abonne`;
CREATE TABLE IF NOT EXISTS `abonne` (
  `idAbonne` int NOT NULL AUTO_INCREMENT,
  `nom` varchar(100) NOT NULL,
  `prenom` varchar(100) NOT NULL,
  `adresse` varchar(255) NOT NULL,
  `date_inscription` date NOT NULL,
  `type_abonnement` enum('standard','premium') NOT NULL,
  `cotisation` decimal(10,2) NOT NULL,
  PRIMARY KEY (`idAbonne`)
) ENGINE=MyISAM AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `abonne`
--

INSERT INTO `abonne` (`idAbonne`, `nom`, `prenom`, `adresse`, `date_inscription`, `type_abonnement`, `cotisation`) VALUES
(1, 'Julien', 'Paul', '123 Rue des Jasmins', '2024-01-15', 'standard', 50.00),
(2, 'Martin', 'Sophie', '456 Rue Laval', '2024-02-20', 'premium', 100.00),
(3, 'Durand', 'Dominique', '767 blvd Bourassa', '2024-03-10', 'standard', 50.00),
(4, 'Lemoine', 'Julie', '101 Rue Kepler', '2024-04-05', 'premium', 120.00);

-- --------------------------------------------------------

--
-- Table structure for table `emprunt`
--

DROP TABLE IF EXISTS `emprunt`;
CREATE TABLE IF NOT EXISTS `emprunt` (
  `idEmprunt` int NOT NULL AUTO_INCREMENT,
  `idAbonne` int NOT NULL,
  `idLivre` int NOT NULL,
  `date_emprunt` date NOT NULL,
  `date_retour` date DEFAULT NULL,
  PRIMARY KEY (`idEmprunt`),
  KEY `idAbonne` (`idAbonne`),
  KEY `idLivre` (`idLivre`)
) ENGINE=MyISAM AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `emprunt`
--

INSERT INTO `emprunt` (`idEmprunt`, `idAbonne`, `idLivre`, `date_emprunt`, `date_retour`) VALUES
(1, 1, 1, '2024-05-10', '2024-06-10'),
(2, 2, 2, '2024-06-01', '2024-07-01'),
(3, 3, 3, '2024-06-15', '2024-07-15'),
(4, 4, 4, '2024-07-05', '2024-08-05');

-- --------------------------------------------------------

--
-- Table structure for table `livre`
--

DROP TABLE IF EXISTS `livre`;
CREATE TABLE IF NOT EXISTS `livre` (
  `idLivre` int NOT NULL AUTO_INCREMENT,
  `titre` varchar(255) NOT NULL,
  `auteur` varchar(255) NOT NULL,
  `annee_publication` int NOT NULL,
  `disponible` tinyint(1) NOT NULL DEFAULT '1',
  PRIMARY KEY (`idLivre`)
) ENGINE=MyISAM AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `livre`
--

INSERT INTO `livre` (`idLivre`, `titre`, `auteur`, `annee_publication`, `disponible`) VALUES
(1, 'Les Misérables', 'Victor Hugo', 1862, 1),
(2, 'Le Petit Prince', 'Antoine de Saint-Exupéry', 1943, 1),
(3, '1984', 'George Orwell', 1949, 1),
(4, 'La Peste', 'Albert Camus', 1947, 1),
(5, 'Les Fleurs du mal', 'Charles Baudelaire', 1857, 0);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
