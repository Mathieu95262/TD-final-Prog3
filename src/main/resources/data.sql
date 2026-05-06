INSERT INTO collectivities (numero, nom, ville, specialite_agricole, date_creation, autorisation_ouverture, cotisation_annuelle_obligatoire)
SELECT 'COL-001', 'Mpanorina', 'Ambatondrazaka', 'Riziculture', '2023-01-01', true, 100000
WHERE NOT EXISTS (SELECT 1 FROM collectivities WHERE numero = 'COL-001');

INSERT INTO collectivities (numero, nom, ville, specialite_agricole, date_creation, autorisation_ouverture, cotisation_annuelle_obligatoire)
SELECT 'COL-002', 'Dobo Voalohany', 'Ambatondrazaka', 'Pisciculture', '2023-06-15', true, 100000
WHERE NOT EXISTS (SELECT 1 FROM collectivities WHERE numero = 'COL-002');

INSERT INTO collectivities (numero, nom, ville, specialite_agricole, date_creation, autorisation_ouverture, cotisation_annuelle_obligatoire)
SELECT 'COL-003', 'Tantely Mamy', 'Brickaville', 'Apiculture', '2024-01-10', true, 50000
WHERE NOT EXISTS (SELECT 1 FROM collectivities WHERE numero = 'COL-003');

INSERT INTO membres (nom, prenom, date_naissance, genre, adresse, metier, telephone, email, date_adhesion, poste, actif, collectivite_id) VALUES
    ('Rakoto', 'Jean', '1980-02-01', 'MALE', 'Lot II V M Ambato', 'Riziculteur', '0341234567', 'rakoto.jean.c1@fed-agri.mg', '2025-01-01', 'PRESIDENT', true, 1)
ON CONFLICT (email) DO NOTHING;

INSERT INTO membres (nom, prenom, date_naissance, genre, adresse, metier, telephone, email, date_adhesion, poste, actif, collectivite_id) VALUES
    ('Rabe', 'Marie', '1982-03-05', 'FEMALE', 'Lot II F Ambato', 'Agriculteur', '0321234567', 'rabe.marie.c1@fed-agri.mg', '2025-01-01', 'VICE_PRESIDENT', true, 1)
ON CONFLICT (email) DO NOTHING;

INSERT INTO membres (nom, prenom, date_naissance, genre, adresse, metier, telephone, email, date_adhesion, poste, actif, collectivite_id) VALUES
    ('Rasoa', 'Paul', '1992-03-10', 'MALE', 'Lot II J Ambato', 'Collecteur', '0331234567', 'rasoa.paul.c1@fed-agri.mg', '2025-01-01', 'SECRETARY', true, 1)
ON CONFLICT (email) DO NOTHING;

INSERT INTO membres (nom, prenom, date_naissance, genre, adresse, metier, telephone, email, date_adhesion, poste, actif, collectivite_id) VALUES
    ('Razafy', 'Jeanne', '1988-05-22', 'FEMALE', 'Lot A K 50 Ambato', 'Distributeur', '0381234567', 'razafy.jeanne.c1@fed-agri.mg', '2025-01-01', 'TREASURER', true, 1)
ON CONFLICT (email) DO NOTHING;

INSERT INTO membres (nom, prenom, date_naissance, genre, adresse, metier, telephone, email, date_adhesion, poste, actif, collectivite_id) VALUES
    ('Andria', 'Pierre', '1999-08-21', 'MALE', 'Lot UV 80 Ambato', 'Riziculteur', '0373434567', 'andria.pierre.c1@fed-agri.mg', '2025-01-01', 'CONFIRMED_MEMBER', true, 1)
ON CONFLICT (email) DO NOTHING;

