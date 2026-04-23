
INSERT INTO collectivities (id, number, name, location, specialty, creation_date, federation_approval, annual_membership_dues) VALUES
                                                                                                                                   ('col-1', '1', 'Mpanorina', 'Ambatondrazaka', 'Riziculture', '2023-01-01', true, 100000),
                                                                                                                                   ('col-2', '2', 'Dobo voalahany', 'Ambatondrazaka', 'Pisciculture', '2023-06-15', true, 100000),
                                                                                                                                   ('col-3', '3', 'Tantely mamy', 'Brickaville', 'Apiculture', '2024-01-10', true, 50000);


INSERT INTO members (id, last_name, first_name, birth_date, gender, address, profession, phone_number, email, occupation, collectivity_id, adhesion_date, registration_fee_paid, membership_dues_paid) VALUES
                                                                                                                                                                                                           ('C1-M1', 'Rakoto', 'Jean', '1980-02-01', 'MALE', 'Lot II V M Ambato', 'Riziculteur', '0341234567', 'member.1@fed-agri.mg', 'PRESIDENT', 'col-1', '2025-01-01', true, true),
                                                                                                                                                                                                           ('C1-M2', 'Rabe', 'Marie', '1982-03-05', 'FEMALE', 'Lot II F Ambato', 'Agriculteur', '0321234567', 'member.2@fed-agri.mg', 'VICE_PRESIDENT', 'col-1', '2025-01-01', true, true),
                                                                                                                                                                                                           ('C1-M3', 'Rasoa', 'Paul', '1992-03-10', 'MALE', 'Lot II J Ambato', 'Collecteur', '0331234567', 'member.3@fed-agri.mg', 'SECRETARY', 'col-1', '2025-01-01', true, true),
                                                                                                                                                                                                           ('C1-M4', 'Razafy', 'Jeanne', '1988-05-22', 'FEMALE', 'Lot A K 50 Ambato', 'Distributeur', '0381234567', 'member.4@fed-agri.mg', 'TREASURER', 'col-1', '2025-01-01', true, true),
                                                                                                                                                                                                           ('C1-M5', 'Andria', 'Pierre', '1999-08-21', 'MALE', 'Lot UV 80 Ambato', 'Riziculteur', '0373434567', 'member.5@fed-agri.mg', 'SENIOR', 'col-1', '2025-01-01', true, true),
                                                                                                                                                                                                           ('C1-M6', 'Rakotomalala', 'Sophie', '1998-08-22', 'FEMALE', 'Lot UV 6 Ambato', 'Riziculteur', '0372234567', 'member.6@fed-agri.mg', 'SENIOR', 'col-1', '2025-01-01', true, true),
                                                                                                                                                                                                           ('C1-M7', 'Rakotonirina', 'Michel', '1998-01-31', 'MALE', 'Lot UV 7 Ambato', 'Riziculteur', '0374234567', 'member.7@fed-agri.mg', 'SENIOR', 'col-1', '2025-01-01', true, true),
                                                                                                                                                                                                           ('C1-M8', 'Randria', 'Claire', '1975-08-20', 'FEMALE', 'Lot UV 8 Ambato', 'Riziculteur', '0370234567', 'member.8@fed-agri.mg', 'SENIOR', 'col-1', '2025-01-01', true, true);


