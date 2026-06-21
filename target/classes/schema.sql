CREATE TABLE IF NOT EXISTS notice (
    id INT PRIMARY KEY AUTO_INCREMENT,
    title VARCHAR(200),
    content VARCHAR(1000),
    author VARCHAR(50),
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

INSERT INTO notice (id, title, content, author) VALUES
(1, 'Welcome', 'Welcome to DF OA System', 'admin'),
(2, 'Meeting', 'Meeting at 3pm today', 'admin'),
(3, 'Holiday', 'Holiday from Feb 1-7', 'manager');