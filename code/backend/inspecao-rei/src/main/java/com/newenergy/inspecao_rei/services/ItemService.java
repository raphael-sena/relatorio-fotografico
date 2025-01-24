package com.newenergy.inspecao_rei.services;

import com.newenergy.inspecao_rei.models.Item;
import com.newenergy.inspecao_rei.models.dtos.ItemDTO;
import com.newenergy.inspecao_rei.repositories.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ItemService {

    @Autowired
    private ItemRepository itemRepository;

    @Transactional
    public void criarItem(ItemDTO obj) {

        Item item = new Item();
        item.setImagem(obj.getImagem());
        item.setRelatorio(obj.getRelatorio());

        itemRepository.save(item);
    }
}