INSERT INTO membres (nom, prenom, date_naissance, genre, adresse, metier, telephone, email, date_adhesion, poste, actif, collectivite_id) VALUES
    ('Rakotomalala', 'Sophie', '1998-08-22', 'FEMALE', 'Lot UV 6 Ambato', 'Riziculteur', '0372234567', 'rakotomalala.sophie.c1@fed-agri.mg', '2025-01-01', 'CONFIRMED_MEMBER', true, 1)
ON CONFLICT (email) DO NOTHING;

INSERT INTO membres (nom, prenom, date_naissance, genre, adresse, metier, telephone, email, date_adhesion, poste, actif, collectivite_id) VALUES
    ('Rakotonirina', 'Michel', '1998-01-31', 'MALE', 'Lot UV 7 Ambato', 'Riziculteur', '0374234567', 'rakotonirina.michel.c1@fed-agri.mg', '2025-01-01', 'CONFIRMED_MEMBER', true, 1)
ON CONFLICT (email) DO NOTHING;

INSERT INTO membres (nom, prenom, date_naissance, genre, adresse, metier, telephone, email, date_adhesion, poste, actif, collectivite_id) VALUES
    ('Randria', 'Claire', '1975-08-20', 'FEMALE', 'Lot UV 8 Ambato', 'Riziculteur', '0370234567', 'randria.claire.c1@fed-agri.mg', '2025-01-01', 'CONFIRMED_MEMBER', true, 1)
ON CONFLICT (email) DO NOTHING;

INSERT INTO membres (nom, prenom, date_naissance, genre, adresse, metier, telephone, email, date_adhesion, poste, actif, collectivite_id) VALUES
    ('Rakoto', 'Jean', '1980-02-01', 'MALE', 'Lot II V M Ambato', 'Riziculteur', '0341234567', 'rakoto.jean.c2@fed-agri.mg', '2025-01-01', 'CONFIRMED_MEMBER', true, 2)
ON CONFLICT (email) DO NOTHING;

INSERT INTO membres (nom, prenom, date_naissance, genre, adresse, metier, telephone, email, date_adhesion, poste, actif, collectivite_id) VALUES
    ('Rabe', 'Marie', '1982-03-05', 'FEMALE', 'Lot II F Ambato', 'Agriculteur', '0321234567', 'rabe.marie.c2@fed-agri.mg', '2025-01-01', 'CONFIRMED_MEMBER', true, 2)
ON CONFLICT (email) DO NOTHING;

INSERT INTO membres (nom, prenom, date_naissance, genre, adresse, metier, telephone, email, date_adhesion, poste, actif, collectivite_id) VALUES
    ('Rasoa', 'Paul', '1992-03-10', 'MALE', 'Lot II J Ambato', 'Collecteur', '0331234567', 'rasoa.paul.c2@fed-agri.mg', '2025-01-01', 'CONFIRMED_MEMBER', true, 2)
ON CONFLICT (email) DO NOTHING;

INSERT INTO membres (nom, prenom, date_naissance, genre, adresse, metier, telephone, email, date_adhesion, poste, actif, collectivite_id) VALUES
    ('Razafy', 'Jeanne', '1988-05-22', 'FEMALE', 'Lot A K 50 Ambato', 'Distributeur', '0381234567', 'razafy.jeanne.c2@fed-agri.mg', '2025-01-01', 'CONFIRMED_MEMBER', true, 2)
ON CONFLICT (email) DO NOTHING;

INSERT INTO membres (nom, prenom, date_naissance, genre, adresse, metier, telephone, email, date_adhesion, poste, actif, collectivite_id) VALUES
    ('Andria', 'Pierre', '1999-08-21', 'MALE', 'Lot UV 80 Ambato', 'Riziculteur', '0373434567', 'andria.pierre.c2@fed-agri.mg', '2025-01-01', 'PRESIDENT', true, 2)
ON CONFLICT (email) DO NOTHING;

