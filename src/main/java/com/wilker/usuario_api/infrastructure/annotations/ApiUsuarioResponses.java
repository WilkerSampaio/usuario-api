package com.wilker.usuario_api.infrastructure.annotations;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import java.lang.annotation.*;

@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@ApiResponses({
        @ApiResponse(responseCode = "400", description = "Requisição inválida"),
        @ApiResponse(responseCode = "401", description = "Não autorizado"),
        @ApiResponse(responseCode = "404", description = "Não encontrado"),
        @ApiResponse(responseCode = "409", description = "Conflito"),
        @ApiResponse(responseCode = "500", description = "Erro interno")
})
public @interface ApiUsuarioResponses {
}
