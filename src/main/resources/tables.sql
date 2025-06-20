CREATE TABLE t_user
(
    id                 BIGINT PRIMARY KEY,   -- 用户ID
    username           varchar(16) NOT NULL, -- 用户名
    account            varchar(16) NOT NULL, -- 学号（账户）
    password           char(32)    NOT NULL, -- 密码
    avatar_url         VARCHAR(255),         -- 头像URL
    total_days         INT DEFAULT 0,        -- 累积天数
    consecutive_days   INT DEFAULT 0,        -- 连续打卡天数
    last_check_in_time timestamp,            -- 上次打卡时间
    check_in_history   TEXT ,                 -- 历史打卡记录（JSON格式存储）
    index(account),
    index(total_days),
    index(last_check_in_time, consecutive_days)
);


CREATE TABLE t_check_in_records
(
    id                 BIGINT PRIMARY KEY, -- 记录ID
    user_id            BIGINT NOT NULL,    -- 关联用户ID
    check_in_time      timestamp,          -- 打卡时间
    study_time         INT,                -- 学习时间（分钟）
    study_record       TEXT,               -- 学习记录
    resume_submissions INT,                -- 投递简历数
    interview_record   tinyint,            -- 是否面试
    interview_details  TEXT,               -- 面试记录
    offer              tinyint,            -- 是否收到offer
    offer_details      TEXT                -- offer记录
);