package com.pti.udemy.financas.model.repository;

import com.pti.udemy.financas.model.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    //Query Methods
    boolean existsByEmail(String email);

}
