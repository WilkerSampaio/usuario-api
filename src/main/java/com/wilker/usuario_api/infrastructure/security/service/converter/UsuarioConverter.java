package com.wilker.usuario_api.infrastructure.security.service.converter;

import com.wilker.usuario.infrastructure.dtos.in.EnderecoDTORequest;
import com.wilker.usuario.infrastructure.dtos.in.TelefoneDTORequest;
import com.wilker.usuario.infrastructure.dtos.in.UsuarioDTORequest;
import com.wilker.usuario.infrastructure.dtos.out.EnderecoDTOResponse;
import com.wilker.usuario.infrastructure.dtos.out.TelefoneDTOResponse;
import com.wilker.usuario.infrastructure.dtos.out.UsuarioDTOResponse;
import com.wilker.usuario.infrastructure.entity.Endereco;
import com.wilker.usuario.infrastructure.entity.Telefone;
import com.wilker.usuario.infrastructure.entity.Usuario;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UsuarioConverter {

    // ----------- CONVERSÃO DE DTO PARA ENTIDADE (Entrada) -----------

    public Usuario converterParaEntity(UsuarioDTORequest usuarioDTORequest) {
        return Usuario.builder()
                .nome(usuarioDTORequest.getNome())
                .email(usuarioDTORequest.getEmail())
                .senha(usuarioDTORequest.getSenha())
                .enderecos(usuarioDTORequest.getEnderecos() != null ? converterListaEnderecosParaEntity(usuarioDTORequest.getEnderecos()): null)
                .telefones(usuarioDTORequest.getTelefones() != null ? converterListaTelefonesParaEntity(usuarioDTORequest.getTelefones()) : null)
                .build();
    }

    public Endereco converterParaEntity(EnderecoDTORequest enderecoDTORequest) {
        return Endereco.builder()
                .rua(enderecoDTORequest.getRua())
                .numero(enderecoDTORequest.getNumero())
                .complemento(enderecoDTORequest.getComplemento())
                .estado(enderecoDTORequest.getEstado())
                .cidade(enderecoDTORequest.getCidade())
                .cep(enderecoDTORequest.getCep())
                .build();
    }

    public List<Endereco> converterListaEnderecosParaEntity(List<EnderecoDTORequest> enderecoDTORequests) {
        return enderecoDTORequests.stream()
                .map(this::converterParaEntity)
                .toList();
    }

    public Telefone converterParaEntity(TelefoneDTORequest telefoneDTORequest) {
        return Telefone.builder()
                .ddd(telefoneDTORequest.getDdd())
                .numero(telefoneDTORequest.getNumero())
                .build();
    }

    public List<Telefone> converterListaTelefonesParaEntity(List<TelefoneDTORequest> telefoneDTORequests) {
        return telefoneDTORequests.stream()
                .map(this::converterParaEntity)
                .toList();
    }

    // ----------- CONVERSÃO DE ENTIDADE PARA DTO (Saída) -----------

    public UsuarioDTOResponse converterParaDTO(Usuario usuarioEntity) {
        return UsuarioDTOResponse.builder()
                .nome(usuarioEntity.getNome())
                .email(usuarioEntity.getEmail())
                .senha(usuarioEntity.getSenha())
                .enderecos(usuarioEntity.getEnderecos() != null ? converterListaEnderecosParaDTO(usuarioEntity.getEnderecos()) : null)
                .telefones(usuarioEntity.getEnderecos() != null ? converterListaTelefonesParaDTO(usuarioEntity.getTelefones()) : null)
                .build();
    }

    public EnderecoDTOResponse converterParaDTO(Endereco enderecoEntity) {
        return EnderecoDTOResponse.builder()
                .id(enderecoEntity.getId())
                .rua(enderecoEntity.getRua())
                .numero(enderecoEntity.getNumero())
                .complemento(enderecoEntity.getComplemento())
                .estado(enderecoEntity.getEstado())
                .cidade(enderecoEntity.getCidade())
                .cep(enderecoEntity.getCep())
                .build();
    }

    public List<EnderecoDTOResponse> converterListaEnderecosParaDTO(List<Endereco> listaEnderecosEntity) {
        return listaEnderecosEntity.stream()
                .map(this::converterParaDTO)
                .toList();
    }

    public TelefoneDTOResponse converterParaDTO(Telefone telefoneEntity) {
        return TelefoneDTOResponse.builder()
                .id(telefoneEntity.getId())
                .ddd(telefoneEntity.getDdd())
                .numero(telefoneEntity.getNumero())
                .build();
    }

    public List<TelefoneDTOResponse> converterListaTelefonesParaDTO(List<Telefone> listaTelefoneEntity) {
        return listaTelefoneEntity.stream()
                .map(this::converterParaDTO)
                .toList();
    }

    // ----------- Updates Usuário, Endereco e Telefone -----------

    public Usuario updateUsuario(UsuarioDTORequest usuarioDTORequest, Usuario usuarioEntity){
        return Usuario.builder()
                .nome(usuarioDTORequest.getNome() != null ? usuarioDTORequest.getNome() : usuarioEntity.getNome())
                .id(usuarioEntity.getId())
                .senha(usuarioDTORequest.getSenha() != null ? usuarioDTORequest.getSenha() : usuarioEntity.getSenha())
                .email(usuarioDTORequest.getEmail() != null ? usuarioDTORequest.getEmail() : usuarioEntity.getEmail())
                .enderecos(usuarioEntity.getEnderecos())
                .telefones(usuarioEntity.getTelefones())
                .build();
    }

    public Endereco updateEndereco(EnderecoDTORequest enderecoDTORequest, Endereco enderecoEntity){
        return Endereco.builder()
                .id(enderecoEntity.getId())
                .rua(enderecoDTORequest.getRua() != null ? enderecoDTORequest.getRua() : enderecoEntity.getRua())
                .numero(enderecoDTORequest.getNumero() != null ? enderecoDTORequest.getNumero() : enderecoEntity.getNumero())
                .complemento(enderecoDTORequest.getComplemento() != null ? enderecoDTORequest.getComplemento() : enderecoEntity.getComplemento())
                .estado(enderecoDTORequest.getEstado() != null ? enderecoDTORequest.getEstado() : enderecoEntity.getEstado())
                .cidade(enderecoDTORequest.getCidade() != null ? enderecoDTORequest.getCidade() : enderecoEntity.getCidade())
                .cep(enderecoDTORequest.getCep() != null ? enderecoDTORequest.getCep() : enderecoEntity.getCep())
                .build();
    }

    public Telefone updateTelefone(TelefoneDTORequest telefoneDTORequest, Telefone telefoneEntity){
        return Telefone.builder()
                .id(telefoneEntity.getId())
                .ddd(telefoneDTORequest.getDdd() != null ? telefoneDTORequest.getDdd() : telefoneEntity.getDdd())
                .numero(telefoneDTORequest.getNumero() != null ? telefoneDTORequest.getNumero() : telefoneEntity.getNumero())
                .build();
    }


    // ----------- Cadastrar novo Endereco e Telefone -----------

    public Endereco NovoEnderecoParaEntity(EnderecoDTORequest enderecoDTORequest, Long idUsuario){
        return Endereco.builder()
                .rua(enderecoDTORequest.getRua())
                .cidade(enderecoDTORequest.getCidade())
                .cep(enderecoDTORequest.getCep())
                .complemento(enderecoDTORequest.getComplemento())
                .estado(enderecoDTORequest.getEstado())
                .numero(enderecoDTORequest.getNumero())
                .usuario_id(idUsuario)
                .build();
    }

    public Telefone NovoTelefoneParaEntity(TelefoneDTORequest telefoneDTORequest, Long idUsuario){
        return Telefone.builder()
                .ddd(telefoneDTORequest.getDdd())
                .numero(telefoneDTORequest.getNumero())
                .usuario_id(idUsuario)
                .build();
    }
}