INSERT INTO membres (nom, prenom, date_naissance, genre, adresse, metier, telephone, email, date_adhesion, poste, actif, collectivite_id) VALUES
    ('Rakotomalala', 'Sophie', '1998-08-22', 'FEMALE', 'Lot UV 6 Ambato', 'Riziculteur', '0372234567', 'rakotomalala.sophie.c2@fed-agri.mg', '2025-01-01', 'VICE_PRESIDENT', true, 2)
ON CONFLICT (email) DO NOTHING;

INSERT INTO membres (nom, prenom, date_naissance, genre, adresse, metier, telephone, email, date_adhesion, poste, actif, collectivite_id) VALUES
    ('Rakotonirina', 'Michel', '1998-01-31', 'MALE', 'Lot UV 7 Ambato', 'Riziculteur', '0374234567', 'rakotonirina.michel.c2@fed-agri.mg', '2025-01-01', 'SECRETARY', true, 2)
ON CONFLICT (email) DO NOTHING;

INSERT INTO membres (nom, prenom, date_naissance, genre, adresse, metier, telephone, email, date_adhesion, poste, actif, collectivite_id) VALUES
    ('Randria', 'Claire', '1975-08-20', 'FEMALE', 'Lot UV 8 Ambato', 'Riziculteur', '0370234567', 'randria.claire.c2@fed-agri.mg', '2025-01-01', 'TREASURER', true, 2)
ON CONFLICT (email) DO NOTHING;

INSERT INTO membres (nom, prenom, date_naissance, genre, adresse, metier, telephone, email, date_adhesion, poste, actif, collectivite_id) VALUES
    ('Rakoto', 'Jean', '1988-01-02', 'MALE', 'Lot 33 J Antsirabe', 'Apiculteur', '0340345671', 'rakoto.jean.c3@fed-agri.mg', '2025-01-01', 'PRESIDENT', true, 3)
ON CONFLICT (email) DO NOTHING;

INSERT INTO membres (nom, prenom, date_naissance, genre, adresse, metier, telephone, email, date_adhesion, poste, actif, collectivite_id) VALUES
    ('Rabe', 'Marie', '1982-03-05', 'MALE', 'Lot 2 J Antsirabe', 'Agriculteur', '0338634567', 'rabe.marie.c3@fed-agri.mg', '2025-01-01', 'VICE_PRESIDENT', true, 3)
ON CONFLICT (email) DO NOTHING;

INSERT INTO membres (nom, prenom, date_naissance, genre, adresse, metier, telephone, email, date_adhesion, poste, actif, collectivite_id) VALUES
    ('Rasoa', 'Paul', '1992-03-12', 'MALE', 'Lot 8 KM Antsirabe', 'Collecteur', '0338234567', 'rasoa.paul.c3@fed-agri.mg', '2025-01-01', 'SECRETARY', true, 3)
ON CONFLICT (email) DO NOTHING;

INSERT INTO membres (nom, prenom, date_naissance, genre, adresse, metier, telephone, email, date_adhesion, poste, actif, collectivite_id) VALUES
    ('Razafy', 'Jeanne', '1988-05-10', 'FEMALE', 'Lot A K 50 Antsirabe', 'Distributeur', '0382334567', 'razafy.jeanne.c3@fed-agri.mg', '2025-01-01', 'TREASURER', true, 3)
ON CONFLICT (email) DO NOTHING;

INSERT INTO membres (nom, prenom, date_naissance, genre, adresse, metier, telephone, email, date_adhesion, poste, actif, collectivite_id) VALUES
    ('Andria', 'Pierre', '1999-08-11', 'MALE', 'Lot UV 80 Antsirabe', 'Apiculteur', '0373365567', 'andria.pierre.c3@fed-agri.mg', '2025-01-01', 'CONFIRMED_MEMBER', true, 3)
ON CONFLICT (email) DO NOTHING;

