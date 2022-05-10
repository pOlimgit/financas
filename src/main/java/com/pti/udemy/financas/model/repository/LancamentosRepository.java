package com.pti.udemy.financas.model.repository;

import com.pti.udemy.financas.model.entity.Lancamentos;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LancamentosRepository extends JpaRepository<Lancamentos, Long> {
}
