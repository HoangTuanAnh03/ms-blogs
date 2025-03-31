SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";

-- Tạo bảng nếu chưa tồn tại
-- CREATE TABLE IF NOT EXISTS `invalidated_token`
-- (
--     `id`          varchar(255) NOT NULL,
--     `expiry_time` datetime(6) DEFAULT NULL,
--     PRIMARY KEY (`id`)
-- ) ENGINE = InnoDB
--   DEFAULT CHARSET = utf8mb4
--   COLLATE = utf8mb4_general_ci;

-- Chèn dữ liệu vào roles nếu chưa tồn tại
INSERT IGNORE INTO `roles` (`id`, `created_at`, `created_by`, `updated_at`, `updated_by`, `active`, `description`,
                            `name`)
VALUES (1, NULL, NULL, NULL, NULL, b'1', 'ROLE USER', 'USER'),
       (2, NULL, NULL, NULL, NULL, b'1', 'ROLE HR', 'HR'),
       (3, NULL, NULL, NULL, NULL, b'1', 'ROLE ADMIN', 'ADMIN');

-- Tạo bảng users nếu chưa tồn tại
CREATE TABLE IF NOT EXISTS `users`
(
    `id`            varchar(255) NOT NULL,
    `created_at`    datetime(6)                    DEFAULT NULL,
    `created_by`    varchar(255)                   DEFAULT NULL,
    `updated_at`    datetime(6)                    DEFAULT NULL,
    `updated_by`    varchar(255)                   DEFAULT NULL,
    `address`       varchar(255)                   DEFAULT NULL,
    `company_id`    bigint(20)                     DEFAULT NULL,
    `dob`           date                           DEFAULT NULL,
    `email`         varchar(255)                   DEFAULT NULL,
    `gender`        enum ('FEMALE','MALE','OTHER') DEFAULT NULL,
    `mobile_number` varchar(255)                   DEFAULT NULL,
    `name`          varchar(255)                   DEFAULT NULL,
    `password`      varchar(255)                   DEFAULT NULL,
    `role_id`       bigint(20)                     DEFAULT NULL,
    `active`          bit                              null,
    PRIMARY KEY (`id`),
    KEY `FKp56c1712k691lhsyewcssf40f` (`role_id`),
    CONSTRAINT `FKp56c1712k691lhsyewcssf40f` FOREIGN KEY (`role_id`) REFERENCES `roles` (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_general_ci;

-- Chèn dữ liệu vào users nếu chưa tồn tại
INSERT IGNORE INTO `users` (`id`, `created_at`, `created_by`, `updated_at`, `updated_by`, `address`, `company_id`,
                            `dob`, `email`, `gender`, `mobile_number`, `name`, `password`, `role_id`, `active`)
VALUES
    ('e692cd89-e09e-4651-afb8-8956d349ff6c', '2024-07-25 16:55:14.000000', 'anonymousUser', NULL, NULL, 'string', 0,
     '2003-07-17', 'user@gmail.com', 'FEMALE', NULL, 'USER',
     '$2a$10$EJkL.sXN6Tg.NHzrmTk7DeWJf2lO/QYAJk7x7S41T4iHlgfimeUQu', 1, 1),

    ('e692cd89-e09e-4651-afb8-8956d349ff6b', '2024-07-25 16:55:14.000000', 'anonymousUser', NULL, NULL, 'string', 0,
     '2003-07-17', 'hr@gmail.com', 'FEMALE', NULL, 'HR',
     '$2a$10$EJkL.sXN6Tg.NHzrmTk7DeWJf2lO/QYAJk7x7S41T4iHlgfimeUQu', 1, 1),

    ('e692cd89-e09e-4651-afb8-8956d349ff6a', '2024-07-25 16:55:14.000000', 'anonymousUser', NULL, NULL, 'string', 0,
     '2003-07-17', 'admin@gmail.com', 'FEMALE', NULL, 'ADMIN',
     '$2a$10$EJkL.sXN6Tg.NHzrmTk7DeWJf2lO/QYAJk7x7S41T4iHlgfimeUQu', 3, 1);


-- Thực hiện ALTER TABLE một cách an toàn
-- Nếu cột id trong bảng roles chưa có AUTO_INCREMENT, thì thêm nó
ALTER TABLE `roles`
    MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT,
    AUTO_INCREMENT = 5;

COMMIT;
