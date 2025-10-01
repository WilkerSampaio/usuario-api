package com.wilker.usuario_api.controller;

import com.wilker.usuario_api.infrastructure.annotations.ApiUsuarioResponses;
import com.wilker.usuario_api.infrastructure.dto.in.EnderecoDTORequest;
import com.wilker.usuario_api.infrastructure.dto.in.LoginDTORequest;
import com.wilker.usuario_api.infrastructure.dto.in.TelefoneDTORequest;
import com.wilker.usuario_api.infrastructure.dto.in.UsuarioDTORequest;
import com.wilker.usuario_api.infrastructure.dto.out.EnderecoDTOResponse;
import com.wilker.usuario_api.infrastructure.dto.out.TelefoneDTOResponse;
import com.wilker.usuario_api.infrastructure.dto.out.UsuarioDTOResponse;
import com.wilker.usuario_api.infrastructure.dto.out.ViaCepDTOResponse;
import com.wilker.usuario_api.infrastructure.security.SecurityConfig;
import com.wilker.usuario_api.service.UsuarioService;
import com.wilker.usuario_api.service.ViaCepService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/usuario")
@RequiredArgsConstructor
@Tag(name = "Usuários", description = "Cadastro e Login de Usuários")
@SecurityRequirement(name = SecurityConfig.SECURITY_SCHEME)

public class UsuarioController {
    private final UsuarioService usuarioService;
    private final ViaCepService viaCepService;

    @PostMapping
    @Operation(summary = "Salva Usuários", description = "Cria um novo usuário")
    @ApiUsuarioResponses
    public ResponseEntity<UsuarioDTOResponse> registraUsuario(@RequestBody UsuarioDTORequest usuarioDTORequest) {
        return ResponseEntity.ok(usuarioService.salvarUsuario(usuarioDTORequest));
    }
    @PostMapping("/login")
    @Operation(summary = "Login Usuário", description = "Authentica usuário")
    @ApiUsuarioResponses
    public String authenticarUsuario(@RequestBody LoginDTORequest loginDTORequest){
        return usuarioService.authenticarUsuario(loginDTORequest);
    }
    @GetMapping
    @Operation(summary = "Buscar Dados do Usuário por Email", description = "Buscar dados do usuário por email ")
    @ApiUsuarioResponses
    @SecurityRequirement(name = SecurityConfig.SECURITY_SCHEME)
    public ResponseEntity<UsuarioDTOResponse> buscarUsuarioPeloEmail(@RequestParam ("email") String email){
        return ResponseEntity.ok(usuarioService.buscarUsuarioPeloEmail(email));
    }
    @DeleteMapping("/{email}")
    @Operation(summary = "Deleta um Usuário", description = "Deleta um usuário por email")
    @ApiUsuarioResponses
    @SecurityRequirement(name = SecurityConfig.SECURITY_SCHEME)
    public ResponseEntity<Void> deletaUsuarioPeloEmail(@PathVariable String email){
        usuarioService.deletaUsuarioPeloEmail(email);
        return ResponseEntity.ok().build();
    }
    @PutMapping
    @Operation(summary = "Atualiza Dados do Usuário", description = "Atualiza dados de um usuário")
    @ApiUsuarioResponses
    @SecurityRequirement(name = SecurityConfig.SECURITY_SCHEME)
    public ResponseEntity<UsuarioDTOResponse> atualizarDadosUsuario(@RequestBody UsuarioDTORequest usuarioDTORequest,
                                                                    @RequestHeader("Authorization") String token){
        return ResponseEntity.ok(usuarioService.atualizarDadosUsuario(usuarioDTORequest, token));
    }
    @PutMapping("/endereco")
    @Operation(summary = "Atualiza Endereço do Usuário", description = "Atualiza um endereço do usuário")
    @ApiUsuarioResponses
    @SecurityRequirement(name = SecurityConfig.SECURITY_SCHEME)
    public ResponseEntity<EnderecoDTOResponse> atualizarEndereco(@RequestBody EnderecoDTORequest enderecoDTORequest,
                                                                 @RequestParam("id") Long idEndereco,
                                                                 @RequestHeader("Authorization") String token){
        return ResponseEntity.ok(usuarioService.atualizarEndereco(enderecoDTORequest, idEndereco, token ));
    }
    @PutMapping("/telefone")
    @Operation(summary = "Atualiza Telefone do Usuário", description = "Atualiza um telefone do usuário")
    @ApiUsuarioResponses
    @SecurityRequirement(name = SecurityConfig.SECURITY_SCHEME)
    public ResponseEntity<TelefoneDTOResponse> atualizarTelefone(@RequestBody TelefoneDTORequest telefoneDTORequest,
                                                                 @RequestParam ("id") Long idTelefone,
                                                                 @RequestHeader("Authorization") String token){
        return ResponseEntity.ok(usuarioService.atualizarTelefone(telefoneDTORequest, idTelefone, token));
    }
    @PostMapping("/endereco")
    @Operation(summary = "Salva Novo Endereço do Usuário", description = "Salva endereço de usuário")
    @ApiUsuarioResponses
    @SecurityRequirement(name = SecurityConfig.SECURITY_SCHEME)
    public ResponseEntity<EnderecoDTOResponse> cadastrarEndereco (@RequestBody EnderecoDTORequest enderecoDTORequest,
                                                                  @RequestHeader ("Authorization") String token){
        return ResponseEntity.ok(usuarioService.cadastrarEndereco(enderecoDTORequest,token));
    }
    @PostMapping("/telefone")
    @Operation(summary = "Salva Novo Telefone do Usuário", description = "Salva telefone de usuário")
    @ApiUsuarioResponses
    @SecurityRequirement(name = SecurityConfig.SECURITY_SCHEME)
    public ResponseEntity<TelefoneDTOResponse> cadastrarTelefone(@RequestBody TelefoneDTORequest telefoneDTORequest,
                                                                 @RequestHeader ("Authorization") String token){
        return ResponseEntity.ok(usuarioService.cadastrarTelefone(telefoneDTORequest,token));
    }
    @GetMapping("/endereco/{cep}")
    @Operation(summary = "Busca Endereço pelo CEP", description = "Busca dados de endereço recebendo um CEP")
    public ResponseEntity<ViaCepDTOResponse> buscarDadosCep (@PathVariable ("cep") String cep){
        return ResponseEntity.ok(viaCepService.buscaDadosEndereco(cep));

    }
}