INSERT INTO members (id, last_name, first_name, birth_date, gender, address, profession, phone_number, email, occupation, collectivity_id, adhesion_date, registration_fee_paid, membership_dues_paid) VALUES
                                                                                                                                                                                                           ('C2-M1', 'Rakoto', 'Jean', '1980-02-01', 'MALE', 'Lot II V M Ambato', 'Riziculteur', '0341234567', 'member.1@fed-agri.mg', 'SENIOR', 'col-2', '2025-01-01', true, true),
                                                                                                                                                                                                           ('C2-M2', 'Rabe', 'Marie', '1982-03-05', 'FEMALE', 'Lot II F Ambato', 'Agriculteur', '0321234567', 'member.2@fed-agri.mg', 'SENIOR', 'col-2', '2025-01-01', true, true),
                                                                                                                                                                                                           ('C2-M3', 'Rasoa', 'Paul', '1992-03-10', 'MALE', 'Lot II J Ambato', 'Collecteur', '0331234567', 'member.3@fed-agri.mg', 'SENIOR', 'col-2', '2025-01-01', true, true),
                                                                                                                                                                                                           ('C2-M4', 'Razafy', 'Jeanne', '1988-05-22', 'FEMALE', 'Lot A K 50 Ambato', 'Distributeur', '0381234567', 'member.4@fed-agri.mg', 'SENIOR', 'col-2', '2025-01-01', true, true),
                                                                                                                                                                                                           ('C2-M5', 'Andria', 'Pierre', '1999-08-21', 'MALE', 'Lot UV 80 Ambato', 'Riziculteur', '0373434567', 'member.5@fed-agri.mg', 'PRESIDENT', 'col-2', '2025-01-01', true, true),
                                                                                                                                                                                                           ('C2-M6', 'Rakotomalala', 'Sophie', '1998-08-22', 'FEMALE', 'Lot UV 6 Ambato', 'Riziculteur', '0372234567', 'member.6@fed-agri.mg', 'VICE_PRESIDENT', 'col-2', '2025-01-01', true, true),
                                                                                                                                                                                                           ('C2-M7', 'Rakotonirina', 'Michel', '1998-01-31', 'MALE', 'Lot UV 7 Ambato', 'Riziculteur', '0374234567', 'member.7@fed-agri.mg', 'SECRETARY', 'col-2', '2025-01-01', true, true),
                                                                                                                                                                                                           ('C2-M8', 'Randria', 'Claire', '1975-08-20', 'FEMALE', 'Lot UV 8 Ambato', 'Riziculteur', '0370234567', 'member.8@fed-agri.mg', 'TREASURER', 'col-2', '2025-01-01', true, true);


INSERT INTO members (id, last_name, first_name, birth_date, gender, address, profession, phone_number, email, occupation, collectivity_id, adhesion_date, registration_fee_paid, membership_dues_paid) VALUES
                                                                                                                                                                                                           ('C3-M1', 'Rakoto', 'Jean', '1988-01-02', 'MALE', 'Lot 33 J Antsirabe', 'Apiculteur', '0340345671', 'member.9@fed-agri.mg', 'PRESIDENT', 'col-3', '2025-01-01', true, true),
                                                                                                                                                                                                           ('C3-M2', 'Rabe', 'Marie', '1982-03-05', 'MALE', 'Lot 2 J Antsirabe', 'Agriculteur', '0338634567', 'member.10@fed-agri.mg', 'VICE_PRESIDENT', 'col-3', '2025-01-01', true, true),
                                                                                                                                                                                                           ('C3-M3', 'Rasoa', 'Paul', '1992-03-12', 'MALE', 'Lot 8 KM Antsirabe', 'Collecteur', '0338234567', 'member.11@fed-agri.mg', 'SECRETARY', 'col-3', '2025-01-01', true, true),
                                                                                                                                                                                                           ('C3-M4', 'Razafy', 'Jeanne', '1988-05-10', 'FEMALE', 'Lot A K 50 Antsirabe', 'Distributeur', '0382334567', 'member.12@fed-agri.mg', 'TREASURER', 'col-3', '2025-01-01', true, true),
                                                                                                                                                                                                           ('C3-M5', 'Andria', 'Pierre', '1999-08-11', 'MALE', 'Lot UV 80 Antsirabe', 'Apiculteur', '0373365567', 'member.13@fed-agri.mg', 'SENIOR', 'col-3', '2025-01-01', true, true),
                                                                                                                                                                                                           ('C3-M6', 'Rakotomalala', 'Sophie', '1998-08-09', 'FEMALE', 'Lot UV 6 Antsirabe', 'Apiculteur', '0378234567', 'member.14@fed-agri.mg', 'SENIOR', 'col-3', '2025-01-01', true, true),
                                                                                                                                                                                                           ('C3-M7', 'Rakotonirina', 'Michel', '1998-01-13', 'MALE', 'Lot UV 7 Antsirabe', 'Apiculteur', '0374914567', 'member.15@fed-agri.mg', 'SENIOR', 'col-3', '2025-01-01', true, true),
                                                                                                                                                                                                           ('C3-M8', 'Randria', 'Claire', '1975-08-02', 'MALE', 'Lot UV 8 Antsirabe', 'Apiculteur', '0370634567', 'member.16@fed-agri.mg', 'SENIOR', 'col-3', '2025-01-01', true, true);

