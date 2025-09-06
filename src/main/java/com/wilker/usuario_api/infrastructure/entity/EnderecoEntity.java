package com.wilker.usuario_api.infrastructure.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "endereco")
@Builder

public class EnderecoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "rua", length = 300)
    private String rua;

    @Column(name = "numero", length = 10)
    private String numero;

    @Column(name = "complemento", length = 100)
    private String complemento;

    @Column(name = "estado", length = 2)
    private String estado;

    @Column(name = "cidade", length = 100)
    private String cidade;

    @Column(name = "cep", length = 9)
    private String cep;

    @Column(name = "usuario_id")
    private UsuarioEntity usuario_id;


}
