package com.wilker.usuario_api.infrastructure.security;


import com.wilker.usuario_api.infrastructure.entity.UsuarioEntity;
import com.wilker.usuario_api.infrastructure.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    // Repositório para acessar dados de usuário no banco de dados
    @Autowired
    private UsuarioRepository usuarioRepository;

    // Implementação do metodo que carrega os detalhes de autenticação de um usuário pelo email
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        // Busca o usuário no banco de dados pelo email. Se não encontrar, lança UsernameNotFoundException
        UsuarioEntity usuarioEntity = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado: " + email));

        return org.springframework.security.core.userdetails.User
                .withUsername(usuarioEntity.getEmail()) // Define o nome de usuário como o email
                .password(usuarioEntity.getSenha()) // Define a senha do usuário
                .build(); // Constrói o objeto UserDetails
    }
}
