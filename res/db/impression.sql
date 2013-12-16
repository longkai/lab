-- generate by longkai@2013-07-31
DROP TABLE IF EXISTS articles;

# CREATE TABLE impression_authors (
#   id      BIGINT(20) AUTO_INCREMENT,
#   name    VARCHAR(20) DEFAULT '佚名',
#   contact VARCHAR(30),
#   about   VARCHAR(255),
#   CONSTRAINT pk_impression_aurhor PRIMARY KEY (id)
# )
#   DEFAULT CHARSET = utf8;

CREATE TABLE articles (
	id                 BIGINT(20) AUTO_INCREMENT,
	title              VARCHAR(50)                          NOT NULL,
	category           ENUM ('LITERATURE', 'NEWS', 'VOICE') NOT NULL,
	layout             ENUM ('TEXT', 'IMAGE_WITH_TEXT', 'LIST_IMAGE_WITH_TEXT', 'GALLERY', 'PAGER_TEXT', 'VIDEO', 'AUDIO'),
	content            TEXT                                 NOT NULL,
	recommended        TINYINT DEFAULT 0,
	top                TINYINT DEFAULT 0,
	blocked            TINYINT DEFAULT 0,
	added_date         DATETIME                             NOT NULL,
	last_modified_date DATETIME,
	author_name        VARCHAR(30) DEFAULT '佚名',
	author_contact     VARCHAR(30),
	author_about       VARCHAR(255),
	CONSTRAINT pk_impression_articles PRIMARY KEY (id)
)
	DEFAULT CHARSET = utf8;

# INSERT INTO impression_authors
# (id, name, contact, about)
#   VALUES (null, '龙凯', 'im.longkai@gmail.com', '我想养柯基！！！');
#
# INSERT INTO impression_authors
# (id, name, contact, about)
#   VALUES
#   (null, null, '110', '猜猜我是谁？');

-- insert articles with some predefined layouts

INSERT INTO articles
(id, title, category, layout, content, recommended, added_date, last_modified_date, author_name, author_contact, author_about)
VALUES
	(null, '简单的文字排版', 'LITERATURE', 'TEXT', '{"text":"This is a text!"}', 1, '2013-09-10', null, '龙凯',
	 'im.longkai@gmail.com', '我想养柯基！！！');

INSERT INTO articles
(id, title, category, layout, content, recommended, added_date, last_modified_date, author_name, author_contact, author_about)
VALUES
	(null, '文字加图片排版', 'NEWS', 'IMAGE_WITH_TEXT',
	 '{"text":"This is a text!","image":"http://lab.newgxu.cn/resources/images/lab/index/impression_gxu_feature.jpg"}',
	 1, '2013-09-11', '2013-09-13', '阿里', 'ali@ali.com', 'no abount!');

INSERT INTO articles
(id, title, category, layout, content, recommended, added_date, last_modified_date, author_name, author_contact, author_about)
VALUES
	(null, '文字加图片列表排版', 'VOICE', 'LIST_IMAGE_WITH_TEXT',
	 '[{"text":"This is the 1st text!","image":"http://lab.newgxu.cn/resources/images/lab/index/impression_gxu_feature.jpg"},{"text":"This is the 2nd text!","image":"http://lab.newgxu.cn/resources/images/lab/index/impression_gxu_feature.jpg"}]',
	 1, '2013-09-12', null, null, null, null);

INSERT INTO articles
(id, title, category, layout, content, recommended, added_date, last_modified_date, author_name, author_contact, author_about)
VALUES
	(null, '相册排版', 'LITERATURE', 'GALLERY',
	 '[{"title":"title 1","text":"This is the 1st text!","image":"http://lab.newgxu.cn/resources/images/lab/index/impression_gxu_feature.jpg"},{"title":"title2","text":"This is the 2nd text!","image":"http://lab.newgxu.cn/resources/images/lab/index/impression_gxu_feature.jpg"}]',
	 1, '2013-09-12', null, '123', '123', '123');

INSERT INTO articles
(id, title, category, layout, content, recommended, added_date, last_modified_date, author_name, author_contact, author_about)
VALUES
	(null, '滑动分页文字排版', 'LITERATURE', 'PAGER_TEXT',
	 '[{"text":"This is the 1st text!","title":"this is the title"},{"text":"This is the 2nd text!","title":"title 2"}]',
	 1, '2013-09-13', null, 'abc', 'cba', 'nba');
