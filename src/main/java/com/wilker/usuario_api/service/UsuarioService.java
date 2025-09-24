package com.wilker.usuario_api.service;

import com.wilker.usuario_api.infrastructure.converter.UsuarioConverter;
import com.wilker.usuario_api.infrastructure.dto.in.EnderecoDTORequest;
import com.wilker.usuario_api.infrastructure.dto.in.LoginDTORequest;
import com.wilker.usuario_api.infrastructure.dto.in.TelefoneDTORequest;
import com.wilker.usuario_api.infrastructure.dto.in.UsuarioDTORequest;
import com.wilker.usuario_api.infrastructure.dto.out.EnderecoDTOResponse;
import com.wilker.usuario_api.infrastructure.dto.out.TelefoneDTOResponse;
import com.wilker.usuario_api.infrastructure.dto.out.UsuarioDTOResponse;
import com.wilker.usuario_api.infrastructure.entity.EnderecoEntity;
import com.wilker.usuario_api.infrastructure.entity.TelefoneEntity;
import com.wilker.usuario_api.infrastructure.entity.UsuarioEntity;
import com.wilker.usuario_api.infrastructure.exception.ConflictException;
import com.wilker.usuario_api.infrastructure.exception.ResourceNotFoundException;
import com.wilker.usuario_api.infrastructure.exception.UnauthorizedException;
import com.wilker.usuario_api.infrastructure.repository.EnderecoRepository;
import com.wilker.usuario_api.infrastructure.repository.TelefoneRepository;
import com.wilker.usuario_api.infrastructure.repository.UsuarioRepository;
import com.wilker.usuario_api.infrastructure.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
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
        try{
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginDTORequest.getEmail(), loginDTORequest.getSenha()));
            return "Bearer " + jwtUtil.generateToken(authentication.getName());
        }catch(UnauthorizedException e){
            throw new UnauthorizedException("Credenciais inválidas. Verifique seu email e senha");
        }

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

        UsuarioEntity usuarioAlterado = usuarioConverter.updateUsuario(usuarioDTORequest, usuarioEntity);
        return usuarioConverter.converterParaDTO(usuarioRepository.save(usuarioAlterado));
    }

    public EnderecoDTOResponse atualizarEndereco(EnderecoDTORequest enderecoDTORequest, Long idEndereco, String token){
        String email = jwtUtil.extractUsername(token.substring(7));
        UsuarioEntity usuarioEntity = usuarioRepository.findByEmail(email).orElseThrow(
                () -> new ResourceNotFoundException("Email não localizado"));

        EnderecoEntity enderecoEntity = enderecoRepository.findById(idEndereco).orElseThrow(
                () -> new ResourceNotFoundException("ID não encontrado " + idEndereco));

        EnderecoEntity enderecoAlteradoEntity = usuarioConverter.updateEndereco(enderecoDTORequest, enderecoEntity, usuarioEntity.getId());

        return usuarioConverter.converterParaDTO(enderecoRepository.save(enderecoAlteradoEntity));
    }

    public TelefoneDTOResponse atualizarTelefone(TelefoneDTORequest telefoneDTORequest, Long idTelefone, String token){
        String email = jwtUtil.extractUsername(token.substring(7));
        UsuarioEntity usuarioEntity = usuarioRepository.findByEmail(email).orElseThrow(
                () -> new ResourceNotFoundException("Email não localizado"));

        TelefoneEntity telefoneEntity = telefoneRepository.findById(idTelefone).orElseThrow(
                ()-> new ResourceNotFoundException("ID não encontrado " + idTelefone));
        TelefoneEntity telefoneAlteradoEntity = usuarioConverter.updateTelefone(telefoneDTORequest, telefoneEntity, usuarioEntity.getId());

        return usuarioConverter.converterParaDTO(telefoneRepository.save(telefoneAlteradoEntity));
    }
    public EnderecoDTOResponse cadastrarEndereco(EnderecoDTORequest enderecoDTORequest, String token){
        String email = jwtUtil.extractUsername(token.substring(7));
        UsuarioEntity usuarioEntity = usuarioRepository.findByEmail(email).orElseThrow(
                () -> new ResourceNotFoundException("Email não encontrado"));

        EnderecoEntity enderecoEntity = usuarioConverter.NovoEnderecoParaEntity(enderecoDTORequest, usuarioEntity.getId());
        return usuarioConverter.converterParaDTO(enderecoRepository.save(enderecoEntity));
    }
    public TelefoneDTOResponse cadastrarTelefone(TelefoneDTORequest telefoneDTORequest, String token){
        String email = jwtUtil.extractUsername(token.substring(7));
        UsuarioEntity usuarioEntity = usuarioRepository.findByEmail(email).orElseThrow(
                () -> new ResourceNotFoundException("Email não encontrado"));

        TelefoneEntity telefoneEntity = usuarioConverter.NovoTelefoneParaEntity(telefoneDTORequest, usuarioEntity.getId());
        return usuarioConverter.converterParaDTO(telefoneRepository.save(telefoneEntity));
    }
}