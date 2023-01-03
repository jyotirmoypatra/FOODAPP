-- phpMyAdmin SQL Dump
-- version 4.9.5
-- https://www.phpmyadmin.net/
--
-- Host: localhost
-- Generation Time: Jan 03, 2023 at 02:20 PM
-- Server version: 10.5.16-MariaDB
-- PHP Version: 7.3.32

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `id20013410_foodbite`
--

-- --------------------------------------------------------

--
-- Table structure for table `admin`
--

CREATE TABLE `admin` (
  `id` int(11) NOT NULL,
  `ad_name` varchar(255) NOT NULL,
  `ad_email` varchar(255) NOT NULL,
  `ad_pass` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `admin`
--

INSERT INTO `admin` (`id`, `ad_name`, `ad_email`, `ad_pass`) VALUES
(1, 'Jyotirmoy Patra', 'j@gmail.com', '12345');

-- --------------------------------------------------------

--
-- Table structure for table `food`
--

CREATE TABLE `food` (
  `id` int(11) NOT NULL,
  `foodName` varchar(255) NOT NULL,
  `foodCategory` varchar(255) NOT NULL,
  `foodPrice` int(11) NOT NULL,
  `imgurl` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `food`
--

INSERT INTO `food` (`id`, `foodName`, `foodCategory`, `foodPrice`, `imgurl`) VALUES
(19, 'Chicken Tandoori', 'NON-VEG', 399, 'https://chichi-feather.000webhostapp.com/foodimage/picture_20221215T004514.jpg'),
(20, 'Fried Rice', 'VEG', 199, 'https://chichi-feather.000webhostapp.com/foodimage/picture_20221215T004620.jpg'),
(21, 'Chicken Biryani', 'NON-VEG', 299, 'https://chichi-feather.000webhostapp.com/foodimage/picture_20221215T004723.jpg'),
(22, 'Lachha Paratha', 'VEG', 35, 'https://chichi-feather.000webhostapp.com/foodimage/picture_20221215T005329.jpg'),
(23, 'Belgium Chocolate Ice cream', 'Icecream', 299, 'https://chichi-feather.000webhostapp.com/foodimage/picture_20221215T005657.jpg'),
(24, 'Coca-Cola 1.2Ltr', 'Colddrink', 1, 'https://chichi-feather.000webhostapp.com/foodimage/picture_20221215T005825.jpg'),
(25, 'Vanilla Ice Cream', 'Icecream', 199, 'https://chichi-feather.000webhostapp.com/foodimage/picture_20221215T010341.jpg'),
(26, 'tedt', 'VEG', 120, 'https://chichi-feather.000webhostapp.com/foodimage/picture_20221220T165913.jpg');

-- --------------------------------------------------------

--
-- Table structure for table `foodCategory`
--

CREATE TABLE `foodCategory` (
  `id` int(11) NOT NULL,
  `categoryName` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `foodCategory`
--

INSERT INTO `foodCategory` (`id`, `categoryName`) VALUES
(1, 'VEG'),
(2, 'NON-VEG'),
(8, 'indian'),
(9, 'Icecream'),
(10, 'Colddrink'),
(11, 'Wine');

-- --------------------------------------------------------

--
-- Table structure for table `itemOrder`
--

CREATE TABLE `itemOrder` (
  `id` int(11) NOT NULL,
  `order_id` int(11) NOT NULL,
  `food_id` int(11) NOT NULL,
  `Quantity` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `itemOrder`
--

INSERT INTO `itemOrder` (`id`, `order_id`, `food_id`, `Quantity`) VALUES
(45, 39, 19, 1),
(46, 39, 20, 2),
(47, 40, 22, 1),
(48, 41, 19, 1),
(49, 42, 20, 1),
(50, 43, 21, 3),
(51, 43, 20, 5),
(52, 44, 19, 2),
(53, 45, 21, 4),
(54, 45, 22, 1),
(55, 46, 19, 1),
(56, 47, 20, 3),
(57, 47, 21, 3),
(58, 48, 19, 1),
(59, 48, 20, 1),
(60, 48, 21, 1),
(61, 49, 19, 1),
(62, 49, 21, 1),
(63, 49, 24, 1),
(64, 49, 23, 1),
(65, 49, 20, 3),
(66, 49, 22, 1),
(67, 49, 25, 1),
(68, 50, 24, 1),
(69, 50, 23, 1),
(70, 50, 25, 1),
(71, 51, 20, 1),
(72, 52, 24, 1);

-- --------------------------------------------------------

--
-- Table structure for table `orderTable`
--

CREATE TABLE `orderTable` (
  `orderId` int(11) NOT NULL,
  `userid` int(11) NOT NULL,
  `payment_type` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `transaction_id` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `total_price` varchar(255) COLLATE utf8_unicode_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `orderTable`
--

INSERT INTO `orderTable` (`orderId`, `userid`, `payment_type`, `transaction_id`, `total_price`) VALUES
(39, 1, 'COD', 'null', '797'),
(40, 1, 'COD', 'null', '35'),
(41, 1, 'RAZORPAY', 'pay_KxI3DSy9uWeSLs', '399'),
(42, 1, 'COD', 'null', '199'),
(43, 1, 'COD', 'null', '1892'),
(44, 1, 'COD', 'null', '798'),
(45, 1, 'COD', 'null', '1231'),
(46, 1, 'RAZORPAY', 'pay_KxJK2Sr8Q1wI6T', '399'),
(47, 1, 'COD', 'null', '1494'),
(48, 1, 'COD', 'null', '897'),
(49, 1, 'RAZORPAY', 'pay_KzJEvJ92F9201g', '1888'),
(50, 1, 'RAZORPAY', 'pay_KzJFzAxa9RJhgb', '558'),
(51, 1, 'RAZORPAY', 'pay_KzJHCHDj8BrlPG', '199'),
(52, 1, 'RAZORPAY', 'pay_KzJOWCWWfIYJDx', '1');

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE `users` (
  `id` int(11) NOT NULL,
  `uname` varchar(255) NOT NULL,
  `uemail` varchar(255) NOT NULL,
  `upass` varchar(255) NOT NULL,
  `uphone` varchar(255) NOT NULL,
  `uaddress` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`id`, `uname`, `uemail`, `upass`, `uphone`, `uaddress`) VALUES
(1, 'Jyotirmoy Patra', 'u@gmail.com', '123456', '7602773406', 'Kolkata, Newtown,700102'),
(2, 'Jyotirmoy', 'jp@gmail.com', '123', '', ''),
(3, 'jyoti', 'r@gmail.com', 'jyoti', 'jyoti', 'kolkata');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `admin`
--
ALTER TABLE `admin`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `food`
--
ALTER TABLE `food`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `foodCategory`
--
ALTER TABLE `foodCategory`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `itemOrder`
--
ALTER TABLE `itemOrder`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `orderTable`
--
ALTER TABLE `orderTable`
  ADD PRIMARY KEY (`orderId`);

--
-- Indexes for table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `admin`
--
ALTER TABLE `admin`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT for table `food`
--
ALTER TABLE `food`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=27;

--
-- AUTO_INCREMENT for table `foodCategory`
--
ALTER TABLE `foodCategory`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=12;

--
-- AUTO_INCREMENT for table `itemOrder`
--
ALTER TABLE `itemOrder`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=73;

--
-- AUTO_INCREMENT for table `orderTable`
--
ALTER TABLE `orderTable`
  MODIFY `orderId` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=53;

--
-- AUTO_INCREMENT for table `users`
--
ALTER TABLE `users`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
