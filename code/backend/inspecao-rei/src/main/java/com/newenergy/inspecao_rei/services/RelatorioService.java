package com.newenergy.inspecao_rei.services;

import com.newenergy.inspecao_rei.models.Relatorio;
import com.newenergy.inspecao_rei.models.dtos.RelatorioDTO;
import com.newenergy.inspecao_rei.repositories.RelatorioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RelatorioService {

    @Autowired
    private RelatorioRepository relatorioRepository;

    public void criarRelatorio(RelatorioDTO obj) {
        Relatorio relatorio = new Relatorio();
        relatorio.setInspecao(obj.getInspecao());
        relatorio.setItens(obj.getItens());
        relatorioRepository.save(relatorio);
    }
}
