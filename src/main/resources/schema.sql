DROP TABLE IF EXISTS characters;

CREATE TABLE characters (
    id INT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    description VARCHAR(5000) NOT NULL)