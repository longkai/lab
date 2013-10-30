-- generate by longkai@2013-07-31
DROP TABLE IF EXISTS notices;
DROP TABLE IF EXISTS auth_users;

CREATE TABLE auth_users (
  id                 BIGINT(20) AUTO_INCREMENT,
  about              VARCHAR(255) DEFAULT NULL,
  account            VARCHAR(32) NOT NULL,
  authed_name        VARCHAR(32) NOT NULL,
  blocked            TINYINT(1)  NOT NULL DEFAULT 0,
  contact            VARCHAR(50) NOT NULL,
  join_date          DATETIME    NOT NULL,
  last_login_ip      VARCHAR(20) DEFAULT NULL,
  last_login_date    DATETIME DEFAULT NULL,
  last_modified_date DATETIME DEFAULT NULL,
  org                VARCHAR(30) NOT NULL,
  password           CHAR(32)    NOT NULL,
  is_admin           TINYINT(1) NOT NULL DEFAULT 0,
  CONSTRAINT PK_INFO_USERS PRIMARY KEY (id)
)
  DEFAULT CHARSET = utf8;

CREATE TABLE notices (
  id                 BIGINT(20) AUTO_INCREMENT,
  add_date           DATETIME       NOT NULL,
  blocked            TINYINT(1) DEFAULT 0,
  click_times        BIGINT(20) DEFAULT 0,
  content            VARCHAR(20000) NOT NULL,
  doc_name           VARCHAR(255) DEFAULT NULL,
  doc_url            VARCHAR(255) DEFAULT NULL,
  last_modified_date DATETIME DEFAULT NULL,
  title              VARCHAR(255)   NOT NULL,
  uid                BIGINT(20)     NOT NULL,
  CONSTRAINT PK_INFO_NOTICES PRIMARY KEY (id),
  CONSTRAINT FK_INFO_NOTICES FOREIGN KEY (uid) REFERENCES auth_users (id)
)
  DEFAULT CHARSET = utf8;
