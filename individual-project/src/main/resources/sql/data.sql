-- DELETE FROM UNIVERSITY;
-- DELETE FROM CAMPUS;
-- DELETE FROM TEACHER;
-- DELETE FROM APPLICATION_USER;
-- DELETE FROM COURSE;


INSERT INTO APPLICATION_USER (USERNAME,PASSWORD,USER_ROLE)
VALUES ('admin','$2a$10$8D2TNPROVT.8U8isD75noOXTLNfXeFADLb3iWFRtbfXmENIZtugv2',0), -- admin
       ('user','$2a$10$SgerV5tJJ3IITlbxg24mu.TzN.wbETy4o9jwN6UeAs2ZIIhYtpsOe',1), -- user;
       ('jan.derijke','$2a$10$MIftCH46ZaLegEudcUH0/OSJ5LkTcedduJYBF2r7t6U5vhjqO1QFu',2),
       ('wim.dekeyser','$2a$10$MIftCH46ZaLegEudcUH0/OSJ5LkTcedduJYBF2r7t6U5vhjqO1QFu',2),
       ('ann.gielis','$2a$10$MIftCH46ZaLegEudcUH0/OSJ5LkTcedduJYBF2r7t6U5vhjqO1QFu',2),
       ('hans.vochten','$2a$10$MIftCH46ZaLegEudcUH0/OSJ5LkTcedduJYBF2r7t6U5vhjqO1QFu',2),
       ('lisa.coucke','$2a$10$MIftCH46ZaLegEudcUH0/OSJ5LkTcedduJYBF2r7t6U5vhjqO1QFu',2),
       ('sam.ketels','$2a$10$MIftCH46ZaLegEudcUH0/OSJ5LkTcedduJYBF2r7t6U5vhjqO1QFu',2),

       ('jan.velaers','$2a$10$MIftCH46ZaLegEudcUH0/OSJ5LkTcedduJYBF2r7t6U5vhjqO1QFu',2),
       ('christine.vandenwyngaert','$2a$10$MIftCH46ZaLegEudcUH0/OSJ5LkTcedduJYBF2r7t6U5vhjqO1QFu',2),
       ('maarten.larmuseau','$2a$10$MIftCH46ZaLegEudcUH0/OSJ5LkTcedduJYBF2r7t6U5vhjqO1QFu',2),

       ('anita.evenepoel','$2a$10$MIftCH46ZaLegEudcUH0/OSJ5LkTcedduJYBF2r7t6U5vhjqO1QFu',2),
       ('edwig.abrath','$2a$10$MIftCH46ZaLegEudcUH0/OSJ5LkTcedduJYBF2r7t6U5vhjqO1QFu',2),

       ('lars.willemsens','$2a$10$MIftCH46ZaLegEudcUH0/OSJ5LkTcedduJYBF2r7t6U5vhjqO1QFu',2);




INSERT INTO UNIVERSITY(NAME, FOUNDING_DATE, UNIVERSITY_TYPE)
VALUES ('Karel de Grote', '1995-09-01', 'HOGESCHOOL'),
       ('Karel de Kleine', '1995-09-01', 'HOGESCHOOL'),
       ('Universiteit Antwerpen', '2003-10-01', 'UNIVERSITEIT'),
       ('Artesis Plantijn', '2012-12-12', 'HOGESCHOOL');




INSERT INTO CAMPUS(UNIVERSITY_NAME, NAME, ADDRESS, POSTAL_CODE, CITY, OPENING_TIME, CLOSING_TIME)
VALUES ('Karel de Grote','Groenplaats','Nationalestraat 5',2000,'Antwerpen','07:00','20:00'),
       ('Karel de Grote','Pothoek','Pothoekstraat 125',2060,'Borgerhout','08:00','20:00'),
       ('Karel de Grote','Zuid','Brusselstraat 45',2018,'Antwerpen','08:30','20:00'),
       ('Karel de Grote','Hoboken','Salesianenlaan 90',2660,'Hoboken','06:45','20:00'),

       ('Universiteit Antwerpen','Stadscampus','Prinsstraat 13',2000,'Antwerpen','07:00','23:00'),
       ('Universiteit Antwerpen','Mutsaard','Blindestraat 4/22',2000,'Antwerpen','08:00','20:00'),

       ('Artesis Plantijn','Royal Conservatoire','Desguinlei 25',2018,'Antwerpen','07:00','00:00'),
       ('Artesis Plantijn','Royal Academy of Fine Arts','Mutsaardstraat 31',2000,'Antwerpen','06:00','23:30');
-- 1234




INSERT INTO TEACHER(FIRST_NAME, LAST_NAME, SEX, USER_ID)
VALUES ('Jan','de Rijke','MALE',3),
       ('Wim','de Keyser','MALE',4),
       ('Ann','Gielis','FEMALE',5),
       ('Hans','Vochten','MALE',6),
       ('Lisa','Coucke','FEMALE',7),
       ('Sam','Ketels','MALE',8),

       ('Jan','Velaers','MALE',9),
       ('Christine','Van Den Wyngaert','FEMALE',10),
       ('Maarten','Larmuseau','MALE',11),

       ('Anita','Evenepoel','FEMALE',12),
       ('Edwig','Abrath','MALE',13),
       ('Lars','Willemsens','MALE',14);




INSERT INTO COURSE(TEACHER_ID, CAMPUS_ID, DISCIPLINE, ROOM, DAY_OF_WEEK, START_TIME, END_TIME)
VALUES (1,1,'Programming','G407','MONDAY','09:15','16:15'),
       (2,1,'Data&AI','G304','WEDNESDAY','08:15','12:15'),
       (3,1,'Integration','G100','FRIDAY','09:00','16:00'),
       (5,1,'The Company','G302','THURSDAY','10:15','13:15'),
       (6,1,'The Company','G302','THURSDAY','10:15','13:15'),
       (6,1,'Integration','G100','FRIDAY','09:00','16:00'),
       (12,1,'Integration','G404','MONDAY','09:00','16:00'),
-- Groenplaats
       (1,2,'Integration','PH300','MONDAY','09:15','16:15'),
       (4,2,'Integration','PH300','MONDAY','09:15','16:15'),
       (4,2,'Programming','PH209','FRIDAY','09:15','16:15'),
       (12,2,'Programming','PH300','FRIDAY','09:15','16:15'),
-- Pothoek
       (5,3,'Management','Z104','TUESDAY','10:00','15:00'),
       (6,3,'Accounting','K200','FRIDAY','11:00','17:00'),
-- Zuid
       (6,4,'Management','H110','WEDNESDAY','10:00','13:00'),
-- Hoboken
       (7,5,'Constitutional Law','A104','MONDAY','09:00','14:00'),
       (8,5,'International Criminal Law','C207','WEDNESDAY','08:00','12:00'),
-- Stadscampus
       (9,6,'Genetic Heritage','M009','TUESDAY','11:00','14:00'),
-- Mutsaard
       (11,7,'Fashion','AULA001','FRIDAY','09:30','16:30'),
-- Conservatoire
       (10,8,'General Music Theory','AUDITORIUM003','MONDAY','10:00','15:00');
-- Royal Academy



-- SELECT * FROM UNIVERSITY;
-- SELECT * FROM CAMPUS;
-- SELECT * FROM TEACHER;
-- SELECT * FROM APPLICATION_USER;
-- SELECT * FROM COURSE;