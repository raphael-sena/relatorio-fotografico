package com.newenergy.inspecao_rei.services;

import com.newenergy.inspecao_rei.models.Item;
import com.newenergy.inspecao_rei.models.Relatorio;
import com.newenergy.inspecao_rei.models.dtos.ItemDTO;
import com.newenergy.inspecao_rei.repositories.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ItemService {

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private RelatorioService relatorioService;

    @Transactional
    public void criarItem(ItemDTO obj) {

        Item item = new Item();
        item.setCodigo(obj.getCodigo());
        item.setImagem(obj.getImagem());

        Relatorio relatorio = relatorioService.findById(obj.getRelatorioId());
        item.setRelatorio(relatorio);

        relatorioService.addItem(item);
        itemRepository.save(item);
    }
}
