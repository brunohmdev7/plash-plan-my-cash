CREATE TABLE planejamentos (
    id             BIGINT          NOT NULL AUTO_INCREMENT,
    usuario_id     BIGINT          NOT NULL,
    tipo           VARCHAR(50)     NOT NULL,
    nome           VARCHAR(255)    NOT NULL,
    valor_meta     DECIMAL(19, 2)  NOT NULL,
    valor_atual    DECIMAL(19, 2)  NOT NULL,
    prazo_inicio   DATE            NOT NULL,
    prazo_fim      DATE            NOT NULL,
    moeda          VARCHAR(10)     NOT NULL,
    criado_em      DATETIME(6)     NOT NULL,
    atualizado_em  DATETIME(6)     NOT NULL,

    PRIMARY KEY (id),
    CONSTRAINT fk_planejamentos_usuario FOREIGN KEY (usuario_id) REFERENCES usuarios (id)
);