INSERT INTO membres (nom, prenom, date_naissance, genre, adresse, metier, telephone, email, date_adhesion, poste, actif, collectivite_id) VALUES
    ('Rakotomalala', 'Sophie', '1998-08-09', 'FEMALE', 'Lot UV 6 Antsirabe', 'Apiculteur', '0378234567', 'rakotomalala.sophie.c3@fed-agri.mg', '2025-01-01', 'CONFIRMED_MEMBER', true, 3)
ON CONFLICT (email) DO NOTHING;

INSERT INTO membres (nom, prenom, date_naissance, genre, adresse, metier, telephone, email, date_adhesion, poste, actif, collectivite_id) VALUES
    ('Rakotonirina', 'Michel', '1998-01-13', 'MALE', 'Lot UV 7 Antsirabe', 'Apiculteur', '0374914567', 'rakotonirina.michel.c3@fed-agri.mg', '2025-01-01', 'CONFIRMED_MEMBER', true, 3)
ON CONFLICT (email) DO NOTHING;

INSERT INTO membres (nom, prenom, date_naissance, genre, adresse, metier, telephone, email, date_adhesion, poste, actif, collectivite_id) VALUES
    ('Randria', 'Claire', '1975-08-02', 'MALE', 'Lot UV 8 Antsirabe', 'Apiculteur', '0370634567', 'randria.claire.c3@fed-agri.mg', '2025-01-01', 'CONFIRMED_MEMBER', true, 3)
ON CONFLICT (email) DO NOTHING;

INSERT INTO cotisations (type_cotisation, montant, description, collectivite_id)
SELECT 'ANNUAL', 100000, 'Cotisation annuelle 2026', 1
WHERE NOT EXISTS (SELECT 1 FROM cotisations WHERE collectivite_id = 1 AND description = 'Cotisation annuelle 2026');

INSERT INTO cotisations (type_cotisation, montant, description, collectivite_id)
SELECT 'ANNUAL', 100000, 'Cotisation annuelle 2026', 2
WHERE NOT EXISTS (SELECT 1 FROM cotisations WHERE collectivite_id = 2 AND description = 'Cotisation annuelle 2026');

INSERT INTO cotisations (type_cotisation, montant, description, collectivite_id)
SELECT 'ANNUAL', 50000, 'Cotisation annuelle 2026', 3
WHERE NOT EXISTS (SELECT 1 FROM cotisations WHERE collectivite_id = 3 AND description = 'Cotisation annuelle 2026');

INSERT INTO comptes (dtype, nom_titulaire, solde, date_solde, type_compte, collectivite_id, appartient_federation)
SELECT 'CAISSE', 'Caisse Mpanorina', 750000, '2026-01-01', 'CASH_REGISTER', 1, false
WHERE NOT EXISTS (SELECT 1 FROM comptes WHERE nom_titulaire = 'Caisse Mpanorina');

INSERT INTO comptes (dtype, nom_titulaire, solde, date_solde, type_compte, collectivite_id, appartient_federation, service_mobile_money, numero_telephone)
SELECT 'MOBILE_MONEY', 'Mpanorina', 0, '2026-01-01', 'MOBILE_MONEY', 1, false, 'Orange Money', '0370489612'
WHERE NOT EXISTS (SELECT 1 FROM comptes WHERE numero_telephone = '0370489612');

INSERT INTO comptes (dtype, nom_titulaire, solde, date_solde, type_compte, collectivite_id, appartient_federation)
SELECT 'CAISSE', 'Caisse Dobo Voalohany', 550000, '2026-01-01', 'CASH_REGISTER', 2, false
WHERE NOT EXISTS (SELECT 1 FROM comptes WHERE nom_titulaire = 'Caisse Dobo Voalohany');

INSERT INTO comptes (dtype, nom_titulaire, solde, date_solde, type_compte, collectivite_id, appartient_federation, service_mobile_money, numero_telephone)
SELECT 'MOBILE_MONEY', 'Dobo Voalohany', 100000, '2026-01-01', 'MOBILE_MONEY', 2, false, 'Orange Money', '0320489612'
WHERE NOT EXISTS (SELECT 1 FROM comptes WHERE numero_telephone = '0320489612');

