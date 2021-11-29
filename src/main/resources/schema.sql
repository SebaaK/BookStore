DROP TABLE IF EXISTS `reader`;
CREATE TABLE `reader`
(
    `id`             SERIAL PRIMARY KEY,
    `name`           VARCHAR(50) NOT NULL,
    `lastname`       VARCHAR(50) NOT NULL,
    `create_account` DATETIME    NOT NULL
);

DROP TABLE IF EXISTS `book`;
CREATE TABLE `book`
(
    `id`               SERIAL PRIMARY KEY,
    `title`            VARCHAR(50) NOT NULL,
    `author`           VARCHAR(80) NOT NULL,
    `publication_date` DATETIME    NOT NULL
);

DROP TABLE IF EXISTS `storage`;
CREATE TABLE `storage`
(
    `id`      SERIAL PRIMARY KEY,
    `book_id` BIGINT UNSIGNED NOT NULL,
    `status`  VARCHAR(15)     NOT NULL,
    FOREIGN KEY (`book_id`) REFERENCES book (`id`)
);

DROP TABLE IF EXISTS `borrow_book`;
CREATE TABLE `borrow_book`
(
    `id`             SERIAL PRIMARY KEY,
    `storage_id`     BIGINT UNSIGNED NOT NULL,
    `reader_id`      BIGINT UNSIGNED NOT NULL,
    `borrow_date`    DATETIME        NOT NULL,
    `returning_date` DATETIME        NULL,
    FOREIGN KEY (`storage_id`) REFERENCES storage (`id`),
    FOREIGN KEY (`reader_id`) REFERENCES reader (`id`)
);