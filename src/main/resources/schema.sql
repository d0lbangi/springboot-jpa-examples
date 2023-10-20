-- auto-generated definition
create table USER
(
    ID          BIGINT auto_increment primary key,
    EMAIL       VARCHAR(255),
    USER_NAME   VARCHAR(255),
    PASSWORD    VARCHAR(255),
    PHONE       VARCHAR(255),
    REG_DATE    TIMESTAMP,
    UPDATE_DATE TIMESTAMP,
    STATUS      INTEGER,
    LOCK_YN     BOOLEAN
);

-- auto-generated definition
create table NOTICE
(
    ID       BIGINT auto_increment primary key,
    TITLE    VARCHAR(255),
    CONTENTS VARCHAR(255),
    HITS     INTEGER,
    LIKES    INTEGER,
    REG_DATE    TIMESTAMP,
    UPDATE_DATE TIMESTAMP,
    DELETED_DATE TIMESTAMP,
    DELETED     BOOLEAN,
    USER_ID     BIGINT,
    constraint FK_NOTICE_USER_ID foreign key (USER_ID) references USER(ID)
);

-- auto-generated definition
create table NOTICE_LIKE
(
  ID BIGINT auto_increment primary key,
  NOTICE_ID BIGint,
  USER_ID BIGINT not null,
  constraint FK_NOTICE_LIKE_NOTICE_ID foreign key (NOTICE_ID) references NOTICE (ID),
  constraint FK_NOTICE_LIKE_USER_ID foreign key (USER_ID) references USER (ID)
);

create table USER_LOGIN_HISTORY
(
    ID          BIGINT auto_increment primary key,
    USER_ID     BIGINT,
    EMAIL       VARCHAR(255),
    USER_NAME   VARCHAR(255),
    LOGIN_DATE  TIMESTAMP,
    IP_ADDR     VARCHAR(255)
);

create table BOARD_TYPE
(
    ID          BIGINT auto_increment primary key,
    BOARD_NAME  VARCHAR(255),
    REG_DATE    TIMESTAMP,
    UPDATE_DATE TIMESTAMP
);