INSERT INTO comptes (dtype, nom_titulaire, solde, date_solde, type_compte, collectivite_id, appartient_federation)
SELECT 'CAISSE', 'Caisse Tantely Mamy', 0, '2026-01-01', 'CASH_REGISTER', 3, false
WHERE NOT EXISTS (SELECT 1 FROM comptes WHERE nom_titulaire = 'Caisse Tantely Mamy');

INSERT INTO parrainages (relation, candidat_email, parrain_id)
SELECT 'FAMILLE', 'candidat1@test.mg', 1
WHERE NOT EXISTS (SELECT 1 FROM parrainages WHERE candidat_email = 'candidat1@test.mg' AND parrain_id = 1);

INSERT INTO parrainages (relation, candidat_email, parrain_id)
SELECT 'COLLEGUE', 'candidat1@test.mg', 2
WHERE NOT EXISTS (SELECT 1 FROM parrainages WHERE candidat_email = 'candidat1@test.mg' AND parrain_id = 2);

INSERT INTO parrainages (relation, candidat_email, parrain_id)
SELECT 'AMI', 'candidat2@test.mg', 1
WHERE NOT EXISTS (SELECT 1 FROM parrainages WHERE candidat_email = 'candidat2@test.mg' AND parrain_id = 1);

INSERT INTO parrainages (relation, candidat_email, parrain_id)
SELECT 'COLLEGUE', 'candidat2@test.mg', 2
WHERE NOT EXISTS (SELECT 1 FROM parrainages WHERE candidat_email = 'candidat2@test.mg' AND parrain_id = 2);

INSERT INTO parrainages (relation, candidat_email, parrain_id)
SELECT 'FAMILLE', 'candidat3@test.mg', 1
WHERE NOT EXISTS (SELECT 1 FROM parrainages WHERE candidat_email = 'candidat3@test.mg' AND parrain_id = 1);

INSERT INTO parrainages (relation, candidat_email, parrain_id)
SELECT 'AMI', 'candidat3@test.mg', 2
WHERE NOT EXISTS (SELECT 1 FROM parrainages WHERE candidat_email = 'candidat3@test.mg' AND parrain_id = 2);

INSERT INTO parrainages (relation, candidat_email, parrain_id)
SELECT 'COLLEGUE', 'candidat4@test.mg', 1
WHERE NOT EXISTS (SELECT 1 FROM parrainages WHERE candidat_email = 'candidat4@test.mg' AND parrain_id = 1);

INSERT INTO parrainages (relation, candidat_email, parrain_id)
SELECT 'FAMILLE', 'candidat4@test.mg', 2
WHERE NOT EXISTS (SELECT 1 FROM parrainages WHERE candidat_email = 'candidat4@test.mg' AND parrain_id = 2);

INSERT INTO paiements (montant, date_encaissement, mode_paiement, membre_id, cotisation_id)
SELECT 100000, '2026-01-01', 'CASH', 1, 1
WHERE NOT EXISTS (SELECT 1 FROM paiements WHERE membre_id = 1 AND cotisation_id = 1);

INSERT INTO paiements (montant, date_encaissement, mode_paiement, membre_id, cotisation_id)
SELECT 100000, '2026-01-01', 'CASH', 2, 1
WHERE NOT EXISTS (SELECT 1 FROM paiements WHERE membre_id = 2 AND cotisation_id = 1);

INSERT INTO paiements (montant, date_encaissement, mode_paiement, membre_id, cotisation_id)
SELECT 100000, '2026-01-01', 'CASH', 3, 1
WHERE NOT EXISTS (SELECT 1 FROM paiements WHERE membre_id = 3 AND cotisation_id = 1);

