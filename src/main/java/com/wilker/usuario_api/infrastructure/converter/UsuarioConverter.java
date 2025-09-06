package com.wilker.usuario_api.infrastructure.converter;


import com.wilker.usuario_api.infrastructure.dto.in.EnderecoDTORequest;
import com.wilker.usuario_api.infrastructure.dto.in.TelefoneDTORequest;
import com.wilker.usuario_api.infrastructure.dto.in.UsuarioDTORequest;
import com.wilker.usuario_api.infrastructure.dto.out.EnderecoDTOResponse;
import com.wilker.usuario_api.infrastructure.dto.out.TelefoneDTOResponse;
import com.wilker.usuario_api.infrastructure.dto.out.UsuarioDTOResponse;
import com.wilker.usuario_api.infrastructure.entity.EnderecoEntity;
import com.wilker.usuario_api.infrastructure.entity.TelefoneEntity;
import com.wilker.usuario_api.infrastructure.entity.UsuarioEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UsuarioConverter {

    // ----------- CONVERSÃO DE DTO PARA ENTIDADE (Entrada) -----------

    public UsuarioEntity converterParaEntity(UsuarioDTORequest usuarioDTORequest) {
        return UsuarioEntity.builder()
                .nome(usuarioDTORequest.getNome())
                .email(usuarioDTORequest.getEmail())
                .senha(usuarioDTORequest.getSenha())
                .enderecos(usuarioDTORequest.getEnderecos() != null ? converterListaEnderecosParaEntity(usuarioDTORequest.getEnderecos()): null)
                .telefones(usuarioDTORequest.getTelefones() != null ? converterListaTelefonesParaEntity(usuarioDTORequest.getTelefones()) : null)
                .build();
    }

    public EnderecoEntity converterParaEntity(EnderecoDTORequest enderecoDTORequest) {
        return EnderecoEntity.builder()
                .rua(enderecoDTORequest.getRua())
                .numero(enderecoDTORequest.getNumero())
                .complemento(enderecoDTORequest.getComplemento())
                .estado(enderecoDTORequest.getEstado())
                .cidade(enderecoDTORequest.getCidade())
                .cep(enderecoDTORequest.getCep())
                .build();
    }

    public List<EnderecoEntity> converterListaEnderecosParaEntity(List<EnderecoDTORequest> enderecoDTORequests) {
        return enderecoDTORequests.stream()
                .map(this::converterParaEntity)
                .toList();
    }

    public TelefoneEntity converterParaEntity(TelefoneDTORequest telefoneDTORequest) {
        return TelefoneEntity.builder()
                .ddd(telefoneDTORequest.getDdd())
                .numero(telefoneDTORequest.getNumero())
                .build();
    }

    public List<TelefoneEntity> converterListaTelefonesParaEntity(List<TelefoneDTORequest> telefoneDTORequests) {
        return telefoneDTORequests.stream()
                .map(this::converterParaEntity)
                .toList();
    }

    // ----------- CONVERSÃO DE ENTIDADE PARA DTO (Saída) -----------

    public UsuarioDTOResponse converterParaDTO(UsuarioEntity usuarioEntity) {
        return UsuarioDTOResponse.builder()
                .nome(usuarioEntity.getNome())
                .email(usuarioEntity.getEmail())
                .senha(usuarioEntity.getSenha())
                .enderecos(usuarioEntity.getEnderecos() != null ? converterListaEnderecosParaDTO(usuarioEntity.getEnderecos()) : null)
                .telefones(usuarioEntity.getEnderecos() != null ? converterListaTelefonesParaDTO(usuarioEntity.getTelefones()) : null)
                .build();
    }

    public EnderecoDTOResponse converterParaDTO(EnderecoEntity enderecoEntity) {
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

    public List<EnderecoDTOResponse> converterListaEnderecosParaDTO(List<EnderecoEntity> listaEnderecosEntity) {
        return listaEnderecosEntity.stream()
                .map(this::converterParaDTO)
                .toList();
    }

    public TelefoneDTOResponse converterParaDTO(TelefoneEntity telefoneEntity) {
        return TelefoneDTOResponse.builder()
                .id(telefoneEntity.getId())
                .ddd(telefoneEntity.getDdd())
                .numero(telefoneEntity.getNumero())
                .build();
    }

    public List<TelefoneDTOResponse> converterListaTelefonesParaDTO(List<TelefoneEntity> listaTelefoneEntity) {
        return listaTelefoneEntity.stream()
                .map(this::converterParaDTO)
                .toList();
    }

    // ----------- Updates Usuário, Endereco e Telefone -----------

    public UsuarioEntity updateUsuario(UsuarioDTORequest usuarioDTORequest, UsuarioEntity usuarioEntity){
        return UsuarioEntity.builder()
                .nome(usuarioDTORequest.getNome() != null ? usuarioDTORequest.getNome() : usuarioEntity.getNome())
                .id(usuarioEntity.getId())
                .senha(usuarioDTORequest.getSenha() != null ? usuarioDTORequest.getSenha() : usuarioEntity.getSenha())
                .email(usuarioDTORequest.getEmail() != null ? usuarioDTORequest.getEmail() : usuarioEntity.getEmail())
                .enderecos(usuarioEntity.getEnderecos())
                .telefones(usuarioEntity.getTelefones())
                .build();
    }

    public EnderecoEntity updateEndereco(EnderecoDTORequest enderecoDTORequest, EnderecoEntity enderecoEntity){
        return EnderecoEntity.builder()
                .id(enderecoEntity.getId())
                .rua(enderecoDTORequest.getRua() != null ? enderecoDTORequest.getRua() : enderecoEntity.getRua())
                .numero(enderecoDTORequest.getNumero() != null ? enderecoDTORequest.getNumero() : enderecoEntity.getNumero())
                .complemento(enderecoDTORequest.getComplemento() != null ? enderecoDTORequest.getComplemento() : enderecoEntity.getComplemento())
                .estado(enderecoDTORequest.getEstado() != null ? enderecoDTORequest.getEstado() : enderecoEntity.getEstado())
                .cidade(enderecoDTORequest.getCidade() != null ? enderecoDTORequest.getCidade() : enderecoEntity.getCidade())
                .cep(enderecoDTORequest.getCep() != null ? enderecoDTORequest.getCep() : enderecoEntity.getCep())
                .build();
    }

    public TelefoneEntity updateTelefone(TelefoneDTORequest telefoneDTORequest, TelefoneEntity telefoneEntity){
        return TelefoneEntity.builder()
                .id(telefoneEntity.getId())
                .ddd(telefoneDTORequest.getDdd() != null ? telefoneDTORequest.getDdd() : telefoneEntity.getDdd())
                .numero(telefoneDTORequest.getNumero() != null ? telefoneDTORequest.getNumero() : telefoneEntity.getNumero())
                .build();
    }


    // ----------- Cadastrar novo Endereco e Telefone -----------

    public EnderecoEntity NovoEnderecoParaEntity(EnderecoDTORequest enderecoDTORequest, UsuarioEntity usuarioEntity){
        return EnderecoEntity.builder()
                .rua(enderecoDTORequest.getRua())
                .cidade(enderecoDTORequest.getCidade())
                .cep(enderecoDTORequest.getCep())
                .complemento(enderecoDTORequest.getComplemento())
                .estado(enderecoDTORequest.getEstado())
                .numero(enderecoDTORequest.getNumero())
                .usuarioEntity(usuarioEntity)
                .build();
    }

    public TelefoneEntity NovoTelefoneParaEntity(TelefoneDTORequest telefoneDTORequest, UsuarioEntity usuarioEntity){
        return TelefoneEntity.builder()
                .ddd(telefoneDTORequest.getDdd())
                .numero(telefoneDTORequest.getNumero())
                .usuarioEntity(usuarioEntity)
                .build();
    }
}
