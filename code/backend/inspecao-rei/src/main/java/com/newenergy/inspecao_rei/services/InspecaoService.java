package com.newenergy.inspecao_rei.services;

import com.newenergy.inspecao_rei.models.Inspecao;
import com.newenergy.inspecao_rei.models.Relatorio;
import com.newenergy.inspecao_rei.models.dtos.InspecaoCreateDTO;
import com.newenergy.inspecao_rei.repositories.InspecaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class InspecaoService {

    @Autowired
    private InspecaoRepository inspecaoRepository;

    @Autowired
    private RelatorioService relatorioService;

    @Transactional
    public void criarInspecao(InspecaoCreateDTO obj) {
        Inspecao inspecao = new Inspecao();
        inspecao.setCliente(obj.getCliente());
        inspecao.setData(obj.getData());
        inspecao.setPedidoCompra(obj.getPedidoCompra());
        inspecao = inspecaoRepository.save(inspecao);

        Relatorio relatorio = relatorioService.criarRelatorio(inspecao);

        inspecao.setRelatorio(relatorio);
        inspecaoRepository.save(inspecao);
    }
}
