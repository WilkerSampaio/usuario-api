package com.wilker.usuario_api.controller;

import com.wilker.usuario_api.infrastructure.dto.in.LoginDTORequest;
import com.wilker.usuario_api.infrastructure.dto.in.UsuarioDTORequest;
import com.wilker.usuario_api.infrastructure.dto.out.UsuarioDTOResponse;
import com.wilker.usuario_api.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/usuario")
@RequiredArgsConstructor
public class UsuarioController {
    private final UsuarioService usuarioService;

    @PostMapping
    public ResponseEntity<UsuarioDTOResponse> registraUsuario(@RequestBody UsuarioDTORequest usuarioDTORequest) {
        return ResponseEntity.ok(usuarioService.salvarUsuario(usuarioDTORequest));
    }
    @PostMapping("/login")
    public String authenticarUsuario(@RequestBody LoginDTORequest loginDTORequest){
        return usuarioService.authenticarUsuario(loginDTORequest);
    }


}