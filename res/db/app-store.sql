-- created by longkai@2013-08-13

CREATE TABLE members (
	id                 BIGINT(20) AUTO_INCREMENT,
	uid                BIGINT(20)           NOT NULL,
	comefrom           TINYINT(1) DEFAULT 0 NOT NULL,
	nick               VARCHAR(15)          NOT NULL,
	label              VARCHAR(30),
	invalid            TINYINT(1) DEFAULT 0 NOT NULL,
	contact            VARCHAR(30)          NOT NULL,
	description        VARCHAR(255)         NOT NULL,
	participate_date   DATETIME             NOT NULL,
	last_modified_date DATETIME,
	CONSTRAINT pk_members PRIMARY KEY (id)
)
	DEFAULT CHARSET = utf8;

INSERT INTO members (id, uid, comefrom, nick, label, invalid, contact, description, participate_date, last_modified_date)
VALUES (
	null, 86133, 0, '爱因斯坦的狗_', 'IT/侦探', 0, 'im.longkai@gmail.com', '我是一只咸鱼', '2013-08-13', null
);
INSERT INTO members (id, uid, comefrom, nick, label, invalid, contact, description, participate_date, last_modified_date)
VALUES (
	null, 2, 0, '福尔摩斯', '推理/探险', 0, 'holmes@gmail.com', '大侦探！', '1900-1-1', null
);
INSERT INTO members (id, uid, comefrom, nick, label, invalid, contact, description, participate_date, last_modified_date)
VALUES (
	null, 3, 0, '林丹', '羽毛球', 0, 'superdan@gmail.com', '超级丹', '1980-1-1', '2013-8-12'
);
INSERT INTO members (id, uid, comefrom, nick, label, invalid, contact, description, participate_date, last_modified_date)
VALUES (
	null, 4, 0, '阿森纳', '足球', 1, 'arsenal@gmail.com', '最已阵！', '1890-1-1', null
);
INSERT INTO members (id, uid, comefrom, nick, label, invalid, contact, description, participate_date, last_modified_date)
VALUES (
	null, 5, 0, 'geek', '折腾', 0, 'geek@gmail.com', 'zzzzz', '1990-1-1', null
);
INSERT INTO members (id, uid, comefrom, nick, label, invalid, contact, description, participate_date, last_modified_date)
VALUES (
	null, 6, 0, 'vimmer', '编辑器', 0, 'vimmer@gmail.com', '上古神器，超级无敌牛逼', '1950-1-1', '1960-1-1'
);

CREATE TABLE apps (
	id                  BIGINT(20) AUTO_INCREMENT                                        NOT NULL,
	name                VARCHAR(30)                                                      NOT NULL,
	uid                 BIGINT                                                           NOT NULL,
	category            ENUM ('DEFAULT', 'GAME', 'INDIVIDUAL', 'EMTERTAINMENT', 'STUDY') NOT NULL,
	platform            ENUM ('APPLE', 'ANDROID', 'WINDOWS', 'FIREFOX')                  NOT NULL,
	os_requirement      VARCHAR(30)                                                      NOT NULL,
	description         VARCHAR(255)                                                     NOT NULL,
	total_install_count BIGINT(20) DEFAULT 0                                             NOT NULL,
	icon                VARCHAR(255),
	outside_link        VARCHAR(255),
	rate                BIGINT(20) DEFAULT 0                                             NOT NULL,
	rate_count          INT DEFAULT 0                                                    NOT NULL,
	extra               VARCHAR(255),
	created_date        DATETIME                                                         NOT NULL,
	CONSTRAINT pk_apps PRIMARY KEY (id),
	CONSTRAINT fk_member_apps FOREIGN KEY (uid) REFERENCES members (id)
)
	DEFAULT CHARSET = utf8;

CREATE TABLE app_versions (
	id            BIGINT(20) AUTO_INCREMENT,
	app_id        BIGINT(20)           NOT NULL,
	version       VARCHAR(30)          NOT NULL,
	update_date   DATETIME             NOT NULL,
	install_count BIGINT(20) DEFAULT 0 NOT NULL,
	size          BIGINT(20)           NOT NULL,
	update_info   VARCHAR(255)         NOT NULL,
	uri           VARCHAR(255) DEFAULT NULL,
	extra         VARCHAR(255) DEFAULT NULL,
	CONSTRAINT pk_app_versions PRIMARY KEY (id),
	CONSTRAINT fk_app_app_versions FOREIGN KEY (app_id) REFERENCES apps (id)
)
	DEFAULT CHARSET = utf8;

