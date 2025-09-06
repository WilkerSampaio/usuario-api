package com.wilker.usuario_api.infrastructure.dto.out;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ErrorDTOResponse {
    private String mensagem;
}
