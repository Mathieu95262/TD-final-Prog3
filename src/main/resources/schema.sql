    CREATE TABLE IF NOT EXISTS collectivities (
                                                  id BIGSERIAL PRIMARY KEY,
                                                  numero VARCHAR(50) UNIQUE,
        nom VARCHAR(100) UNIQUE,
        ville VARCHAR(100) NOT NULL,
        specialite_agricole VARCHAR(100) NOT NULL,
        date_creation DATE NOT NULL,
        autorisation_ouverture BOOLEAN NOT NULL DEFAULT false,
        cotisation_annuelle_obligatoire BIGINT
        );

    CREATE TABLE IF NOT EXISTS membres (
                                           id BIGSERIAL PRIMARY KEY,
                                           nom VARCHAR(100) NOT NULL,
        prenom VARCHAR(100) NOT NULL,
        date_naissance DATE NOT NULL,
        genre VARCHAR(10) NOT NULL,
        adresse VARCHAR(255) NOT NULL,
        metier VARCHAR(100) NOT NULL,
        telephone VARCHAR(20) NOT NULL,
        email VARCHAR(255) NOT NULL UNIQUE,
        date_adhesion DATE NOT NULL,
        poste VARCHAR(20) NOT NULL,
        actif BOOLEAN NOT NULL DEFAULT true,
        collectivite_id BIGINT NOT NULL REFERENCES collectivities(id)
        );

    CREATE TABLE IF NOT EXISTS cotisations (
                                               id BIGSERIAL PRIMARY KEY,
                                               type_cotisation VARCHAR(20) NOT NULL,
        montant BIGINT NOT NULL,
        description VARCHAR(255),
        collectivite_id BIGINT NOT NULL REFERENCES collectivities(id),
        active BOOLEAN NOT NULL DEFAULT true
        );

    CREATE TABLE IF NOT EXISTS comptes (
                                           id BIGSERIAL PRIMARY KEY,
                                           dtype VARCHAR(20) NOT NULL,
        nom_titulaire VARCHAR(200) NOT NULL,
        solde BIGINT NOT NULL DEFAULT 0,
        date_solde DATE NOT NULL,
        type_compte VARCHAR(20) NOT NULL,
        collectivite_id BIGINT REFERENCES collectivities(id),
        appartient_federation BOOLEAN NOT NULL DEFAULT false,
        nom_banque VARCHAR(50),
        numero_compte_bancaire VARCHAR(23) UNIQUE,
        service_mobile_money VARCHAR(30),
        numero_telephone VARCHAR(20) UNIQUE
        );

    CREATE TABLE IF NOT EXISTS paiements (
                                             id BIGSERIAL PRIMARY KEY,
                                             montant BIGINT NOT NULL,
                                             date_encaissement DATE NOT NULL,
                                             mode_paiement VARCHAR(20) NOT NULL,
        membre_id BIGINT NOT NULL REFERENCES membres(id),
        cotisation_id BIGINT NOT NULL REFERENCES cotisations(id)
        );

    CREATE TABLE IF NOT EXISTS parrainages (
                                               id BIGSERIAL PRIMARY KEY,
                                               relation VARCHAR(50) NOT NULL,
        candidat_email VARCHAR(255),
        parrain_id BIGINT NOT NULL REFERENCES membres(id)
        );