INSERT INTO paiements (montant, date_encaissement, mode_paiement, membre_id, cotisation_id)
SELECT 100000, '2026-01-01', 'CASH', 4, 1
WHERE NOT EXISTS (SELECT 1 FROM paiements WHERE membre_id = 4 AND cotisation_id = 1);

INSERT INTO paiements (montant, date_encaissement, mode_paiement, membre_id, cotisation_id)
SELECT 100000, '2026-01-01', 'CASH', 5, 1
WHERE NOT EXISTS (SELECT 1 FROM paiements WHERE membre_id = 5 AND cotisation_id = 1);

INSERT INTO paiements (montant, date_encaissement, mode_paiement, membre_id, cotisation_id)
SELECT 100000, '2026-01-01', 'CASH', 6, 1
WHERE NOT EXISTS (SELECT 1 FROM paiements WHERE membre_id = 6 AND cotisation_id = 1);

INSERT INTO paiements (montant, date_encaissement, mode_paiement, membre_id, cotisation_id)
SELECT 60000, '2026-01-01', 'CASH', 7, 1
WHERE NOT EXISTS (SELECT 1 FROM paiements WHERE membre_id = 7 AND cotisation_id = 1);

INSERT INTO paiements (montant, date_encaissement, mode_paiement, membre_id, cotisation_id)
SELECT 90000, '2026-01-01', 'CASH', 8, 1
WHERE NOT EXISTS (SELECT 1 FROM paiements WHERE membre_id = 8 AND cotisation_id = 1);

INSERT INTO paiements (montant, date_encaissement, mode_paiement, membre_id, cotisation_id)
SELECT 60000, '2026-01-01', 'CASH', 9, 2
WHERE NOT EXISTS (SELECT 1 FROM paiements WHERE membre_id = 9 AND cotisation_id = 2);

INSERT INTO paiements (montant, date_encaissement, mode_paiement, membre_id, cotisation_id)
SELECT 90000, '2026-01-01', 'CASH', 10, 2
WHERE NOT EXISTS (SELECT 1 FROM paiements WHERE membre_id = 10 AND cotisation_id = 2);

INSERT INTO paiements (montant, date_encaissement, mode_paiement, membre_id, cotisation_id)
SELECT 100000, '2026-01-01', 'CASH', 11, 2
WHERE NOT EXISTS (SELECT 1 FROM paiements WHERE membre_id = 11 AND cotisation_id = 2);

INSERT INTO paiements (montant, date_encaissement, mode_paiement, membre_id, cotisation_id)
SELECT 100000, '2026-01-01', 'CASH', 12, 2
WHERE NOT EXISTS (SELECT 1 FROM paiements WHERE membre_id = 12 AND cotisation_id = 2);

INSERT INTO paiements (montant, date_encaissement, mode_paiement, membre_id, cotisation_id)
SELECT 100000, '2026-01-01', 'CASH', 13, 2
WHERE NOT EXISTS (SELECT 1 FROM paiements WHERE membre_id = 13 AND cotisation_id = 2);

INSERT INTO paiements (montant, date_encaissement, mode_paiement, membre_id, cotisation_id)
SELECT 100000, '2026-01-01', 'CASH', 14, 2
WHERE NOT EXISTS (SELECT 1 FROM paiements WHERE membre_id = 14 AND cotisation_id = 2);

INSERT INTO paiements (montant, date_encaissement, mode_paiement, membre_id, cotisation_id)
SELECT 40000, '2026-01-01', 'MOBILE_MONEY', 15, 2
WHERE NOT EXISTS (SELECT 1 FROM paiements WHERE membre_id = 15 AND cotisation_id = 2);

INSERT INTO paiements (montant, date_encaissement, mode_paiement, membre_id, cotisation_id)
SELECT 60000, '2026-01-01', 'MOBILE_MONEY', 16, 2
WHERE NOT EXISTS (SELECT 1 FROM paiements WHERE membre_id = 16 AND cotisation_id = 2);