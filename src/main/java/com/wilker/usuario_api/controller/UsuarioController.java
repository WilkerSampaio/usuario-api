package com.wilker.usuario_api.controller;

import com.wilker.usuario_api.infrastructure.dto.in.EnderecoDTORequest;
import com.wilker.usuario_api.infrastructure.dto.in.LoginDTORequest;
import com.wilker.usuario_api.infrastructure.dto.in.TelefoneDTORequest;
import com.wilker.usuario_api.infrastructure.dto.in.UsuarioDTORequest;
import com.wilker.usuario_api.infrastructure.dto.out.EnderecoDTOResponse;
import com.wilker.usuario_api.infrastructure.dto.out.TelefoneDTOResponse;
import com.wilker.usuario_api.infrastructure.dto.out.UsuarioDTOResponse;
import com.wilker.usuario_api.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    @GetMapping
    public ResponseEntity<UsuarioDTOResponse> buscarUsuarioPeloEmail(@RequestParam ("email") String email){
        return ResponseEntity.ok(usuarioService.buscarUsuarioPeloEmail(email));
    }
    @DeleteMapping("/{email}")
    public ResponseEntity<Void> deletaUsuarioPeloEmail(@PathVariable String email){
        usuarioService.deletaUsuarioPeloEmail(email);
        return ResponseEntity.ok().build();
    }
    @PutMapping
    public ResponseEntity<UsuarioDTOResponse> atualizarDadosUsuario(@RequestBody UsuarioDTORequest usuarioDTORequest,
                                                                    @RequestHeader("Authorization") String token){
        return ResponseEntity.ok(usuarioService.atualizarDadosUsuario(usuarioDTORequest, token));
    }
    @PutMapping("/endereco")
    public ResponseEntity<EnderecoDTOResponse> atualizarEndereco(@RequestBody EnderecoDTORequest enderecoDTORequest,
                                                                 @RequestParam("id") Long idEndereco,
                                                                 @RequestHeader("Authorization") String token){
        return ResponseEntity.ok(usuarioService.atualizarEndereco(enderecoDTORequest, idEndereco, token ));
    }
    @PutMapping("/telefone")
    public ResponseEntity<TelefoneDTOResponse> atualizarTelefone(@RequestBody TelefoneDTORequest telefoneDTORequest,
                                                                 @RequestParam ("id") Long idTelefone,
                                                                 @RequestHeader("Authorization") String token){
        return ResponseEntity.ok(usuarioService.atualizarTelefone(telefoneDTORequest, idTelefone, token));
    }
    @PostMapping("/endereco")
    public ResponseEntity<EnderecoDTOResponse> cadastrarEndereco (@RequestBody EnderecoDTORequest enderecoDTORequest,
                                                                  @RequestHeader ("Authorization") String token){
        return ResponseEntity.ok(usuarioService.cadastrarEndereco(enderecoDTORequest,token));
    }
    @PostMapping("/telefone")
    public ResponseEntity<TelefoneDTOResponse> cadastrarTelefone(@RequestBody TelefoneDTORequest telefoneDTORequest,
                                                                 @RequestHeader ("Authorization") String token){
        return ResponseEntity.ok(usuarioService.cadastrarTelefone(telefoneDTORequest,token));
    }
}