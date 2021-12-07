--Script to preload database with drone records

INSERT INTO drones (id,serial_number,drone_model,battery_capacity,weight_lim_in_gr,drone_state,drone_uid,created_at) VALUES (10,'12345','LIGHTWEIGHT',24,200,'IDLE' ,'06364cbc-9468-4bfe-a917-1ab610bd49f1',CURRENT_TIMESTAMP);
INSERT INTO drones (id,serial_number,drone_model,battery_capacity,weight_lim_in_gr,drone_state,drone_uid,created_at) VALUES (20,'12346','LIGHTWEIGHT',75,200,'LOADING','06364cbc-9468-4bfe-a917-1ab641bd49f1',CURRENT_TIMESTAMP);
INSERT INTO drones (id,serial_number,drone_model,battery_capacity,weight_lim_in_gr,drone_state,drone_uid,created_at) VALUES (30,'123456','HEAVYWEIGHT',75,500,'IDLE','06364cbc-9468-4bfe-a917-1ab650bd49f1',CURRENT_TIMESTAMP);
INSERT INTO drones (id,serial_number,drone_model,battery_capacity,weight_lim_in_gr,drone_state,drone_uid,created_at) VALUES (40,'12325','LIGHTWEIGHT',90,200,'IDLE', '06764cbc-9468-4bfe-a917-1ab640bd49f1',CURRENT_TIMESTAMP);
INSERT INTO drones (id,serial_number,drone_model,battery_capacity,weight_lim_in_gr,drone_state,drone_uid,created_at) VALUES (50,'123457','LIGHTWEIGHT',100,200,'IDLE', '06364cbc-9468-4bfe-a918-1ab640bd49f1',CURRENT_TIMESTAMP);
INSERT INTO drones (id,serial_number,drone_model,battery_capacity,weight_lim_in_gr,drone_state,drone_uid,created_at) VALUES (60,'145','LIGHTWEIGHT',95,100,'IDLE', '06364cbb-9468-4bfe-a917-1ab640bd49f1',CURRENT_TIMESTAMP);
INSERT INTO drones (id,serial_number,drone_model,battery_capacity,weight_lim_in_gr,drone_state,drone_uid,created_at) VALUES (70,'1253','LIGHTWEIGHT',85,200,'IDLE', '06364cbc-9478-4bfe-a917-1ab640bd49f1',CURRENT_TIMESTAMP);
INSERT INTO drones (id,serial_number,drone_model,battery_capacity,weight_lim_in_gr,drone_state,drone_uid,created_at) VALUES (80,'1233235','LIGHTWEIGHT',35,100,'IDLE', '06364cbc-9468-4bfe-a917-1ab640bd59f1' ,CURRENT_TIMESTAMP);
INSERT INTO drones (id,serial_number,drone_model,battery_capacity,weight_lim_in_gr,drone_state,drone_uid,created_at) VALUES (90,'12345127','LIGHTWEIGHT',75,100,'IDLE', '06364cbc-9468-4bfe-a917-1ab640bd49f2',CURRENT_TIMESTAMP);
INSERT INTO drones (id,serial_number,drone_model,battery_capacity,weight_lim_in_gr,drone_state,drone_uid,created_at) VALUES (100,'1345','LIGHTWEIGHT',85,100,'IDLE', '06364cbc-9468-4bfe-a917-1ab640bd49f1',CURRENT_TIMESTAMP);


--Script to add new medication record
INSERT INTO medications (ID,MEDICATION_CODE,NAME_OF_MEDICATION,MEDICATION_UID,WEIGHT_OF_MEDICATION_IN_GR,DRONE_ID,IMG_OF_MEDICATION,created_at,med_img_uid) VALUES (10,	'CODE2',	'Panadol',	'ae29110e-a6e0-435d-8733-fb3a1bc3d5e5',	40,	20,	'ffdeab',CURRENT_TIMESTAMP,'07364cbc-9468-abfe-a917-1ab640bd49f1')
