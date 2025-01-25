package com.newenergy.inspecao_rei.controllers;

import com.newenergy.inspecao_rei.models.dtos.RelatorioDTO;
import com.newenergy.inspecao_rei.services.RelatorioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/relatorio")
public class RelatorioController {

    @Autowired
    private RelatorioService relatorioService;

//    @PostMapping
//    public ResponseEntity<Void> criarRelatorio(RelatorioDTO obj) {
//
//        relatorioService.criarRelatorio(obj);
//        return ResponseEntity
//                .status(HttpStatus.CREATED)
//                .build();
//    }
}
