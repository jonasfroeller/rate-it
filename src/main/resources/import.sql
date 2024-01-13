/*
CREATE TABLE SOFTWARE
(
    ID             BIGINT PRIMARY KEY AUTO_INCREMENT,
    NAME           VARCHAR(255) NOT NULL,
    DESCRIPTION    VARCHAR(255),
    WEBSITE        VARCHAR(255),
    REPOSITORY     VARCHAR(255),
    IS_OPEN_SOURCE BOOLEAN
);

CREATE TABLE RATING
(
    ID          INT PRIMARY KEY AUTO_INCREMENT,
    RATING      INT,
    SOFTWARE_ID BIGINT,
    FOREIGN KEY (SOFTWARE_ID) REFERENCES SOFTWARE (ID)
);
*/

INSERT INTO SOFTWARE (NAME, DESCRIPTION, WEBSITE, REPOSITORY, IS_OPEN_SOURCE)
VALUES ('Software1', 'Beschreibung für Software 1', 'http://www.software1.com', 'http://www.repo1.com', true),
       ('Software2', 'Beschreibung für Software 2', 'http://www.software2.com', 'http://www.repo2.com', false),
       ('Software3', 'Beschreibung für Software 3', 'http://www.software3.com', 'http://www.repo3.com', true),
       ('Software4', 'Beschreibung für Software 4', 'http://www.software4.com', 'http://www.repo4.com', false),
       ('Software5', 'Beschreibung für Software 5', 'http://www.software5.com', 'http://www.repo5.com', true);

INSERT INTO RATING (RATING, SOFTWARE_ID)
VALUES (5, 1),
       (4, 2),
       (2, 3),
       (5, 4),
       (4, 5);
