package com.fabricaescuela.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fabricaescuela.models.entity.Permiso;

@Repository
public interface PermisoRepository extends JpaRepository<Permiso, Long> {
    Permiso findByNombre(String nombre);
}
