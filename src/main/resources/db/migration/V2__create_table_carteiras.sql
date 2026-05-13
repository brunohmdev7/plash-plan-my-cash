CREATE TABLE carteiras (
    id             BIGINT          NOT NULL AUTO_INCREMENT,
    usuario_id     BIGINT          NOT NULL,
    apelido        VARCHAR(255)    NOT NULL,
    tipo_carteira  VARCHAR(50)     NOT NULL,
    tipo_conta     VARCHAR(50)     NOT NULL,
    tipo_moeda     VARCHAR(10)     NOT NULL,
    saldo          DECIMAL(19, 2)  NOT NULL,
    criado_em      DATETIME(6)     NOT NULL,
    atualizado_em  DATETIME(6)     NOT NULL,

    PRIMARY KEY (id),
    CONSTRAINT uq_carteiras_usuario_apelido UNIQUE (usuario_id, apelido),
    CONSTRAINT fk_carteiras_usuario FOREIGN KEY (usuario_id) REFERENCES usuarios (id)
);