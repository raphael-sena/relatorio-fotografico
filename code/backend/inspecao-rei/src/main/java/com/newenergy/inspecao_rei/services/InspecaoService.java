package com.newenergy.inspecao_rei.services;

import com.newenergy.inspecao_rei.models.Inspecao;
import com.newenergy.inspecao_rei.models.dtos.InspecaoCreateDTO;
import com.newenergy.inspecao_rei.repositories.InspecaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InspecaoService {

    @Autowired
    private InspecaoRepository inspecaoRepository;

    public void criarInspecao(InspecaoCreateDTO obj) {
        Inspecao inspecao = new Inspecao();
        inspecao.setCliente(obj.getCliente());
        inspecao.setData(obj.getData());
        inspecaoRepository.save(inspecao);
    }
}
