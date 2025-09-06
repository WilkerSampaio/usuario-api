package com.wilker.usuario_api.infrastructure.dto.out;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class TelefoneDTOResponse {

    private Long id;
    private String ddd;
    private String numero;


}
