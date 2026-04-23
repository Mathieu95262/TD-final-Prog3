
CREATE TABLE IF NOT EXISTS collectivities (
                                              id VARCHAR(36) PRIMARY KEY,
    number VARCHAR(10) UNIQUE,
    name VARCHAR(100) UNIQUE,
    location VARCHAR(100),
    specialty VARCHAR(100),
    creation_date DATE,
    federation_approval BOOLEAN,
    annual_membership_dues DECIMAL(10,2)
    );


CREATE TABLE IF NOT EXISTS members (
                                       id VARCHAR(36) PRIMARY KEY,
    last_name VARCHAR(100),
    first_name VARCHAR(100),
    birth_date DATE,
    gender VARCHAR(10),
    address TEXT,
    profession VARCHAR(100),
    phone_number VARCHAR(20),
    email VARCHAR(255),
    occupation VARCHAR(20),
    collectivity_id VARCHAR(36),
    adhesion_date DATE,
    registration_fee_paid BOOLEAN,
    membership_dues_paid BOOLEAN,
    FOREIGN KEY (collectivity_id) REFERENCES collectivities(id)
    );

CREATE TABLE IF NOT EXISTS collectivity_structures (
                                                       id VARCHAR(36) PRIMARY KEY,
    collectivity_id VARCHAR(36) NOT NULL UNIQUE,
    president_id VARCHAR(36),
    vice_president_id VARCHAR(36),
    treasurer_id VARCHAR(36),
    secretary_id VARCHAR(36),
    mandate_year INT,
    start_date DATE,
    end_date DATE,
    FOREIGN KEY (collectivity_id) REFERENCES collectivities(id),
    FOREIGN KEY (president_id) REFERENCES members(id),
    FOREIGN KEY (vice_president_id) REFERENCES members(id),
    FOREIGN KEY (treasurer_id) REFERENCES members(id),
    FOREIGN KEY (secretary_id) REFERENCES members(id)
    );


CREATE TABLE IF NOT EXISTS referees (
                                        id VARCHAR(36) PRIMARY KEY,
    member_id VARCHAR(36),
    referee_id VARCHAR(36),
    relationship VARCHAR(50),
    FOREIGN KEY (member_id) REFERENCES members(id),
    FOREIGN KEY (referee_id) REFERENCES members(id)
    );


CREATE TABLE IF NOT EXISTS membership_fees (
                                               id VARCHAR(36) PRIMARY KEY,
    label VARCHAR(255),
    frequency VARCHAR(20),
    eligible_from DATE,
    amount DECIMAL(10,2),
    active BOOLEAN,
    collectivity_id VARCHAR(36),
    FOREIGN KEY (collectivity_id) REFERENCES collectivities(id)
    );


CREATE TABLE IF NOT EXISTS accounts (
                                        id VARCHAR(36) PRIMARY KEY,
    account_type VARCHAR(20),
    holder_name VARCHAR(200),
    bank_name VARCHAR(50),
    mobile_service VARCHAR(30),
    phone_number VARCHAR(20),
    account_number VARCHAR(50),
    balance DECIMAL(15,2),
    collectivity_id VARCHAR(36),
    FOREIGN KEY (collectivity_id) REFERENCES collectivities(id)
    );


CREATE TABLE IF NOT EXISTS payments (
                                        id VARCHAR(36) PRIMARY KEY,
    amount DECIMAL(15,2),
    payment_mode VARCHAR(20),
    payment_date DATE,
    member_id VARCHAR(36),
    membership_fee_id VARCHAR(36),
    account_id VARCHAR(36),
    FOREIGN KEY (member_id) REFERENCES members(id),
    FOREIGN KEY (membership_fee_id) REFERENCES membership_fees(id),
    FOREIGN KEY (account_id) REFERENCES accounts(id)
    );


CREATE TABLE IF NOT EXISTS transactions (
                                            id VARCHAR(36) PRIMARY KEY,
    amount DECIMAL(15,2),
    payment_mode VARCHAR(20),
    creation_date DATE,
    collectivity_id VARCHAR(36),
    account_id VARCHAR(36),
    member_id VARCHAR(36),
    FOREIGN KEY (collectivity_id) REFERENCES collectivities(id),
    FOREIGN KEY (account_id) REFERENCES accounts(id),
    FOREIGN KEY (member_id) REFERENCES members(id)
    );