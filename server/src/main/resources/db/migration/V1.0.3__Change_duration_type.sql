ALTER TABLE music_track
    ALTER COLUMN duration TYPE numeric(10, 2);

ALTER TABLE music_track
    RENAME COLUMN track_name TO title;