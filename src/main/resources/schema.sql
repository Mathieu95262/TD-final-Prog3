DROP TABLE IF EXISTS attendances CASCADE;
DROP TABLE IF EXISTS activities CASCADE;
DROP TABLE IF EXISTS payments CASCADE;
DROP TABLE IF EXISTS contributions CASCADE;
DROP TABLE IF EXISTS accounts CASCADE;
DROP TABLE IF EXISTS referees CASCADE;
DROP TABLE IF EXISTS mandates CASCADE;
DROP TABLE IF EXISTS collectivity_structures CASCADE;
DROP TABLE IF EXISTS federation CASCADE;
DROP TABLE IF EXISTS members CASCADE;
DROP TABLE IF EXISTS collectivities CASCADE;

-- 1. Table members
CREATE TABLE members (
                         id VARCHAR(36) PRIMARY KEY,
                         first_name VARCHAR(100) NOT NULL,
                         last_name VARCHAR(100) NOT NULL,
                         birth_date DATE NOT NULL,
                         gender VARCHAR(10) NOT NULL CHECK (gender IN ('MALE', 'FEMALE')),
                         address TEXT NOT NULL,
                         profession VARCHAR(100) NOT NULL,
                         phone_number VARCHAR(20) NOT NULL UNIQUE,
                         email VARCHAR(255) NOT NULL UNIQUE,
                         occupation VARCHAR(20) NOT NULL CHECK (occupation IN ('JUNIOR', 'SENIOR', 'SECRETARY', 'TREASURER', 'VICE_PRESIDENT', 'PRESIDENT')),
                         adhesion_date DATE NOT NULL,
                         collectivity_id VARCHAR(36),
                         registration_fee_paid BOOLEAN DEFAULT FALSE,
                         membership_dues_paid BOOLEAN DEFAULT FALSE
);

-- 2. Table collectivities
CREATE TABLE collectivities (
                                id VARCHAR(36) PRIMARY KEY,
                                name VARCHAR(100) NOT NULL UNIQUE,
                                location VARCHAR(100) NOT NULL,
                                specialty VARCHAR(100) NOT NULL,
                                creation_date DATE NOT NULL,
                                federation_approval BOOLEAN DEFAULT FALSE,
                                annual_membership_dues DECIMAL(10,2) NOT NULL DEFAULT 200000.00
);

-- 3. Ajouter la clé étrangère
ALTER TABLE members
    ADD CONSTRAINT fk_members_collectivity
        FOREIGN KEY (collectivity_id) REFERENCES collectivities(id);

