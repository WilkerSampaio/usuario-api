package com.wilker.usuario_api.infrastructure.repository;

import com.wilker.usuario_api.infrastructure.entity.UsuarioEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<UsuarioEntity, Long> {

    boolean existsByEmail(String email);

    Optional<UsuarioEntity> findByEmail(String email);

    @Transactional
    void deleteByEmail(String email);
}

