-- 首先启用pg_trgm扩展
CREATE EXTENSION IF NOT EXISTS pg_trgm;

-- 创建GIN索引加速模糊搜索
CREATE INDEX trgm_idx ON maps USING gin (title gin_trgm_ops);
