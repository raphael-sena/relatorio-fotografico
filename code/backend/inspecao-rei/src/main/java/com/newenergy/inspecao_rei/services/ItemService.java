package com.newenergy.inspecao_rei.services;

import com.newenergy.inspecao_rei.models.Item;
import com.newenergy.inspecao_rei.models.Relatorio;
import com.newenergy.inspecao_rei.models.dtos.ItemDTO;
import com.newenergy.inspecao_rei.repositories.ItemRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Service
public class ItemService {

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private RelatorioService relatorioService;

    @Transactional
    public List<Item> findAll() {
        return itemRepository.findAll();
    }

    @Transactional
    public Item findById(Long id) {
        Optional<Item> user = this.itemRepository.findById(id);
        return user.orElseThrow(() -> new EntityNotFoundException(
                "item não encontrado! Id: " + id + ", Tipo: " + Item.class.getName()));
    }

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

    @Transactional
    public Item updateItem(ItemDTO obj) {
        findById(obj.getId());
        Item oldItem = findById(obj.getId());
        relatorioService.removeItem(oldItem);

        Item newObj = findById(obj.getId());

        newObj.setCodigo(obj.getCodigo());
        newObj.setImagem(obj.getImagem());

        if (obj.getRelatorioId() != null) {
            Relatorio relatorio = relatorioService.findById(obj.getRelatorioId());
            if (relatorio != null) {
                newObj.setRelatorio(relatorio);
            } else {
                throw new RuntimeException("Relatório não encontrado.");
            }
        }

        relatorioService.addItem(newObj);

        return this.itemRepository.save(newObj);
    }

    public Item fromDTO(@Valid ItemDTO obj) {
        Item item = new Item();
        item.setImagem(obj.getImagem());
        item.setCodigo(obj.getCodigo());

        Relatorio relatorio = relatorioService.findById(obj.getRelatorioId());
        item.setRelatorio(relatorio);
        return item;
    }

    public void deletarItem(Long id) {
        findById(id);
        this.itemRepository.deleteById(id);
    }
}
