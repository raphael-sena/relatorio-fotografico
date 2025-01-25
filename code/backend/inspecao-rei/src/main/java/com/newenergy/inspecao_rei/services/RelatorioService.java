package com.newenergy.inspecao_rei.services;

import com.newenergy.inspecao_rei.models.Inspecao;
import com.newenergy.inspecao_rei.models.Item;
import com.newenergy.inspecao_rei.models.Relatorio;
import com.newenergy.inspecao_rei.repositories.RelatorioRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;

@Service
public class RelatorioService {

    @Autowired
    private RelatorioRepository relatorioRepository;

    public Relatorio findById(Long id) {
        return relatorioRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Relatório não encontrado para o ID: " + id));
    }

    public Relatorio criarRelatorio(Inspecao obj) {
        Relatorio relatorio = new Relatorio();
        relatorio.setInspecao(obj);
        relatorio.setItens(Collections.emptyList());
        return relatorioRepository.save(relatorio);
    }

    @Transactional
    public void addItem(Item item) {
        Relatorio relatorio = findById(item.getRelatorio().getId());
        relatorio.getItens().add(item);
    }

    @Transactional
    public void removeItem(Item item) {
        Relatorio relatorio = findById(item.getRelatorio().getId());
        relatorio.getItens().remove(item);
    }


}
