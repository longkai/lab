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

INSERT INTO members (id, uid, comefrom, nick, label, invalid, contact, description, participate_date, last_modified_date) VALUES (
  null, 86133, 0, '爱因斯坦的狗_', 'IT/侦探', 0, 'im.longkai@gmail.com', '我是一只咸鱼', '2013-08-13', null
);
INSERT INTO members (id, uid, comefrom, nick, label, invalid, contact, description, participate_date, last_modified_date) VALUES (
  null, 2, 0, '福尔摩斯', '推理/探险', 0, 'holmes@gmail.com', '大侦探！', '1900-1-1', null
);
INSERT INTO members (id, uid, comefrom, nick, label, invalid, contact, description, participate_date, last_modified_date) VALUES (
  null, 3, 0, '林丹', '羽毛球', 0, 'superdan@gmail.com', '超级丹', '1980-1-1', '2013-8-12'
);
INSERT INTO members (id, uid, comefrom, nick, label, invalid, contact, description, participate_date, last_modified_date) VALUES (
  null, 4, 0, '阿森纳', '足球', 1, 'arsenal@gmail.com', '最已阵！', '1890-1-1', null
);
INSERT INTO members (id, uid, comefrom, nick, label, invalid, contact, description, participate_date, last_modified_date) VALUES (
  null, 5, 0, 'geek', '折腾', 0, 'geek@gmail.com', 'zzzzz', '1990-1-1', null
);
INSERT INTO members (id, uid, comefrom, nick, label, invalid, contact, description, participate_date, last_modified_date) VALUES (
  null, 6, 0, 'vimmer', '编辑器', 0, 'vimmer@gmail.com', '上古神器，超级无敌牛逼', '1950-1-1', '1960-1-1'
);
