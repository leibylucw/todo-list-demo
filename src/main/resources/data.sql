-- Sample Users
INSERT INTO users (id, name) VALUES
(1, 'Luke'),
(2, 'Alex');

-- Sample Tasks for Luke
INSERT INTO tasks (user_id, name, description) VALUES
(1, 'Buy groceries', 'Milk, bread, eggs, and coffee'),
(1, 'Finish Spring Boot project', 'Complete CRUD endpoints for todo list demo'),
(1, 'Workout', '1-hour bike ride at the gym');

-- Sample Tasks for Alex
INSERT INTO tasks (user_id, name, description) VALUES
(2, 'Clean the house', 'Vacuum, dust, and mop floors'),
(2, 'Pay bills', 'Electric, internet, and credit card payment'),
(2, 'Read a book', 'Finish reading The Pragmatic Programmer');
