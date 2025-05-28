-- 首先启用pg_trgm扩展
CREATE EXTENSION IF NOT EXISTS pg_trgm;

-- 创建GIN索引加速模糊搜索
CREATE INDEX title_index ON maps USING gin (title_lower gin_trgm_ops);
