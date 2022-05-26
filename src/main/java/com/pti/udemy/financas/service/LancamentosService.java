package com.pti.udemy.financas.service;

import com.pti.udemy.financas.model.entity.Lancamentos;
import com.pti.udemy.financas.model.enums.StatusLancamento;

import java.util.List;

public interface LancamentosService {

    Lancamentos salvar (Lancamentos lancamentos);

    Lancamentos atualizar (Lancamentos lancamentos);

    List<Lancamentos> consultar (Lancamentos lancamentosFiltro);

    void excluir (Lancamentos lancamentos);

    void atualizarStatus (Lancamentos lancamentos, StatusLancamento statusLancamento);

}
