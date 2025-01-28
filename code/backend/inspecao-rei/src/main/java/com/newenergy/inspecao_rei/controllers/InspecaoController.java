package com.newenergy.inspecao_rei.controllers;

import com.newenergy.inspecao_rei.models.Inspecao;
import com.newenergy.inspecao_rei.models.dtos.*;
import com.newenergy.inspecao_rei.services.InspecaoService;
import com.newenergy.inspecao_rei.services.ItemService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/inspecao")
public class InspecaoController {

    @Autowired
    private InspecaoService inspecaoService;

    @Autowired
    private ItemService itemService;

    @GetMapping
    public ResponseEntity<List<InspecaoDTO>> findAll() {
        List<Inspecao> inspecoes = inspecaoService.findAll();

        List<InspecaoDTO> inspecaoDTOS = inspecoes.stream()
                .map(inspecao -> new InspecaoDTO(
                        inspecao.getId(),
                        inspecao.getData(),
                        inspecao.getCliente(),
                        inspecao.getPedidoCompra(),
                        inspecao.getItens().stream()
                                .map(item -> new ItemMinDTO(item.getId(), item.getCodigo()))
                                .toList())
                )
                .collect(Collectors.toList());

        return ResponseEntity.ok().body(inspecaoDTOS);
    }

    @GetMapping("/{id}")
    public ResponseEntity<InspecaoDTO> findById(@PathVariable Long id) {
        Inspecao inspecao = this.inspecaoService.findById(id);

        InspecaoDTO inspecaoDTO = new InspecaoDTO(
                inspecao.getId(),
                inspecao.getData(),
                inspecao.getCliente(),
                inspecao.getPedidoCompra(),
                inspecao.getItens().stream()
                        .map(item -> new ItemMinDTO(item.getId(), item.getCodigo()))
                        .toList()
        );

        return ResponseEntity.ok(inspecaoDTO);
    }

    @PostMapping
    public ResponseEntity<Long> criarInspecao(@RequestBody InspecaoCreateDTO obj) {
        Inspecao createdInspecao = inspecaoService.criarInspecao(obj);

        return ResponseEntity.status(HttpStatus.CREATED).body(createdInspecao.getId());
    }

    @PostMapping("/{id}/itens")
    public ResponseEntity<Void> adicionarItens(@PathVariable Long id, @RequestBody List<ItemDTO> itensDTO) {
        try {
            // Para cada item, associamos à inspeção correspondente
            for (ItemDTO itemDTO : itensDTO) {
                itemService.criarItem(itemDTO, id);
            }
            return ResponseEntity.ok().build();
        } catch (EntityNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> atualizarInspecao(@RequestBody InspecaoUpdateDTO inspecao, @PathVariable Long id) {
        inspecao.setId(id);
        inspecaoService.updateInspecao(inspecao);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarInspecao(@PathVariable Long id) {
        this.inspecaoService.deletarInspecao(id);
        return ResponseEntity.noContent().build();
    }
}
