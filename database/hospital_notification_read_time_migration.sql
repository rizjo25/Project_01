USE road_accident_db;

ALTER TABLE notifications
    ADD COLUMN read_at TIMESTAMP NULL DEFAULT NULL;
