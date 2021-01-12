INSERT INTO role(id, name) VALUES (1, 'ROLE_DOCTOR');
INSERT INTO role(id, name) VALUES (2, 'ROLE_PATIENT');
INSERT INTO role(id, name) VALUES (3, 'ROLE_CAREGIVER');
INSERT INTO address(id, city, number, street) VALUES (0, 'Fagaras', 23, 'Plopilor');
INSERT INTO user(id, birth_date, first_name, gender, is_active, last_name, password, user_name, address_id, email, role_id)
            VALUES(0, '1997-11-14', 'Tanase', 0, 1, 'Andrei', '$2a$10$WxxG1H2O96GAsgYRgm.AWeJjmTyMbl0mPKpfHnXCelz4X5jAsnnmK', 'doctor', 0, 'andreitanase@gmail.com', 1);
INSERT INTO doctor(id) VALUES (0);
INSERT INTO user(id, birth_date, first_name, gender, is_active, last_name, password, user_name, address_id, email, role_id)
            VALUES(1, '1997-11-14', 'Tanase', 0, 1, 'Andrei', '$2a$10$WxxG1H2O96GAsgYRgm.AWeJjmTyMbl0mPKpfHnXCelz4X5jAsnnmK', 'patient', 0, 'andreitanase@gmail.com', 1);
INSERT INTO patient(id, doctor_id) VALUES (1, 0);