-- 4. Table collectivity_structures
CREATE TABLE collectivity_structures (
                                         id VARCHAR(36) PRIMARY KEY,
                                         collectivity_id VARCHAR(36) NOT NULL UNIQUE,
                                         president_id VARCHAR(36) NOT NULL,
                                         vice_president_id VARCHAR(36) NOT NULL,
                                         treasurer_id VARCHAR(36) NOT NULL,
                                         secretary_id VARCHAR(36) NOT NULL,
                                         mandate_year INT NOT NULL,
                                         start_date DATE NOT NULL,
                                         end_date DATE NOT NULL,
                                         FOREIGN KEY (collectivity_id) REFERENCES collectivities(id) ON DELETE CASCADE,
                                         FOREIGN KEY (president_id) REFERENCES members(id),
                                         FOREIGN KEY (vice_president_id) REFERENCES members(id),
                                         FOREIGN KEY (treasurer_id) REFERENCES members(id),
                                         FOREIGN KEY (secretary_id) REFERENCES members(id)
);
-- 5. Table referees (parrainages)
CREATE TABLE referees (
                          id VARCHAR(36) PRIMARY KEY,
                          member_id VARCHAR(36) NOT NULL,
                          referee_id VARCHAR(36) NOT NULL,
                          relationship VARCHAR(50) NOT NULL,
                          FOREIGN KEY (member_id) REFERENCES members(id) ON DELETE CASCADE,
                          FOREIGN KEY (referee_id) REFERENCES members(id),
                          UNIQUE(member_id, referee_id)
);
-- 6. Table mandates
CREATE TABLE mandates (
                          id VARCHAR(36) PRIMARY KEY,
                          member_id VARCHAR(36) NOT NULL,
                          collectivity_id VARCHAR(36) NOT NULL,
                          role VARCHAR(20) NOT NULL CHECK (role IN ('PRESIDENT', 'VICE_PRESIDENT', 'TREASURER', 'SECRETARY')),
                          mandate_year INT NOT NULL,
                          start_date DATE NOT NULL,
                          end_date DATE NOT NULL,
                          is_active BOOLEAN DEFAULT TRUE,
                          FOREIGN KEY (member_id) REFERENCES members(id),
                          FOREIGN KEY (collectivity_id) REFERENCES collectivities(id),
                          UNIQUE(collectivity_id, role, mandate_year)
);
-- 7. Table accounts
CREATE TABLE accounts (
                          id VARCHAR(36) PRIMARY KEY,
                          collectivity_id VARCHAR(36),
                          account_type VARCHAR(20) NOT NULL CHECK (account_type IN ('CASH', 'BANK', 'MOBILE_MONEY')),
                          account_holder_name VARCHAR(200),
                          bank_name VARCHAR(50),
                          account_number VARCHAR(30),
                          mobile_service VARCHAR(20),
                          phone_number VARCHAR(20),
                          balance DECIMAL(15,2) DEFAULT 0.00,
                          FOREIGN KEY (collectivity_id) REFERENCES collectivities(id) ON DELETE CASCADE
);
-- 8. Table contributions
CREATE TABLE contributions (
                               id VARCHAR(36) PRIMARY KEY,
                               name VARCHAR(100) NOT NULL,
                               collectivity_id VARCHAR(36) NOT NULL,
                               amount DECIMAL(15,2) NOT NULL,
                               frequency VARCHAR(20) NOT NULL CHECK (frequency IN ('MONTHLY', 'ANNUAL', 'PUNCTUAL')),
                               is_mandatory BOOLEAN DEFAULT FALSE,
                               description TEXT,
                               FOREIGN KEY (collectivity_id) REFERENCES collectivities(id) ON DELETE CASCADE
);
-- 9. Table payments
CREATE TABLE payments (
                          id VARCHAR(36) PRIMARY KEY,
                          member_id VARCHAR(36) NOT NULL,
                          contribution_id VARCHAR(36),
                          account_id VARCHAR(36) NOT NULL,
                          amount DECIMAL(15,2) NOT NULL,
                          payment_date DATE NOT NULL,
                          payment_method VARCHAR(20) NOT NULL CHECK (payment_method IN ('CASH', 'BANK_TRANSFER', 'MOBILE_MONEY')),
                          reference_number VARCHAR(100),
                          FOREIGN KEY (member_id) REFERENCES members(id),
                          FOREIGN KEY (contribution_id) REFERENCES contributions(id),
                          FOREIGN KEY (account_id) REFERENCES accounts(id)
);
-- 10. Table activities
CREATE TABLE activities (
                            id VARCHAR(36) PRIMARY KEY,
                            collectivity_id VARCHAR(36),
                            title VARCHAR(200) NOT NULL,
                            description TEXT,
                            activity_type VARCHAR(20) NOT NULL CHECK (activity_type IN ('MONTHLY_MEETING', 'JUNIOR_TRAINING', 'EXCEPTIONAL')),
                            is_mandatory BOOLEAN DEFAULT FALSE,
                            target_audience VARCHAR(20),
                            scheduled_date DATE NOT NULL,
                            start_time TIME,
                            end_time TIME,
                            location VARCHAR(200),
                            created_by VARCHAR(36),
                            FOREIGN KEY (collectivity_id) REFERENCES collectivities(id) ON DELETE CASCADE,
                            FOREIGN KEY (created_by) REFERENCES members(id)
);
-- 11. Table attendances
CREATE TABLE attendances (
                             id VARCHAR(36) PRIMARY KEY,
                             activity_id VARCHAR(36) NOT NULL,
                             member_id VARCHAR(36) NOT NULL,
                             is_present BOOLEAN DEFAULT FALSE,
                             is_excused BOOLEAN DEFAULT FALSE,
                             excuse_reason TEXT,
                             is_visitor BOOLEAN DEFAULT FALSE,
                             visitor_collectivity_id VARCHAR(36),
                             FOREIGN KEY (activity_id) REFERENCES activities(id) ON DELETE CASCADE,
                             FOREIGN KEY (member_id) REFERENCES members(id),
                             FOREIGN KEY (visitor_collectivity_id) REFERENCES collectivities(id),
                             UNIQUE(activity_id, member_id)
);
-- 12. Table federation
CREATE TABLE federation (
                            id VARCHAR(36) PRIMARY KEY,
                            president_id VARCHAR(36) NOT NULL,
                            vice_president_id VARCHAR(36) NOT NULL,
                            treasurer_id VARCHAR(36) NOT NULL,
                            secretary_id VARCHAR(36) NOT NULL,
                            mandate_start_date DATE NOT NULL,
                            mandate_end_date DATE NOT NULL,
                            is_active BOOLEAN DEFAULT TRUE,
                            FOREIGN KEY (president_id) REFERENCES members(id),
                            FOREIGN KEY (vice_president_id) REFERENCES members(id),
                            FOREIGN KEY (treasurer_id) REFERENCES members(id),
                            FOREIGN KEY (secretary_id) REFERENCES members(id)
);