INSERT INTO users (id, name, mail)
SELECT * FROM (
    VALUES
        ('11111111-1111-1111-1111-111111111111'::uuid, 'John Doe', 'john@example.com'),
        ('22222222-2222-2222-2222-222222222222'::uuid, 'Alice Smith', 'alice@example.com'),
        ('33333333-3333-3333-3333-333333333333'::uuid, 'Bob Johnson', 'bob@example.com')
) AS v(id, name, mail)
WHERE NOT EXISTS (SELECT 1 FROM users);