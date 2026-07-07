INSERT INTO music_track (id,  track_name, url, is_need_proxy)
VALUES
    (1, 'В. Цой - Группа крови', 'https://muzmas.ru/uploads/music/2023/30/kino-v-coj-gruppa-krovi-mp3.mp3', false),
    (2, 'Дурной Вкус - Пластинки', 'https://dnl2.drivemusic.me/dl/D6LbkIcIjm2lM0buLPT_vw/1710815764/download_music/2023/08/durnojj-vkus-plastinki.mp3', false),
    (3, 'В. Цой - Спокойная ночь', 'https://s.muzrecord.com/files/kino-spokoynaya-noch.mp3', false),
    (4, 'Сплин - Выхода нет', 'https://dnl1.drivemusic.me/dl/zpn_cj1mWs4DJ2BctTOykA/1713142632/download_music/2012/10/splin-vykhoda-net.mp3', false),
    (5, 'Би-2, Агата Кристи - А мы не ангелы, парень', 'https://s.muzrecord.com/mp3/2020-10-2/1601618222_agata_kristi_bi2_a_my_ne_angely_paren.mp3', false),
    (6, 'Би-2, Чичерина - Мой рокнролл', 'https://muzgen.net/uploads/music/2021/08/Bi_2_i_Chicherina_Moj_rok_n_rol.mp3', false);

INSERT INTO pattern (id, title)
VALUES
    (1, 'в. цой - группа крови'),
    (2, 'группа крови'),
    (3, 'кино - группа крови'),
    (4, 'дурной вкус - пластинки'),
    (5, 'дурной вкус пластинки'),
    (6, 'пластинки'),
    (7, 'спокойная ночь'),
    (8, 'цой - спокойная ночь'),
    (9, 'виктор цой - спокойная ночь'),
    (10, 'сплин - выхода нет'),
    (11, 'выхода нет'),
    (12, 'а мы не ангелы парень'),
    (13, 'а мы не ангелы'),
    (14, 'би2 - мы не ангелы'),
    (15, 'мой рокнролл'),
    (16, 'би-2 - мой рокнролл');



INSERT INTO music_track__pattern (track_id, pattern_id)
VALUES
    (1, 1),
    (1, 2),
    (1, 3),
    (2, 4),
    (2, 5),
    (2, 6),
    (3, 7),
    (3, 8),
    (3, 9),
    (4, 10),
    (4, 11),
    (5, 12),
    (5, 13),
    (5, 14),
    (6, 15),
    (6, 16);