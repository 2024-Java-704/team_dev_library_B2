-- 資料タイトルテーブルにデータを挿入
INSERT INTO item_titles(name, author, image, publisher, publication_date, summary, category_id, sub_category_id, rental_number)
VALUES
('7つの習慣', 'スティーブ・R・コビー', '', 'キングベアー出版', '1996-12-25', '', 2, 5, 100),
('よいこの君主論', '架神恭介', '', '筑摩書房', '2009-05-11', '', 4, 10, 40),
('人間失格', '太宰治', '', '新潮社', '2006-01-01', '', 10, 28, 120),
('読書の力', '若松英輔', '', '亜紀書房', '2020-11-25', '', 1, 2, 25),
('ギネス世界記録2023', 'クレイグ・クレンディ', '', '角川アスキー総合研究所', '2020-11-18', '', 1, 3, 45),
('心理学概論', '鈴木直人', '', 'ナカニシヤ出版', '2014-04-20', '', 2, 4, 48),
('世界宗教事典', '村上重良', '', '講談社', '1987-12-01', '', 2, 6, 28),
('九州考古学の現在（いま）', '西谷正', '', '海鳥社', '2020-12-25', '', 3, 7, 24),
('ピアノで楽しむスプラトゥーン3', '任天堂株式会社', '', 'ヤマハミュージック ', '2023-09-27', '', 8, 23, 55),
('原生生物学事典', '矢﨑裕規', '', '朝倉書店',  '2023-05-01', '', 5, 15, 34),
('海の見える街', '畑野智美', '', '講談社', '2012-12-06', '', 10, 28, 125),
('新わかりやすいJava 入門編 第3版', '川場隆', '', '秀和システム', '2022-12-13', '', 1, 1, 76),
('水道事業経営の基本', '石井晴夫', '', '白桃書房', '2015-12-04', '', 6, 16, 45),
('薬屋のひとりごと', '日向夏', '', 'イマジカインフォス', '2024-05-01', '', 10, 28, 65),
('豆類の百科事典', '国分牧衛', '', '朝倉書店', '2024-05-01', '', 7, 20, 20);

-- 資料テーブルにデータを挿入
INSERT INTO items(item_title_id, status, arrival_date, memo)
VALUES
(1, 1, '1997-02-25', ''),
(1, 0, '1997-02-25', ''),
(2, 0, '2009-07-11', ''),
(3, 0, '2006-03-01', ''),
(3, 0, '2006-03-01', ''),
(4, 0, '2021-01-25', ''),
(5, 0, '2021-01-25', ''),
(6, 0, '2014-06-20', ''),
(7, 0, '1997-02-25', ''),
(8, 0, '2021-01-25', ''),
(9, 1, '2023-11-27', ''),
(10, 0, '2023-11-27', ''),
(11, 0, '2013-02-06', ''),
(11, 0, '2013-02-06', ''),
(12, 0, '2023-02-13', ''),
(12, 0, '2023-02-13', ''),
(13, 0, '2016-02-04', ''),
(14, 1, '2024-04-01', ''),
(14, 0, '2024-04-01', ''),
(15, 0, '2024-05-15', ''),
(16, 1, '2024-04-01', '');

-- カテゴリーテーブルにデータを挿入
INSERT INTO categories(name)
VALUES
('総記'),
('哲学'),
('歴史'),
('社会科学'),
('自然科学'),
('技術'),
('産業'),
('芸術'),
('言語'),
('文学');

-- サブカテゴリーテーブルにデータを挿入
INSERT INTO sub_categories(category_id, name)
VALUES
(1, '総記'),
(1, '図書館'),
(1, '百科事典'),
(2, '心理学'),
(2, '倫理学'),
(2, '宗教学'),
(3, '日本史'),
(3, '東洋史'),
(3, '西洋史'),
(4, '政治'),
(4, '法律'),
(4, '経済'),
(5, '物理学'),
(5, '化学'),
(5, '生物科学'),
(6, '土木工学'),
(6, '機械工学'),
(6, '海洋工学'),
(7, '産業'),
(7, '農業'),
(7, '通信事業'),
(8, '美術'),
(8, '音楽'),
(8, 'スポーツ'),
(9, '日本語'),
(9, '英語'),
(9, 'フランス語'),
(10, '日本文学'),
(10, '東洋文学'),
(10, '西洋文学');

-- 会員テーブルにデータを挿入
INSERT INTO users(name, address, tel, email, birthday, password, join_date)
VALUES
('田中太郎', '東京都新宿区〇〇', '08012345678', 'tanaka@aaa.com', '1995-04-20', 'tanaka123', '2015-06-30'),
('鈴木一郎', '東京都新宿区△△', '09087654321', 'suzuki@bbb.com', '2000-09-30', 'suzuki456', '2010-12-15'),
('山田花子', '東京都新宿区□□', '0300002222', 'yamada@ccc.com', '1998-06-10', 'yamada789', '2013-08-20');

-- 予約情報テーブル
INSERT INTO reservations(item_title_id, item_id, user_id, ordered_on, status)
VALUES
(3, 4, 2, '2020-04-01', 3),
(12, 15, 1, '2023-02-20', 3),
(3, 4, 1, '2023-04-01', 3),
(9, 11, 2, '2024-06-02', 0),
(16, 21, 3, '2024-06-03', 0);

-- 貸出情報テーブル
INSERT INTO rentals(item_id, user_id, rental_date, return_date, closing_date, status)
VALUES
(1, 1, '2018-04-13', '2018-04-25', '2018-04-28', 1),
(13, 2, '2018-05-01', '2018-05-15', '2018-05-16', 1),
(4, 2, '2020-04-10', '2020-04-20', '2020-04-25', 1),
(15, 1, '2023-03-01', '2023-03-10', '2023-03-11', 1),
(6, 3, '2023-07-01', '2023-07-17', '2023-07-16', 1),
(18, 2, '2024-05-01', '2024-05-11', '2024-05-11', 1),
(1, 2, '2024-05-29', null, '2024-06-08', 0),
(18, 3, '2024-05-29', null, '2024-06-13', 0),
(11, 3, '2024-06-01', null, '2024-06-11', 0),
(21, 1, '2024-06-01', null, '2024-06-16',0);

-- 休館カレンダーテーブル
INSERT INTO calendars(closed_date, date_detail)
VALUES
('2024-06-20', '');