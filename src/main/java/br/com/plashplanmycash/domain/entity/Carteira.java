package br.com.plashplanmycash.domain.entity;

import br.com.plashplanmycash.domain.enums.TipoCarteira;
import br.com.plashplanmycash.domain.enums.TipoConta;
import br.com.plashplanmycash.domain.enums.TipoMoeda;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "carteiras")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Carteira {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @Column(nullable = false)
    private String apelido;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoCarteira tipoCarteira;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoConta tipoConta;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoMoeda tipoMoeda;

    @Column(nullable = false, precision = 19, scale = 2)
    private BigDecimal saldo;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime criadoEm;

    @UpdateTimestamp
    @Column(nullable = false)
    private LocalDateTime atualizadoEm;
}
