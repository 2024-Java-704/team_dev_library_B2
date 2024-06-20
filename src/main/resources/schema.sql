-- 各種テーブル削除
DROP TABLE IF EXISTS item_titles;
DROP TABLE IF EXISTS items;
DROP TABLE IF EXISTS categories;
DROP TABLE IF EXISTS sub_categories;
DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS reservations;
DROP TABLE IF EXISTS rentals;
DROP TABLE IF EXISTS calendars;

-- 資料タイトルテーブル
CREATE TABLE item_titles
(
id SERIAL PRIMARY KEY,
name VARCHAR(50),
author VARCHAR(50),
image TEXT,
publisher VARCHAR(50),
publication_date DATE,
summary VARCHAR(300),
category_id INTEGER,
sub_category_id INTEGER,
rental_number INTEGER
);
-- 資料テーブル
CREATE TABLE items
(
id SERIAL PRIMARY KEY,
item_title_id INTEGER,
status INTEGER,
arrival_date DATE,
memo VARCHAR(300)
);
-- カテゴリーテーブル
CREATE TABLE categories
(
id SERIAL PRIMARY KEY,
name VARCHAR(20) UNIQUE
);
-- サブカテゴリーテーブル
CREATE TABLE sub_categories
(
id SERIAL PRIMARY KEY,
category_id INTEGER,
name VARCHAR(20)
);
-- 会員テーブル
CREATE TABLE users
(
id SERIAL PRIMARY KEY,
name VARCHAR(100),
address VARCHAR(150),
tel VARCHAR(11),
email VARCHAR(254) UNIQUE,
birthday DATE,
password VARCHAR(20),
join_date DATE,
status INTEGER
);
-- 予約情報テーブル
CREATE TABLE reservations
(
id SERIAL PRIMARY KEY,
item_title_id INTEGER,
item_id INTEGER,
user_id INTEGER,
ordered_on DATE,
status INTEGER
);
-- 貸出情報テーブル
CREATE TABLE rentals
(
id SERIAL PRIMARY KEY,
item_id INTEGER,
user_id INTEGER,
rental_date DATE,
return_date DATE,
closing_date DATE,
status INTEGER
);
-- 休館カレンダー
CREATE TABLE calendars
(
id SERIAL PRIMARY KEY,
closed_date DATE,
date_detail VARCHAR(200)
);