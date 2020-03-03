insert into doctor(id, name, specialization, address, education, manager_id) 
values
(1205,'Ali','bones','Alex','Alex university',NULL),
(1201,'ahmed ','teeth','mansura','mansura university',1205),
(1202,'amr','teeth','cairo','cairo university',1201),
(1204,'Ibrahim','bones','Bani Suif','BaniSuif University',1205),
(1203,'doaa','bones','Alex','Alex university',1204);
insert into patient(patient_id, birth_date, gender, name)
values(1102,NULL,'male','fahmy'),(1103,'1992-12-08','male','Alaa'),(1104,'1994-12-08','male','Emad'),(1105,NULL,'male','mina');
insert into appointment(appointment_id,date_time,patient_id, doctor_id, brief_complain)
VALUES (1001,'2020-03-02 00:00:00',1102,1202,'tooth removal'),(1002,'2020-05-12 00:00:00',1104,1202,'checkup'),
(1003,'2020-04-02 00:00:00',1105,1202,'teeth cleaning'),(1004,'2020-04-02 00:00:00',1103,1203,'checking broken leg');