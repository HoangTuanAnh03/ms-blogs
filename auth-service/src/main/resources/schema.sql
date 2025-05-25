SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `authdb`
--

-- --------------------------------------------------------


-- --------------------------------------------------------
--
-- Table structure for table `users`
--
CREATE TABLE IF NOT EXISTS `users`
(
    `id`         varchar(255) NOT NULL,
    `created_at` datetime(6) DEFAULT NULL,
    `created_by` varchar(255) DEFAULT NULL,
    `updated_at` datetime(6) DEFAULT NULL,
    `updated_by` varchar(255) DEFAULT NULL,
    `active`     bit(1)       DEFAULT NULL,
    `avatar`     varchar(255) DEFAULT NULL,
    `dob`        date         DEFAULT NULL,
    `email`      varchar(255) DEFAULT NULL,
    `gender`     enum('FEMALE','MALE') DEFAULT 'FEMALE',
    `name`       varchar(255) DEFAULT NULL,
    `role`       varchar(255) DEFAULT 'USER',
    `password`   varchar(255) DEFAULT NULL,
    `is_locked`  bit(1)       DEFAULT 0,
    PRIMARY KEY (`id`)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

INSERT INTO users (id, created_at, created_by, updated_at, updated_by, active, avatar, dob, email, gender, name, role, password, is_locked)
SELECT 'e692cd89-e09e-4651-afb8-8956d349ff6c',
       NOW(),
       'system',
       NOW(),
       'system',
       1,
       NULL,
       '2000-01-01',
       'user@gmail.com',
       'FEMALE',
       'Default User',
       'USER',
       '$2a$10$EJkL.sXN6Tg.NHzrmTk7DeWJf2lO/QYAJk7x7S41T4iHlgfimeUQu',
       0 WHERE NOT EXISTS (SELECT 1 FROM users);

INSERT INTO users (id, created_at, created_by, updated_at, updated_by, active, avatar, dob, email, gender, name, role, password, is_locked)
SELECT 'e692cd89-e09e-4651-afb8-8956d349ftdf',
       NOW(),
       'system',
       NOW(),
       'system',
       1,
       NULL,
       '2000-01-01',
       'thaidangfa@gmail.com',
       'FEMALE',
       'IAmTDF',
       'USER',
       '$2a$10$EJkL.sXN6Tg.NHzrmTk7DeWJf2lO/QYAJk7x7S41T4iHlgfimeUQu',
       0 WHERE NOT EXISTS (SELECT 1 FROM users);

INSERT INTO users (id, created_at, created_by, updated_at, updated_by, active, avatar, dob, email, gender, name, role, password, is_locked)
SELECT
    'a85f7cb2-9fe4-42d3-b821-4f6e95d7f761',
    NOW(),
    'system',
    NOW(),
    'system',
    1,
    NULL,
    '1990-01-01',
    'admin@gmail.com',
    'MALE',
    'System Administrator',
    'ADMIN',
    '$2a$10$EJkL.sXN6Tg.NHzrmTk7DeWJf2lO/QYAJk7x7S41T4iHlgfimeUQu',
    0
    WHERE NOT EXISTS (SELECT 1 FROM users WHERE role = 'ADMIN');
-- --------------------------------------------------------

--
-- Table structure for table `verification_code`
--

CREATE TABLE IF NOT EXISTS `verification_code`
(
    `id`    bigint(20) NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `code`  varchar(255) DEFAULT NULL,
    `email` varchar(255) DEFAULT NULL,
    `exp`   datetime(6) DEFAULT NULL,
    `type`  tinyint(4) DEFAULT NULL
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
