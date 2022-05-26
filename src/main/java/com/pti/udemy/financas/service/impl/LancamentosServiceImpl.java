package com.pti.udemy.financas.service.impl;

import com.pti.udemy.financas.model.entity.Lancamentos;
import com.pti.udemy.financas.model.enums.StatusLancamento;
import com.pti.udemy.financas.model.repository.LancamentosRepository;
import com.pti.udemy.financas.service.LancamentosService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;

@Service
public class LancamentosServiceImpl implements LancamentosService {

    private LancamentosRepository repository;

    public LancamentosServiceImpl(LancamentosRepository lancamentosRepository){
        this.repository = lancamentosRepository;
    }


    @Override
    @Transactional//Se sucesso faz commit, se erro faz Rollback;
    public Lancamentos salvar(Lancamentos lancamentos) {
        return repository.save(lancamentos);
    }

    @Override
    public Lancamentos atualizar(Lancamentos lancamentos) {
        Objects.requireNonNull(lancamentos.getId());
        return repository.save(lancamentos);
    }

    @Override
    public List<Lancamentos> consultar(Lancamentos lancamentosFiltro) {
        return null;
    }

    @Override
    public void excluir(Lancamentos lancamentos) {
        Objects.requireNonNull(lancamentos.getId());
        repository.delete(lancamentos);

    }

    @Override
    public void atualizarStatus(Lancamentos lancamentos, StatusLancamento statusLancamento) {
        lancamentos.setStatus(statusLancamento);
        atualizar(lancamentos);

    }
}
