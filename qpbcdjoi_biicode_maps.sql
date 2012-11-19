-- phpMyAdmin SQL Dump
-- version 3.4.3.2
-- http://www.phpmyadmin.net
--
-- Servidor: localhost
-- Tiempo de generación: 19-11-2012 a las 17:55:56
-- Versión del servidor: 5.0.92
-- Versión de PHP: 5.2.6

SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Base de datos: `qpbcdjoi_biicode_maps`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `routes`
--

CREATE TABLE IF NOT EXISTS `routes` (
  `origin` varchar(60) NOT NULL,
  `destiny` varchar(60) NOT NULL,
  `path` varchar(200) NOT NULL,
  `searchedTimes` int(11) NOT NULL,
  PRIMARY KEY  (`origin`,`destiny`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `routes`
--

INSERT INTO `routes` (`origin`, `destiny`, `path`, `searchedTimes`) VALUES
('Roma', 'Constantinopla', 'Roma>Paris>Jerusalen>Constantinopla', 19),
('Numancia', 'Cartago', 'Numancia>Stalingrado>Tobruk>Constantinopla>Cartago', 19),
('Cartago', 'Kursk', 'Cartago>Constantinopla>Jerusalen>Berlin>Kursk', 19);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `visitedCities`
--

CREATE TABLE IF NOT EXISTS `visitedCities` (
  `visitedCity` varchar(60) NOT NULL,
  `visitedTimes` int(11) NOT NULL,
  PRIMARY KEY  (`visitedCity`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `visitedCities`
--

INSERT INTO `visitedCities` (`visitedCity`, `visitedTimes`) VALUES
('Paris', 52),
('Berlin', 52),
('Jerusalen', 69),
('Constantinopla', 52),
('Cartago', 35),
('Tobruk', 69),
('Kursk', 52),
('Toledo', 52),
('Stalingrado', 69),
('Marathon', 52),
('Numancia', 35),
('Roma', 52);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
