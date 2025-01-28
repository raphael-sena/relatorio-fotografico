package com.newenergy.inspecao_rei.services;

import com.newenergy.inspecao_rei.models.Inspecao;
import com.newenergy.inspecao_rei.models.Item;
import com.newenergy.inspecao_rei.models.dtos.ItemDTO;
import com.newenergy.inspecao_rei.repositories.ItemRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ItemService {

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private InspecaoService inspecaoService;

    @Transactional
    public List<Item> findAll() {
        return itemRepository.findAll();
    }

    @Transactional
    public Item findById(Long id) {
        Optional<Item> item = this.itemRepository.findById(id);
        return item.orElseThrow(() -> new EntityNotFoundException(
                "Item não encontrado! Id: " + id + ", Tipo: " + Item.class.getName()));
    }

    @Transactional
    public void criarItem(ItemDTO obj) {
        Item item = new Item();
        item.setCodigo(obj.getCodigo());
        item.setImagem(obj.getImagem());

        if (obj.getInspecaoId() != null) {
            Inspecao inspecao = inspecaoService.findById(obj.getInspecaoId());
            item.setInspecao(inspecao);
        }

        itemRepository.save(item);

        if (obj.getInspecaoId() != null) {
            Inspecao inspecao = item.getInspecao();
            if (!inspecao.getItens().contains(item)) {
                inspecao.getItens().add(item);
                inspecaoService.criarInspecao(inspecao);
            }
        }
    }

    @Transactional
    public Item updateItem(ItemDTO obj) {
        Item oldItem = findById(obj.getId());

        oldItem.setCodigo(obj.getCodigo());
        oldItem.setImagem(obj.getImagem());

        if (obj.getInspecaoId() != null) {
            Inspecao inspecao = inspecaoService.findById(obj.getInspecaoId());
            oldItem.setInspecao(inspecao);
        }

        itemRepository.save(oldItem);

        return oldItem;
    }


    @Transactional
    public void deletarItem(Long id) {
        Item item = findById(id);

        Inspecao inspecao = item.getInspecao();
        if (inspecao != null) {
            inspecao.getItens().remove(item);
            inspecaoService.save(inspecao);
        }

        itemRepository.deleteById(id);
    }

}
