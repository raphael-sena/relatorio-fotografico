package com.newenergy.inspecao_rei.controllers;

import com.newenergy.inspecao_rei.models.Item;
import com.newenergy.inspecao_rei.models.dtos.ItemDTO;
import com.newenergy.inspecao_rei.services.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/item")
public class ItemController {

    @Autowired
    private ItemService itemService;

    @GetMapping
    public ResponseEntity<List<ItemDTO>> findAll() {
        List<Item> items = itemService.findAll();

        List<ItemDTO> itemDTOs = items.stream()
                .map(item -> new ItemDTO(
                        item.getId(),
                        item.getCodigo(),
                        item.getImagem(),
                        item.getInspecao().getId())
                )
                .collect(Collectors.toList());

        return ResponseEntity.ok().body(itemDTOs);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ItemDTO> findById(@PathVariable Long id) {
        Item item = this.itemService.findById(id);

        ItemDTO itemDTO = new ItemDTO(
                item.getId(),
                item.getCodigo(),
                item.getImagem(),
                item.getInspecao().getId()
        );

        return ResponseEntity.ok(itemDTO);
    }

    @PostMapping
    public ResponseEntity<Void> criarItem(@RequestBody ItemDTO itemDTO) {
        itemService.criarItem(itemDTO);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(itemDTO.getId())
                .toUri();

        return ResponseEntity.created(uri).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> atualizarItem(@RequestBody ItemDTO itemDTO, @PathVariable Long id) {
        itemDTO.setId(id);
        itemService.updateItem(itemDTO);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarItem(@PathVariable Long id) {
        this.itemService.deletarItem(id);
        return ResponseEntity.noContent().build();
    }
}
