package com.wilker.usuario_api.infrastructure.security.service;

import com.wilker.usuario.infrastructure.dtos.in.EnderecoDTORequest;
import com.wilker.usuario.infrastructure.dtos.in.LoginDTORequest;
import com.wilker.usuario.infrastructure.dtos.in.TelefoneDTORequest;
import com.wilker.usuario.infrastructure.dtos.in.UsuarioDTORequest;
import com.wilker.usuario.infrastructure.dtos.out.EnderecoDTOResponse;
import com.wilker.usuario.infrastructure.dtos.out.TelefoneDTOResponse;
import com.wilker.usuario.infrastructure.dtos.out.UsuarioDTOResponse;
import com.wilker.usuario.infrastructure.entity.Endereco;
import com.wilker.usuario.infrastructure.entity.Telefone;
import com.wilker.usuario.infrastructure.entity.Usuario;
import com.wilker.usuario.infrastructure.exceptions.ConflictException;
import com.wilker.usuario.infrastructure.exceptions.ResourceNotFoundException;
import com.wilker.usuario.infrastructure.repository.EnderecoRepository;
import com.wilker.usuario.infrastructure.repository.TelefoneRepository;
import com.wilker.usuario.infrastructure.repository.UsuarioRepository;
import com.wilker.usuario.infrastructure.security.JwtUtil;
import com.wilker.usuario.service.converter.UsuarioConverter;
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

    public UsuarioDTOResponse salvarUsuario(UsuarioDTORequest usuarioDTORequest){
        emailExiste(usuarioDTORequest.getEmail());

        Usuario usuarioEntity = usuarioConverter.converterParaEntity(usuarioDTORequest);
        usuarioEntity.setSenha(passwordEncoder.encode(usuarioEntity.getSenha()));
        Usuario UsuarioSalvo = usuarioRepository.save(usuarioEntity);

        return usuarioConverter.converterParaDTO(UsuarioSalvo);
    }

    public void emailExiste(String email){
        if(verificarEmailExistente(email)){
            throw new ConflictException("Email já cadastrado!");
        }
    }

    public boolean verificarEmailExistente(String email){
        return usuarioRepository.existsByEmail(email);
    }

    public String authenticarUsuario(LoginDTORequest loginDTORequest){
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginDTORequest.getEmail(), loginDTORequest.getSenha()));
          return "Bearer " + jwtUtil.generateToken(authentication.getName());
    }

    public UsuarioDTOResponse buscarUsuarioPeloEmail(String email){
        Usuario usuario = usuarioRepository.findByEmail(email).orElseThrow(
                ()->new ResourceNotFoundException("Email não encontrado"));

        return usuarioConverter.converterParaDTO(usuario);
    }

    public void deletaUsuarioPeloEmail(String email){
        buscarUsuarioPeloEmail(email);
        usuarioRepository.deleteByEmail(email);
    }

    public UsuarioDTOResponse atualizarDadosUsuario(UsuarioDTORequest usuarioDTORequest,String token){
        String email = jwtUtil.extractUsername(token.substring(7));
        Usuario usuarioEntity = usuarioRepository.findByEmail(email).orElseThrow(
                () -> new ResourceNotFoundException("Email não localizado"));

        if(usuarioDTORequest.getSenha() != null){
            usuarioEntity.setSenha(passwordEncoder.encode(usuarioDTORequest.getSenha()));
        }

         usuarioConverter.updateUsuario(usuarioDTORequest, usuarioEntity);
        return usuarioConverter.converterParaDTO(usuarioRepository.save(usuarioEntity));
    }

    public EnderecoDTOResponse atualizarEndereco(Long idEndereco, EnderecoDTORequest enderecoDTORequest){
        Endereco enderecoEntity = enderecoRepository.findById(idEndereco).orElseThrow(
                () -> new ResourceNotFoundException("ID não encontrado " + idEndereco));
        Endereco enderecoAlteradoEntity = usuarioConverter.updateEndereco(enderecoDTORequest, enderecoEntity);

        return usuarioConverter.converterParaDTO(enderecoRepository.save(enderecoAlteradoEntity));
    }

    public TelefoneDTOResponse atualizarTelefone(Long idTelefone, TelefoneDTORequest telefoneDTORequest){
        Telefone telefone = telefoneRepository.findById(idTelefone).orElseThrow(
                ()-> new ResourceNotFoundException("ID não encontrado " + idTelefone));
        Telefone telefoneAlteradoEntity = usuarioConverter.updateTelefone(telefoneDTORequest,telefone);

        return usuarioConverter.converterParaDTO(telefoneRepository.save(telefoneAlteradoEntity));

    }
    public EnderecoDTOResponse cadastrarEndereco(EnderecoDTORequest enderecoDTORequest, String token){
        String email = jwtUtil.extractUsername(token.substring(7));
        Usuario usuarioEntity = usuarioRepository.findByEmail(email).orElseThrow(
                () -> new ResourceNotFoundException("Email não encontrado"));

        Endereco enderecoEntity = usuarioConverter.NovoEnderecoParaEntity(enderecoDTORequest, usuarioEntity.getId());
        return usuarioConverter.converterParaDTO(enderecoRepository.save(enderecoEntity));
    }

    public TelefoneDTOResponse cadastrarTelefone(TelefoneDTORequest telefoneDTORequest, String token){
        String email = jwtUtil.extractUsername(token.substring(7));
        Usuario usuarioEntity = usuarioRepository.findByEmail(email).orElseThrow(
                () -> new ResourceNotFoundException("Email não encontrado"));

        Telefone telefoneEntity = usuarioConverter.NovoTelefoneParaEntity(telefoneDTORequest, usuarioEntity.getId());
        return usuarioConverter.converterParaDTO(telefoneRepository.save(telefoneEntity));
    }




}