INSERT INTO collectivity_structures (id, collectivity_id, president_id, vice_president_id, treasurer_id, secretary_id, mandate_year, start_date, end_date) VALUES
                                                                                                                                                               ('cs1', 'col-1', 'C1-M1', 'C1-M2', 'C1-M4', 'C1-M3', 2026, '2026-01-01', '2026-12-31'),
                                                                                                                                                               ('cs2', 'col-2', 'C2-M5', 'C2-M6', 'C2-M8', 'C2-M7', 2026, '2026-01-01', '2026-12-31'),
                                                                                                                                                               ('cs3', 'col-3', 'C3-M1', 'C3-M2', 'C3-M4', 'C3-M3', 2026, '2026-01-01', '2026-12-31');


INSERT INTO referees (id, member_id, referee_id, relationship) VALUES
                                                                   ('r1', 'C1-M3', 'C1-M1', 'FAMILLE'),
                                                                   ('r2', 'C1-M3', 'C1-M2', 'COLLEGUE'),
                                                                   ('r3', 'C1-M4', 'C1-M1', 'AMI'),
                                                                   ('r4', 'C1-M4', 'C1-M2', 'COLLEGUE'),
                                                                   ('r5', 'C1-M5', 'C1-M1', 'FAMILLE'),
                                                                   ('r6', 'C1-M5', 'C1-M2', 'AMI'),
                                                                   ('r7', 'C1-M6', 'C1-M1', 'COLLEGUE'),
                                                                   ('r8', 'C1-M6', 'C1-M2', 'FAMILLE'),
                                                                   ('r9', 'C1-M7', 'C1-M1', 'AMI'),
                                                                   ('r10', 'C1-M7', 'C1-M2', 'COLLEGUE'),
                                                                   ('r11', 'C1-M8', 'C1-M6', 'FAMILLE'),
                                                                   ('r12', 'C1-M8', 'C1-M7', 'AMI'),
                                                                   ('r13', 'C2-M3', 'C1-M1', 'COLLEGUE'),
                                                                   ('r14', 'C2-M3', 'C1-M2', 'FAMILLE'),
                                                                   ('r15', 'C2-M5', 'C1-M1', 'AMI'),
                                                                   ('r16', 'C2-M5', 'C1-M2', 'COLLEGUE'),
                                                                   ('r17', 'C3-M1', 'C1-M1', 'FAMILLE'),
                                                                   ('r18', 'C3-M1', 'C1-M2', 'COLLEGUE'),
                                                                   ('r19', 'C3-M3', 'C3-M1', 'COLLEGUE'),
                                                                   ('r20', 'C3-M3', 'C3-M2', 'FAMILLE');


INSERT INTO membership_fees (id, label, frequency, eligible_from, amount, active, collectivity_id) VALUES
                                                                                                       ('cot-1', 'Cotisation annuelle', 'ANNUALLY', '2026-01-01', 100000, true, 'col-1'),
                                                                                                       ('cot-2', 'Cotisation annuelle', 'ANNUALLY', '2026-01-01', 100000, true, 'col-2'),
                                                                                                       ('cot-3', 'Cotisation annuelle', 'ANNUALLY', '2026-01-01', 50000, true, 'col-3');


INSERT INTO accounts (id, account_type, holder_name, mobile_service, phone_number, balance, collectivity_id) VALUES
                                                                                                                 ('C1-A-CASH', 'CASH', NULL, NULL, NULL, 750000, 'col-1'),
                                                                                                                 ('C1-A-MOBILE-1', 'MOBILE_MONEY', 'Mpanorina', 'ORANGE_MONEY', '0370489612', 0, 'col-1'),
                                                                                                                 ('C2-A-CASH', 'CASH', NULL, NULL, NULL, 550000, 'col-2'),
                                                                                                                 ('C2-A-MOBILE-1', 'MOBILE_MONEY', 'Dobo voalohany', 'ORANGE_MONEY', '0320489612', 100000, 'col-2'),
                                                                                                                 ('C3-A-CASH', 'CASH', NULL, NULL, NULL, 0, 'col-3');

INSERT INTO payments (id, amount, payment_mode, payment_date, member_id, membership_fee_id, account_id) VALUES
                                                                                                            ('p1', 100000, 'CASH', '2026-01-01', 'C1-M1', 'cot-1', 'C1-A-CASH'),
                                                                                                            ('p2', 100000, 'CASH', '2026-01-01', 'C1-M2', 'cot-1', 'C1-A-CASH'),
                                                                                                            ('p3', 100000, 'CASH', '2026-01-01', 'C1-M3', 'cot-1', 'C1-A-CASH'),
                                                                                                            ('p4', 100000, 'CASH', '2026-01-01', 'C1-M4', 'cot-1', 'C1-A-CASH'),
                                                                                                            ('p5', 100000, 'CASH', '2026-01-01', 'C1-M5', 'cot-1', 'C1-A-CASH'),
                                                                                                            ('p6', 100000, 'CASH', '2026-01-01', 'C1-M6', 'cot-1', 'C1-A-CASH'),
                                                                                                            ('p7', 60000, 'CASH', '2026-01-01', 'C1-M7', 'cot-1', 'C1-A-CASH'),
                                                                                                            ('p8', 90000, 'CASH', '2026-01-01', 'C1-M8', 'cot-1', 'C1-A-CASH');

