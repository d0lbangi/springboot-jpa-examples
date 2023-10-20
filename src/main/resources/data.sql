INSERT INTO USER (ID, EMAIL, PASSWORD, PHONE, REG_DATE, UPDATE_DATE, USER_NAME, STATUS, LOCK_YN)
VALUES (1, 'test@naver.com', '$2a$10$tinOeKIqbIoFUB3FPvYhp.3hiuZtLPjPgL2CJ4/KFgQot.6Iz/lDS', '010-1111-2222', '2021-02-01 00:49:43.000000', null, '박규태', 1, 0)
     , (2, 'test1@naver.com', '2222', '010-3333-2222', '2023-10-20 00:50:43.000000', null, '정혜경', 1, 0)
     , (3, 'test2@naver.com', '3333', '010-5555-2222', '2023-10-20 01:49:43.000000', null, '박하은', 1, 0)
     , (4, 'test3@naver.com', '4444', '010-7777-2222', '2021-02-01 02:57:43.000000', null, '박하영', 2, 0);

INSERT INTO NOTICE (ID, CONTENTS, DELETED_DATE, DELETED, HITS, LIKES, REG_DATE, TITLE, UPDATE_DATE, USER_ID)
VALUES (1, '내용1', null, false, 0, 0,'2021-02-01 01:12:20.000000', '제목1', null, 1)
     , (2, '내용2', null, false, 0, 0,'2021-02-01 01:12:37.000000', '제목2', null, 1)
     , (3, '내용3', null, false, 0, 0,'2021-02-01 01:13:07.000000', '제목3', null, 2)
     , (4, '내용4', null, false, 0, 0,'2021-02-01 01:13:10.000000', '제목4', null, 2)
     , (5, '내용5', null, false, 0, 0,'2021-02-01 23:31:23.000000', '제목5', null, 2)
     , (6, '내용6', null, false, 0, 0,'2021-02-01 23:31:26.000000', '제목6', null, 1)
     , (7, '내용7', null, false, 0, 0,'2021-02-01 23:31:32.000000', '제목7', null, 3)
     , (8, '내용8', null, false, 0, 0,'2021-02-01 23:31:36.000000', '제목8', null, 3)
     , (9, '내용9', null, false, 0, 0,'2021-02-01 23:31:41.000000', '제목9', null, 1)
     , (10, '내용10', null, false, 0, 0,'2021-02-01 23:31:45.000000', '제목10', null, 1);

INSERT INTO NOTICE_LIKE (ID, NOTICE_ID, USER_ID)
VALUES (1, 3, 1)
     , (2, 4, 1);