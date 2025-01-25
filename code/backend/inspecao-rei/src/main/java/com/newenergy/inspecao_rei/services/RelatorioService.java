package com.newenergy.inspecao_rei.services;

import com.newenergy.inspecao_rei.models.Inspecao;
import com.newenergy.inspecao_rei.models.Relatorio;
import com.newenergy.inspecao_rei.repositories.RelatorioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class RelatorioService {

    @Autowired
    private RelatorioRepository relatorioRepository;

    public Relatorio criarRelatorio(Inspecao obj) {
        Relatorio relatorio = new Relatorio();
        relatorio.setInspecao(obj);
        relatorio.setItens(Collections.emptyList());
        return relatorioRepository.save(relatorio);
    }
}
