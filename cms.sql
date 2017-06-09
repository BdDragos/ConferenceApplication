-- phpMyAdmin SQL Dump
-- version 4.6.5.2
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Jun 08, 2017 at 09:14 AM
-- Server version: 10.1.21-MariaDB
-- PHP Version: 7.1.1

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `cms`
--

-- --------------------------------------------------------

--
-- Table structure for table `accept`
--

CREATE TABLE `accept` (
  `idac` int(11) NOT NULL,
  `acceptlvl` varchar(40) NOT NULL,
  `idf` int(11) NOT NULL,
  `idcm` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `admin`
--

CREATE TABLE `admin` (
  `idAd` int(11) NOT NULL,
  `username` varchar(50) NOT NULL,
  `password` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `admin`
--

INSERT INTO `admin` (`idAd`, `username`, `password`) VALUES
(1, 'admin', 'admin');

-- --------------------------------------------------------

--
-- Table structure for table `attendant`
--

CREATE TABLE `attendant` (
  `idat` int(11) NOT NULL,
  `username` varchar(70) NOT NULL,
  `password` varchar(70) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `attendant`
--

INSERT INTO `attendant` (`idat`, `username`, `password`) VALUES
(1, 'Marius', 'Marius'),
(2, 'Razvan', 'Razvan'),
(3, 'Elena', 'Elena'),
(4, 'Maria', 'Maria');

-- --------------------------------------------------------

--
-- Table structure for table `authors`
--

CREATE TABLE `authors` (
  `ida` int(11) NOT NULL,
  `username` varchar(50) NOT NULL,
  `password` varchar(50) NOT NULL,
  `name` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `authors`
--

INSERT INTO `authors` (`ida`, `username`, `password`, `name`) VALUES
(1, 'Pitagora', 'Pitagora', 'Pitagora'),
(2, 'Vasile', 'Vasile', 'Vasile'),
(3, 'Manolescu', 'Manolescu', 'Manolescu'),
(4, 'Cezar', 'Cezar', 'Cezar');

-- --------------------------------------------------------

--
-- Table structure for table `cm`
--

CREATE TABLE `cm` (
  `idcm` int(11) NOT NULL,
  `username` varchar(50) NOT NULL,
  `password` varchar(50) NOT NULL,
  `name` varchar(50) NOT NULL,
  `affiliation` varchar(50) NOT NULL,
  `email` text NOT NULL,
  `webpage` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `cm`
--

INSERT INTO `cm` (`idcm`, `username`, `password`, `name`, `affiliation`, `email`, `webpage`) VALUES
(1, 'Istvan', 'Istvan', 'Istvan', 'Babes', 'babes@bolyai.com', 'www.ubbcluj.com'),
(2, 'Rares', 'Rares', 'Rares', 'Rares Inc', 'raresinc@gmail.com', 'www.raresinc.com'),
(3, 'Cristi', 'Cristi', 'Cristi', 'Cristi Corp', 'cristicorp@gmail.com', 'www.cristi.com'),
(4, 'Catalina', 'Catalina', 'Catalina', 'Catalina Market', 'camarket@yahoo.com', 'www.camarket.com'),
(5, 'Mihaela', 'Mihaela', 'Mihaela', 'Evozon', 'mievo@gmail.com', 'www.evozon.com');

-- --------------------------------------------------------

--
-- Table structure for table `conference`
--

CREATE TABLE `conference` (
  `idconf` int(11) NOT NULL,
  `noparticipants` int(11) NOT NULL,
  `name` varchar(50) NOT NULL,
  `deadlineProposal` varchar(20) NOT NULL,
  `deadlineAbstract` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `conference`
--

INSERT INTO `conference` (`idconf`, `noparticipants`, `name`, `deadlineProposal`, `deadlineAbstract`) VALUES
(1, 25, 'JavaPreparation', '2017-10-02', '2017-10-02'),
(2, 30, 'C#andAI', '2017-10-10', '2017-10-10'),
(3, 45, 'ReactJS', '2017-06-06', '2017-06-06'),
(4, 40, 'VirtualMachine', '2017-06-06', '2017-06-06');

-- --------------------------------------------------------

--
-- Table structure for table `file`
--

CREATE TABLE `file` (
  `idf` int(11) NOT NULL,
  `keywords` text NOT NULL,
  `topic` text NOT NULL,
  `filedoc` varchar(50) NOT NULL,
  `cmaccept` varchar(20) NOT NULL DEFAULT 'borderline',
  `idses` int(11) DEFAULT '0',
  `reviewerCount` int(11) DEFAULT '0',
  `abstractData` varchar(200) NOT NULL,
  `titlu` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `file`
--

INSERT INTO `file` (`idf`, `keywords`, `topic`, `filedoc`, `cmaccept`, `idses`, `reviewerCount`, `abstractData`, `titlu`) VALUES
(1, 'computer;information;science', 'CS', 'www.tutorialsonpoint.com', 'borderline', 0, 0, 'Introduction in CS', 'CS for everyone'),
(2, 'math;algebra;linear', 'Algebric Realtions', 'www.google.com', 'borderline', 0, 0, 'Introduction in linear Algebra', 'Linear Algebra'),
(3, 'java;hibernate;mtom', 'Java', 'www.oracle.com', 'borderline', 0, 0, 'Usefulness of Java', 'Java 8 for everyone'),
(4, 'C#;windows;studio;visual', 'C#', 'www.microsoft.com', 'borderline', 0, 0, 'C# and Visual Studio', 'Working wth C#');

-- --------------------------------------------------------

--
-- Table structure for table `legaf`
--

CREATE TABLE `legaf` (
  `idaf` int(11) NOT NULL,
  `ida` int(11) NOT NULL,
  `idf` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `legaf`
--

INSERT INTO `legaf` (`idaf`, `ida`, `idf`) VALUES
(1, 1, 2),
(2, 1, 3),
(5, 2, 4),
(8, 3, 3),
(9, 4, 2),
(11, 2, 1),
(12, 3, 1),
(13, 4, 1);

-- --------------------------------------------------------

--
-- Table structure for table `participants`
--

CREATE TABLE `participants` (
  `idpa` int(11) NOT NULL,
  `ida` int(11) DEFAULT NULL,
  `idat` int(11) DEFAULT NULL,
  `ids` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `participants`
--

INSERT INTO `participants` (`idpa`, `ida`, `idat`, `ids`) VALUES
(1, 1, NULL, 1),
(2, 2, NULL, 3),
(3, 3, NULL, 5),
(4, 4, NULL, 2),
(7, NULL, 2, 6),
(8, NULL, 1, 3),
(9, NULL, 1, 1),
(10, NULL, 4, 4),
(11, NULL, 3, 1),
(12, NULL, 2, 5),
(13, 4, NULL, 1);

-- --------------------------------------------------------

--
-- Table structure for table `sections`
--

CREATE TABLE `sections` (
  `ids` int(11) NOT NULL,
  `idc` int(11) NOT NULL,
  `seschair` int(11) NOT NULL,
  `name` varchar(50) NOT NULL DEFAULT '0',
  `hour` varchar(10) NOT NULL DEFAULT '0',
  `date` varchar(30) NOT NULL DEFAULT '0'
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `sections`
--

INSERT INTO `sections` (`ids`, `idc`, `seschair`, `name`, `hour`, `date`) VALUES
(1, 1, 2, 'Workshop', '19:00', '2017-11-11'),
(2, 1, 1, 'Introduction', '19:00', '2017-11-11'),
(3, 2, 4, 'TreasureHunt', '15:00', '2017-10-10'),
(4, 2, 3, 'FastCode', '13:00', '2017-10-10'),
(5, 3, 5, 'Optimisation', '17:30', '2017-09-08'),
(6, 4, 4, 'Hackathon', '15:20', '2017-09-08');

-- --------------------------------------------------------

--
-- Table structure for table `userdefault`
--

CREATE TABLE `userdefault` (
  `username` varchar(50) NOT NULL,
  `password` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Indexes for dumped tables
--

--
-- Indexes for table `accept`
--
ALTER TABLE `accept`
  ADD PRIMARY KEY (`idac`),
  ADD KEY `idf` (`idf`),
  ADD KEY `idcm` (`idcm`);

--
-- Indexes for table `admin`
--
ALTER TABLE `admin`
  ADD PRIMARY KEY (`idAd`);

--
-- Indexes for table `attendant`
--
ALTER TABLE `attendant`
  ADD PRIMARY KEY (`idat`);

--
-- Indexes for table `authors`
--
ALTER TABLE `authors`
  ADD PRIMARY KEY (`ida`);

--
-- Indexes for table `cm`
--
ALTER TABLE `cm`
  ADD PRIMARY KEY (`idcm`);

--
-- Indexes for table `conference`
--
ALTER TABLE `conference`
  ADD PRIMARY KEY (`idconf`);

--
-- Indexes for table `file`
--
ALTER TABLE `file`
  ADD PRIMARY KEY (`idf`),
  ADD KEY `fk_file_ses` (`idses`);

--
-- Indexes for table `legaf`
--
ALTER TABLE `legaf`
  ADD PRIMARY KEY (`idaf`),
  ADD KEY `ida` (`ida`),
  ADD KEY `idf` (`idf`);

--
-- Indexes for table `participants`
--
ALTER TABLE `participants`
  ADD PRIMARY KEY (`idpa`),
  ADD KEY `idat` (`idat`),
  ADD KEY `ida` (`ida`),
  ADD KEY `ids` (`ids`);

--
-- Indexes for table `sections`
--
ALTER TABLE `sections`
  ADD PRIMARY KEY (`ids`),
  ADD KEY `idc` (`idc`),
  ADD KEY `fk_section_cm` (`seschair`);

--
-- Indexes for table `userdefault`
--
ALTER TABLE `userdefault`
  ADD PRIMARY KEY (`username`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `accept`
--
ALTER TABLE `accept`
  MODIFY `idac` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `admin`
--
ALTER TABLE `admin`
  MODIFY `idAd` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;
--
-- AUTO_INCREMENT for table `attendant`
--
ALTER TABLE `attendant`
  MODIFY `idat` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;
--
-- AUTO_INCREMENT for table `authors`
--
ALTER TABLE `authors`
  MODIFY `ida` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;
--
-- AUTO_INCREMENT for table `cm`
--
ALTER TABLE `cm`
  MODIFY `idcm` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;
--
-- AUTO_INCREMENT for table `file`
--
ALTER TABLE `file`
  MODIFY `idf` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=12;
--
-- AUTO_INCREMENT for table `legaf`
--
ALTER TABLE `legaf`
  MODIFY `idaf` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=32;
--
-- AUTO_INCREMENT for table `participants`
--
ALTER TABLE `participants`
  MODIFY `idpa` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=14;
--
-- AUTO_INCREMENT for table `sections`
--
ALTER TABLE `sections`
  MODIFY `ids` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;
--
-- Constraints for dumped tables
--

--
-- Constraints for table `accept`
--
ALTER TABLE `accept`
  ADD CONSTRAINT `accept_ibfk_1` FOREIGN KEY (`idf`) REFERENCES `file` (`idf`),
  ADD CONSTRAINT `accept_ibfk_2` FOREIGN KEY (`idcm`) REFERENCES `cm` (`idcm`);

--
-- Constraints for table `legaf`
--
ALTER TABLE `legaf`
  ADD CONSTRAINT `legaf_ibfk_1` FOREIGN KEY (`ida`) REFERENCES `authors` (`ida`),
  ADD CONSTRAINT `legaf_ibfk_2` FOREIGN KEY (`idf`) REFERENCES `file` (`idf`);

--
-- Constraints for table `participants`
--
ALTER TABLE `participants`
  ADD CONSTRAINT `participants_ibfk_1` FOREIGN KEY (`idat`) REFERENCES `attendant` (`idat`),
  ADD CONSTRAINT `participants_ibfk_2` FOREIGN KEY (`ida`) REFERENCES `authors` (`ida`),
  ADD CONSTRAINT `participants_ibfk_3` FOREIGN KEY (`ids`) REFERENCES `sections` (`ids`);

--
-- Constraints for table `sections`
--
ALTER TABLE `sections`
  ADD CONSTRAINT `fk_section_cm` FOREIGN KEY (`seschair`) REFERENCES `cm` (`idcm`),
  ADD CONSTRAINT `fk_section_conference` FOREIGN KEY (`idc`) REFERENCES `conference` (`idconf`);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
