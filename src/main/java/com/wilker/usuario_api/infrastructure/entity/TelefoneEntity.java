package com.wilker.usuario_api.infrastructure.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "telefone")
@Builder

public class TelefoneEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "ddd", length = 3)
    private String ddd;

    @Column(name = "numero", length = 9)
    private String numero;

    @ManyToOne(fetch = FetchType.LAZY)
    @Column(name = "usuario_id", nullable = false)
    private UsuarioEntity usuarioEntity;
}
