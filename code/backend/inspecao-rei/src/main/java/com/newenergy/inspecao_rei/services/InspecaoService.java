package com.newenergy.inspecao_rei.services;

import com.newenergy.inspecao_rei.models.Inspecao;
import com.newenergy.inspecao_rei.models.Item;
import com.newenergy.inspecao_rei.models.Relatorio;
import com.newenergy.inspecao_rei.models.dtos.InspecaoCreateDTO;
import com.newenergy.inspecao_rei.models.dtos.InspecaoUpdateDTO;
import com.newenergy.inspecao_rei.models.dtos.ItemDTO;
import com.newenergy.inspecao_rei.repositories.InspecaoRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class InspecaoService {

    @Autowired
    private InspecaoRepository inspecaoRepository;

    @Autowired
    private RelatorioService relatorioService;

    @Transactional
    public List<Inspecao> findAll() {
        return inspecaoRepository.findAll();
    }

    @Transactional
    public Inspecao findById(Long id) {
        Optional<Inspecao> inspecao = this.inspecaoRepository.findById(id);
        return inspecao.orElseThrow(() -> new EntityNotFoundException(
                "Inspeção não encontrada! Id: " + id + ", Tipo: " + Inspecao.class.getName()));
    }

    @Transactional
    public void criarInspecao(InspecaoCreateDTO obj) {
        Inspecao inspecao = new Inspecao();
        inspecao.setCliente(obj.getCliente());
        inspecao.setData(LocalDate.now());
        inspecao.setPedidoCompra(obj.getPedidoCompra());
        inspecao = inspecaoRepository.save(inspecao);

        Relatorio relatorio = relatorioService.criarRelatorio(inspecao);

        inspecao.setRelatorio(relatorio);
        inspecaoRepository.save(inspecao);
    }

    @Transactional
    public Inspecao updateInspecao(InspecaoUpdateDTO obj) {
        findById(obj.getId());

        Inspecao newObj = findById(obj.getId());

        newObj.setCliente(obj.getCliente());
        newObj.setPedidoCompra(obj.getPedidoCompra());

        return this.inspecaoRepository.save(newObj);
    }

    public void deletarInspecao(Long id) {
        findById(id);
        this.inspecaoRepository.deleteById(id);
    }
}
