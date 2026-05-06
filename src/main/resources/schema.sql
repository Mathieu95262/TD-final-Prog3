CREATE TYPE gender_enum AS ENUM ('MALE', 'FEMALE');
CREATE TYPE occupation_enum AS ENUM ('JUNIOR', 'SENIOR', 'SECRETARY', 'TREASURER', 'VICE_PRESIDENT', 'PRESIDENT');

CREATE TABLE collectivity (
    id VARCHAR(255) PRIMARY KEY,
    name VARCHAR(255) UNIQUE NOT NULL,
    location VARCHAR(255) NOT NULL,
    agricultural_specialty VARCHAR(255) NOT NULL,
    creation_date DATE NOT NULL,
    federation_approval BOOLEAN DEFAULT FALSE,
    annual_dues BIGINT NOT NULL DEFAULT 0,
    president_id VARCHAR(255),
    vice_president_id VARCHAR(255),
    treasurer_id VARCHAR(255),
    secretary_id VARCHAR(255)
);

CREATE TABLE member (
    id VARCHAR(255) PRIMARY KEY,
    collectivity_id VARCHAR(255) NOT NULL,
    first_name VARCHAR(255) NOT NULL,
    last_name VARCHAR(255) NOT NULL,
    birth_date DATE NOT NULL,
    gender gender_enum NOT NULL,
    address VARCHAR(255) NOT NULL,
    profession VARCHAR(255) NOT NULL,
    phone_number BIGINT NOT NULL,
    email VARCHAR(255) NOT NULL,
    adhesion_date DATE NOT NULL,
    occupation occupation_enum NOT NULL,
    registration_fee_paid BOOLEAN DEFAULT FALSE,
    membership_dues_paid BOOLEAN DEFAULT FALSE,
    FOREIGN KEY (collectivity_id) REFERENCES collectivity(id)
);

ALTER TABLE collectivity
    ADD CONSTRAINT fk_president FOREIGN KEY (president_id) REFERENCES member(id),
    ADD CONSTRAINT fk_vice_president FOREIGN KEY (vice_president_id) REFERENCES member(id),
    ADD CONSTRAINT fk_treasurer FOREIGN KEY (treasurer_id) REFERENCES member(id),
    ADD CONSTRAINT fk_secretary FOREIGN KEY (secretary_id) REFERENCES member(id);

CREATE TABLE sponsorship (
    candidate_id VARCHAR(255) NOT NULL,
    sponsor_id VARCHAR(255) NOT NULL,
    relationship_nature VARCHAR(255) NOT NULL,
    PRIMARY KEY (candidate_id, sponsor_id),
    FOREIGN KEY (candidate_id) REFERENCES member(id),
    FOREIGN KEY (sponsor_id) REFERENCES member(id)
);

CREATE TYPE fee_type_enum AS ENUM ('MONTHLY', 'ANNUAL', 'PUNCTUAL');
CREATE TYPE payment_mode_enum AS ENUM ('CASH', 'BANK_TRANSFER', 'MOBILE_MONEY');

-- Membership fees defined by a collectivity
CREATE TABLE membership_fee (
    id VARCHAR(255) PRIMARY KEY,
    collectivity_id VARCHAR(255) NOT NULL,
    label VARCHAR(255) NOT NULL,
    amount BIGINT NOT NULL,
    fee_type fee_type_enum NOT NULL,
    active BOOLEAN NOT NULL DEFAULT TRUE,
    created_at DATE NOT NULL DEFAULT CURRENT_DATE,
    FOREIGN KEY (collectivity_id) REFERENCES collectivity(id)
);

-- Payments made by members
CREATE TABLE member_payment (
    id VARCHAR(255) PRIMARY KEY,
    member_id VARCHAR(255) NOT NULL,
    membership_fee_id VARCHAR(255) NOT NULL,
    amount BIGINT NOT NULL,
    payment_date DATE NOT NULL,
    payment_mode payment_mode_enum NOT NULL,
    FOREIGN KEY (member_id) REFERENCES member(id),
    FOREIGN KEY (membership_fee_id) REFERENCES membership_fee(id)
);

-- Feature J: unique number and name assignment
ALTER TABLE collectivity ADD COLUMN IF NOT EXISTS unique_number VARCHAR(255) UNIQUE;
ALTER TABLE collectivity ADD COLUMN IF NOT EXISTS unique_name VARCHAR(255) UNIQUE;

-- Feature D: financial accounts (cash, bank, mobile money)
CREATE TYPE account_type_enum AS ENUM ('CASH', 'BANK', 'MOBILE_MONEY');

CREATE TABLE financial_account (
    id VARCHAR(255) PRIMARY KEY,
    collectivity_id VARCHAR(255) NOT NULL,
    account_type account_type_enum NOT NULL,
    -- bank fields
    account_holder VARCHAR(255),
    bank_name VARCHAR(255),
    account_number VARCHAR(255),
    -- mobile money fields
    mobile_holder VARCHAR(255),
    mobile_service VARCHAR(255),
    mobile_phone VARCHAR(255),
    -- initial balance
    initial_balance BIGINT NOT NULL DEFAULT 0,
    FOREIGN KEY (collectivity_id) REFERENCES collectivity(id)
);

-- Feature D: transactions (every payment in/out on an account)
CREATE TYPE transaction_type_enum AS ENUM ('CREDIT', 'DEBIT');

CREATE TABLE financial_transaction (
    id VARCHAR(255) PRIMARY KEY,
    financial_account_id VARCHAR(255) NOT NULL,
    collectivity_id VARCHAR(255) NOT NULL,
    amount BIGINT NOT NULL,
    transaction_type transaction_type_enum NOT NULL,
    payment_mode payment_mode_enum NOT NULL,
    transaction_date DATE NOT NULL,
    description VARCHAR(255),
    FOREIGN KEY (financial_account_id) REFERENCES financial_account(id),
    FOREIGN KEY (collectivity_id) REFERENCES collectivity(id)
);

-- Feature E: activities
CREATE TYPE activity_type_enum AS ENUM ('MONTHLY_ASSEMBLY', 'JUNIOR_TRAINING', 'EXCEPTIONAL');
CREATE TYPE attendance_requirement_enum AS ENUM ('ALL', 'JUNIORS_ONLY', 'SPECIFIC', 'OPTIONAL');

CREATE TABLE activity (
    id VARCHAR(255) PRIMARY KEY,
    collectivity_id VARCHAR(255) NOT NULL,
    title VARCHAR(255) NOT NULL,
    activity_date DATE NOT NULL,
    activity_type activity_type_enum NOT NULL,
    attendance_requirement attendance_requirement_enum NOT NULL DEFAULT 'ALL',
    FOREIGN KEY (collectivity_id) REFERENCES collectivity(id)
);

-- Feature F: attendance per activity
CREATE TYPE attendance_status_enum AS ENUM ('PRESENT', 'ABSENT', 'EXCUSED');

CREATE TABLE activity_attendance (
    activity_id VARCHAR(255) NOT NULL,
    member_id VARCHAR(255) NOT NULL,
    status attendance_status_enum NOT NULL,
    excuse_reason VARCHAR(255),
    PRIMARY KEY (activity_id, member_id),
    FOREIGN KEY (activity_id) REFERENCES activity(id),
    FOREIGN KEY (member_id) REFERENCES member(id)
);