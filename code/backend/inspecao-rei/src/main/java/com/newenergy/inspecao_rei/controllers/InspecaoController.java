package com.newenergy.inspecao_rei.controllers;

import com.newenergy.inspecao_rei.models.Inspecao;
import com.newenergy.inspecao_rei.models.dtos.InspecaoCreateDTO;
import com.newenergy.inspecao_rei.models.dtos.InspecaoDTO;
import com.newenergy.inspecao_rei.models.dtos.InspecaoUpdateDTO;
import com.newenergy.inspecao_rei.models.dtos.ItemDTO;
import com.newenergy.inspecao_rei.services.InspecaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/inspecao")
public class InspecaoController {

    @Autowired
    private InspecaoService inspecaoService;

    @GetMapping
    public ResponseEntity<List<InspecaoDTO>> findAll() {
        List<Inspecao> inspecoes = inspecaoService.findAll();

        List<InspecaoDTO> inspecaoDTOS = inspecoes.stream()
                .map(inspecao -> new InspecaoDTO(
                        inspecao.getId(),
                        inspecao.getData(),
                        inspecao.getCliente(),
                        inspecao.getPedidoCompra(),
                        inspecao.getRelatorio().getId())
                )
                .collect(Collectors.toList());

        return ResponseEntity
                .ok()
                .body(inspecaoDTOS);
    }

    @GetMapping("/{id}")
    public ResponseEntity<InspecaoDTO> findById(@PathVariable Long id) {
        Inspecao inspecao = this.inspecaoService.findById(id);

        InspecaoDTO inspecaoDTO = new InspecaoDTO(
                inspecao.getId(),
                inspecao.getData(),
                inspecao.getCliente(),
                inspecao.getPedidoCompra(),
                inspecao.getRelatorio().getId()
        );

        return ResponseEntity.ok(inspecaoDTO);
    }

    @PostMapping
    public ResponseEntity<Void> criarInspecao(@RequestBody InspecaoCreateDTO obj) {

        inspecaoService.criarInspecao(obj);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .build();
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
