package com.pti.udemy.financas.service.impl;

import com.pti.udemy.financas.exceptions.RegraNegocioException;
import com.pti.udemy.financas.model.entity.Lancamentos;
import com.pti.udemy.financas.model.enums.StatusLancamento;
import com.pti.udemy.financas.model.repository.LancamentosRepository;
import com.pti.udemy.financas.service.LancamentosService;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;
import org.springframework.web.client.support.RestGatewaySupport;

import javax.transaction.Transactional;
import java.math.BigDecimal;
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
        validar(lancamentos);
        lancamentos.setStatus(StatusLancamento.PENDENTE);
        return repository.save(lancamentos);
    }

    @Override
    public Lancamentos atualizar(Lancamentos lancamentos) {
        Objects.requireNonNull(lancamentos.getId());
        validar(lancamentos);
        return repository.save(lancamentos);
    }

    @Override
    @Transactional
    public List<Lancamentos> consultar(Lancamentos lancamentosFiltro) {
        Example example = Example.of(lancamentosFiltro,
                ExampleMatcher.matching()
                        .withIgnoreCase()
                        .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING)
        );
        return repository.findAll(example);
    }

    @Override
    @Transactional
    public void excluir(Lancamentos lancamentos) {
        Objects.requireNonNull(lancamentos.getId());
        repository.delete(lancamentos);

    }

    @Override
    public void atualizarStatus(Lancamentos lancamentos, StatusLancamento statusLancamento) {
        lancamentos.setStatus(statusLancamento);
        atualizar(lancamentos);

    }

    @Override
    public void validar(Lancamentos lancamentos) {
        if (lancamentos.getDescricao() == null || lancamentos.getDescricao().trim().equals("")){
            throw new RegraNegocioException("Informe uma Descrição válida.");
        }

        if (lancamentos.getMes() == null || lancamentos.getMes() < 1 || lancamentos.getMes() > 12){
            throw new RegraNegocioException("Informe um Mês válido.");
        }

        if (lancamentos.getAno() == null || lancamentos.getAno().toString().length() != 4){
            throw new RegraNegocioException("Informe um Ano válido.");
        }

        if (lancamentos.getUsuario() == null || lancamentos.getUsuario().getId() == null){
            throw new RegraNegocioException("Informe um Usuário");
        }

        if (lancamentos.getValor() == null || lancamentos.getValor().compareTo(BigDecimal.ZERO) < 1){
            throw new RegraNegocioException("Informe um Valor válido");
        }

        if (lancamentos.getTipo() == null){
            throw new RegraNegocioException("Informe um Tipo de lançamento.");
        }

    }
}