CREATE TABLE app_comments (
	id           BIGINT(20) AUTO_INCREMENT,
	uid          BIGINT(20)   NOT NULL,
	nick         VARCHAR(20)  NOT NULL,
	app_id       BIGINT(20)   NOT NULL,
	rate         TINYINT(1)   NOT NULL,
	useful_count TINYINT(2) DEFAULT 0,
	comment      VARCHAR(255) NOT NULL,
	rate_date    DATETIME     NOT NULL,
	CONSTRAINT pk_app_comments PRIMARY KEY (id),
	CONSTRAINT fk_app_app_comments FOREIGN KEY (app_id) REFERENCES apps (id)
)
	DEFAULT CHARSET = utf8;

INSERT INTO apps (id, name, uid, category, platform, os_requirement, description, total_install_count, icon, outside_link, rate, rate_count, extra, created_date)
VALUES
	(null, 'android app1', 1, 'GAME', 'ANDROID', 'android 2.1以上', 'app1...', 10, null, null, 17, 4,
	 null, '2013-08-1');
INSERT INTO apps (id, name, uid, category, platform, os_requirement, description, total_install_count, icon, outside_link, rate, rate_count, extra, created_date)
VALUES
	(null, 'apple app2', 2, 'INDIVIDUAL', 'APPLE', 'ios 5以上', 'app2...', 110, null, null, 0, 0, null,
	 '2013-08-2');
INSERT INTO apps (id, name, uid, category, platform, os_requirement, description, total_install_count, icon, outside_link, rate, rate_count, extra, created_date)
VALUES
	(null, 'windows app3', 3, 'EMTERTAINMENT', 'WINDOWS', 'windows8以上', 'app3...', 3, null, null, 0,
	 0, null, '2013-08-8');
INSERT INTO apps (id, name, uid, category, platform, os_requirement, description, total_install_count, icon, outside_link, rate, rate_count, extra, created_date)
VALUES
	(null, 'android app3', 1, 'STUDY', 'ANDROID', 'android 4.0以上', 'app3...', 76, null, null, 0, 0,
	 null, '2013-08-10');

INSERT INTO app_versions (id, app_id, version, update_date, install_count, size, update_info, uri, extra)
VALUES (null, 1, '1.1.0', '2013-08-1', 3, 1000000, 'new app.', '/', 'no extra');
INSERT INTO app_versions (id, app_id, version, update_date, install_count, size, update_info, uri)
VALUES (null, 1, '1.2.0', '2013-08-3', 4, 1000400, 'new update!bug fix.', '/');
INSERT INTO app_versions (id, app_id, version, update_date, install_count, size, update_info, uri)
VALUES (null, 1, '1.3.0', '2013-08-5', 3, 1006000, 'new update!bug fix.', '/');
INSERT INTO app_versions (id, app_id, version, update_date, install_count, size, update_info, uri)
VALUES (null, 2, '1.0', '2013-08-2', 110, 1006000, 'new app.', '/');
INSERT INTO app_versions (id, app_id, version, update_date, install_count, size, update_info, uri)
VALUES (null, 3, '1.0', '2013-08-8', 3, 16000, 'new app.', '/');
INSERT INTO app_versions (id, app_id, version, update_date, install_count, size, update_info, uri)
VALUES (null, 4, '1.0', '2013-08-10', 76, 100432, 'new app.', '/');

INSERT INTO app_comments (id, uid, nick, app_id, rate, useful_count, comment, rate_date) VALUES (
	null, 1, 'user1', 1, 5, 1, 'very good!', '2013-08-2'
);
INSERT INTO app_comments (id, uid, nick, app_id, rate, useful_count, comment, rate_date) VALUES (
	null, 2, 'user2', 1, 4, 0, 'nice', '2013-08-2'
);
INSERT INTO app_comments (id, uid, nick, app_id, rate, useful_count, comment, rate_date) VALUES (
	null, 3, 'user3', 1, 3, 1, 'soso', '2013-08-3'
);
INSERT INTO app_comments (id, uid, nick, app_id, rate, useful_count, comment, rate_date) VALUES (
	null, 4, 'user4', 1, 5, 1, 'very good!', '2013-08-8'
);