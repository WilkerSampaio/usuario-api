package com.wilker.usuario_api.infrastructure.repository;

import com.wilker.usuario_api.infrastructure.entity.EnderecoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EnderecoRepository extends JpaRepository<EnderecoEntity, Long> {

    @Override
    Optional<EnderecoEntity> findById(Long id);
}