INSERT INTO payments (id, amount, payment_mode, payment_date, member_id, membership_fee_id, account_id) VALUES
                                                                                                            ('p9', 60000, 'CASH', '2026-01-01', 'C2-M1', 'cot-2', 'C2-A-CASH'),
                                                                                                            ('p10', 90000, 'CASH', '2026-01-01', 'C2-M2', 'cot-2', 'C2-A-CASH'),
                                                                                                            ('p11', 100000, 'CASH', '2026-01-01', 'C2-M3', 'cot-2', 'C2-A-CASH'),
                                                                                                            ('p12', 100000, 'CASH', '2026-01-01', 'C2-M4', 'cot-2', 'C2-A-CASH'),
                                                                                                            ('p13', 100000, 'CASH', '2026-01-01', 'C2-M5', 'cot-2', 'C2-A-CASH'),
                                                                                                            ('p14', 100000, 'CASH', '2026-01-01', 'C2-M6', 'cot-2', 'C2-A-CASH'),
                                                                                                            ('p15', 40000, 'MOBILE_MONEY', '2026-01-01', 'C2-M7', 'cot-2', 'C2-A-MOBILE-1'),
                                                                                                            ('p16', 60000, 'MOBILE_MONEY', '2026-01-01', 'C2-M8', 'cot-2', 'C2-A-MOBILE-1');


INSERT INTO transactions (id, amount, payment_mode, creation_date, collectivity_id, account_id, member_id) VALUES
                                                                                                               ('t1', 100000, 'CASH', '2026-01-01', 'col-1', 'C1-A-CASH', 'C1-M1'),
                                                                                                               ('t2', 100000, 'CASH', '2026-01-01', 'col-1', 'C1-A-CASH', 'C1-M2'),
                                                                                                               ('t3', 100000, 'CASH', '2026-01-01', 'col-1', 'C1-A-CASH', 'C1-M3'),
                                                                                                               ('t4', 100000, 'CASH', '2026-01-01', 'col-1', 'C1-A-CASH', 'C1-M4'),
                                                                                                               ('t5', 100000, 'CASH', '2026-01-01', 'col-1', 'C1-A-CASH', 'C1-M5'),
                                                                                                               ('t6', 100000, 'CASH', '2026-01-01', 'col-1', 'C1-A-CASH', 'C1-M6'),
                                                                                                               ('t7', 60000, 'CASH', '2026-01-01', 'col-1', 'C1-A-CASH', 'C1-M7'),
                                                                                                               ('t8', 90000, 'CASH', '2026-01-01', 'col-1', 'C1-A-CASH', 'C1-M8');


INSERT INTO transactions (id, amount, payment_mode, creation_date, collectivity_id, account_id, member_id) VALUES
                                                                                                               ('t9', 60000, 'CASH', '2026-01-01', 'col-2', 'C2-A-CASH', 'C2-M1'),
                                                                                                               ('t10', 90000, 'CASH', '2026-01-01', 'col-2', 'C2-A-CASH', 'C2-M2'),
                                                                                                               ('t11', 100000, 'CASH', '2026-01-01', 'col-2', 'C2-A-CASH', 'C2-M3'),
                                                                                                               ('t12', 100000, 'CASH', '2026-01-01', 'col-2', 'C2-A-CASH', 'C2-M4'),
                                                                                                               ('t13', 100000, 'CASH', '2026-01-01', 'col-2', 'C2-A-CASH', 'C2-M5'),
                                                                                                               ('t14', 100000, 'CASH', '2026-01-01', 'col-2', 'C2-A-CASH', 'C2-M6'),
                                                                                                               ('t15', 40000, 'MOBILE_MONEY', '2026-01-01', 'col-2', 'C2-A-MOBILE-1', 'C2-M7'),
                                                                                                               ('t16', 60000, 'MOBILE_MONEY', '2026-01-01', 'col-2', 'C2-A-MOBILE-1', 'C2-M8');