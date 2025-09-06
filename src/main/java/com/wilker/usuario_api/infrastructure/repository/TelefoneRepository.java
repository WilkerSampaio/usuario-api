package com.wilker.usuario_api.infrastructure.repository;

import com.wilker.usuario_api.infrastructure.entity.TelefoneEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TelefoneRepository extends JpaRepository<TelefoneEntity, Long> {
    Optional<TelefoneEntity> findById(Long id);
}
