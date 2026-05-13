CREATE TABLE usuarios (
    id           BIGINT          NOT NULL AUTO_INCREMENT,
    nome         VARCHAR(255)    NOT NULL,
    email        VARCHAR(255)    NOT NULL,
    senha        VARCHAR(255)    NOT NULL,
    criado_em    DATETIME(6)     NOT NULL,
    atualizado_em DATETIME(6)   NOT NULL,

    PRIMARY KEY (id),
    CONSTRAINT uq_usuarios_email UNIQUE (email)
);
