CREATE TABLE contacts
(
    id                  BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    name                VARCHAR(255)                            NOT NULL,
    date_of_birth       VARCHAR(255)                            NOT NULL,
    phone               VARCHAR(255)                            NOT NULL,
    address             VARCHAR(255)                            NOT NULL,
    credit_card_number  VARCHAR(255)                            NOT NULL,
    credit_card_network VARCHAR(255)                            NOT NULL,
    email               VARCHAR(255)                            NOT NULL,
    user_id             BIGINT                                  NOT NULL,
    imported_file_id    BIGINT                                  NOT NULL,
    created_at          TIMESTAMP WITHOUT TIME ZONE,
    CONSTRAINT pk_contacts PRIMARY KEY (id)
);

CREATE TABLE falied_contacts
(
    id                  BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    name                VARCHAR(255),
    date_of_birth       VARCHAR(255),
    phone               VARCHAR(255),
    address             VARCHAR(255),
    credit_card_number  VARCHAR(255),
    credit_card_network VARCHAR(255),
    email               VARCHAR(255),
    errors              VARCHAR(255),
    imported_file_id    BIGINT                                  NOT NULL,
    user_id             BIGINT                                  NOT NULL,
    created_at          TIMESTAMP WITHOUT TIME ZONE,
    CONSTRAINT pk_falied_contacts PRIMARY KEY (id)
);

CREATE TABLE file_statues
(
    id   INTEGER GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    name VARCHAR(255)                             NOT NULL,
    CONSTRAINT pk_file_statues PRIMARY KEY (id)
);

CREATE TABLE imported_files
(
    id   BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    name VARCHAR(255)                            NOT NULL,
    CONSTRAINT pk_imported_files PRIMARY KEY (id)
);

CREATE TABLE users
(
    id       BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    email    VARCHAR(255)                            NOT NULL,
    password VARCHAR(255)                            NOT NULL,
    CONSTRAINT pk_users PRIMARY KEY (id)
);

ALTER TABLE users
    ADD CONSTRAINT uc_users_email UNIQUE (email);

ALTER TABLE contacts
    ADD CONSTRAINT FK_CONTACTS_ON_IMPORTEDFILE FOREIGN KEY (imported_file_id) REFERENCES imported_files (id);

ALTER TABLE contacts
    ADD CONSTRAINT FK_CONTACTS_ON_USER FOREIGN KEY (user_id) REFERENCES users (id);

ALTER TABLE falied_contacts
    ADD CONSTRAINT FK_FALIED_CONTACTS_ON_IMPORTEDFILE FOREIGN KEY (imported_file_id) REFERENCES imported_files (id);

ALTER TABLE falied_contacts
    ADD CONSTRAINT FK_FALIED_CONTACTS_ON_USER FOREIGN KEY (user_id) REFERENCES users (id);