CREATE TABLE comments (
    id SERIAL PRIMARY KEY,
    post_id BIGINT NOT NULL,
    content TEXT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Attach the trigger to the table
CREATE TRIGGER update_updated_at_timestamp
BEFORE UPDATE ON comments
FOR EACH ROW
EXECUTE PROCEDURE update_timestamp();