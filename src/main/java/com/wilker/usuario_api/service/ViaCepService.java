package com.wilker.usuario_api.service;

import com.wilker.usuario_api.infrastructure.clients.ViaCepClient;
import com.wilker.usuario_api.infrastructure.dto.out.ViaCepDTO;
import com.wilker.usuario_api.infrastructure.exception.IllegalArgumentException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ViaCepService {
    private final ViaCepClient viaCepClient;

    public ViaCepDTO buscaDadosEndereco(String cep){
        String cepFormatado = processarCep(cep);
        return viaCepClient.buscaDadosEndereco(cep);
    }

    private String processarCep(String cep){
        String cepFormatado = cep.replace(" ", "").replace("-", "");

        if(!cepFormatado.matches("\\d{8}")){
            throw new IllegalArgumentException("O CEP contém caracteres inválidos");
        }

        return cepFormatado;

    }

}
