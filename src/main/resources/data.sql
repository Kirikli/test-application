INSERT INTO users (id, name, mail)
SELECT * FROM (
    VALUES
        (gen_random_uuid(), 'John Doe', 'john@example.com'),
        (gen_random_uuid(), 'Alice Smith', 'alice@example.com'),
        (gen_random_uuid(), 'Bob Johnson', 'bob@example.com')
) AS v(id, name, mail)
WHERE NOT EXISTS (SELECT 1 FROM users);