package com.wilker.usuario_api.service;

import com.wilker.usuario_api.infrastructure.converter.UsuarioConverter;
import com.wilker.usuario_api.infrastructure.dto.in.LoginDTORequest;
import com.wilker.usuario_api.infrastructure.dto.in.UsuarioDTORequest;
import com.wilker.usuario_api.infrastructure.dto.out.UsuarioDTOResponse;
import com.wilker.usuario_api.infrastructure.entity.UsuarioEntity;
import com.wilker.usuario_api.infrastructure.exception.ConflictException;
import com.wilker.usuario_api.infrastructure.exception.ResourceNotFoundException;
import com.wilker.usuario_api.infrastructure.repository.EnderecoRepository;
import com.wilker.usuario_api.infrastructure.repository.TelefoneRepository;
import com.wilker.usuario_api.infrastructure.repository.UsuarioRepository;
import com.wilker.usuario_api.infrastructure.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UsuarioService {
    private final UsuarioRepository usuarioRepository;
    private final UsuarioConverter usuarioConverter;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final EnderecoRepository enderecoRepository;
    private final TelefoneRepository telefoneRepository;

    public UsuarioDTOResponse salvarUsuario(UsuarioDTORequest usuarioDTORequest) {
        emailExiste(usuarioDTORequest.getEmail());

        UsuarioEntity usuarioEntity = usuarioConverter.converterParaEntity(usuarioDTORequest);
        usuarioEntity.setSenha(passwordEncoder.encode(usuarioEntity.getSenha()));
        UsuarioEntity UsuarioSalvo = usuarioRepository.save(usuarioEntity);

        return usuarioConverter.converterParaDTO(UsuarioSalvo);
    }

    public void emailExiste(String email) {
        if (verificarEmailExistente(email)) {
            throw new ConflictException("Email já cadastrado!");
        }
    }

    public boolean verificarEmailExistente(String email) {
        return usuarioRepository.existsByEmail(email);
    }

    public String authenticarUsuario(LoginDTORequest loginDTORequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginDTORequest.getEmail(), loginDTORequest.getSenha()));
        return "Bearer " + jwtUtil.generateToken(authentication.getName());
    }

    public UsuarioDTOResponse buscarUsuarioPeloEmail(String email) {
        UsuarioEntity usuario = usuarioRepository.findByEmail(email).orElseThrow(
                () -> new ResourceNotFoundException("Email não encontrado"));

        return usuarioConverter.converterParaDTO(usuario);
    }

    public void deletaUsuarioPeloEmail(String email) {
        buscarUsuarioPeloEmail(email);
        usuarioRepository.deleteByEmail(email);
    }

    public UsuarioDTOResponse atualizarDadosUsuario(UsuarioDTORequest usuarioDTORequest,String token){
        String email = jwtUtil.extractUsername(token.substring(7));
        UsuarioEntity usuarioEntity = usuarioRepository.findByEmail(email).orElseThrow(
                () -> new ResourceNotFoundException("Email não localizado"));

        if(usuarioDTORequest.getSenha() != null){
            usuarioEntity.setSenha(passwordEncoder.encode(usuarioDTORequest.getSenha()));
        }

        usuarioConverter.updateUsuario(usuarioDTORequest, usuarioEntity);
        return usuarioConverter.converterParaDTO(usuarioRepository.save(usuarioEntity));
    }
}