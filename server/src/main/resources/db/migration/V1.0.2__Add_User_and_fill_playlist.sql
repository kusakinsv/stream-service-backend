INSERT INTO users (id, username, email, password, role, user_status)
VALUES (1, 'admin', 'admin@admin.aa', '$2a$12$29mPsDjJfNj4lUAouXpFr.3/5AHNRcx.EJgwx8ewLpZwW8Qti4Z7y', 'ADMIN', 'ACTIVE'), --admin/admin
       (2, 'user', 'user@test.test', '$2a$12$vUZQKhn4Py.fKdg.JZ52O.KBf46x3Ogi8Pucq3VVopvNdQPVzo3J2', 'USER', 'ACTIVE');

insert into playlist (id, title, user_id, is_main)
VALUES (1, 'My music', 1, true),
       (2, 'My music', 2, true);

insert into playlist_position (id, position, title, music_track_id, playlist_id)
VALUES (1, 1, 'В. Цой - Группа крови', 1, 1),
       (2, 1, 'В. Цой - Группа крови', 1, 2),
       (3, 2, 'Дурной вкус - Пластинки', 2, 1),
       (4, 2, 'Дурной вкус - Пластинки', 2, 2),
       (5, 3, 'В. Цой - Спокойная ночь', 3, 1),
       (6, 3, 'В. Цой - Спокойная ночь', 3, 2),
       (7, 4, 'Сплин - Выхода нет', 4, 1),
       (8, 4, 'Сплин - Выхода нет', 4, 2),
       (9, 5, 'Би-2 - А мы не ангелы, парень', 5, 1),
       (10, 5, 'Би-2 - А мы не ангелы, парень', 5, 2),
       (11, 6, 'Би-2, Чичерина - Мой рокнролл', 6, 1),
       (12, 6, 'Би-2, Чичерина - Мой рокнролл', 6, 2);