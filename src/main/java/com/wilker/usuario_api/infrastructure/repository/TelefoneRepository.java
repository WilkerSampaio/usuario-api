package com.wilker.usuario_api.infrastructure.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TelefoneRepository extends JpaRepository<TelefoneRepository, Long> {
    Optional<TelefoneRepository> findById(Long id);
}
