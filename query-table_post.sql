
CREATE TABLE IF NOT EXISTS posts (
    id BIGSERIAL PRIMARY KEY,
    title VARCHAR(100) NOT NULL,      
    author VARCHAR(10) NOT NULL,     
    password VARCHAR(255) NOT NULL,   
    content TEXT NOT NULL,            
    views INTEGER DEFAULT 0,          
    is_deleted BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP
);


INSERT INTO posts (title, author, password, content, views, is_deleted, created_at) 
VALUES 
('Post 1', 'Admin', '1234', 'This is the first post.', 0, FALSE, NOW()),
('Post 2', 'User01', '1234', 'Content for the second post.', 0, FALSE, NOW());